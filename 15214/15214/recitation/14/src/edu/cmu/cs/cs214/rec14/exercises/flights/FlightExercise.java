package edu.cmu.cs.cs214.rec14.exercises.flights;

import java.util.ArrayList;
import java.util.List;

public class FlightExercise {

    /**
     * 1. Given an initial city and an unordered list of Flights, identify
     * a circuit that starts and ends in the initial city, using each flight
     * once (but not necessarily in the order given in the input list).
     * Your function should express this circuit by returning the list of
     * cities in the order they are visited. You may assume that each city
     * is only visited once, and the number of cities visited is equal to
     * the number of flights.  The initial city should be at the beginning
     * of the output list, but not at the end.
     */
    public List<String> getItinerary(String initialCity, List<Flight> flights) {
        return new ArrayList<>();
    }

    /**
     * Given a list of cities and the outgoing flights from that city, return all cities which have incoming flights.
     */
    public List<String> getCitiesWithIncomingFlights(List<Flight> flights) {
        return new ArrayList<>();
    }

}
