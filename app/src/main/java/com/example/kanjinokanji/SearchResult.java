package com.example.kanjinokanji;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;

public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        EditText searchResultResult = findViewById(R.id.searchResultResult);

        Bundle bundle = getIntent().getExtras();
        String verifiedText = bundle.getString("verified_text");

        Log.d("SLATT", verifiedText);

        // https://developer.android.com/reference/java/lang/Thread

        // no, Tasks will not resolve android.os.NetworkOnMainThreadException
        // basically this occurs because trying to do network functionality in the main
        // thread of ur program will cause it to freeze and look slow
        // because that totally matters for ur personal project that like 5 people will use but ok

        // introducing.....THREADS
        // basically reworked searchRemy class (the remywiki searching functionality) to become
        // a thread object aka KanjiNoKanji is now technically a multi-threaded application
        // :OkayChamp: :thumbsup:


        try {
            //THREADS BABY
            SearchRemy sr = new SearchRemy(verifiedText);
            sr.start(); //basically creates da thread instance (as an object)
            sr.join(); // basically makes program wait forever til it the thread dies
            String[] results = sr.getResults();
            String resultTitle = results[0];
            String resultPage = results[1];
            String resultID = results[2];
            ParseRemy pr = new ParseRemy(resultID);
            pr.start();
            pr.join();



            if(resultPage != null){
                searchResultResult.setText(resultPage);
                Log.d("BRUH?", resultTitle);
            }
            else{

                searchResultResult.setText("No valid song on RemyWiki found for " + verifiedText + ".");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BRUH?", "ERROR OCCURRED", e);
        }

    }
}
