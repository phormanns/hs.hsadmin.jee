package de.hsadmin.service.pac;

import java.util.Date;
import java.util.List;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractVO;
import de.hsadmin.module.property.ElementsType;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.Required;
import de.hsadmin.module.property.Search;
import de.hsadmin.module.property.SearchPolicy;
import de.hsadmin.module.property.mapping.DefaultStringParameterMapMapper;
import de.hsadmin.module.property.mapping.Mapping;
import de.hsadmin.module.property.mapping.ReferredStringPersistentObjectMapper;

public class PacVO extends AbstractVO implements ValueObject {

	@Required(true)
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Search(SearchPolicy.LIKE)
	private String name;
	
	@Required(true)
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.EQUALS)
	@Mapping(
			boMapping=ReferredStringPersistentObjectMapper.class, 
			rpcMapping=DefaultStringParameterMapMapper.class, 
			boMappingPath="customer.name")
	private String customer;

	@Required(true)
	@ReadWrite(ReadWritePolicy.READWRITE)
	@Search(SearchPolicy.EQUALS)
	@Mapping(
			boMapping=ReferredStringPersistentObjectMapper.class, 
			rpcMapping=DefaultStringParameterMapMapper.class, 
			boMappingPath="basePac.name")
	private String basePac;
	
	@Required(true)
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Search(SearchPolicy.EQUALS)
	@Mapping(
			boMapping=ReferredStringPersistentObjectMapper.class, 
			rpcMapping=DefaultStringParameterMapMapper.class, 
			boMappingPath="hive.name")
	private String hive;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private Date created;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private Date cancelled;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	private Boolean free;

	@Required(true)
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Mapping(
			boMapping=ReferredStringPersistentObjectMapper.class, 
			rpcMapping=DefaultStringParameterMapMapper.class, 
			boMappingPath="curINetAddr.inetAddr")
	private String inetAddress;

	@ReadWrite(ReadWritePolicy.READWRITE)
	@ElementsType(PacComponentVO.class)
	private List<PacComponentVO> pacComponents;

	public PacVO() throws TechnicalException {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(final String customer) {
		this.customer = customer;
	}

	public String getBasePac() {
		return basePac;
	}

	public void setBasePac(final String basePac) {
		this.basePac = basePac;
	}

	public String getHive() {
		return hive;
	}

	public void setHive(final String hive) {
		this.hive = hive;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(final Date created) {
		this.created = created;
	}

	public Date getCancelled() {
		return cancelled;
	}

	public void setCancelled(final Date cancelled) {
		this.cancelled = cancelled;
	}

	public Boolean getFree() {
		return free;
	}

	public void setFree(final Boolean free) {
		this.free = free;
	}

	public String getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(final String inetAddress) {
		this.inetAddress = inetAddress;
	}

	public List<PacComponentVO> getPacComponents() {
		return pacComponents;
	}

	public void setPacComponents(List<PacComponentVO> pacComponents) {
		this.pacComponents = pacComponents;
	}
}
