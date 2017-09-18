package com.stock.price.model;

public class Columns {

    private String name;
    private String type;

    public Columns() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Columns{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
