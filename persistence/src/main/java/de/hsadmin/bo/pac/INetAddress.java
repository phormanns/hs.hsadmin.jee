package de.hsadmin.bo.pac;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "INetAddress")
@Table(name = "inet_addr")
@SequenceGenerator(name = "INetAddressesSeqGen", sequenceName = "inet_addr_inet_addr_id_seq")
public class INetAddress implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "INetAddressesSeqGen")
	@Column(name = "inet_addr_id")
	private long id;

	@Column(name = "inet_addr", unique = true, length=-1)
	private String inetAddr;

	@Column(name = "description", columnDefinition = "character varying(100)")
	private String description;

	public INetAddress() {
	}

	public INetAddress(final String inetAddr) {
		this.inetAddr = inetAddr;
	}

	public INetAddress(final String inetAddr, final String desc) {
		this(inetAddr);
		description = desc;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
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
