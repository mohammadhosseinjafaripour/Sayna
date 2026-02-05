package ir.coolroom2.Fragment.Setting;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import ir.coolroom2.Config;
import ir.coolroom2.R;
import ir.coolroom2.RepeatListener;

public class Setting_Tab_Pressure extends Fragment {
    private ViewPager viewPager;
    private Typeface typeface;
    private SmartTabLayout smartTabLayout;
    private RadioGroup pressure_v1;
    private LinearLayout pomp_down_linear, linear_right;
    private Button pressure_v2_up, pressure_v2_down;
    private TextView pomp_down_title, pressure_v2;
    private RepeatListener repeatListener2_1, repeatListener2_2;
    RadioButton normal, pumpdown;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting_tab_pressure, container, false);

        findview(view);


        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/iransans.ttf");
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        smartTabLayout = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        smartTabLayout.setViewPager(viewPager);
        smartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Config.setting_tab_pressure_index = position;
            }

            @Override
            public void onPageSelected(int position) {
                Config.setting_tab_pressure_index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Config.general_data_setting.get(Config.KEY_PRESSURE_V1).equals("0")) {
                    pressure_v1.clearCheck();
                    normal.setChecked(true);
                    Config.setting_tab_pressure_checked_radio_btn_id = R.id.pressure_v1_normal;
                    pomp_down_linear.setClickable(false);
                    pomp_down_linear.setBackground(getResources().getDrawable(R.drawable.infobox_disable));
                    linear_right.setBackground(getResources().getDrawable(R.drawable.forground_gray));
                    pressure_v2_up.setOnTouchListener(null);//up
                    pressure_v2_down.setOnTouchListener(null);//down
                    pressure_v2_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_gray_24dp));
                    pressure_v2_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_gray_24dp));
                    pressure_v2.setTextColor(Color.parseColor("#ababab"));
                } else {
                    pressure_v1.clearCheck();
                    pumpdown.setChecked(true);
                    Config.setting_tab_pressure_checked_radio_btn_id = R.id.pressure_v1_pomp_down;
                    pomp_down_linear.setClickable(true);
                    pomp_down_linear.setBackground(getResources().getDrawable(R.drawable.info_box));
                    linear_right.setBackground(getResources().getDrawable(R.drawable.info_box_header_right));
                    pressure_v2_up.setOnTouchListener(repeatListener2_1);//up
                    pressure_v2_down.setOnTouchListener(repeatListener2_2);//down
                    pressure_v2_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                    pressure_v2_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                    pomp_down_title.setTextColor(getResources().getColor(android.R.color.white));
                    pressure_v2.setTextColor(getResources().getColor(android.R.color.black));
                    Config.Pressure_radio_btn_low_gas_automatic.setChecked(true);
                    Config.Pressure_radio_btn_low_gas_manual.setChecked(false);
                }
            }
        }, 1000);


        pressure_v2_up.setOnTouchListener(null);
        pressure_v2_down.setOnTouchListener(null);


        pressure_v2.setText(Integer.parseInt(Config.general_data_setting.get(Config.KEY_PRESSURE_V2)) + "");
        pressure_v1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pressure_v1_normal:
                        Config.general_data_setting.put(Config.KEY_PRESSURE_V1, "0");
                        Config.setting_tab_pressure_checked_radio_btn_id = R.id.pressure_v1_normal;
                        pomp_down_linear.setClickable(false);
                        pomp_down_linear.setBackground(getResources().getDrawable(R.drawable.infobox_disable));
                        linear_right.setBackground(getResources().getDrawable(R.drawable.forground_gray));
                        pressure_v2_up.setOnTouchListener(null);//up
                        pressure_v2_down.setOnTouchListener(null);//down
                        pressure_v2_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_gray_24dp));
                        pressure_v2_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_gray_24dp));
                        pressure_v2.setTextColor(Color.parseColor("#ababab"));
                        break;
                    case R.id.pressure_v1_pomp_down:
                        Config.general_data_setting.put(Config.KEY_PRESSURE_V1, "1");
                        Config.setting_tab_pressure_checked_radio_btn_id = R.id.pressure_v1_pomp_down;
                        pomp_down_linear.setClickable(true);
                        pomp_down_linear.setBackground(getResources().getDrawable(R.drawable.info_box));
                        linear_right.setBackground(getResources().getDrawable(R.drawable.info_box_header_right));
                        pressure_v2_up.setOnTouchListener(repeatListener2_1);//up
                        pressure_v2_down.setOnTouchListener(repeatListener2_2);//down
                        pressure_v2_up.setBackground(getResources().getDrawable(R.drawable.ic_expand_less_black_24dp));
                        pressure_v2_down.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                        pomp_down_title.setTextColor(getResources().getColor(android.R.color.white));
                        pressure_v2.setTextColor(getResources().getColor(android.R.color.black));
                        Config.Pressure_radio_btn_low_gas_automatic.setChecked(true);
                        Config.Pressure_radio_btn_low_gas_manual.setChecked(false);
                        break;
                    default:
                        Config.setting_tab_pressure_checked_radio_btn_id = R.id.pressure_v1_pomp_down;
                }
            }
        });


        repeatListener2_1 = new RepeatListener(400, 400, 20f, 0f, 1f, true,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener2_1.check(repeatListener2_1.Max_num, repeatListener2_1.Min_num, repeatListener2_1.Count_num, repeatListener2_1.up_down, getContext());
                pressure_v2.setText(Math.round(repeatListener2_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_V2, Math.round(repeatListener2_1.result) + "");
            }
        }, getContext());
        repeatListener2_2 = new RepeatListener(100, 50, 20f, 0f, (repeatListener2_1.Count_num), false,
                Float.parseFloat(Config.general_data_setting.get(Config.KEY_PRESSURE_V2)), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatListener2_1.check(repeatListener2_1.Max_num, repeatListener2_1.Min_num, repeatListener2_1.Count_num, repeatListener2_2.up_down, getContext());
                pressure_v2.setText(Math.round(repeatListener2_1.result) + "");
                Config.general_data_setting.put(Config.KEY_PRESSURE_V2, Math.round(repeatListener2_1.result) + "");
            }
        }, getContext());

        configTypeface();

        Config.Pressure_view = view;


        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        Setting_Tab_Pressure.ViewPagerAdapter adapter = new Setting_Tab_Pressure.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Setting_Tab_Pressure_Oil(), "فشار روغن");
        adapter.addFragment(new Setting_Tab_Pressure_Low_Gas(), "فشار گاز پایین");
        adapter.addFragment(new Setting_Tab_Pressure_High_Gas(), "فشار گاز بالا");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void changeTabsTitleTypeFace(LinearLayout ly, int position) {
        for (int j = 0; j < ly.getChildCount(); j++) {
            TextView tvTabTitle = (TextView) ly.getChildAt(j);
            tvTabTitle.setTypeface(typeface, Typeface.NORMAL);
            if (j == position) tvTabTitle.setTypeface(typeface, Typeface.NORMAL);
        }
    }

    private void configTypeface() {
        final LinearLayout lyTabs_top = (LinearLayout) smartTabLayout.getChildAt(0);
        for (int i = 0; i < 3; i++) {
            changeTabsTitleTypeFace(lyTabs_top, i);
        }
    }

    public void findview(View view) {
        pressure_v2_up = (Button) view.findViewById(R.id.pressure_v2_up);
        pressure_v2_down = (Button) view.findViewById(R.id.pressure_v2_down);
        normal = view.findViewById(R.id.pressure_v1_normal);
        pumpdown = view.findViewById(R.id.pressure_v1_pomp_down);
        pressure_v1 = (RadioGroup) view.findViewById(R.id.pressure_v1);
        pomp_down_title = (TextView) view.findViewById(R.id.pomp_down_title);
        pressure_v2 = (TextView) view.findViewById(R.id.pressure_v2);
        pomp_down_linear = (LinearLayout) view.findViewById(R.id.pomp_down_linear);
        linear_right = (LinearLayout) view.findViewById(R.id.linear_right);
    }

}
