package edu.cmu.cs.cs214.rec14.exercises.stocks;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StockExerciseTest {

    private Stock microsoft, google, facebook;
    private StockExercise exercise;

    @Before
    public void setUp() {
        microsoft = new Stock("Microsoft", Arrays.asList(47.81, 47.75, 47.47, 47.59, 47.9, 48.70));
        google = new Stock("Google", Arrays.asList(541.83, 540.37, 541.08, 539.27, 537.50, 534.83));
        facebook = new Stock("Facebook", Arrays.asList(77.70, 77.62, 75.63, 74.01, 73.75, 73.60));
        exercise = new StockExercise(Arrays.asList(microsoft, google, facebook));
    }

    @Test
    public void testGetPriceInfo() {
        List<PriceInfo> info = exercise.getPriceInfo();
        assertTrue(info.contains(new PriceInfo(microsoft, 47.47, 48.70)));
        assertTrue(info.contains(new PriceInfo(google, 534.83, 541.83)));
        assertTrue(info.contains(new PriceInfo(facebook, 73.60, 77.70)));
    }

    @Test
    public void testMaxChange() {
        assertEquals(7.00, exercise.maxChange(), .001);
    }
}