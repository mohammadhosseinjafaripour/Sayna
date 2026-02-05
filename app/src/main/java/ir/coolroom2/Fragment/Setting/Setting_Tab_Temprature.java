package ir.coolroom2.Fragment.Setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.R;
import ir.coolroom2.RepeatListener;
import ir.coolroom2.Sms.PrepareDataAndSendSms;
import ir.coolroom2.Sms.SmsCodeAndDecode;


public class Setting_Tab_Temprature extends Fragment {


    Button temperature_v1_up;
    Button temperature_v1_down;
    Button temperature_v2_up;
    Button temperature_v2_down;
    Button temperature_v3_up;
    Button temperature_v3_down;
    Button temperature_v4_up;
    Button temperature_v4_down;
    Button temperature_v5_up;
    Button temperature_v5_down;
    Button temperature_v6_up;
    Button temperature_v6_down;


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
            repeatListener10,
            repeatListener11,
            repeatListener12;

    TextView temperature_v1;
    TextView temperature_v2;
    TextView temperature_v3;
    TextView temperature_v4;
    TextView temperature_v5;
    TextView temperature_v6;

    DatabaseHandler db;

    boolean fr_check = false;


    private static final String KEY_TEMPERATURE_V1 = "temperature_V1";
    private static final String KEY_TEMPERATURE_V2 = "temperature_V2";
    private static final String KEY_TEMPERATURE_V3 = "temperature_V3";
    private static final String KEY_TEMPERATURE_V4 = "temperature_V4";
    private static final String KEY_TEMPERATURE_V5 = "temperature_V5";
    private static final String KEY_TEMPERATURE_V6 = "temperature_V6";

    SmsCodeAndDecode smsCodeAndDecode = new SmsCodeAndDecode();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting__tab__temprature, container, false);
        db = new DatabaseHandler(getActivity());

        findview(view);


        temperature_v1.setText(String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V1))) + "");
        temperature_v2.setText(Config.general_data_setting.get(KEY_TEMPERATURE_V2));
        temperature_v3.setText(Config.general_data_setting.get(KEY_TEMPERATURE_V3));
        temperature_v4.setText(Config.general_data_setting.get(KEY_TEMPERATURE_V4));
        temperature_v5.setText(Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V5)) + "");
        temperature_v6.setText(Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V6)) + "");


        loadRepeat();

        temperature_v1_up.setOnTouchListener(repeatListener1);
        temperature_v1_down.setOnTouchListener(repeatListener2);

        temperature_v2_up.setOnTouchListener(repeatListener3);
        temperature_v2_down.setOnTouchListener(repeatListener4);

        temperature_v3_up.setOnTouchListener(repeatListener5);
        temperature_v3_down.setOnTouchListener(repeatListener6);

        temperature_v4_up.setOnTouchListener(repeatListener7);
        temperature_v4_down.setOnTouchListener(repeatListener8);

        temperature_v5_up.setOnTouchListener(repeatListener9);
        temperature_v5_down.setOnTouchListener(repeatListener10);

        temperature_v6_up.setOnTouchListener(repeatListener11);
        temperature_v6_down.setOnTouchListener(repeatListener12);


        temperature_v5_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener9.up_down, getContext());
                temperature_v5.setText(Math.round(repeatListener9.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V5, Math.round(repeatListener9.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V2, Math.round(repeatListener3.result) + "");
            }
        });
        temperature_v5_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener10.up_down, getContext());
                temperature_v5.setText(Math.round(repeatListener9.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V5, Math.round(repeatListener9.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V2, Math.round(repeatListener3.result) + "");

            }
        });

        temperature_v2_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener3.up_down, getContext());
                temperature_v2.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V2, Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V5, Math.round(repeatListener9.result) + "");
            }
        });

        temperature_v2_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener4.up_down, getContext());
                temperature_v2.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V2, Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V5, Math.round(repeatListener9.result) + "");

            }
        });

        return view;
    }

    void findview(View view) {


        temperature_v1 = (TextView) view.findViewById(R.id.temperature_v1);
        temperature_v4 = (TextView) view.findViewById(R.id.temperature_v4);
        temperature_v2 = (TextView) view.findViewById(R.id.temperature_v2);
        temperature_v3 = (TextView) view.findViewById(R.id.temperature_v3);
        temperature_v5 = (TextView) view.findViewById(R.id.temperature_v5);
        temperature_v6 = (TextView) view.findViewById(R.id.temperature_v6);

        temperature_v1_up = (Button) view.findViewById(R.id.temperature_v1_up);
        temperature_v1_down = (Button) view.findViewById(R.id.temperature_v1_down);
        temperature_v4_up = (Button) view.findViewById(R.id.temperature_v4_up);
        temperature_v4_down = (Button) view.findViewById(R.id.temperature_v4_down);
        temperature_v2_up = (Button) view.findViewById(R.id.temperature_v2_up);
        temperature_v2_down = (Button) view.findViewById(R.id.temperature_v2_down);
        temperature_v3_up = (Button) view.findViewById(R.id.temperature_v3_up);
        temperature_v3_down = (Button) view.findViewById(R.id.temperature_v3_down);
        temperature_v5_up = (Button) view.findViewById(R.id.temperature_v5_up);
        temperature_v5_down = (Button) view.findViewById(R.id.temperature_v5_down);
        temperature_v6_up = (Button) view.findViewById(R.id.temperature_v6_up);
        temperature_v6_down = (Button) view.findViewById(R.id.temperature_v6_down);
    }

    void loadRepeat() {

        repeatListener1 = new RepeatListener(40, 40, 15f, -40f, 0.1f, true, Float.parseFloat((Config.general_data_setting.get(KEY_TEMPERATURE_V1))), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener1.up_down, getContext());
                temperature_v1.setText(String.format("%.1f", repeatListener1.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V1, repeatListener1.result + "");
            }
        }, getContext());

        repeatListener2 = new RepeatListener(100, 40, 15f, -40f, (repeatListener1.Count_num), false, Float.parseFloat((Config.general_data_setting.get(KEY_TEMPERATURE_V1))), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener2.up_down, getContext());
                temperature_v1.setText(String.format("%.1f", repeatListener1.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V1, repeatListener1.result + "");
            }
        }, getContext());

        //********************************************

        repeatListener3 = new RepeatListener(40, 40, 9f, 1f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = check_Repeat(Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V5)), (Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V2))));
                if (result) {
                    repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener3.up_down, getContext());
                    temperature_v2.setText(Math.round(repeatListener3.result) + "");
                    Config.general_data_setting.put(KEY_TEMPERATURE_V2, Math.round(repeatListener3.result) + "");
                }

            }
        }, getContext());

        repeatListener4 = new RepeatListener(100, 40, 9f, 1f, (repeatListener3.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener4.up_down, getContext());
                temperature_v2.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V2, Math.round(repeatListener3.result) + "");


            }
        }, getContext());

        //********************************************

        repeatListener5 = new RepeatListener(40, 40, -1f, -9f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener5.up_down, getContext());
                temperature_v3.setText(Math.round(repeatListener5.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V3, Math.round(repeatListener5.result) + "");


            }
        }, getContext());

        repeatListener6 = new RepeatListener(100, 40, -1f, -9f, (repeatListener5.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = check_Repeat2(Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V6)), (Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V3))));
                if (result) {
                    repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener6.up_down, getContext());
                    temperature_v3.setText(Math.round(repeatListener5.result) + "");
                    Config.general_data_setting.put(KEY_TEMPERATURE_V3, Math.round(repeatListener5.result) + "");
                }

            }
        }, getContext());

        //*************************************************

        repeatListener7 = new RepeatListener(40, 40, 9f, -9f, Config.Current_counter, true, Float.valueOf(Config.general_data_setting.get(KEY_TEMPERATURE_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener7.up_down, getContext());
                temperature_v4.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V4, Math.round(repeatListener7.result) + "");

            }
        }, getContext());

        repeatListener8 = new RepeatListener(100, 40, 9f, -9f, (repeatListener7.Count_num), false, Float.valueOf(Config.general_data_setting.get(KEY_TEMPERATURE_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener8.up_down, getContext());
                temperature_v4.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V4, Math.round(repeatListener7.result) + "");

            }
        }, getContext());


        //*************************************************

        repeatListener9 = new RepeatListener(1000, 1000, 12f, -4f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int distance_check = Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V5)) - (Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V2)));
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener9.up_down, getContext());
                temperature_v5.setText(Math.round(repeatListener9.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V5, Math.round(repeatListener9.result) + "");

            }
        }, getContext());

        repeatListener10 = new RepeatListener(1000, 1000, 12f, -4f, (repeatListener9.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = check_Repeat(Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V5)), (Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V2))));
                if (result) {
                    repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener10.up_down, getContext());
                    temperature_v5.setText(Math.round(repeatListener9.result) + "");
                    Config.general_data_setting.put(KEY_TEMPERATURE_V5, Math.round(repeatListener9.result) + "");
                }
            }
        }, getContext());

        //*************************************************

        repeatListener11 = new RepeatListener(40, 40, -4f, -12f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = check_Repeat2(Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V6)), (Integer.parseInt(Config.general_data_setting.get(KEY_TEMPERATURE_V3))));
                if (result) {
                    repeatListener11.check(repeatListener11.Max_num, repeatListener11.Min_num, repeatListener11.Count_num, repeatListener11.up_down, getContext());
                    temperature_v6.setText(Math.round(repeatListener11.result) + "");
                    Config.general_data_setting.put(KEY_TEMPERATURE_V6, Math.round(repeatListener11.result) + "");
                }

            }
        }, getContext());

        repeatListener12 = new RepeatListener(100, 40, -4f, -12f, (repeatListener11.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_TEMPERATURE_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeatListener11.check(repeatListener11.Max_num, repeatListener11.Min_num, repeatListener11.Count_num, repeatListener12.up_down, getContext());
                temperature_v6.setText(Math.round(repeatListener11.result) + "");
                Config.general_data_setting.put(KEY_TEMPERATURE_V6, Math.round(repeatListener11.result) + "");

            }
        }, getContext());


    }

    boolean check_Repeat(int var2, int var1) {
        int result = (Math.abs(var2)) - (Math.abs(var1));
        if (result > 3) {
            return true;
        } else if (result == 3) {

            repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener9.up_down, getContext());
            temperature_v5.setText(Math.round(repeatListener9.result) + "");
            Config.general_data_setting.put(KEY_TEMPERATURE_V5, Math.round(repeatListener9.result) + "");

        }
        return true;
    }


    boolean check_Repeat2(int var2, int var1) {
        int result = (Math.abs(var2)) - (Math.abs(var1));
        if (result > 3) {
            return true;
        } else if (result == 3) {

            repeatListener11.check(repeatListener11.Max_num, repeatListener11.Min_num, repeatListener11.Count_num, repeatListener12.up_down, getContext());
            temperature_v6.setText(Math.round(repeatListener11.result) + "");
            Config.general_data_setting.put(KEY_TEMPERATURE_V6, Math.round(repeatListener11.result) + "");
        }
        return true;
    }
}
