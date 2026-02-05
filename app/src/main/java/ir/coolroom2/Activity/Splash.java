package ir.coolroom2.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.ReportModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.PrepareDataAndSendSms;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Splash extends Activity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 900;
    Intent mainIntent;
    DatabaseHandler db;

    PrepareDataAndSendSms prepare;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        setVersionNumber();

        db = new DatabaseHandler(Splash.this);
        prepare = new PrepareDataAndSendSms(Splash.this);
        if (db.getSelectedRoom() != null) {
            Config.currentRoomID = db.getSelectedRoom().get_id();
            ReportModel reportModel = db.getLastLog(Config.currentRoomID);
            if (reportModel != null) {
                String message_content = reportModel.message;
                if (message_content.length() > 0) {
                    prepare.getInformation(message_content);
                } else {
                    message_content = "#I        .$%!<!D !,,,,,. I& $8( r# I& $8( r #";
                    prepare.getInformation(message_content);
                }
            } else {
                String message_content = "";
                message_content = "#I        .$%!<!D !,,,,,. I& $8( r# I& $8( r #";
                prepare.getInformation(message_content);
            }
        } else {
            if (!db.isInit()) {
                db.createOrder(
                        "#I        .$%!<!D !,,,,,. I& $8( r# I& $8( r #",
                        "09020000000",
                        Config._STATE_COMPLETED,
                        Config._REPORT_FROM_CLIENT,
                        "",
                        Config.general_data_setting,
                        Config._TYPE_RECEIVED_MESSAGE,
                        1,
                        1);//todo check code
                String message_content = "#I        .$%!<!D !,,,,,. I& $8( r# I& $8( r #";
                prepare.getInformation(message_content);
            }
        }

        load_map();
        db.repairSettingTable();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (db.getSelectedRoom() != null) {
                    String lock_pattern = db.getLockPattern();
                    if (lock_pattern == null || lock_pattern.equals("")) {
                        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                        Splash.this.startActivity(mainIntent);
                        Splash.this.finish();
                        return;
                    } else {
                        Intent mainIntent = new Intent(Splash.this, LockActivity.class);
                        Splash.this.startActivity(mainIntent);
                        Splash.this.finish();
                        return;
                    }
                } else {
                    mainIntent = new Intent(Splash.this, LoginActivity.class);
                }

                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    void setVersionNumber() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ((TextView) findViewById(R.id.version_number)).setText("ورژن : " + pInfo.versionName);
    }

    void load_map() {

        //todo must load from db

        if (!db.isNewSetting(String.valueOf(Config.currentRoomID))) {

            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V1, "-10");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V2, "3");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V3, "-3");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V4, "0");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V5, "6");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V6, "-6");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V7, "-");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V8, "-");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V9, "-");
            Config.general_data_setting.put(Config.KEY_TEMPERATURE_V10, "-");

            Config.general_data_setting.put(Config.KEY_HUMOUR_V1, "20");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V2, "3");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V3, "-3");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V4, "0");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V5, "6");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V6, "-6");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V7, "-");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V8, "-");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V9, "-");
            Config.general_data_setting.put(Config.KEY_HUMOUR_V10, "-");

            /*111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111*/
            Config.general_data_setting.put(Config.KEY_DEFROST_V1, "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
            Config.INFORMATION_DEFROST = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
            Config.general_data_setting.put(Config.KEY_DEFROST_V2, "11");

//            Config.general_data_setting.put(Config.INFORMATION_PHASE_COUNT, "1");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V1, "1");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, "390");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, "370");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V4, "60");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V5, "5");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V6, "5");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V7, "-");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V8, "-");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V9, "-");
            Config.general_data_setting.put(Config.KEY_VOLTAGE_V10, "-");

            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V1, "3");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V2, "1");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V3, "0");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V4, "60");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V5, "5");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V6, "10");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V7, "5");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V8, "0");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V9, "0");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V10, "0");

            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V1, "7");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V2, "1");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V3, "0");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V4, "60");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V5, "5");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V6, "10");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V7, "5");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V8, "0");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V9, "0");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V10, "0");

            Config.general_data_setting.put(Config.KEY_PRESSURE_V1, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_V2, "5");
            Config.general_data_setting.put(Config.KEY_PRESSURE_V3, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_V4, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_V5, "0");


            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V1, "1");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V2, "400");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V3, "10");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V4, "60");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V5, "5");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V6, "300");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V7, "200");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V8, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V9, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V10, "0");

            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V1, "1");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V2, "10");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V3, "5");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V4, "60");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V5, "5");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V6, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V7, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V8, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V9, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V10, "0");

            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V1, "1");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V2, "50");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V3, "5");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V4, "60");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V5, "5");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V6, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V7, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V8, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V9, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V10, "0");

            Config.general_data_setting.put(Config.KEY_TIMER, "0015");


            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V1, "0");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V2, "0");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V3, "5");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V4, "0");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V5, "");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V6, "0");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V7, "0");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V8, "0");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V9, "0");
            Config.general_data_setting.put(Config.KEY_INFO_CURRENT_V10, "0");

            /*Termodisk*/

            Config.general_data_setting.put(Config.KEY_TERMODISK_V1, "0");//off
            Config.general_data_setting.put(Config.KEY_TERMODISK_V2, "5");

//        Config.INFORMATION_DEFROSTTIME = "0000";

//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V1, "0");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V2, "0");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V3, "5");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V4, "0");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V5, "");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V6, "0");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V7, "0");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V8, "0");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V9, "0");
//        Config.general_data_setting.put(Config.KEY_INFO_VOLTAGE_V10, "0");

        } else {

            Config.general_data_setting.put(Config.KEY_VOLTAGE_V1, "1");
            Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V3, "1");
            Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V3, "1");
            Config.general_data_setting.put(Config.KEY_PRESSURE_V1, "0");
            Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V1, "1");
            Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V1, "1");
            Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V1, "1");
            Config.general_data_setting.put(Config.KEY_TIMER, "0015");


            PrepareDataAndSendSms prepareDataAndSendSms = new PrepareDataAndSendSms(Splash.this);
            String Message = db.getLastSetting(String.valueOf(Config.currentRoomID));
            if (!Message.equals("-1"))
                prepareDataAndSendSms.get_Setting(Message);
            else {
                Toast.makeText(this, "data corrupt", Toast.LENGTH_SHORT).show();
            }
        }
        List<String> province_list = Arrays.asList(getResources().getStringArray(R.array.province_list));
        for (int i = 0; i < province_list.size(); i++) {
            Config.provincial_code.put(province_list.get(i), (i < 10 ? "0" + String.valueOf(i) : String.valueOf(i)));
            Config.provincial_code_reverese.put((i < 10 ? "0" + String.valueOf(i) : String.valueOf(i)), province_list.get(i));


        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}