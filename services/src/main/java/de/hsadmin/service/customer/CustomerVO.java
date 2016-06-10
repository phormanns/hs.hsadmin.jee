package de.hsadmin.service.customer;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.impl.AbstractVO;
import de.hsadmin.module.property.Display;
import de.hsadmin.module.property.DisplayPolicy;
import de.hsadmin.module.property.ElementsType;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.Required;
import de.hsadmin.module.property.Search;
import de.hsadmin.module.property.SearchPolicy;
import de.hsadmin.module.property.StringSet;

public class CustomerVO extends AbstractVO {

	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.LIKE)
	@Pattern(regexp="hsh00\\-[a-z0-9]{3}")
	@Size(min=9,max=9)
	private String name;
	
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Required(true)
	@Search(SearchPolicy.EQUALS)
	private Integer memberNo;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private String memberRole;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.COMPARE)
	private Date memberSince;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.COMPARE)
	private Date memberUntil;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	private Date authorContract;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private Date nonDiscContract;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private Date sharesUpdated;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private Integer sharesSigned;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private Boolean free;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private Boolean exemptVAT;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private String indicatorVAT;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	private String uidVAT;
	
	@ElementsType(ContactVO.class)
	@ReadWrite(ReadWritePolicy.READ)
	@Display(visible=DisplayPolicy.OPTIONAL)
	private List<ContactVO> contacts;
	
	@ElementsType(SEPADirectDebitVO.class)
	@ReadWrite(ReadWritePolicy.READ)
	@Display(visible=DisplayPolicy.NEVER)
	private List<SEPADirectDebitVO> sepaDirectDebits;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	private final StringSet priceLists;
	
	public CustomerVO() throws TechnicalException {
		super();
		priceLists = new StringSet();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public Date getMemberUntil() {
		return memberUntil;
	}

	public void setMemberUntil(Date memberUntil) {
		this.memberUntil = memberUntil;
	}

	public Date getAuthorContract() {
		return authorContract;
	}

	public void setAuthorContract(Date authorContract) {
		this.authorContract = authorContract;
	}

	public Date getNonDiscContract() {
		return nonDiscContract;
	}

	public void setNonDiscContract(Date nonDiscContract) {
		this.nonDiscContract = nonDiscContract;
	}

	public Date getSharesUpdated() {
		return sharesUpdated;
	}

	public void setSharesUpdated(Date sharesUpdated) {
		this.sharesUpdated = sharesUpdated;
	}

	public Integer getSharesSigned() {
		return sharesSigned;
	}

	public void setSharesSigned(Integer sharesSigned) {
		this.sharesSigned = sharesSigned;
	}

	public Boolean getFree() {
		return free;
	}

	public void setFree(Boolean free) {
		this.free = free;
	}

	public Boolean getExemptVAT() {
		return exemptVAT;
	}

	public void setExemptVAT(Boolean exemptVAT) {
		this.exemptVAT = exemptVAT;
	}

	public String getIndicatorVAT() {
		return indicatorVAT;
	}

	public void setIndicatorVAT(String indicatorVAT) {
		this.indicatorVAT = indicatorVAT;
	}

	public String getUidVAT() {
		return uidVAT;
	}

	public void setUidVAT(String uidVAT) {
		this.uidVAT = uidVAT;
	}

	public List<ContactVO> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactVO> contacts) {
		this.contacts = contacts;
	}

	public String[] getPriceLists() {
		return priceLists.getStrings();
	}

	public void setPriceLists(String[] priceLists) {
		this.priceLists.setStrings(priceLists);
	}

	public List<SEPADirectDebitVO> getSepaDirectDebits() {
		return sepaDirectDebits;
	}

	public void setSepaDirectDebits(List<SEPADirectDebitVO> sepaDirectDebits) {
		this.sepaDirectDebits = sepaDirectDebits;
	}

}
