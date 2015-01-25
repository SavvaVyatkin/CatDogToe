package com.savva.TicaTacToe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.savva.strategies.Board;
import com.savva.strategies.BoardDumb;
import com.savva.strategies.BoardHard;

public class TicTacToe extends Activity implements OnClickListener {

    private View easyButton;
    private View exitButton;
    private View mediumButton;
    private View hardButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        easyButton = findViewById(R.id.EasyDifficulty);
        easyButton.setOnClickListener(this);
        mediumButton = findViewById(R.id.MediumDifficulty);
        mediumButton.setOnClickListener(this);
        hardButton = findViewById(R.id.HardDifficulty);
        hardButton.setOnClickListener(this);

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.MediumDifficulty:
                Game.ticTacToeStrategy = new Board();
                newGameDialog();
                break;
            case R.id.HardDifficulty:
                Game.ticTacToeStrategy = new BoardHard();
                newGameDialog();
                break;
            case R.id.EasyDifficulty:
                Game.ticTacToeStrategy = new BoardDumb();
                newGameDialog();
                break;
            case R.id.exitButton:
                finish();
                break;
        }
    }

    private void newGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.whosFirstLabel)
                .setItems(R.array.whosFirst,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                startGame(i);
                            }
                        }).show();
    }

    /**
     * @param i - 0 AI moves first, 1 User moves first
     */
    private void startGame(int i) {
        Intent intent = new Intent(TicTacToe.this, Game.class);
        intent.putExtra(Game.PLAYER, i);
        startActivity(intent);
    }
}
