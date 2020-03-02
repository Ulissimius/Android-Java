package com.e.cameraapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileShareActivity extends AppCompatActivity {

    // The path to the "images" subdirectory
    private File imagesDir;
    // Array of files in the images subdirectory
    File[] imageFiles;
    // Array of filenames corresponding to imageFiles
    String[] imageFilenames;
    Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_share);

        // Set up an Intent to send back to apps that request a file
        resultIntent = new Intent("com.e.cameraapp.ACTION_RETURN_FILE");
        // Get the files/images subdirectory;
        imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Get the files in the images subdirectory
        imageFiles = imagesDir.listFiles();
        // Set the Activity's result to null to begin with
        setResult(Activity.RESULT_CANCELED, null);
        /*
         * Display the file names in the ListView fileListView.
         * Back the ListView with the array imageFilenames, which
         * you can create by iterating through imageFiles and
         * calling File.getAbsolutePath() for each File
         */
        int i = 0;
        imageFilenames = new String[imageFiles.length];
        try {
            for (File f : imageFiles) {
                imageFilenames[i] = f.getAbsolutePath();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListView mFileListView = findViewById(R.id.ListView);

        //Create a list from string array elements
        final List<String> fileList = new ArrayList<>(Arrays.asList(imageFilenames));

        //Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);

        //DataBind ListView with items from ArrayAdapter
        mFileListView.setAdapter(arrayAdapter);

        // Define a listener that responds to clicks on a file in the ListView
        mFileListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    /*
                     * When a filename in the ListView is clicked, get its
                     * content URI and send it to the requesting app
                     */
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                        /*
                         * Get a File for the selected file name.
                         * Assume that the file names are in the
                         * imageFilename array.
                         */
                        File requestFile = new File(imageFilenames[position]);
                        /*
                         * Most file-related method calls need to be in
                         * try-catch blocks.
                         */
                        // Use the FileProvider to get a content URI
                        Uri fileUri = null;

                        try {
                            fileUri = FileProvider.getUriForFile(FileShareActivity.this, "com.e.cameraapp", requestFile);
                        } catch (IllegalArgumentException e) {
                            Log.e("File Selector", "The selected file can't be shared: " + requestFile.toString());
                        }

                        if (fileUri != null) {
                            // Grant temporary read permission to the content URI
                            resultIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            // Put the Uri and MIME type in the result Intent
                            resultIntent.setDataAndType(fileUri, getContentResolver().getType(fileUri));
                            // Set the result
                            FileShareActivity.this.setResult(Activity.RESULT_OK, resultIntent);
                        } else {
                            resultIntent.setDataAndType(null, "");
                            FileShareActivity.this.setResult(RESULT_CANCELED, resultIntent);
                        }
                    }
                }
        );
    }

    public void onDoneClick(View v) {
        // Associate a method with the Done button
        finish();
    }
}
