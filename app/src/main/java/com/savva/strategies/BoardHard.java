package com.savva.strategies;

public class BoardHard extends Board {

    //checks to see if certain strategies are attempted by the player

    @Override
    public int move(String o) {
        if (this.board[1][1].equals(" ")) {
            this.board[1][1] = o;
            return 4;
        }
        String x;
        if (o == O) x = X;
        else x = O;
        // check cross
        if (getSize() == 3) {
            if ((checkElementTrue(1, 1, o) && ((checkElementTrue(2, 2, x) && checkElementTrue(0, 0, x)) || (checkElementTrue(0, 2, x) && checkElementTrue(2, 0, x)))))
                return 1;
        }
        // check corners 00 02 20 22 with neighbor
        if (checkCorner(0, 0, o) && (checkElementTrue(0, 1, x) || checkElementTrue(1, 0, x))) {
            this.board[0][0] = o;
            return 0;
        }
        if (checkCorner(0, 2, o) && (checkElementTrue(0, 1, x) || checkElementTrue(1, 2, x))) {
            this.board[0][2] = o;
            return 2;
        }
        if (checkCorner(2, 0, o) && (checkElementTrue(1, 0, x) || checkElementTrue(2, 1, x))) {
            this.board[2][0] = o;
            return 6;
        }
        if (checkCorner(2, 2, o) && (checkElementTrue(1, 2, x) || checkElementTrue(2, 1, x))) {
            this.board[2][2] = o;
            return 8;
        }

        // check corners 00 02 20 22
        if (checkCorner(0, 0, o)) {
            this.board[0][0] = o;
            return 0;
        }
        if (checkCorner(0, 2, o)) {
            this.board[0][2] = o;
            return 2;
        }
        if (checkCorner(2, 0, o)) {
            this.board[2][0] = o;
            return 6;
        }
        if (checkCorner(2, 2, o)) {
            this.board[2][2] = o;
            return 8;
        }

        return getRandomMove(o);
    }

    @Override
    public int getStrategyMove(String O, String X) {
        int selected;
        if (getBoardElement(1, 1).equals(" ")) {
            selected = 4;

        } else {
            int m = winMove(O);
            if (m != -1) {
                selected = m;
            } else {
                int mEnemyWin = winEnemyMove(X, O);
                if (mEnemyWin == -1) {
                    selected = move(O);
                } else
                    selected = mEnemyWin;
            }
        }
        return selected;
    }
}
