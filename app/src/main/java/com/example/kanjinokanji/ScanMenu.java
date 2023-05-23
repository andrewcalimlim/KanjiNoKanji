package com.example.kanjinokanji;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScanMenu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_menu);

        Button uploadMenuButton = (Button) findViewById(R.id.scan_uploadButton);
        uploadMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uploadReminderMessage = "When uploading an image, please crop it as much" +
                        " as you can.";

                AlertDialog.Builder builder = new AlertDialog.Builder(ScanMenu.this);
                builder.setTitle("Upload Reminder");
                builder.setMessage(uploadReminderMessage);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setNegativeButton("NVM then", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });





    }
}
