package com.example.sparshgupta.othello;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by sparshgupta on 31/01/17.
 */

public class CustomButton extends Button {

    boolean isDot = false;

    ArrayList<Integer> rowDueTo = new ArrayList<>();
    ArrayList<Integer> columnDueTo = new ArrayList<>();

    public boolean isDot() {
        return isDot;
    }

    public void setDot(boolean dot) {
        isDot = dot;
    }

    int boxColor = -1;
    int row,column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getBoxColor() {
        return boxColor;
    }

    public void setBoxColor(int boxColor) {
        this.boxColor = boxColor;
    }

    public CustomButton(Context context) {
        super(context);
    }
}
