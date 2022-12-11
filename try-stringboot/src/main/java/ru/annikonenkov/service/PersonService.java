package ru.annikonenkov.service;

import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.person.Person;

// TODO: Это спринговский аналог DAO?

@Service
public class PersonService {

    @Autowired
    private ru.annikonenkov.repository.PersonRepository personRepository;

    // TODO: Так допустимо получать EntityManagerFactory а через него entityManagerFactory.createEntityManager() ???
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    // TODO: Так допустимо получать EntityManager в спринге?
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.d__D__HH:mm:ss:S");

    public List<Person> getAll() {
        return personRepository.findAll(Sort.by(Sort.Order.asc("personOld"), Sort.Order.desc("personName")));
    }

    public Person save(Person person) {
        log.info("save(...) {} ---- {}", dateFormat.format(new Date()), person.toString());
        return personRepository.save(person);
    }

    public void delete(int id) {
        log.info("delete(...) {} ---- {}", dateFormat.format(new Date()), id);
        personRepository.deleteById(id);
    }

    public Person getPersonById(Integer id) {
        Person person = personRepository.getByIdPerson(id);
        log.info("getPersonById(...) {} ---- {}", dateFormat.format(new Date()), person.toString());
        return person;
    }

    public void saveExistPerson(Person person) {
        log.info("saveExistPerson(...) {} ---- {}", dateFormat.format(new Date()), person.toString());
        entityManager.merge(person);
    }

}
