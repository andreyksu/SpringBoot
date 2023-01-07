package ru.annikonenkov.enity.pet;

public class ConvertorPetToDTO {

    public PetDTO convertToDTOOnlyPet(Pet pet) {
        return new PetDTO(pet.getId(), pet.getName(), pet.getOld());
    }

    public PetDTO convertToDTOWithPerson(Pet pet) {
        return new PetDTO(pet.getId(), pet.getName(), pet.getOld(), pet.getLink(), pet.getRepast());
    }

    public PetDTO convertToDTOWithReapset(Pet pet) {
        return new PetDTO(pet.getId(), pet.getName(), pet.getOld(), pet.getLink());
    }

    public PetDTO convertToDTOWithPersonAndRepast(Pet pet) {
        return new PetDTO(pet.getRepast(), pet.getId(), pet.getName(), pet.getOld());
    }

}
