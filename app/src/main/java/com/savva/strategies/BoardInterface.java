package com.savva.strategies;

public interface BoardInterface {

    /**
     * @param o2 -X or O
     * @return- true if winning move is found
     */

    public abstract int winMove(String o2);

    /**
     * @param x2 what the AI uses
     * @param o2 what the player uses
     * @return
     */
    public abstract int winEnemyMove(String x2, String o2);

    /**
     * @return false if board is not full
     */
    public abstract boolean isBoardFull();

    /**
     * @return true if winning move is found
     */
    public boolean isWin();

    public int isWinInt();

    /**
     * @return gives results to the original board
     */
    public abstract String[][] getBoard();

    public abstract void setBoard(String[][] board);

    public abstract String getBoardElement(int i, int j);

    public abstract void setBoardElement(String x2, int i, int j);

    public abstract int move(String o2);

    public abstract String toString();

    public abstract void clear();

    public abstract void setBoardFromGrid(int[] grid);

    int getStrategyMove(String O, String X);

}