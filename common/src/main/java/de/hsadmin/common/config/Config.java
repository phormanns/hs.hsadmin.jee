package de.hsadmin.common.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import de.hsadmin.common.error.TechnicalException;

public class Config {

	public static final String CAS_VALIDATE_URL = "cas.validate.url";
	public static final String CAS_SERVICE_URL = "cas.service.url";
	public static final String PAC_HOSTMASTER_ACCOUNTS = "accountprefix.hostmaster";
	public static final String PAC_CUSTOMER_ACCOUNTS = "accountprefix.customer";
	public static final String PACKET_DOMAINS_POSTFIX = "domainpostfix.pacdomain";
	public static final String TICKETVALIDATOR_CLASS = "ticketvalidator.class";
	
	private static Config instance;

	private final Properties props;
	
	private Config() throws TechnicalException {
		props = new Properties();
		File file = new File(System.getProperty("user.dir") + "/hsadmin.properties");
		if (!file.canRead()) {
			file = new File(System.getProperty("user.dir") + "/conf/hsadmin.properties");
		}
		if (!file.canRead()) {
			file = new File(System.getProperty("user.home") + "/.hsadmin.properties");
		}
		if (!file.canRead()) {
			file = new File("/etc/hsadmin.properties");
		}
		if (!file.canRead()) {
			file = new File("/etc/hsadmin/hsadmin.properties");
		}
		if (file.canRead()) {
			try {
				props.load(new FileReader(file));
			} catch (IOException e) {
				throw new TechnicalException(e);
			}
		}
	}
	
	public static Config getInstance() throws TechnicalException {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
	
	public String getProperty(final String propertyName) {
		return props.getProperty(propertyName);
	}
	
	public String getProperty(final String propertyName, final String defaultValue) {
		return props.getProperty(propertyName, defaultValue).trim();
	}
	
}
