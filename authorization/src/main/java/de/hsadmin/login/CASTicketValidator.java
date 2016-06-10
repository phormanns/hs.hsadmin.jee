package de.hsadmin.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import de.hsadmin.common.config.Config;
import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserException;

public class CASTicketValidator implements TicketValidator {

	private final String proxyValidateURL;
	private final String proxyServiceURL;
	
	public CASTicketValidator() throws TechnicalException {
		Config config = Config.getInstance();
		proxyValidateURL = config.getProperty(Config.CAS_VALIDATE_URL);
		proxyServiceURL = config.getProperty(Config.CAS_SERVICE_URL);
	}
	
	@Override
	public String validate(final String ticket) throws UserException, TechnicalException {
		if (proxyServiceURL == null || proxyValidateURL == null) {
			throw new TechnicalException("TicketValidator is not initialized.");
		}
		try {
			URL url = new URL(proxyValidateURL + "?service=" + proxyServiceURL + "&ticket=" + ticket);
			URLConnection httpConnection = url.openConnection();
			httpConnection.connect();
			InputStream inputStream = httpConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String nextLine = reader.readLine();  
			while (nextLine != null) {
				if (nextLine.contains("<cas:user>")) {
					String user = extractUser(nextLine);
					inputStream.close();
					return user;
				}
				nextLine = reader.readLine();
			}
			inputStream.close();
			throw new UserException(new UserError(UserError.MSG_INVALID_TICKET));
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	private String extractUser(String nextLine) {
		int start = nextLine.indexOf("<cas:user>");
		int end = nextLine.indexOf("</cas:user>");
		String user = nextLine.substring(start + 10, end);
		return user;
	}

}
