package local.themepark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {

    private boolean moreLess = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.help_landscape);
        } else {
            setContentView(R.layout.activity_help);
        }

        fragLoader();

        ViewGroup root = findViewById(R.id.frmLayAbtUs);
        Scene s = Scene.getSceneForLayout(root, R.layout.help_animation_landscape2, this);

        Transition slide = new Slide();
        TransitionManager.go(s, slide);
        TransitionManager.endTransitions(root);

    }

    public void showMoreBtn(View v) {
        Button btnMore = findViewById(R.id.btnMore);
        Button btnLess = findViewById(R.id.btnLess);
        if (moreLess) {
            ViewGroup root = findViewById(R.id.frmLayAbtUs);
            Scene s = Scene.getSceneForLayout(root, R.layout.help_animation_landscape, this);

            Transition fade = new Fade();
            TransitionManager.go(s, fade);

            btnMore.setVisibility(View.INVISIBLE);
            btnLess.setVisibility(View.VISIBLE);
            moreLess = false;
        } else {
            ViewGroup root = findViewById(R.id.frmLayAbtUs);
            Scene s = Scene.getSceneForLayout(root, R.layout.help_animation_landscape2, this);

            Transition fade = new Fade();
            TransitionManager.go(s, fade);

            btnMore.setVisibility(View.VISIBLE);
            btnLess.setVisibility(View.INVISIBLE);
            moreLess = true;
        }


        ViewGroup root = findViewById(R.id.frmLayAbtUs);
        Scene s = Scene.getSceneForLayout(root, R.layout.help_animation_landscape, this);

        Transition slide = new Slide();
        TransitionManager.go(s, slide);
    }

    public void fragLoader() {
        //ParkLogoFrag
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ParkLogoFrag pLF = new ParkLogoFrag();
        ft.add(R.id.helpCtr, pLF);

        //HomeBtnFrag
        Fragment homeFrag = HomeBtnFrag.newInstance();
        ft.add(R.id.helpCtr, homeFrag, "hFB");

        ft.commit();
    }
}
