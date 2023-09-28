package com.example.kanjinokanji;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

    public boolean isTextTooLarge(TextView tv){
        TextPaint paint = tv.getPaint();
        Rect rect = new Rect();
        String text = String.valueOf(tv.getText());
        paint.getTextBounds(text, 0, text.length(), rect);
        if(rect.height() > tv.getHeight() || rect.width() > tv.getWidth()){
            return true;
        }
        return false;
    }

    public static boolean isTextTruncated( String text, TextView textView )
    {
        if ( textView != null && text != null )
        {

            Layout layout = textView.getLayout();
            if ( layout != null )
            {
                int lines = layout.getLineCount();
                if ( lines > 0 )
                {
                    int ellipsisCount = layout.getEllipsisCount( lines - 1 );
                    if ( ellipsisCount > 0 )
                    {
                        return true;
                    }
                }
            }

        }
        return false;
    }
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

    }
}
