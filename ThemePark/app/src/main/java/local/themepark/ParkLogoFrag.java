package local.themepark;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ParkLogoFrag extends Fragment {

    //Takes a String
//    ParkLogoFrag(String parkTitle) {
//        this.txtString = parkTitle;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Reference the view
        View view = inflater.inflate(R.layout.fragment_park_logo, container, false);
        //Reference the Text Box
        TextView tv = view.findViewById(R.id.txtTitle);
        //Overwrite the Text of the Text box
        String txtString = "Luna Park V0.132";
        tv.setText(txtString);

        return view;
    }
}
