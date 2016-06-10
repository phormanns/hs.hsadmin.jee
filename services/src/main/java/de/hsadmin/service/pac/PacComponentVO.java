package de.hsadmin.service.pac;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractVO;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.mapping.DefaultStringParameterMapMapper;
import de.hsadmin.module.property.mapping.Mapping;
import de.hsadmin.module.property.mapping.ReferredStringPersistentObjectMapper;

public class PacComponentVO extends AbstractVO implements ValueObject {

	@Mapping(
			boMapping=ReferredStringPersistentObjectMapper.class, 
			rpcMapping=DefaultStringParameterMapMapper.class, 
			boMappingPath="baseComponent.feature")
	@ReadWrite(ReadWritePolicy.READ)
	private String feature;
	
	@ReadWrite(ReadWritePolicy.READWRITE)
	private Integer quantity;
	
	public PacComponentVO() throws TechnicalException {
		super();
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(final String feature) {
		this.feature = feature;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

}
