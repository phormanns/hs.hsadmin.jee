package de.hsadmin.bo.database;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "PgSqlUser")
@DiscriminatorValue("pgsql")
public class PgSqlUser extends DatabaseUser implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String createQueryFromStringKey(String humanKey) {
		return "obj.name='" + humanKey + "' AND obj.instance='pgsql'";
	}

	public PgSqlUser() {
		setInstance("pgsql");
	}

}
