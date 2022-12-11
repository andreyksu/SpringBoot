package ru.annikonenkov.enity.link;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.annikonenkov.enity.person.Person;
import ru.annikonenkov.enity.pet.Pet;

@Entity
@Table(name = "spring_person_pet_link")
public class PersonPetLink {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "spring_additional_field")
    private String additionalField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_link_on_a_person"), nullable = false)
    private Person personOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_link_on_a_pet"), nullable = false)
    private Pet pet;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    
    public void setAdditionalField(String additionalField) {
        this.additionalField = additionalField;
    }

    public String getAdditionalField() {
        return this.additionalField;
    }
    
    
    public void setPerson(Person person) {
        person.getLink().add(this);
        this.personOwner = person;
    }

    public Person getPerson() {
        return this.personOwner;
    }    
    
    public void setPet(Pet pet) {
        pet.getLink().add(this);
        this.pet = pet;
    }

    public Pet getPet() {
        return this.pet;
    }
    
    

}
