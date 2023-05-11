package com.example.kanjinokanji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class ImageProcessing {

    // debug method to just test images manually, will delete eventually
    static Object getDrawableFromString(String fileName){

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

    //not allowed to use await on the main thread, must do it in background
    // https://stackoverflow.com/questions/50939645/android-await-must-not-be-called-on-the-ui-thread
    // its nbd i guess i'll just debug another way then...

    /*
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
    */

    // Google ML-Kit Text Recognition method basically
    static void imageProcess(Context context, String fileName) {
        // basically following this tutorial
        // https://developers.google.com/ml-kit/vision/text-recognition/v2/android

        TextRecognizer recognizer =
                TextRecognition.getClient(new JapaneseTextRecognizerOptions.Builder().build());

        InputImage image;
        try {
            Uri test_uri = Uri.parse("android.resource://com.example.kanjinokanji/" +
                    getDrawableFromString(fileName));
            image = InputImage.fromFilePath(context, test_uri);
            Task<Text> result = recognizer.process(image).addOnSuccessListener(
                    new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            String process_result = text.getText();
                            Log.d("PROCESS RESULT", process_result);

                            try {
                                //Log.d("RESULT", "begin");
                                String[] search_result = SearchRemy.searchRemy(process_result);
                                if(search_result[0] == null){
                                    Log.d("SEARCH RESULT", "null");

                                }
                                else{
                                    Log.d("SEARCH RESULT", search_result[0]);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("SEARCH RESULT", "ERROR");
                            }

                        }
                    });
        } catch (IOException e) { //Exceptions gotta catch em all
            e.printStackTrace();
        }
    }
    /***
     *
     * Never mind sanitization, just give the user the ability to manually edit it
     *
     * and remind the user to delete any characters that don't appear in the original
     */
}
