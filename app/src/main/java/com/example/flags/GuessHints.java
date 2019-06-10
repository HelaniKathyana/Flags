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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GuessHints extends AppCompatActivity {

    ImageView imageView;
    TextView tv_hintletters, tv_answerStatus, tv_correctName, tv_timer;
    EditText ed_character;
    Button button;
    Random r;
    Db db;
    Country currentCountry;

    //Creating arraylist
    ArrayList<Country> country_list;

    char[] countryName;
    char[] hintLetters;

    int wrongCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_hints);

        // Initialize all the view variables.
        imageView = findViewById(R.id.imageView);
        tv_hintletters = findViewById(R.id.hintletters_textView);
        ed_character = findViewById(R.id.character_edittext);
        tv_answerStatus = findViewById(R.id.answerStatus_textView);
        tv_correctName = findViewById(R.id.correctName_textView);
        tv_timer = findViewById(R.id.tv_timer2);
        button = findViewById(R.id.button);

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

        loadNewQuestion();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("Submit")) {
                    checkAnswer();
                } else {
                    loadNewQuestion();
                }
            }
        });
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

    private void checkAnswer() {
        //the character which will user have to enter
        tv_answerStatus.setText("");
        String s = ed_character.getText().toString().toUpperCase();

        //user is only allowed up to 3 incorrect character guesses
        if (wrongCount >= 3) {
            wrong();
        } else {
           //if user enter a character more than 1
            if (s.length() > 1) {
                Toast.makeText(this, "You can enter only one character at a time!", Toast.LENGTH_LONG).show();
                ed_character.setText("");
            } else {
                //create an array list for check whether the how many characters for one country name
                ArrayList<Integer> indexes = new ArrayList();
                //boolean for found the entered character in somewhere through country name
                boolean foundAtLeastOneCharacter = false;
                //check whether the character is there
                for (int i = 0; i < countryName.length; i++) {
                    char c = countryName[i];

                    //if character is there, position of the character add to the array list
                    if (s.charAt(0) == c) {
                        indexes.add(i);
                        foundAtLeastOneCharacter = true;
                    }
                }

                //if at least one character is there,
                if (foundAtLeastOneCharacter) {
                    //add entered character to the hyphen
                    for (int i : indexes) {
                        hintLetters[i] = s.charAt(0);
                    }

                    //shows hint letters in text view
                    ed_character.setText("");
                    tv_hintletters.setText(String.valueOf(hintLetters));
                } else {
                    incorrectCharacter();
                }

                boolean hintsComplete = true;

                //for loop for check whether the hyphens are empty
                for (char c : hintLetters) {
                    String ss = String.valueOf(c);

                    if (ss.equals("-")) {
                        hintsComplete = false;
                        break;
                    }

                }
                //if hyphens are not empty the word is complete and calling correct method
                if (hintsComplete) {
                    correct();
                }
            }

        }
    }

    void incorrectCharacter(){
        wrongCount += 1;

        tv_answerStatus.setTextColor(Color.parseColor("#FFFF00"));
        tv_answerStatus.setText("Incorrect Character!");
    }

    void loadNewQuestion() {
        wrongCount = 0;

        int i = r.nextInt(country_list.size());
        currentCountry = country_list.get(i);
        imageView.setImageResource(currentCountry.getImage()); //image loading

        //set as a char array of current country name
        countryName = new char[currentCountry.getName().length()];
        //set a char array for hint letters
        hintLetters = new char[countryName.length];

        //shows country name in capital
        String ss = currentCountry.getName().toUpperCase();
        countryName = ss.toCharArray();

        //In hint letters, for every character replace a hyphen instead of a space
        //when starting a hint letters firstly it'll replace as a hyphen
        for (int k = 0; k < countryName.length; k++) {
            char c = countryName[k];

            if (!(String.valueOf(c).equals(" "))) {
                String s = "-";
                hintLetters[k] = s.charAt(0);
            } else {
                String s = " ";
                hintLetters[k] = s.charAt(0);
            }
        }

        System.out.println(countryName);

        tv_hintletters.setText(String.valueOf(hintLetters));
        tv_answerStatus.setText("");
        tv_correctName.setText("");
        button.setText("Submit");

        if (MainActivity.isTimerOn){
            setTimer();
        }
    }

    void wrong() {
        tv_answerStatus.setTextColor(Color.parseColor("#FF0000"));
        tv_answerStatus.setText("Wrong!");
        tv_correctName.setText(currentCountry.getName());
        button.setText("Next");
    }

    void correct() {
        tv_answerStatus.setTextColor(Color.parseColor("#008000"));
        tv_answerStatus.setText("Correct!");
        button.setText("Next");
    }
}
