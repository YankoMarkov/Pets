package com.yanmark.pets.domain.entities;

import com.yanmark.pets.domain.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "pets")
public class Pet extends BaseEntity {

  private String image;
  private Animal animal;
  private String name;
  private LocalDate birthDate;
  private String breed;
  private String furColor;
  private Gender gender;
  private boolean isCastrated;
  private LocalDate vaccineDate;
  private Set<Illness> illnesses;
  private User owner;

  public Pet() {
    this.illnesses = new HashSet<>();
  }

  @Column(name = "image", nullable = false)
  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @ManyToOne(targetEntity = Animal.class)
  @JoinColumn(name = "animal_id", nullable = false, referencedColumnName = "id")
  public Animal getAnimal() {
    return animal;
  }

  public void setAnimal(Animal animal) {
    this.animal = animal;
  }

  @Column(name = "name", nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "birth_date", nullable = false)
  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  @Column(name = "breed")
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

  @Column(name = "is_castrated", nullable = false)
  public boolean isCastrated() {
    return isCastrated;
  }

  public void setCastrated(boolean castrated) {
    isCastrated = castrated;
  }

  @Column(name = "vaccine_date")
  public LocalDate getVaccineDate() {
    return vaccineDate;
  }

  public void setVaccineDate(LocalDate vaccineDate) {
    this.vaccineDate = vaccineDate;
  }

  @OneToMany(targetEntity = Illness.class, mappedBy = "pet", cascade = CascadeType.ALL)
  public Set<Illness> getIllnesses() {
    return illnesses;
  }

  public void setIllnesses(Set<Illness> illnesses) {
    this.illnesses = illnesses;
  }

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }
}
