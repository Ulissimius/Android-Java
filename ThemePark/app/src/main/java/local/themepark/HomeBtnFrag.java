package local.themepark;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeBtnFrag extends Fragment {

    private float curRate;

    public static HomeBtnFrag newInstance(float a) {
        HomeBtnFrag myFragment = new HomeBtnFrag();

        Bundle args = new Bundle();
        args.putFloat("rateCur", a);
        myFragment.setArguments(args);

        return myFragment;
    }

    public static HomeBtnFrag newInstance() {
        HomeBtnFrag myFragment = new HomeBtnFrag();

        Bundle args = new Bundle();
        myFragment.setArguments(args);

        return myFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            curRate = bundle.getFloat("rateCur");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.fragment_home_btn_landscape, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_home_btn, container, false);
        }

        readBundle(getArguments());

        Button homeBtn = view.findViewById(R.id.btnHome);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            //Does exactly what it did before, now with *optional* data being passed
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("rateCur", curRate);

                startActivity(intent);
            }
        });
        return view;
    }
}
