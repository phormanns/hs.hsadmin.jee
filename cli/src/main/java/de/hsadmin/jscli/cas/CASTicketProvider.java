package de.hsadmin.jscli.cas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import de.hsadmin.jscli.conf.Config;
import de.hsadmin.jscli.console.PasswordReader;
import de.hsadmin.jscli.exception.JSCliException;

public class CASTicketProvider {

	private static final String LOGIN_URL = "https://login.hostsharing.net:443/cas/v1/tickets";
	private static final String BACKEND_URL = "https://config.hostsharing.net:443/hsar/backend";

	final private String loginURL;
	final private String backendURL;
	final private String runAs;
	final private String user;
	final private PasswordReader passwordReader;

	private String grantingTicket;

	public CASTicketProvider(final PasswordReader console, final String user, final String runAs) throws JSCliException {
		this.passwordReader = console;
		this.user = user;
		this.runAs = runAs;
		final Config config = Config.getInstance();
		backendURL = config.getProperty("backendURL", BACKEND_URL);
		loginURL = config.getProperty("loginURL", LOGIN_URL);
		if ("TestUmgebung".equals(loginURL)) {
			grantingTicket = "ticket:" + user;
		} else {
			grantingTicket = readFiledGrantingTicket();
		}
	}
	
	public String getTicket() throws JSCliException, FileNotFoundException {
		if (grantingTicket != null && grantingTicket.startsWith("ticket:")) {
			return grantingTicket.replaceFirst("ticket", "user");
		}
		try {
			String encodedParams = URLEncoder.encode("service", "UTF-8")
					+ "=" + URLEncoder.encode(backendURL, "UTF-8");
				return doHttpPost(grantingTicket, encodedParams);
		} catch (UnsupportedEncodingException e) {
			throw new JSCliException(e);
		}
	}

	public String getRunAs() {
		return runAs;
	}

	private String getGrantingTicket() throws JSCliException {
		grantingTicket = null;
		String password = Config.getInstance().getProperty(user + ".passWord");
		if (password == null || password.length() <= 0) {
			password = readPasswordFromConsole();
		}
		try {
			String encodedParams = URLEncoder.encode("username", "UTF-8")
					+ "=" + URLEncoder.encode(user, "UTF-8")
					+ "&" + URLEncoder.encode("password", "UTF-8")
					+ "=" + URLEncoder.encode(password, "UTF-8");
			grantingTicket = doHttpPost(loginURL, encodedParams);		
		} catch (UnsupportedEncodingException e) {
			throw new JSCliException(e);
		} catch (FileNotFoundException e) {
			throw new JSCliException("cas server not available: " + loginURL);
		}
		return grantingTicket;
	}

	private String readPasswordFromConsole() throws JSCliException {
		return passwordReader.readPassword();
	}

	private String doHttpPost(final String urlString, final String encodedParams) throws JSCliException, FileNotFoundException {
		String result = null;
		try {
			result = extractTicket(urlString, encodedParams);
		} catch (FileNotFoundException e) {
			grantingTicket = getGrantingTicket();
			saveProperties(grantingTicket, getTicketFile());
			try {
				result = extractTicket(grantingTicket, encodedParams);
			} catch (IOException e1) {
				throw new JSCliException(e1);
			}
		} catch (IOException e) {
			throw new JSCliException(e);
		}
		return result;
	}

	private String extractTicket(final String urlString,
			final String encodedParams) throws MalformedURLException,
			IOException, ProtocolException {
		String result;
		final HttpsURLConnection connection = doConnect(urlString, encodedParams);
		final String ticket = readTicket(connection);
		if (ticket != null && ticket.startsWith("ST-")) {
			result = ticket;
		} else {
			result = connection.getHeaderField("Location");
		}
		return result;
	}

	private String readTicket(final HttpsURLConnection connection)
			throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		final String ticket = reader.readLine();
		String readLine = null;
		do {
			readLine = reader.readLine();
		} while (readLine != null);
		return ticket;
	}

	private HttpsURLConnection doConnect(final String urlString,
			final String encodedParams) throws MalformedURLException,
			IOException, ProtocolException {
		final URL url = new URL(urlString);
		final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setAllowUserInteraction(false);
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
		writer.write(encodedParams);
		writer.close();
		return connection;
	}

	private String readFiledGrantingTicket() throws JSCliException {
		String filedTicket = null;
		final File file = getTicketFile();
		final Properties properties = loadProperties(file);
		filedTicket = properties.getProperty(user);
		if (filedTicket == null) {
			filedTicket = getGrantingTicket();
			saveProperties(filedTicket, file);
		}
		return filedTicket;
	}

	private File getTicketFile() {
		final String userHome = System.getProperty("user.home");
		final String ticketFileName = userHome + "/.hsadmin.tgt";
		return new File(ticketFileName);
	}

	private void saveProperties(final String filedTicket, final File file) throws JSCliException {
		final Properties properties = loadProperties(file);
		if (filedTicket != null) {
			properties.setProperty(user, filedTicket);
			try {
				properties.store(new FileOutputStream(file), "");
			} catch (IOException e) {
				throw new JSCliException(e);
			}
		}
	}

	private Properties loadProperties(final File file) throws JSCliException {
		final Properties properties = new Properties();
		if (file.isFile() && file.canRead()) {
			try {
				properties.load(new FileReader(file));
			} catch (IOException e) {
				throw new JSCliException(e);
			}
		}
		return properties;
	}

	@Override
	public String toString() {
		return grantingTicket;
	}
	
}
