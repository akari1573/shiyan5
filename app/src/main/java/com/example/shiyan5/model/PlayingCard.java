package com.example.shiyan5.model;

public class PlayingCard extends Card {

    public static final int MATCH_SCORE_SAME_RANK = 4;
    public static final int MATCH_SCORE_SAME_SUIT = 1;
    public static final int MATCH_SCORE_THREE_SAME_RANK = 12;
    public static final int MATCH_SCORE_THREE_SAME_SUIT = 6;

    private final String suit;
    private final String rank;

    public PlayingCard(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String getContents() {
        return rank + suit;
    }

    @Override
    public int match(Card[] otherCards) {
        int score = 0;
        if (otherCards == null || otherCards.length == 0) {
            return score;
        }
        if (otherCards.length == 1) {
            PlayingCard other = (PlayingCard) otherCards[0];
            if (rank.equals(other.rank)) {
                score = MATCH_SCORE_SAME_RANK;
            } else if (suit.equals(other.suit)) {
                score = MATCH_SCORE_SAME_SUIT;
            }
        } else if (otherCards.length == 2) {
            PlayingCard first = (PlayingCard) otherCards[0];
            PlayingCard second = (PlayingCard) otherCards[1];
            if (rank.equals(first.rank) && rank.equals(second.rank)) {
                score = MATCH_SCORE_THREE_SAME_RANK;
            } else if (suit.equals(first.suit) && suit.equals(second.suit)) {
                score = MATCH_SCORE_THREE_SAME_SUIT;
            }
        }
        return score;
    }
}
