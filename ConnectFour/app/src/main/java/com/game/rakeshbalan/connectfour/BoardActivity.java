package com.game.rakeshbalan.connectfour;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by rakeshbalan on 11/11/2015.
 */
public class BoardActivity extends ActionBarActivity {

    BoardAdapter boardAdapter;
    GridView gridView;
    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;
    Button buttonE;
    Button buttonF;
    Button buttonG;
    boolean myTurn = true;
    HashMap<Integer,String> keyValuePair = null;
    ArrayList<Integer> keys = null;
    int location = 0;
    int width = 7;
    int height = 6;
    int winLength = 4;
    static Random random = new Random();
    Board board = new Board(height, width, winLength);
    Ai ai = new Ai(board);
    int column = 0;
    AlertDialog.Builder builder;
    boolean checkFull = false;
    private View mView;
    private Dialog mDialog;
    private LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setIcon(R.drawable.app_icon);

        keyValuePair = new HashMap<Integer, String>();

        gridView = (GridView) findViewById(R.id.gridViewBoard);
        boardAdapter = new BoardAdapter(BoardActivity.this, R.layout.activity_image, keyValuePair);
        gridView.setAdapter(boardAdapter);
        boardAdapter.notifyDataSetChanged();

        boolean initialTurn = getRandomBoolean();
        if (!initialTurn) {
            int aiColumn = ai.makeTurn();
            myTurn = false;
            int[] arrayAi = getArray(aiColumn);
            aPlay(arrayAi, myTurn, buttonA);
            if (board.hasWinner() || board.isTie()) {
                decision();
            }
        }


        buttonA = (Button) findViewById(R.id.buttonA);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] arrayA = getArray(0);
                myTurn = true;
                aPlay(arrayA, myTurn, buttonA);

                column = 0;
                if (board.isValidMove(column) || checkFull != true)
                {
                    board.makeMovePlayer(column);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }

                    int aiColumn = ai.makeTurn();
                    myTurn = false;
                    int[] arrayAi = getArray(aiColumn);
                    aPlay(arrayAi, myTurn, buttonA);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }
                }
                else
                {
                    checkFull = false;
                    Toast.makeText(BoardActivity.this, "Invalid Move!", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonB = (Button) findViewById(R.id.buttonB);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] arrayB = getArray(1);
                myTurn = true;
                aPlay(arrayB, myTurn, buttonB);

                column = 1;
                if (board.isValidMove(column) || checkFull != true)
                {
                    board.makeMovePlayer(column);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }

                    int aiColumn = ai.makeTurn();
                    myTurn = false;
                    int[] arrayAi = getArray(aiColumn);
                    aPlay(arrayAi, myTurn, buttonB);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }
                }
                else
                {
                    checkFull = false;
                    Toast.makeText(BoardActivity.this, "Invalid Move!", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonC = (Button) findViewById(R.id.buttonC);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] arrayC = getArray(2);
                myTurn = true;
                aPlay(arrayC, myTurn, buttonC);

                column = 2;
                if (board.isValidMove(column) || checkFull != true)
                {
                    board.makeMovePlayer(column);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }

                    int aiColumn = ai.makeTurn();
                    myTurn = false;
                    int[] arrayAi = getArray(aiColumn);
                    aPlay(arrayAi, myTurn, buttonC);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }
                }
                else
                {
                    checkFull = false;
                    Toast.makeText(BoardActivity.this, "Invalid Move!", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonD = (Button) findViewById(R.id.buttonD);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] arrayD = getArray(3);
                myTurn = true;
                aPlay(arrayD, myTurn, buttonD);

                column = 3;
                if (board.isValidMove(column) || checkFull != true)
                {
                    board.makeMovePlayer(column);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }

                    int aiColumn = ai.makeTurn();
                    myTurn = false;
                    int[] arrayAi = getArray(aiColumn);
                    aPlay(arrayAi, myTurn, buttonD);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }
                }
                else
                {
                    checkFull = false;
                    Toast.makeText(BoardActivity.this, "Invalid Move!", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonE = (Button) findViewById(R.id.buttonE);
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] arrayE = getArray(4);
                myTurn = true;
                aPlay(arrayE, myTurn, buttonE);

                column = 4;
                if (board.isValidMove(column) || checkFull != true)
                {
                    board.makeMovePlayer(column);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }

                    int aiColumn = ai.makeTurn();
                    myTurn = false;
                    int[] arrayAi = getArray(aiColumn);
                    aPlay(arrayAi, myTurn, buttonE);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }
                }
                else
                {
                    checkFull = false;
                    Toast.makeText(BoardActivity.this, "Invalid Move!", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonF = (Button) findViewById(R.id.buttonF);
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] arrayF = getArray(5);
                myTurn = true;
                aPlay(arrayF, myTurn, buttonF);

                column = 5;
                if (board.isValidMove(column) || checkFull != true)
                {
                    board.makeMovePlayer(column);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }

                    int aiColumn = ai.makeTurn();
                    myTurn = false;
                    int[] arrayAi = getArray(aiColumn);
                    aPlay(arrayAi, myTurn, buttonF);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }
                }
                else
                {
                    checkFull = false;
                    Toast.makeText(BoardActivity.this, "Invalid Move!", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonG = (Button) findViewById(R.id.buttonG);
        buttonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] arrayG = getArray(6);
                myTurn = true;
                aPlay(arrayG, myTurn, buttonG);

                column = 6;
                if (board.isValidMove(column) || checkFull != true)
                {
                    board.makeMovePlayer(column);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }

                    int aiColumn = ai.makeTurn();
                    myTurn = false;
                    int[] arrayAi = getArray(aiColumn);
                    aPlay(arrayAi, myTurn, buttonG);

                    if (board.hasWinner() || board.isTie()) {
                        decision();
                    }
                }
                else
                {
                    checkFull = false;
                    Toast.makeText(BoardActivity.this, "Invalid Move!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public int[] getArray(int columns) {
        int[] array = null;
        switch(columns) {
            case 0:
                array = new int[]{35, 28, 21, 14, 7, 0};
                break;
            case 1:
                array = new int[]{36, 29, 22, 15, 8, 1};
                break;
            case 2:
                array = new int[]{37, 30, 23, 16, 9, 2};
                break;
            case 3:
                array = new int[]{38, 31, 24, 17, 10, 3};
                break;
            case 4:
                array = new int[]{39, 32, 25, 18, 11, 4};
                break;
            case 5:
                array = new int[]{40, 33, 26, 19, 12, 5};
                break;
            case 6:
                array = new int[]{41, 34, 27, 20, 13, 6};
                break;
        }

        return array;
    }

    public void decision()
    {
        String resultDisplay;
        if (board.playerIsWinner()) {
            resultDisplay = "You win! Congratulations!";
        } else if (board.isTie()) {
            resultDisplay = "It's a tie! Try again!";
        } else {
            resultDisplay = "You lose! Try again!";
        }

        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
        buttonE.setEnabled(false);
        buttonF.setEnabled(false);
        buttonG.setEnabled(false);
        showCustomDialog(resultDisplay);


    }

    private void showCustomDialog(String resultDisplay) {
        mInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ContextThemeWrapper mTheme = new ContextThemeWrapper(this,R.style.dialog);

        mView = mInflater.inflate(R.layout.activity_dialog, null);

        mDialog = new Dialog(mTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(this.mView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        TextView tv = (TextView) mView.findViewById(R.id.textViewResult);
        tv.setText(" Game Result: " + resultDisplay);

        Button buttonQuit = (Button) mView.findViewById(R.id.buttonQuit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button buttonRestart = (Button) mView.findViewById(R.id.buttonRestart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                startActivity(intent);
                finish();
            }
        });
        Button buttonShowResult = (Button) mView.findViewById(R.id.buttonShowResult);
        buttonShowResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                buttonA.setEnabled(false);
                buttonB.setEnabled(false);
                buttonC.setEnabled(false);
                buttonD.setEnabled(false);
                buttonE.setEnabled(false);
                buttonF.setEnabled(false);
                buttonG.setEnabled(false);
            }
        });
    }

    static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    public void aPlay(int[] array, boolean myTurnPass, Button button)
    {
        keys = new ArrayList<Integer>(keyValuePair.keySet());
        boolean full = false;


        for (int i = 0; i < array.length; i++)
        {
            if (keys.contains(array[i]) == true)
            {
                full = true;
            } else {
                location = array[i];
                full = false;
                break;
            }
        }

        if (full)
        {
            button.setEnabled(false);
            checkFull = true;
        }
        else
        {
            String col;
            if (myTurnPass)
            {col = "GREEN";}
            else
            {col = "RED";}

            keyValuePair.put(location, col);
            boardAdapter = new BoardAdapter(BoardActivity.this, R.layout.activity_image, keyValuePair);
            gridView.setAdapter(boardAdapter);
            boardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.replay)
        {
            Intent intent = getIntent();
            startActivity(intent);
            finish();
        }
        else if (id == R.id.quit)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
