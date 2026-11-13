package com.example.shiyan5.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardMatchingGame {

    public static final int MATCH_MODE_TWO = 2;
    public static final int MATCH_MODE_THREE = 3;
    public static final int MISMATCH_PENALTY = 2;
    public static final int COST_TO_CHOOSE = 1;

    private final Card[] cards;
    private final int matchMode;
    private int score = 0;

    public CardMatchingGame(int cardCount, Deck deck) {
        this(cardCount, deck, MATCH_MODE_THREE);
    }

    public CardMatchingGame(int cardCount, Deck deck, int matchMode) {
        this.matchMode = matchMode;
        cards = new Card[cardCount];
        for (int i = 0; i < cardCount; i++) {
            Card card = deck.drawRandomCard();
            if (card != null) {
                cards[i] = card;
            }
        }
    }

    public Card[] getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public int getMatchMode() {
        return matchMode;
    }

    public Card cardAtIndex(int index) {
        if (index < 0 || index >= cards.length) {
            return null;
        }
        return cards[index];
    }

    public void chooseCardAtIndex(int index) {
        Card card = cardAtIndex(index);
        if (card == null || card.isMatched()) {
            return;
        }
        if (card.isFaceUp()) {
            card.setFaceUp(false);
            return;
        }

        List<Card> faceUpUnmatched = new ArrayList<>();
        for (Card other : cards) {
            if (other != null && other.isFaceUp() && !other.isMatched()) {
                faceUpUnmatched.add(other);
            }
        }

        if (faceUpUnmatched.size() == matchMode - 1) {
            Card[] others = faceUpUnmatched.toArray(new Card[0]);
            int matchScore = card.match(others);
            card.setFaceUp(true);
            score -= COST_TO_CHOOSE;
            if (matchScore > 0) {
                score += matchScore;
                card.setMatched(true);
                for (Card other : others) {
                    other.setMatched(true);
                }
            } else {
                score -= MISMATCH_PENALTY;
                for (Card other : others) {
                    other.setFaceUp(false);
                }
            }
        } else {
            card.setFaceUp(true);
            score -= COST_TO_CHOOSE;
        }
    }

    public void reset() {
        score = 0;
        Collections.shuffle(Arrays.asList(cards));
        for (Card card : cards) {
            if (card != null) {
                card.setFaceUp(false);
                card.setMatched(false);
            }
        }
    }

    public boolean isFinished() {
        for (Card card : cards) {
            if (card != null && !card.isMatched()) {
                return false;
            }
        }
        return true;
    }
}
