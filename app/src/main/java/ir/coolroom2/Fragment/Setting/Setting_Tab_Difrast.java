package ir.coolroom2.Fragment.Setting;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.HashMap;

import ir.coolroom2.ArcRound;
import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.RepeatListener;
import ir.coolroom2.RepeatListener2;
import ir.coolroom2.Sms.PrepareDataAndSendSms;
import ir.coolroom2.Sms.SmsCodeAndDecode;

import static ir.coolroom2.Config.KEY_DEFROST_V2;

/**
 * Created by JFP on 12/2/2017.
 */

public class Setting_Tab_Difrast extends Fragment {

    private static final String KEY_DEFROST_V1 = "defrost_V1";

    private ToggleButton one, two, three, four, five, six;
    private Button up, down;
    private View view;
    private TextView textClock;
    private Handler mHandler;
    private TextView saat_tv;
    private ArcRound defrost;
    Button manual_difrast;

    int minut = 0;
    int second = 0;
    TextView timer_tv;

    int saat = 1;
    int[] minuts = new int[144];
    String[] names = new String[144];
    private SmsCodeAndDecode hexBinConvertor = new SmsCodeAndDecode();
    private SmsCodeAndDecode sms_CodeAndDecode = new SmsCodeAndDecode();

    private final String KEY_TIMER = "timer";

    TextView defrost_v2;
    RepeatListener repeatListener1;
    RepeatListener repeatListener2;

    private PrepareDataAndSendSms prepareDataAndSendSms;
    UserModel userModel = null;

    DatabaseHandler db;

    RepeatListener repeatListener3, repeatListener4;



    /*Termodisk*/

    RadioGroup termodisk_v1;
    RadioButton off;
    RadioButton on;
    Button termodisk_v2_down;
    Button termodisk_v2_up;
    TextView termodisk_v2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_tab_difrast, container, false);
        find_items();
        findview(view);
        loadRepeat();

        prepareDataAndSendSms = new PrepareDataAndSendSms(getActivity());
        db = new DatabaseHandler(getActivity());
        userModel = db.getFirstUser_for_room(Config.currentRoomID);


        manual_difrast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog noRoomFoundDialog = new Dialog(getActivity());
                noRoomFoundDialog.setContentView(R.layout.dialog_manual_difrast);
                timer_tv = (TextView) noRoomFoundDialog.findViewById(R.id.timer_tv);
                minut = Integer.parseInt(Config.general_data_setting.get(KEY_TIMER).substring(2, 4));


                second = updateClock(sms_CodeAndDecode.truecent_length(Integer.toString(minut), 2));
                noRoomFoundDialog.findViewById(R.id.second_inc).setOnTouchListener(new RepeatListener2(1000, 100, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (second < 60) {
                            ++second;
                            updateClock(second + "");
                        } else {
                            second = 5;
                            updateClock(second + "");
                        }
                        Config.general_data_setting.put(KEY_TIMER, "00" + sms_CodeAndDecode.truecent_length(Integer.toString(second), 2));
                    }
                }));
                noRoomFoundDialog.findViewById(R.id.second_dec).setOnTouchListener(new RepeatListener2(1000, 100, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (second > 5) {
                            --second;
                            updateClock(second + "");

                        } else {
                            second = 60;
                            updateClock(second + "");
                        }
                        Config.general_data_setting.put(KEY_TIMER, "00" + sms_CodeAndDecode.truecent_length(Integer.toString(second), 2));
                    }
                }));

                LinearLayout close = noRoomFoundDialog.findViewById(R.id.close_difrast);
                LinearLayout save = noRoomFoundDialog.findViewById(R.id.save_difrast);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noRoomFoundDialog.hide();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        prepareDataAndSendSms.send_direct_command(userModel.get_mobile(), "00", String.valueOf((second < 10 ? "0" + second : second)));
                        noRoomFoundDialog.hide();

                        db.createOrder("",
                                db.getRoom(Config.currentRoomID).get_number(),
                                Config._STATE_PENDING,
                                Config._REPORT_FROM_CLIENT,
                                "", new HashMap<String, String>(),
                                Config._MANUAL_DEFROST,
                                Config.currentRoomID,
                                db.getFirstUser_for_room(Config.currentRoomID).get_id());
                    }
                });
                noRoomFoundDialog.show();


            }
        });


        Button defrost_v2_up = (Button) view.findViewById(R.id.defrost_v2_up);
        Button defrost_v2_down = (Button) view.findViewById(R.id.defrost_v2_down);
        defrost_v2 = (TextView) view.findViewById(R.id.defrost_v2);
        defrost_v2.setText(Config.general_data_setting.get(Config.KEY_DEFROST_V2));


        repeatListener1 = new RepeatListener(40, 40, 99f, 0f, 1f, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_DEFROST_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener1.up_down, getContext());
                defrost_v2.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(Config.KEY_DEFROST_V2, Math.round(repeatListener1.result) + "");
            }
        }, getContext());
        repeatListener2 = new RepeatListener(100, 40, 99f, 0f, (repeatListener1.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_DEFROST_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener1.check(repeatListener1.Max_num, repeatListener1.Min_num, repeatListener1.Count_num, repeatListener2.up_down, getContext());
                defrost_v2.setText(Math.round(repeatListener1.result) + "");
                Config.general_data_setting.put(Config.KEY_DEFROST_V2, Math.round(repeatListener1.result) + "");
            }
        }, getContext());

        defrost_v2_up.setOnTouchListener(repeatListener1);
        defrost_v2_down.setOnTouchListener(repeatListener2);


        textClock = (TextView) view.findViewById(R.id.textClock);
        Calendar c = Calendar.getInstance();
        int min = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        textClock.setText(sms_CodeAndDecode.truecent_length(hour + "", 2) + ":" + sms_CodeAndDecode.truecent_length(min + "", 2));
        mHandler = new Handler();
        timer();

        String binaries = (Config.general_data_setting.get(KEY_DEFROST_V1));
        for (int i = 0; i < 144; i++) { //todo test initializing
            minuts[i] = Integer.parseInt(String.valueOf(binaries.charAt(i)));

            /*Changed ! */
            // TODO: 6/7/2018  Chaneged Defrost section
            if (minuts[i] == 1)
                names[i] = "DEF";
            else
                names[i] = "COM";

        }

        defrost.minute10 = minuts;
        defrost.indicatorStart = (saat * 15) - 105;
        update_state();


        defrost.minute10 = minuts;
        defrost.indicatorStart = (saat * 15) - 105;
        defrost.invalidate();


        up.setOnTouchListener(new RepeatListener2(600, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (saat + 1 != 25) {
                    saat++;
                    defrost.indicatorStart = (saat * 15) - 105;
                    defrost.minute10 = minuts;
                    update_state();
                } else {
                    saat = 1;
                    defrost.indicatorStart = (saat * 15) - 105;
                    defrost.minute10 = minuts;
                    update_state();
                }
            }
        }));

        down.setOnTouchListener(new RepeatListener2(600, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saat - 1 != 0) {
                    saat--;
                    defrost.indicatorStart = (saat * 15) - 105;
                    defrost.minute10 = minuts;
                    update_state();
                } else {
                    saat = 24;
                    defrost.indicatorStart = (saat * 15) - 105;
                    defrost.minute10 = minuts;
                    update_state();
                }
            }
        }));

        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                update_map();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                update_map();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                update_map();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                update_map();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                update_map();
            }
        });
        six.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                update_map();
            }
        });
        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                minuts[(saat - 1) * 6] = (isChecked) ? 0 : 1;
                defrost.indicatorStart = (saat * 15) - 105;
                defrost.minute10 = minuts;
                update_state();
            }
        });
        two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                minuts[(saat - 1) * 6 + 1] = (isChecked) ? 0 : 1;
                defrost.indicatorStart = (saat * 15) - 105;
                defrost.minute10 = minuts;
                update_state();
            }
        });
        three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                minuts[(saat - 1) * 6 + 2] = (isChecked) ? 0 : 1;
                defrost.indicatorStart = (saat * 15) - 105;
                defrost.minute10 = minuts;
                update_state();
            }
        });
        four.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                minuts[(saat - 1) * 6 + 3] = (isChecked) ? 0 : 1;
                defrost.indicatorStart = (saat * 15) - 105;
                defrost.minute10 = minuts;
                update_state();
            }
        });
        five.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                minuts[(saat - 1) * 6 + 4] = (isChecked) ? 0 : 1;
                defrost.indicatorStart = (saat * 15) - 105;
                defrost.minute10 = minuts;
                update_state();
            }
        });
        six.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                minuts[(saat - 1) * 6 + 5] = (isChecked) ? 0 : 1;
                defrost.indicatorStart = (saat * 15) - 105;
                defrost.minute10 = minuts;
                update_state();
            }
        });


        /*Termodisk*/

        termodisk_v2_up.setOnTouchListener(repeatListener3);
        termodisk_v2_down.setOnTouchListener(repeatListener4);


        final LinearLayout delay_on_connect_linear = (LinearLayout) view.findViewById(R.id.pomp_down_linear);
        final LinearLayout linear_right = (LinearLayout) view.findViewById(R.id.linear_right);
        final TextView pomp_down_title = (TextView) view.findViewById(R.id.pomp_down_title);

        if (Config.general_data_setting.get(Config.KEY_TERMODISK_V1).equals("1")) {
            termodisk_v1.clearCheck();
            on.setChecked(true);
            Config.general_data_setting.put(Config.KEY_TERMODISK_V1, "1");
            delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.info_box));
            linear_right.setBackground(getResources().getDrawable(R.drawable.info_box_header_right));
            termodisk_v2_up.setOnTouchListener(repeatListener3);
            termodisk_v2_down.setOnTouchListener(repeatListener4);
            termodisk_v2_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
            termodisk_v2_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
            pomp_down_title.setTextColor(getResources().getColor(android.R.color.white));
            termodisk_v2.setTextColor(getResources().getColor(android.R.color.black));


        } else {
            termodisk_v1.clearCheck();
            off.setChecked(true);
            Config.general_data_setting.put(Config.KEY_TERMODISK_V1, "0");
            delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.infobox_disable));
            linear_right.setBackground(getResources().getDrawable(R.drawable.forground_gray));
            termodisk_v2.setTextColor(Color.parseColor("#ababab"));
            termodisk_v2_up.setOnTouchListener(null);
            termodisk_v2_down.setOnTouchListener(null);
            termodisk_v2_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_gray_24dp));
            termodisk_v2_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_gray_24dp));
        }

        termodisk_v1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
                switch (checkid) {
                    case R.id.termodisk_v1_on:
                        Config.general_data_setting.put(Config.KEY_TERMODISK_V1, "1");
                        delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.info_box));
                        linear_right.setBackground(getResources().getDrawable(R.drawable.info_box_header_right));
                        termodisk_v2_up.setOnTouchListener(repeatListener3);
                        termodisk_v2_down.setOnTouchListener(repeatListener4);
                        termodisk_v2_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                        termodisk_v2_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                        pomp_down_title.setTextColor(getResources().getColor(android.R.color.white));
                        termodisk_v2.setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case R.id.termodisk_v1_off:
                        Config.general_data_setting.put(Config.KEY_TERMODISK_V1, "0");
                        delay_on_connect_linear.setBackground(getResources().getDrawable(R.drawable.infobox_disable));
                        linear_right.setBackground(getResources().getDrawable(R.drawable.forground_gray));
                        termodisk_v2.setTextColor(Color.parseColor("#ababab"));
                        termodisk_v2_up.setOnTouchListener(null);
                        termodisk_v2_down.setOnTouchListener(null);
                        termodisk_v2_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_gray_24dp));
                        termodisk_v2_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_gray_24dp));
                        break;
                }
            }
        });


        return view;
    }

    public void timer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(30000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                Calendar c = Calendar.getInstance();
                                int min = c.get(Calendar.MINUTE);
                                int hour = c.get(Calendar.HOUR_OF_DAY);
                                textClock.setText(sms_CodeAndDecode.truecent_length(hour + "", 2) + ":" + sms_CodeAndDecode.truecent_length(min + "", 2));
                            }
                        });
                    } catch (Exception ignored) {
                    }
                }
            }
        }).start();
    }

    public void find_items() {
        one = (ToggleButton) view.findViewById(R.id.one);
        two = (ToggleButton) view.findViewById(R.id.two);
        three = (ToggleButton) view.findViewById(R.id.three);
        four = (ToggleButton) view.findViewById(R.id.four);
        five = (ToggleButton) view.findViewById(R.id.five);
        six = (ToggleButton) view.findViewById(R.id.six);

        saat_tv = (TextView) view.findViewById(R.id.saat);

        up = (Button) view.findViewById(R.id.up);
        down = (Button) view.findViewById(R.id.down);
        defrost = (ArcRound) view.findViewById(R.id.defrost);
        manual_difrast = (Button) view.findViewById(R.id.manual_difrast);
    }

    public void update_state() {
        saat_tv.setText(Integer.toString(saat - 1));

        if (minuts[(saat - 1) * 6] == 0) {
            one.setChecked(true);
        } else {
            one.setChecked(false);
        }
        if (minuts[(saat - 1) * 6 + 1] == 0) {
            two.setChecked(true);
        } else {
            two.setChecked(false);
        }
        if (minuts[(saat - 1) * 6 + 2] == 0) {
            three.setChecked(true);
        } else {
            three.setChecked(false);
        }
        if (minuts[(saat - 1) * 6 + 3] == 0) {
            four.setChecked(true);
        } else {
            four.setChecked(false);
        }
        if (minuts[(saat - 1) * 6 + 4] == 0) {
            five.setChecked(true);
        } else {
            five.setChecked(false);
        }
        if (minuts[(saat - 1) * 6 + 5] == 0) {
            six.setChecked(true);
        } else {
            six.setChecked(false);
        }

        defrost.invalidate();

    }

    public void update_map() {
        String tmp = "";
        for (int i = 0; i < 144; i++) {
            tmp += Integer.toString(minuts[i]);
        }
        Config.general_data_setting.put(KEY_DEFROST_V1, tmp);
    }

    private int updateClock(String timer) {
        timer_tv.setText(timer);
        return Integer.parseInt(timer);
    }

    public void findview(View view) {
        termodisk_v1 = view.findViewById(R.id.termodisk_v1);
        off = view.findViewById(R.id.termodisk_v1_off);
        on = view.findViewById(R.id.termodisk_v1_on);
        termodisk_v2_down = view.findViewById(R.id.termodisk_v2_down);
        termodisk_v2_up = view.findViewById(R.id.termodisk_v2_up);
        termodisk_v2 = view.findViewById(R.id.termodisk_v2);
        termodisk_v2.setText(Config.general_data_setting.get(Config.KEY_TERMODISK_V2) + "");

    }

    void loadRepeat() {
        termodisk_v2.setText(Config.general_data_setting.get(Config.KEY_TERMODISK_V2));
        repeatListener3 = new RepeatListener(40, 40, 15f, -10f, 1f, true, Float.parseFloat(Config.general_data_setting.get(Config.KEY_TERMODISK_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener3.up_down, getContext());
                termodisk_v2.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(Config.KEY_TERMODISK_V2, Math.round(repeatListener3.result) + "");
            }
        }, getContext());
        repeatListener4 = new RepeatListener(40, 40, 15f, 5f, (repeatListener3.Count_num), false, Float.parseFloat(Config.general_data_setting.get(Config.KEY_TERMODISK_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener3.check(repeatListener3.Max_num, repeatListener3.Min_num, repeatListener3.Count_num, repeatListener4.up_down, getContext());
                termodisk_v2.setText(Math.round(repeatListener3.result) + "");
                Config.general_data_setting.put(Config.KEY_TERMODISK_V2, Math.round(repeatListener3.result) + "");
            }
        }, getContext());
    }


}
