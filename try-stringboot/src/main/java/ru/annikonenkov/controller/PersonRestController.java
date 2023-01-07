package ru.annikonenkov.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.annikonenkov.enity.food.Food;
import ru.annikonenkov.enity.link.PersonPetLink;
import ru.annikonenkov.enity.person.ConvertorPersonToDTO;
import ru.annikonenkov.enity.person.Person;
import ru.annikonenkov.enity.person.PersonDTO;
import ru.annikonenkov.enity.person.photo.PersonPhoto;
import ru.annikonenkov.enity.pet.Pet;
import ru.annikonenkov.enity.repast.ConvertReapstToDTO;
import ru.annikonenkov.enity.repast.Repast;
import ru.annikonenkov.service.FoodService;
import ru.annikonenkov.service.PersonPetLinkService;
import ru.annikonenkov.service.PersonPhotoService;
import ru.annikonenkov.service.PersonService;
import ru.annikonenkov.service.PetService;
import ru.annikonenkov.service.RepastService;

@Transactional(readOnly = true)
@RestController
@RequestMapping("/person")
public class PersonRestController {

    private static final Logger log = LoggerFactory.getLogger(PersonRestController.class);

    @Autowired
    PersonService personService;

    @Autowired
    PersonPhotoService personPhotoService;

    @Autowired
    PetService petService;

    @Autowired
    PersonPetLinkService personPetLinkService;

    @Autowired
    FoodService foodService;

    @Autowired
    RepastService repastService;

    // TODO: Добавить минимальную валидация возраста и имени.
    @Transactional(readOnly = false)
    @PostMapping("/addNewPersonWithoutPhoto")
    // ++++++++++++++++++++++++++++++++++++++++
    public ResponseEntity<PersonDTO> addNewPerson(@RequestParam(value = "name") String name, @RequestParam(value = "old") Integer old) {
        Person person = new Person();
        person.setName(name);
        person.setOld(old);

        Person persistedPerson = personService.savePerson(person);

        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        PersonDTO personDTO = convertorPersonToDTO.convertToDTOOnlyPerson(person);

        return ResponseEntity.status(HttpStatus.OK).header("personID", personDTO.getId().toString()).body(personDTO);
    }

    // TODO: Добавить проверку на размер файла, валидацию на пользователя (возраст и имя).
    // @PostMapping("/post")
    @Transactional(readOnly = false)
    @RequestMapping(value = "/addNewPersonWithPhoto", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"})
    @ResponseBody
    // ++++++++++++++++++++++++++++++++++++++++
    public ResponseEntity<PersonDTO> addNewPersonWithPhoto(@RequestPart("file") MultipartFile file, @RequestParam(value = "name") String name, @RequestParam(value = "old") Integer old) {

        String getOriginalFilename = file.getOriginalFilename();
        Long length = file.getSize();
        String mine = file.getContentType();
        // String name = file.getName();// Соответствует - Part name of the multy-part-form-data

        Person person = new Person();
        person.setName(name);
        person.setOld(old);

        PersonPhoto personPhoto = new PersonPhoto();
        personPhoto.setPhotoName(getOriginalFilename);
        personPhoto.setMime(mine);
        personPhoto.setSize(length);

        personPhoto.setPerson(person);

        try {
            personPhoto.setFileOfPhoto(file.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // TODO: Подумать над каскадным сохранением, чтоб не сохранять каждую сущность отдельно.
        Person persistedPerson = personService.savePerson(person);
        PersonPhoto persistedPersonPhoto = personPhotoService.saveNewPersonPhoto(personPhoto);

        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        PersonDTO personDTO = convertorPersonToDTO.convertToDTOWithPhotos(persistedPerson);

        return ResponseEntity.status(HttpStatus.OK).header("personID", personDTO.getId().toString()).body(personDTO);
    }

    @RequestMapping(value = "/addPhotoToPerson", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"})
    @ResponseBody
    @Transactional(readOnly = false)
    // Мешает - вновь добавленное фото еще не успевает сохраниться в БД (т.к. транзакция еще не закоммичена) и как следствие - падаем при сереализации в JSON на null для PrsonPhoto - Photo::: {id = 0, photoName = null, mime = null, size =
    // null}]
    // Поменял местами PersonPhoto persistedPersonPhoto = personPhotoService.saveNewPersonPhoto(personPhoto); Person persistedPerson = personService.saveExistPerson(person); - и проблема с null - решилась - не понятно почему - ведь
    // транзакция еще не завершилась. Может будет плавающая ошибка.
    // ----
    // TODO: Нужно разобраться: Без @Transactional - фото не добавляется. saveExistPerson(...) т.е. там где используется merge - без транзакции не работает.
    // ++++++++++++++++-------------------------- нужно еще разбираться
    public PersonDTO addPhotoToPerson(@RequestPart("file") MultipartFile file, @RequestParam(value = "personId") Integer personId, @RequestParam(value = "isMain") Boolean isMain) {
        Person person = personService.getPersonById(personId);
        if (person == null)
            return null;

        String getOriginalFilename = file.getOriginalFilename();
        Long length = file.getSize();
        String mine = file.getContentType();

        PersonPhoto personPhoto = new PersonPhoto();
        personPhoto.setPhotoName(getOriginalFilename);
        personPhoto.setMime(mine);
        personPhoto.setSize(length);

        personPhoto.setPerson(person);

        try {
            personPhoto.setFileOfPhoto(file.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        PersonPhoto persistedPersonPhoto = personPhotoService.saveNewPersonPhoto(personPhoto);
        Person persistedPerson = personService.savePerson(person);

        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        Person updatedPerson = personService.getPersonIdWithPhotos(personId);
        PersonDTO personDTO = convertorPersonToDTO.convertToDTOWithPhotos(updatedPerson);
        // PersonDTO personDTO = convertorPersonToDTO.convertToDTOWithPhotos(persistedPerson);

        return personDTO;
    }

    // TODO: Добавить пагинацию/пэйджинг (т.е. отдавать порционно). 
    @GetMapping("/getFullListOfPerson")
    // ++++++++++++++++++++++++++++++++++++++++
    public List<PersonDTO> getFullListOfPerson() {
        List<Person> personList = personService.getAllPerson();
        List<PersonDTO> personDTOList = new ArrayList<>();
        if (personList == null || personList.size() == 0) {
            log.warn("\n The GET method 'getFullListOfPerson': call personService.getAllPerson() return null or size() is 0 \n");
            return personDTOList;
        } else {
            log.debug("\n All is good personList.size() = {} \n", personList.size());
        }
        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        // personList.forEach(item -> personDTOList.add(convertorPersonToDTO.convertToDTOWithPets(item)));
        // Самое интересное, что так работает т.е. получаем информацию по животным хотя транзацкции нет. Но каждый запрос идет отдельно по каждому животному. Это как?????
        // А ответ простой, в SpringData - hibernate-session(entityManager) видимо продолжает жить (т.е. не вызывается метод close()|evict()) и в рамках него продолжают выполняться запросы - т.е. выдергиваться Pet.
        // Нужно видимо изучать классик org.springframework.data.jpa.repository.support.SimpleJpaRepository<T, ID> - видимо в нем кроются все ответы.
        personList.forEach(item -> personDTOList.add(convertorPersonToDTO.convertToDTOOnlyPerson(item)));
        return personDTOList;
    }

    @GetMapping("/getListOfPersonMatchedName")
    // ++++++++++++++++++++++++++++++++++++++++
    public List<PersonDTO> getListOfPersonMatchedName(@RequestParam(value = "maskOfName") String maskOfName) {
        List<PersonDTO> personDTOList = new ArrayList<>();
        List<Person> personList = personService.getPersonMatchedByName(maskOfName);
        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        // personList.forEach(item -> personDTOList.add(convertorPersonToDTO.convertToDTOWithPets(item)));
        // Самое интересное, что так работает т.е. получаем информацию по животным хотя транзацкции нет. Но каждый запрос идет отдельно по каждому животному. Это как?????
        // А ответ простой, в SpringData - hibernate-session(entityManager) видимо продолжает жить (т.е. не вызывается метод close()|evict()) и в рамках него продолжают выполняться запросы - т.е. выдергиваться Pet.
        personList.forEach(item -> personDTOList.add(convertorPersonToDTO.convertToDTOOnlyPerson(item)));
        return personDTOList;
    }

    @GetMapping("/getPersonIdWithPets")
    // ++++++++++++++++++++++++++++++++++++++++
    public ResponseEntity<?> getPersonIdWithPets(@RequestParam(value = "personId") Integer personId) {
        Person person = personService.getPersonByIdWithPets(personId);
        if (person == null) {
            log.warn("\n person = {} \n", person);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не найден целевой пользователь по id = " + personId);
            // return null;
            // return new PersonDTO();
        }
        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        PersonDTO personDTO = convertorPersonToDTO.convertToDTOWithPets(person);
        return ResponseEntity.status(HttpStatus.OK).header("personID", personDTO.getId().toString()).body(personDTO);

    }

    @Transactional(readOnly = false)
    // Без @Transactional падает ошибка javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'merge' call.
    // Если используется entityManager.merge(person);
    @PutMapping("/editOldPersonId")
    // ++++++++++++++++++++++++++++++++++++++++
    public PersonDTO editOldPersonId(@RequestParam(value = "personId") Integer personId, @RequestParam(value = "old") Integer old) {
        Person person = personService.getPersonByIdWithPets(personId);
        if (person == null) {
            log.warn("\n person = {} \n", person);
            return null;
            // return new PersonDTO();
        }
        person.setOld(old);
        Person updatedPerson = personService.savePerson(person);
        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        return convertorPersonToDTO.convertToDTOOnlyPerson(updatedPerson);
    }

    @Transactional(readOnly = false)
    @PutMapping("/editNamePersonId")
    // ++++++++++++++++++++++++++++++++++++++++
    public PersonDTO editNamePersonId(@RequestParam(value = "personId") Integer personId, @RequestParam(value = "name") String name) {
        Person person = personService.getPersonByIdWithPets(personId);
        if (person == null) {
            log.warn("\n person = {} \n", person);
            return null;
            // return new PersonDTO();
        }
        person.setName(name);
        Person updatedPerson = personService.savePerson(person);
        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        return convertorPersonToDTO.convertToDTOOnlyPerson(updatedPerson);
    }

    @Transactional(readOnly = false)
    @PutMapping("/addPetToPetsonId")
    // ++++++++++++++++++++++++++++++++++++++++
    public PersonDTO addPetToPetsonId(@RequestParam(value = "petId") Integer petId, @RequestParam(value = "personId") Integer personId) {
        Person person = personService.getPersonByIdWithPets(personId);
        Pet pet = petService.getPetById(petId);
        if (person == null || pet == null) {
            log.warn("\n person = {} or pet = {} \n", person, pet);
            return null;
        }

        Set<PersonPetLink> links = person.getPetsLinks();
        for (PersonPetLink link : links) {
            if (link.getPet().getId() == petId) {
                ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
                return convertorPersonToDTO.convertToDTOWithPets(person);
            }
        }

        PersonPetLink personPetLink = new PersonPetLink();
        personPetLink.setPerson(person);
        personPetLink.setPet(pet);
        personPetLinkService.save(personPetLink);
        personService.savePerson(person);
        petService.savePet(pet);

        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        Person personWithPets = personService.getPersonByIdWithPets(personId);

        return convertorPersonToDTO.convertToDTOWithPets(personWithPets);
    }

    @GetMapping(value = "/getPersonIdWithItsPhotos/{personId}")
    @ResponseBody
    @Transactional(readOnly = false)
    // @Transactional // Без @Transactional не работает, падает с ошибкой (ругается на Blob)
    public PersonDTO getPersonIdWithItsPhotos(@PathVariable Integer personId) {
        Person person = personService.getPersonIdWithPhotos(personId);
        // Person person = personService.getPersonById(personId);
        if (person == null) {
            log.warn("\n person = {} \n", person);
            return null;
        }
        ConvertorPersonToDTO convertorPersonToDTO = new ConvertorPersonToDTO();
        return convertorPersonToDTO.convertToDTOWithPhotos(person);
    }

    // @Transactional // Помогает выдернуть файл. Видимо все запросы в одной транзацкции. //@Transactional - указал на уровне класса
    @GetMapping(value = "/getPhotoId/{photoIdPath}")
    @ResponseBody
    public byte[] getPhotoId(@RequestParam(value = "photoIdReq") Integer photoIdReq, @PathVariable Integer photoIdPath, HttpServletResponse response) {
        PersonPhoto persPhoto = personPhotoService.getById(photoIdReq);
        if (persPhoto == null) {
            log.warn("\n persPhoto = {} \n", persPhoto);
            return new byte[0];
        }
        MediaType mediaType = MediaType.parseMediaType(persPhoto.getMime());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        response.setHeader("Content-Disposition", "attachment; filename=" + persPhoto.getPhotoName());
        return persPhoto.getFileOfPhoto();
    }

    // TODO: Снова, если такого Person нет и нет Pet и нет Food c id.
    @PutMapping(value = "/getFoodToPet")
    @ResponseBody
    public ResponseEntity<?> getFoodToPet(@RequestParam(value = "personId") Integer personId, @RequestParam(value = "petId") Integer petId, @RequestParam(value = "foodId") Integer foodId, @RequestParam(value = "weight") Integer weight) {
        Person person = personService.getPersonById(personId);
        Pet pet = petService.getPetById(petId);// Добавить проверку, а принадлежит ли это животное данному человеку.
        Food food = foodService.getFoodById(foodId);
        if (person == null || pet == null || food == null) {
            log.warn("\n person = {}, pet = {}, food = {} \n", person, pet, food);
            return null;
        }

        Repast repast = new Repast();

        repast.setPet(pet);
        repast.setFood(food);
        repast.setPortionWeight(weight);
        repast.setTime(new Date());

        Repast persistedRepast = repastService.saveNew(repast);
        petService.savePet(pet);

        ConvertReapstToDTO convertReapstToDTO = new ConvertReapstToDTO();

        return ResponseEntity.status(HttpStatus.OK).body(persistedRepast.toString());
        // TODO:Ошибка - разобраться.
        // return convertReapstToDTO.convertToDTO(persistedRepast);
    }

}
