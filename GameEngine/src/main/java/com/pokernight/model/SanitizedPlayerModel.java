package com.pokernight.model;

import pokernight.model.Card;

import java.util.Set;

/**
 * Created by cdufresne on 1/2/16.
 */
public class SanitizedPlayerModel {
    private String name;
    private float chips;
    private Set<Card> visibleCards;
    private int numberOfDownCards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
