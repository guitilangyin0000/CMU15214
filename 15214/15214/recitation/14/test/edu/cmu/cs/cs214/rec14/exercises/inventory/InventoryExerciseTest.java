package edu.cmu.cs.cs214.rec14.exercises.inventory;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class InventoryExerciseTest {

    private Set<Item> items;
    private InventoryExercise exercise;
    private List<String> itemNames;

    @Before
    public void setUp() {

        items = new HashSet<Item>();
        items.add(new Item("Dell XPS 15", "Laptop", 1300));
        items.add(new Item("Dell XPS 15", "Laptop", 1300));
        items.add(new Item("Egg", "Food", 1));
        items.add(new Item("Egg", "Food", 1));
        items.add(new Item("Egg", "Food", 1));
        items.add(new Item("Egg", "Food", 1));
        items.add(new Item("Egg", "Food", 1));
        items.add(new Item("Bacon", "Food", 4));
        items.add(new Item("Google Chromebook", "Laptop", 400));
        items.add(new Item("Dell Alienware 17", "Laptop", 2300));
        items.add(new Item("1984", "Book", 20));
        items.add(new Item("1984", "Book", 20));
        items.add(new Item("Brave New World", "Book", 20));
        items.add(new Item("Samsung TV", "TV", 1400));

        itemNames = Arrays.asList(
                "Dell XPS 15",
                "Egg",
                "Bacon",
                "Google Chromebook",
                "Dell Alienware 17",
                "1984",
                "Brave New World",
                "Samsung TV"
        );
        exercise = new InventoryExercise(items);
    }

    @Test
    public void testGetAllItemNames() {
        List<String> result = exercise.getAllItemNames();
        assertNotNull(result);
        assertEquals(result.size(), items.size());
        for (String item : itemNames)
            assertTrue(result.contains(item));
    }

    @Test
    public void testGetDistinctItemNames() {
        List<String> result = exercise.getDistinctItemNames();
        assertNotNull(result);
        Collections.sort(result);
        Collections.sort(itemNames);
        assertEquals(itemNames, result);
    }

    @Test
    public void testGetTotalPrice() {
        assertEquals(6769, exercise.getTotalPrice());
    }

    @Test
    public void testGetTotalLaptopPrice() {
        assertEquals(4900, exercise.getTotalLaptopPrice());
    }

    @Test
    public void testGetTotalPricePerCategory() {
        Map<String, Integer> categoryMap = new HashMap<>();
        categoryMap.put("Laptop", 5300);
        categoryMap.put("Food", 9);
        categoryMap.put("Book", 60);
        categoryMap.put("TV", 1400);
        assertEquals(categoryMap, exercise.getTotalPricePerCategory());
    }
}