package ru.annikonenkov.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.Person;

@Service
public class PersonService {

	@Autowired
	private ru.annikonenkov.repository.PersonRepository personRepository;

	public List<Person> getAll() {
		return personRepository.findAll(Sort.by(Sort.Order.asc("personOld"), Sort.Order.desc("personName")));
	}

	public Person save(Person person) {
		return personRepository.save(person);
	}

	public void delete(int id) {
		personRepository.deleteById(id);
	}

}
