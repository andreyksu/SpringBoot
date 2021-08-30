package ru.annikonenkov.enity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

	@GeneratedValue
	@Id
	@Column(name = "id")
	private int id;

	// @Column(nullable = false, unique = true)
	@Column(name = "person_name")
	private String personName;

	// @Column(nullable = false)
	@Column(name = "person_old")
	private int personOld;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.personName = name;
	}

	public String getName() {
		return this.personName;
	}

	public void setOld(int old) {
		this.personOld = old;
	}

	public int getOld() {
		return this.personOld;
	}

	@Override
	public String toString() {
		return String.format("Person{id = %d,personName = %s, personOld = %d}", id, personName, personOld);
	}

}