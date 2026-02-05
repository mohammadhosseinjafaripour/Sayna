package ir.coolroom2.Fragment.Setting;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.R;
import ir.coolroom2.RepeatListener;
import ir.coolroom2.Sms.SmsCodeAndDecode;


public class Setting_Tab_Humour extends Fragment {


    Button humour_v1_up;
    Button humour_v1_down;
    Button humour_v2_up;
    Button humour_v2_down;
    Button humour_v3_up;
    Button humour_v3_down;
    Button humour_v4_up;
    Button humour_v4_down;
    Button humour_v5_up;
    Button humour_v5_down;
    Button humour_v6_up;
    Button humour_v6_down;


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

    TextView humour_v1;
    TextView humour_v4;
    TextView humour_v2;
    TextView humour_v3;
    TextView humour_v5;
    TextView humour_v6;

    DatabaseHandler db;


    private static final String KEY_HUMOUR_V1 = "humour_V1";
    private static final String KEY_HUMOUR_V2 = "humour_V2";
    private static final String KEY_HUMOUR_V3 = "humour_V3";
    private static final String KEY_HUMOUR_V4 = "humour_V4";
    private static final String KEY_HUMOUR_V5 = "humour_V5";
    private static final String KEY_HUMOUR_V6 = "humour_V6";
    private static final String KEY_HUMOUR_V7 = "humour_V7";
    private static final String KEY_HUMOUR_V8 = "humour_V8";
    private static final String KEY_HUMOUR_V9 = "humour_V9";
    private static final String KEY_HUMOUR_V10 = "humour_V10";

    SmsCodeAndDecode smsCodeAndDecode = new SmsCodeAndDecode();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_tab_humour, container, false);
        db = new DatabaseHandler(getActivity());
        findview(view);

        humour_v1.setText(Config.general_data_setting.get(KEY_HUMOUR_V1));
        humour_v2.setText(Config.general_data_setting.get(KEY_HUMOUR_V2));
        humour_v3.setText(Config.general_data_setting.get(KEY_HUMOUR_V3));
        humour_v4.setText(Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V4)) + "");
        humour_v5.setText(Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V5)) + "");
        humour_v6.setText(Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V6)) + "");

        loadRepeat();

        humour_v1_up.setOnTouchListener(repeatListener1);
        humour_v1_down.setOnTouchListener(repeatListener2);

        humour_v2_up.setOnTouchListener(repeatListener3);
        humour_v2_down.setOnTouchListener(repeatListener4);

        humour_v3_up.setOnTouchListener(repeatListener5);
        humour_v3_down.setOnTouchListener(repeatListener6);

        humour_v4_up.setOnTouchListener(repeatListener7);
        humour_v4_down.setOnTouchListener(repeatListener8);

        humour_v5_up.setOnTouchListener(repeatListener9);
        humour_v5_down.setOnTouchListener(repeatListener10);

        humour_v6_up.setOnTouchListener(repeatListener11);
        humour_v6_down.setOnTouchListener(repeatListener12);


        return view;
    }

    void findview(View view) {


        humour_v1 = (TextView) view.findViewById(R.id.humour_v1);
        humour_v2 = (TextView) view.findViewById(R.id.humour_v2);
        humour_v3 = (TextView) view.findViewById(R.id.humour_v3);
        humour_v4 = (TextView) view.findViewById(R.id.humour_v4);
        humour_v5 = (TextView) view.findViewById(R.id.humour_v5);
        humour_v6 = (TextView) view.findViewById(R.id.humour_v6);

        humour_v1_up = (Button) view.findViewById(R.id.humour_v1_up);
        humour_v1_down = (Button) view.findViewById(R.id.humour_v1_down);
        humour_v4_up = (Button) view.findViewById(R.id.humour_v4_up);
        humour_v4_down = (Button) view.findViewById(R.id.humour_v4_down);
        humour_v2_up = (Button) view.findViewById(R.id.humour_v2_up);
        humour_v2_down = (Button) view.findViewById(R.id.humour_v2_down);
        humour_v3_up = (Button) view.findViewById(R.id.humour_v3_up);
        humour_v3_down = (Button) view.findViewById(R.id.humour_v3_down);
        humour_v5_up = (Button) view.findViewById(R.id.humour_v5_up);
        humour_v5_down = (Button) view.findViewById(R.id.humour_v5_down);
        humour_v6_up = (Button) view.findViewById(R.id.humour_v6_up);
        humour_v6_down = (Button) view.findViewById(R.id.humour_v6_down);
    }

    void loadRepeat() {

        repeatListener1 = new RepeatListener(50, 50, 95f, 5f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V1)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener1.up_down, getContext());
                humour_v1.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(KEY_HUMOUR_V1, Math.round(repeatListener1.result) + "");

            }
        }, getContext());

        repeatListener2 = new RepeatListener(100, 50, 95f, 5f, (repeatListener1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V1)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener2.up_down, getContext());
                humour_v1.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(KEY_HUMOUR_V1, Math.round(repeatListener1.result) + "");
            }
        }, getContext());

        //*************************************************************V2

        repeatListener3 = new RepeatListener(50, 50, 9f, 1f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = check_Repeat(Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V5)), (Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V2))));
                if (result) {
                    repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener3.up_down, getContext());
                    humour_v2.setText(Math.round(repeatListener3.result) + "");
                    Config.general_data_setting.put(KEY_HUMOUR_V2, Math.round(repeatListener3.result) + "");
                }
            }
        }, getContext());

        repeatListener4 = new RepeatListener(100, 50, 9f, 1f, (repeatListener3.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener4.up_down, getContext());
                humour_v2.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(KEY_HUMOUR_V2, Math.round(repeatListener3.result) + "");
            }
        }, getContext());

        //*************************************************************V3

        repeatListener5 = new RepeatListener(50, 50, -1f, -9f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener5.up_down, getContext());
                humour_v3.setText(Math.round(repeatListener5.result) + "");
                Config.general_data_setting.put(KEY_HUMOUR_V3, Math.round(repeatListener5.result) + "");


            }
        }, getContext());

        repeatListener6 = new RepeatListener(100, 50, -1f, -9f, (repeatListener5.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = check_Repeat2(Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V6)), (Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V3))));
                if (result) {
                    repeatListener5.check(repeatListener5.Max_num, repeatListener5.Min_num, repeatListener5.Count_num, repeatListener6.up_down, getContext());
                    humour_v3.setText(Math.round(repeatListener5.result) + "");
                    Config.general_data_setting.put(KEY_HUMOUR_V3, Math.round(repeatListener5.result) + "");
                }
            }
        }, getContext());

        //*************************************************************V4

        repeatListener7 = new RepeatListener(50, 50, 9f, -9f, Config.Current_counter, true, Float.parseFloat((Config.general_data_setting.get(KEY_HUMOUR_V4))), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener7.up_down, getContext());
                humour_v4.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(KEY_HUMOUR_V4, Math.round(repeatListener7.result) + "");
            }
        }, getContext());

        repeatListener8 = new RepeatListener(100, 400, 9f, -9f, (repeatListener7.Count_num), false, Float.parseFloat((Config.general_data_setting.get(KEY_HUMOUR_V4))), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7.check(repeatListener7.Max_num, repeatListener7.Min_num, repeatListener7.Count_num, repeatListener8.up_down, getContext());
                humour_v4.setText(Math.round(repeatListener7.result) + "");
                Config.general_data_setting.put(KEY_HUMOUR_V4, Math.round(repeatListener7.result) + "");
            }
        }, getContext());

        //*************************************************************V5

        repeatListener9 = new RepeatListener(50, 50, 12f, 4f, Config.Current_counter, true, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener9.up_down, getContext());
                humour_v5.setText(Math.round(repeatListener9.result) + "");
                Config.general_data_setting.put(KEY_HUMOUR_V5, Math.round(repeatListener9.result) + "");
            }
        }, getContext());

        repeatListener10 = new RepeatListener(100, 50, 12f, 4f, (repeatListener9.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = check_Repeat(Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V5)), (Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V2))));
                if (result) {
                    repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener10.up_down, getContext());
                    humour_v5.setText(Math.round(repeatListener9.result) + "");
                    Config.general_data_setting.put(KEY_HUMOUR_V5, Math.round(repeatListener9.result) + "");
                }
            }
        }, getContext());

        //*************************************************************V6

        repeatListener11 = new RepeatListener(50, 50, -4f, -12f, 1f, true, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = check_Repeat2(Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V6)), (Integer.parseInt(Config.general_data_setting.get(KEY_HUMOUR_V3))));
                if (result) {
                    repeatListener11.check(repeatListener11.Max_num, repeatListener11.Min_num, repeatListener11.Count_num, repeatListener11.up_down, getContext());
                    humour_v6.setText(Math.round(repeatListener11.result) + "");
                    Config.general_data_setting.put(KEY_HUMOUR_V6, Math.round(repeatListener11.result) + "");
                }

            }
        }, getContext());

        repeatListener12 = new RepeatListener(100, 50, -4f, -12f, (repeatListener11.Count_num), false, Float.parseFloat(Config.general_data_setting.get(KEY_HUMOUR_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeatListener11.check(repeatListener11.Max_num, repeatListener11.Min_num, repeatListener11.Count_num, repeatListener12.up_down, getContext());
                humour_v6.setText(Math.round(repeatListener11.result) + "");
                Config.general_data_setting.put(KEY_HUMOUR_V6, Math.round(repeatListener11.result) + "");

            }
        }, getContext());


    }

    boolean check_Repeat(int var2, int var1) {
        int result = (Math.abs(var2)) - (Math.abs(var1));
        if (result > 3) {
            return true;
        } else if (result == 3) {

            repeatListener9.check(repeatListener9.Max_num, repeatListener9.Min_num, repeatListener9.Count_num, repeatListener9.up_down, getContext());
            humour_v5.setText(Math.round(repeatListener9.result) + "");
            Config.general_data_setting.put(KEY_HUMOUR_V5, Math.round(repeatListener9.result) + "");

        }
        return true;
    }


    boolean check_Repeat2(int var2, int var1) {
        int result = (Math.abs(var2)) - (Math.abs(var1));
        if (result > 3) {
            return true;
        } else if (result == 3) {

            repeatListener11.check(repeatListener11.Max_num, repeatListener11.Min_num, repeatListener11.Count_num, repeatListener12.up_down, getContext());
            humour_v6.setText(Math.round(repeatListener11.result) + "");
            Config.general_data_setting.put(KEY_HUMOUR_V6, Math.round(repeatListener11.result) + "");
        }
        return true;
    }
}
