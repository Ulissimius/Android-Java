package com.e.animationtest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Slide());
    }

    public void newActivity (View v) {
        Bundle a = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent, a);
    }
}
