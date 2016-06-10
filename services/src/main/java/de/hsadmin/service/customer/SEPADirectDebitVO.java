package de.hsadmin.service.customer;

import java.util.Date;

import javax.validation.constraints.Pattern;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractVO;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.Required;
import de.hsadmin.module.property.Search;
import de.hsadmin.module.property.SearchPolicy;
import de.hsadmin.module.property.mapping.DefaultStringParameterMapMapper;
import de.hsadmin.module.property.mapping.Mapping;
import de.hsadmin.module.property.mapping.ReferredStringPersistentObjectMapper;

public class SEPADirectDebitVO extends AbstractVO implements ValueObject {

	@Mapping(boMapping=ReferredStringPersistentObjectMapper.class, 
			rpcMapping=DefaultStringParameterMapMapper.class, 
			boMappingPath="customer.name")
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.EQUALS)
	private String customer;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ ]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Required(true)
	@Search(SearchPolicy.LIKE)
	private String bankCustomer;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ \\.\\/\\&]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Required(false)
	@Search(SearchPolicy.LIKE)
	private String bankName;

	@Pattern(regexp="[A-Z0-9]*")
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.LIKE)
	private String bankIBAN;

	@Pattern(regexp="[A-Z0-9]*")
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.LIKE)
	private String bankBIC;

	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.LIKE)
	private String mandatRef;

	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.COMPARE)
	private Date mandatSigned;
	
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.COMPARE)
	private Date mandatSince;

	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.COMPARE)
	private Date mandatUntil;

	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.COMPARE)
	private Date mandatUsed;
	
	public SEPADirectDebitVO() throws TechnicalException {
		super();
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getBankCustomer() {
		return bankCustomer;
	}

	public void setBankCustomer(String bankCustomer) {
		this.bankCustomer = bankCustomer;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankIBAN() {
		return bankIBAN;
	}

	public void setBankIBAN(String bankIBAN) {
		this.bankIBAN = bankIBAN;
	}

	public String getBankBIC() {
		return bankBIC;
	}

	public void setBankBIC(String bankBIC) {
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
