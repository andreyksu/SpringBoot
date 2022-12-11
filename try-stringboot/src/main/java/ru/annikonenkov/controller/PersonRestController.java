package ru.annikonenkov.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.annikonenkov.enity.link.PersonPetLink;
import ru.annikonenkov.enity.person.Person;
import ru.annikonenkov.enity.pet.Pet;
import ru.annikonenkov.enity.photo.PersonPhoto;
import ru.annikonenkov.service.PersonPetLinkService;
import ru.annikonenkov.service.PersonPhotoService;
import ru.annikonenkov.service.PersonService;
import ru.annikonenkov.service.PetService;

@RestController
@RequestMapping("/person")
public class PersonRestController {

    @Autowired
    PersonService personService;

    @Autowired
    PersonPhotoService personPhotoService;

    @Autowired
    PetService petService;

    @Autowired
    PersonPetLinkService personPetLinkService;

    @GetMapping("/get")
    public String getInfo(@RequestParam(value = "info") String info) {
        return String.format("This is the GET request. It has next requestParameter vith info = %s", info);
    }

    @PostMapping("/post")
    public String postInfo(@RequestParam(value = "info") String info) {
        return String.format("This is the POST request. It has next requestParameter vith info = %s", info);
    }

    @GetMapping("/addNewPerson")
    public String addNewPerson(@RequestParam(value = "name") String name, @RequestParam(value = "old") Integer old) {
        Person person = new Person();
        person.setName(name);
        person.setOld(old);
        personService.save(person);
        return person.toString();
    }

    @GetMapping("/getAllPersons")
    public List<Person> getAllPerson() {
        return personService.getAll();
    }

    @GetMapping("/getPersonById")
    public String getPersonById(@RequestParam(value = "id") Integer id) {
        return personService.getPersonById(id).toString();
    }
    
    @Transactional
    @GetMapping("/editPersonById")
    public String editPersonById(@RequestParam(value = "id") Integer id) {
        Person person = personService.getPersonById(id);
        person.setOld(55);
        personService.saveExistPerson(person);
        return personService.getPersonById(id).toString();

    }

    // Пример запроса информации вне транзакции, когда связи Lazy - но выборка идет через JPQL с JOIN FETCH см. класс PersonRepository
    @GetMapping("/getPetByIdOfPerson")
    public String getPetByIdOfPerson(@RequestParam(value = "id") Integer id) {
        StringBuilder sb = new StringBuilder();
        personService.getPersonById(id).getLink().forEach(cI -> {
            sb.append(cI.getPet().toString());
            sb.append("\n");
        });
        return sb.toString();
    }

    // @PostMapping("/post")
    // @Transactional //Если порядок сохранять, то работает без транзакции.
    @RequestMapping(value = "/post", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"})
    @ResponseBody
    public String fristPostMethod(@RequestPart("file") MultipartFile file, @RequestPart("file1") MultipartFile file1, @RequestPart("Fila") String fila) {
        String getOriginalFilename = file.getOriginalFilename();
        Long length = file.getSize();
        // String name = file.getName();//Part name
        String mine = file.getContentType();

        Person person = new Person();
        person.setName("Filipppp");
        person.setOld(222);

        Pet pet = new Pet();
        pet.setName("Richi");
        pet.setOld(1);

        PersonPetLink personPetLink = new PersonPetLink();
        personPetLink.setPerson(person);
        personPetLink.setPet(pet);

        PersonPhoto personPhoto = new PersonPhoto();
        personPhoto.setPhotoName(getOriginalFilename);
        personPhoto.setMime(mine);
        personPhoto.setPerson(person);
        personPhoto.setSize(length);

        try {
            personPhoto.setFileOfPhoto(file.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        personService.save(person);
        petService.save(pet);
        personPetLinkService.save(personPetLink);
        personPhotoService.save(personPhoto);

        return person.toString();
    }

    /*
     * @ResponseBody
     * @RequestMapping(value = "/action/{abcd}/{efgh}", method = RequestMethod.GET, produces = "application/zip")
     * @PreAuthorize("@authorizationService.authorizeMethod()") public FileSystemResource doAction(@PathVariable String abcd, @PathVariable String efgh) { File zipFile = service.getFile(abcd, efgh); return new FileSystemResource(zipFile); }
     */

    @Transactional // Помогает выдернуть файл. Видимо все запросы в одной транзацкции.
    @GetMapping(value = "/getPhoto/{aaa}")
    @ResponseBody
    public byte[] getPhoto(@RequestParam(value = "fileId") Integer fileId, @PathVariable String aaa, HttpServletResponse response) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        PersonPhoto persPhoto = personPhotoService.getById(fileId);
        System.out.println(persPhoto);

        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentType(MediaType.TEXT_PLAIN);
        response.setHeader("Content-Disposition", "attachment; filename=" + new Date().getTime() + "___" + aaa + ".txt");
        return persPhoto.getFileOfPhoto();
    }

    // @PostMapping("/post")
    @RequestMapping(value = "/s_post", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"})
    @ResponseBody
    public String secondPostMethod(@RequestPart("file") List<MultipartFile> files) {
        String res = "";
        for (MultipartFile f : files) {
            String getOriginalFilename = f.getOriginalFilename();
            Long length = f.getSize();
            String name = f.getName();
            String resultName = String.format("getOriginalFilename() = %s, getSize() = %d, getName() = %s   ______   ", getOriginalFilename, length, name);
            res += resultName;
        }
        return res;
    }
}
