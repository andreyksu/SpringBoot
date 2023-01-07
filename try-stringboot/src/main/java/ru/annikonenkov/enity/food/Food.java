package ru.annikonenkov.enity.food;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "spring_food", uniqueConstraints = {@UniqueConstraint(columnNames = {"food_name"}, name = "uk_food_name") })
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "food_name", unique = true, nullable = false)
    private String foodName;

    @Column(name = "food_weight")
    private Integer foodWeight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public void setFoodWeight(Integer foodWeight) {
        this.foodWeight = foodWeight;
    }

    public Integer getFoodWeight() {
        return this.foodWeight;
    }

    @Override
    public String toString() {
        return String.format("Food {id = %d, foodName = %s, foodWeight = %d}", id, foodName, foodWeight);
    }

}
