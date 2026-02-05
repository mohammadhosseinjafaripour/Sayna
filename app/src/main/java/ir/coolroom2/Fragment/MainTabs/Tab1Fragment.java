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

import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Fragment.Information.FTest1;
import ir.coolroom2.Fragment.Information.FTest2;
import ir.coolroom2.Fragment.Information.Tab_Current;
import ir.coolroom2.Fragment.Information.Tab_Difrast;
import ir.coolroom2.Fragment.Information.Tab_Pressure;
import ir.coolroom2.Fragment.Information.Tab_Signal;
import ir.coolroom2.Fragment.Information.Tab_Temprture;
import ir.coolroom2.Fragment.Information.Tab_Voltage;
import ir.coolroom2.Fragment.Information.Tab_Wet;
import ir.coolroom2.R;

public class Tab1Fragment extends Fragment {


    private static int inst = 0;

    private TabLayout tabLayout;
    private ViewPager viewPager_top, viewPager_buttom;

    DatabaseHandler db;

    Typeface typeface ;

    public Tab1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.tab1_view, container, false);
        db = new DatabaseHandler(getActivity());


        typeface = Typeface.createFromAsset(getActivity().getAssets(),"font/iransans.ttf");

        viewPager_top = (ViewPager) V.findViewById(R.id.viewpager_top);
        viewPager_buttom = (ViewPager) V.findViewById(R.id.viewpager_buttom);
        setupViewPagerTop(viewPager_top);
        setupViewPagerButtom(viewPager_buttom);

        SmartTabLayout smartTabLayout_top = (SmartTabLayout) V.findViewById(R.id.viewpagertab_top);
        SmartTabLayout smartTabLayout_buttom = (SmartTabLayout) V.findViewById(R.id.viewpagertab_buttom);
        smartTabLayout_top.setViewPager(viewPager_top);
        smartTabLayout_buttom.setViewPager(viewPager_buttom);

        final LinearLayout lyTabs_top = (LinearLayout) smartTabLayout_top.getChildAt(0);
        final LinearLayout lyTabs_buttom = (LinearLayout) smartTabLayout_buttom.getChildAt(0);

        for (int i = 0; i < 7; i++) {
            changeTabsTitleTypeFace(lyTabs_top, i);
            changeTabsTitleTypeFace(lyTabs_buttom, i);
        }

        smartTabLayout_top.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void setupViewPagerTop(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Tab_Pressure(), "فشار");
        adapter.addFragment(new Tab_Current(), "جریان");
        adapter.addFragment(new Tab_Voltage(), "ولتاژ");
        adapter.addFragment(new Tab_Difrast(), "دیفراست");
        adapter.addFragment(new Tab_Signal(), "سیگنال");
        adapter.addFragment(new Tab_Wet(), "رطوبت");
        adapter.addFragment(new Tab_Temprture(), "دما");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(7);
    }

    private void setupViewPagerButtom(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FTest1(), "تحلیل و آنالیز متنی");
        adapter.addFragment(new FTest2(), "تحلیل و آنالیز تصویری");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
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

    public static int instance() {
        return inst;
    }

    public void changeTabsTitleTypeFace(LinearLayout ly, int position) {
        for (int j = 0; j < ly.getChildCount(); j++) {
            TextView tvTabTitle = (TextView) ly.getChildAt(j);
            tvTabTitle.setTypeface(typeface, Typeface.NORMAL);
            if (j == position) tvTabTitle.setTypeface(typeface, Typeface.NORMAL);
        }
    }

}
