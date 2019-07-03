package com.yanmark.pets.domain.entities;

import com.yanmark.pets.domain.enums.Animal;
import com.yanmark.pets.domain.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "pets")
public class Pet extends BaseEntity {

    private Animal animal;
    private String name;
    private List<Image> images;
    private LocalDate birthDate;
    private String breed;
    private String furColor;
    private Gender gender;
    private Healthy healthy;
    private User owner;

    public Pet() {
        this.images = new ArrayList<>();
    }

    @Column(name = "animals", nullable = false)
    @Enumerated(EnumType.STRING)
    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = Image.class, mappedBy = "pet")
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Column(name = "birth_date", nullable = false)
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "breed", nullable = false)
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Column(name = "fur_color")
    public String getFurColor() {
        return furColor;
    }

    public void setFurColor(String furColor) {
        this.furColor = furColor;
    }

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @OneToOne(targetEntity = Healthy.class)
    @JoinColumn(name = "healthy_id", referencedColumnName = "id")
    public Healthy getHealthy() {
        return healthy;
    }

    public void setHealthy(Healthy healthy) {
        this.healthy = healthy;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
