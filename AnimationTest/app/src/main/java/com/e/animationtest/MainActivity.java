package com.e.animationtest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean visible;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Fade());
    }

    public void newActivity (View v) {
        Bundle a = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        Intent intent = new Intent( this, TransitionActivity.class);
        startActivity(intent, a);
    }

    public void sceneSwap (View v) {
        if (i == 0) {
            sceneA(v);
            i++;
        } else if (i == 1) {
            sceneB(v);
            i++;
        } else if (i == 2) {
            sceneC(v);
            i++;
        } else if (i == 3) {
            sceneD(v);
            i = 0;
        }
    }

    public void sceneA(View v) {
        ViewGroup root = findViewById(R.id.layRoot);
        Scene s = Scene.getSceneForLayout(root, R.layout.a_scene, this);

        Transition fade = new Fade();
        TransitionManager.go(s, fade);
    }

    public void sceneB(View v) {
        ViewGroup root = findViewById(R.id.layRoot);
        Scene s = Scene.getSceneForLayout(root, R.layout.b_scene, this);

        Transition slide = new Slide();
        TransitionManager.go(s, slide);
    }

    public void sceneC(View v) {
        ViewGroup root = findViewById(R.id.layRoot);
        Scene s = Scene.getSceneForLayout(root, R.layout.c_scene, this);

        Transition explode = new Explode();
        TransitionManager.go(s, explode);
    }

    public void sceneD(View v) {
        ViewGroup root = findViewById(R.id.layRoot);
        Scene s = Scene.getSceneForLayout(root, R.layout.d_scene, this);

        Transition slide = new Slide();
        TransitionManager.go(s, slide);
    }

    public void newTransitions (View v) {
        ViewGroup root = findViewById(R.id.scene_root);
        TextView text = findViewById(R.id.textView);
        TransitionManager.beginDelayedTransition(root);
        visible = !visible;
        text.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void sceneTransition(View v) {
        ViewGroup root = findViewById(R.id.scene_root);
        Transition transition = new CustomTransition();
        transition.setDuration(4000);
        TextView textView = findViewById(R.id.textView);

        TransitionManager.beginDelayedTransition(root, transition);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        textView.setTextColor(color);
    }
}
