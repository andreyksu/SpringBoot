package ru.annikonenkov.enity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pets")
public class Pet {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "pets_name")
	private String petsName;

	@Column(name = "pets_old")
	private int petsOld;

	@Column(name = "person_id")
	private int personId;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.petsName = name;
	}

	public String getName() {
		return this.petsName;
	}

	public void setOld(int old) {
		this.petsOld = old;
	}

	public int getOld() {
		return this.petsOld;
	}

	public void setPerson(int personId) {
		this.personId = personId;
	}

	public int getPerson() {
		return this.personId;
	}

}
