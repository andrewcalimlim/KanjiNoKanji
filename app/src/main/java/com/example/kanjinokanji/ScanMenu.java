package com.example.kanjinokanji;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.text.Text;

public class ScanMenu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_menu);

        int SELECT_PICTURE = 200;

        // on start-up the Analyze button is disabled and semi-transparent
        // because no image has been uploaded yet
        Button analyzeButton = (Button) findViewById(R.id.scan_analyzeButton);
        analyzeButton.setEnabled(false);
        analyzeButton.setAlpha(.5f);

        TextView noImageTextView = (TextView) findViewById(R.id.scan_noImageText);
        ImageView selectedImageView = (ImageView) findViewById(R.id.scan_selectedImage);

        // image URI needs to persist so we will make it global to the class
        // and since it can be updated on this screen several times it should be a mutable
        // string aka STRING BUILDER TIME!!! :OkayChamp:
        StringBuilder uri_string = new StringBuilder("");

        // PhotoPicker activity will be in single-select mode
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new PickVisualMedia(), selectedImageUri -> {
                    if (selectedImageUri != null){
                        Log.d("PhotoPicker", "Selected URI: " + selectedImageUri);

                        // image has been selected from phone, display it on-screen and update
                        // the scan menu UI (show the image, re-enable proceed button)
                        selectedImageView.setImageURI(selectedImageUri);
                        noImageTextView.setVisibility(View.INVISIBLE);
                        analyzeButton.setEnabled(true);
                        analyzeButton.setAlpha(1f);
                        uri_string.append(selectedImageUri.toString()); //updating uri string

                    } else {
                        Log.d("PhotoPicker", "No media selected");

                    }
                });

        Button selectButton = (Button) findViewById(R.id.scan_selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ScanMenu.this);
                builder.setTitle("Image Cropping Reminder");

                // adding text and images via custom XML!
                LayoutInflater inflater = ScanMenu.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_scan_menu, null);
                builder.setView(dialogView);
                ConstraintLayout cl = (ConstraintLayout) dialogView.findViewById(R.id.scan_dialog_constraint_layout);
                AlertDialog dialog = builder.create();

                Button holdUpButton = (Button) cl.findViewById(R.id.scan_dialog_hold_up_button);
                holdUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

                Button okButton = (Button) cl.findViewById(R.id.scan_dialog_ok_button);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // using a new Android photopicker UI that doesn't require a bunch of permissions
                        //https://developer.android.com/training/data-storage/shared/photopicker

                        // which is cool and easy to implement...
                        // but also buggy as seen by this error that won't go away
                        // yet it still compiles so whatever

                        dialog.dismiss();
                        uri_string.setLength(0); // changing image ui means string will be changed
                        // so time to clear the uri string

                        pickMedia.launch(new PickVisualMediaRequest.Builder()
                                .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                                .build());


                    }
                });

                dialog.show();

            }
        });

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri theUri = Uri.parse(uri_string.toString());

                //async task? idk
                // start activity of loading screen?

                ImageProcessing.imageProcess(getApplicationContext(), theUri).addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text result) {
                        Intent analyzeIntent = new Intent(getApplicationContext(), AnalyzeMenu.class);
                        analyzeIntent.putExtra("image_uri", uri_string.toString());
                        analyzeIntent.putExtra("result", result.getText());
                        Log.d("SLATT", result.getText());
                        startActivity(analyzeIntent);

                    }
                });

                // TODO: loading screen to show that app is not freezing
                // TODO: also an OnFailure notification in case Google OCR not working

            }
        });

    }
}
