package ru.annikonenkov.enity.pet;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ru.annikonenkov.enity.link.PersonPetLink;

@Entity
@Table(name = "spring_pet")
public class Pet {

    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "pets_name")
    private String petsName;

    @Column(name = "pets_old")
    private int petsOld;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private Set<PersonPetLink> personLinks = new HashSet<>();

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

    public Set<PersonPetLink> getLink() {
        return this.personLinks;
    }

    public void setLink(PersonPetLink personPetLink) {
        personPetLink.setPet(this);
    }

    public void setLinks(Set<PersonPetLink> personPetLink) {
        personPetLink.forEach(currentItem -> currentItem.setPet(this));
    }
    
    @Override
    public String toString() {
        return String.format("Pet{id = %d, petName = %s, petsOld = %d}", id, petsName, petsOld);
    }

}
