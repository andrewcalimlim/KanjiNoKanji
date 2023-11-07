package com.example.kanjinokanji;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResult extends AppCompatActivity {

    // so link works i guess
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle bundle = getIntent().getExtras();
        String resultTitle = bundle.getString("result_title");
        String resultPage = bundle.getString("result_page");
        String resultID = bundle.getString("result_ID");
        String resultArtist = bundle.getString("result_artist");
        String resultBPM = bundle.getString("result_BPM");
        String resultJPTitle = bundle.getString("result_JPTitle");
        String imageURI_string = bundle.getString("image_URI");
        Log.d("BRUH?", "image uri string: " + imageURI_string);

        //setting layout programatically

        //first thee uploaded image to remind user of their input
        ImageView selectedImageView = (ImageView) findViewById(R.id.searchResult_uploadedImage);
        Uri imageURI = Uri.parse(imageURI_string);
        selectedImageView.setImageURI(imageURI);

        TextView jpTitle = (TextView) findViewById(R.id.searchResult_JPTitle);
        jpTitle.setText(resultJPTitle);

        TextView songTitle = (TextView) findViewById(R.id.searchResult_titleData);
        songTitle.setText(resultTitle);

        TextView artistName = (TextView) findViewById(R.id.searchResult_artistData);
        artistName.setText(resultArtist);

        TextView bpmData = (TextView) findViewById(R.id.searchResult_BPMData);
        bpmData.setText(resultBPM);

        // https://stackoverflow.com/questions/9290651/make-a-hyperlink-textview-in-android
        TextView rw_link = (TextView) findViewById(R.id.searchResult_link);
        rw_link.setClickable(true);
        rw_link.setMovementMethod(LinkMovementMethod.getInstance());
        String code = "<a href='" + resultPage + "'> RemyWiki Page for more information </a>";

        rw_link.setText(Html.fromHtml(code, Html.FROM_HTML_MODE_COMPACT));


    }
}
