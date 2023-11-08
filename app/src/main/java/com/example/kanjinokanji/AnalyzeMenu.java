package com.example.kanjinokanji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnalyzeMenu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_menu);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Text Verification Tutorial");

        // adding text and images via custom XML!
        LayoutInflater inflater = AnalyzeMenu.this.getLayoutInflater();

        // lmaooooo the issue was that u can't find a view in the layout until its actually in the layout
        // and u forgot that the dialog is a separate xml file
        View dialogView = inflater.inflate(R.layout.dialog_analyze_menu, null);
        ViewFlipper theVF = (ViewFlipper) dialogView.findViewById(R.id.analyzeMenuDialogViewFlipper);

        builder.setView(dialogView);

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        theVF.setInAnimation(in);
        theVF.setOutAnimation(out);

        // in order to locate the subviews within a dialog, the actual dialog view (that is inflated with the xml file)
        // needs to be referred to
        // otherwise its just trying to search the activity layout
        // which is why everything is coming fuccing NULL
        ConstraintLayout cl = (ConstraintLayout) dialogView.findViewById(R.id.analyze_dialog_constraint_layout);

        // can't be called before all the other layouts are added
        AlertDialog dialog = builder.create();

        // Dialog buttons (they appear first in the process of using KJK so it makes sense
        // to programatically design them first
        Button prevButton = (Button) cl.findViewById(R.id.analyzeMenuDialogPrevButton);
        Button nextButton = (Button) cl.findViewById(R.id.analyzeMenuDialogNextButton);
        Button okButton = (Button) cl.findViewById(R.id.analyzeMenuDialogOKButton);

        // Menu buttons
        Button tutorialButton = (Button) findViewById(R.id.analyzeMenuTutorialButton);
        Button searchButton = (Button) findViewById(R.id.analyzeMenuSearchButton);

        EditText editableTitle = (EditText)  findViewById(R.id.analyzeMenuEditableTitle);
        //EditText theText = (EditText) findViewById(R.id.analyzeMenuEditText);

        Bundle bundle = getIntent().getExtras();
        Uri theUri =  Uri.parse(bundle.getString("image_uri"));
        String result = bundle.getString("result");

        ImageView selectedImageView = (ImageView)  findViewById(R.id.analyzeMenuImage);
        selectedImageView.setImageURI(theUri);

        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });

        // loading screen
        AlertDialog.Builder pBuilder = new AlertDialog.Builder(AnalyzeMenu.this);
        LayoutInflater pInflater = AnalyzeMenu.this.getLayoutInflater();
        View pView = pInflater.inflate(R.layout.dialog_analyze_menu_progress, null);
        pBuilder.setView(pView);
        AlertDialog pDialog = pBuilder.create();
        pDialog.setCancelable(false);
        pDialog.setInverseBackgroundForced(false);
        //pDialog.show();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialog.show();

                Intent scanIntent = new Intent(getApplicationContext(), SearchResult.class);
                String curText = editableTitle.getText().toString();
                //scanIntent.putExtra("verified_text", curText);

                // nvm threads are cool but using join here freezes the main UI
                // and prevents loading screens from working so we just do it in the background
                // and update the UI in the meantime

                // i....kinda get it i need to look at this more, executors and handlers are
                // too abstracted away away from android programming for me to fully understand it
                // but it basically is using asynchronous programming to split UI changes
                // reliant on results of long-running tasks

                //asynchronous programming meaning multiple threads are occurring at the same time
                // so ui updating is happening while the searching is happening in the background
                // and the program WILL update the UI with the final results because it knows
                // that the UI is reliant on those final results

                // i think

                // also i was gonna use asynctasks but they're deprecated smhd
                // https://stackoverflow.com/questions/58767733/the-asynctask-api-is-deprecated-in-android-11-what-are-the-alternatives
                // so the bottom is basically copy pasta

                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                executor.execute(() -> {
                    // everything else is my work dont get it twisted!
                    try{
                        SearchRemy sr = new SearchRemy(curText);
                        sr.doTheThing(); // remnant of thread class extension, too lazy to change it back
                        String[] results = sr.getResults();
                        String resultTitle = results[0];
                        String resultPage = results[1];
                        String resultID = results[2];
                        ParseRemy pr = new ParseRemy(resultID);
                        pr.doTheThing(); // also, object oriented programming! W O W! he is objEcTin! ! !
                        String[] more_results = pr.getResults();
                        String resultArtist = more_results[0];
                        String resultBPM = more_results[1];
                        String resultJPTitle = more_results[2];

                        //Log.d("BRUH?", "da result title is " + resultTitle);
                        //Log.d("BRUH?", "da result page is " + resultPage);
                        //Log.d("BRUH?", "da result ID is " + resultID);
                        scanIntent.putExtra("result_title", resultTitle);
                        scanIntent.putExtra("result_page", resultPage);
                        scanIntent.putExtra("result_ID", resultID);
                        scanIntent.putExtra("result_artist", resultArtist);
                        scanIntent.putExtra("result_BPM", resultBPM);
                        scanIntent.putExtra("result_JPTitle", resultJPTitle);
                        scanIntent.putExtra("image_URI", theUri.toString());

                        //startActivity(scanIntent);
                    } catch (Exception e){
                        e.printStackTrace();
                        Log.e("BRUH?", "ERROR OCCURRED", e);
                    }

                    handler.post(() -> {
                        //UI Thread work here
                        pDialog.dismiss();
                        startActivity(scanIntent);
                    });
                });




            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theVF.showPrevious();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theVF.showNext();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });





        // TODO: Refactor this amazing resizing method i did by hand into another method

        // TODO: fix comments

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

        dialog.show();

    }

    public void showAnalyzeMenuExplanation(Context c){}


}
