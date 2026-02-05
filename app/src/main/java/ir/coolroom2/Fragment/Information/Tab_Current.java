package ir.coolroom2.Fragment.Information;

/**
 * Created by JFP on 11/29/2017.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.R;

public class Tab_Current extends Fragment {

    private static Tab_Current inst;

    TextView information_current_v1,
            information_current_v2,
            information_current_v3,
            information_current_v4,
            information_current_v5,
            information_current_v6,
            information_current_v7,
            information_current_v8;
    DatabaseHandler db;
    private static final String KEY_CURRENT_V1 = "current_V1";
    private static final String KEY_CURRENT_V2 = "current_V2";

    private TabLayout tabLayout;
    private ViewPager viewPager;

    Typeface typeface;

    @Override
    public void onResume() {
        super.onResume();
        inst = this;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    HashMap<String, String> map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.tab_current, container, false);


        information_current_v1 = (TextView) V.findViewById(R.id.information_current_v1);
        information_current_v2 = (TextView) V.findViewById(R.id.information_current_v2);
        information_current_v3 = (TextView) V.findViewById(R.id.information_current_v3);
        information_current_v4 = (TextView) V.findViewById(R.id.information_current_v4);
        information_current_v5 = (TextView) V.findViewById(R.id.information_current_v5);
        information_current_v6 = (TextView) V.findViewById(R.id.information_current_v6);
        information_current_v7 = (TextView) V.findViewById(R.id.information_current_v7);
        information_current_v8 = (TextView) V.findViewById(R.id.information_current_v8);

        Float ova = (Float.parseFloat(Config.INFORMATION_CURRENTOVAPERATOR_R) +
                Float.parseFloat(Config.INFORMATION_CURRENTOVAPERATOR_S) +
                Float.parseFloat(Config.INFORMATION_CURRENTOVAPERATOR_T))
                / 3;

        Float com = (Float.parseFloat(Config.INFORMATION_CURRENTCOMPRESSOR_R) +
                Float.parseFloat(Config.INFORMATION_CURRENTCOMPRESSOR_S) +
                Float.parseFloat(Config.INFORMATION_CURRENTCOMPRESSOR_T))
                / 3;
        information_current_v1.setText(ova + "");
        information_current_v2.setText(Float.parseFloat(Config.INFORMATION_CURRENTOVAPERATOR_R) + "");
        information_current_v3.setText(Float.parseFloat(Config.INFORMATION_CURRENTOVAPERATOR_S) + "");
        information_current_v4.setText(Float.parseFloat(Config.INFORMATION_CURRENTOVAPERATOR_T) + "");
        information_current_v5.setText(com + "");
        information_current_v6.setText(Float.parseFloat(Config.INFORMATION_CURRENTCOMPRESSOR_R) + "");
        information_current_v7.setText(Float.parseFloat(Config.INFORMATION_CURRENTCOMPRESSOR_S) + "");
        information_current_v8.setText(Float.parseFloat(Config.INFORMATION_CURRENTCOMPRESSOR_T) + "");


        return V;
    }

    public static Tab_Current instance() {
        return inst;
    }


}
