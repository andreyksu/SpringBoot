package ru.annikonenkov.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.annikonenkov.enity.food.Food;

public interface FoodRepository extends JpaRepository<Food, Integer> {

    // TODO: Эти выборки были добавлены для LAZY инициализации. Пока везде добавил EAGER
    @Query("SELECT f FROM Food f WHERE f.foodName LIKE %:maskOfFood%")
    public List<Food> getFoodMatchedByName(@Param("maskOfFood") String maskOfFood);

    @Query("SELECT f FROM Food f WHERE f.foodWeight < :foodWeight")
    public List<Food> getFoodWithWeightLessThan(@Param("foodWeight") Integer foodWeight);

}
