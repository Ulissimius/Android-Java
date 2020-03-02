package com.e.externalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

//For this assignment you must
//        • Meet all of the items listed in the attached Java Coding Standard (20 pts)
//        • Open an email from your application (40 pts)
//        • Open a Google search page from your application (40 pts)

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openMail(View v) {
        Intent mIntent = new Intent(Intent.ACTION_SEND);

        mIntent.setType("type/text");

        mIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"qwertyjones048@gmail.com"});
        mIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject: New Subject");
        mIntent.putExtra(Intent.EXTRA_TEXT, "Body Text Goes Here");

        startActivity(mIntent);
    }

    public void openGoogleS(View v) {
        EditText eTxt = findViewById(R.id.editText);
        String userData = eTxt.getText().toString();

        Uri gSearch = Uri.parse("https://www.google.com/search?q="+userData);

        Intent mIntent = new Intent(Intent.ACTION_VIEW, gSearch);

        startActivity(mIntent);
    }

    public void openMaps(View v) {
        EditText eTxt = findViewById(R.id.editText);
        String userData = eTxt.getText().toString();

        Uri location = Uri.parse("geo:0,0?q="+userData);

        Intent mIntent = new Intent(Intent.ACTION_VIEW, location);

        startActivity(mIntent);
    }
}
