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
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
     private GameBoard board;
     private TextView txtSteps;
     private int stepsCount=0;
     private TextView txtTime;
     private int timeCount=0;
     private Button btnNewGame;
     private TextView buttons[][];
     Thread thread;
     private boolean exit;
     private int size = 4;
     private boolean playChecked;
     private MediaPlayer ring;


    @Override
     protected void onCreate(Bundle savedInstanceState) {
        Log.d("mylog", ">>> onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        playChecked = getIntent().getExtras().getBoolean("music");
        if(playChecked) {
            ring = MediaPlayer.create(this, R.raw.piano);
            ring.setLooping(true);
        }
        playMusic(playChecked);
        board = new GameBoard(size);
        buttons = new TextView[size][size];
        txtSteps = findViewById(R.id.txt_steps);
        txtTime = findViewById(R.id.txt_timer);
        btnNewGame = findViewById(R.id.btn_new_game);
        btnNewGame.setOnClickListener(this);
        for (int i=0;i<size*size;i++){
            String id = "btn_ID"+(i+1);
            int resID = getResources().getIdentifier(id,"id", getPackageName());
            buttons[i / size][i % size] = (TextView) findViewById(resID);
            buttons[i / size][i % size].setOnClickListener(this);
        }
        newGame();
    }

     private void setTime(int timeCount) {
        int second = timeCount % 60;
        int minute = timeCount / 60;
        txtTime.setText(String.format("Time: %02d:%02d",minute,second));
     }
     private void loadTimer() {
        exit = false;
        if(thread == null) {
            Log.d("mylog", ">>> new thread");
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    while (!exit) {
                        timeCount++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setTime(timeCount);
                            }
                        });
                        SystemClock.sleep(1000);

                    }
                    Log.d("mylog", ">>> kill thread");
                    thread.interrupt();
                    thread=null;
                }
            });
            thread.start();
        }
    }
     private void playMusic(boolean playChecked){
        if(ring != null) {
            if (playChecked)
                ring.start();
            else
                ring.pause();
        }
    }
     @Override
     public void onClick(View v) {
         switch (v.getId())
         {
             case R.id.btn_new_game:
                 exit=true;
                 newGame();
                 break;

             default:
                 for (int i=0;i<size*size;i++){
                     if (buttons[i/size][i%size].getId()==v.getId() && buttons[i/size][i%size].getText()!= " "){
                         board.updateMove(Integer.valueOf(String.valueOf(buttons[i/size][i%size].getText())));
                         stepsCount = board.getSteps();
                         txtSteps.setText(String.format("Moves: %04d",stepsCount));
                         drawBoard();
                         isWin();
                         break;
                     }
                 }

         }
     }
     private void drawBoard(){
        int num;
        for(int i=0 ;i<size*size;i++){
            num = board.getVal(i/size,i%size);
            if(num == 0) { //empty cell
                buttons[i/size][i%size].setText(" ");
                 GradientDrawable bgShape = (GradientDrawable)buttons[i/size][i%size].getBackground();
                 bgShape.setColor(Color.WHITE);
            }
            else {
                buttons[i / size][i % size].setText(String.valueOf(num));
                GradientDrawable bgShape = (GradientDrawable)buttons[i/size][i%size].getBackground();
                bgShape.setColor(Color.DKGRAY);
            }
        }
    }
     private void isWin(){
          if(board.isGameOver()){
            Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
            for (int j = 0; j < size * size; j++) {
                buttons[j / size][j % size].setClickable(false);
          }
          txtSteps.setTextColor(RED);
          txtTime.setTextColor(RED);
          exit=true;
        }
    }
     private void newGame(){
        board.newGame();
        for (int j = 0; j < size * size; j++) {
            buttons[j / size][j % size].setClickable(true);
        }

        stepsCount = board.getSteps();
        timeCount=0;
        setTime(timeCount);
        txtSteps.setText(String.format("Moves: %04d",stepsCount));
        drawBoard();
        loadTimer();
        txtSteps.setTextColor(BLACK);
        txtTime.setTextColor(BLACK);
    }
     @Override
     protected void onResume(){
        super.onResume();
        if(!board.isGameOver())
            loadTimer();
        playMusic(true);
        drawBoard();
        Log.d("mylog", ">>> onResume()");
    }
     @Override
     protected void onPause(){
        super.onPause();
        playMusic(false);
        exit = true;
        Log.d("mylog", ">>> onPause()");
    }
     protected void onDestroy() {
        super.onDestroy();
        if(ring!=null)
            ring.stop();
        exit = true;
        Log.d("mylog", ">>> onDestroy()");
    }

}