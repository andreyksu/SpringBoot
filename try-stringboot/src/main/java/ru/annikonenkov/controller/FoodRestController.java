package ru.annikonenkov.controller;

import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.annikonenkov.enity.food.Food;
import ru.annikonenkov.service.FoodService;

@Transactional(readOnly = true)
@RestController
@RequestMapping("/food")
public class FoodRestController {

    @Autowired
    FoodService foodService;

    private static final Logger log = LoggerFactory.getLogger(FoodRestController.class);

    @Transactional(readOnly = false)
    @PostMapping("/food") // Так вроде правильнее со стороны Rest - от сущности к действиям и никаких глаголов в запросах
    public ResponseEntity<Food> trueWayAddNewFood(@RequestParam(value = "foodName") String foodName, @RequestParam(value = "foodWeight") Integer foodWeight) throws PSQLException {
        Food food = new Food();
        food.setFoodWeight(foodWeight);
        food.setFoodName(foodName);
        log.debug("Пытаемся сохранить новую еду {}", food.toString());
        Food persistedFood = foodService.saveFood(food);
        return ResponseEntity.status(HttpStatus.OK).body(persistedFood);
    }

    // @Transactional(readOnly = false)
    // @PostMapping("/addNewFood")
    // public ResponseEntity<Food> addNewFood(@RequestParam(required = true, defaultValue = "NoName", value = "foodName") String foodName, @RequestParam(required = true, value = "foodWeight") Integer foodWeight) throws PSQLException {
    // Food food = new Food();
    // food.setFoodWeight(foodWeight);
    // food.setFoodName(foodName);
    // log.debug("Пытаемся сохранить новую еду {}", food.toString());
    // Food persistedFood = foodService.saveFood(food);
    // // TODO: Есть уникальность на добавление еды. Уникальность по имени. Соответственно выкидывается исключение - нужно обработать это исключение.
    // return ResponseEntity.status(HttpStatus.OK).body(persistedFood);
    // }

    @Transactional(readOnly = false)
    @PutMapping("/food") // Так вроде правильнее со стороны Rest - от сущности к действиям и никаких глаголов в запросах
    public Food trueWayEditWeightExistFood(@RequestParam(value = "foodId") Integer foodId, @RequestParam(value = "foodWeight") Integer foodWeight) {
        Food food = foodService.getFoodById(foodId);
        food.setFoodWeight(foodWeight);
        Food updatedFood = foodService.saveFood(food);
        return updatedFood;
    }

    // @Transactional(readOnly = false)
    // @PutMapping("/editWeightExistFood") public Food editWeightExistFood(@RequestParam(value = "foodId") Integer foodId, @RequestParam(value = "foodWeight") Integer foodWeight) { Food food = foodService.getFoodById(foodId);
    // food.setFoodWeight(foodWeight);
    // Food updatedFood = foodService.saveFood(food);
    // return updatedFood;
    // }

    // Вместо трёх путей и трех методов. Так вроде правильнее со стороны Rest - от сущности к действиям и никаких глаголов в запросах
    // TODO: Додавить пагинацию.
    @GetMapping("/food")
    public List<Food> trueWayGetFoodList(@RequestParam(value = "maskOfFood", required = false, defaultValue = "___empty___") String maskOfFood, @RequestParam(value = "weight", required = false, defaultValue = "-1") Integer weight) {
        log.info("maskOfFood = {}, weight = {}", maskOfFood, weight);
        List<Food> foodList = new ArrayList<>();
        if (maskOfFood.equals("___empty___") && weight == -1) {
            foodList = foodService.getAllFood();
        } else if (maskOfFood.equals("___empty___")) {
            foodList = foodService.getFoodWithWeightLessThan(weight);
        } else if (weight == -1) {
            foodList = foodService.getFoodMatchedToMask(maskOfFood);
        }
        return foodList;
    }

    // @GetMapping("/getFoodList")
    // public List<Food> getFoodList() {
    // List<Food> foodList = foodService.getAllFood();
    // return foodList;
    // }
    //
    // @GetMapping("/getFoodListMathcedForName")
    // public List<Food> getFoodListMathcedForName(@RequestParam(value = "maskOfFood") String maskOfFood) {
    // List<Food> foodMatchedName = foodService.getFoodMatchedToMask(maskOfFood);
    // return foodMatchedName;
    // }
    //
    // @GetMapping("/getFoodWithWeightLessThan")
    // public List<Food> getFoodWithWeightLessThan(@RequestParam(value = "weight") Integer weight) {
    // List<Food> foodMatchedName = foodService.getFoodWithWeightLessThan(weight);
    // return foodMatchedName;
    // }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> headerException(PSQLException e) {
        String messageResult = "We have a problem. Some going wrong:::\n\n" + e.getMessage() + ":::\n\nit is the end of the message!";
        log.error(messageResult);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(messageResult);
    }

}
