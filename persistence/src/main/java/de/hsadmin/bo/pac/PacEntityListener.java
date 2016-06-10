package de.hsadmin.bo.pac;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class PacEntityListener {

	public PacEntityListener() {
		System.out.println("PacEntityListener()");
	}
	
	@PostLoad
	public void onLoad(Object pacObject) {
		System.out.println("onLoad");
		if (pacObject instanceof Pac) {
			Pac pac = (Pac) pacObject;
			System.out.println("Pac: " + pac.getName() + " free:" + pac.isFree());
		}
		System.out.println(" from: " + this.toString());
	}

	@PrePersist
	public void onCreate(Object pacObject) {
		System.out.println("onCreate");
		if (pacObject instanceof Pac) {
			Pac pac = (Pac) pacObject;
			System.out.println("Pac: " + pac.getName() + " free:" + pac.isFree());
		}
		System.out.println(" from: " + this.toString());
	}

	@PreUpdate
	public void onUpdate(Object pacObject) {
		System.out.println("onUpdate");
		if (pacObject instanceof Pac) {
			Pac pac = (Pac) pacObject;
			System.out.println("Pac: " + pac.getName() + " free:" + pac.isFree());
		}
		System.out.println(" from: " + this.toString());
	}

	@PreRemove
	public void onDelete(Object pacObject) {
		System.out.println("onDelete");
		if (pacObject instanceof Pac) {
			Pac pac = (Pac) pacObject;
			System.out.println("Pac: " + pac.getName() + " free:" + pac.isFree());
		}
		System.out.println(" from: " + this.toString());
	}

}
