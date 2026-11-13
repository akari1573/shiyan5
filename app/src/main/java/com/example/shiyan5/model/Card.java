package com.example.shiyan5.model;

public abstract class Card {

    private boolean faceUp = false;
    private boolean matched = false;

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public abstract int match(Card[] otherCards);

    public abstract String getContents();
}
