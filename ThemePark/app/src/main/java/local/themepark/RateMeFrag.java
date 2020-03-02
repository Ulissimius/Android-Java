package local.themepark;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class RateMeFrag extends Fragment {

    final private int MAX_STARS = 6;
    private String rateText;
    private String username;
    private float curRate;

    public static RateMeFrag newInstance(float a, String b) {
        RateMeFrag myFragment = new RateMeFrag();

        Bundle args = new Bundle();
        args.putFloat("rateCur", a);
        args.putString("username", b);
        myFragment.setArguments(args);

        return myFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            username = bundle.getString("username");
            curRate = bundle.getFloat("rateCur");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.fragment_rate_me_landscape, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_rate_me, container, false);
        }

        readBundle(getArguments());

        TextView tv = view.findViewById(R.id.txtRate);

        if (curRate == 0.0f){
            rateText = "User: " + username + "\nStatus: " + "New User" + "\nThis message brought to you by Fragments.";
        } else {
            rateText = "User: " + username + "\nRating: " + curRate + "/" + MAX_STARS + "\nThis message brought to you by Fragments.";
        }

        tv.setText(rateText);

        return view;
    }
}
