package ru.annikonenkov.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.food.Food;

@Service
public class FoodService {

    @Autowired
    private ru.annikonenkov.repository.FoodRepository foodRepository;

    @PersistenceContext
    private EntityManager entityManager; // Добавлял для merge - но оказалось что в spring - в метод save(...) уже обёрнут метод merge(...).

    private static final Logger log = LoggerFactory.getLogger(FoodService.class);

    public Food saveFood(Food food) {
        Food managedFood = foodRepository.save(food);
        log.debug("save(...) ---- The new Food was saved = {}", managedFood.toString());
        return managedFood;
    }
    /*
     * См. класс PersonService - там все пояснения. public Food saveExistFood(Food food) { Food managedFood = entityManager.merge(food); log.info("saveExistFood(...) ---- The new Food was saved = {}", managedFood.toString()); return
     * managedFood; }
     */

    public Food getFoodById(Integer foodId) {
        Food managedFood = foodRepository.getById(foodId);
        log.debug("getFoodById(...) ---- The Food that extracted by id = {}", managedFood.toString());
        return managedFood;
    }

    public List<Food> getAllFood() {
        log.debug("getAllFood(...) ---- Will be returned full list of food");
        return foodRepository.findAll();
    }

    public List<Food> getFoodMatchedToMask(String maskOfFood) {
        List<Food> foodList = foodRepository.getFoodMatchedByName(maskOfFood);
        log.debug("getFoodMatchedToMask(...) ---- Will be returned list of food that match to string = {}", maskOfFood);
        return foodList;
    }

    public List<Food> getFoodWithWeightLessThan(Integer weight) {
        List<Food> foodList = foodRepository.getFoodWithWeightLessThan(weight);
        log.debug("getFoodWithWeightLessThan(...) ---- Will be returned list of food that less than weight = {}", weight);
        return foodList;
    }

}
