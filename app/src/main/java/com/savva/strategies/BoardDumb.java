package com.savva.strategies;

import java.util.Random;

public class BoardDumb extends Board {

    // completely random moves

    @Override
    public int move(String o2) {
        if (this.isBoardFull()) return -1;
        int i = 0, j = 0;
        int r;
        Random rn = new Random();
        do {
            r = rn.nextInt(9);
            i = r / 3;
            j = r % 3;
        }
        while (board[i][j] != " ");
        this.board[i][j] = o2;

        return r;
    }

    @Override
    public int getStrategyMove(String o2, String x) {
        if (this.isBoardFull())
            return -1;
        int r = winMove(o2);
        if (r == -1) {
            r = getRandomMove(o2);
        }
        return r;
    }
}
