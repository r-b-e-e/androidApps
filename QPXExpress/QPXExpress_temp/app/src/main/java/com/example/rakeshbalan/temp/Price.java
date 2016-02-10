package com.example.rakeshbalan.temp;

import java.io.Serializable;

/**
 * Created by rakeshbalan on 12/29/2015.
 */
public class Price implements Serializable{
    String saleFareTotalAdult, saleTaxTotalAdult, saleTotalAdult;
    String saleFareTotalChild, saleTaxTotalChild, saleTotalChild;

    public String getSaleFareTotalAdult() {
        return saleFareTotalAdult;
    }

    public void setSaleFareTotalAdult(String saleFareTotalAdult) {
        this.saleFareTotalAdult = saleFareTotalAdult;
    }

    public String getSaleTaxTotalAdult() {
        return saleTaxTotalAdult;
    }

    public void setSaleTaxTotalAdult(String saleTaxTotalAdult) {
        this.saleTaxTotalAdult = saleTaxTotalAdult;
    }

    public String getSaleTotalAdult() {
        return saleTotalAdult;
    }

    public void setSaleTotalAdult(String saleTotalAdult) {
        this.saleTotalAdult = saleTotalAdult;
    }

    public String getSaleFareTotalChild() {
        return saleFareTotalChild;
    }

    public void setSaleFareTotalChild(String saleFareTotalChild) {
        this.saleFareTotalChild = saleFareTotalChild;
    }

    public String getSaleTaxTotalChild() {
        return saleTaxTotalChild;
    }

    public void setSaleTaxTotalChild(String saleTaxTotalChild) {
        this.saleTaxTotalChild = saleTaxTotalChild;
    }

    public String getSaleTotalChild() {
        return saleTotalChild;
    }

    public void setSaleTotalChild(String saleTotalChild) {
        this.saleTotalChild = saleTotalChild;
    }
}
