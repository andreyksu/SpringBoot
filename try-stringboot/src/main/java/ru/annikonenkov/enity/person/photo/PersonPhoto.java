package ru.annikonenkov.enity.person.photo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ru.annikonenkov.enity.person.Person;

@Entity
@Table(name = "spring_person_photo")
public class PersonPhoto {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "photo_size")
    private Long size;

    @Lob
    @Column(name = "photo")
    // @Basic(fetch = FetchType.EAGER) //Если без @Transactional то FetchType.EAGER тоже не помогает.
    @Basic(fetch = FetchType.LAZY)
    private byte[] fileOfPhoto;

    @Column(name = "mime")
    private String mime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_photo_on_a_person"), nullable = false)
    @JsonIgnore
    private Person person;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setPhotoName(String name) {
        this.photoName = name;
    }

    public String getPhotoName() {
        return this.photoName;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getSize() {
        return this.size;
    }

    public void setFileOfPhoto(byte[] byteArray) {
        this.fileOfPhoto = byteArray;
    }

    @JsonIgnore
    public byte[] getFileOfPhoto() {
        return this.fileOfPhoto;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getMime() {
        return this.mime;
    }

    public void setPerson(Person person) {
        person.getPersonPhotos().add(this);
        this.person = person;
    }

    @JsonIgnore
    public Person getPerson() {
        return this.person;
    }

    @Override
    public String toString() {
        return String.format("Photo::: {id = %d, photoName = %s, mime = %s, size = %d}", id, photoName, mime, size);
    }

}
