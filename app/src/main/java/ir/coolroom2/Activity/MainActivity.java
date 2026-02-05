package ir.coolroom2.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ir.coolroom2.Adapter.SearchRoomAdapter;
import ir.coolroom2.Adapter.SelectRoomsAdapter;
import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Fragment.MainTabs.Tab1Fragment;
import ir.coolroom2.Fragment.MainTabs.Tab2Fragment;
import ir.coolroom2.Fragment.MainTabs.Tab3Fragment;
import ir.coolroom2.Fragment.MainTabs.Tab4Fragment;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.PrepareDataAndSendSms;
import ir.coolroom2.Sms.SmsCodeAndDecode;
import ir.coolroom2.Utils.BackgroundChecker;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private DatabaseHandler db;
    public static MainActivity instance;

    private List<RoomModel> roomsList;
    private RecyclerView recyclerView;
    private SelectRoomsAdapter mAdapter;

    private Toolbar toolbar;
    private ListView listView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] titles;
    private int[] images;
    private Typeface typeface;


    private TextView tv0, tv1, tv2, tv3;

    private String permission_level;
    private LinearLayout coolroomView;
    private BottomSheetDialog bottomSheetDialog;

    /*filter boolean*/
    private Boolean _province = false, _number = false, _name = false, _description = false;
    private LinearLayout roomName_linear_dg;
    private LinearLayout roomNumber_linear_dg;
    private LinearLayout roomLocation_linear_dg;
    private LinearLayout roomProductType_linear_dg;
    private LinearLayout roomDescription_linear_dg;
    private LinearLayout dimension_linear_dg;
    private LinearLayout power_linear_dg;
    private LinearLayout roomUnderZero_linear_dg;
    TextView timers;

    private RadioButton roomUnderZero_rg_under_zero;
    private RadioButton roomUnderZero_rg_over_zero;
    private RadioGroup roomUnderZero_rg;
    private List<UserModel> usersList = new ArrayList<>();
    private static final int DEF_SMS_REQ = 0;

    Button updateBtn;


    @Override
    public void onStart() {
        super.onStart();
        instance = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SmsCodeAndDecode smsCodeAndDecode = new SmsCodeAndDecode();
        String compressed = "EyC$9!$ao$%.$7#.,66 B'' 5B'' ffffffffffffffffffffffff,.$aF c&&# + c&+&( + c&+& TI! c&?\" ++&' T& Sc&+/";
        String temp_data = smsCodeAndDecode.decompress(compressed.substring(4, compressed.length() - 1));

        String checking = smsCodeAndDecode.decompress(compressed);

        Config.myActivity = MainActivity.this;

        startService(new Intent(MainActivity.this, BackgroundChecker.class));

        typeface = Typeface.createFromAsset(getAssets(), "font/iransans.ttf");
        db = new DatabaseHandler(MainActivity.this);
        loadErrorMap();

        timers = (TextView) findViewById(R.id.timer);
        TextView date = (TextView) findViewById(R.id.date);
        String TimeAndDate = db.getLastInformation(Config.currentRoomID);
        //tmda = time and date
        String[] tmda = TimeAndDate.split("-");
        timers.setText(tmda[0]);
        date.setText(tmda[1]);

        instance = this;
        Config.permision_level = db.getFirstUser_for_room(Config.currentRoomID).get_permision_level();
        ((TextView) findViewById(R.id.userName)).setText(db.getRoom(Config.currentRoomID).get_name());

        final LinearLayout btn_data = (LinearLayout) findViewById(R.id.btn_data);
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.permision_level.equals("three")) {
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setText("توجه");
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    description.setText("شما اجازه ارسال تنظیمات را ندارید !");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    posetive_btn_text.setText("تایید");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                        }
                    });

                    cancel.setVisibility(View.GONE);
                    dialog.setCancelable(false);
                    dialog.show();
                } else {

                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setText("توجه");
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    description.setText("از ارسال خود اطمینان کامل دارید ؟");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
                    posetive_btn_text.setText("بله");
                    negative_btn_text.setText("خیر");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PrepareDataAndSendSms prepareDataAndSendSms = new PrepareDataAndSendSms(MainActivity.this);
                            prepareDataAndSendSms.send_total_data();
                            dialog.dismiss();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });

        coolroomView = (LinearLayout) findViewById(R.id.coolroomView);
        coolroomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.getlsat_room_id() != 0) {

                    RoomModel roomtmp = db.getRoom(Config.currentRoomID);
                    final Dialog dialog_editRoom = new Dialog(MainActivity.this);
                    dialog_editRoom.setContentView(R.layout.dialog_room_detail_show);

                    Linear_for_dialog(dialog_editRoom);

                    ((Button) dialog_editRoom.findViewById(R.id.cancel)).setVisibility(View.GONE);
                    Button cancel = (Button) dialog_editRoom.findViewById(R.id.edit);
                    cancel.setText("تایید");

                    final EditText roomName = (EditText) dialog_editRoom.findViewById(R.id.roomName);
                    final EditText roomNumber = (EditText) dialog_editRoom.findViewById(R.id.roomNumber);
                    final EditText roomLocation = (EditText) dialog_editRoom.findViewById(R.id.roomLocation);
                    final EditText roomWidth = (EditText) dialog_editRoom.findViewById(R.id.Width);
                    final EditText roomHeight = (EditText) dialog_editRoom.findViewById(R.id.Height);
                    final EditText roomLength = (EditText) dialog_editRoom.findViewById(R.id.length);
                    final EditText roomThickness = (EditText) dialog_editRoom.findViewById(R.id.Thickness);
                    final EditText roomCompressor = (EditText) dialog_editRoom.findViewById(R.id.compressor);
                    final EditText roomCondonsor = (EditText) dialog_editRoom.findViewById(R.id.condonsor);
                    final EditText roomOvaperator = (EditText) dialog_editRoom.findViewById(R.id.ovaperator);
                    final EditText roomProductType = (EditText) dialog_editRoom.findViewById(R.id.roomProductType);
                    final EditText roomDescription = (EditText) dialog_editRoom.findViewById(R.id.roomDescription);
                    roomUnderZero_rg_under_zero = (RadioButton) dialog_editRoom.findViewById(R.id.roomUnderZero_rg_under_zero);
                    roomUnderZero_rg_over_zero = (RadioButton) dialog_editRoom.findViewById(R.id.roomUnderZero_rg_over_zero);
                    roomUnderZero_rg = (RadioGroup) dialog_editRoom.findViewById(R.id.roomUnderZero_rg);

                    switch (Config.permision_level) {
                        case "one":
                            valid_first_dialog();
                            break;
                        case "two":
                            valid_second_dialog();
                            break;
                        case "three":
                            valid_third_dialog();
                            break;
                    }

                    roomName.setText(roomtmp.get_name());
                    roomNumber.setText(roomtmp.get_number());
                    roomWidth.setText(roomtmp.get_width());
                    roomHeight.setText(roomtmp.get_height());
                    roomLength.setText(roomtmp.get_length());
                    roomThickness.setText(roomtmp.get_thickness());
                    roomCompressor.setText(roomtmp.get_compressor());
                    roomOvaperator.setText(roomtmp.get_ovaperator());
                    roomCondonsor.setText(roomtmp.get_condonsor());
                    if (roomtmp.is_under_zero()) {
                        roomUnderZero_rg_under_zero.setChecked(true);
                        roomUnderZero_rg_over_zero.setChecked(false);
                    } else {
                        roomUnderZero_rg_under_zero.setChecked(false);
                        roomUnderZero_rg_over_zero.setChecked(true);
                    }

                    roomDescription.setText(roomtmp.get_description());
                    roomLocation.setText(roomtmp.get_location());

                    if (roomtmp.get_product_type().length() > 0) {
                        String bin = String.format("%6s", Integer.toBinaryString(Integer.parseInt(roomtmp.get_product_type()))).replace(" ", "0");
                        ArrayList<String> products = new ArrayList<>();
                        String[] p_arr = Arrays.copyOfRange(getResources().getStringArray(R.array.product_type_list), 1, getResources().getStringArray(R.array.product_type_list).length);
                        for (int i = 0; i < bin.length(); i++) {
                            char c = bin.charAt(i);
                            if (c == '1') {
                                products.add(p_arr[i]);
                            }
                        }
                        String p_str = products.toString();
                        roomProductType.setText(p_str.substring(1, p_str.length() - 1));
                    }

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog_editRoom.dismiss();
                        }
                    });

                    dialog_editRoom.show();
                } else {
                    Toast.makeText(MainActivity.this, "سردخانه ای وجود ندارد!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        permission_level = db.getFirstUser_for_room(Config.currentRoomID).get_permision_level();
        final FragmentTabHost mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(MainActivity.this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("enrol").setIndicator("رجیستری"), new Tab4Fragment().getClass(), null);
        mTabHost.addTab(mTabHost.newTabSpec("report").setIndicator("گزارشات"), new Tab3Fragment().getClass(), null);
        mTabHost.addTab(mTabHost.newTabSpec("setting").setIndicator("تنظیمات"), new Tab2Fragment().getClass(), null);
        mTabHost.addTab(mTabHost.newTabSpec("information").setIndicator("اطلاعات"), new Tab1Fragment().getClass(), null);
        mTabHost.getTabWidget().setCurrentTab(3);
        mTabHost.setCurrentTab(3);
        for (int i = 0; i < 4; i++) {
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.white));
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (40 * this.getResources().getDisplayMetrics().density);
        }

        Config.mainTabHost = mTabHost;


        tv0 = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv1 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        tv2 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        tv3 = (TextView) mTabHost.getTabWidget().getChildAt(3).findViewById(android.R.id.title);


        tv3.setTextColor(getResources().getColor(R.color.firstTabSelectedColor));

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("setting")) {

                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setTextColor(Color.parseColor("#000000"));
                    LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
                    top.setBackgroundColor(getResources().getColor(R.color.warningColor));
                    title.setText("هشدار");
                    Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    title.setTypeface(typeface);
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    description.setText("با وارد شدن به این بخش شما تنظیمات اصلی را تغییر خواهید داد." + "\n" + "آیا مطمئن هستید ؟");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
                    posetive_btn_text.setText("بله");
                    negative_btn_text.setText("خیر");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            mTabHost.setCurrentTab(3);
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();

                }
                if (tabId.equals("enrol")) {
                    btn_data.setVisibility(View.GONE);
                    tv0.setTextColor(getResources().getColor(R.color.firstTabSelectedColor));
                    tv1.setTextColor(getResources().getColor(R.color.white));
                    tv2.setTextColor(getResources().getColor(R.color.white));
                    tv3.setTextColor(getResources().getColor(R.color.white));
                } else if (tabId.equals("report")) {
                    btn_data.setVisibility(View.GONE);
                    tv0.setTextColor(getResources().getColor(R.color.white));
                    tv1.setTextColor(getResources().getColor(R.color.firstTabSelectedColor));
                    tv2.setTextColor(getResources().getColor(R.color.white));
                    tv3.setTextColor(getResources().getColor(R.color.white));
                } else if (tabId.equals("setting")) {
                    btn_data.setVisibility(View.VISIBLE);
                    tv0.setTextColor(getResources().getColor(R.color.white));
                    tv1.setTextColor(getResources().getColor(R.color.white));
                    tv2.setTextColor(getResources().getColor(R.color.firstTabSelectedColor));
                    tv3.setTextColor(getResources().getColor(R.color.white));
                } else if (tabId.equals("information")) {
                    btn_data.setVisibility(View.GONE);
                    tv0.setTextColor(getResources().getColor(R.color.white));
                    tv1.setTextColor(getResources().getColor(R.color.white));
                    tv2.setTextColor(getResources().getColor(R.color.white));
                    tv3.setTextColor(getResources().getColor(R.color.firstTabSelectedColor));
                }


            }
        });



        /*  amgh  */
        Button select_room = (Button) findViewById(R.id.select_room);
        select_room.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                roomsList = db.getRooms();

                final Dialog dialog_selectRoom = new Dialog(MainActivity.this);
                dialog_selectRoom.setContentView(R.layout.dialog_select_room);
                final FloatingSearchView searchView = (FloatingSearchView) dialog_selectRoom.findViewById(R.id.floating_search_view);

                recyclerView = (RecyclerView) dialog_selectRoom.findViewById(R.id.selectRoomsRecyclerView);

                mAdapter = new SelectRoomsAdapter(roomsList, MainActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));*/

                final RelativeLayout not_found = (RelativeLayout) dialog_selectRoom.findViewById(R.id.not_found);
                searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                    @Override
                    public void onSearchTextChanged(String oldQuery, String newQuery) {
                        not_found.setVisibility(View.GONE);
                        roomsList = db.getSearched_rooms(_province, _name, _number, _description, newQuery);
                        SearchRoomAdapter madapter = new SearchRoomAdapter(roomsList, MainActivity.this);
                        recyclerView.setAdapter(madapter);

                        if (roomsList.isEmpty()) {
                            not_found.setVisibility(View.VISIBLE);
                        }
                    }
                });

                bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setContentView(R.layout.dialog_room_filter_bottom_sheet);

                final CheckBox number = (CheckBox) bottomSheetDialog.findViewById(R.id.number);
                final CheckBox province = (CheckBox) bottomSheetDialog.findViewById(R.id.province);
                final CheckBox name = (CheckBox) bottomSheetDialog.findViewById(R.id.name);
                final CheckBox description = (CheckBox) bottomSheetDialog.findViewById(R.id.description);

                LinearLayout ok = (LinearLayout) bottomSheetDialog.findViewById(R.id.ok);
                LinearLayout cancel = (LinearLayout) bottomSheetDialog.findViewById(R.id.cancelBtn);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _number = number.isChecked();
                        _name = name.isChecked();
                        _province = province.isChecked();
                        _description = description.isChecked();

                        bottomSheetDialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });


                searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(MenuItem item) {
                        bottomSheetDialog.show();
                    }
                });

                dialog_selectRoom.show();
            }
        });


        updateBtn = (Button) findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PrepareDataAndSendSms prepareDataAndSendSms = new PrepareDataAndSendSms(MainActivity.this);
                final RoomModel roomModel = db.getRoom(Config.currentRoomID);

                final String content = prepareDataAndSendSms.get_update();

                checkRunTimePermission();

                String DELIVERED = "SMS_DELIVERED";
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context arg0, Intent arg1) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK: {
                                /*final Dialog dialog = new Dialog(MainActivity.this);
                                dialog.setContentView(R.layout.dialog_custom_alert);
                                TextView title = (TextView) dialog.findViewById(R.id.title);
                                title.setTextColor(Color.parseColor("#000000"));
                                LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
                                top.setBackgroundColor(getResources().getColor(R.color.warningColor));
                                title.setText("هشدار");
                                Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);
                                title.setTypeface(typeface);
                                TextView description = (TextView) dialog.findViewById(R.id.description);
                                description.setText("پیام شما با موفقیت روی سرور ثبت شد");
                                LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                                LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                                TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                                TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
                                posetive_btn_text.setText("تایید");
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                cancel.setVisibility(View.GONE);
                                dialog.show();*/
                            }
                            break;
                            case Activity.RESULT_CANCELED:
                                /*final Dialog dialog = new Dialog(MainActivity.this);
                                dialog.setContentView(R.layout.dialog_custom_alert);
                                TextView title = (TextView) dialog.findViewById(R.id.title);
                                title.setTextColor(Color.parseColor("#000000"));
                                LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
                                top.setBackgroundColor(getResources().getColor(R.color.warningColor));
                                title.setText("هشدار");
                                Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);
                                title.setTypeface(typeface);
                                TextView description = (TextView) dialog.findViewById(R.id.description);
                                description.setText("ارسال پیام با موفقیت انجام نشد");
                                LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                                LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                                TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                                TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
                                posetive_btn_text.setText("ارسال مجدد");
                                negative_btn_text.setText("انصراف");

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.hide();
                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage(roomModel.get_number(), null, content, null, null);
                                    }
                                });

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });

                                dialog.setCancelable(false);
                                dialog.show();*/

                                break;
                        }
                    }
                }, new IntentFilter(DELIVERED));


            }
        });


        loadNavigationMenu();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_custom_alert);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setTextColor(Color.parseColor("#000000"));
        LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
        top.setBackgroundColor(getResources().getColor(R.color.warningColor));
        title.setText("هشدار");
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        title.setTypeface(boldTypeface);
        TextView description = (TextView) dialog.findViewById(R.id.description);
        description.setText("آیا می خواهید خارج شوید ؟");
        LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
        LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
        TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
        TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
        posetive_btn_text.setText("بله");
        negative_btn_text.setText("خیر");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void loadNavigationMenu() {
        /* drawer menu */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);


        TextView tv_site = toolbar.findViewById(R.id.toolbar_title);
        tv_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.atis-cool.ir"));
                startActivity(browserIntent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.nav_header_main, listView, false);
        listView.addHeaderView(header, null, false);

        TextView user_name = (TextView) header.findViewById(R.id.user_name);
        TextView room_name = (TextView) header.findViewById(R.id.room_name);
        ImageView imageView_logo = (ImageView) header.findViewById(R.id.imageView);
        imageView_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.atis-cool.ir"));
                startActivity(browserIntent);
            }
        });

        usersList = db.getUsers();
        UserModel user = usersList.get(0);
        Config.currentUserName = user.get_name() + " " + user.get_family();


        user_name.setText(Config.currentUserName);
        room_name.setText(db.getRoom(Config.currentRoomID).get_name());

        titles = getResources().getStringArray(R.array.list_items);

        images = new int[]{
                R.drawable.ic_settings,
                R.drawable.ic_info_outline_black_24dp,
                R.drawable.ic_power_settings_new_black_24dp
        };

        MyAdapter myAdapter = new MyAdapter(getApplicationContext());
        listView.setAdapter(myAdapter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ImageView menubtn = (ImageView) toolbar.findViewById(R.id.menubtn);

        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        listView.setOnItemClickListener(new DrawerItemClickListener());
    }

    public static MainActivity instance_ativity() {
        return instance;
    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflaer;

        public MyAdapter(Context context) {
            mInflaer = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                view = mInflaer.inflate(R.layout.list_items, null);
            } else {
                view = convertView;
            }
            TextView textView = (TextView) view.findViewById(R.id.list_text);
            ImageView imageView = (ImageView) view.findViewById(R.id.list_image);
            textView.setText(titles[position]);
            textView.setTypeface(typeface);

            imageView.setImageResource(images[position]);
            return view;
        }


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            selectitem(position);
        }

        private void selectitem(int position) {

            switch (position) {
                case 1:
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    mDrawerLayout.closeDrawers();
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    mDrawerLayout.closeDrawers();
                    break;
                case 3:
                    mDrawerLayout.closeDrawers();
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setTextColor(Color.parseColor("#000000"));
                    LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
                    top.setBackgroundColor(getResources().getColor(R.color.warningColor));
                    title.setText("هشدار");
                    Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    title.setTypeface(boldTypeface);
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    description.setText("آیا می خواهید خارج شوید ؟");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
                    posetive_btn_text.setText("بله");
                    negative_btn_text.setText("خیر");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                            finish();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    dialog.setCancelable(false);
                    dialog.show();
                    break;
            }
        }
    }

    public void update_UserDetails() {
        TextView userName = (TextView) findViewById(R.id.userName);
        coolroomView = (LinearLayout) findViewById(R.id.coolroomView);
        if (db.getlsat_room_id() == 0) {
            userName.setText("کاربری وجود ندارد !");
        } else {
            userName.setText(db.getRoom(Config.currentRoomID).get_name());
        }
    }

    /*
    for show room detail dialog
     */
    void Linear_for_dialog(Dialog view) {
        roomName_linear_dg = (LinearLayout) view.findViewById(R.id.roomName_linear);
        roomNumber_linear_dg = (LinearLayout) view.findViewById(R.id.roomNumber_linear);
        roomLocation_linear_dg = (LinearLayout) view.findViewById(R.id.roomLocation_linear);
        roomProductType_linear_dg = (LinearLayout) view.findViewById(R.id.roomProductType_linear);
        roomDescription_linear_dg = (LinearLayout) view.findViewById(R.id.roomDescription_linear);
        dimension_linear_dg = (LinearLayout) view.findViewById(R.id.dimension_linear);
        power_linear_dg = (LinearLayout) view.findViewById(R.id.power_linear);
        roomUnderZero_linear_dg = (LinearLayout) view.findViewById(R.id.roomUnderZero_linear);
        ((TextView) view.findViewById(R.id.title)).setText("اطلاعات سردخانه");
    }

    void valid_first_dialog() {
        roomName_linear_dg.setVisibility(View.VISIBLE);
        roomNumber_linear_dg.setVisibility(View.VISIBLE);
        roomLocation_linear_dg.setVisibility(View.VISIBLE);
        roomDescription_linear_dg.setVisibility(View.VISIBLE);
        roomProductType_linear_dg.setVisibility(View.VISIBLE);
        dimension_linear_dg.setVisibility(View.VISIBLE);
        power_linear_dg.setVisibility(View.VISIBLE);
        roomUnderZero_linear_dg.setVisibility(View.VISIBLE);
    }

    void valid_second_dialog() {
        roomName_linear_dg.setVisibility(View.VISIBLE);
        roomNumber_linear_dg.setVisibility(View.VISIBLE);
        roomDescription_linear_dg.setVisibility(View.VISIBLE);
        roomUnderZero_linear_dg.setVisibility(View.GONE);
        roomProductType_linear_dg.setVisibility(View.GONE);
        power_linear_dg.setVisibility(View.GONE);
        dimension_linear_dg.setVisibility(View.GONE);
        roomLocation_linear_dg.setVisibility(View.GONE);
    }

    void valid_third_dialog() {
        roomName_linear_dg.setVisibility(View.VISIBLE);
        roomNumber_linear_dg.setVisibility(View.VISIBLE);
        roomDescription_linear_dg.setVisibility(View.VISIBLE);

        roomLocation_linear_dg.setVisibility(View.GONE);
        roomProductType_linear_dg.setVisibility(View.GONE);
        dimension_linear_dg.setVisibility(View.GONE);
        power_linear_dg.setVisibility(View.GONE);
        roomUnderZero_linear_dg.setVisibility(View.GONE);
    }

    public void timer() {
        Timer timer = new Timer();
        final Handler handler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timers.setText(current_time());
                    }
                }, 1000);
            }
        }, 0);
    }

    public String current_time() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().getHours() + ":" + (("" + calendar.getTime().getMinutes()).length() != 2 ? "0" + calendar.getTime().getMinutes() : calendar.getTime().getMinutes()) + ":" + (("" + calendar.getTime().getSeconds()).length() != 2 ? "0" + calendar.getTime().getSeconds() : calendar.getTime().getSeconds());
    }

    public void loadErrorMap() {
        Config.ERROR_MAP.put("ERROR_Disconnect_PHASE_R", Config.ERROR_Disconnect_PHASE_R);
        Config.ERROR_MAP.put("ERROR_Disconnect_PHASE_S", Config.ERROR_Disconnect_PHASE_S);
        Config.ERROR_MAP.put("ERROR_PHASE_FAILURE", Config.ERROR_PHASE_FAILURE);
        Config.ERROR_MAP.put("ERROR_PHASE_ASYMMETRY", Config.ERROR_PHASE_ASYMMETRY);
        Config.ERROR_MAP.put("ERROR_INCREASE_VOLTAGE_R", Config.ERROR_INCREASE_VOLTAGE_R);
        Config.ERROR_MAP.put("ERROR_INCREASE_VOLTAGE_S", Config.ERROR_INCREASE_VOLTAGE_S);
        Config.ERROR_MAP.put("ERROR_INCREASE_VOLTAGE_T", Config.ERROR_INCREASE_VOLTAGE_T);
        Config.ERROR_MAP.put("ERROR_DECREASE_VOLTAGE_R", Config.ERROR_DECREASE_VOLTAGE_R);
        Config.ERROR_MAP.put("ERROR_DECREASE_VOLTAGE_S", Config.ERROR_DECREASE_VOLTAGE_S);
        Config.ERROR_MAP.put("ERROR_DECREASE_VOLTAGE_T", Config.ERROR_DECREASE_VOLTAGE_T);
        Config.ERROR_MAP.put("ERROR_COMPRESSOR_OVERLOAD", Config.ERROR_COMPRESSOR_OVERLOAD);
        Config.ERROR_MAP.put("ERROR_INCREASE_COMPRESSOR_CURRENT", Config.ERROR_INCREASE_COMPRESSOR_CURRENT);
        Config.ERROR_MAP.put("ERROR_DECREASE_COMPRESSOR_CURRENT", Config.ERROR_DECREASE_COMPRESSOR_CURRENT);
        Config.ERROR_MAP.put("ERROR_COMPRESSOR_ASYMMETRY", Config.ERROR_COMPRESSOR_ASYMMETRY);
        Config.ERROR_MAP.put("ERROR_INCREASE_OVAPRATOR_CURRENT", Config.ERROR_INCREASE_OVAPRATOR_CURRENT);
        Config.ERROR_MAP.put("ERROR_DECREASE_OVAPRATOR_CURRENT", Config.ERROR_DECREASE_OVAPRATOR_CURRENT);
        Config.ERROR_MAP.put("ERROR_OVAPRATOR_FLOW_ASYMMETRY", Config.ERROR_OVAPRATOR_FLOW_ASYMMETRY);
        Config.ERROR_MAP.put("ERROR_HIGH_GAS", Config.ERROR_HIGH_GAS);
        Config.ERROR_MAP.put("ERROR_LOW_GAS", Config.ERROR_LOW_GAS);
        Config.ERROR_MAP.put("ERROR_OIL", Config.ERROR_OIL);


    }

    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 1);
        } else {

        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    checkRunTimePermission();
                }
                return;
            }
        }
    }

    public void showDialog() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, Config.myActivity.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Config.myActivity.startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.KITKAT && !isDefaultSmsApp(Config.myActivity)) {
//            Toast.makeText(instance, "دسترسی رد شد!", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(instance, ":|", Toast.LENGTH_SHORT).show();
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isDefaultSmsApp(Context context) {
        return context.getPackageName().equals(Telephony.Sms.getDefaultSmsPackage(context));
    }

    @Override
    protected void onStop() {
        stopService(new Intent(MainActivity.this, BackgroundChecker.class));
        super.onStop();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e(getClass().getSimpleName(), "dispatchKeyEvent: " + event.getKeyCode() + event.getAction());
        if (event.getKeyCode() == KeyEvent.KEYCODE_HOME) {
            stopService(new Intent(MainActivity.this, BackgroundChecker.class));
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onPause() {
        stopService(new Intent(MainActivity.this, BackgroundChecker.class));
        super.onPause();
    }
}
