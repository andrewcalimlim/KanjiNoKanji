package com.example.kanjinokanji;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class TextRecognition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // prob change it to japanese here

        // https://bit.ly/3JYsA0z
        /*
        TextRecognizer recognizer =
                TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        */



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
    }
}