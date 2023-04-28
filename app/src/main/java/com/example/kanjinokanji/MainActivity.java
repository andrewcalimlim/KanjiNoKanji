package com.example.kanjinokanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions;

import java.io.IOException;

import com.example.kanjinokanji.SearchRemy;


public class MainActivity extends AppCompatActivity { //LMAO IT WAS JUST THE NAME

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // now following this tutorial since Japanese recognition is in Beta rn
        // forefront of innovation lesss goooo

        // basically following this tutorial
        // https://developers.google.com/ml-kit/vision/text-recognition/v2/android

        // 1. Create an instance of Text Recognizer
        TextRecognizer recognizer =
                TextRecognition.getClient(new JapaneseTextRecognizerOptions.Builder().build());

        // 2. Prepare the input image > Using a file URI (media.Image for camera, looks hard tbh)

        // plus no one will be using this app in-game, timers in bemani games range from
        // anywhere between 10-40 seconds

        InputImage image;

        // remember that for our intents and purposes: context is literally "this"

        // trying to figure out how to get the file path of shime.png into a URI
        // drawable/shime.png

        try {
            // wow https://stackoverflow.com/questions/4896223/how-to-get-an-uri-of-an-image-resource-in-android
            // this is starting to get niche
            // said no programmer ever lmaooo

            Uri test_uri = Uri.parse("android.resource://com.example.kanjinokanji/" +
                    R.drawable.prim); //change this to any image you want

            //import images by dragging them to the res/drawable folder

            image = InputImage.fromFilePath(this, test_uri);

            // 3. Process the image
            Task<Text> result =  recognizer.process(image)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {

                            // Task completed successfully
                            // :Pog:

                            String output = text.getText();
                            Log.d("AYOOOO", "kanji detected: " + output);
                            try {
                                String[] search = SearchRemy.searchRemy(output);
                                String romanized = search[0];
                                String url = search[1];
                                Log.d("AYOOOO", "romanization: " + romanized);
                                Log.d("AYOOOO", "URL: " + url);

                            } catch (Exception e) {
                                Log.d("AYOOOO", "RemyWiki search found nothing.");
                                e.printStackTrace();
                            }

                            /*
                            welcome back gamer

                            current issue:

                            kanji is being detected on score photos
                            i think

                            but the kanji spacing needs to be exact, and possibly strip away any
                            latin words

                            test the spacing by checking all possible spacings? (possibly inefficient)

                            but yeah detection aight, GJ!

                            aight refactor this to a separate function so u can test several images
                            at a time

                            and maybe show them on screen? nahhh

                            another example of needing to "check" the ml-text recognizer:

                            「罪過の聖堂 needs to be 罪過の聖堂
                            表裏一旗!?怪盗いいんちょの悩みケ should be a unicode heart lol maybe just
                            tell the user to crop any unicode hearts or ish like that idk

                            or to edit the text before sending it off to search

                            which might involve detecting japanese characters (kana or Han i guess)

                            https://stackoverflow.com/questions/1499804/how-can-i-detect-japanese-text-in-a-java-string


                             */




                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // :WeirdChamp:
                        }
                    });

            //String resultText = result.toString();
            //Log.d("AYOOO", resultText.);

            // 4. Extract text from blocks of recognized text



        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}