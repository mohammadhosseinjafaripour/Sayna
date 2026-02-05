package ir.coolroom2.Fragment.Setting;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ir.coolroom2.R;

/**
 * Created by JFP on 12/7/2017.
 */

public class Setting_Tab_Current_V2 extends Fragment {

    private ViewPager viewPager;
    private Typeface typeface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_tab_current_v2, container, false);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/iransans.ttf");


        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        SmartTabLayout smartTabLayout = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        smartTabLayout.setViewPager(viewPager);


        final LinearLayout lyTabs_top = (android.widget.LinearLayout) smartTabLayout.getChildAt(0);
        for(int i = 0; i < 2; i++) {

            changeTabsTitleTypeFace(lyTabs_top, i);
        }

        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Setting_Tab_Current_Compressor(), "کمپرسور");
        adapter.addFragment(new Setting_Tab_Current_Operator(), "اواپراتور");
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

    public void changeTabsTitleTypeFace(LinearLayout ly, int position) {
        for (int j = 0; j < ly.getChildCount(); j++) {
            TextView tvTabTitle = (TextView) ly.getChildAt(j);
            tvTabTitle.setTypeface(typeface, Typeface.NORMAL);
            if (j == position) tvTabTitle.setTypeface(typeface, Typeface.NORMAL);
        }
    }
}
