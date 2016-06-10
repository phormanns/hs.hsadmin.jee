package de.hsadmin.bo.customer;

import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity(name = "SEPADirectDebit")
@Table(name = "sepa_mandat")
public class SEPADirectDebit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sepa_mandat_id", columnDefinition = "integer")
	private long id;

	@JoinColumn(name = "bp_id", columnDefinition = "integer")
	@OneToOne(fetch = EAGER)
	private Customer customer;

	@Column(name = "bank_customer", columnDefinition = "character varying(50)", nullable = false)
	private String bankCustomer;

	@Column(name = "bank_name", columnDefinition = "character varying(50)")
	private String bankName;

	@Column(name = "bank_iban", columnDefinition = "character varying(34)", nullable = false)
	private String bankIBAN;

	@Column(name = "bank_bic", columnDefinition = "character varying(11)", nullable = false)
	private String bankBIC;

	@Column(name = "mandat_ref", columnDefinition = "character varying(10)", nullable = false)
	private String mandatRef; 

	@Column(name = "mandat_signed", columnDefinition = "date", nullable = false)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date mandatSigned;

	@Column(name = "mandat_since", columnDefinition = "date", nullable = true)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date mandatSince;

	@Column(name = "mandat_until", columnDefinition = "date", nullable = true)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date mandatUntil;

	@Column(name = "mandat_used", columnDefinition = "date", nullable = true)
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date mandatUsed;

	public SEPADirectDebit() {
		this.customer = null;
	}
	
	public SEPADirectDebit(final Customer customer) {
		this.customer = customer;
	}
	
	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public String getBankCustomer() {
		return bankCustomer;
	}

	public void setBankCustomer(final String bankCustomer) {
		this.bankCustomer = bankCustomer;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	public String getBankIBAN() {
		return bankIBAN;
	}

	public void setBankIBAN(final String bankIBAN) {
		this.bankIBAN = bankIBAN;
	}

	public String getBankBIC() {
		return bankBIC;
	}

	public void setBankBIC(final String bankBIC) {
		this.bankBIC = bankBIC;
	}

	public String getMandatRef() {
		return mandatRef;
	}

	public void setMandatRef(String mandatRef) {
		this.mandatRef = mandatRef;
	}

	public Date getMandatSigned() {
		return mandatSigned;
	}

	public void setMandatSigned(Date mandatSigned) {
		this.mandatSigned = mandatSigned;
	}

	public Date getMandatSince() {
		return mandatSince;
	}

	public void setMandatSince(Date mandatSince) {
		this.mandatSince = mandatSince;
	}

	public Date getMandatUntil() {
		return mandatUntil;
	}

	public void setMandatUntil(Date mandatUntil) {
		this.mandatUntil = mandatUntil;
	}

	public Date getMandatUsed() {
		return mandatUsed;
	}

	public void setMandatUsed(Date mandatUsed) {
		this.mandatUsed = mandatUsed;
	}

}
