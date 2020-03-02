package com.e.cameraapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final private int REQUEST_IMAGE_CAPTURE = 1;
    static final private int REQUEST_TAKE_PHOTO = 2;
    static final private int REQUEST_CAMERA = 3;
    private String mCurrentPhotoPath;
    private View empty = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toFileShare(View v) {
        Intent intFileShare = new Intent(this, FileShareActivity.class);
        startActivity(intFileShare);
    }

    //Onclick event tied to the image view, opens the google picture gallery inside the app
    public void openGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media/"));
        startActivity(intent);
    }

    //Onclick event tied to button 1, sends you to camera and requests the photo the user takes
    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Onclick (goes through the Permission Check first) opens the camera and saves the picture taken to external memory
    public void TakeAndSave(View v) {
        Intent pictureInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureInt.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.e.cameraapp",photoFile);
                pictureInt.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);

                startActivityForResult(pictureInt,REQUEST_TAKE_PHOTO);
            }
        }
    }

    //~*~*~*~*~*~*~*~*~*~* Start Permission Check *~*~*~*~*~*~*~*~*~*~
    public void showCamera(View v) {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            TakeAndSave(empty);
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Camera permission is needed to show the camera preview.", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                TakeAndSave(empty);
            } else {
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    //~*~*~*~*~*~*~*~*~*~* End Permission Check *~*~*~*~*~*~*~*~*~*~

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
        //Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //~*~*~*~*~*~*~*~*~*~* Start Results *~*~*~*~*~*~*~*~*~*~
    //Result method handles both picture taking method results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView mImageView = findViewById(R.id.imageView);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }

        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            System.out.println("Completed and Saved");
            galleryAddPic();
        }
    }

    //Adds the picture from Take&Save to memory on the phone
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    //~*~*~*~*~*~*~*~*~*~* End Results *~*~*~*~*~*~*~*~*~*~
}
