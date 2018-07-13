package com.example.user.project1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int userTurnscore=0,computerTurnscore=0,userTotalscore=0,computerTotalscore=0;
    private Random random=new Random(System.nanoTime());//nano.Time() is to provide seed.
    private Button rollButton,holdButton,resetButton;
    private TextView turnScore,userScore,computerScore;
    private ImageView diceFace;
    private int num;
    private Handler timeHandler=new Handler();
        Runnable timeRunnable = new Runnable() {

            @Override
            public void run() {
                if (num != 1 && computerTurnscore <=20){
                    computerTurn();
                    timeHandler.postDelayed(this, 5000);
                }
                if (computerTurnscore > 20) {
                    setcomputerTotalscore();
                    Toast.makeText(MainActivity.this, "Computer Holds!", Toast.LENGTH_SHORT).show();
                    timeHandler.removeCallbacks(this);
                }
            }
        };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceFace=(ImageView) findViewById(R.id.dice1);
        rollButton=(Button) findViewById(R.id.button4);
        holdButton=(Button) findViewById(R.id.button5);
        resetButton=(Button) findViewById(R.id.button7);
        turnScore=(TextView) findViewById(R.id.turnscore);
        userScore=(TextView) findViewById(R.id.userscore);
        computerScore=(TextView) findViewById(R.id.computerscore);

       /* rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }*/
        }
    public void roll( View view ){

        num=random.nextInt(6) + 1;//nextInt(upperbound)..here 0 to 5 num will be gen but we need from 1 to 6 so we are adding 1
        setScore(num,'u');
        if(num==1){
            Toast.makeText(MainActivity.this,"You Rolled a 1!",Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this,"Its computer's turn now.",Toast.LENGTH_SHORT ).show();
            computerTurn();
            if(computerTurnscore<100){
                turnScore.setText("Your Turn score:"+userTurnscore);
            }
        }
    }


    public void hold( View view){
        Toast.makeText(MainActivity.this,"You Hold!",Toast.LENGTH_SHORT).show();
        setuserTotalscore();
        if(userTotalscore<100){
            Toast.makeText(MainActivity.this,"Its computer's turn now.",Toast.LENGTH_SHORT).show();
            computerTurn();
            if(computerTurnscore<100){
                turnScore.setText("Your Turn score:"+userTurnscore);
            }
        }
    }

    public void reset(View view){
        userTurnscore=0;
        userTotalscore=0;
        computerTurnscore=0;
        computerTotalscore=0;
        turnScore.setText("Turn Score: 0");
        userScore.setText("Your Score: 0");
        computerScore.setText("Computer Score: 0");
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
    }
    public void setScore(int num,char ch){
        if( ch=='u'){
            Toast.makeText(MainActivity.this,""+num,Toast.LENGTH_SHORT).show();
            if(num==1)
            {
            userTurnscore=0;
            diceFace.setImageResource(R.drawable.dice1);
            }
            else
            {
            if(num==2)
                diceFace.setImageResource(R.drawable.dice2);
            if(num==3)
                diceFace.setImageResource(R.drawable.dice3);
            if(num==4)
                diceFace.setImageResource(R.drawable.dice4);
            if(num==5)
                diceFace.setImageResource(R.drawable.dice5);
            if(num==6)
                diceFace.setImageResource(R.drawable.dice6);

            userTurnscore+=num;
            }
            turnScore.setText("Your turn score"+ userTurnscore);
        }
        else if( ch=='c')
        {
            if(num==1)
            {
            computerTurnscore=0;
            diceFace.setImageResource(R.drawable.dice1);
            }
            else {
                if (num == 2)
                    diceFace.setImageResource(R.drawable.dice2);
                if (num == 3)
                    diceFace.setImageResource(R.drawable.dice3);
                if (num == 4)
                    diceFace.setImageResource(R.drawable.dice4);
                if (num == 5)
                    diceFace.setImageResource(R.drawable.dice5);
                if (num == 6)
                    diceFace.setImageResource(R.drawable.dice6);

                computerTurnscore += num;
            }
                turnScore.setText("Computer turn score"+ computerTurnscore);
        }

    }
    public void computerTurn(){
        turnScore.setText("Computer turn score:"+ computerTurnscore);
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        resetButton.setEnabled(false);
            num=random.nextInt(6)+1;
            if(num!=1 && computerTurnscore<=20){
            setScore(num,'c');
            timeHandler.postDelayed(timeRunnable,5000);
        }
        else{
                timeHandler.removeCallbacks(timeRunnable);
                if(num==1){
                    diceFace.setImageResource(R.drawable.dice1);
                    Toast.makeText(this , "Computer Rolled a 1!\nIt's your turn now.\n     Press Roll." , Toast.LENGTH_SHORT).show();
                    computerTurnscore = 0;
                }
                setcomputerTotalscore();

            }

    }



    void declareWinner(){
        if(computerTotalscore>100 || userTotalscore>100){
            if(computerTotalscore>100){
                Toast.makeText(MainActivity.this,"Computer Win.",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(MainActivity.this,"User Win.",Toast.LENGTH_LONG).show();
            }
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
        }
    }

    public void setcomputerTotalscore(){
        computerTotalscore+=computerTurnscore;
        computerTurnscore=0;
        computerScore.setText("Computer Overall:"+computerTotalscore);
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        resetButton.setEnabled(true);

    }
    public void setuserTotalscore(){
        userTotalscore+=userTurnscore;
        userScore.setText("Your Overall:"+userTotalscore);
        userTurnscore=0;
        turnScore.setText("Your turn score"+ userTurnscore);
        declareWinner();
    }
    @Override
    protected void onPause(){
        super.onPause();
        timeHandler.removeCallbacks(timeRunnable);
    }
}


