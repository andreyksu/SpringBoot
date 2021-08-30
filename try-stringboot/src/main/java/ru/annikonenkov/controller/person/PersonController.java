package ru.annikonenkov.controller.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.annikonenkov.enity.Person;

//@Controller //@ResponseBody use with
@RestController
@RequestMapping("/api")
public class PersonController {

	@Autowired
	private ru.annikonenkov.service.PersonService personService;

	@GetMapping("/getAllAsModel")
	public String getAllAsModel(Model model) {
		List<Person> personList = personService.getAll();
		model.addAttribute("personList", personList);
		model.addAttribute("personSize", personList.size());
		return "index";// нужно добавить index.html
	}

	@GetMapping(path = "/getAllAsList", produces = MediaType.TEXT_PLAIN_VALUE)
	// @GetMapping("/getAllAsList")
	public String getAllAsModel() {
		List<Person> personList = personService.getAll();
		String result = "";
		for (Person p : personList) {
			result += "\n" + p.toString();
		}
		return result;
	}

	@RequestMapping("/delete/{id}")
	public String deletePerson(@PathVariable int id) {
		personService.delete(id);
		return "redirect:/";
	}

	@PostMapping("/add")
	public String addPerson(@ModelAttribute Person person) {
		personService.save(person);
		return "redirect:/";
	}

}
