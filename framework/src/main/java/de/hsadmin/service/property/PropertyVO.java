package de.hsadmin.service.property;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractVO;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.Search;
import de.hsadmin.module.property.SearchPolicy;
import de.hsadmin.module.property.Display;

public class PropertyVO extends AbstractVO implements ValueObject {

	@Display(sequence = 10)
	@Search(SearchPolicy.EQUALS)
	@ReadWrite(ReadWritePolicy.READ)
	private String module;

	@Display(sequence = 20)
	@ReadWrite(ReadWritePolicy.READ)
	private String name;

	@Display(sequence = 50)
	@ReadWrite(ReadWritePolicy.READ)
	private String searchable;

	@Display(sequence = 40)
	@ReadWrite(ReadWritePolicy.READ)
	private String readwriteable;

	@Display(sequence = 30)
	@ReadWrite(ReadWritePolicy.READ)
	private String type;
	
	@Display(sequence = 60)
	@ReadWrite(ReadWritePolicy.READ)
	private Integer displaySequence;

	@Display(sequence = 70)
	@ReadWrite(ReadWritePolicy.READ)
	private String displayVisible;

	@Display(sequence = 80)
	@ReadWrite(ReadWritePolicy.READ)
	private Integer minLength;

	@Display(sequence = 81)
	@ReadWrite(ReadWritePolicy.READ)
	private Integer maxLength;

	@Display(sequence = 90)
	@ReadWrite(ReadWritePolicy.READ)
	private String validationRegexp;

	public PropertyVO() throws TechnicalException {
		super();
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchable() {
		return searchable;
	}

	public void setSearchable(String searchable) {
		this.searchable = searchable;
	}

	public String getReadwriteable() {
		return readwriteable;
	}

	public void setReadwriteable(String readwriteable) {
		this.readwriteable = readwriteable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer sequence) {
		this.displaySequence = sequence;
	}

	public String getDisplayVisible() {
		return displayVisible;
	}

	public void setDisplayVisible(String displayVisible) {
		this.displayVisible = displayVisible;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public String getValidationRegexp() {
		return validationRegexp;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public void setValidationRegexp(String validationRegexp) {
		this.validationRegexp = validationRegexp;
	}
	
}
