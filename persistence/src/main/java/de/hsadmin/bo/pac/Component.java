package de.hsadmin.bo.pac;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Component")
@Table(name = "component")
@SequenceGenerator(name = "CompSeqGen", sequenceName = "component_id_seq")
public class Component implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "CompSeqGen")
	@Column(name = "component_id", columnDefinition = "integer")
	private long componentId;
	
	@ManyToOne
	@JoinColumn(name="basecomponent_id")
	private BaseComponent baseComponent;
	
	@ManyToOne
	@JoinColumn(name="basepacket_id")
	private BasePac basePacket;
	
	@Column(name = "article_number", columnDefinition = "integer", nullable=false)
	private int articleNumber;

	@Column(name = "min_quantity", columnDefinition = "integer")
	private int minimumQuantity;

	@Column(name = "max_quantity", columnDefinition = "integer")
	private int maximimumQuantity;

	@Column(name = "default_quantity", columnDefinition = "integer")
	private int defaultQuantity;

	@Column(name = "increment_quantity", columnDefinition = "integer")
	private int incrementQuantity;

	@Column(name = "include_quantity", columnDefinition = "integer")
	private int includedQuantity;

	@Column(name = "admin_only", columnDefinition = "boolean")
	private boolean adminOnly;

	public BaseComponent getBaseComponent() {
		return baseComponent;
	}

	public void setBaseComponent(final BaseComponent baseComponent) {
		this.baseComponent = baseComponent;
	}

	public int getMinimumQuantity() {
		return minimumQuantity;
	}

	public void setMinimumQuantity(final int minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	public int getMaximimumQuantity() {
		return maximimumQuantity;
	}

	public void setMaximimumQuantity(final int maximimumQuantity) {
		this.maximimumQuantity = maximimumQuantity;
	}

	public int getDefaultQuantity() {
		return defaultQuantity;
	}

	public void setDefaultQuantity(final int defiautoQuantity) {
		this.defaultQuantity = defiautoQuantity;
	}

	public int getIncrementQuantity() {
		return incrementQuantity;
	}

	public void setIncrementQuantity(final int incrementQuantity) {
		this.incrementQuantity = incrementQuantity;
	}

	public int getIncludedQuantity() {
		return includedQuantity;
	}

	public void setIncludedQuantity(final int includedQuantity) {
		this.includedQuantity = includedQuantity;
	}

	public boolean isAdminOnly() {
		return adminOnly;
	}

	public void setAdminOnly(final boolean adminOnly) {
		this.adminOnly = adminOnly;
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(final int articleNumber) {
		this.articleNumber = articleNumber;
	}

	public long getComponentId() {
		return componentId;
	}

	public void setComponentId(final long componentId) {
		this.componentId = componentId;
	}

	public BasePac getBasePacket() {
		return basePacket;
	}

	public void setBasePacket(final BasePac basePacket) {
		this.basePacket = basePacket;
	}

}
