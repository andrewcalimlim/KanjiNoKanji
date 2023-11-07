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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.text.Text;

public class ScanMenu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO: change buttons to image cropping reminder (XML)
        //TODO: diagnose error when upload button is used more than once
        //TODO: loading screens lol

        //TODO: actual good UI layout lol
        //TODO: refactor functionality? idk

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_menu);

        int SELECT_PICTURE = 200;

        // this is how u edit xml attributes programmatically yo!!! remember?

        Button proceedButton = (Button) findViewById(R.id.scan_analyzeButton);

        // Scan Menu's proceed button is disabled and semi-transparent on startup because no
        // image has been uploaded yet
        proceedButton.setEnabled(false);
        proceedButton.setAlpha(.5f);

        TextView noImageTextView = (TextView) findViewById(R.id.scan_noImageText);
        ImageView selectedImageView = (ImageView) findViewById(R.id.scan_selectedImage);
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
                        proceedButton.setEnabled(true);
                        proceedButton.setAlpha(1f);
                        uri_string.append(selectedImageUri.toString());

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

                builder.setNegativeButton("Hold Up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //https://developer.android.com/training/data-storage/shared/photopicker

                        pickMedia.launch(new PickVisualMediaRequest.Builder()
                                .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                                .build());


                }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        Button analyzeButton = (Button) findViewById(R.id.scan_analyzeButton);
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
