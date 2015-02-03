package edu.cmu.cs.cs214.rec14.exercises.flights;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FlightExerciseTest {

    private FlightExercise exercise;
    private Flight a, b, c, d, e, f, g, h, i, j, k;

    @Before
    public void setUp() throws Exception {
        a = new Flight("Chicago", "Philadelphia");
        b = new Flight("Philadelphia", "Los Angeles");
        c = new Flight("Los Angeles", "San Antonio");
        d = new Flight("San Antonio", "Argentina");
        e = new Flight("Argentina", "New Mexico");
        f = new Flight("New Mexico", "Chicago");


        g = new Flight("Chicago", "Philadelphia");
        h = new Flight("Chicago", "New York");
        i = new Flight("Chicago", "Dallas");
        j = new Flight("Denver", "Philadelphia");
        k = new Flight("New York", "Dallas");
        exercise = new FlightExercise();
    }

    @Test
    public void testGetItinerary() {
        List<String> result = exercise.getItinerary("Chicago", Arrays.asList(f, e, c, d, a, b));
        assertEquals(Arrays.asList("Chicago", "Philadelphia", "Los Angeles", "San Antonio",
                "Argentina", "New Mexico"), result);
    }

    @Test
    public void testGetCitiesWithIncomingFlights() {

        List<String> result = exercise.getCitiesWithIncomingFlights(Arrays.asList(g, h, i, j, k));
        Collections.sort(result);
        List<String> expected = Arrays.asList("Philadelphia", "New York", "Dallas");
        Collections.sort(expected);
        assertEquals(expected, result);
    }
}