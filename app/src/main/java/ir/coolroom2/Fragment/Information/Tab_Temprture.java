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
import ir.coolroom2.Sms.SmsCodeAndDecode;

public class Tab_Temprture extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    Typeface typeface;

    private static Tab_Temprture inst;
    TextView information_temperature_v1;
    DatabaseHandler db;
    HashMap<String, String> map;


    private static final String KEY_TEMPERATURE_V1 = "temperature_V1";
    private static final String KEY_TEMPERATURE_V2 = "temperature_V2";
    private static final String KEY_TEMPERATURE_V3 = "temperature_V3";
    private static final String KEY_TEMPERATURE_V4 = "temperature_V4";

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


    public Tab_Temprture() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_tempreture, container, false);
        db = new DatabaseHandler(getActivity());

        information_temperature_v1 = (TextView) V.findViewById(R.id.information_temperature_v1);

        if (Config.INFORMATION_TEMPRETUREV1.length() > 0) {

            information_temperature_v1.setText(String.valueOf((Config.INFORMATION_TEMPRETUREV1)) + " C ");

        }

        return V;
    }

    public static Tab_Temprture instance() {
        return inst;
    }

}
