package com.pokernight.model;

import pokernight.model.Card;
import pokernight.model.DeckModel;

import java.util.ArrayList;
import java.util.List;

public class PokerTableModel {
    private List<PlayerModel> players;
    private DeckModel deck;

    PokerTableModel(int players) {
        this.players = new ArrayList<PlayerModel>(players);
        this.deck = new DeckModel();
    }

    public void dealCardToTable(int x, int y, Card card) {
        //TODO: Stub
    }
}
