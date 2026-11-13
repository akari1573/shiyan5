package com.example.shiyan5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiyan5.model.Card;
import com.example.shiyan5.model.PlayingCard;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    public interface OnCardClickListener {
        void onCardClick(int position);
    }

    private final Card[] cards;
    private final OnCardClickListener listener;

    public CardAdapter(Card[] cards, OnCardClickListener listener) {
        this.cards = cards;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cards[position];
        if (card.isMatched()) {
            holder.cardView.setBackgroundResource(R.drawable.card_matched);
            holder.cardView.setText(card.getContents());
            holder.cardView.setTextColor(suitColor(holder, card));
        } else if (card.isFaceUp()) {
            holder.cardView.setBackgroundResource(R.drawable.card_face);
            holder.cardView.setText(card.getContents());
            holder.cardView.setTextColor(suitColor(holder, card));
        } else {
            holder.cardView.setBackgroundResource(R.drawable.card_back);
            holder.cardView.setText("");
        }
        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onCardClick(pos);
            }
        });
    }

    private int suitColor(CardViewHolder holder, Card card) {
        if (card instanceof PlayingCard) {
            PlayingCard playingCard = (PlayingCard) card;
            if ("♥".equals(playingCard.getSuit()) || "♦".equals(playingCard.getSuit())) {
                return ContextCompat.getColor(holder.cardView.getContext(), R.color.card_suit_red);
            }
        }
        return ContextCompat.getColor(holder.cardView.getContext(), R.color.card_suit_black);
    }

    @Override
    public int getItemCount() {
        return cards.length;
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {

        final TextView cardView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_content);
        }
    }
}
