package com.example.sparshgupta.othello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout mainLayout;
    LinearLayout rows[];
    CustomButton buttons[][];
    public final static int WHITE = 0;
    public final static int BLACK = 1;
    boolean isBlackTurn = true;
    int blackCount = 2, whiteCount = 2;
    int[] blackRow = new int[8];
    int[] blackColumn = new int[8];
    int[] whiteRow = new int[8];
    int[] whiteColumn = new int[8];
    boolean isGameOver;
    int countEnabledButtons = 0;
    TextView t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.layout1);
        setUpGame();
        Button restart = (Button) findViewById(R.id.restart);
        restart.setOnClickListener(this);

    }

    private void setUpGame() {
        rows = new LinearLayout[8];
        buttons = new CustomButton[8][8];
        mainLayout.removeAllViews();
        isGameOver = false;
        mainLayout.removeAllViews();
        blackCount = 2;
        whiteCount = 2;
        t1 = (TextView) findViewById(R.id.scoreDisp1);
        t2 = (TextView) findViewById(R.id.scoreDisp2);
        t1.setText("2");
        t2.setText("2");
        isBlackTurn = true;

        for(int i = 0; i < 8; i++){
            blackRow[i] = blackColumn[i] = whiteRow[i] = whiteColumn[i] = -1;
        }

        //for layouts
        for (int i = 0; i < 8; i++) {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            rows[i].setLayoutParams(p);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);
        }

        //for buttons
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new CustomButton(this);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                p.setMargins(1, 1, 1, 1);
                buttons[i][j].setLayoutParams(p);
                buttons[i][j].setText("");
                buttons[i][j].setRow(i);
                buttons[i][j].setColumn(j);
                buttons[i][j].setEnabled(false);
                buttons[i][j].setBackgroundColor(getResources().getColor(R.color.colorGreen));
                buttons[i][j].setOnClickListener(this);
                rows[i].addView(buttons[i][j]);
            }
        }
        //setting up playble buttons
        buttons[3][3].setBackgroundColor(getResources().getColor(R.color.colorWhite));
        buttons[3][3].setBoxColor(WHITE);
        whiteRow[3] = 3;
        whiteColumn[3] = 3;
        buttons[3][4].setBackgroundColor(getResources().getColor(R.color.colorBlack));
        buttons[3][4].setBoxColor(BLACK);
        blackRow[3] = 3;
        blackColumn[4] = 4;
        buttons[4][3].setBackgroundColor(getResources().getColor(R.color.colorBlack));
        buttons[4][3].setBoxColor(BLACK);
        blackRow[4] = 4;
        blackColumn[3] = 3;
        buttons[4][4].setBackgroundColor(getResources().getColor(R.color.colorWhite));
        buttons[4][4].setBoxColor(WHITE);
        whiteRow[4] = 4;
        whiteColumn[4] = 4;

        enableButtons();
    }

    int[] iterateRow = {-1, -1, -1, 0, 1, 1, 1, 0};
    int[] iterateColumn = {-1, 0, 1, 1, 1, 0, -1, -1};

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.restart){
            setUpGame();
        }else {
            CustomButton b = (CustomButton) view;

            if (isGameOver) {
                return;
            }

            if (b.getBoxColor() == BLACK || b.getBoxColor() == WHITE) {
                b.setEnabled(false);
                return;
            }
            if (isBlackTurn) {
                for (int i = 0; i < b.rowDueTo.size(); i++) {
                    int r = b.rowDueTo.get(i);
                    int c = b.columnDueTo.get(i);
                    //when row equal
                    if (b.getRow() - r == 0) {
                        if (c > b.getColumn()) {
                            while (c - b.getColumn() >= 0) {
                                if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == WHITE) {
                                    buttons[r][c].setBoxColor(BLACK);
                                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                    buttons[r][c].setText("");
                                    blackCount++;
                                    whiteCount--;
                                }
                                c--;
                            }
                            if (b.getBoxColor() != BLACK) {
                                b.setBoxColor(BLACK);
                                b.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                b.setText("");
                                blackCount++;
                            }
                        } else {
                            while (b.getColumn() - c >= 0) {
                                if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == WHITE) {
                                    buttons[r][c].setBoxColor(BLACK);
                                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                    buttons[r][c].setText("");
                                    blackCount++;
                                    whiteCount--;

                                }
                                c++;
                            }
                            if (b.getBoxColor() != BLACK) {
                                b.setBoxColor(BLACK);
                                b.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                b.setText("");
                                blackCount++;
                            }
                        }
                    }
                    //when column equal
                    else if (b.getColumn() - c == 0) {
                        if (r > b.getRow()) {
                            while (r - b.getRow() >= 0) {
                                if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == WHITE) {
                                    buttons[r][c].setBoxColor(BLACK);
                                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                    buttons[r][c].setText("");
                                    blackCount++;
                                    whiteCount--;
                                }
                                r--;
                            }
                            if (b.getBoxColor() != BLACK) {
                                b.setBoxColor(BLACK);
                                b.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                b.setText("");
                                blackCount++;
                            }
                        } else {
                            while (b.getRow() - r >= 0) {
                                if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == WHITE) {
                                    buttons[r][c].setBoxColor(BLACK);
                                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                    buttons[r][c].setText("");
                                    blackCount++;
                                    whiteCount--;

                                }
                                r++;
                            }
                            if (b.getBoxColor() != BLACK) {
                                b.setBoxColor(BLACK);
                                b.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                b.setText("");
                                blackCount++;
                            }
                        }
                    }
                    //when diagonal
                    else if (r > b.getRow() && c > b.getColumn()) {
                        while (r - b.getRow() >= 0 && c - b.getColumn() >= 0) {
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == WHITE) {
                                buttons[r][c].setBoxColor(BLACK);
                                buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                buttons[r][c].setText("");
                                blackCount++;
                                whiteCount--;
                            }
                            r--;
                            c--;
                        }
                        if (b.getBoxColor() != BLACK) {
                            b.setBoxColor(BLACK);
                            b.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                            b.setText("");
                            blackCount++;
                        }
                    } else if (r < b.getRow() && c > b.getColumn()) {
                        while (b.getRow() - r >= 0 && c - b.getColumn() >= 0) {
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == WHITE) {
                                buttons[r][c].setBoxColor(BLACK);
                                buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                buttons[r][c].setText("");
                                blackCount++;
                                whiteCount--;
                            }
                            r++;
                            c--;
                        }
                        if (b.getBoxColor() != BLACK) {
                            b.setBoxColor(BLACK);
                            b.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                            b.setText("");
                            blackCount++;
                        }
                    } else if (r > b.getRow() && c < b.getColumn()) {
                        while (r - b.getRow() >= 0 && b.getColumn() - c >= 0) {
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == WHITE) {
                                buttons[r][c].setBoxColor(BLACK);
                                buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                buttons[r][c].setText("");
                                blackCount++;
                                whiteCount--;
                            }
                            r--;
                            c++;
                        }
                        if (b.getBoxColor() != BLACK) {
                            b.setBoxColor(BLACK);
                            b.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                            b.setText("");
                            blackCount++;
                        }
                    } else if (r < b.getRow() && c < b.getColumn()) {
                        while (b.getRow() - r >= 0 && b.getColumn() - c >= 0) {
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == WHITE) {
                                buttons[r][c].setBoxColor(BLACK);
                                buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorBlack));
                                buttons[r][c].setText("");
                                blackCount++;
                                whiteCount--;
                            }
                            r++;
                            c++;
                        }
                        if (b.getBoxColor() != BLACK) {
                            b.setBoxColor(BLACK);
                            b.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                            b.setText("");
                            blackCount++;
                        }
                    }
                }
            } else {
                for (int i = 0; i < b.rowDueTo.size(); i++) {
                    int r = b.rowDueTo.get(i);
                    int c = b.columnDueTo.get(i);
                    //when row equal
                    if (b.getRow() - r == 0) {
                        if (c > b.getColumn()) {
                            while (c - b.getColumn() >= 0) {
                                if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == BLACK) {
                                    buttons[r][c].setBoxColor(WHITE);
                                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                    buttons[r][c].setText("");
                                    blackCount--;
                                    whiteCount++;
                                }
                                c--;
                            }
                            if (b.getBoxColor() != WHITE) {
                                b.setBoxColor(WHITE);
                                b.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                b.setText("");
                                whiteCount++;
                            }
                        } else {
                            while (b.getColumn() - c >= 0) {
                                if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == BLACK) {
                                    buttons[r][c].setBoxColor(WHITE);
                                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                    buttons[r][c].setText("");
                                    whiteCount++;
                                    blackCount--;
                                }
                                c++;
                            }
                            if (b.getBoxColor() != WHITE) {
                                b.setBoxColor(WHITE);
                                b.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                b.setText("");
                                whiteCount++;
                            }
                        }
                    }
                    //when column equal
                    else if (b.getColumn() - c == 0) {
                        if (r > b.getRow()) {
                            while (r - b.getRow() >= 0) {
                                if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == BLACK) {
                                    buttons[r][c].setBoxColor(WHITE);
                                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                    buttons[r][c].setText("");
                                    blackCount--;
                                    whiteCount++;
                                }
                                r--;
                            }
                            if (b.getBoxColor() != WHITE) {
                                b.setBoxColor(WHITE);
                                b.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                b.setText("");
                                whiteCount++;
                            }
                        } else {
                            while (b.getRow() - r >= 0) {
                                if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == BLACK) {
                                    buttons[r][c].setBoxColor(WHITE);
                                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                    buttons[r][c].setText("");
                                    blackCount--;
                                    whiteCount++;

                                }
                                r++;
                            }
                            if (b.getBoxColor() != WHITE) {
                                b.setBoxColor(WHITE);
                                b.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                b.setText("");
                                whiteCount++;
                            }
                        }
                    }
                    //diagonal
                    else if (r > b.getRow() && c > b.getColumn()) {
                        while (r - b.getRow() >= 0 && c - b.getColumn() >= 0) {
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == BLACK) {
                                buttons[r][c].setBoxColor(WHITE);
                                buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                buttons[r][c].setText("");
                                blackCount--;
                                whiteCount++;
                            }
                            r--;
                            c--;
                        }
                        if (b.getBoxColor() != WHITE) {
                            b.setBoxColor(WHITE);
                            b.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            b.setText("");
                            whiteCount++;
                        }
                    } else if (r < b.getRow() && c > b.getColumn()) {
                        while (b.getRow() - r >= 0 && c - b.getColumn() >= 0) {
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == BLACK) {
                                buttons[r][c].setBoxColor(WHITE);
                                buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                buttons[r][c].setText("");
                                blackCount--;
                                whiteCount++;
                            }
                            r++;
                            c--;
                        }
                        if (b.getBoxColor() != WHITE) {
                            b.setBoxColor(WHITE);
                            b.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            b.setText("");
                            whiteCount++;
                        }
                    } else if (r > b.getRow() && c < b.getColumn()) {
                        while (r - b.getRow() >= 0 && b.getColumn() - c >= 0) {
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == BLACK) {
                                buttons[r][c].setBoxColor(WHITE);
                                buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                buttons[r][c].setText("");
                                blackCount--;
                                whiteCount++;
                            }
                            r--;
                            c++;
                        }
                        if (b.getBoxColor() != WHITE) {
                            b.setBoxColor(WHITE);
                            b.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            b.setText("");
                            whiteCount++;
                        }
                    } else if (r < b.getRow() && c < b.getColumn()) {
                        while (b.getRow() - r >= 0 && b.getColumn() - c >= 0) {
                            if (r >= 0 && r < 8 && c >= 0 && c < 8 && buttons[r][c].getBoxColor() == BLACK) {
                                buttons[r][c].setBoxColor(WHITE);
                                buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                buttons[r][c].setText("");
                                blackCount--;
                                whiteCount++;
                            }
                            r++;
                            c++;
                        }
                        if (b.getBoxColor() != WHITE) {
                            b.setBoxColor(WHITE);
                            b.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            b.setText("");
                            whiteCount++;
                        }
                    }
                }
            }
            isBlackTurn = !isBlackTurn;
            disableButtons();
            int a = enableButtons();
            t1 = (TextView) findViewById(R.id.scoreDisp1);
            t2 = (TextView) findViewById(R.id.scoreDisp2);
            t1.setText(blackCount + "");
            t2.setText(whiteCount + "");
            if (a == 0 && whiteCount + blackCount < 64) {
                if (isBlackTurn) {
                    Toast.makeText(this, "Black has no valid moves left!!! White will continue until Black can have any valid moves.", Toast.LENGTH_SHORT).show();
                    isBlackTurn = false;
                    int c = enableButtons();
                    if (c == 0) {
                        isGameOver = true;
                    }
                } else {
                    Toast.makeText(this, "White has no valid moves left!!! Black will continue until White can have any valid moves.", Toast.LENGTH_SHORT).show();
                    isBlackTurn = true;
                    int c = enableButtons();
                    if (c == 0) {
                        isGameOver = true;
                    }
                }
            }
            if (whiteCount + blackCount == 64 || whiteCount == 0 || blackCount == 0) {
                Toast.makeText(this, "Game over", Toast.LENGTH_SHORT).show();
                isGameOver = true;
            }
        }
    }

    private void disableButtons() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(buttons[i][j].getBoxColor() == -1 && buttons[i][j].isDot){
                    buttons[i][j].setEnabled(false);
                    buttons[i][j].setText("");
                    buttons[i][j].isDot = false;
                    buttons[i][j].rowDueTo.clear();
                    buttons[i][j].columnDueTo.clear();

                }
            }
        }
        countEnabledButtons = 0;
    }

    private int enableButtons() {

        if(isBlackTurn){
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(buttons[i][j].getBoxColor() == -1) {
                        for (int k = 0; k < 8; k++) {
                            boolean flag = true;
                            if (i + iterateRow[k] >= 0 && i + iterateRow[k] < 8 && j + iterateColumn[k] >= 0 && j + iterateColumn[k] < 8) {
                                if (buttons[i + iterateRow[k]][j + iterateColumn[k]].getBoxColor() == WHITE) {
                                    int r = i + iterateRow[k];
                                    int c = j + iterateColumn[k];
                                    while (buttons[r][c].getBoxColor() == WHITE) {
                                        r += iterateRow[k];
                                        c += iterateColumn[k];
                                        if (r < 0 || r > 7 || c < 0 || c > 7 || buttons[r][c].getBoxColor() == -1) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if (flag && buttons[r][c].getBoxColor() == BLACK) {
                                        buttons[i][j].rowDueTo.add(r);
                                        buttons[i][j].columnDueTo.add(c);
                                        buttons[i][j].setEnabled(true);
                                        buttons[i][j].setText("*");
                                        buttons[i][j].setDot(true);
                                        buttons[i][j].setTextColor(getResources().getColor(R.color.colorBlack));
                                        countEnabledButtons++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(buttons[i][j].getBoxColor() == -1) {
                        for (int k = 0; k < 8; k++) {
                            boolean flag = true;
                            if (i + iterateRow[k] >= 0 && i + iterateRow[k] < 8 && j + iterateColumn[k] >= 0 && j + iterateColumn[k] < 8) {
                                if (buttons[i + iterateRow[k]][j + iterateColumn[k]].getBoxColor() == BLACK) {
                                    int r = i + iterateRow[k];
                                    int c = j + iterateColumn[k];
                                    while (buttons[r][c].getBoxColor() == BLACK) {
                                        r += iterateRow[k];
                                        c += iterateColumn[k];
                                        if (r < 0 || r > 7 || c < 0 || c > 7 || buttons[r][c].getBoxColor() == -1) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if (flag && buttons[r][c].getBoxColor() == WHITE) {
                                        buttons[i][j].rowDueTo.add(r);
                                        buttons[i][j].columnDueTo.add(c);
                                        buttons[i][j].setEnabled(true);
                                        buttons[i][j].setText("*");
                                        buttons[i][j].setDot(true);
                                        buttons[i][j].setTextColor(getResources().getColor(R.color.colorWhite));
                                        countEnabledButtons++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return countEnabledButtons;
    }
}
