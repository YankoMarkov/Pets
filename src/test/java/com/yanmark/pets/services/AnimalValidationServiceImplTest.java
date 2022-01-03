package com.yanmark.pets.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnimalValidationServiceImplTest {

    private AnimalValidationServiceImpl animalValidationService;

    @Before
    public void setUp() {
        this.animalValidationService = new AnimalValidationServiceImpl();
    }

    @Test
    public void isValidString_whenValidString_thenTrue() {
        boolean isValid = this.animalValidationService.isValidString("name");

        Assert.assertTrue(isValid);
    }

    @Test
    public void isValidString_whenEmptyString_thenFalse() {
        boolean isValid = this.animalValidationService.isValidString("");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenWhitespaceString_thenFalse() {
        boolean isValid = this.animalValidationService.isValidString("    ");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenStringIsNull_thenFalse() {
        boolean isValid = this.animalValidationService.isValidString(null);

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenStringIsWrong_thenFalse() {
        boolean isValid = this.animalValidationService.isValidString("name123");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenStringIsShort_thenFalse() {
        boolean isValid = this.animalValidationService.isValidString("a");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenStringIsLong_thenFalse() {
        boolean isValid = this.animalValidationService.isValidString("qazwsxedcrfvtgby");

        Assert.assertFalse(isValid);
    }
}