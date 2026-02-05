package ir.coolroom2.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.PrepareDataAndSendSms;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends AppCompatActivity {

    private TextInputLayout phone_number_box, name_box, user_number_box, family_box, coolroom_name_box, details_box;
    private EditText phone_number, name, user_number, family, coolroom_name, details;
    private EditText length, Width, Height, Thickness, compressor, condonsor, ovaperator;
    private LinearLayout dimension_linear, power_linear, province_linear, underzero_linear, type_of_product_linear;
    private DatabaseHandler db;

    private RadioGroup underzero_rg;
    private Spinner province_sp;
    private Button type_of_product;
    private boolean permission_status = false;
    private boolean phone_number_text_change_flag = false;
    private ArrayList<Boolean> type_of_product_values = new ArrayList<>();
    private String type_of_product_string_value = "";
    private PrepareDataAndSendSms prepareDataAndSendSms;
    private boolean user_number_text_change_flag = false;
    public static LoginActivity instance;
    TextView typeOfproduct_tv;

    private Dialog dialogSelectProductType;

    @Override
    public void onStart() {
        super.onStart();
        instance = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHandler(LoginActivity.this);
        prepareDataAndSendSms = new PrepareDataAndSendSms(LoginActivity.this);
        checkRunTimePermission();


        if (!Config.ForceToCreateRoom)
            if (db.getSelectedRoom() != null) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        setContentView(R.layout.activity_login);

        ImageView linkimg = (ImageView) findViewById(R.id.linkimg);
        linkimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.atis-cool.ir"));
                startActivity(browserIntent);
            }
        });






        Config.loginActivity = LoginActivity.this;

        findView();

        final RadioGroup level_select = (RadioGroup) findViewById(R.id.level_select);
        final Button send = (Button) findViewById(R.id.send);

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.province_list));
        provinceAdapter.setDropDownViewResource(R.layout.dropdown_rtl_layout);
        province_sp.setAdapter(provinceAdapter);


        level_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.one:
                        one_selected();
                        typeOfproduct_tv.setVisibility(View.VISIBLE);
                        break;
                    case R.id.two:
                        two_selected();
                        typeOfproduct_tv.setVisibility(View.GONE);
                        break;
                    case R.id.three:
                        three_selected();
                        typeOfproduct_tv.setVisibility(View.GONE);
                        break;
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flage = false;
                switch (level_select.getCheckedRadioButtonId()) {
                    case R.id.one:
                        Config.permision_level = "one";
                        flage = valid_on_one();
                        break;
                    case R.id.two:
                        flage = valid_on_two();
                        Config.permision_level = "two";
                        break;
                    case R.id.three:
                        flage = valid_on_three();
                        Config.permision_level = "three";
                        break;

                }



                /*
                dimension => width:height:length:thickness
                power => compressor:condonsor:ovaprator
                */

                if (phone_number.getText().toString().length() >= 2) {
                    if (!phone_number.getText().toString().substring(0, 2).equals("09") || phone_number.getText().toString().length() < 11) {
                        phone_number.setError("شماره وارد شده صحیح نمی باشد.");
                        phone_number_text_change_flag = false;
                    } else {
                        phone_number.setError(null);
                        phone_number_text_change_flag = true;
                    }
                }

                if (user_number.getText().toString().length() >= 2) {
                    if (!user_number.getText().toString().substring(0, 2).equals("09") || user_number.getText().toString().length() < 11) {
                        user_number.setError("شماره وارد شده صحیح نمی باشد.");
                        user_number_text_change_flag = false;
                    } else {
                        user_number.setError(null);
                        user_number_text_change_flag = true;
                    }
                }

                if (flage && phone_number_text_change_flag) {

                    final RoomModel roomModel = db.getRoom(phone_number.getText().toString());
                    if (roomModel == null) {
                        RoomModel room = new RoomModel(
                                phone_number.getText().toString(),
                                coolroom_name.getText().toString(),
                                province_sp.getSelectedItem().toString(),
                                Width.getText().toString(),
                                Height.getText().toString(),
                                length.getText().toString(),
                                Thickness.getText().toString(),
                                compressor.getText().toString(),
                                ovaperator.getText().toString(),
                                condonsor.getText().toString(),
                                type_of_product_string_value,
                                underzero_rg.getCheckedRadioButtonId() == R.id.under_zero,
                                details.getText().toString(),
                                "-1"
                        );

                        String message = "";

                        if (Config.permision_level.matches("one")) {
                            message = prepareDataAndSendSms.send_registery_data(
                                    phone_number.getText().toString(),
                                    "M",
                                    "A",
                                    "1",
                                    user_number.getText().toString(),
                                    Config.provincial_code.get(province_sp.getSelectedItem().toString()),
                                    type_of_product_string_value,
                                    ((underzero_rg.getCheckedRadioButtonId() == R.id.under_zero) ? "1" : "0"),
                                    length.getText().toString(),
                                    Width.getText().toString(),
                                    Height.getText().toString(),
                                    Thickness.getText().toString(),
                                    compressor.getText().toString(),
                                    condonsor.getText().toString(),
                                    ovaperator.getText().toString());


                        } else {
                            message = prepareDataAndSendSms.send_registery_data(
                                    phone_number.getText().toString(),
                                    (Config.permision_level.matches("two") ? "T" : "U"),
                                    "A",
                                    (Config.permision_level.matches("two") ? "2" : "3"),
                                    user_number.getText().toString(),
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

                        ////////////////////// start ///////////////////
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("توجه");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("لطفا منتظر پاسخ سرور باشید.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "خروج",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        LoginActivity.this.finish();
                                        dialog.dismiss();
                                    }
                                });


                        int re = db.createRoom(room);
                        if (re == 0) {
                            Config.currentRoomID = db.getLastRoom().get_id();
                            db.createUser(new UserModel(
                                    name.getText().toString(),
                                    family.getText().toString(),
                                    user_number.getText().toString(),
                                    Config.permision_level,
                                    Config.currentRoomID));
                            alertDialog.show();

//                            db.resetAllRoomStatus();
                        } else {
                            Toast.makeText(LoginActivity.this, "این سردخانه قبلا تعریف شده است.", Toast.LENGTH_SHORT).show();
                        }

                        db.createOrder("",
                                room.get_number(),
                                Config._STATE_COMPLETED,
                                Config._REPORT_FROM_CLIENT,
                                "",
                                new HashMap<String, String>(),
                                Config._TYPE_CREATE_ROOM,
                                Config.currentRoomID,
                                db.getFirstUser_for_room(Config.currentRoomID).get_id());


                        db.createOrder(message,
                                room.get_number(),
                                Config._STATE_PENDING,
                                Config._REPORT_FROM_CLIENT,
                                "",
                                new HashMap<String, String>(),
                                Config._TYPE_CREATE_USER,
                                Config.currentRoomID,
                                db.getFirstUser_for_room(Config.currentRoomID).get_id());


                        /////////////////////////  end  /////////////////////////////


                    } else {

                        String message = "";


                        ////////////////////// start ///////////////////
                        int resu = db.createRoom(roomModel);
                        if (resu == 0) {

                            Config.currentRoomID = db.getLastRoom().get_id();
                            db.createUser(new UserModel(
                                    name.getText().toString(),
                                    family.getText().toString(),
                                    user_number.getText().toString(),
                                    Config.permision_level,
                                    Config.currentRoomID));


                            if (Config.permision_level.matches("one") && resu == 0) {
                                message = prepareDataAndSendSms.send_registery_data(
                                        phone_number.getText().toString(),
                                        "M",
                                        "A",
                                        "1",
                                        user_number.getText().toString(),
                                        Config.provincial_code.get(province_sp.getSelectedItem().toString()),
                                        type_of_product_string_value,
                                        ((underzero_rg.getCheckedRadioButtonId() == R.id.under_zero) ? "1" : "0"),
                                        length.getText().toString(),
                                        Width.getText().toString(),
                                        Height.getText().toString(),
                                        Thickness.getText().toString(),
                                        compressor.getText().toString(),
                                        condonsor.getText().toString(),
                                        ovaperator.getText().toString());


                            } else if (Config.permision_level.matches("two") || Config.permision_level.matches("three") && resu == 0) {
                                message = prepareDataAndSendSms.send_registery_data(
                                        phone_number.getText().toString(),
                                        (Config.permision_level.matches("two") ? "T" : "U"),
                                        "A",
                                        (Config.permision_level.matches("two") ? "2" : "3"),
                                        user_number.getText().toString(),
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

                            db.createOrder("",
                                    roomModel.get_number(),
                                    Config._STATE_COMPLETED,
                                    Config._REPORT_FROM_CLIENT,
                                    "",
                                    new HashMap<String, String>(),
                                    Config._TYPE_CREATE_ROOM,
                                    Config.currentRoomID,
                                    db.getFirstUser_for_room(Config.currentRoomID).get_id());


                            db.createOrder(message,
                                    roomModel.get_number(),
                                    Config._STATE_PENDING,
                                    Config._REPORT_FROM_CLIENT,
                                    "",
                                    new HashMap<String, String>(),
                                    Config._TYPE_CREATE_USER,
                                    Config.currentRoomID,
                                    db.getFirstUser_for_room(Config.currentRoomID).get_id());


//                            db.resetAllRoomStatus();
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("توجه");
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage("لطفا منتظر پاسخ سرور باشید.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "خروج",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LoginActivity.this.finish();
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();


                        } else {

//                            Toast.makeText(LoginActivity.this, "این سردخانه قبلا تعریف شده است.", Toast.LENGTH_SHORT).show();
                        }


                        /////////////////////////  end  /////////////////////////////


                        dialogSelectProductType.dismiss();
                    }


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        checkRunTimePermission();
                    } else {
                        permission_status = true;
                        if (permission_status) {

//                            SmsManager smsManager = SmsManager.getDefault();
//                            smsManager.sendTextMessage(phone_number.getText().toString(), null, "report", null, null);

                        } else {
//                            checkRunTimePermission();
                        }
                    }

                    //Warning don't delete this method ! ☻
                    //THIS METHOD WILL ACTIVE FIRST ROOM OR ACTIVE AT LAST FINEDE ROOM IN DB
                    Config.currentRoomID = db.customActiveRoom();


                } else {
                    Toast.makeText(LoginActivity.this, "لطفا مقادیر را دوباره چک کنید.", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        dialog product type
        dialogSelectProductType = new Dialog(LoginActivity.this);
        dialogSelectProductType.setContentView(R.layout.dialog_select_product_type);

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

        ArrayAdapter<String> productTypeAdapter = new ArrayAdapter<String>(LoginActivity.this,
                R.layout.adapter_style_product_type_select, Arrays.copyOfRange(getResources().getStringArray(R.array.product_type_list), 1, getResources().getStringArray(R.array.product_type_list).length));
        productTypeAdapter.setDropDownViewResource(R.layout.adapter_style_product_type_select);
        listView.setAdapter(productTypeAdapter);

        for (int i = 0; i < getResources().getStringArray(R.array.product_type_list).length - 1; i++)
            type_of_product_values.add(false);
        type_of_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSelectProductType();
            }
        });
    }


    public void findView() {

        //master
        phone_number = (EditText) findViewById(R.id.number);
        name = (EditText) findViewById(R.id.name);
        family = (EditText) findViewById(R.id.family);
        user_number = (EditText) findViewById(R.id.user_number);
        coolroom_name = (EditText) findViewById(R.id.coolroom_name);
        details = (EditText) findViewById(R.id.details);
        province_sp = (Spinner) findViewById(R.id.province_sp);
        phone_number_box = (TextInputLayout) findViewById(R.id.number_box);
        name_box = (TextInputLayout) findViewById(R.id.name_box);
        family_box = (TextInputLayout) findViewById(R.id.family_box);
        user_number_box = (TextInputLayout) findViewById(R.id.user_number_box);
        coolroom_name_box = (TextInputLayout) findViewById(R.id.coolroom_name_box);
        details_box = (TextInputLayout) findViewById(R.id.details_box);
        underzero_rg = (RadioGroup) findViewById(R.id.underzero_rg);
        type_of_product = (Button) findViewById(R.id.type_of_product);
        type_of_product_linear = (LinearLayout) findViewById(R.id.type_of_product_linear);

        phone_number = (EditText) findViewById(R.id.number);
        name = (EditText) findViewById(R.id.name);

        underzero_linear = (LinearLayout) findViewById(R.id.underZero_linear);

        length = (EditText) findViewById(R.id.length);
        Width = (EditText) findViewById(R.id.Width);
        Height = (EditText) findViewById(R.id.Height);
        Thickness = (EditText) findViewById(R.id.Thickness);
        compressor = (EditText) findViewById(R.id.compressor);
        condonsor = (EditText) findViewById(R.id.condonsor);
        ovaperator = (EditText) findViewById(R.id.ovaperator);

        dimension_linear = (LinearLayout) findViewById(R.id.dimension_linear);
        power_linear = (LinearLayout) findViewById(R.id.power_linear);
        province_linear = (LinearLayout) findViewById(R.id.choose_province);

        typeOfproduct_tv = (TextView) findViewById(R.id.type_of_product_tv);


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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permission_status = true;
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phone_number.getText().toString(), null, "report", null, null);
                } else {
                    permission_status = false;
                    checkRunTimePermission();
                }
                return;
            }
        }
    }

    private void one_selected() {
        name_box.setVisibility(View.VISIBLE);
        family_box.setVisibility(View.VISIBLE);
        user_number_box.setVisibility(View.VISIBLE);
        phone_number_box.setVisibility(View.VISIBLE);
        coolroom_name_box.setVisibility(View.VISIBLE);
        details_box.setVisibility(View.VISIBLE);
        province_linear.setVisibility(View.VISIBLE);
        underzero_rg.setVisibility(View.VISIBLE);
        underzero_linear.setVisibility(View.VISIBLE);
        dimension_linear.setVisibility(View.VISIBLE);
        power_linear.setVisibility(View.VISIBLE);
        type_of_product_linear.setVisibility(View.VISIBLE);
    }

    private void two_selected() {
        name_box.setVisibility(View.VISIBLE);
        family_box.setVisibility(View.VISIBLE);
        user_number_box.setVisibility(View.VISIBLE);
        phone_number_box.setVisibility(View.VISIBLE);
        details_box.setVisibility(View.VISIBLE);
        underzero_rg.setVisibility(View.GONE);
        type_of_product_linear.setVisibility(View.GONE);
        dimension_linear.setVisibility(View.GONE);
        power_linear.setVisibility(View.GONE);
        underzero_linear.setVisibility(View.GONE);
        province_linear.setVisibility(View.GONE);
        coolroom_name_box.setVisibility(View.VISIBLE);
    }

    private void three_selected() {
        name_box.setVisibility(View.VISIBLE);
        family_box.setVisibility(View.VISIBLE);
        user_number_box.setVisibility(View.VISIBLE);
        phone_number_box.setVisibility(View.VISIBLE);
        details_box.setVisibility(View.VISIBLE);
        coolroom_name_box.setVisibility(View.VISIBLE);
        underzero_rg.setVisibility(View.GONE);
        type_of_product_linear.setVisibility(View.GONE);
        dimension_linear.setVisibility(View.GONE);
        province_linear.setVisibility(View.GONE);
        power_linear.setVisibility(View.GONE);
        underzero_linear.setVisibility(View.GONE);
    }


    private boolean valid_on_one() {
        boolean flag = true;
        if (name.getText().toString().matches("")) {
            flag = false;
            name_box.setError(" ");
        } else {
            name_box.setError(null);
        }
        if (phone_number.getText().toString().matches("")) {
            flag = false;
            phone_number_box.setError(" ");
        } else {
            phone_number_box.setError(null);
        }

        if (family.getText().toString().matches("")) {
            flag = false;
            family_box.setError(" ");
        } else {
            family_box.setError(null);
        }
        if (user_number.getText().toString().matches("")) {
            flag = false;
            user_number_box.setError(" ");
        } else {
            user_number_box.setError(null);
        }
        if (coolroom_name.getText().toString().matches("")) {
            flag = false;
            coolroom_name_box.setError(" ");
        } else {
            coolroom_name_box.setError(null);
        }
        if (details.getText().toString().matches("")) {
            flag = false;
            details_box.setError(" ");
        } else {
            details_box.setError(null);
        }
        if (province_sp.getSelectedItemPosition() == 0) {
            flag = false;
            Toast.makeText(this, "لطفا یک استان را انتخاب نمایید", Toast.LENGTH_SHORT).show();
        }
        if (underzero_rg.getCheckedRadioButtonId() == -1) {
            flag = false;
            coolroom_name_box.setError(" ");
        } else {
            coolroom_name_box.setError(null);
        }
        if (type_of_product_string_value.length() <= 0 || type_of_product_string_value.equals("0")) {
            flag = false;
            Toast.makeText(this, "لطفا یک محصول را انتخاب کنید", Toast.LENGTH_SHORT).show();
        }
        if (!Width.getText().toString().matches("[0-9.?]+")) {
            flag = false;
            Width.setError(" ");
        } else {
            Width.setError(null);
        }
        if (!Height.getText().toString().matches("[0-9.?]+")) {
            flag = false;
            Height.setError(" ");
        } else {
            Height.setError(null);
        }
        if (!length.getText().toString().matches("[0-9.?]+")) {
            flag = false;
            length.setError(" ");
        } else {
            length.setError(null);
        }
        if (!Thickness.getText().toString().matches("[0-9.?]+")) {
            flag = false;
            Thickness.setError(" ");
        } else {
            Thickness.setError(null);
        }
        if (compressor.getText().toString().matches("")) {
            flag = false;
            compressor.setError("");
        } else {
            compressor.setError(null);
        }
        if (condonsor.getText().toString().matches("")) {
            flag = false;
            condonsor.setError("");
        } else {
            condonsor.setError(null);
        }
        if (ovaperator.getText().toString().matches("")) {
            flag = false;
            ovaperator.setError("");
        } else {
            ovaperator.setError(null);
        }
        return flag;
    }

    private boolean valid_on_two() {

        boolean flag = true;
        if (name.getText().toString().matches("")) {
            flag = false;
            name_box.setError(" ");
        } else {
            name_box.setError(null);
        }
        if (family.getText().toString().matches("")) {
            flag = false;
            family_box.setError(" ");
        } else {
            family_box.setError(null);
        }
        if (user_number.getText().toString().matches("")) {
            flag = false;
            user_number_box.setError(" ");
        } else {
            user_number_box.setError(null);
        }
        if (phone_number.getText().toString().matches("")) {
            flag = false;
            phone_number_box.setError(" ");
        } else {
            phone_number_box.setError(null);
        }
        if (details.getText().toString().matches("")) {
            flag = false;
            details.setError(" ");
            details_box.setErrorTextAppearance(android.R.style.TextAppearance_Holo);
        }
        if (coolroom_name.getText().toString().matches("")) {
            flag = false;
            coolroom_name_box.setError(" ");
        } else {
            coolroom_name_box.setError(null);
        }


        return flag;
    }

    private boolean valid_on_three() {
        boolean flag = true;
        if (name.getText().toString().matches("")) {
            flag = false;
            name_box.setError(" ");
            name_box.setErrorTextAppearance(android.R.style.TextAppearance_Holo);
        }
        if (family.getText().toString().matches("")) {
            flag = false;
            family_box.setError(" ");
            family_box.setErrorTextAppearance(android.R.style.TextAppearance_Holo);
        }
        if (user_number.getText().toString().matches("")) {
            flag = false;
            user_number_box.setError(" ");
            user_number_box.setErrorTextAppearance(android.R.style.TextAppearance_Holo);
        } else {
            user_number_box.setError(null);
        }
        if (phone_number.getText().toString().matches("")) {
            flag = false;
            phone_number_box.setError(" ");
            phone_number_box.setErrorTextAppearance(android.R.style.TextAppearance_Holo);
        }
        if (details.getText().toString().matches("")) {
            flag = false;
            details.setError(" ");
            details_box.setErrorTextAppearance(android.R.style.TextAppearance_Holo);
        }
        if (coolroom_name.getText().toString().matches("")) {
            flag = false;
            coolroom_name_box.setError(" ");
        } else {
            coolroom_name_box.setError(null);
        }
        return flag;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
                typeOfproduct_tv.setText(p_str.substring(1, p_str.length() - 1));

                dialogSelectProductType.hide();
            }
        });

        dialogSelectProductType.show();
    }
}
