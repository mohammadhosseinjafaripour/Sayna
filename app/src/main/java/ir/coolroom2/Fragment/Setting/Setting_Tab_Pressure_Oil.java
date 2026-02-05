package ir.coolroom2.Fragment.Setting;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.RepeatListener;
import ir.coolroom2.Sms.PrepareDataAndSendSms;

/**
 * A simple {@link Fragment} subclass.
 */
public class Setting_Tab_Pressure_Oil extends Fragment {

    private RadioGroup pressure_v1;
    private Button
            pressure_v2_up, pressure_v2_down,
            pressure_v3_up, pressure_v3_down,
            pressure_v4_up, pressure_v4_down,
            pressure_v5_up, pressure_v5_down;

    private RepeatListener
            repeatListener2_1, repeatListener2_2,
            repeatListener3_1, repeatListener3_2,
            repeatListener4_1, repeatListener4_2,
            repeatListener5_1, repeatListener5_2;

    private TextView
            pressure_v2,
            pressure_v3,
            pressure_v4,
            pressure_v5;

    private ViewPager viewPager;
    private RadioButton pressure_v1_automatic, pressure_v1_manual;

    Button manual_oil;
    UserModel userModel = null;
    DatabaseHandler db;

    public Setting_Tab_Pressure_Oil() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting__tab__pressure__oil, container, false);

        db = new DatabaseHandler(getActivity());
        userModel = db.getFirstUser_for_room(Config.currentRoomID);

        findview(view);
        // TODO: 22/01/2018  radio group set
        pressure_v2.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V2)) + "");
        pressure_v3.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V3)) + "");
        pressure_v4.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V4)) + "");
        pressure_v5.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V5)) + "");


        loadRepeat();

        // TODO: 22/01/2018  radio group set

        pressure_v2_up.setOnTouchListener(repeatListener2_1);
        pressure_v2_down.setOnTouchListener(repeatListener2_2);

        pressure_v3_up.setOnTouchListener(repeatListener3_1);
        pressure_v3_down.setOnTouchListener(repeatListener3_2);

        pressure_v4_up.setOnTouchListener(repeatListener4_1);
        pressure_v4_down.setOnTouchListener(repeatListener4_2);

        pressure_v5_up.setOnTouchListener(repeatListener5_1);
        pressure_v5_down.setOnTouchListener(repeatListener5_2);


        final LinearLayout pomp_down_linear = (LinearLayout) view.findViewById(R.id.pomp_down_linear);
        final RadioButton v3_manual, v3_automatic;
        v3_manual = (RadioButton) view.findViewById(R.id.pressure_v1_manual);
        v3_automatic = (RadioButton) view.findViewById(R.id.pressure_v1_automatic);

        final LinearLayout delay_on_connect_linear = (LinearLayout) view.findViewById(R.id.delay_on_connect_linear);
        final LinearLayout oil_linear = (LinearLayout) view.findViewById(R.id.oil_linear);
        final TextView delay_on_connect_title = (TextView) view.findViewById(R.id.delay_on_connect_title);


        if (Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V1).equals("0")) {
            pressure_v1.clearCheck();
            v3_manual.setChecked(true);
            delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.infobox_disable));
            oil_linear.setBackground(getResources().getDrawable(R.drawable.forground_gray));
            pressure_v4.setTextColor(Color.parseColor("#ababab"));
            pressure_v4_up.setOnTouchListener(null);
            pressure_v4_down.setOnTouchListener(null);
            pressure_v4_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_gray_24dp));
            pressure_v4_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_gray_24dp));


            manual_oil.setBackground(getResources().getDrawable(R.drawable.gradient));
            manual_oil.setEnabled(true);
            manual_oil.setClickable(true);
            manual_oil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog noRoomFoundDialog = new Dialog(getActivity());
                    noRoomFoundDialog.setContentView(R.layout.dialog_manual);

                    LinearLayout save_difrast = noRoomFoundDialog.findViewById(R.id.save_difrast);
                    LinearLayout close_difrast = noRoomFoundDialog.findViewById(R.id.close_difrast);

                    save_difrast.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PrepareDataAndSendSms prepareDataAndSendSms = new PrepareDataAndSendSms(getActivity());
                            prepareDataAndSendSms.send_direct_oil(userModel.get_mobile(), "05");

                            db.createOrder(prepareDataAndSendSms.Global_Message,
                                    db.getRoom(Config.currentRoomID).get_number(),
                                    Config._STATE_PENDING,
                                    Config._REPORT_FROM_CLIENT,
                                    "", new HashMap<String, String>(),
                                    Config._MANUAL_OIL,
                                    Config.currentRoomID,
                                    db.getFirstUser_for_room(Config.currentRoomID).get_id());
                            noRoomFoundDialog.dismiss();
                        }
                    });

                    TextView msg = noRoomFoundDialog.findViewById(R.id.msg);
                    msg.setText("با اینکار شما تنظیمات فشار روغن دستی را ارسال می کنید." + "\n" + "مطمئن هستید ؟");
                    close_difrast.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            noRoomFoundDialog.dismiss();
                        }
                    });

                    noRoomFoundDialog.show();
                }
            });

        } else {
            pressure_v1.clearCheck();
            v3_automatic.setChecked(true);
            delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.info_box));
            oil_linear.setBackground(getResources().getDrawable(R.drawable.info_box_header_right));
            pressure_v4_up.setOnTouchListener(repeatListener4_1);
            pressure_v4_down.setOnTouchListener(repeatListener4_2);
            pressure_v4_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
            pressure_v4_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
            delay_on_connect_title.setTextColor(getResources().getColor(android.R.color.white));
            pressure_v4.setTextColor(getResources().getColor(android.R.color.black));


            manual_oil.setBackgroundColor(Color.parseColor("#dadada"));
            manual_oil.setEnabled(false);
            manual_oil.setClickable(false);
        }


        pressure_v1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pressure_v1_manual:
                        Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V1, "0");
                        delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.infobox_disable));
                        oil_linear.setBackground(getResources().getDrawable(R.drawable.forground_gray));
                        pressure_v4.setTextColor(Color.parseColor("#ababab"));
                        pressure_v4_up.setOnTouchListener(null);
                        pressure_v4_down.setOnTouchListener(null);
                        pressure_v4_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_gray_24dp));
                        pressure_v4_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_gray_24dp));


                        manual_oil.setBackground(getResources().getDrawable(R.drawable.gradient));
                        manual_oil.setEnabled(true);
                        manual_oil.setClickable(true);
                        manual_oil.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog noRoomFoundDialog = new Dialog(getActivity());
                                noRoomFoundDialog.setContentView(R.layout.dialog_manual);

                                LinearLayout save_difrast = noRoomFoundDialog.findViewById(R.id.save_difrast);
                                LinearLayout close_difrast = noRoomFoundDialog.findViewById(R.id.close_difrast);

                                save_difrast.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        PrepareDataAndSendSms prepareDataAndSendSms = new PrepareDataAndSendSms(getActivity());
                                        prepareDataAndSendSms.send_direct_oil(userModel.get_mobile(), "05");

                                        db.createOrder(prepareDataAndSendSms.Global_Message,
                                                db.getRoom(Config.currentRoomID).get_number(),
                                                Config._STATE_PENDING,
                                                Config._REPORT_FROM_CLIENT,
                                                "", new HashMap<String, String>(),
                                                Config._MANUAL_OIL,
                                                Config.currentRoomID,
                                                db.getFirstUser_for_room(Config.currentRoomID).get_id());
                                        noRoomFoundDialog.dismiss();
                                    }
                                });

                                TextView msg = noRoomFoundDialog.findViewById(R.id.msg);
                                msg.setText("با اینکار شما تنظیمات فشار روغن دستی را ارسال می کنید." + "\n" + "مطمئن هستید ؟");
                                close_difrast.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        noRoomFoundDialog.dismiss();
                                    }
                                });

                                noRoomFoundDialog.show();
                            }
                        });

                        break;
                    case R.id.pressure_v1_automatic:
                        Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V1, "1");
                        delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.info_box));
                        oil_linear.setBackground(getResources().getDrawable(R.drawable.info_box_header_right));
                        pressure_v4_up.setOnTouchListener(repeatListener4_1);
                        pressure_v4_down.setOnTouchListener(repeatListener4_2);
                        pressure_v4_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                        pressure_v4_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                        delay_on_connect_title.setTextColor(getResources().getColor(android.R.color.white));
                        pressure_v4.setTextColor(getResources().getColor(android.R.color.black));


                        manual_oil.setBackgroundColor(Color.parseColor("#dadada"));
                        manual_oil.setEnabled(false);
                        manual_oil.setClickable(false);

                        break;
                }
            }
        });


        return view;
    }

    void findview(View view) {

        pressure_v1 = (RadioGroup) view.findViewById(R.id.pressure_v1);
        pressure_v2 = (TextView) view.findViewById(R.id.pressure_v2);
        pressure_v3 = (TextView) view.findViewById(R.id.pressure_v3);
        pressure_v4 = (TextView) view.findViewById(R.id.pressure_v4);
        pressure_v5 = (TextView) view.findViewById(R.id.pressure_v5);

        pressure_v1_automatic = (RadioButton) view.findViewById(R.id.pressure_v1_automatic);
        pressure_v1_manual = (RadioButton) view.findViewById(R.id.pressure_v1_manual);
        pressure_v2_up = (Button) view.findViewById(R.id.pressure_v2_up);
        pressure_v2_down = (Button) view.findViewById(R.id.pressure_v2_down);
        pressure_v3_up = (Button) view.findViewById(R.id.pressure_v3_up);
        pressure_v3_down = (Button) view.findViewById(R.id.pressure_v3_down);
        pressure_v4_up = (Button) view.findViewById(R.id.pressure_v4_up);
        pressure_v4_down = (Button) view.findViewById(R.id.pressure_v4_down);
        pressure_v5_up = (Button) view.findViewById(R.id.pressure_v5_up);
        pressure_v5_down = (Button) view.findViewById(R.id.pressure_v5_down);
        manual_oil = view.findViewById(R.id.manual_oil);
    }

    void loadRepeat() {

        repeatListener2_1 = new RepeatListener(50, 50, 100f, 10f, 1f, true,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener2_1.check(repeatListener2_1.Max_num, repeatListener2_1.Min_num, repeatListener2_1.Count_num, repeatListener2_1.up_down, getContext());
                pressure_v2.setText(Math.round(repeatListener2_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V2, Math.round(repeatListener2_1.result) + "");
            }
        }, getContext());
        repeatListener2_2 = new RepeatListener(50, 50, 100f, 10f, (repeatListener2_1.Count_num), false,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener2_1.check(repeatListener2_1.Max_num, repeatListener2_1.Min_num, repeatListener2_1.Count_num, repeatListener4_2.up_down, getContext());
                pressure_v2.setText(Math.round(repeatListener2_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V2, Math.round(repeatListener2_1.result) + "");
            }
        }, getContext());


        repeatListener3_1 = new RepeatListener(50, 50, 20f, 2f, 1f, true,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3_1.check(repeatListener3_1.Max_num, repeatListener3_1.Min_num, repeatListener3_1.Count_num, repeatListener3_1.up_down, getContext());
                pressure_v3.setText(Math.round(repeatListener3_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V3, Math.round(repeatListener3_1.result) + "");
            }
        }, getContext());
        repeatListener3_2 = new RepeatListener(100, 400, 20f, 2f, (repeatListener3_1.Count_num), false,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V3)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3_1.check(repeatListener3_1.Max_num, repeatListener3_1.Min_num, repeatListener3_1.Count_num, repeatListener5_2.up_down, getContext());
                pressure_v3.setText(Math.round(repeatListener3_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V3, Math.round(repeatListener3_1.result) + "");
            }
        }, getContext());

        repeatListener4_1 = new RepeatListener(40, 40, 300f, 5f, 1f, true,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener4_1.check(repeatListener4_1.Max_num, repeatListener4_1.Min_num, repeatListener4_1.Count_num, repeatListener4_1.up_down, getContext());
                pressure_v4.setText(Math.round(repeatListener4_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V4, Math.round(repeatListener4_1.result) + "");
            }
        }, getContext());
        repeatListener4_2 = new RepeatListener(40, 40, 300f, 5f, (repeatListener4_1.Count_num), false,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener4_1.check(repeatListener4_1.Max_num, repeatListener4_1.Min_num, repeatListener4_1.Count_num, repeatListener4_2.up_down, getContext());
                pressure_v4.setText(Math.round(repeatListener4_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V4, Math.round(repeatListener4_1.result) + "");
            }
        }, getContext());

        repeatListener5_1 = new RepeatListener(40, 40, 99f, 1f, 1f, true,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5_1.check(repeatListener5_1.Max_num, repeatListener5_1.Min_num, repeatListener5_1.Count_num, repeatListener5_1.up_down, getContext());
                pressure_v5.setText(Math.round(repeatListener5_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V5, Math.round(repeatListener5_1.result) + "");
            }
        }, getContext());
        repeatListener5_2 = new RepeatListener(40, 40, 99f, 1f, (repeatListener5_1.Count_num), false,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5_1.check(repeatListener5_1.Max_num, repeatListener5_1.Min_num, repeatListener5_1.Count_num, repeatListener5_2.up_down, getContext());
                pressure_v5.setText(Math.round(repeatListener5_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V5, Math.round(repeatListener5_1.result) + "");
            }
        }, getContext());

    }

}
