package com.example.shiyan5.model;

public class PlayingCardDeck extends Deck {

    public static final String[] SUITS = {"♠", "♥", "♣", "♦"};
    public static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public PlayingCardDeck() {
        for (int r = 0; r < RANKS.length; r++) {
            for (int s = 0; s < 3; s++) {
                addCard(new PlayingCard(SUITS[(r + s) % SUITS.length], RANKS[r]));
            }
        }
    }
}
