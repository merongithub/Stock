package com.stock.price.model;

import java.util.List;

public class PriceRequest {
    private Double openPriceSum;
    private Double closePriceSum;
    private int opensize;
    private int closeSize;

    public PriceRequest() {
        openPriceSum=new Double(0);
        closePriceSum=new Double(0);

    }

    public void addOpenPrice(Double price) {
        openPriceSum += price;
        opensize++;
    }

    public void addClosePrice(Double price) {
        closePriceSum += price;
        closeSize++;
    }

    public Double getAvarageOpenPriceSum() {
        if (opensize != 0)
            return (openPriceSum / opensize);
        return openPriceSum;
    }

    public Double getAvarageClosePriceSum() {
        if (closeSize != 0)
            return (closePriceSum / closeSize);
        return closePriceSum;
    }

    public Double getOpenPriceSum() {
        return openPriceSum;
    }

    public void setOpenPriceSum(Double openPriceSum) {
        this.openPriceSum = openPriceSum;
    }

    public Double getClosePriceSum() {
        return closePriceSum;
    }

    public void setClosePriceSum(Double closePriceSum) {
        this.closePriceSum = closePriceSum;
    }

    public int getOpensize() {
        return opensize;
    }

    public void setOpensize(int opensize) {
        this.opensize = opensize;
    }

    public int getCloseSize() {
        return closeSize;
    }

    public void setCloseSize(int closeSize) {
        this.closeSize = closeSize;
    }

    @Override
    public String toString() {
        return "PriceRequest{" +
                "openPriceSum=" + openPriceSum +
                ", closePriceSum=" + closePriceSum +
                ", opensize=" + opensize +
                ", closeSize=" + closeSize +
                '}';
    }
}
