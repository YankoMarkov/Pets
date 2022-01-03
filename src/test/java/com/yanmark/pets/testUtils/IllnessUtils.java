package com.yanmark.pets.testUtils;

import com.yanmark.pets.domain.entities.Illness;
import com.yanmark.pets.domain.entities.Pet;

import java.time.LocalDate;
import java.util.HashSet;

public class IllnessUtils {

    public static Illness getIllness() {
        Illness illness = new Illness();
        illness.setId("id");
        illness.setName("name");
        illness.setDescription("description");
        illness.setDate(LocalDate.now());
        illness.setPet(new Pet());
        illness.setImages(new HashSet<>());
        return illness;
    }
}
