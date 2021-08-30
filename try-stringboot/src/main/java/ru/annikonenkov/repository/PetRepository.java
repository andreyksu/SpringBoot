package ru.annikonenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.annikonenkov.enity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
}
