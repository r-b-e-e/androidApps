package com.example.rakeshbalan.temp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by rakeshbalan on 12/29/2015.
 */
public class Option implements Serializable{
    String optionId, saleTotal;
    Price price;
    Integer noOfAdults, noOfChild;
    Integer totalFlightDuration;
    List<Flight> flightList = new ArrayList<Flight>();

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public Integer getTotalFlightDuration() {
        return totalFlightDuration;
    }

    public void setTotalFlightDuration(Integer totalFlightDuration) {
        this.totalFlightDuration = totalFlightDuration;
    }

    public Integer getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(Integer noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public Integer getNoOfChild() {
        return noOfChild;
    }

    public void setNoOfChild(Integer noOfChild) {
        this.noOfChild = noOfChild;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(String saleTotal) {
        this.saleTotal = saleTotal;
    }
}
