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

import de.hsadmin.bo.pac.Pac;

@Entity
@Table(name = "database")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="engine", discriminatorType=DiscriminatorType.STRING)
@SequenceGenerator(name = "DatabaseSeqGen", sequenceName = "database_database_id_seq")
public abstract class Database implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "DatabaseSeqGen")
	@Column(name = "database_id", columnDefinition = "integer", updatable=false, insertable=false)
	private long id;

	@Column(name = "engine", columnDefinition = "character varying(12)", updatable=false)
	private String instance;

	@Column(name = "name", columnDefinition = "character varying(24)", updatable=false)
	private String name;

	@Column(name = "owner", columnDefinition = "character varying(24)")
	private String owner;

	@JoinColumn(name = "packet_id", columnDefinition = "integer", updatable=false)
	@ManyToOne(fetch = EAGER)
	private Pac pac;

	@Column(name = "encoding", columnDefinition = "character varying(24)", updatable=false)
	private String encoding;

	protected Database() {
		encoding = "UTF-8";
	}

	protected Database(String instance, Pac pac, String name, String owner,
			String encoding) {
		this.instance = instance;
		this.pac = pac;
		this.name = name;
		this.owner = owner;
		this.encoding = encoding;
	}

	public abstract String getSystemEncoding();

	public abstract Class<? extends DatabaseUser> getSqlUserClass();

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Pac getPac() {
		return pac;
	}

	public void setPac(Pac pac) {
		this.pac = pac;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
