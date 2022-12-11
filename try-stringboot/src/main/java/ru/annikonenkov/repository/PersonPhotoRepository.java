package ru.annikonenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.annikonenkov.enity.photo.PersonPhoto;

@Repository
public interface PersonPhotoRepository extends JpaRepository<PersonPhoto, Integer> {

}
