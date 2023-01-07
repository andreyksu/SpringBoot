package ru.annikonenkov.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.annikonenkov.enity.pet.ConvertorPetToDTO;
import ru.annikonenkov.enity.pet.Pet;
import ru.annikonenkov.enity.pet.PetDTO;
import ru.annikonenkov.service.PetService;

@Transactional
@RestController
@RequestMapping("/pet")
public class PetRestController {

    @Autowired
    PetService petService;

    @PostMapping("/addNewPet")
    public ResponseEntity<PetDTO> addNewPet(@RequestParam(value = "name") String name, @RequestParam(value = "old") Integer old) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setOld(old);
        petService.savePet(pet);

        ConvertorPetToDTO convertor = new ConvertorPetToDTO();
        PetDTO petDTO = convertor.convertToDTOWithPerson(pet);
        return ResponseEntity.status(HttpStatus.OK).body(petDTO);
    }

    @PutMapping("/editNameOfPet")
    public ResponseEntity<PetDTO> editNameOfPet(@RequestParam(value = "name") String name, @RequestParam(value = "petId") Integer petId) {
        Pet pet = petService.getPetById(petId);
        pet.setName(name);
        petService.savePet(pet);

        ConvertorPetToDTO convertor = new ConvertorPetToDTO();
        PetDTO petDTO = convertor.convertToDTOWithPerson(pet);
        return ResponseEntity.status(HttpStatus.OK).body(petDTO);
    }

    @PutMapping("/editOldOfPet")
    public ResponseEntity<PetDTO> editOldOfPet(@RequestParam(value = "old") Integer old, @RequestParam(value = "petId") Integer petId) {
        Pet pet = petService.getPetById(petId);
        pet.setOld(old);
        petService.savePet(pet);

        ConvertorPetToDTO convertor = new ConvertorPetToDTO();
        PetDTO petDTO = convertor.convertToDTOWithPerson(pet);
        return ResponseEntity.status(HttpStatus.OK).body(petDTO);
    }

    @GetMapping("/getPetIdWithItsPerson")
    public ResponseEntity<PetDTO> getPetIdWithItsPerson(@RequestParam(value = "petId") Integer petId) {
        Pet pet = petService.getPetWithPerson(petId);

        ConvertorPetToDTO convertor = new ConvertorPetToDTO();
        PetDTO petDTO = convertor.convertToDTOWithPerson(pet);
        return ResponseEntity.status(HttpStatus.OK).body(petDTO);
    }

    @GetMapping("/getPetIdWithItsRepast")
    public ResponseEntity<PetDTO> getPetIdWithItsRepast(@RequestParam(value = "petId") Integer petId) {
        Pet pet = petService.getPetWithRepast(petId);

        ConvertorPetToDTO convertor = new ConvertorPetToDTO();
        PetDTO petDTO = convertor.convertToDTOWithReapset(pet);
        return ResponseEntity.status(HttpStatus.OK).body(petDTO);
    }

    @GetMapping("/getPetIdWithItsPersonAndRepast") // TODO: Возвращает без Person - нужно разобраться
    public ResponseEntity<PetDTO> getPetIdWithItsPersonAndRepast(@RequestParam(value = "petId") Integer petId) {
        Pet pet = petService.getPetWithRepastAndPerson(petId);

        ConvertorPetToDTO convertor = new ConvertorPetToDTO();
        PetDTO petDTO = convertor.convertToDTOWithPersonAndRepast(pet);
        return ResponseEntity.status(HttpStatus.OK).body(petDTO);
    }

    @GetMapping("/getAllPets")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<Pet> pets = petService.getAllPet();
        List<PetDTO> petsDTO = new ArrayList<>();

        ConvertorPetToDTO convertor = new ConvertorPetToDTO();
        pets.forEach(item -> petsDTO.add(convertor.convertToDTOOnlyPet(item)));
        return ResponseEntity.status(HttpStatus.OK).body(petsDTO);
    }

    @GetMapping("/getPetsMatchedForName")
    public ResponseEntity<List<PetDTO>> getPetsMatchedForName(@RequestParam(value = "maskOfName") String maskOfName) {
        List<Pet> pets = petService.getPersonMatchedByName(maskOfName);
        List<PetDTO> petsDTO = new ArrayList<>();

        ConvertorPetToDTO convertor = new ConvertorPetToDTO();
        pets.forEach(item -> petsDTO.add(convertor.convertToDTOOnlyPet(item)));
        return ResponseEntity.status(HttpStatus.OK).body(petsDTO);
    }

}
