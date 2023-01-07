package ru.annikonenkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.annikonenkov.enity.repast.Repast;

public interface RepastRepositoy extends JpaRepository<Repast, Integer> {

}
