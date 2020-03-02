package local.themepark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private float curRate;
    private String username;
    private SharedPreferences prefs;
    private SharedPreferences prefs1;
    private EditText editName;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private Fragment rateFrag;
    private boolean btnPress = false;

    /*
    x (10pts) Implement logic that checks for specific Android Version
    x (10pts) Display different layouts for Landscaped vs. Portrait orientations
    __ (10pts) Implement a Google Maps Implicit Intent
    x (20pts) Implement data sharing via Intent Extras
    x (20pts) Implement data persistence via Shared Preferences
    x (20pts) Implement any type of Fragment in your application
    __ (10pts) Add a system permission to your application
    __ (10pts) Ask the user to grant a system permission in your application
    x (10pts) Implement some type of animation in your application
    __ (10pts) Get some piece of data from an external application
    x (10pts) Add a JUnit test to your application
    __ (10pts) Demonstrate file sharing from your application
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.main_horizontal);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }

        versionCheck();
        fragmentLoaderBeefy();
    }

    //passes the activity to the about us page
    public void helpPage(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    //passes the activity to the rating page, along with values for/from the page
    public void ratePage(View view) {
        //if (editName.getText().toString().equalsIgnoreCase("")){
        if (!btnPress) {
            Toast.makeText(getApplication(),"Please login to visit this page!",Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent( this, RatingActivity.class);
            intent.putExtra("rateCur", curRate);
            startActivity(intent);
        }
    }

    //The much bulkier version of fragmentLoader.
    //Loads the fragments onto the page and manages the shared preferences
    public void fragmentLoaderBeefy() {
        fm = getSupportFragmentManager(); //Manager only needs to be set once. Controls all fragments
        ft = fm.beginTransaction(); //Begins a new fragment transaction

        //ParkLogoFrag
        ParkLogoFrag pLF = new ParkLogoFrag();

        ft.add(R.id.mainActiviyCtr, pLF);

        //RateMeFrag
        //This should load first, without a name, to recall the last users name/rating
        prefs1 = getSharedPreferences("default", MODE_PRIVATE); //pfrefs1 is the placeholder that displays when you arrive at MainActivity

        loadIntent(); //Loads the intent if you just came from RatingActivity

        username = prefs1.getString("currentName", ""); //Sets the last known username to prefs1
        prefs = getSharedPreferences(username, MODE_PRIVATE); // Loads up that users personal preference

        if (curRate == 0.0f) { //If the intent returns a default value this will pull in the users last known rating
            curRate = prefs1.getFloat("currentRating", 0.0f);
        }

        if ((curRate > 0 && curRate <= 6) && !username.equals("")) { //Only allows a valid rating to load the fragment
            prefs.edit().putFloat("currentRating", curRate).apply(); //This lives right here so both prefs and prefs1 set their data for the next time they are called
            prefs1.edit().putFloat("currentRating", curRate).apply();
            detachFrag(curRate, username);
            ft.add(R.id.mainActiviyCtr, rateFrag, "rMFS"); //Loads RateMeFrag View on the page

        } else if (!username.equals("")) {
            detachFrag(curRate, username);

            ft.add(R.id.mainActiviyCtr, rateFrag, "rMFS");
        }

        ft.commit(); //Commits the fragment load.

        //Button controls a user "logging in"
        Button loginButton = findViewById(R.id.btnLogin);

        editName = findViewById(R.id.etxtLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lock this behind a username
                ft = fm.beginTransaction();

                if (editName.getText().toString().equalsIgnoreCase("")) { //Tells the user to input something before they continue
                    Toast.makeText(getApplication(), "Please enter a name", Toast.LENGTH_LONG).show();
                } else { //Else, either loads up the input username's old profile, or creates a new one.
                    btnPress = true;
                    username = editName.getText().toString();
                    curRate = 0.0f; //Reset the curRate variable from the previous intent call

                    prefs = getSharedPreferences(username, MODE_PRIVATE);

                    curRate = prefs.getFloat("currentRating", 0.0f);

                    if (curRate > 0 && curRate <= 6) { //Allows only a valid rating to load the fragment
                        prefs.edit().putFloat("currentRating", curRate).apply(); //These are here to update data if it has changed. Some of them are probably redundant
                        prefs1.edit().putString("currentName", username).apply();
                        prefs1.edit().putFloat("currentRating", curRate).apply();
                        detachFrag(curRate, username);
                        ft.add(R.id.mainActiviyCtr, rateFrag);
                        ft.commit();
                    } else { //Allows only new users
                        prefs1.edit().putString("currentName", username).apply(); //Saves the new users name to display immediately.
                        prefs1.edit().putFloat("currentRating", curRate).apply();
                        detachFrag(curRate, username);
                        ft.add(R.id.mainActiviyCtr, rateFrag);
                        ft.commit();
                    }
                }
            }
        });
    }

    //Pulls data from the intent
    private void loadIntent() {
        Intent i = getIntent();
        curRate = i.getFloatExtra("rateCur", 0.0f);
    }

    //detachFrag acts like a fragment refresher. Allows the data in the fragment to be updated without stacking infinite fragment views.
    private void detachFrag(float curRate, String username) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("rMFS");

        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        rateFrag = RateMeFrag.newInstance(curRate, username);
    }

    //-------------------------Currently unused methods below-------------------------

    //Checks the phones android version against Oreo
    //Current button is hidden.
    private void versionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast.makeText(getApplicationContext(), "Thank you for staying up to date!\nMinimum Version: " + Build.VERSION_CODES.O + "\nCurrent Version: "+Build.VERSION.SDK_INT,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "You're out of date!\nconsider upgrading to Oreo!\nCurrent Version: "+Build.VERSION.SDK_INT + "Recommended Version: " + Build.VERSION_CODES.O,Toast.LENGTH_SHORT).show();
        }
    }

    //Checks for and displays the current orientation of the phone
    //Currently unused
    private void setOrientationMessage() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this,"landscape",Toast.LENGTH_SHORT).show();
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this,"portrait",Toast.LENGTH_SHORT).show();
        }
    }

    //Used for the unit test. Passes in a string in place of the text box and automatically presses the button.
    public void fragmentLoaderBeefyTest(final String s) {
        fm = getSupportFragmentManager(); //Manager only needs to be set once. Controls all fragments
        ft = fm.beginTransaction(); //Begins a new fragment transaction

        //ParkLogoFrag
        ParkLogoFrag pLF = new ParkLogoFrag();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ft.add(R.id.mainActiviyCtr, pLF);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ft.add(R.id.mainActiviyCtr, pLF);
        }

        //RateMeFrag
        //This should load first, without a name, to recall the last users name/rating
        prefs1 = getSharedPreferences("default", MODE_PRIVATE); //pfrefs1 is the placeholder that displays when you arrive at MainActivity

        loadIntent(); //Loads the intent if you just came from RatingActivity

        username = prefs1.getString("currentName", ""); //Sets the last known username to prefs1
        prefs = getSharedPreferences(username, MODE_PRIVATE); // Loads up that users personal preference

        if (curRate == 0.0f) { //If the intent returns a default value this will pull in the users last known rating
            curRate = prefs1.getFloat("currentRating", 0.0f);
        }

        if ((curRate > 0 && curRate <= 6) && !username.equals("")) { //Only allows a valid rating to load the fragment
            prefs.edit().putFloat("currentRating", curRate).apply(); //This lives right here so both prefs and prefs1 set their data for the next time they are called
            prefs1.edit().putFloat("currentRating", curRate).apply();
            detachFrag(curRate, username);
            ft.add(R.id.mainActiviyCtr, rateFrag, "rMFS"); //Loads RateMeFrag View on the page

        } else if (!username.equals("")) {
            detachFrag(curRate, username);

            ft.add(R.id.mainActiviyCtr, rateFrag, "rMFS");
        }

        ft.commit(); //Commits the fragment load.

        editName = findViewById(R.id.etxtLogin);

        ft = fm.beginTransaction();

        username = s;
        curRate = 0.0f; //Reset the curRate variable from the previous intent call

        prefs = getSharedPreferences(username, MODE_PRIVATE);

        curRate = prefs.getFloat("currentRating", 0.0f);

        if (curRate > 0 && curRate <= 6) { //Allows only a valid rating to load the fragment
            prefs.edit().putFloat("currentRating", curRate).apply(); //These are here to update data if it has changed. Some of them are probably redundant
            prefs1.edit().putString("currentName", username).apply();
            prefs1.edit().putFloat("currentRating", curRate).apply();
            detachFrag(curRate, username);
            ft.add(R.id.mainActiviyCtr, rateFrag);
            ft.commit();
        } else { //Allows only new users
            prefs1.edit().putString("currentName", username).apply(); //Saves the new users name to display immediately.
            prefs1.edit().putFloat("currentRating", curRate).apply();
            detachFrag(curRate, username);
            ft.add(R.id.mainActiviyCtr, rateFrag);
            ft.commit();
        }
    }
}
