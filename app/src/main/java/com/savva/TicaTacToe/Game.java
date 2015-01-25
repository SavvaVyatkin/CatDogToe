package com.savva.TicaTacToe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.savva.strategies.Board;
import com.savva.strategies.BoardInterface;

public class Game extends Activity {

    public static final String PLAYER = "com.savva.TicTacToe.Game.PLAYER";
    public static final String PREF_GAME = "catdogtoe";
    public static final String PREF_NAME = "myPrefs";
    public static final int CROSS = 1; // cross
    public static final int CIRCLE = 2; // circle
    protected static final int CONTINUE = -1;
    public static BoardInterface ticTacToeStrategy;
    public static boolean isMessagePrint;
    static int i = 0;
    static int j = 0;
    private int[] grid = new int[3 * 3];
    private int player;
    private int opponent;
    private int curMove;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setGrid();
        // who will be first
        int temp = getIntent().getIntExtra(PLAYER, CIRCLE);

        // if continue game
        if (temp == -1) {
            getSavedState();
            if (curMove == opponent) {
                computerMove();
            }
            // removing old preferences
            SharedPreferences preferences = getSharedPreferences(PREF_GAME, 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();

        }
        // normal mode
        else {
            if (temp == 0) {
                opponent = CROSS;
                player = CIRCLE;
            } else {
                player = CROSS;
                opponent = CIRCLE;
            }
            // if computer is first
            if (opponent == 1) {
                computerMove();
            }
        }
        gameView = new GameView(this);
        setContentView(gameView);
        gameView.requestFocus();

        // If the activity is restarted, do a continue next time
        getIntent().putExtra(PLAYER, CONTINUE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the current GRID if not full
        if (checkGameFinished() == 0) {
            SharedPreferences settings = getSharedPreferences(PREF_GAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_NAME, setSavedState());
            editor.commit();
        }
    }

    /**
     * set data from previous unfinished game
     */
    private void getSavedState() {
        String puz;
        String def = "000" + "000" + "000" + "121";
        SharedPreferences settings = getSharedPreferences(PREF_GAME, 0);
        puz = settings.getString(PREF_NAME, def);

        for (int i = 0; i < puz.length(); i++) {
            if (i == puz.length() - 1)
                curMove = puz.charAt(i) - '0';
            if (i == puz.length() - 2)
                opponent = puz.charAt(i) - '0';
            if (i == puz.length() - 3)
                player = puz.charAt(i) - '0';
            if (i < puz.length() - 3)
                grid[i] = puz.charAt(i) - '0';
        }
    }

    /**
     * Convert an array into a grid string
     */
    private String setSavedState() {
        StringBuilder buf = new StringBuilder();
        for (int element : grid) {
            buf.append(element);
        }
        buf.append(player);
        Log.v("before save", "t" + player);
        buf.append(opponent);
        Log.v("before save", "t" + opponent);
        buf.append(curMove);
        Log.v("before save", "t" + curMove);
        return buf.toString();
    }

    /**
     * set grid in 0 values, beginning state
     */
    public void setGrid() {
        for (int i = 0; i < grid.length; i++) {
            grid[i] = 0;
        }
// ####
        ticTacToeStrategy.clear();
        isMessagePrint = false;
        if (opponent == 1) {
            computerMove();
        }
    }

    /**
     * get value from grid on specified position
     *
     * @param x - column
     * @param y - string
     * @return 0 if values is wrong
     */
    public int getGridPosition(int x, int y) {
        if (y * 3 + x < grid.length)
            return grid[y * 3 + x];
        else
            return 0;
    }

    /**
     * set value from grid to specified value
     *
     * @param x - column
     * @param y - string
     * @return true if is succeeded, and false otherwise
     */
    public boolean setGridPosition(int x, int y, int value) {
        if (y * 3 + x < grid.length)
            if (grid[y * 3 + x] == 0) {
                grid[y * 3 + x] = value;
                return true;
            }
        return false;
    }

    public int getPlayer() {
        return player;
    }

    public void setCurMove(int mov) {
        curMove = mov;
    }

    public int getOpponent() {
        return opponent;
    }

    /**
     * @return 0 - game continuing 1 - won cross, 2 - won circle, 3 - drawn game
     */
    public int checkGameFinished() {
        ticTacToeStrategy.setBoardFromGrid(grid);
        int status = ticTacToeStrategy.isWinInt();
        if (status == 0) {
            if(ticTacToeStrategy.isBoardFull()) {
                status = 3;
                printMessage(status);
            }
        } else
            printMessage(status);
        return status;
    }

    private void printMessage(int status) {
        if (isMessagePrint) return;
        String message = "Draw!";
        if (status == 1)
            if (getPlayer() == 1)
                message = "You Win!";
            else message = "You Lost!";
        if (status == 2)
            if (getPlayer() == 2)
                message = "You Win!";
            else message = "You Lost!";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        isMessagePrint = true;
    }
    /**
     * AI move
     */
    public void computerMove() {
        String O = Board.O;
        String X = Board.X;
        if (opponent == 1) {
            O = Board.X;
            X = Board.O;
        }
        int selected = -1;
        ticTacToeStrategy.setBoardFromGrid(grid);
        Log.v("move: ", String.valueOf(selected));
        grid[ticTacToeStrategy.getStrategyMove(O, X)] = opponent;
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_MENU) {
            new AlertDialog.Builder(this).setItems(R.array.optionsDialog,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface,
                                            int i) {
                            if (i == 1) {
                                finish();
                            }

                        }
                    }).create().show();
        }
        return super.onKeyDown(keycode, event);
    }

}
