package com.vaadin.testapp.ui.cardlist;

import lombok.Data;

@Data
public class Person {

    private String image;
    private String name;
    private String date;
    private String post;
    private String likes;
    private String comments;
    private String shares;

    public Person() {
    }
}