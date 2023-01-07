package ru.annikonenkov.service;

import java.util.List;
import java.util.Set;
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
import ru.annikonenkov.enity.person.photo.PersonPhoto;

// TODO: Это спринговский аналог DAO?

@Service
public class PersonService {

    @Autowired
    private ru.annikonenkov.repository.PersonRepository personRepository;

    // TODO: Так допустимо получать EntityManagerFactory а через него entityManagerFactory.createEntityManager() в Spring???
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    // TODO: Так допустимо получать EntityManager в Spring? Добавлял для merge - но оказалось что в spring - в метод save(...) уже обёрнут метод merge(...).
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd___HH:mm:ss:S");

    public Person savePerson(Person person) {
        Person savedPerson = personRepository.save(person);
        /*
         * Если посмотреть реализацию этого метода save(...) в классе SimpleJpaRepository<T, ID> - то увидимо, что данный метод аннотирован @Transactional. И более того если сущность новая - то вызовится persist а если уже существовала то merge. См.
         * реализацию метода.
         */
        log.debug("saveNewPerson(...) ---- The new Person was saved = {}", dateFormat.format(new Date()), savedPerson);
        return savedPerson;
    }
    /*
     * public Person saveExistPersonForTry(Person person) { Person managedPerson = entityManager.merge(person); // Работает только в рамках транзакции - собственно что и говорится в описании метода merge(...).
     * log.info("saveExistPerson(...) ---- Person that will be saved as exist = {}", managedPerson); return managedPerson; }
     */

    // TODO: Вот тут с удалением - есть вопросы, ибо от этой сущности зависят другие и удаление не будет каскадным т.к. аннотации не проставлены. Вероятно нужно убрать - либо добавить поле isDeleted.
    public void delete(int id) {
        log.debug("delete(...) ---- Id of Person for Delete = {}", id);
        personRepository.deleteById(id);
    }

    public List<Person> getAllPerson() {
        return personRepository.findAll(Sort.by(Sort.Order.asc("personOld"), Sort.Order.desc("personName")));
    }

    // Может быть null - по этой причине никаких обращейний к этой переменной без проверки на null. По этомй причине не использую toString()
    public Person getPersonById(Integer id) {
        Person person = personRepository.getById(id);
        log.debug("getPersonById(...) ---- Person that has got form DB by ID = {}", person);
        return person;
    }

    // Может быть null - по этой причине никаких обращейний к этой переменной без проверки на null. По этомй причине не использую toString()
    public Person getPersonByIdWithPets(Integer id) {
        Person person = personRepository.getPersonIdWithPets(id);
        log.debug("getPersonByIdWithPets(...) ---- Person that has got form DB by ID = {}", person);
        return person;
    }

    // Может быть null - по этой причине никаких обращейний к этой переменной без проверки на null. По этомй причине не использую toString()
    public Person getPersonIdWithPhotos(Integer id) {
        Person person = personRepository.getPersonIdWithPhotos(id);
        log.debug("getPersonIdWithPhotos(...) ---- Person that has got form DB by ID = {}", person);
        Set<PersonPhoto> pp = person.getPersonPhotos();
        pp.forEach(item -> log.info("PersonPhoto = {}", item.toString()));
        return person;
    }

    public List<Person> getPersonMatchedByName(String maskOfName) {
        List<Person> persons = personRepository.getPersonMatchedByName(maskOfName);
        log.debug("getPersonMatchedByName(...) ---- Person that has got form DB by maskName = {}", persons);
        return persons;
    }
}
