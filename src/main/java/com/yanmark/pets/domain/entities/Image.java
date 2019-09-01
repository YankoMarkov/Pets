package com.yanmark.pets.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "images")
public class Image extends BaseEntity {

    private String image;
    private Illness illness;

    @Column(name = "image", nullable = false, unique = true)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
