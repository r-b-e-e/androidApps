package com.example.rakeshbalan.temp;

import java.io.Serializable;

/**
 * Created by rakeshbalan on 12/30/2015.
 */
public class Flight implements Serializable{
    Integer flightDuration;
    String flightCarrier, flightNumber;
    Integer connectionDuration;
    String arrivalTime, departureTime, origin, destination;

    public Integer getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(Integer flightDuration) {
        this.flightDuration = flightDuration;
    }

    public String getFlightCarrier() {
        return flightCarrier;
    }

    public void setFlightCarrier(String flightCarrier) {
        this.flightCarrier = flightCarrier;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getConnectionDuration() {
        return connectionDuration;
    }

    public void setConnectionDuration(Integer connectionDuration) {
        this.connectionDuration = connectionDuration;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
