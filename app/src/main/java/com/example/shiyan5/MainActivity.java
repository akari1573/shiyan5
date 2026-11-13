package com.example.shiyan5;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiyan5.model.CardMatchingGame;
import com.example.shiyan5.model.PlayingCardDeck;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private static final int PORTRAIT_CARD_COUNT = 24;
    private static final int LANDSCAPE_CARD_COUNT = 30;
    private static final int PORTRAIT_COLUMN_COUNT = 4;
    private static final int LANDSCAPE_COLUMN_COUNT = 6;

    private RecyclerView recyclerView;
    private TextView scoreLabel;
    private CardAdapter adapter;
    private CardMatchingGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        scoreLabel = findViewById(R.id.score_label);

        boolean landscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        int cardCount = landscape ? LANDSCAPE_CARD_COUNT : PORTRAIT_CARD_COUNT;
        int columnCount = landscape ? LANDSCAPE_COLUMN_COUNT : PORTRAIT_COLUMN_COUNT;

        game = new CardMatchingGame(cardCount, new PlayingCardDeck());
        adapter = new CardAdapter(game.getCards(), position -> {
            game.chooseCardAtIndex(position);
            adapter.notifyDataSetChanged();
            updateScore();
            if (game.isFinished()) {
                showGameFinishedDialog();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        updateScore();
    }

    private void updateScore() {
        scoreLabel.setText(getString(R.string.score_format, game.getScore()));
    }

    private void restartGame() {
        game.reset();
        adapter.notifyDataSetChanged();
        updateScore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            showAboutDialog();
            return true;
        } else if (id == R.id.action_restart) {
            restartGame();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAboutDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.menu_about)
                .setMessage(R.string.about_message)
                .setPositiveButton(R.string.dialog_ok, null)
                .show();
    }

    private void showGameFinishedDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.game_finished_title)
                .setMessage(getString(R.string.game_finished_message, game.getScore()))
                .setPositiveButton(R.string.play_again, (dialog, which) -> restartGame())
                .setNegativeButton(R.string.dialog_finish, null)
                .show();
    }
}
