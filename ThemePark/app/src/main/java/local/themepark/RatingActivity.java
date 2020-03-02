package local.themepark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;


public class RatingActivity extends AppCompatActivity {

    private RatingBar rBar;
    private float curRate;
    private Fragment homeFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        //keeps the data from resetting when RatingActivity is visited but not updated
        Intent i = getIntent();
        curRate = i.getFloatExtra("rateCur", 0.0f);
        ratingBar(); // click listener for the rating bar
        fragLoader();

    }

    public void ratingBar() {
        rBar = findViewById(R.id.ratingBar1);

        rBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                curRate = rBar.getRating();
                refresh();
            }
        });
    }

    public void fragLoader() {
        //ParkLogoFrag
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ParkLogoFrag pLF = new ParkLogoFrag();
        ft.add(R.id.rateCtr, pLF, "pLF");

        //RateMeFrag
        detachFrag(curRate);
        ft.add(R.id.rateCtr, homeFrag, "hFB");

        ft.commit();
    }

    //updates the fragment data when the rating changes
    public void refresh() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        detachFrag(curRate);
        ft.add(R.id.rateCtr, homeFrag, "hFB");

        ft.commit();
    }

    private void detachFrag(float curRate) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("rMFS");

        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        homeFrag = HomeBtnFrag.newInstance(curRate);
    }
}
