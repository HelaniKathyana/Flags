package com.example.flags;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GuessTheFlag extends AppCompatActivity {

    ImageView imageView1, imageView2, imageView3, correctFlag;
    TextView tv_answerStatus, tv_countryName, tv_timer;
    Button button;
    Random r;
    Db db;
    Country currentCountry;

    //Creating arraylist
    ArrayList<Country> country_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag);

        // Initialize all the view variables.
        button = findViewById(R.id.next_button);
        imageView1 = findViewById(R.id.flag1_imageView);
        imageView2 = findViewById(R.id.flag2_imageView);
        imageView3 = findViewById(R.id.flag3_imageView);
        tv_answerStatus = findViewById(R.id.answerStatus_textView);
        tv_countryName = findViewById(R.id.countryname_textView);
        tv_timer = findViewById(R.id.tv_timer3);

        r = new Random();
        db = new Db();

        country_list = new ArrayList();

        //add answers to array
        for (int i = 0; i < db.answers.length; i++) {
            Country c = new Country(db.answers[i], db.images[i]);
            country_list.add(c);
        }

        // shuffle the list
        Collections.shuffle(country_list);


        // set on-click listener for button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNewCountry();
            }
        });

        // set on-click listener for ImageView 1
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifCorrect(imageView1);
            }
        });

        // set on-click listener for ImageView 2
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifCorrect(imageView2);
            }
        });

        // set on-click listener for ImageView 3
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifCorrect(imageView3);
            }
        });

        loadNewCountry();

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

    void loadNewCountry(){
        tv_answerStatus.setText("");
        //select images randomly
        int i = r.nextInt(country_list.size());
        int i2 = r.nextInt(country_list.size());
        int i3 = r.nextInt(country_list.size());
        int i4 = r.nextInt(3);
        currentCountry = country_list.get(i);

        while (i2 == i || i3 == i){
            i2 = r.nextInt(country_list.size());
            i3 = r.nextInt(country_list.size());
        }

        //if the image equals to position 1
        if (i4 == 1){
            imageView1.setImageResource(currentCountry.getImage());
            imageView2.setImageResource(country_list.get(i2).getImage());
            imageView3.setImageResource(country_list.get(i3).getImage());
            correctFlag = imageView1;
            //image equals to position 2
        }else if (i4 == 2){
            imageView1.setImageResource((country_list.get(i2).getImage()));
            imageView2.setImageResource(currentCountry.getImage());
            imageView3.setImageResource(country_list.get(i3).getImage());
            correctFlag = imageView2;
        }else{
            imageView1.setImageResource(country_list.get(i3).getImage());
            imageView2.setImageResource(country_list.get(i2).getImage());
            imageView3.setImageResource(currentCountry.getImage());
            correctFlag = imageView3;
        }

        tv_countryName.setText(currentCountry.getName());

        if (MainActivity.isTimerOn){
            setTimer();
        }
    }

    void ifCorrect(ImageView img){
        if (img == correctFlag){
            correct();
        }else{
           wrong();
        }
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
