package de.hsadmin.bo.pac;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@javax.persistence.Entity(name = "EMailAliases")
@Table(name = "emailalias")
@SequenceGenerator(name = "EMailAliasesSeqGen", sequenceName = "emailalias_emailalias_id_seq")
public class EMailAlias implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "EMailAliasesSeqGen")
	@Column(name = "emailalias_id", columnDefinition = "integer", insertable=false, updatable=false)
	private long id;

	@ManyToOne()
	@JoinColumn(name = "pac_id", columnDefinition = "integer")
	private Pac pac;

	@Column(updatable=false)
	private String name;

	@Column
	private String target;

	public EMailAlias() {
	}

	public EMailAlias(Pac pac, String name, String target) {
		this.pac = pac;
		this.name = name;
		this.target = target;
	}

	public static String createQueryFromStringKey(String humanKey) {
		return "obj.name='" + humanKey + "'";
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public Pac getPac() {
		return pac;
	}

	public void setPac(Pac pac) {
		this.pac = pac;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
