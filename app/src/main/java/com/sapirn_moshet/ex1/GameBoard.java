// ######################################
// #             Puzzle 15              #
// ######################################
// Authors:
// Sapir Nahum
// Moshe Tendler

package com.sapirn_moshet.ex1;

// ######################################
// #              Imports               #
// ######################################
import android.util.Log;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class GameBoard {
    private int size;
    private static final Random RANDOM = new Random();
    private int steps=0;
    private int[][] tiles;
    private int blankPos_row;
    private int blankPos_col;
    private boolean gameOver;

    public GameBoard(int size) {
        this.size = size;
        tiles = new int[size][size];
        gameOver = false;
        newGame();
    }
    public void newGame() {
        this.steps = 0;
        reset();
        shuffle_moves();
        gameOver = false;
    }
    private void reset() {
        for(int i=0; i< size*size;i++)
            tiles[i / 4][i % 4] = i+1;
        tiles[size-1][size-1] = 0;
    }
    private void shuffle_moves() {
        for (int i=0;i<30;i++) {
            getMove();
        }
        this.steps = 0;
    }
    private void getMove() {
        updateBlankPos();
        int[] newItems = new int[4];//maximun 4 legal neigbors to cell
        int i=0;
        if (blankPos_col >= 1) {
            newItems[i++] = tiles[blankPos_row][blankPos_col-1]; //left
        }
        if (blankPos_col < size-1) {
            newItems[i++] = tiles[blankPos_row][blankPos_col+1];//right
        }
        if (blankPos_row > 0) {
            newItems[i++] = tiles[blankPos_row-1][blankPos_col];//up
        }
        if (blankPos_row < size-1) {
            newItems[i++] = tiles[blankPos_row+1][blankPos_col];//down
        }
        int rnd = new Random().nextInt(i); //i keeps the current size of array
        updateMove(newItems[rnd]);
    }
    public void updateBlankPos() {
        int pos_row = 0;
        int pos_col = 0;
        for(int i=0;i<size;i++) {
            for(int j=0;j<size;j++) {
                if(tiles[i][j] == 0) {
                    pos_row = i;
                    pos_col = j;
                    break;
                }
            }
        }
        blankPos_row =  pos_row;
        blankPos_col =  pos_col;
    }
    public void updateMove(int m) {
        int x=0;
        int y=0;
        for(int i=0;i<size;i++) {
            for(int j=0;j<size;j++)
                if(tiles[i][j] == m) {
                    x=i;
                    y=j;
                    break;
                }
        }
        if(x>0 && tiles[x-1][y] == 0) { // up
            swap(x-1,y,x,y);
            return;
        }
        if(y>0 && tiles[x][y-1] == 0) { //left
            swap(x,y-1,x,y);
            return;
        }
        if(y < size-1 && tiles[x][y+1] == 0) { // right
            swap(x,y+1,x,y);
            return;
        }
        if(x <size-1 && tiles[x+1][y] == 0) { //down
            swap(x+1,y,x,y);
            return;
        }
    }
    private void swap(int i, int j,int x, int y) {
        this.steps++;
        int temp = tiles[i][j];
        tiles[i][j]	= tiles[x][y];
        tiles[x][y] = temp;
        gameOver=isWin();
    }
    private boolean isWin() {
        if(tiles[size-1][size-1] !=0) {
            return false;
        }
        for (int i= 0;i<size*size-1;i++) {
            if(tiles[i/4][i%4] != i+1)
                return false;
        }
        return true;
    }
    public boolean isGameOver(){
        return gameOver;
    }
    public int getSteps(){
        return steps;
    }
    public int getVal (int i, int j) {
        return(tiles[i][j]);
    }
}
