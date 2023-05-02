package com.example.kanjinokanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.example.kanjinokanji.SearchRemy;


public class MainActivity extends AppCompatActivity { //LMAO IT WAS JUST THE NAME

    // debug method to just test images manually, will delete eventually
    Object getDrawableFromString(String fileName){

        // this is a horrible way of doing it but its debug so w/e

        HashMap<String, Object> hashItOut = new HashMap<>();
        hashItOut.put("ado", R.drawable.ado);
        hashItOut.put("idk", R.drawable.idk);
        hashItOut.put("oyster", R.drawable.oyster);
        hashItOut.put("prim", R.drawable.prim);
        hashItOut.put("romantic", R.drawable.romantic);
        hashItOut.put("shime", R.drawable.shime);
        hashItOut.put("technorch", R.drawable.technorch);

        return hashItOut.get(fileName);

    }

    // Google ML-Kit Text Recognition method basically
    String imageProcess(String fileName){
        // basically following this tutorial
        // https://developers.google.com/ml-kit/vision/text-recognition/v2/android

        TextRecognizer recognizer =
                TextRecognition.getClient(new JapaneseTextRecognizerOptions.Builder().build());

        InputImage image;
        try{
            Uri test_uri = Uri.parse("android.resource://com.example.kanjinokanji/" +
                    getDrawableFromString(fileName));
            image = InputImage.fromFilePath(this, test_uri);
            String result =  Tasks.await(recognizer.process(image)).getText();
            return result;
        } catch (IOException e) { //Exceptions gotta catch em all
        e.printStackTrace();
          } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ""; // i don't think this will return on a successful read

    }

    /***
     * Method to check for spacing/unneeded characters
     * @param result
     * @return
     *
     * Some examples of proper sanitization
     *
     * Input:  "真地獄超特急-HELL or HELL-"
     * Output: "真 地獄超特急 -HELL or HELL-"
     *
     * Note: Kanji generally doesn't have spacing so just manually check for this song? lol
     *
     * Input:  "「罪過の聖堂"
     * Output: "罪過の聖堂"
     *
     * Note: Just remove non JP or Latin characters?
     *
     * Input:  "表裏一旗!?怪盗いいんちょの悩みケ"
     * Output: "表裏一体！？怪盗いいんちょの悩み♥"
     *
     * Note: it got one wrong so consider searching for parts of each?
     * also full width conversion of punctuation needed
     *
     * Use this
     * https://stackoverflow.com/questions/1499804/how-can-i-detect-japanese-text-in-a-java-string
     */

    String textSanitization(String result){
        return result;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}