package ir.coolroom2.Fragment.Setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.R;
import ir.coolroom2.RepeatListener;

/**
 * Created by JFP on 12/4/2017.
 */

public class Setting_Tab_Voltage extends Fragment {


    Button voltage_v6_up;
    Button voltage_v6_down;
    Button voltage_v2_up;
    Button voltage_v2_down;
    Button voltage_v3_up;
    Button voltage_v3_down;
    Button voltage_v4_up;
    Button voltage_v4_down;
    Button voltage_v5_up;
    Button voltage_v5_down;


    RepeatListener
            repeatListener1,
            repeatListener2,
            repeatListener3,
            repeatListener4,
            repeatListener5,
            repeatListener6,
            repeatListener7,
            repeatListener8,
            repeatListener9,
            repeatListener10;

    RadioGroup voltage_v1;
    TextView voltage_v2;
    TextView voltage_v3;
    TextView voltage_v4;
    TextView voltage_v5;

    TextView voltage_v6;

    RadioButton voltage_v1_2, voltage_v1_1;


    DatabaseHandler db;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting_tab_voltage, container, false);

        db = new DatabaseHandler(getActivity());
        findview(view);


        int check = Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V1));

        if (check == 0) {
            voltage_v1.clearCheck();
            voltage_v1_1.setChecked(true);
            loadRepeat_SingleFaz_Dynamic();
            if (!db.isNewSetting(String.valueOf(db.getSelectedRoom().get_id()))) {
                loadRepeat_SingleFaz();
            }
        } else {
            voltage_v1.clearCheck();
            voltage_v1_2.setChecked(true);
            loadRepeat_ThreeFaz();
        }

        voltage_v2.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2));
        voltage_v3.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3));
        voltage_v4.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)) + "");
        voltage_v5.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)) + "");
        voltage_v6.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)) + "%");

        voltage_v2_up.setOnTouchListener(repeatListener1);
        voltage_v2_down.setOnTouchListener(repeatListener2);

        voltage_v3_up.setOnTouchListener(repeatListener3);
        voltage_v3_down.setOnTouchListener(repeatListener4);

        voltage_v4_up.setOnTouchListener(repeatListener5);
        voltage_v4_down.setOnTouchListener(repeatListener6);

        voltage_v5_up.setOnTouchListener(repeatListener7);
        voltage_v5_down.setOnTouchListener(repeatListener8);

        voltage_v6_up.setOnTouchListener(repeatListener9);
        voltage_v6_down.setOnTouchListener(repeatListener10);

        voltage_v1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {

                Config.general_data_setting.put(Config.KEY_VOLTAGE_V1, String.valueOf(((checkid == R.id.voltage_v6_1) ? 0 : 1)));
                switch (checkid) {
                    case R.id.voltage_v6_1:

                        Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, "230");
                        Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, "210");
                        loadRepeat_SingleFaz_Dynamic();


                        voltage_v2.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2));
                        voltage_v3.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3));
                        voltage_v4.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4));
                        voltage_v5.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5));
                        break;
                    case R.id.voltage_v6_2:


                        Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, "390");
                        Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, "370");

                        loadRepeat_ThreeFaz();
                        voltage_v2.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2));
                        voltage_v3.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3));
                        voltage_v4.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4));
                        voltage_v5.setText(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5));
                        break;
                }
            }
        });


        return view;

    }

    void findview(View view) {
        voltage_v1 = (RadioGroup) view.findViewById(R.id.voltage_v1);
        voltage_v2 = (TextView) view.findViewById(R.id.voltage_v2);
        voltage_v3 = (TextView) view.findViewById(R.id.voltage_v3);
        voltage_v4 = (TextView) view.findViewById(R.id.voltage_v4);
        voltage_v5 = (TextView) view.findViewById(R.id.voltage_v5);
        voltage_v6 = (TextView) view.findViewById(R.id.voltage_v6);

        voltage_v2_up = (Button) view.findViewById(R.id.voltage_v2_up);
        voltage_v2_down = (Button) view.findViewById(R.id.voltage_v2_down);
        voltage_v3_up = (Button) view.findViewById(R.id.voltage_v3_up);
        voltage_v3_down = (Button) view.findViewById(R.id.voltage_v3_down);
        voltage_v4_up = (Button) view.findViewById(R.id.voltage_v4_up);
        voltage_v4_down = (Button) view.findViewById(R.id.voltage_v4_down);
        voltage_v5_up = (Button) view.findViewById(R.id.voltage_v5_up);
        voltage_v5_down = (Button) view.findViewById(R.id.voltage_v5_down);
        voltage_v6_up = (Button) view.findViewById(R.id.voltage_v6_up);
        voltage_v6_down = (Button) view.findViewById(R.id.voltage_v6_down);

        voltage_v1_1 = (RadioButton) view.findViewById(R.id.voltage_v6_1);
        voltage_v1_2 = (RadioButton) view.findViewById(R.id.voltage_v6_2);
    }

    void loadRepeat_ThreeFaz() {

        repeatListener1 = new RepeatListener(40, 40, 450f, 385f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener1.up_down, getContext());
                voltage_v2.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, Math.round(repeatListener1.result) + "");
            }
        }, getContext());
        repeatListener2 = new RepeatListener(40, 40, 450f, 385f, (repeatListener1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener2.up_down, getContext());
                voltage_v2.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, Math.round(repeatListener1.result) + "");
            }
        }, getContext());


        repeatListener3 = new RepeatListener(40, 40, 375f, 320f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener3.up_down, getContext());
                voltage_v3.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, Math.round(repeatListener3.result) + "");
            }
        }, getContext());
        repeatListener4 = new RepeatListener(40, 40, 375f, 320f, (repeatListener3.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener4.up_down, getContext());
                voltage_v3.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, Math.round(repeatListener3.result) + "");
            }
        }, getContext());


        repeatListener5 = new RepeatListener(40, 40, 300f, 5f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener5.up_down, getContext());
                voltage_v4.setText(Math.round(repeatListener5.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V4, Math.round(repeatListener5.result) + "");
            }
        }, getContext());
        repeatListener6 = new RepeatListener(100, 40, 300f, 5f, (repeatListener5.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener6.up_down, getContext());
                voltage_v4.setText(Math.round(repeatListener5.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V4, Math.round(repeatListener5.result) + "");
            }
        }, getContext());


        repeatListener7 = new RepeatListener(40, 40, 99f, 1f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener7.up_down, getContext());
                voltage_v5.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V5, Math.round(repeatListener7.result) + "");
            }
        }, getContext());
        repeatListener8 = new RepeatListener(100, 40, 99f, 1f, (repeatListener7.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener8.up_down, getContext());
                voltage_v5.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V5, Math.round(repeatListener7.result) + "");
            }
        }, getContext());

        repeatListener9 = new RepeatListener(40, 40, 20f, 1f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener9.up_down, getContext());
                voltage_v6.setText(Math.round(repeatListener9.result) + "  %");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V6, Math.round(repeatListener9.result) + "");
            }
        }, getContext());
        repeatListener10 = new RepeatListener(100, 40, 20f, 1f, (repeatListener9.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener10.up_down, getContext());
                voltage_v6.setText(Math.round(repeatListener9.result) + "  %");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V6, Math.round(repeatListener9.result) + "");
            }
        }, getContext());


    }

    void loadRepeat_SingleFaz() {

        repeatListener1 = new RepeatListener(40, 40, 280f, 225f, Config.Current_counter, true, 230, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener1.up_down, getContext());
                voltage_v2.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, Math.round(repeatListener1.result) + "");
            }
        }, getContext());
        repeatListener2 = new RepeatListener(40, 40, 280f, 225f, (repeatListener1.Count_num), false, 230, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener2.up_down, getContext());
                voltage_v2.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, Math.round(repeatListener1.result) + "");
            }
        }, getContext());

        repeatListener3 = new RepeatListener(40, 40, 215f, 170f, Config.Current_counter, true, 210, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener3.up_down, getContext());
                voltage_v3.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, Math.round(repeatListener3.result) + "");
            }
        }, getContext());
        repeatListener4 = new RepeatListener(40, 40, 215f, 170f, (repeatListener3.Count_num), false, 210, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener4.up_down, getContext());
                voltage_v3.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, Math.round(repeatListener3.result) + "");
            }
        }, getContext());


        repeatListener5 = new RepeatListener(40, 40, 300f, 5f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener5.up_down, getContext());
                voltage_v4.setText(Math.round(repeatListener5.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V4, Math.round(repeatListener5.result) + "");
            }
        }, getContext());
        repeatListener6 = new RepeatListener(100, 40, 300f, 5f, (repeatListener5.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener6.up_down, getContext());
                voltage_v4.setText(Math.round(repeatListener5.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V4, Math.round(repeatListener5.result) + "");
            }
        }, getContext());


        repeatListener7 = new RepeatListener(40, 40, 99f, 1f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener7.up_down, getContext());
                voltage_v5.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V5, Math.round(repeatListener7.result) + "");
            }
        }, getContext());
        repeatListener8 = new RepeatListener(100, 40, 99f, 1f, (repeatListener7.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener8.up_down, getContext());
                voltage_v5.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V5, Math.round(repeatListener7.result) + "");
            }
        }, getContext());

        repeatListener9 = new RepeatListener(40, 40, 20f, 1f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener9.up_down, getContext());
                voltage_v6.setText(Math.round(repeatListener9.result) + "  %");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V6, Math.round(repeatListener9.result) + "");
            }
        }, getContext());
        repeatListener10 = new RepeatListener(100, 40, 20f, 1f, (repeatListener9.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener10.up_down, getContext());
                voltage_v6.setText(Math.round(repeatListener9.result) + "  %");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V6, Math.round(repeatListener9.result) + "");
            }
        }, getContext());


    }

    /*dynamic*/

    void loadRepeat_SingleFaz_Dynamic() {

        repeatListener1 = new RepeatListener(40, 40, 280f, 225f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener1.up_down, getContext());
                voltage_v2.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, Math.round(repeatListener1.result) + "");
            }
        }, getContext());
        repeatListener2 = new RepeatListener(40, 40, 280f, 225f, (repeatListener1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener2.up_down, getContext());
                voltage_v2.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, Math.round(repeatListener1.result) + "");
            }
        }, getContext());

        repeatListener3 = new RepeatListener(40, 40, 215f, 170f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener3.up_down, getContext());
                voltage_v3.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, Math.round(repeatListener3.result) + "");
            }
        }, getContext());
        repeatListener4 = new RepeatListener(40, 40, 215f, 170f, (repeatListener3.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener4.up_down, getContext());
                voltage_v3.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, Math.round(repeatListener3.result) + "");
            }
        }, getContext());


        repeatListener5 = new RepeatListener(40, 40, 300f, 5f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener5.up_down, getContext());
                voltage_v4.setText(Math.round(repeatListener5.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V4, Math.round(repeatListener5.result) + "");
            }
        }, getContext());
        repeatListener6 = new RepeatListener(100, 40, 300f, 5f, (repeatListener5.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener6.up_down, getContext());
                voltage_v4.setText(Math.round(repeatListener5.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V4, Math.round(repeatListener5.result) + "");
            }
        }, getContext());


        repeatListener7 = new RepeatListener(40, 40, 99f, 1f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener7.up_down, getContext());
                voltage_v5.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V5, Math.round(repeatListener7.result) + "");
            }
        }, getContext());
        repeatListener8 = new RepeatListener(100, 40, 99f, 1f, (repeatListener7.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener8.up_down, getContext());
                voltage_v5.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V5, Math.round(repeatListener7.result) + "");
            }
        }, getContext());

        repeatListener9 = new RepeatListener(40, 40, 20f, 1f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener9.up_down, getContext());
                voltage_v6.setText(Math.round(repeatListener9.result) + "  %");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V6, Math.round(repeatListener9.result) + "");
            }
        }, getContext());
        repeatListener10 = new RepeatListener(100, 40, 20f, 1f, (repeatListener9.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener10.up_down, getContext());
                voltage_v6.setText(Math.round(repeatListener9.result) + "  %");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V6, Math.round(repeatListener9.result) + "");
            }
        }, getContext());


    }
}
