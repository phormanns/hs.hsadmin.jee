package de.hsadmin.bo.database;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "PgSqlDatabase")
@DiscriminatorValue("pgsql")
public class PgSqlDatabase extends Database implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public PgSqlDatabase() {
		setInstance("pgsql");
		setEncoding("UTF-8");
	}

	@Override
	public Class<? extends DatabaseUser> getSqlUserClass() {
		return PgSqlUser.class;
	}

	public String getSystemEncoding() {
		return getEncoding();
	}

}

