package com.example.flags;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GuessTheCountry extends AppCompatActivity {

    Spinner spinner;
    ImageView imageView;
    TextView tv_correctName, tv_answerStatus, tv_timer;
    Button button;
    Random r;
    Db db;
    Country currentCountry;

    //Creating arraylist
    ArrayList<Country> country_list;
    // Create a list of strings
    List<String> answer_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_country);

        // Spinner element
        spinner = findViewById(R.id.country_spinner);
        // Initialize all the view of guess the country variables.
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        tv_correctName = findViewById(R.id.tv_correctName);
        tv_answerStatus = findViewById(R.id.tv_answerStatus);
        tv_timer = findViewById(R.id.tv_timer1);

        r = new Random();
        db = new Db();

        country_list = new ArrayList();
        answer_list = new ArrayList();

        //add answers to array
        for(int i = 0; i < db.answers.length; i++){
            Country c = new Country(db.answers[i], db.images[i]);
            country_list.add(c);
            answer_list.add(c.getName());
        }

        //Sort ArrayList - to sort the list elements
        Collections.sort(answer_list);
        // shuffle the list
        Collections.shuffle(country_list);

        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, answer_list);
        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        loadNewCountry();
        // attaching adapter to spinner
        spinner.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Submit")){
                   String selectedCountry = spinner.getSelectedItem().toString();

                   if (currentCountry.getName().equals(selectedCountry)){
                        correct();
                   }else {
                        wrong();
                   }

                }else{
                    loadNewCountry();
                }
            }
        });
    }

    void loadNewCountry(){
        int i = r.nextInt(country_list.size());
        currentCountry = country_list.get(i);
        imageView.setImageResource(currentCountry.getImage()); //image loading
        tv_answerStatus.setText("");
        tv_correctName.setText("");
        button.setText("Submit");

        if (MainActivity.isTimerOn){
            setTimer();
        }
    }

    void setTimer(){

        new CountDownTimer(11000, 1000) {
            //It callback fired on regular interval and millisUntilFinished is the number of millis in the future
            // from the call until the countdown is done.
            public void onTick(long millisUntilFinished) {
                tv_timer.setText("" + millisUntilFinished / 1000);
            }
            //It fires then the countdown timer finishes i.e time is up.
            public void onFinish() {
                wrong();
            }
            //It simply starts the countdown timer.
        }.start();
    }

    void wrong(){
        tv_answerStatus.setTextColor(Color.parseColor("#FF0000"));
        tv_answerStatus.setText("Wrong!");
        tv_correctName.setText(currentCountry.getName());
        button.setText("Next");
    }

    void correct(){
        tv_answerStatus.setTextColor(Color.parseColor("#008000"));
        tv_answerStatus.setText("Correct!");
        button.setText("Next");
    }
}
