package de.hsadmin.bo.pac;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.hsadmin.bo.customer.Customer;

@Entity(name = "Pac")
@Table(name = "packet")
@SequenceGenerator(name = "PacsSeqGen", sequenceName = "packet_packet_id_seq")
@EntityListeners({PacEntityListener.class})
public class Pac implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final int UNDEFINED_QUANTITY = -1;
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "PacsSeqGen")
	@Column(name = "packet_id", columnDefinition = "integer")
	private long id;

	@Column(name = "packet_name", unique = true)
	private String name;

	@JoinColumn(name = "bp_id")
	@ManyToOne(fetch = LAZY)
	private Customer customer;

	@JoinColumn(name = "basepacket_id")
	@ManyToOne(fetch = EAGER)
	private BasePac basePac;

	@JoinColumn(name = "hive_id")
	@ManyToOne(fetch = EAGER)
	private Hive hive;

	@Column(name = "created")
	@Temporal(TemporalType.DATE)
	private Date created;

	@Column(name = "cancelled", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date cancelled;

	@Column(name = "free", nullable = false)
	private Boolean free = false;
	
	@JoinColumn(name = "cur_inet_addr_id", nullable = true)
	@ManyToOne(fetch = EAGER)
	private INetAddress curINetAddr;

	@JoinColumn(name = "old_inet_addr_id", nullable = true)
	@ManyToOne(fetch = EAGER)
	private INetAddress oldINetAddr;

	@OneToMany(fetch = EAGER, cascade = ALL, mappedBy="pac")
	private Set<PacComponent> pacComponents;

	@OneToMany(fetch = LAZY, cascade = ALL, mappedBy="pac")
	private Set<UnixUser> unixUser;

	public void initPacComponents(final EntityManager em, final BasePac aBasepac, final boolean setDefaults) {
		final Query qAttachedBasepac = em.createQuery("SELECT b FROM BasePac b WHERE b.valid = :valid AND b.name = :name");
		qAttachedBasepac.setParameter("valid", Boolean.TRUE);
		qAttachedBasepac.setParameter("name", aBasepac.getName());
		basePac = (BasePac) qAttachedBasepac.getSingleResult();
		pacComponents = new HashSet<PacComponent>();
		final Date today = new Date();
		for (Component comp : basePac.getComponents()) {
			final PacComponent pacComp = new PacComponent();
			pacComp.setCreated(today);
			pacComp.setBaseComponent(comp.getBaseComponent());
			pacComp.setPac(this);
			if (setDefaults) {
				pacComp.setQuantity(comp.getDefaultQuantity());
			} else {
				pacComp.setQuantity(UNDEFINED_QUANTITY);
			}
			pacComponents.add(pacComp);
		}
	}

	public long getId() {
		return id;
	}

	protected void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Hive getHive() {
		return hive;
	}

	public void setHive(final Hive hive) {
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

	public INetAddress getCurINetAddr() {
		return curINetAddr;
	}

	public void setCurINetAddr(final INetAddress curINetAddr) {
		this.curINetAddr = curINetAddr;
	}

	public INetAddress getOldINetAddr() {
		if (oldINetAddr == null) {
			return getCurINetAddr();
		}
		return oldINetAddr;
	}

	public void setOldINetAddr(final INetAddress oldINetAddr) {
		this.oldINetAddr = oldINetAddr;
	}

	public BasePac getBasePac() {
		return basePac;
	}

	public void setBasePac(final BasePac basepac) {
		this.basePac = basepac;
	}

	public Set<PacComponent> getPacComponents() {
		return pacComponents;
	}

	public void setPacComponents(final Set<PacComponent> pacComponents) {
		this.pacComponents = pacComponents;
	}

	public PacComponent getPacComponent(final String feature) {
		if (pacComponents != null) {
			for (PacComponent pc : pacComponents) {
				if (feature.equals(pc.getBaseComponent().getFeature())) {
					return pc;
				}
			}
		}
		return null;
	}

	public Set<UnixUser> getUnixUser() {
		return unixUser;
	}

	public void setUnixUser(Set<UnixUser> unixUser) {
		this.unixUser = unixUser;
	}

	public String toString() {
		return super.toString() + "{ name=" + name + " }";
	}

	public Boolean isFree() {
		return free;
	}

	public Boolean getFree() {
		return free;
	}

	public void setFree(final Boolean free) {
		if (free == null) {
			this.free = Boolean.FALSE;
		} else {
			this.free = free;
		}
	}

	public int getQuantityByComponentName(final String componentName) {
		PacComponent multi = getPacComponent(componentName);
		if (multi == null) {
			return 0;
		} else { 
			return multi.getQuantity();
		}
	}
	
}
