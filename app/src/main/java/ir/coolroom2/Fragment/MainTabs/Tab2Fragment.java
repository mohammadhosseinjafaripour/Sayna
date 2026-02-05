package ir.coolroom2.Fragment.MainTabs;

/**
 * Created by JFP on 11/29/2017.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import ir.coolroom2.Fragment.Setting.Setting_Tab_Current_V2;
import ir.coolroom2.Fragment.Setting.Setting_Tab_Difrast;
import ir.coolroom2.Fragment.Setting.Setting_Tab_Humour;
import ir.coolroom2.Fragment.Setting.Setting_Tab_Pressure;
import ir.coolroom2.Fragment.Setting.Setting_Tab_Temprature;
import ir.coolroom2.Fragment.Setting.Setting_Tab_Voltage;
import ir.coolroom2.R;


public class Tab2Fragment extends Fragment {

    private ViewPager viewPager;
    public Typeface typeface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab2_view, container, false);

        typeface = Typeface.createFromAsset(getActivity().getAssets(),"font/iransans.ttf");

        viewPager = (ViewPager) V.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        SmartTabLayout smartTabLayout = (SmartTabLayout) V.findViewById(R.id.viewpagertab);
        smartTabLayout.setViewPager(viewPager);

        final LinearLayout lyTabs_top = (LinearLayout) smartTabLayout.getChildAt(0);

        for (int i = 0; i < 6; i++) {
            changeTabsTitleTypeFace(lyTabs_top, i);
        }

        smartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return V;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Setting_Tab_Pressure(), "فشار");
        adapter.addFragment(new Setting_Tab_Current_V2(), "جریان");
        adapter.addFragment(new Setting_Tab_Voltage(), "ولتاژ");
        adapter.addFragment(new Setting_Tab_Difrast(), "دیفراست");
        adapter.addFragment(new Setting_Tab_Humour(), "رطوبت");
        adapter.addFragment(new Setting_Tab_Temprature(), "دما");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(8);
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

}
