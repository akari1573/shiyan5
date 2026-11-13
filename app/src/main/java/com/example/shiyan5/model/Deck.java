package com.example.shiyan5.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Deck {

    protected final List<Card> cards = new ArrayList<>();
    private final Random random = new Random();

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card drawRandomCard() {
        if (cards.isEmpty()) {
            return null;
        }
        int index = random.nextInt(cards.size());
        Card card = cards.get(index);
        cards.remove(index);
        return card;
    }

    public int size() {
        return cards.size();
    }
}
