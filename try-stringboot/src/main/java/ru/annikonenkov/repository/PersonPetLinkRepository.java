package ru.annikonenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.annikonenkov.enity.link.PersonPetLink;

public interface PersonPetLinkRepository extends JpaRepository<PersonPetLink, Integer> {

}
