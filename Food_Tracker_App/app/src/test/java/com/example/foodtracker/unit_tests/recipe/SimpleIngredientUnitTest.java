package com.example.foodtracker.unit_tests.recipe;

import static org.junit.Assert.assertTrue;

import com.example.foodtracker.model.recipe.SimpleIngredient;

import org.junit.Test;

public class SimpleIngredientUnitTest {
    public SimpleIngredient getMockSimpleIngredient(){
        SimpleIngredient simpleIngredient = new SimpleIngredient();
        return simpleIngredient;
    }

    @Test
    public void testSetAndGetDescription(){
        SimpleIngredient mockSimpleIngredient = getMockSimpleIngredient();
        mockSimpleIngredient.setDescription("Apple");
        assertTrue(mockSimpleIngredient.getDescription() == "Apple");
    }

    @Test
    public void testSetAndGetAmount(){
        SimpleIngredient mockSimpleIngredient = getMockSimpleIngredient();
        mockSimpleIngredient.setAmount(350);
        assertTrue(mockSimpleIngredient.getAmount() == 350);
    }

    @Test
    public void testSetAndGetUnit(){
        SimpleIngredient mockSimpleIngredient = getMockSimpleIngredient();
        mockSimpleIngredient.setUnit("GRAM");
        assertTrue(mockSimpleIngredient.getUnit() == "GRAM");
    }


}
