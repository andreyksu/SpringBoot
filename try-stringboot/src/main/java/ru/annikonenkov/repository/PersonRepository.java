package ru.annikonenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import ru.annikonenkov.enity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}