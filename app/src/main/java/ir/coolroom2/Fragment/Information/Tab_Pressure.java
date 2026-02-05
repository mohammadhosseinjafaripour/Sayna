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

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.R;

import static ir.coolroom2.Config.KEY_PRESSURE_V5;

public class Tab_Pressure extends Fragment {

    private static final String KEY_PRESSURE_V1 = "pressure_V1";
    private static final String KEY_PRESSURE_V2 = "pressure_V2";
    private static final String KEY_PRESSURE_V3 = "pressure_V3";
    private static final String KEY_PRESSURE_V4 = "pressure_V4";

    private static Tab_Pressure inst;
    TextView information_pressure_v1, information_pressure_v2, information_pressure_v3, information_pressure_v4, information_pressure_v5, information_pressure_v6;
    DatabaseHandler db;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_pressure, container, false);
        findViews(V);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/iransans.ttf");
        db = new DatabaseHandler(getActivity());

        if (
                Config.INFORMATION_HIGHGAS != null &&
                        Config.INFORMATION_LOWHGAS != null &&
                        Config.INFORMATION_OIL != null) {

            information_pressure_v1.setText(Config.INFORMATION_HIGHGAS);
            information_pressure_v2.setText(Config.INFORMATION_LOWHGAS);
            information_pressure_v3.setText(Config.INFORMATION_OIL);

            information_pressure_v4.setText(String.format("%.2f", Double.valueOf(Config.INFORMATION_HIGHGAS) * 0.0689475728));
            information_pressure_v5.setText(String.format("%.2f", Double.valueOf(Config.INFORMATION_LOWHGAS) * 0.0689475728));
            information_pressure_v6.setText(String.format("%.2f", Double.valueOf(Config.INFORMATION_OIL) * 0.0689475728));
        }


        return V;
    }


    private void findViews(View V) {
        information_pressure_v1 = (TextView) V.findViewById(R.id.information_pressure_v1);
        information_pressure_v2 = (TextView) V.findViewById(R.id.information_pressure_v2);
        information_pressure_v3 = (TextView) V.findViewById(R.id.information_pressure_v3);
        information_pressure_v4 = (TextView) V.findViewById(R.id.information_pressure_v4);
        information_pressure_v5 = (TextView) V.findViewById(R.id.information_pressure_v5);
        information_pressure_v6 = (TextView) V.findViewById(R.id.information_pressure_v6);
    }
}
