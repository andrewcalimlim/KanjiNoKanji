package com.example.kanjinokanji;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;

public class AnalyzeMenu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analyze_menu);

        // grabbing data sent from scan menu
        Bundle bundle = getIntent().getExtras();
        Uri theUri =  Uri.parse(bundle.getString("image_uri"));
        String result = bundle.getString("result");

        ImageView selectedImageView = (ImageView)  findViewById(R.id.analyze_image);
        selectedImageView.setImageURI(theUri);

        EditText editableTitle = (EditText)  findViewById(R.id.analyze_result);
        editableTitle.setText(result);
        // sizes of text are max 70 sp

        // ANDREW'S GREAT JAPANESE TEXT RESIZING OF SEPTEMBER 27TH 2023
        //
        // IN WHICH HE SPENT LEGIT LIKE THE PAST 7-8 HOURS FIGURING OUT
        // THEE FEASIBILITY OF AUTO RESIZING EDITABLE TEXT
        // AND THEE ANSWER IS.....PROBABLY NOT POSSIBLE RIGHT NOW
        //
        //
        // AWESOME
        //
        // ANYWAYS
        //
        //
        //this is a trial-and-error guide to sizing based on japanese char width,
        // setting the size of the text based off the string length
        // fairly ugly code-wise but unfortunately autosizing doesn't seem to work with
        // EditTexts (despite TextView being a super class of it)
        // but hey this is a good review/usage of hashmaps
        // and this solution to the problem is wholly my idea!

        // also if your text is over 15 characters then the user can just scroll through it
        // because the font being smaller than 20sp would not be helpful to the user
        // CONSIDER breaking text into two lines over 15 characters idk

        java.util.HashMap<Integer,Integer> sizes = new java.util.HashMap<Integer,Integer>();
        sizes.put(6,60);
        sizes.put(7,52);
        sizes.put(8,45);
        sizes.put(9,40);
        sizes.put(10,35);
        sizes.put(11,32);
        sizes.put(12,30);
        sizes.put(13,27);
        sizes.put(14,25);
        sizes.put(15,23);

        String curText = editableTitle.getText().toString();

        if(curText.length() < 6){
            editableTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
        }
        else if(curText.length() > 15){
            editableTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        }
        else{
            editableTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizes.get(curText.length()));
        }

        showAnalyzeMenuExplanation(AnalyzeMenu.this);

    }

    public void showAnalyzeMenuExplanation(Context c){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Verify the scanned text");
        // adding text and images via custom XML!
        LayoutInflater inflater = AnalyzeMenu.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.analyze_menu_dialog, null);
        builder.setView(dialogView);
        builder.setNeutralButton("OK",null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
