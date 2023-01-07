package ru.annikonenkov.enity.person;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.annikonenkov.enity.food.Food;
import ru.annikonenkov.enity.link.PersonPetLink;
import ru.annikonenkov.enity.person.photo.PersonPhoto;
import ru.annikonenkov.enity.pet.Pet;

/**
 * Является Человеком - владельцем питомца. Располагает ссылками на свои фотографии, и ссылкой на ТАБЛИЦУ связи с питомцем.
 */
@Entity
@Table(name = "spring_person")
public class Person {

    // @GeneratedValue(strategy = GenerationType.AUTO)
    // TODO: Интересно, что данная последовательность В КАЖДОЙ СУЩНОСТИ берёт/резервирует себе по 50 - для постоянно-запущенного приложения это норм, но для отладки - слишком быстро расходуется последовательность.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Column(name = "id")
    private int id;

    // @Column(nullable = false, unique = true)
    @Column(name = "person_name")
    private String personName;

    // @Column(nullable = false)
    @Column(name = "person_old")
    private int personOld;

    @JsonIgnore
    // @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<PersonPhoto> personPhotos = new HashSet<>();

    /**
     * Можно было бы сделать с питомцем связь ManyToMany - но решил попробовать через третью таблицу (и в этой третей таблице можно добавить свои дополнительные поля)-допустим срок владения, место покупки питомца.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "personOwner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<PersonPetLink> petsLinks = new HashSet<>();

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

    @JsonIgnore // Позволяет не сереализовать в JSON (пропускать при сереализации)
    public Set<PersonPhoto> getPersonPhotos() {
        return this.personPhotos;
    }

    public void setPersonPhoto(PersonPhoto personPhoto) {
        personPhoto.setPerson(this);
        // personPhotos.add(personPhoto); это не нужно, т.к. делается добавление в коллекцию в методе setPerson(...)
    }

    public void setPersonPhotos(Set<PersonPhoto> personPhotos) {
        personPhotos.forEach(currnetItem -> currnetItem.setPerson(this));
        // personPhotos.addAll(personPhotos); это не нужно, т.к. делается добавление в коллекцию в методе setPerson(...)
    }

    @JsonIgnore // Позволяет не сереализовать в JSON (пропускать при сереализации)
    public Set<PersonPetLink> getPetsLinks() {
        return this.petsLinks;
    }

    public void setPetsLink(PersonPetLink petLink) {
        petLink.setPerson(this);
    }

    public void setPetsLinks(Set<PersonPetLink> petLinks) {
        petLinks.forEach(currentItem -> currentItem.setPerson(this));
    }

    public void getEatToPet(Food eat, Pet pet) {
        // TODO: Что здесь должно быть?
    }

    @Override
    public String toString() {
        return String.format("Person::: {id = %d, personName = %s, personOld = %d}", id, personName, personOld);
    }

}
