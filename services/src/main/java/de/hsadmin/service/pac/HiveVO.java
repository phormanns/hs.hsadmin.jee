package de.hsadmin.service.pac;

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

public class HiveVO extends AbstractVO implements ValueObject {

	@Required(true)
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Search(SearchPolicy.EQUALS)
	private String name;

	@Required(true)
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Mapping(
			boMapping=ReferredStringPersistentObjectMapper.class, 
			rpcMapping=DefaultStringParameterMapMapper.class, 
			boMappingPath="inetAddr.inetAddr")
	private String inetAddress;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	private String description;

	public HiveVO() throws TechnicalException {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(final String inetAddress) {
		this.inetAddress = inetAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
