package com.example.kanjinokanji;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class ScanMenu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_menu);

        int SELECT_PICTURE = 200;

        Button uploadMenuButton = (Button) findViewById(R.id.scan_uploadButton);
        uploadMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ScanMenu.this);
                builder.setTitle("Image Cropping Reminder");

                // adding text and images via custom XML!
                LayoutInflater inflater = ScanMenu.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog, null);
                builder.setView(dialogView);

                builder.setNegativeButton("Hold Up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ActivityResultLauncher<Pick> pickMedia;
                        //https://developer.android.com/training/data-storage/shared/photopicker

                        // use this tutorial

                        // PhotoPicker activity will be in single-select mode
                        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                                registerForActivityResult(new PickVisualMedia(), uri -> {
                                    if (uri != null){
                                        Log.d("PhotoPicker", "Selected URI: " + uri);
                                    } else {
                                        Log.d("PhotoPicker", "No media selected");
                                    }
                        });
                        /*
                        pickMedia.launch(new PickVisualMediaRequest.Builder()
                                .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                                .build());
                        */



                }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });





    }
}
