package ru.annikonenkov.enity.repast;

public class ConvertReapstToDTO {

    public RepastDTO convertToDTO(Repast repast) {
        return new RepastDTO(repast.getId(), repast.getTime(), repast.getFood(), repast.getPortionWeight(), repast.getComments());
    }

}
