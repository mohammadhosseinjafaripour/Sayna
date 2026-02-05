package ir.coolroom2.Fragment.Information;

/**
 * Created by JFP on 11/29/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ir.coolroom2.Config;
import ir.coolroom2.R;


public class Tab_Voltage extends Fragment {

    TextView information_voltage_v1, information_voltage_v2,
            information_voltage_v3, information_voltage_v4,
            information_voltage_v5, information_voltage_v6,
            information_voltage_v7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab_voltage, container, false);

        information_voltage_v1 = V.findViewById(R.id.information_voltage_v1);
        information_voltage_v2 = V.findViewById(R.id.information_voltage_v2);
        information_voltage_v3 = V.findViewById(R.id.information_voltage_v3);
        information_voltage_v4 = V.findViewById(R.id.information_voltage_v4);
        information_voltage_v5 = V.findViewById(R.id.information_voltage_v5);
        information_voltage_v6 = V.findViewById(R.id.information_voltage_v6);
        information_voltage_v7 = V.findViewById(R.id.information_voltage_v7);

        /*
        * real = vr+vs/2
          image = vs * 0.866
          vrs = sqrt (real*real+image*image)
        * */

        double real, image, vrs;

        real = (Integer.parseInt(Config.INFORMATION_RTON) + Integer.parseInt(Config.INFORMATION_STON)) / 2;
        image = Float.parseFloat(Config.INFORMATION_STON) * 0.866;
        vrs = Math.sqrt(((real * real) + (image * image)));

        if (!Config.general_data_setting.isEmpty()) {
            information_voltage_v1.setText(String.valueOf(vrs));

            real = (Integer.parseInt(Config.INFORMATION_RTON) + Integer.parseInt(Config.INFORMATION_TTON)) / 2;
            image = Float.parseFloat(Config.INFORMATION_TTON) * 0.866;
            vrs = Math.sqrt(((real * real) + (image * image)));
            information_voltage_v2.setText(String.valueOf(vrs));

            real = (Integer.parseInt(Config.INFORMATION_STON) + Integer.parseInt(Config.INFORMATION_TTON)) / 2;
            image = Float.parseFloat(Config.INFORMATION_TTON) * 0.866;
            vrs = Math.sqrt(((real * real) + (image * image)));
            information_voltage_v3.setText(String.valueOf(vrs));

            information_voltage_v4.setText(Config.INFORMATION_RTON);
            information_voltage_v5.setText(Config.INFORMATION_STON);
            information_voltage_v6.setText(Config.INFORMATION_TTON);
            if (Config.general_data_setting.get(Config.INFORMATION_PHASE_COUNT) != null)
                if (Config.general_data_setting.get(Config.INFORMATION_PHASE_COUNT).length() != 0)
                    information_voltage_v7.setText((Config.general_data_setting.get(Config.INFORMATION_PHASE_COUNT).equals("0") ? "تک فاز" : "سه فاز"));
        }

        return V;
    }

}
