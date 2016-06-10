package de.hsadmin.bo.database;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.hsadmin.bo.pac.Pac;

@Entity
@Table(name = "database_user")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="engine", discriminatorType=DiscriminatorType.STRING)
@SequenceGenerator(name = "DatabaseUserSeqGen", sequenceName = "dbuser_dbuser_id_seq")
public abstract class DatabaseUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "DatabaseUserSeqGen")
	@Column(name = "dbuser_id", columnDefinition = "integer", updatable=false, insertable=false)
	private long id;

	@Column(name = "name", columnDefinition = "character varying(24)", updatable=false)
	private String name;

	@Transient
	private String password;

	@Column(name = "engine", columnDefinition = "character varying(12)", updatable=false)
	protected String instance;

	@JoinColumn(name = "packet_id", columnDefinition = "integer", updatable=false)
	@ManyToOne(fetch = EAGER)
	protected Pac pac;

	protected DatabaseUser() {
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Pac getPac() {
		return pac;
	}

	public void setPac(Pac pac) {
		this.pac = pac;
	}

}
