package edu.cmu.cs.cs214.rec14.exercises.flights;

public class Flight {

    private String departureCity;
    private String arrivalCity;

    public Flight(String departureCity, String arrivalCity) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }
}
