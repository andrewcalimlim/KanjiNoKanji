package com.example.kanjinokanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity { //LMAO IT WAS JUST THE NAME

    protected void displayScanButton(Context c){
        Button scanMenuButton = (Button) findViewById(R.id.main_scanButton);
        Log.d("VALID", Integer.toString(R.id.main_scanButton));
        // this unsightly mess is how u change activities in Java btw
        // https://developer.android.com/reference/android/content/Context#startActivity(android.content.Intent)

        scanMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(c, ScanMenu.class));
            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu); // note this has to be called
        displayScanButton(MainMenu.this); //before grabbing views by ID like here
        // cause the view object hasn't been made yet otherwise


    }
}