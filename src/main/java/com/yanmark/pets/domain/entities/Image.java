package com.yanmark.pets.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "images")
public class Image extends BaseEntity {

    private String image;
    private Pet pet;
    private Illness illness;

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne(targetEntity = Pet.class)
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @ManyToOne(targetEntity = Illness.class)
    @JoinColumn(name = "illness_id", referencedColumnName = "id")
    public Illness getIllness() {
        return illness;
    }

    public void setIllness(Illness illness) {
        this.illness = illness;
    }
}
