package de.hsadmin.bo.database;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "MySqlDatabase")
@DiscriminatorValue("mysql")
public class MySqlDatabase extends Database implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public MySqlDatabase() {
		setInstance("mysql");
		setEncoding("UTF-8");
	}

	public String getSystemEncoding() {
		String sysEnc = getEncoding().toLowerCase().replaceAll("-", "");
		return sysEnc;
	}

	@Override
	public Class<? extends DatabaseUser> getSqlUserClass() {
		return MySqlUser.class;
	}

}
