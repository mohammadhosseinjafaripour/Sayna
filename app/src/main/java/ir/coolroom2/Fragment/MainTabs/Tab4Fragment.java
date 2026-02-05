package ir.coolroom2.Fragment.MainTabs;

/**
 * Created by JFP on 11/29/2017.
 */

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import ir.coolroom2.Config;
import ir.coolroom2.CustomViewPager;
import ir.coolroom2.Fragment.Registry.AddRoomFragment;
import ir.coolroom2.Fragment.Registry.AddUserFragment;
import ir.coolroom2.R;


public class Tab4Fragment extends Fragment {

    private CustomViewPager viewPager;
    ViewPagerAdapter adapter;
    Typeface typeface;
    AddUserFragment addUserFragment;
    AddRoomFragment addRoomFragment;
    SmartTabLayout smartTabLayout;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab4_view, container, false);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/iransans.ttf");

        addUserFragment = new AddUserFragment();
        addRoomFragment = new AddRoomFragment();
        adapter = null;
        viewPager = null;
        smartTabLayout = null;
        smartTabLayout = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPager = (CustomViewPager) view.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.notifyDataSetChanged();
        setupViewPager(viewPager);
        smartTabLayout.setViewPager(viewPager);
        final LinearLayout lyTabs = (LinearLayout) smartTabLayout.getChildAt(0);
        changeTabsTitleTypeFace(lyTabs, 0);
        changeTabsTitleTypeFace(lyTabs, 1);

        if (!Config.permision_level.equals("one")) {
            smartTabLayout.getTabAt(0).setClickable(false);
            viewPager.setPagingEnabled(false);
        }

        smartTabLayout.getTabAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!Config.permision_level.equals("one")) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
                    top.setBackgroundColor(getResources().getColor(R.color.warningColor));
                    title.setText("اخطار");
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    description.setText("شما اجازه دسترسی به این بخش را ندارید !");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    posetive_btn_text.setText("تایید");
                    cancel.setVisibility(View.GONE);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();
                }

                return false;
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/iransans.ttf");

        addUserFragment = new AddUserFragment();
        addRoomFragment = new AddRoomFragment();
        adapter = null;
        viewPager = null;
        smartTabLayout = null;
        smartTabLayout = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPager = (CustomViewPager) view.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.notifyDataSetChanged();
        setupViewPager(viewPager);
        smartTabLayout.setViewPager(viewPager);
        final LinearLayout lyTabs = (LinearLayout) smartTabLayout.getChildAt(0);
        changeTabsTitleTypeFace(lyTabs, 0);
        changeTabsTitleTypeFace(lyTabs, 1);

        if (!Config.permision_level.equals("one")) {
            smartTabLayout.getTabAt(0).setClickable(false);
            viewPager.setPagingEnabled(false);
        }

        smartTabLayout.getTabAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!Config.permision_level.equals("one")) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
                    top.setBackgroundColor(getResources().getColor(R.color.warningColor));
                    title.setText("اخطار");
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    description.setText("شما اجازه دسترسی به این بخش را ندارید !");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    posetive_btn_text.setText("تایید");
                    cancel.setVisibility(View.GONE);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();
                }

                return false;
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(addUserFragment, "کاربر");
        adapter.addFragment(addRoomFragment, "سردخانه");
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(1, true);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
