package com.yanmark.pets.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "healthiest")
public class Healthy extends BaseEntity {

    private boolean isCastrated;
    private LocalDate vaccineDate;
    private List<Illness> illnesses;
    private Pet pet;

    public Healthy() {
        this.illnesses = new ArrayList<>();
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

    @OneToMany(targetEntity = Illness.class, mappedBy = "healthy")
    public List<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<Illness> illnesses) {
        this.illnesses = illnesses;
    }

    @OneToOne(targetEntity = Pet.class)
    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
