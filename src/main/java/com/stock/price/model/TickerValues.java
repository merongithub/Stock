package com.stock.price.model;

import java.util.Date;

public class TickerValues {

    private String  month;
    private Double average_open;
    private Double average_close;
    public TickerValues(){
        average_close=new Double(0);
        average_close=new Double(0);

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getAverage_open() {
        return average_open;
    }

    public void setAverage_open(Double average_open) {
        this.average_open = average_open;
    }

    public Double getAverage_close() {
        return average_close;
    }

    public void setAverage_close(Double average_close) {
        this.average_close = average_close;
    }

    @Override
    public String toString() {
        return "TickerValues{" +
                "month='" + month + '\'' +
                ", average_open=" + average_open +
                ", average_close=" + average_close +
                '}';
    }
}
