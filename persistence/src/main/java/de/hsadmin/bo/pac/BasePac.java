package de.hsadmin.bo.pac;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "BasePac")
@Table(name = "basepacket")
@SequenceGenerator(name = "BasePacsSeqGen", sequenceName = "basepacket_basepacket_id_seq")
public class BasePac implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public BasePac() {
		components = new HashSet<Component>();
	}

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "BasePacsSeqGen")
	@Column(name = "basepacket_id", columnDefinition = "integer")
	private long basePacId;

	@Column(name = "basepacket_code", columnDefinition = "character varying(10)")
	private String name;

	@Column(name = "description", columnDefinition = "character varying(100)")
	private String description;

	@Column(name = "article_number", columnDefinition = "integer", nullable=false)
	private int articleNumber;

	@Column(name = "sorting", columnDefinition = "integer")
	private int sorting;

	@Column(name = "valid", columnDefinition = "boolean")
	private boolean valid;

	@OneToMany(fetch = FetchType.LAZY, cascade=ALL, mappedBy="basePacket")
	private Set<Component> components;

	public long getBasePacId() {
		return basePacId;
	}

	public void setBasePacId(final long id) {
		this.basePacId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(final boolean valid) {
		this.valid = valid;
	}

	public Set<Component> getComponents() {
		return components != null ? components
				: (components = new HashSet<Component>());
	}

	public void setComponents(final Set<Component> components) {
		this.components = components;
	}

	public Component getComponent(final String feature) {
		for (Component comp : getComponents())
			if (feature.equals(comp.getBaseComponent().getFeature()))
				return comp;
		return null;
	}

	public void addComponent(final Component comp) {
		getComponents().add(comp);
	}

	public void removeComponent(final Component comp) {
		getComponents().remove(comp);
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(final int articleNumber) {
		this.articleNumber = articleNumber;
	}
	
	@Override
	public String toString() {
		return "BasePac \"" + getName() + "\"";
	}
}
