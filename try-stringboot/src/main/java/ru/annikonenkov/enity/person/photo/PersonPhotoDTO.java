package ru.annikonenkov.enity.person.photo;

public class PersonPhotoDTO {

    private int id;

    private String photoName;

    private long size;

    private String mime;

    public PersonPhotoDTO(int id, String photoName, long size, String mime) {
        this.id = id;
        this.photoName = photoName;
        this.size = size;
        this.mime = mime;
    }

    public int getId() {
        return this.id;
    }

    public String getPhotoName() {
        return this.photoName;
    }

    public long getSize() {
        return this.size;
    }

    public String getMime() {
        return this.mime;
    }

}
