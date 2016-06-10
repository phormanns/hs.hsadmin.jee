package de.hsadmin.bo.pac;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "BaseComponent")
@Table(name = "basecomponent")
@SequenceGenerator(name = "BaseComponentsSeqGen", sequenceName = "basecomponent_basecomponent_seq")
public class BaseComponent implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "BaseComponentsSeqGen")
	@Column(name = "basecomponent_id", columnDefinition = "integer")
	private long baseComponentId;

	@Column(name = "basecomponent_code", columnDefinition = "character varying(10)")
	private String feature;

	@Column(name = "description", columnDefinition = "character varying(100)")
	private String description;

	@Column(name = "sorting", columnDefinition = "integer")
	private int sorting;

	@Column(name = "valid", columnDefinition = "boolean")
	private boolean valid;

	public BaseComponent() {
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(final String code) {
		this.feature = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public int getSorting() {
		return sorting;
	}

	public void setSorting(final int sorting) {
		this.sorting = sorting;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(final boolean valid) {
		this.valid = valid;
	}

	public void setBaseComponentId(final long baseComponentId) {
		this.baseComponentId = baseComponentId;
	}

	public long getBaseComponentId() {
		return baseComponentId;
	}
}
