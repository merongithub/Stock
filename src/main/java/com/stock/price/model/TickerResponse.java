package com.stock.price.model;

import java.util.ArrayList;
import java.util.List;

public class TickerResponse {

    private List<TickerValues> vlues;

    public TickerResponse() {
        vlues = new ArrayList<TickerValues>();

    }

    public List<TickerValues> getVlues() {
        return vlues;
    }

    public void setVlues(List<TickerValues> vlues) {
        this.vlues = vlues;
    }

    public void addVlues(TickerValues values) {
        this.vlues.add(values);
    }
}
