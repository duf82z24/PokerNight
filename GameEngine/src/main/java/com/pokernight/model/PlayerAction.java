package com.pokernight.model;

import pokernight.exception.IllegalBetException;

/**
 * Created by cdufresne on 1/2/16.
 */
public class PlayerAction {
    public enum Action {
        ANTE,
        CHECK,
        CALL,
        RAISE,
        FOLD
    }

    public final Action action;
    public final int amount;

    public PlayerAction(Action action) {
        amount = 0;
        this.action = action;
        verifyAction();
    }

    public PlayerAction(Action action, int amount) {
        this.amount = amount;
        this.action = action;
        verifyAction();
    }

    private void verifyAction() {
        switch (action) {
            case ANTE:
            case CALL:
            case RAISE:
                verifyAmountGtZero();
                break;
            case FOLD:
            case CHECK:
                verifyAmountEqZero();
                break;
            default:
                throw new IllegalBetException("Illegal bet type");
        }
    }

    private void verifyAmountGtZero() {
        if (amount <= 0) {
            throw new IllegalBetException("Cannot ante/bet/raise 0");
        }
    }

    private void verifyAmountEqZero() {
        if (amount != 0) {
            throw new IllegalBetException(("Cannot check/fold an amount"));
        }
    }
}
