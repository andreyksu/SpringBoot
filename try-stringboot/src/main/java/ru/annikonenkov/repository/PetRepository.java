package ru.annikonenkov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.annikonenkov.enity.pet.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    // TODO: Эти выборки были добавлены для LAZY инициализации. Пока везде добавил EAGER

    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.personLinks pl LEFT JOIN FETCH pl.personOwner WHERE p.id = :id")
    public Pet getPetWithPerson(@Param("id") Integer id);

    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.personLinks pl LEFT JOIN FETCH pl.personOwner LEFT JOIN FETCH p.repast WHERE p.id = :id")
    public Pet getPetWithRepastAndPerson(@Param("id") Integer id);

    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.repast WHERE p.id = :id")
    public Pet getPetWithRepast(@Param("id") Integer id);

    @Query("SELECT p FROM Pet p WHERE p.petsName LIKE %:maskOfName%")
    public List<Pet> getPersonMatchedByName(@Param("maskOfName") String maskOfName);
}
