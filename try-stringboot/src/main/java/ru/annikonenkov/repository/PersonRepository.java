package ru.annikonenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.annikonenkov.enity.person.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("Select p From Person p JOIN FETCH p.petsLinks pl JOIN FETCH pl.pet WHERE p.id = :id")
    public Person getByIdPerson(@Param("id") Integer id);
}
