package com.savva.TicaTacToe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {

    private final Game game;
    private Bitmap backgroundGrid;
    private Bitmap bitmapCross;
    private Bitmap bitmapCircle;
    private int aiWins;
    private int userWins;
    private int draws;
    private String youLabel;
    private String aiLabel;

    public GameView(Context context) {
        super(context);
        this.game = (Game) context;

        aiWins = 0;
        userWins = 0;
        draws = 0;
        youLabel = game.getString(R.string.youLabel);
        aiLabel = game.getString(R.string.aiLabel);

        bitmapCross = BitmapFactory.decodeResource(game.getResources(),
                R.drawable.cross);
        bitmapCircle = BitmapFactory.decodeResource(game.getResources(),
                R.drawable.circle);
        backgroundGrid = BitmapFactory.decodeResource(game.getResources(),
                R.drawable.grid);

        setEnabled(true);
        setClickable(true);
    }

    protected void onDraw(Canvas canvas) {
        Paint background = new Paint();
        Paint text = new Paint();
        background.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);
        canvas.drawBitmap(backgroundGrid, 10, 0, null);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                int pos = game.getGridPosition(i, j);
                if (pos == 1) {
                    canvas.drawBitmap(bitmapCross, i
                            * (bitmapCross.getWidth() + 20), j
                            * (bitmapCross.getHeight() + 15), null);
                }
                if (pos == 2) {
                    canvas.drawBitmap(bitmapCircle, i
                            * (bitmapCircle.getWidth() + 20), j
                            * (bitmapCircle.getHeight() + 15), null);
                }
            }
        //current rank
        text.setAntiAlias(true);
        text.setColor(Color.BLACK);
        text.setTextSize(30);
        canvas.drawText(youLabel, 30, backgroundGrid.getHeight() + 50, text);
        canvas.drawText(String.valueOf(userWins), 210, backgroundGrid.getHeight() + 50, text);

        canvas.drawText(aiLabel, 30, backgroundGrid.getHeight() + 90, text);
        canvas.drawText(String.valueOf(aiWins), 210, backgroundGrid.getHeight() + 90, text);

        canvas.drawText("Draw", 30, backgroundGrid.getHeight() + 130, text);
        canvas.drawText(String.valueOf(draws), 210, backgroundGrid.getHeight() + 130, text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int winner = -1;
        boolean isValidMove = false;

        if (action == MotionEvent.ACTION_DOWN) {
            return true;

        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            // if game over reset grid, and begin new game
            if (game.checkGameFinished() != 0) {
                game.setGrid();
                invalidate();
                return false;
            }

            int bgWidth = (backgroundGrid.getWidth() + 10) / 3;
            int bgHeight = backgroundGrid.getHeight() / 3;

            x = x / bgWidth;
            y = y / bgHeight;
            Log.v("test x:y", String.valueOf(x) + " : " + String.valueOf(y));

            if (x < 3 && x >= 0 && y < 3 && y >= 0) {
                isValidMove = game.setGridPosition(x, y, game.getPlayer());
            }
            if (isValidMove == true) {
                winner = game.checkGameFinished();

                if (winner == 0) {
                    game.setCurMove(game.getOpponent());
                    game.computerMove();
                }
                winner = game.checkGameFinished();

                if (winner == 1) {
                    if (game.getPlayer() == 1) {
                        userWins++;
                    } else {
                        aiWins++;
                    }
                }
                if (winner == 2) {
                    if (game.getPlayer() == 2) {
                        userWins++;
                    } else {
                        aiWins++;
                    }
                } else if (winner == 3) {
                    draws++;
                }
            }
        }
        game.setCurMove(game.getPlayer());
        invalidate();
        return false;
    }

}
