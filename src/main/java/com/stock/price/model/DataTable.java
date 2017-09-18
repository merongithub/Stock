package com.stock.price.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class DataTable {

    @JsonProperty("data")
    private List<List<String>> data;
    @JsonProperty("columns")
    private List<Columns> columns;

    public DataTable() {
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public List<Columns> getColumns() {
        return columns;
    }

    public void setColumns(List<Columns> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "DataTable{" +
                "data=" + data +
                ", columns=" + columns +
                '}';
    }
}
