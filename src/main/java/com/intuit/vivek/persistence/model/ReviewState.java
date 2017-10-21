package com.intuit.vivek.persistence.model;

/**
 * Created by vvenugopal on 10/21/17.
 */
public enum ReviewState {

    PENDING(1),
    APPROVED(2),
    REJECTED(3),
    ;

    private int value;

    ReviewState(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ReviewState parse(int value) {
        for (ReviewState reviewState : ReviewState.values()) {
            if (reviewState.getValue() == value) {
                return reviewState;
            }
        }
        return null;
    }

}
