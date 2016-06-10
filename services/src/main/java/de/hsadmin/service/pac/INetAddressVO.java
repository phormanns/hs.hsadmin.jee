package de.hsadmin.service.pac;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractVO;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.Required;
import de.hsadmin.module.property.Search;
import de.hsadmin.module.property.SearchPolicy;

public class INetAddressVO extends AbstractVO implements ValueObject {

	@Required(true)
	@ReadWrite(ReadWritePolicy.WRITEONCE)
	@Search(SearchPolicy.EQUALS)
	private String inetAddr;

	@ReadWrite(ReadWritePolicy.READWRITE)
	private String description;

	
	public INetAddressVO() throws TechnicalException {
		super();
	}

	public String getInetAddr() {
		return inetAddr;
	}

	public void setInetAddr(final String inetAddr) {
		this.inetAddr = inetAddr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
