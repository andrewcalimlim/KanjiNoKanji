package com.example.kanjinokanji;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;

public class SearchResult extends AppCompatActivity {

    // TODO: actually implement all the data u've scraped off of RemyWiki onto the Search Result screen!

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        EditText searchResultResult = findViewById(R.id.searchResultResult);

        Bundle bundle = getIntent().getExtras();
        String resultTitle = bundle.getString("result_title");
        String resultPage = bundle.getString("result_page");
        String resultID = bundle.getString("result_ID");
        String resultArtist = bundle.getString("result_artist");
        String resultBPM = bundle.getString("result_BPM");

        Log.d("BRUH", "result artist: " + resultArtist);
        Log.d("BRUH", "result BPM: " + resultBPM);



        if(resultPage != null){
            searchResultResult.setText(resultPage);
            //Log.d("BRUH?", resultTitle);
        }
        else{

            searchResultResult.setText("No valid song on RemyWiki found for this text.");
        }

    }
}
