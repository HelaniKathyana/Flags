package com.example.flags;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AdvancedLevel extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3;
    TextView tv_score, tv_flag1, tv_flag2, tv_flag3, tv_answerStatus, tv_timer;
    EditText et_answer1, et_answer2, et_answer3;
    Button button;
    Random r;
    Db db;
    Country currentCountry1, currentCountry2, currentCountry3;

    //Creating arraylist
    ArrayList<Country> country_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        // Initialize all the view variables.
        imageView1 = findViewById(R.id.flag1_imageView);
        imageView2 = findViewById(R.id.flag2_imageView);
        imageView3 = findViewById(R.id.flag3_imageView);
        tv_score = findViewById(R.id.tv_score);
        tv_flag1 = findViewById(R.id.tv_flag1Name);
        tv_flag2 = findViewById(R.id.tv_flag2Name);
        tv_flag3 = findViewById(R.id.tv_flag3Name);
        tv_timer = findViewById(R.id.tv_timer4);
        et_answer1 = findViewById(R.id.et_flag1);
        et_answer2 = findViewById(R.id.et_flag2);
        et_answer3 = findViewById(R.id.et_flag3);
        tv_answerStatus = findViewById(R.id.answerStatus_textView);
        button = findViewById(R.id.next_button);

        tv_score.setText("0");

        r = new Random();

        db = new Db();

        country_list = new ArrayList();

        //add answers to array
        for (int i = 0; i < db.answers.length; i++) {
            Country c = new Country(db.answers[i], db.images[i]);
            country_list.add(c);
        }

        Collections.shuffle(country_list);

        loadNew();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Submit")){
                    checkAnswers();
                }else {
                    loadNew();
                }
            }
        });
    }

    void loadNew(){
        //select images randomly
        int i = r.nextInt(country_list.size());
        int i2 = r.nextInt(country_list.size());
        int i3 = r.nextInt(country_list.size());

        while (i2 == i || i3 == i || i3 == i2){
            i2 = r.nextInt(country_list.size());
            i3 = r.nextInt(country_list.size());
        }

        currentCountry1 = country_list.get(i);
        currentCountry2 = country_list.get(i2);
        currentCountry3 = country_list.get(i3);

        imageView1.setImageResource(currentCountry1.getImage());
        imageView2.setImageResource(currentCountry2.getImage());
        imageView3.setImageResource(currentCountry3.getImage());

        tv_flag1.setText("");
        tv_flag2.setText("");
        tv_flag3.setText("");
        et_answer1.setText("");
        et_answer2.setText("");
        et_answer3.setText("");
        et_answer1.setTextColor(Color.parseColor("black"));
        et_answer2.setTextColor(Color.parseColor("black"));
        et_answer3.setTextColor(Color.parseColor("black"));
        button.setText("Submit");
        tv_answerStatus.setText("");

        if (MainActivity.isTimerOn){
            setTimer();
        }
    }

    void checkAnswers(){
        //user typing answer
        String answer1 = et_answer1.getText().toString();
        String answer2 = et_answer2.getText().toString();
        String answer3 = et_answer3.getText().toString();
        //textview value in score
        int score = Integer.parseInt(tv_score.getText().toString());

        //boolean value for whether answer 1,2,3 correct or not
        boolean answer1Correct = false;
        boolean answer2Correct = false;
        boolean answer3Correct = false;

        //check user typed answer 1 same to current country name
        if (!(answer1.equals(currentCountry1.getName()))){
            //user typed answer 1 not same to current current country
            tv_flag1.setText(currentCountry1.getName());
            et_answer1.setTextColor(Color.parseColor("red"));
        }else{
            //user typed answer 1 same to current current country
            //shows as answer 1 is correct and boolean value true
            //add 1 mark to the score
            et_answer1.setTextColor(Color.parseColor("green"));
            answer1Correct = true;
            score +=1 ;
            //set score into the text view
            tv_score.setText(score+"");
        }

        if (!(answer2.equals(currentCountry2.getName()))){
            //user typed answer 2 not same to current current country
            tv_flag2.setText(currentCountry2.getName());
            et_answer2.setTextColor(Color.parseColor("red"));
        }else{
            //user typed answer 2 same to current current country
            //shows as answer 2 is correct and boolean value true
            //add 1 mark to the score
            et_answer2.setTextColor(Color.parseColor("green"));
            answer2Correct = true;
            score +=1 ;
            tv_score.setText(score+"");
        }

        if (!(answer3.equals(currentCountry3.getName()))){
            //user typed answer 3 not same to current current country
            tv_flag3.setText(currentCountry3.getName());
            et_answer3.setTextColor(Color.parseColor("red"));
        }else {
            //user typed answer 3 same to current current country
            //shows as answer 3 is correct and boolean value true
            //add 1 mark to the score
            et_answer3.setTextColor(Color.parseColor("green"));
            answer3Correct = true;
            score +=1 ;
            tv_score.setText(score+"");
        }

        //check answer 1,2 & 3 correct or not
        if(answer1Correct && answer2Correct && answer3Correct){
            correct();
        }else {
            wrong();
        }

        button.setText("Next");
    }

    void setTimer(){

        new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {
                tv_timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                wrong();
            }
        }.start();
    }

    void wrong(){
        tv_answerStatus.setTextColor(Color.parseColor("#FF0000"));
        tv_answerStatus.setText("Wrong!");
    }

    void correct(){
        tv_answerStatus.setTextColor(Color.parseColor("#008000"));
        tv_answerStatus.setText("Correct!");
    }
}
