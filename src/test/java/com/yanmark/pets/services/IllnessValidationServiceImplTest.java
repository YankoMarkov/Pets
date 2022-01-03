package com.yanmark.pets.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IllnessValidationServiceImplTest {

    private IllnessValidationServiceImpl illnessValidationService;

    @Before
    public void setUp() {
        this.illnessValidationService = new IllnessValidationServiceImpl();
    }

    @Test
    public void isValidStrings_whenValidDateAndName_thenTrue() {
        boolean isValid = this.illnessValidationService.isValidStrings("12/06/2020", "name");

        Assert.assertTrue(isValid);
    }

    @Test
    public void isValidStrings_whenValidDateAndNullName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidStrings("12/06/2020", null);

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidStrings_whenValidDateAndEmptyName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidStrings("12/06/2020", "");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidStrings_whenValidDateAndWhitespaceName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidStrings("12/06/2020", "    ");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidStrings_whenValidDateAndShortName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidStrings("12/06/2020", "ab");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidStrings_whenNullDateAndValidName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidStrings(null, "name");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidStrings_whenEmptyDateAndValidName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidStrings("", "name");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidStrings_whenWhitespaceDateAndValidName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidStrings("      ", "name");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidStrings_whenWrongDateAndValidName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidStrings("12/25", "name");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenValidName_thenTrue() {
        boolean isValid = this.illnessValidationService.isValidString("name");

        Assert.assertTrue(isValid);
    }

    @Test
    public void isValidString_whenNullName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidString(null);

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenEmptyName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidString("");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenWhitespaceName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidString("   ");

        Assert.assertFalse(isValid);
    }

    @Test
    public void isValidString_whenShortName_thenFalse() {
        boolean isValid = this.illnessValidationService.isValidString("ab");

        Assert.assertFalse(isValid);
    }
}