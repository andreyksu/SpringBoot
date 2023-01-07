package ru.annikonenkov.enity.repast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import ru.annikonenkov.enity.food.Food;
import ru.annikonenkov.enity.pet.Pet;

@Entity
@Table(name = "spring_repast")
public class Repast {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    // TODO: А зачем у нас трапеза(приём пищи) знает о животном?
    // TODO: В данном конкретном случае - нужна односторонняя связь. Т.е. когда животное знает о приёме пищи. Или же у животного добавить даже не связь а @ElementCollection.
    // TODO: mappedBy согласно Энтони Гонсалвес JavaEE - не может присутствовать на стороне ManyToOne
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_repast_on_a_pet"), nullable = false)
    private Pet pet;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ref_to_food", nullable = false)
   // @Column(name = "food")
    private Food food;

    @Column(name = "portion_weight")
    private Integer portionWeight;

    // TODO: Пример поля, что не будет сохранено в БД.
    @Transient
    private Integer someExampleThatWilltBeSaved;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "comment")
    @Column(name = "spring_comment_table")
    private List<String> comments = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Pet getPet() {
        return this.pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Food getFood() {
        return this.food;
    }

    public void setPortionWeight(Integer portionWeight) {
        this.portionWeight = portionWeight;
    }

    public Integer getPortionWeight() {
        return this.portionWeight;
    }

    public void setSomeExampleThatWilltBeSaved(Integer some) {
        this.someExampleThatWilltBeSaved = some;
    }

    public Integer setSomeExampleThatWilltBeSaved() {
        return this.someExampleThatWilltBeSaved;
    }

    public void setCommetn(String comment) {
        this.comments.add(comment);
    }

    public List<String> getComments() {
        return this.comments;
    }

    @Override
    public String toString() {
        return String.format("Repast{id = %d, time = %s, foodName = %s}", id, time.toString(), food);
    }

}
