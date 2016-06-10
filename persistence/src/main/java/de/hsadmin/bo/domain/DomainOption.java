package de.hsadmin.bo.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name="domain_option")
@Entity(name="DomainOption")
@SequenceGenerator(name = "DomainOptionSeqGen", sequenceName = "domain_option_id_seq")
public class DomainOption {

	@Id
	@Column(name = "domain_option_id", columnDefinition = "integer")
	@GeneratedValue(strategy = SEQUENCE, generator = "DomainOptionSeqGen")
	private long id;

	@Column(name = "domain_option_name", columnDefinition = "character varying(256)", nullable = false)
	private String name;

	@ManyToMany(mappedBy="domainoptions", fetch=FetchType.LAZY)
	private List<Domain> domains;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DomainOption) {
			DomainOption opt = (DomainOption) obj;
			return getName().equals(opt.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

}
