package de.hsadmin.bo.pac;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "PacComponents")
@Table(name = "packet_component")
@SequenceGenerator(name = "PacCompSeqGen", sequenceName = "packet_component_id_seq")
public class PacComponent implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "PacCompSeqGen")
	@Column(name = "packet_component_id")
	private long pacComponentId;
	
	@ManyToOne
	@JoinColumn(name = "packet_id")
	private Pac pac;
	
	@ManyToOne
	@JoinColumn(name = "basecomponent_id")
	private BaseComponent baseComponent;

	@Column(name = "quantity", columnDefinition = "integer")
	private int quantity;

	@Column(name = "created", columnDefinition = "date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date created;

	@Column(name = "cancelled", columnDefinition = "date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date cancelled;

	public BasePac getBasePac() {
		return pac.getBasePac();
	}

	public BaseComponent getBaseComponent() {
		return baseComponent;
	}

	public void setBaseComponent(final BaseComponent baseComponent) {
		this.baseComponent = baseComponent;
	}

	public Pac getPac() {
		return pac;
	}

	public void setPac(final Pac pac) {
		this.pac = pac;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCancelled() {
		return cancelled;
	}

	public void setCancelled(final Date cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public String toString() {
		return "pac=" + pac.getName() + ";comp=" + getBaseComponent().getFeature() + ";quantity=" + getQuantity();
	}

}
