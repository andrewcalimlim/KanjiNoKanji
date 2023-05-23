package com.example.kanjinokanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity { //LMAO IT WAS JUST THE NAME

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ImageProcessing.imageProcess(this, "romantic");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button scanMenuButton = (Button) findViewById(R.id.main_scanButton);

        // this unsightly mess is how u change activities in Java btw
        // https://developer.android.com/reference/android/content/Context#startActivity(android.content.Intent)


        scanMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenu.this, ScanMenu.class));
            }
        });




    }
}