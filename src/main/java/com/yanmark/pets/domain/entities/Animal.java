package com.yanmark.pets.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "animals")
public class Animal extends BaseEntity {

  private String name;
  private Set<Pet> pets;

  public Animal() {
    this.pets = new HashSet<>();
  }

  @Column(name = "name", nullable = false, unique = true)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(targetEntity = Pet.class, mappedBy = "animal")
  public Set<Pet> getPets() {
    return pets;
  }

  public void setPets(Set<Pet> pets) {
    this.pets = pets;
  }
}
