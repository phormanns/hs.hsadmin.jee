package de.hsadmin.bo.domain;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import de.hsadmin.bo.pac.UnixUser;
import de.hsadmin.common.config.Config;
import de.hsadmin.common.error.TechnicalException;

@Entity(name = "Domain")
@Table(name = "domain")
@SequenceGenerator(name = "DomainsSeqGen", sequenceName = "domain_domain_id_seq")
public class Domain {

	@Id
	@Column(name = "domain_id", columnDefinition = "integer")
	@GeneratedValue(strategy = SEQUENCE, generator = "DomainsSeqGen")
	private long id;

	@Column(name = "domain_name", columnDefinition = "character varying(256)", nullable = false)
	private String name;

	@JoinColumn(name = "domain_owner", columnDefinition = "integer", nullable = false)
	@ManyToOne(fetch = EAGER)
	private UnixUser user;

	@Column(name = "domain_since", columnDefinition = "date")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date since;

	@Column(name = "domain_dns_master", columnDefinition = "character varying(64)")
	private String dnsMaster;

	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinTable(name="domain__domain_option", 
		joinColumns={@JoinColumn(name="domain_id", referencedColumnName="domain_id")},
		inverseJoinColumns={@JoinColumn(name="domain_option_id", referencedColumnName="domain_option_id")})
	private Set<DomainOption> domainoptions;
	
	public Domain() {
	}

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

	public UnixUser getUser() {
		return user;
	}

	public void setUser(UnixUser user) {
		this.user = user;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public String getDnsMaster() {
		return dnsMaster;
	}

	public void setDnsMaster(String dnsMaster) {
		this.dnsMaster = dnsMaster;
	}

	public boolean isPacDomain() {
		if (getUser() != null && getUser().getPac() != null) {
			String pacDomName = getUser().getPac().getName();
			try {
				final String pacDomainPostfix = Config.getInstance().getProperty(Config.PACKET_DOMAINS_POSTFIX, "example.com");
				pacDomName = pacDomName + "." + pacDomainPostfix;
			} catch (TechnicalException e) {
			}
			return pacDomName.equals(getName());
		}
		return false;
	}

	public Set<DomainOption> getDomainoptions() {
		return domainoptions;
	}

	public void setDomainoptions(Set<DomainOption> domainOptions) {
		this.domainoptions = domainOptions;
	}

}
