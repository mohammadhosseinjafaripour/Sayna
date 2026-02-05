package ir.coolroom2.Fragment.Information;

/**
 * Created by JFP on 11/29/2017.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;

import ir.coolroom2.ArcRound;
import ir.coolroom2.Config;
import ir.coolroom2.R;
import ir.coolroom2.RepeatListener2;
import ir.coolroom2.Sms.SmsCodeAndDecode;

public class Tab_Difrast extends Fragment {


    ToggleButton one;
    ToggleButton two;
    ToggleButton three;
    ToggleButton four;
    ToggleButton five;
    ToggleButton six;
    Button up;
    Button down;
    public ArcRound defrost;

    int[] minuts = new int[144];
    String[] names = new String[144];
    int saat = 1;


    private View view;

    private TextView textClock;
    private Handler mHandler;
    TextView saat_tv;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    Typeface typeface;
    private TextView next_defrost_time;
    private TextView next_defrost_time_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.tab_difrast, container, false);

        find_items();

        switch (Config.INFORMATION_WORKMODE) {
            case "0":
                next_defrost_time_title.setText("سردخانه خاموش می باشد");
                break;
            case "1":
                next_defrost_time_title.setText("زمان باقیمانده تا دیفراست بعدی");
                break;
            case "2":
                next_defrost_time_title.setText("زمان باقیمانده تا دیفراست بعدی");
                break;
            case "3":
                next_defrost_time_title.setText("زمان باقیمانده تا دیفراست بعدی");
                break;
            case "4":
                next_defrost_time_title.setText("زمان باقیمانده تا اتمام دیفراست فعلی");
                break;
            case "5":
                next_defrost_time_title.setText("زمان باقیمانده تا اتمام تایمر");
                break;
        }
        if (Config.INFORMATION_DEFROSTTIME.length() > 0)
            next_defrost_time.setText(Config.INFORMATION_DEFROSTTIME.substring(0, 2) + ":" + Config.INFORMATION_DEFROSTTIME.substring(2, 4));
//todo check


        Calendar c = Calendar.getInstance();
        int min = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        textClock.setText(String.valueOf(hour) + ":" + String.valueOf(min));
        mHandler = new Handler();
        timer();

        String binaries = Config.INFORMATION_DEFROST;
        for (int i = 0; i < 144; i++) { //todo test initializing
            minuts[i] = Integer.parseInt(String.valueOf(binaries.charAt(i)));

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

        up.setOnTouchListener(new RepeatListener2(1000, 100, new View.OnClickListener() {
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
        down.setOnTouchListener(new RepeatListener2(1000, 100, new View.OnClickListener() {
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

        up.playSoundEffect(SoundEffectConstants.CLICK);
        down.playSoundEffect(SoundEffectConstants.CLICK);


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
                /*names[(saat - 1) * 6 + 3] = isChecked ? "COM" : "DEF";
                four_tv.setText(names[(saat - 1) * 6 + 3]);
*/
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
                                int min = (c.get(Calendar.MINUTE));
                                int hour = c.get(Calendar.HOUR_OF_DAY);
                                textClock.setText(hour + ":" + min);
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

        textClock = (TextView) view.findViewById(R.id.textClock);
        next_defrost_time = (TextView) view.findViewById(R.id.next_defrost_time);
        next_defrost_time_title = (TextView) view.findViewById(R.id.next_defrost_time_title);

        saat_tv = (TextView) view.findViewById(R.id.saat);

        up = (Button) view.findViewById(R.id.up);
        down = (Button) view.findViewById(R.id.down);
        defrost = (ArcRound) view.findViewById(R.id.defrost);
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

}
