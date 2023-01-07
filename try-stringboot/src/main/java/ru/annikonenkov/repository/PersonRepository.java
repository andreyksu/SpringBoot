package ru.annikonenkov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.annikonenkov.enity.person.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    // TODO: Эти выборки были добавлены для LAZY инициализации. Пока везде добавил EAGER

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.petsLinks pl LEFT JOIN FETCH pl.pet WHERE p.id = :id")
    public Person getPersonIdWithPets(@Param("id") Integer id);

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.personPhotos pP WHERE p.id = :id") // Так один селект.
    // @Query("SELECT p FROM Person p INNER JOIN p.personPhotos pP WHERE p.id = :id") //Так два селекта. Сначала по person потом для personPhoto. Как и следовало ожидать.
    public Person getPersonIdWithPhotos(@Param("id") Integer id);

    @Query("SELECT p FROM Person p WHERE p.personName LIKE %:maskOfName%")
    public List<Person> getPersonMatchedByName(@Param("maskOfName") String maskOfName);

}
