package com.example.kanjinokanji;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions;

import java.io.IOException;

public class ImageProcessing {

    // Google ML-Kit Text Recognition method basically
    static String imageProcess_text(Context context, Uri the_uri) {
        // basically following this tutorial
        // https://developers.google.com/ml-kit/vision/text-recognition/v2/android

        TextRecognizer recognizer =
                TextRecognition.getClient(new JapaneseTextRecognizerOptions.Builder().build());

        InputImage image;
        try {
            image = InputImage.fromFilePath(context, the_uri);
            Task<Text> result = recognizer.process(image).addOnSuccessListener(
                    new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            String process_result = text.getText();
                            Log.d("PROCESS RESULT", process_result);
                        }
                    });
            return result.toString();

        } catch (IOException e) { //Exceptions gotta catch em all
            e.printStackTrace();
            return "";
        }
    };

    // https://developers.google.com/ml-kit/vision/text-recognition/v2/android
    static Task<Text> imageProcess(Context context, Uri the_uri) {

        TextRecognizer recognizer =
                TextRecognition.getClient(new JapaneseTextRecognizerOptions.Builder().build());

        InputImage image;
        try {
            image = InputImage.fromFilePath(context, the_uri);
            Task<Text> result = recognizer.process(image);
            return result;
        } catch (IOException e) { //Exceptions gotta catch em all
            e.printStackTrace();
            return null;
        }
    };
}
