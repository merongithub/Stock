package com.stock.price.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuandliResponse {

  @JsonProperty("datatable")
    private DataTable datatable;
    @JsonProperty("meta")
    private Meta meta;
    public QuandliResponse(){

    }

    public DataTable getDatatable() {
        return datatable;
    }

    public void setDatatable(DataTable datatable) {
        this.datatable = datatable;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "QuandliResponse{" +
                "datatable=" + datatable +
                ", meta=" + meta +
                '}';
    }
}
