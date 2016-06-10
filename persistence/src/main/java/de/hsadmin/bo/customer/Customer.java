package de.hsadmin.bo.customer;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;

import de.hsadmin.bo.pac.Pac;


@Entity(name = "Customer")
@Table(name = "business_partner")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bp_id", columnDefinition = "integer")
	private long id;

	@Column(name = "member_id", columnDefinition = "integer")
	private int memberNo;

	@Column(name = "member_code", columnDefinition = "character varying(20)")
	private String name;
	
	@Column(name = "member_since", columnDefinition = "date", nullable = true)
	private Date memberSince;

	@Column(name = "member_until", columnDefinition = "date", nullable = true)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date memberUntil;

	@Column(name = "member_role", columnDefinition = "character varying(100)", nullable = true)
	private String memberRole;

	@Column(name = "author_contract", columnDefinition = "date", nullable = true)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date authorContract;

	@Column(name = "nondisc_contract", columnDefinition = "date", nullable = true)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date nonDiscContract;

	@Column(name = "shares_updated", columnDefinition = "date", nullable = true)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date sharesUpdated;

	@Column(name = "shares_signed", columnDefinition = "integer")
	private int sharesSigned;

	@Column(name = "free", nullable = false)
	private boolean free = false;

	@Column(name = "exempt_vat", nullable = false)
	private boolean exemptVAT = false;

	@Column(name = "indicator_vat", nullable = false)
	@Basic
    @Enumerated(EnumType.STRING)
	private IndicatorVAT indicatorVAT = IndicatorVAT.GROSS;
	
	@Column(name = "uid_vat", columnDefinition = "character varying(20)", nullable = true)
	private String uidVAT;

	@OneToMany(fetch = EAGER, cascade = ALL, mappedBy = "customer")
	private Set<Contact> contacts;

	@ElementCollection(fetch = EAGER)
	@CollectionTable(
		name="pricelist_ref",
		joinColumns=@JoinColumn(name="bp_id")
	)
	@Column(name="price_list")
	private List<String> priceLists;

	@OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "customer")
	@OrderBy("mandatSigned")
	private Set<SEPADirectDebit> sepaDirectDebits;

	@OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "customer")
	@OrderBy("name")
	private Set<Pac> pacs;

	public Customer() {
		contacts = new HashSet<>();
		pacs = new HashSet<>();
		priceLists = new ArrayList<>();
		sepaDirectDebits = new HashSet<>();
	}
	
	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(final int memberNo) {
		this.memberNo = memberNo;
	}

	public String getName() {
		return name;
	}

	public void setName(final String memberCode) {
		this.name = memberCode;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(final Date memberSince) {
		this.memberSince = memberSince;
	}

	public Date getMemberUntil() {
		return memberUntil;
	}

	public void setMemberUntil(final Date memberUntil) {
		this.memberUntil = memberUntil;
	}

	public String getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(final String memberRole) {
		this.memberRole = memberRole;
	}

	public Date getAuthorContract() {
		return authorContract;
	}

	public void setAuthorContract(final Date authorContract) {
		this.authorContract = authorContract;
	}

	public Date getNonDiscContract() {
		return nonDiscContract;
	}

	public void setNonDiscContract(final Date nonDiscContract) {
		this.nonDiscContract = nonDiscContract;
	}

	public Date getSharesUpdated() {
		return sharesUpdated;
	}

	public void setSharesUpdated(final Date sharesUpdated) {
		this.sharesUpdated = sharesUpdated;
	}

	public int getSharesSigned() {
		return sharesSigned;
	}

	public void setSharesSigned(final int sharesSigned) {
		this.sharesSigned = sharesSigned;
	}

	public String getUidVAT() {
		return uidVAT;
	}

	public void setUidVAT(final String uidVAT) {
		this.uidVAT = uidVAT;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(final Set<Contact> contacts) {
		if (contacts == null) {
			this.contacts.clear();
		} else {
			this.contacts = contacts;
		}
	}

	public Set<Pac> getPacs() {
		return pacs;
	}

	public void setPacs(final Set<Pac> pacs) {
		if (pacs == null) {
			this.pacs.clear();
		} else {
			this.pacs = pacs;
		}
	}

	public boolean isFree() {
		return free;
	}

	public boolean getFree() {
		return free;
	}

	public void setFree(final boolean free) {
		this.free = free;
	}

	public IndicatorVAT getIndicatorVAT() {
		return indicatorVAT;
	}

	public void setIndicatorVAT(final IndicatorVAT indicatorVAT) {
		this.indicatorVAT = indicatorVAT;
	}

	public boolean isExemptVAT() {
		return exemptVAT;
	}

	public boolean getExemptVAT() {
		return exemptVAT;
	}

	public void setExemptVAT(final boolean exemptVAT) {
		this.exemptVAT = exemptVAT;
	}

	public List<String> getPriceLists() {
		return priceLists;
	}

	public void setPriceLists(final List<String> priceLists) {
		if (priceLists == null) {
			this.priceLists.clear();
		} else	{
			this.priceLists = priceLists;
		}
	}

	public Set<SEPADirectDebit> getSepaDirectDebits() {
		return sepaDirectDebits;
	}

	public void setSepaDirectDebits(final Set<SEPADirectDebit> sepaDirectDebits) {
		this.sepaDirectDebits = sepaDirectDebits;
	}
	
}
