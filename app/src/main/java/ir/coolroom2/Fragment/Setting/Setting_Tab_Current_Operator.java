package ir.coolroom2.Fragment.Setting;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
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
 * Created by JFP on 12/7/2017.
 */

public class Setting_Tab_Current_Operator extends Fragment {

    Button current_v1_up;
    Button current_v1_down;
    Button current_v2_up;
    Button current_v2_down;
    Button current_v4_up;
    Button current_v4_down;
    Button current_v5_up;
    Button current_v5_down;
    Button current_v6_up;
    Button current_v6_down;
    Button current_v7_up;
    Button current_v7_down;

    RepeatListener
            repeatListener1_1,
            repeatListener1_2,
            repeatListener2_1,
            repeatListener2_2,
            repeatListener4_1,
            repeatListener4_2,
            repeatListener5_1,
            repeatListener5_2,
            repeatListener6_1,
            repeatListener6_2,
            repeatListener7_1,
            repeatListener7_2;

    TextView current_v1;
    TextView current_v2;
    TextView current_v4;
    TextView current_v5;
    TextView current_v6;
    TextView current_v7;
    TextView second_v1, tv_v1;
    Button manual_ovaperator;

    RadioGroup rest_radioButton;

    DatabaseHandler db;

    UserModel userModel = null;


    RadioButton current_v3_automatic, current_v3_manual;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_tab_current_operator, container, false);

        db = new DatabaseHandler(getActivity());
        userModel = db.getFirstUser_for_room(Config.currentRoomID);
        findview(view);
        current_v1.setText(String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V1))) + "");
        current_v2.setText(String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V2))) + "");
        current_v4.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V4)) + "");
        current_v5.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V5)) + "");
        current_v6.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V6)) + "");
        current_v7.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V7)) + "");
        loadRepeat();

        final LinearLayout delay_on_connect_linear = (LinearLayout) view.findViewById(R.id.delay_on_connect_linear);
        final LinearLayout info_box_header_right = (LinearLayout) view.findViewById(R.id.linear_right);
        final LinearLayout clickable_linear = (LinearLayout) view.findViewById(R.id.clickable_linear);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V3).equals("0")) {
                    rest_radioButton.clearCheck();
                    current_v3_manual.setChecked(true);
                    delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.infobox_disable));
                    info_box_header_right.setBackground(getResources().getDrawable(R.drawable.forground_gray));
                    current_v4_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_gray_24dp));
                    current_v4_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_gray_24dp));
                    current_v4.setTextColor(Color.parseColor("#ababab"));
                    second_v1.setTextColor(Color.parseColor("#ababab"));
                    current_v4_up.setOnTouchListener(null);
                    current_v4_down.setOnTouchListener(null);
                    current_v4_up.setClickable(false);
                    current_v4_down.setClickable(false);


                    manual_ovaperator.setBackground(getResources().getDrawable(R.drawable.gradient));
                    manual_ovaperator.setEnabled(true);
                    manual_ovaperator.setClickable(true);
                    manual_ovaperator.setOnClickListener(new View.OnClickListener() {
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
                                    prepareDataAndSendSms.send_direct_ovapertor(userModel.get_mobile(), "01");

                                    db.createOrder(prepareDataAndSendSms.Global_Message,
                                            db.getRoom(Config.currentRoomID).get_number(),
                                            Config._STATE_PENDING,
                                            Config._REPORT_FROM_CLIENT,
                                            "", new HashMap<String, String>(),
                                            Config._MANUAL_OVAPERATOR,
                                            Config.currentRoomID,
                                            db.getFirstUser_for_room(Config.currentRoomID).get_id());
                                    noRoomFoundDialog.dismiss();
                                }
                            });

                            TextView msg = noRoomFoundDialog.findViewById(R.id.msg);
                            msg.setText("با اینکار شما تنظیمات اواپراتور دستی را ارسال می کنید." + "\n" + "مطمئن هستید ؟");
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
                    rest_radioButton.clearCheck();
                    current_v3_automatic.setChecked(true);
                    delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.info_box));
                    info_box_header_right.setBackground(getResources().getDrawable(R.drawable.info_box_header_right));
                    current_v4.setTextColor(Color.parseColor("#000000"));
                    tv_v1.setTextColor(Color.parseColor("#ffffff"));
                    second_v1.setTextColor(Color.parseColor("#000000"));
                    current_v4_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                    current_v4_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                    current_v4_up.setOnTouchListener(repeatListener4_1);
                    current_v4_down.setOnTouchListener(repeatListener4_2);
                    current_v4_up.setClickable(true);
                    current_v4_down.setClickable(true);


                    manual_ovaperator.setBackgroundColor(Color.parseColor("#dadada"));
                    manual_ovaperator.setEnabled(false);
                    manual_ovaperator.setClickable(false);
                }
            }
        }, 1000);

        current_v1_up.setOnTouchListener(repeatListener1_1);
        current_v1_down.setOnTouchListener(repeatListener1_2);

        current_v2_up.setOnTouchListener(repeatListener2_1);
        current_v2_down.setOnTouchListener(repeatListener2_2);

        current_v4_up.setOnTouchListener(null);
        current_v4_down.setOnTouchListener(null);

        current_v5_up.setOnTouchListener(repeatListener5_1);
        current_v5_down.setOnTouchListener(repeatListener5_2);

        current_v6_up.setOnTouchListener(repeatListener6_1);
        current_v6_down.setOnTouchListener(repeatListener6_2);

        current_v7_up.setOnTouchListener(repeatListener7_1);
        current_v7_down.setOnTouchListener(repeatListener7_2);


        rest_radioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.current_v3_manual:
                        Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V3, "0");
                        delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.infobox_disable));
                        info_box_header_right.setBackground(getResources().getDrawable(R.drawable.forground_gray));
                        current_v4_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_gray_24dp));
                        current_v4_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_gray_24dp));
                        current_v4.setTextColor(Color.parseColor("#ababab"));
                        second_v1.setTextColor(Color.parseColor("#ababab"));
                        current_v4_up.setOnTouchListener(null);
                        current_v4_down.setOnTouchListener(null);
                        current_v4_up.setClickable(false);
                        current_v4_down.setClickable(false);


                        manual_ovaperator.setBackground(getResources().getDrawable(R.drawable.gradient));
                        manual_ovaperator.setEnabled(true);
                        manual_ovaperator.setClickable(true);
                        manual_ovaperator.setOnClickListener(new View.OnClickListener() {
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
                                        prepareDataAndSendSms.send_direct_ovapertor(userModel.get_mobile(), "01");

                                        db.createOrder(prepareDataAndSendSms.Global_Message,
                                                db.getRoom(Config.currentRoomID).get_number(),
                                                Config._STATE_PENDING,
                                                Config._REPORT_FROM_CLIENT,
                                                "", new HashMap<String, String>(),
                                                Config._MANUAL_OVAPERATOR,
                                                Config.currentRoomID,
                                                db.getFirstUser_for_room(Config.currentRoomID).get_id());
                                        noRoomFoundDialog.dismiss();
                                    }
                                });

                                TextView msg = noRoomFoundDialog.findViewById(R.id.msg);
                                msg.setText("با اینکار شما تنظیمات اواپراتور دستی را ارسال می کنید." + "\n" + "مطمئن هستید ؟");
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
                    case R.id.current_v3_automatic:
                        Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V3, "1");
                        delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.info_box));
                        info_box_header_right.setBackground(getResources().getDrawable(R.drawable.info_box_header_right));
                        current_v4.setTextColor(Color.parseColor("#000000"));
                        tv_v1.setTextColor(Color.parseColor("#ffffff"));
                        second_v1.setTextColor(Color.parseColor("#000000"));
                        current_v4_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                        current_v4_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                        current_v4_up.setOnTouchListener(repeatListener4_1);
                        current_v4_down.setOnTouchListener(repeatListener4_2);
                        current_v4_up.setClickable(true);
                        current_v4_down.setClickable(true);

                        manual_ovaperator.setBackgroundColor(Color.parseColor("#dadada"));
                        manual_ovaperator.setEnabled(false);
                        manual_ovaperator.setClickable(false);


                        break;
                }
            }
        });


        return view;
    }

    void findview(View view) {
        current_v1 = (TextView) view.findViewById(R.id.current_v1);
        current_v2 = (TextView) view.findViewById(R.id.current_v2);
        current_v4 = (TextView) view.findViewById(R.id.current_v4);
        current_v5 = (TextView) view.findViewById(R.id.current_v5);
        current_v6 = (TextView) view.findViewById(R.id.current_v6);
        current_v7 = (TextView) view.findViewById(R.id.current_v7);


        current_v1_up = (Button) view.findViewById(R.id.current_v1_up);
        current_v1_down = (Button) view.findViewById(R.id.current_v1_down);
        current_v2_up = (Button) view.findViewById(R.id.current_v2_up);
        current_v2_down = (Button) view.findViewById(R.id.current_v2_down);
        current_v4_up = (Button) view.findViewById(R.id.current_v4_up);
        current_v4_down = (Button) view.findViewById(R.id.current_v4_down);
        current_v5_up = (Button) view.findViewById(R.id.current_v5_up);
        current_v5_down = (Button) view.findViewById(R.id.current_v5_down);
        current_v6_up = (Button) view.findViewById(R.id.current_v6_up);
        current_v6_down = (Button) view.findViewById(R.id.current_v6_down);
        current_v7_up = (Button) view.findViewById(R.id.current_v7_up);
        current_v7_down = (Button) view.findViewById(R.id.current_v7_down);

        rest_radioButton = (RadioGroup) view.findViewById(R.id.rest_radiogroup);

        second_v1 = (TextView) view.findViewById(R.id.second_v1);
        tv_v1 = (TextView) view.findViewById(R.id.tv_v1);

        current_v3_manual = view.findViewById(R.id.current_v3_manual);
        current_v3_automatic = view.findViewById(R.id.current_v3_automatic);

        manual_ovaperator = view.findViewById(R.id.manual_ovaperator);

    }

    void loadRepeat() {

        repeatListener1_1 = new RepeatListener(40, 40, 10f, 0.1f, 0.1f, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V1)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1_1.check(repeatListener1_1.Max_num, repeatListener1_1.Min_num, repeatListener1_1.Count_num, repeatListener1_1.up_down, getContext());
                current_v1.setText(String.format("%.1f", repeatListener1_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V1, (repeatListener1_1.result) + "");
            }
        }, getContext());
        repeatListener1_2 = new RepeatListener(40, 40, 10f, 0.1f, (repeatListener1_1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V1)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1_1.check(repeatListener1_1.Max_num, repeatListener1_1.Min_num, repeatListener1_1.Count_num, repeatListener1_2.up_down, getContext());
                current_v1.setText(String.format("%.1f", repeatListener1_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V1, (repeatListener1_1.result) + "");
            }
        }, getContext());

        repeatListener2_1 = new RepeatListener(40, 40, 10f, 0f, 0.1f, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener2_1.check(repeatListener2_1.Max_num, repeatListener2_1.Min_num, repeatListener2_1.Count_num, repeatListener2_1.up_down, getContext());
                current_v2.setText(String.format("%.1f", repeatListener2_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V2, (repeatListener2_1.result) + "");
            }
        }, getContext());
        repeatListener2_2 = new RepeatListener(40, 40, 10f, 0f, (repeatListener2_1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener2_1.check(repeatListener2_1.Max_num, repeatListener2_1.Min_num, repeatListener2_1.Count_num, repeatListener2_2.up_down, getContext());
                current_v2.setText(String.format("%.1f", repeatListener2_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V2, (repeatListener2_1.result) + "");
            }
        }, getContext());


        repeatListener4_1 = new RepeatListener(40, 40, 300f, 5f, 1f, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener4_1.check(repeatListener4_1.Max_num, repeatListener4_1.Min_num, repeatListener4_1.Count_num, repeatListener4_1.up_down, getContext());
                current_v4.setText(Math.round(repeatListener4_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V4, Math.round(repeatListener4_1.result) + "");
            }
        }, getContext());
        repeatListener4_2 = new RepeatListener(40, 40, 300f, 5f, (repeatListener4_1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V4)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener4_1.check(repeatListener4_1.Max_num, repeatListener4_1.Min_num, repeatListener4_1.Count_num, repeatListener4_2.up_down, getContext());
                current_v4.setText(Math.round(repeatListener4_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V4, Math.round(repeatListener4_1.result) + "");
            }
        }, getContext());


        repeatListener5_1 = new RepeatListener(40, 40, 99f, 1f, 1f, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5_1.check(repeatListener5_1.Max_num, repeatListener5_1.Min_num, repeatListener5_1.Count_num, repeatListener5_1.up_down, getContext());
                current_v5.setText(Math.round(repeatListener5_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V5, Math.round(repeatListener5_1.result) + "");
            }
        }, getContext());
        repeatListener5_2 = new RepeatListener(40, 40, 99f, 1f, (repeatListener5_1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V5)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener5_1.check(repeatListener5_1.Max_num, repeatListener5_1.Min_num, repeatListener5_1.Count_num, repeatListener5_2.up_down, getContext());
                current_v5.setText(Math.round(repeatListener5_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V5, Math.round(repeatListener5_1.result) + "");
            }
        }, getContext());

        repeatListener6_1 = new RepeatListener(40, 40, 15f, 5f, 1f, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener6_1.check(repeatListener6_1.Max_num, repeatListener6_1.Min_num, repeatListener6_1.Count_num, repeatListener6_1.up_down, getContext());
                current_v6.setText(Math.round(repeatListener6_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V6, Math.round(repeatListener6_1.result) + "");
            }
        }, getContext());
        repeatListener6_2 = new RepeatListener(40, 40, 15f, 5f, (repeatListener6_1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V6)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener6_1.check(repeatListener6_1.Max_num, repeatListener6_1.Min_num, repeatListener6_1.Count_num, repeatListener6_2.up_down, getContext());
                current_v6.setText(Math.round(repeatListener6_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V6, Math.round(repeatListener6_1.result) + "");
            }
        }, getContext());


        repeatListener7_1 = new RepeatListener(40, 40, 20f, 1f, 1f, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V7)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7_1.check(repeatListener7_1.Max_num, repeatListener7_1.Min_num, repeatListener7_1.Count_num, repeatListener7_1.up_down, getContext());
                current_v7.setText(Math.round(repeatListener7_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V7, Math.round(repeatListener7_1.result) + "");
            }
        }, getContext());
        repeatListener7_2 = new RepeatListener(40, 40, 20f, 1f, (repeatListener7_1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V7)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener7_1.check(repeatListener7_1.Max_num, repeatListener7_1.Min_num, repeatListener7_1.Count_num, repeatListener7_2.up_down, getContext());
                current_v7.setText(Math.round(repeatListener7_1.result) + "");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V7, Math.round(repeatListener7_1.result) + "");
            }
        }, getContext());
    }
}
