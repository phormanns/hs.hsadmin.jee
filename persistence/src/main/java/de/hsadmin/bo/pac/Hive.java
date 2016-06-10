package de.hsadmin.bo.pac;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Hive")
@Table(name = "hive")
@SequenceGenerator(name = "HivesSeqGen", sequenceName = "hive_hive_id_seq")
public class Hive implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "HivesSeqGen")
	@Column(name = "hive_id")
	private long id;

	@Column(name = "hive_name", columnDefinition = "character varying(3)", unique = true)
	private String name;

	@JoinColumn(name = "inet_addr_id")
	@ManyToOne(fetch = EAGER)
	private INetAddress inetAddr;

	@Column(name = "description", columnDefinition = "character varying(100)")
	private String description;

	@OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "hive")
	@OrderBy("name")
	private Set<Pac> pacs;

	public Hive() {
	}

	public Hive(final INetAddress inetAddr) {
		this.inetAddr = inetAddr;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String hiveName) {
		this.name = hiveName;
	}

	public INetAddress getInetAddr() {
		return inetAddr;
	}

	public void setInetAddr(final INetAddress inetAddr) {
		this.inetAddr = inetAddr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Set<Pac> getPacs() {
		return pacs;
	}

	public void setPacs(final Set<Pac> pacs) {
		this.pacs = pacs;
	}

}
