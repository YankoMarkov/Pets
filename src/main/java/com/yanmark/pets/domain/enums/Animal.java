package com.yanmark.pets.domain.enums;

public enum Animal {

    DOG("Dog"),
    CAT("Cat");

    private String animal;

    Animal(String animal) {
        this.animal = animal;
    }

    public String getAnimal() {
        return animal;
    }

    @Override
    public String toString() {
        return getAnimal();
    }
}
