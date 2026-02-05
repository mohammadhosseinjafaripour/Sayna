package ir.coolroom2.Fragment.Information;

/**
 * Created by JFP on 11/29/2017.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.R;

public class Tab_Signal extends Fragment {

    TextView signal_brodat, brodat_mode, signal_termostat, termostat_mode,
            defrost_mode, signal_defrost, signal_compressor,
            compressor_mode, signal_ovaperator, ovaperator_mode,
            signal_oil, oil_mode, signal_highgass, mode_highgass,
            signal_lowgas, lowgas_mode, phase_mode, compressor_overLoad_mode, phase_tv, compressor_overLoad_mode_tv;

    ImageView Image_R, Image_S, Image_T, borodat_img, termostat_img, defrost_img,
            phase_img, compressor_overLoad_img, signal_comperssor_img,
            signal_ovaperator_img, signal_oil_img, signal_highgas_img, signal_lowgas_img;

    String mode = "";
    String error_type = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.tab_signal, container, false);
        findview(V);
        mode = Config.INFORMATION_WORKMODE;
        error_type = Config.INFORMATION_ERRORTYPE;


        if (mode.equals("2") || mode.equals("3") || mode.equals("4") || mode.equals("5")) {
            switch (mode) {
                case "2":
                    brodat_mode.setText("on");
                    brodat_mode.setTextColor(getResources().getColor(R.color.blue));
                    borodat_img.setImageResource(R.drawable.check_icon_small);
                    signal_brodat.setTextColor(getResources().getColor(R.color.blue));
                    break;
                case "3":
                    termostat_mode.setText("on");
                    termostat_mode.setTextColor(getResources().getColor(R.color.green));
                    termostat_img.setImageResource(R.drawable.check_icon_small);
                    signal_termostat.setTextColor(getResources().getColor(R.color.green));
                    break;
                case ("4"):
                    defrost_mode.setText("on");
                    defrost_img.setImageResource(R.drawable.check_icon_small);
                    defrost_mode.setTextColor(getResources().getColor(R.color.yellow));
                    signal_defrost.setTextColor(getResources().getColor(R.color.yellow));
                    break;
                case "5":
                    defrost_mode.setText("on");
                    defrost_img.setImageResource(R.drawable.check_icon_small);
                    defrost_mode.setTextColor(getResources().getColor(R.color.yellow));
                    signal_defrost.setTextColor(getResources().getColor(R.color.yellow));
                    break;

            }
        } else if (mode.equals("1")) {
            switch (error_type) {
                case "01":
                    Image_R.setImageResource(R.drawable.ic_circle_gray);
                    Image_S.setImageResource(R.drawable.ic_circle_gray);
                    Image_T.setImageResource(R.drawable.ic_circle_gray);
                    break;
                case "02":
                    Image_R.setImageResource(R.drawable.ic_circle_gray);
                    Image_S.setImageResource(R.drawable.ic_circle_gray);
                    Image_T.setImageResource(R.drawable.ic_circle_gray);
                    break;
                case "03":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    Image_R.setImageResource(R.drawable.ic_circle_gray);
                    break;
                case "04":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    Image_S.setImageResource(R.drawable.ic_circle_gray);
                    break;
                case "05":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    Image_T.setImageResource(R.drawable.ic_circle_gray);
                    break;
                case "06":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "07":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "08":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "09":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "10":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "11":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "12":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "13":
                    phase_img.setImageResource(R.drawable.cancel_icon_small);
                    phase_mode.setText("ON");
                    phase_tv.setTextColor(getResources().getColor(R.color.red));
                    phase_mode.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "14":
                    compressor_overLoad_mode_tv.setTextColor(getResources().getColor(R.color.red));
                    compressor_overLoad_mode.setTextColor(getResources().getColor(R.color.red));
                    compressor_overLoad_mode.setText("ON");
                    compressor_overLoad_img.setImageResource(R.drawable.cancel_icon_small);
                    break;
                case "15":
                    signal_compressor.setTextColor(getResources().getColor(R.color.red));
                    signal_comperssor_img.setImageResource(R.drawable.cancel_icon_small);
                    compressor_mode.setTextColor(getResources().getColor(R.color.red));
                    compressor_mode.setText("ON");
                    break;
                case "16":
                    signal_compressor.setTextColor(getResources().getColor(R.color.red));
                    signal_comperssor_img.setImageResource(R.drawable.cancel_icon_small);
                    compressor_mode.setTextColor(getResources().getColor(R.color.red));
                    compressor_mode.setText("ON");
                    break;
                case "17":
                    signal_compressor.setTextColor(getResources().getColor(R.color.red));
                    signal_comperssor_img.setImageResource(R.drawable.cancel_icon_small);
                    compressor_mode.setTextColor(getResources().getColor(R.color.red));
                    compressor_mode.setText("ON");
                    break;
                case "18":
                    signal_ovaperator.setTextColor(getResources().getColor(R.color.red));
                    signal_ovaperator_img.setImageResource(R.drawable.cancel_icon_small);
                    ovaperator_mode.setTextColor(getResources().getColor(R.color.red));
                    ovaperator_mode.setText("ON");
                    break;
                case "19":
                    signal_ovaperator.setTextColor(getResources().getColor(R.color.red));
                    signal_ovaperator_img.setImageResource(R.drawable.cancel_icon_small);
                    ovaperator_mode.setTextColor(getResources().getColor(R.color.red));
                    ovaperator_mode.setText("ON");
                    break;
                case "20":
                    signal_ovaperator.setTextColor(getResources().getColor(R.color.red));
                    signal_ovaperator_img.setImageResource(R.drawable.cancel_icon_small);
                    ovaperator_mode.setTextColor(getResources().getColor(R.color.red));
                    ovaperator_mode.setText("ON");
                    break;
                case "21":
                    signal_highgass.setTextColor(getResources().getColor(R.color.red));
                    signal_highgas_img.setImageResource(R.drawable.cancel_icon_small);
                    mode_highgass.setText("ON");
                    mode_highgass.setTextColor(getResources().getColor(R.color.red));
                    break;
                case "22":
                    lowgas_mode.setTextColor(getResources().getColor(R.color.red));
                    lowgas_mode.setText("ON");
                    signal_lowgas_img.setImageResource(R.drawable.cancel_icon_small);
                    signal_lowgas.setTextColor(getResources().getColor(R.color.red));

                    break;
                case "23":
                    oil_mode.setTextColor(getResources().getColor(R.color.red));
                    oil_mode.setText("ON");
                    signal_oil_img.setImageResource(R.drawable.cancel_icon_small);
                    signal_oil.setTextColor(getResources().getColor(R.color.red));
                    break;


            }
        }
        return V;
    }

    public void findview(View v) {

        signal_brodat = v.findViewById(R.id.signal_brodat);
        brodat_mode = v.findViewById(R.id.brodat_mode);
        signal_termostat = v.findViewById(R.id.signal_termostat);
        termostat_mode = v.findViewById(R.id.termostat_mode);
        defrost_mode = v.findViewById(R.id.defrost_mode);
        signal_defrost = v.findViewById(R.id.signal_defrost);
        signal_compressor = v.findViewById(R.id.signal_compressor);
        compressor_mode = v.findViewById(R.id.compressor_mode);
        signal_ovaperator = v.findViewById(R.id.signal_ovaperator);
        ovaperator_mode = v.findViewById(R.id.ovaperator_mode);
        signal_oil = v.findViewById(R.id.signal_oil);
        oil_mode = v.findViewById(R.id.oil_mode);
        signal_highgass = v.findViewById(R.id.signal_highgass);
        mode_highgass = v.findViewById(R.id.mode_highgass);
        signal_lowgas = v.findViewById(R.id.signal_lowgas);
        lowgas_mode = v.findViewById(R.id.lowgas_mode);
        Image_R = v.findViewById(R.id.R);
        Image_S = v.findViewById(R.id.S);
        Image_T = v.findViewById(R.id.T);
        borodat_img = v.findViewById(R.id.borodat_img);
        termostat_img = v.findViewById(R.id.termostat_img);
        defrost_img = v.findViewById(R.id.defrost_img);
        phase_mode = v.findViewById(R.id.phase_mode);
        phase_img = v.findViewById(R.id.phase_img);
        compressor_overLoad_mode = v.findViewById(R.id.compressor_overLoad_mode);
        compressor_overLoad_img = v.findViewById(R.id.compressor_overLoad_img);
        signal_comperssor_img = v.findViewById(R.id.signal_comperssor_img);
        signal_ovaperator_img = v.findViewById(R.id.signal_ovaperator_img);
        signal_oil_img = v.findViewById(R.id.signal_oil_img);
        signal_highgas_img = v.findViewById(R.id.signal_highgas_img);
        signal_lowgas_img = v.findViewById(R.id.signal_lowgas_img);
        phase_tv = v.findViewById(R.id.phase_tv);
        compressor_overLoad_mode_tv = v.findViewById(R.id.compressor_overLoad_mode_tv);
    }

}
