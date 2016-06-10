package de.hsadmin.service.customer;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractVO;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.Required;
import de.hsadmin.module.property.Search;
import de.hsadmin.module.property.SearchPolicy;
import de.hsadmin.module.property.StringSet;
import de.hsadmin.module.property.mapping.DefaultStringParameterMapMapper;
import de.hsadmin.module.property.mapping.Mapping;
import de.hsadmin.module.property.mapping.ReferredStringPersistentObjectMapper;

public class ContactVO extends AbstractVO implements ValueObject {

	@Mapping(boMapping=ReferredStringPersistentObjectMapper.class, 
			rpcMapping=DefaultStringParameterMapMapper.class, 
			boMappingPath="customer.name")
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.EQUALS)
	private String customer;

	@Pattern(regexp="[A-Za-z]*")
	@Size(min=0,max=16)
	@ReadWrite(ReadWritePolicy.READWRITE)
	private String salut;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ ]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String firstName;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ ]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String lastName;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ ]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	private String title;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ \\.\\/\\&]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String firma;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ ]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	private String co;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ \\.\\/]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	private String street;

	@Pattern(regexp="\\d+")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String zipCode;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ \\(\\)\\/]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String city;

	@Pattern(regexp="[\\p{L}\\p{Nd}\\-\\ ]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String country;

	@Pattern(regexp="[\\+\\d\\-\\ \\(\\)\\/]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String phonePrivate;

	@Pattern(regexp="[\\+\\d\\-\\ \\(\\)\\/]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String phoneOffice;

	@Pattern(regexp="[\\+\\d\\-\\ \\(\\)\\/]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String phoneMobile;

	@Pattern(regexp="[\\+\\d\\-\\ \\(\\)\\/]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.LIKE)
	private String fax;

	@Pattern(regexp="[a-zA-Z0-9\\.\\-\\_]*@[a-z0-9äöüß\\.\\-]*")
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.EQUALS)
	@Required(true)
	private String email;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	private final StringSet roles;

	public ContactVO() throws TechnicalException {
		super();
		roles = new StringSet();
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSalut() {
		return salut;
	}

	public void setSalut(String salut) {
		this.salut = salut;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhonePrivate() {
		return phonePrivate;
	}

	public void setPhonePrivate(String phonePrivate) {
		this.phonePrivate = phonePrivate;
	}

	public String getPhoneOffice() {
		return phoneOffice;
	}

	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getRoles() {
		return roles.getStrings();
	}
	
	public void setRoles(String[] changedRoles) {
		roles.setStrings(changedRoles);
	}

}
