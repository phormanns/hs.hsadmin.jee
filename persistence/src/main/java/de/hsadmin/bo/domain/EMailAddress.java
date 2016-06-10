package de.hsadmin.bo.domain;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity(name = "EMailAddress")
@Table(name = "emailaddr")
@SequenceGenerator(name = "EMailAddressesSeqGen", sequenceName = "emailaddr_emailaddr_id_seq")
public class EMailAddress implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "EMailAddressesSeqGen")
	@Column(name = "emailaddr_id", columnDefinition = "integer")
	private long id;
	
	@Column(name = "localpart", updatable = false, nullable= false)
	private String localpart = "";
	
	@Column(name = "subdomain")
	private String subdomain;
	
	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "domain_id", columnDefinition = "integer", updatable = false)
	private Domain domain;
	
	@Column(name = "target", nullable= false)
	private String target;

	public EMailAddress() {
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public String getLocalpart() {
		return localpart == null ? "" : localpart;
	}

	public void setLocalpart(String localpart) {
		this.localpart = localpart;
	}

	public String getSubdomain() {
		return subdomain == null || subdomain.length() == 0 ? null : subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String toString() {
		if (localpart != null && target != null && domain != null) {
			String local = super.toString() + "{ id=" + id + "; address=" + localpart + "@";
			if (subdomain != null) { local += "." + subdomain; }
			return local + domain + "; target=" + target + " }";
		} else {
			return super.toString();
		}
	}


}
