package de.hsadmin.bo.customer;

import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Contact")
@Table(name = "contact")
public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contact_id", columnDefinition = "integer")
	private long id;

	@JoinColumn(name = "bp_id", columnDefinition = "integer")
	@ManyToOne(fetch = EAGER)
	private Customer customer;

	@Column(name = "salut", columnDefinition = "character varying(30)")
	private String salut;

	@Column(name = "first_name", columnDefinition = "character varying(40)")
	private String firstName;

	@Column(name = "last_name", columnDefinition = "character varying(40)")
	private String lastName;

	@Column(name = "title", columnDefinition = "character varying(20)")
	private String title;

	@Column(name = "firma", columnDefinition = "character varying(120)")
	private String firma;

	@Column(name = "co", columnDefinition = "character varying(50)")
	private String co;

	@Column(name = "street", columnDefinition = "character varying(50)")
	private String street;

	@Column(name = "zipcode", columnDefinition = "character varying(10)")
	private String zipCode;

	@Column(name = "city", columnDefinition = "character varying(40)")
	private String city;

	@Column(name = "country", columnDefinition = "character varying(30)")
	private String country;

	@Column(name = "phone_private", columnDefinition = "character varying(30)")
	private String phonePrivate;

	@Column(name = "phone_office", columnDefinition = "character varying(30)")
	private String phoneOffice;

	@Column(name = "phone_mobile", columnDefinition = "character varying(30)")
	private String phoneMobile;

	@Column(name = "fax", columnDefinition = "character varying(30)")
	private String fax;

	@Column(name = "email", columnDefinition = "character varying(50)")
	private String email;
	
	@ElementCollection(fetch = EAGER)
	@CollectionTable(
		name="contactrole_ref",
		joinColumns=@JoinColumn(name="contact_id")
	)
	@Column(name="role")
	private List<String> roles;
	
	public Contact() {
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

	public String getSalut() {
		return salut;
	}

	public void setSalut(final String salut) {
		this.salut = salut;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(final String firma) {
		this.firma = firma;
	}

	public String getCo() {
		return co;
	}

	public void setCo(final String co) {
		this.co = co;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getPhonePrivate() {
		return phonePrivate;
	}

	public void setPhonePrivate(final String phonePrivate) {
		this.phonePrivate = phonePrivate;
	}

	public String getPhoneOffice() {
		return phoneOffice;
	}

	public void setPhoneOffice(final String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public void setPhoneMobile(final String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
