package com.example.kanjinokanji;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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




    }
}
