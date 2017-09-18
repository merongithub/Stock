package com.stock.price.model;

public class Meta {
    private String next_cursor_id;

    public Meta(){

    }

    public Meta(String next_cursor_id) {
        this.next_cursor_id = next_cursor_id;
    }

    public String getNext_cursor_id() {
        return next_cursor_id;
    }

    public void setNext_cursor_id(String next_cursor_id) {
        this.next_cursor_id = next_cursor_id;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "next_cursor_id='" + next_cursor_id + '\'' +
                '}';
    }
}
