package ir.coolroom2.Fragment.Registry;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ir.coolroom2.Activity.LoginActivity;
import ir.coolroom2.Activity.MainActivity;
import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.PrepareDataAndSendSms;


public class AddRoomFragment extends Fragment {

    DatabaseHandler db;
    private TextView room_name, room_number, room_location, room_product_type, room_width, room_length, room_height, room_compressor, room_ovaprator, room_condonsor, room_description;
    private CheckBox room_uncer_zero;

    RoomModel room;

    private PrepareDataAndSendSms prepareDataAndSendSms;


    LinearLayout roomName_linear,
            roomNumber_linear,
            roomLocation_linear,
            roomProductType_linear,
            roomDescription_linear,
            dimension_linear,
            power_linear,
            roomUnderZero_linear;

    LinearLayout roomName_linear_dg,
            roomNumber_linear_dg,
            roomLocation_linear_dg,
            roomProductType_linear_dg,
            roomDescription_linear_dg,
            dimension_linear_dg,
            power_linear_dg,
            roomUnderZero_linear_dg;
    RoomModel roomtmp;

    private static AddRoomFragment inst;
    private RadioButton roomUnderZero_rg_under_zero;
    private RadioButton roomUnderZero_rg_over_zero;
    private RadioGroup roomUnderZero_rg;
    private TextView room_thickness;

    RadioButton UnderZero, AboveZero;


    //For Dialog
    private Button type_of_product;
    private Dialog dialogSelectProductType;
    private ArrayList<Boolean> type_of_product_values = new ArrayList<>();
    private String type_of_product_string_value = "";
    private TextView type_of_product_tv;
    private ArrayAdapter<String> productTypeAdapter;


    public AddRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onResume() {
        super.onResume();
        inst = this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room, container, false);
        prepareDataAndSendSms = new PrepareDataAndSendSms(getActivity());
        findView(view);
        Linear_findView(view);
        findFragmentViews(view);
        db = new DatabaseHandler(getActivity());
        room = db.getRoom(Config.currentRoomID);
        if (room != null) {
            room_name.setText(room.get_name());
            room_number.setText(room.get_number());
            room_location.setText(room.get_location());
            room_ovaprator.setText(room.get_ovaperator().replaceFirst("^0+(?!$)", ""));
            room_condonsor.setText(room.get_condonsor().replaceFirst("^0+(?!$)", ""));
            room_length.setText(room.get_length().replaceFirst("^0+(?!$)", ""));
            room_width.setText(room.get_width().replaceFirst("^0+(?!$)", ""));
            room_height.setText(room.get_height().replaceFirst("^0+(?!$)", ""));
            room_thickness.setText(room.get_thickness().replaceFirst("^0+(?!$)", ""));
            room_compressor.setText(room.get_compressor().replaceFirst("^0+(?!$)", ""));
            room_description.setText(room.get_description());

            if (room.get_product_type().length() > 0) {
                String bin = String.format("%6s", Integer.toBinaryString(Integer.parseInt(room.get_product_type()))).replace(" ", "0");
                ArrayList<String> products = new ArrayList<>();
                String[] p_arr = Arrays.copyOfRange(getResources().getStringArray(R.array.product_type_list), 1, getResources().getStringArray(R.array.product_type_list).length);
                for (int i = 0; i < bin.length(); i++) {
                    char c = bin.charAt(i);
                    if (c == '1') {
                        products.add(p_arr[i]);
                    }
                }
                String p_str = products.toString();
                room_product_type.setText(p_str.substring(1, p_str.length() - 1));
            }

            if (room.is_under_zero()) {
                room_uncer_zero.setText("سردخانه زیر صفر");
                UnderZero.setChecked(true);
                AboveZero.setClickable(false);
            } else {
                room_uncer_zero.setText("سردخانه بالای صفر");
                AboveZero.setChecked(true);
                UnderZero.setClickable(false);
            }
            room_uncer_zero.setChecked(room.is_under_zero());
        }

        LinearLayout add_room = (LinearLayout) view.findViewById(R.id.add_room);
        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                Config.ForceToCreateRoom = true;
                startActivity(intent);
            }
        });

        LinearLayout edit_room = (LinearLayout) view.findViewById(R.id.edit_room);
        edit_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomtmp = db.getRoom(Config.currentRoomID);
                final Dialog dialog_editRoom = new Dialog(getActivity());
                dialog_editRoom.setContentView(R.layout.dialog_room_detail);
                Linear_for_dialog(dialog_editRoom);

                Button save = (Button) dialog_editRoom.findViewById(R.id.edit);
                Button cancel = (Button) dialog_editRoom.findViewById(R.id.cancel);

                final EditText roomName = (EditText) dialog_editRoom.findViewById(R.id.roomName);
                final EditText roomNumber = (EditText) dialog_editRoom.findViewById(R.id.roomNumber);
                final Spinner roomLocation = (Spinner) dialog_editRoom.findViewById(R.id.roomLocation);
                final EditText roomWidth = (EditText) dialog_editRoom.findViewById(R.id.Width);
                final EditText roomHeight = (EditText) dialog_editRoom.findViewById(R.id.Height);
                final EditText roomLength = (EditText) dialog_editRoom.findViewById(R.id.length);
                final EditText roomThickness = (EditText) dialog_editRoom.findViewById(R.id.Thickness);
                final EditText roomCompressor = (EditText) dialog_editRoom.findViewById(R.id.compressor);
                final EditText roomCondonsor = (EditText) dialog_editRoom.findViewById(R.id.condonsor);
                final EditText roomOvaperator = (EditText) dialog_editRoom.findViewById(R.id.ovaperator);
                final EditText roomDescription = (EditText) dialog_editRoom.findViewById(R.id.roomDescription);
                final CheckBox roomUnderZero = (CheckBox) dialog_editRoom.findViewById(R.id.roomUnderZero);
                final RadioGroup roomUnderZeroooo = (RadioGroup) dialog_editRoom.findViewById(R.id.roomUnderZero_rg);
                final RadioButton unzerz = (RadioButton) dialog_editRoom.findViewById(R.id.roomUnderZero_rg_under_zero);
                final RadioButton overz = (RadioButton) dialog_editRoom.findViewById(R.id.roomUnderZero_rg_over_zero);

                roomNumber.setFocusable(false);
                roomNumber.setClickable(false);

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
                roomWidth.setText(roomtmp.get_width().replaceFirst("^0+(?!$)", ""));
                roomHeight.setText(roomtmp.get_height().replaceFirst("^0+(?!$)", ""));
                roomLength.setText(roomtmp.get_length().replaceFirst("^0+(?!$)", ""));
                roomThickness.setText(roomtmp.get_thickness().replaceFirst("^0+(?!$)", ""));
                roomCompressor.setText(roomtmp.get_compressor().replaceFirst("^0+(?!$)", ""));
                roomOvaperator.setText(roomtmp.get_ovaperator().replaceFirst("^0+(?!$)", ""));
                roomCondonsor.setText(roomtmp.get_condonsor().replaceFirst("^0+(?!$)", ""));
                roomUnderZero.setVisibility(View.GONE);
                if (roomtmp.is_under_zero()) {
                    roomUnderZero_rg_under_zero.setChecked(true);
                    roomUnderZero_rg_over_zero.setChecked(false);
                } else {
                    roomUnderZero_rg_under_zero.setChecked(false);
                    roomUnderZero_rg_over_zero.setChecked(true);
                }
                roomDescription.setText(roomtmp.get_description());

                ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.province_list));
                provinceAdapter.setDropDownViewResource(R.layout.dropdown_rtl_layout);
                roomLocation.setAdapter(provinceAdapter);

                String[] province_list = getResources().getStringArray(R.array.province_list);
                List<String> pl = Arrays.asList(province_list);
                int index_pl = pl.indexOf(room.get_location());
                roomLocation.setSelection(index_pl);


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        db.updateRoomLimited(new RoomModel(
                                room.get_id(),
                                roomName.getText().toString(),
                                roomDescription.getText().toString()));

                        AddRoomFragment addRoomFragment = AddRoomFragment.instance();
                        addRoomFragment.update();

                        String current_permission_level = db.PermissionLevel(db.getFirstUser_for_room(room.get_id()).get_id());
                        if (current_permission_level.matches("one")) {
                            String aBoolean = ((roomUnderZeroooo.getCheckedRadioButtonId() == R.id.roomUnderZero_rg_under_zero) ? "1" : "0");
                            prepareDataAndSendSms.send_registery_data(
                                    roomNumber.getText().toString(),
                                    "M",
                                    "E",
                                    "1",
                                    db.getFirstUser_for_room(Config.currentRoomID).get_mobile(),
                                    Config.provincial_code.get(roomLocation.getSelectedItem().toString()),
                                    type_of_product_string_value,
                                    aBoolean,
                                    roomLength.getText().toString(),
                                    roomWidth.getText().toString(),
                                    roomHeight.getText().toString(),
                                    roomThickness.getText().toString(),
                                    roomCompressor.getText().toString(),
                                    roomCondonsor.getText().toString(),
                                    roomOvaperator.getText().toString());


                        } else if (current_permission_level.matches("two") || current_permission_level.matches("three")) {
                            prepareDataAndSendSms.send_registery_data(
                                    roomNumber.getText().toString(),
                                    (current_permission_level.matches("two") ? "T" : "U"),
                                    "E",
                                    (current_permission_level.matches("two") ? "2" : "3"),
                                    db.getFirstUser_for_room(Config.currentRoomID).get_mobile(),
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "");
                        }


                        db.createOrder(prepareDataAndSendSms.Global_Message,
                                db.getRoom(Config.currentRoomID).get_number(),
                                Config._STATE_PENDING,
                                Config._REPORT_FROM_CLIENT,
                                "",
                                new HashMap<String, String>(),
                                Config._TYPE_EDIT_ROOM,
                                Config.currentRoomID,
                                db.getFirstUser_for_room(Config.currentRoomID).get_id());

                        dialog_editRoom.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog_editRoom.dismiss();
                    }
                });


                //type of product
                type_of_product = dialog_editRoom.findViewById(R.id.type_of_product);
                type_of_product_tv = dialog_editRoom.findViewById(R.id.type_of_product_tv);
                type_of_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialogSelectProductType();
                    }
                });
                if (room.get_product_type().length() > 0) {
                    String bin = String.format("%6s", Integer.toBinaryString(Integer.parseInt(room.get_product_type()))).replace(" ", "0");
                    type_of_product_string_value = toDecimal(bin);
                    ArrayList<String> products = new ArrayList<>();
                    String[] p_arr = Arrays.copyOfRange(getResources().getStringArray(R.array.product_type_list), 1, getResources().getStringArray(R.array.product_type_list).length);
                    for (int i = 0; i < bin.length(); i++) {
                        char c = bin.charAt(i);
                        if (c == '1') {
                            products.add(p_arr[i]);
                        }
                    }
                    String p_str = products.toString();
                    type_of_product_tv.setText(p_str.substring(1, p_str.length() - 1));
                }

                dialog_editRoom.show();
            }
        });


        final Dialog confirm_delete = new Dialog(getActivity());
        confirm_delete.setContentView(R.layout.dialog_custom_alert);
        ((TextView) confirm_delete.findViewById(R.id.posetive_btn_text)).setText("بله");
        ((TextView) confirm_delete.findViewById(R.id.negative_btn_text)).setText("خیر");
        LinearLayout top = (LinearLayout) confirm_delete.findViewById(R.id.top);
        top.setBackgroundColor(getResources().getColor(R.color.warningColor));
        ((TextView) confirm_delete.findViewById(R.id.title)).setText("اخطار!");
        ((TextView) confirm_delete.findViewById(R.id.description)).setText("با حذف کردن این سرد خانه کل اطلاعات شما از دیتابیس حذف خواهد شد.\n آیا مطمئن هستید؟");
        LinearLayout l1 = (LinearLayout) confirm_delete.findViewById(R.id.cancel);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_delete.hide();
            }
        });

        LinearLayout l2 = (LinearLayout) confirm_delete.findViewById(R.id.ok);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.createOrder("",
                        db.getRoom(Config.currentRoomID).get_number(),
                        Config._STATE_COMPLETED,
                        Config._REPORT_FROM_CLIENT,
                        "",
                        new HashMap<String, String>(),
                        Config._TYPE_DELETE_ROOM,
                        Config.currentRoomID,
                        db.getFirstUser_for_room(Config.currentRoomID).get_id());

                UserModel userModel = db.getFirstUser_for_room(Config.currentRoomID);

                if (Config.permision_level.matches("one")) {
                    prepareDataAndSendSms.send_registery_data(
                            db.getRoom(Config.currentRoomID).get_number(),
                            "M",
                            "D",
                            "1",
                            userModel.get_mobile(),
                            Config.provincial_code.get(room.get_location()),
                            "11",
                            (room.is_under_zero() ? "1" : "0"),
                            room.get_length(),
                            room.get_width(),
                            room.get_height(),
                            room.get_thickness(),
                            room.get_compressor(),
                            room.get_condonsor(),
                            room.get_ovaperator());
                } else {
                    prepareDataAndSendSms.send_registery_data(
                            db.getRoom(Config.currentRoomID).get_number(),
                            (Config.permision_level.matches("two") ? "T" : "U"),
                            "D",
                            (Config.permision_level.matches("two") ? "2" : "3"),
                            userModel.get_mobile(),
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "");
                }

                /*db.deleteRoom(room.get_id());
                db.deleteAlluser_for_cool_room(room.get_id());
                db = new DatabaseHandler(getActivity());
                Config.currentRoomID = db.getlsat_room_id();
                room = db.getRoom(Config.currentRoomID);
                UserModel tmp = db.getFirstUser_for_room(Config.currentRoomID);
                if (tmp != null) {
                    Config.permision_level = tmp.get_permision_level();
                }
                Config.mainTabHost.setCurrentTab(3);
                confirm_delete.hide();

                if (db.check_user_exsis() == 0) {
                    deleteCache(getActivity());
                    clearApplicationData();
                    Intent i = getActivity().getPackageManager()
                            .getLaunchIntentForPackage(getActivity().getPackageName());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivity(i);
                }*/

                MainActivity mainActivity = MainActivity.instance_ativity();
                mainActivity.update_UserDetails();
            }
        });


        LinearLayout delete_room = (LinearLayout) view.findViewById(R.id.delete_room);
        final Dialog dialog_delete = new Dialog(getActivity());
        dialog_delete.setContentView(R.layout.dialog_custom_alert);
        ((TextView) dialog_delete.findViewById(R.id.posetive_btn_text)).setText("بله");
        ((TextView) dialog_delete.findViewById(R.id.negative_btn_text)).setText("خیر");
        LinearLayout top2 = (LinearLayout) dialog_delete.findViewById(R.id.top);
        top2.setBackgroundColor(getResources().getColor(R.color.warningColor));
        ((TextView) dialog_delete.findViewById(R.id.title)).setText("اخطار!");
        ((TextView) dialog_delete.findViewById(R.id.description)).setText("آیا مطمئن هستید؟");
        LinearLayout l3 = (LinearLayout) dialog_delete.findViewById(R.id.cancel);
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_delete.hide();
            }
        });
        LinearLayout l4 = (LinearLayout) dialog_delete.findViewById(R.id.ok);
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_delete.show();
                dialog_delete.hide();
            }
        });

        delete_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_delete.show();
            }
        });


        switch (Config.permision_level) {
            case "one":
                valid_first();
                break;
            case "two":
                valid_second();
                break;
            case "three":
                valid_third();
                break;
        }
        if (!room.get_width().matches("")) {
            valid_first();
        }

        //for type of product

        dialogSelectProductType = new Dialog(getActivity());
        dialogSelectProductType.setContentView(R.layout.dialog_select_product_type);


        type_of_product_values.clear();
        if (!room.get_product_type().equals("") && !room.get_product_type().toString().matches("")) {
            String tmp = toBinary(room.get_product_type());
            for (int i = 0; i < 6; i++) {
                type_of_product_values.add((tmp.charAt(i) == '1'));
            }
        }


        ListView listView = dialogSelectProductType.findViewById(R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView ctv = (CheckedTextView) view;
                type_of_product_values.set(i, ctv.isChecked());
            }
        });


        productTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.adapter_style_product_type_select, Arrays.copyOfRange(getResources().getStringArray(R.array.product_type_list), 1, getResources().getStringArray(R.array.product_type_list).length));
        productTypeAdapter.setDropDownViewResource(R.layout.adapter_style_product_type_select);
        listView.setAdapter(productTypeAdapter);

        if (!room.get_product_type().equals("") && !room.get_product_type().toString().matches("")) {
            for (int i = 0; i < 6; i++) {
                listView.setItemChecked(i, type_of_product_values.get(i));
            }
        }


        return view;
    }

    private void findFragmentViews(View view) {
        room_name = (TextView) view.findViewById(R.id.roomName);
        room_number = (TextView) view.findViewById(R.id.roomNumber);
        room_location = (TextView) view.findViewById(R.id.roomLocation);
        room_width = (TextView) view.findViewById(R.id.Width);
        room_thickness = (TextView) view.findViewById(R.id.Thickness);
        room_height = (TextView) view.findViewById(R.id.Height);
        room_length = (TextView) view.findViewById(R.id.length);
        room_compressor = (TextView) view.findViewById(R.id.compressor);
        room_condonsor = (TextView) view.findViewById(R.id.condonsor);
        room_ovaprator = (TextView) view.findViewById(R.id.ovaperator);
        room_product_type = (TextView) view.findViewById(R.id.roomProductType);
        room_uncer_zero = (CheckBox) view.findViewById(R.id.roomUnderZero);
        room_description = (TextView) view.findViewById(R.id.roomDescription);
    }

    public static AddRoomFragment instance() {
        return inst;
    }

    public void update() {
        db = new DatabaseHandler(getActivity());
        final RoomModel room = db.getRoom(Config.currentRoomID);

        room_name.setText(room.get_name());
        room_number.setText(room.get_number());
        room_location.setText(room.get_location());
        room_width.setText(room.get_width().replaceFirst("^0+(?!$)", ""));
        room_height.setText(room.get_height().replaceFirst("^0+(?!$)", ""));
        room_length.setText(room.get_length().replaceFirst("^0+(?!$)", ""));
        room_ovaprator.setText(room.get_ovaperator().replaceFirst("^0+(?!$)", ""));
        room_condonsor.setText(room.get_condonsor().replaceFirst("^0+(?!$)", ""));
        room_compressor.setText(room.get_compressor().replaceFirst("^0+(?!$)", ""));
        room_description.setText(room.get_description());
        room_product_type.setText(decToTypeOfProduct(type_of_product_string_value));
        if (room.is_under_zero()) {
            room_uncer_zero.setText("سردخانه زیر صفر");
        } else {
            room_uncer_zero.setText("سردخانه بالای صفر");
        }
        room_uncer_zero.setChecked(room.is_under_zero());
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    public void clearApplicationData() {
        File cacheDirectory = getActivity().getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    void findView(View view) {
        UnderZero = view.findViewById(R.id.under_zero);
        AboveZero = view.findViewById(R.id.above_zero);
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }

    void Linear_findView(View view) {
        roomName_linear = (LinearLayout) view.findViewById(R.id.roomName_linear);
        roomNumber_linear = (LinearLayout) view.findViewById(R.id.roomNumber_linear);
        roomLocation_linear = (LinearLayout) view.findViewById(R.id.roomLocation_linear);
        roomProductType_linear = (LinearLayout) view.findViewById(R.id.roomProductType_linear);
        roomDescription_linear = (LinearLayout) view.findViewById(R.id.roomDescription_linear);
        dimension_linear = (LinearLayout) view.findViewById(R.id.dimension_linear);
        power_linear = (LinearLayout) view.findViewById(R.id.power_linear);
        roomUnderZero_linear = (LinearLayout) view.findViewById(R.id.roomUnderZero_linear);
    }


    void valid_first() {
        roomName_linear.setVisibility(View.VISIBLE);
        roomNumber_linear.setVisibility(View.VISIBLE);
        roomLocation_linear.setVisibility(View.VISIBLE);
        roomDescription_linear.setVisibility(View.VISIBLE);
        roomProductType_linear.setVisibility(View.VISIBLE);
        dimension_linear.setVisibility(View.VISIBLE);
        power_linear.setVisibility(View.VISIBLE);
        roomUnderZero_linear.setVisibility(View.VISIBLE);
    }

    void valid_second() {
        roomName_linear.setVisibility(View.VISIBLE);
        roomNumber_linear.setVisibility(View.VISIBLE);
        roomDescription_linear.setVisibility(View.VISIBLE);
        roomUnderZero_linear.setVisibility(View.GONE);
        roomProductType_linear.setVisibility(View.GONE);
        power_linear.setVisibility(View.GONE);
        dimension_linear.setVisibility(View.GONE);
        roomLocation_linear.setVisibility(View.GONE);
    }

    void valid_third() {
        roomNumber_linear.setVisibility(View.VISIBLE);
        roomDescription_linear.setVisibility(View.VISIBLE);

        roomLocation_linear.setVisibility(View.GONE);
        roomName_linear.setVisibility(View.GONE);
        roomProductType_linear.setVisibility(View.GONE);
        dimension_linear.setVisibility(View.GONE);
        power_linear.setVisibility(View.GONE);
        roomUnderZero_linear.setVisibility(View.GONE);
    }

    void Linear_for_dialog(Dialog view) {
        roomName_linear_dg = (LinearLayout) view.findViewById(R.id.roomName_linear);
        roomNumber_linear_dg = (LinearLayout) view.findViewById(R.id.roomNumber_linear);
        roomLocation_linear_dg = (LinearLayout) view.findViewById(R.id.roomLocation_linear);
        roomProductType_linear_dg = (LinearLayout) view.findViewById(R.id.roomProductType_linear);
        roomDescription_linear_dg = (LinearLayout) view.findViewById(R.id.roomDescription_linear);
        dimension_linear_dg = (LinearLayout) view.findViewById(R.id.dimension_linear);
        power_linear_dg = (LinearLayout) view.findViewById(R.id.power_linear);
        roomUnderZero_linear_dg = (LinearLayout) view.findViewById(R.id.roomUnderZero_linear);
        roomUnderZero_rg_under_zero = (RadioButton) view.findViewById(R.id.roomUnderZero_rg_under_zero);
        roomUnderZero_rg_over_zero = (RadioButton) view.findViewById(R.id.roomUnderZero_rg_over_zero);
        roomUnderZero_rg = (RadioGroup) view.findViewById(R.id.roomUnderZero_rg);

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


    private void setType_of_product_values(String bin) {
        type_of_product_values.clear();
        for (int i = 0; i < bin.length(); i++) {
            type_of_product_values.add(bin.charAt(i) == '1');
        }
    }

    private String decToTypeOfProduct(String dec) {
        String bin = toBinary(dec);
        ArrayList<String> products = new ArrayList<>();
        String[] p_arr = Arrays.copyOfRange(getResources().getStringArray(R.array.product_type_list), 1, getResources().getStringArray(R.array.product_type_list).length);
        for (int i = 0; i < bin.length(); i++) {
            char c = bin.charAt(i);
            if (c == '1') {
                products.add(p_arr[i]);
            }
        }
        String p_str = products.toString();
        return p_str.substring(1, p_str.length() - 1);
    }

    private String toBinary(String decimal) {
        return String.format("%6s", Integer.toBinaryString(Integer.parseInt(decimal))).replace(" ", "0").substring(0, 6);
    }

    private String toDecimal(String binary) {
        return Integer.parseInt(binary, 2) + "";
    }

    private void showDialogSelectProductType() {
        TextView textView = dialogSelectProductType.findViewById(R.id.internalEmpty);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bin = "";
                for (Boolean b : type_of_product_values) {
                    bin += (b) ? "1" : "0";
                }
                type_of_product_string_value = Integer.parseInt(bin, 2) + "";

                if (!type_of_product_string_value.equals("0")) {
                    type_of_product.setText("ویرایش");
                } else {
                    type_of_product.setText("انتخاب کنید");
                }

                ArrayList<String> products = new ArrayList<>();
                String[] p_arr = Arrays.copyOfRange(getResources().getStringArray(R.array.product_type_list), 1, getResources().getStringArray(R.array.product_type_list).length);
                for (int i = 0; i < bin.length(); i++) {
                    char c = bin.charAt(i);
                    if (c == '1') {
                        products.add(p_arr[i]);
                    }
                }
                String p_str = products.toString();
                type_of_product_tv.setText(p_str.substring(1, p_str.length() - 1));

                dialogSelectProductType.hide();
            }
        });

        dialogSelectProductType.show();
    }
}
