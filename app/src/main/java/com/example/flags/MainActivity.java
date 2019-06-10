package com.example.flags;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    public Button btn_guessthecountry;
    public Button btn_guessHints;
    public Button btn_guesstheflag;
    public Button btn_advancedlevel;
    public Switch timerSwitch;
    public static boolean isTimerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate Buttons
        btn_guessthecountry = (Button) findViewById(R.id.btn_guessthecountry);
        btn_guessHints = (Button) findViewById(R.id.btn_guesshints);
        btn_guesstheflag = (Button) findViewById(R.id.btn_guesstheflag);
        btn_advancedlevel = (Button) findViewById(R.id.btn_advancedlevel);
        // initiate Switch
        timerSwitch = (Switch) findViewById(R.id.timer_switch);

        // Set a checked change listener for timer switch
        timerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //when Switch button is on/checked
                if (timerSwitch.isChecked()) {
                    timerSwitch.setText("Timer ON ");
                    isTimerOn = true;
                    //when Switch is off/unchecked
                } else {
                    timerSwitch.setText("Timer OFF");
                    isTimerOn = false;
                }
            }
        });

        // set on-click listener for guess the country button
        btn_guessthecountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create the intent to start another activity
                // Start GuessTheCountry.class
                Intent intent = new Intent(MainActivity.this, GuessTheCountry.class);
                startActivity(intent);
            }
        });

        // set on-click listener for guess hints button
        btn_guessHints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start GuessHints.class
                Intent intent = new Intent(MainActivity.this, GuessHints.class);
                startActivity(intent);
            }
        });

        // set on-click listener for guess the flag button
        btn_guesstheflag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Start GuessTheFlag.class
                Intent intent = new Intent(MainActivity.this, GuessTheFlag.class);
                startActivity(intent);
            }
        });

        // set on-click listener for advanced level button
        btn_advancedlevel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Start AdvancedLevel.class
                Intent intent = new Intent(MainActivity.this, AdvancedLevel.class);
                startActivity(intent);
            }
        });

    }
}
