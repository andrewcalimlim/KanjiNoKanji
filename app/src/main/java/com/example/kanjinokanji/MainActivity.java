package com.example.kanjinokanji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity { //LMAO IT WAS JUST THE NAME

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageProcessing.imageProcess(this, "romantic");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}