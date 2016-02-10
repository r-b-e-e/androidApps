package com.example.rakeshbalan.temp;

import java.io.Serializable;

/**
 * Created by rakeshbalan on 1/7/2016.
 */
public class Retrieved implements Serializable {
    private String origin;
    private String destination;
    private String travelDate;
    private boolean refundable;
    private Integer maxNoOfStops, noOfAdults, noOfChild;

    public Retrieved(String origin, String destination, String travelDate, boolean refundable, Integer maxNoOfStops, Integer noOfAdults, Integer noOfChild) {
        this.origin = origin;
        this.destination = destination;
        this.travelDate = travelDate;
        this.refundable = refundable;
        this.maxNoOfStops = maxNoOfStops;
        this.noOfAdults = noOfAdults;
        this.noOfChild = noOfChild;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public Integer getMaxNoOfStops() {
        return maxNoOfStops;
    }

    public Integer getNoOfAdults() {
        return noOfAdults;
    }

    public Integer getNoOfChild() {
        return noOfChild;
    }
}
