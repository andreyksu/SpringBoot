package ru.annikonenkov.enity.repast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.annikonenkov.enity.food.Food;

public class RepastDTO {

    private int id;

    private Date time;

    private Food food;

    private int portionWeight;

    private List<String> commetns = new ArrayList<>();

    public RepastDTO(int id, Date time, Food food, int portionWeight, List<String> commetns) {
        this.id = id;
        this.time = time;
        this.food = food;
        this.portionWeight = portionWeight;
        this.commetns = commetns;
    }

    public int getId() {
        return this.id;
    }

    public Date getTime() {
        return this.time;
    }

    public Food getFood() {
        return this.food;
    }

    public int getPortionWeight() {
        return this.portionWeight;
    }

    public List<String> getComments() {
        return this.commetns;
    }

}
