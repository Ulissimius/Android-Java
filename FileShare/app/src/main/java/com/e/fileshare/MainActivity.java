package com.e.fileshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static final private int REQUEST_DATA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void requestFiles(View v) {
        Intent mRequestFileIntent = new Intent(Intent.ACTION_PICK);
        mRequestFileIntent.setType("image/*");
        startActivityForResult(mRequestFileIntent, REQUEST_DATA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            //Exit without doing anything else
            Toast.makeText(this, "Something", Toast.LENGTH_SHORT).show();
        } else {
            //Get the file's content URI from teh incoming Intent
            Uri returnUri = data.getData();

            ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageURI(returnUri);

            //Takes the file path returned by URI and takes only the file name
            String[] separated = returnUri.toString().split("files/");
            TextView tV2 = findViewById(R.id.textView2);
            tV2.setText("File Name\n"+separated[1]);

            //Takes the full file path and mime type and displays it
            String mimeType = getContentResolver().getType(returnUri);
            String concat = "Full File Path\n" + mimeType + " : " + returnUri.toString();
            TextView textView = findViewById(R.id.textView);
            textView.setText(concat);
        }
    }
}
