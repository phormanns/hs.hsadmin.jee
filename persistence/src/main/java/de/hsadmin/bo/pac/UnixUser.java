package de.hsadmin.bo.pac;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity(name = "UnixUsers")
@Table(name = "unixuser")
@SequenceGenerator(name = "UnixUsersSeqGen", sequenceName = "unixuser_unixuser_id_seq")
public class UnixUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "UnixUsersSeqGen")
	@Column(name="unixuser_id", columnDefinition="integer", updatable=false, insertable=false)
	private long id;

	@Column(name="userid", columnDefinition="integer", nullable=false, updatable=false)
	private long userId;

	@Column(name="name", columnDefinition = "character varying(24)", unique=true, updatable=false)
	private String name;

	@Transient
	private String password;

	@JoinColumn(name = "packet_id", columnDefinition = "integer", updatable=false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Pac pac;

	@Column(name = "comment", columnDefinition = "character varying(128)")
	private String comment;

	@Column(name = "shell", columnDefinition = "character varying(32)")
	private String shell;

	@Column(name = "homedir", columnDefinition = "character varying(48)", updatable=false)
	private String homedir;

	@Column(name = "locked", columnDefinition = "boolean")
	private boolean locked;

	@Column(name = "quota_softlimit", columnDefinition = "integer")
	private Integer quotaSoftlimit;

	@Column(name = "quota_hardlimit", columnDefinition = "integer")
	private Integer quotaHardlimit;

	public UnixUser() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	} 
	public void setPassword(String password) {
		this.password = password;
	}

	public Pac getPac() {
		return pac;
	}

	public void setPac(Pac pac) {
		this.pac = pac;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getShell() {
		return shell;
	}

	public void setShell(String shell) {
		this.shell = shell;
	}

	public String getHomedir() {
		return homedir;
	}

	public void setHomedir(String homedir) {
		this.homedir = homedir;
	}

	public boolean isDefaultHomedir() {
		return getHomedir().equals(getDefaultHomedir());
	}

	private String getDefaultHomedir() {
		String pacName = pac.getName();
		if (name.equals(pacName)) {
			return "/home/pacs/" + pacName;
		}
		else {
			return "/home/pacs/" + pacName + "/users/" + name.substring(pacName.length() + 1);
		}
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Integer getQuotaSoftlimit() {
		return quotaSoftlimit;
	}

	public void setQuotaSoftlimit(Integer quota) {
		this.quotaSoftlimit = quota;
	}

	public void setQuotaHardlimit(Integer quotaLimit) {
		this.quotaHardlimit = quotaLimit;
	}

	public Integer getQuotaHardlimit() {
		return quotaHardlimit;
	}

}
