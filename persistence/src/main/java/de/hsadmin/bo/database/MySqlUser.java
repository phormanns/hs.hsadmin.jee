package de.hsadmin.bo.database;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "MySqlUser")
@DiscriminatorValue("mysql")
public class MySqlUser extends DatabaseUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public MySqlUser() {
		setInstance("mysql");
	}

}
