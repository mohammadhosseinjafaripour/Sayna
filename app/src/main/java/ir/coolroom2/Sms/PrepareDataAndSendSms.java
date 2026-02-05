package ir.coolroom2.Sms;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import ir.coolroom2.Activity.MainActivity;
import ir.coolroom2.Activity.Splash;
import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.R;
import ir.coolroom2.Utils.RandomString;
import ir.coolroom2.Utils.Utility;

import static android.content.ContentValues.TAG;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Mohammad on 1/24/2018.
 */

public class PrepareDataAndSendSms {
    private Context _context;
    private DatabaseHandler db;
    private String HEADER;
    private String temp = "";
    private String MESSAGE = "";
    private SmsCodeAndDecode codeAndDecode;
    public String unique_key = "";
    public static String Global_Message = "";

    boolean FLAG_TOAST = false;

    public PrepareDataAndSendSms(Context context) {
        this._context = context;
        db = new DatabaseHandler(context);
        codeAndDecode = new SmsCodeAndDecode();
        unique_key = new RandomString().randomString(3);
    }

    public void send_total_data() {
        HEADER = "";
        MESSAGE = "";


        RoomModel room = db.getRoom(Config.currentRoomID);
        String mobile = room.get_number();

        String date = Utility.getCurrentShamsidate();
        Date currentTime = Calendar.getInstance().getTime();

        HEADER =
                db.getFirstUser_for_room(Config.currentRoomID).get_mobile().substring(1, 11)
                        + date.substring(0, 4)
                        + date.substring(5, 7)
                        + date.substring(8, 10)
                        + codeAndDecode.truecent_length(currentTime.getHours() + "", 2)
                        + codeAndDecode.truecent_length(currentTime.getMinutes() + "", 2);

        temp += HEADER;

        //Temperature OK
        temp += codeAndDecode.truecent_length((codeAndDecode.qwe_float(Float.parseFloat(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V1)), 3)), 4);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V2), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V3)) * -1), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V5))), 2);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V6)) * -1), 2);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V4))), 2);

        //Humour OK
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V1))), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_HUMOUR_V2), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V3)) * -1), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V5))), 2);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V6)) * -1), 2);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V4))), 2);


        //Defrost OK
        temp += codeAndDecode.defrostToDec(Config.general_data_setting.get(Config.KEY_DEFROST_V1));
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_DEFROST_V2), 2);

        //Voltage OK
        temp += Config.general_data_setting.get(Config.KEY_VOLTAGE_V1); // onePhase and threePhase
        temp += String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2)));
        temp += String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3)));
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)))), 3);
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)))), 2);
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)))), 2);

        //Current OK
        temp += codeAndDecode.truecent_length((String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V1))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length((String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V2))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V3), 1);//reset mode
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V6), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V7), 2);

        //Current OK
        temp += codeAndDecode.truecent_length((String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V1))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length((String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V2))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V3), 1);//reset
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V6), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V7), 2);

        //pressure
        temp += Config.general_data_setting.get(Config.KEY_PRESSURE_V1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_V2), 2);

        //Pressure High Gas OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V6), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V7), 3);

        //Pressure Low Gas OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V5), 2);

        //Pressure Oil OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V5), 2);

        //TermoDisk

        temp += codeAndDecode.truecent_length((Config.general_data_setting.get(Config.KEY_TERMODISK_V1)), 1);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TERMODISK_V2))), 3);


        if (temp.length() % 2 != 0)
            temp += "0";

        Log.e(getClass().getName(), "send_total_data: " + temp);


        for (String s : codeAndDecode.split_two_by_two(temp))
            MESSAGE += codeAndDecode.code_map(s);

        //wrapping with signs
        MESSAGE = "#S" + unique_key + MESSAGE + "#";

        Log.e(TAG, "unique_key: " + unique_key);


        Log.d(getClass().getName(), "send_total_data: " + MESSAGE);


        sendSms(MESSAGE, mobile);


        db.createOrder(
                MESSAGE,
                mobile,
                Config._STATE_PENDING,
                Config._REPORT_FROM_CLIENT,
                "",
                new HashMap<String, String>(),
                Config._TYPE_SEND_MESSAGE,
                1,
                Config.currentRoomID, unique_key, "false");//todo check code
        MESSAGE = "";

    }

    public String send_registery_data(String mobile, String person, String activity, String per_level, String phone_number, String location, String product_type, String under_zero, String length, String width, String height, String thickness, String power_compressor, String power_condansor, String power_operator) {

        MESSAGE = "";
        String Temp = "";
        Temp =
                per_level +
                        phone_number.substring(1, 11);

        if (person.matches("M")) {
            Temp += (location) +
                            (product_type) +
                            (under_zero) +
                            (codeAndDecode.truecent_length(length, 3)) +
                            (codeAndDecode.truecent_length(width, 3)) +
                            (codeAndDecode.truecent_length(height, 2)) +
                            (codeAndDecode.truecent_length(thickness, 2)) +
                            (codeAndDecode.truecent_length(power_compressor, 2)) +
                            (codeAndDecode.truecent_length(power_condansor, 2)) +
                            (codeAndDecode.truecent_length(power_operator, 2));      // operator power
        }

        if (Temp.length() % 2 != 0)
            Temp += "0";
        for (String s : codeAndDecode.split_two_by_two(Temp))
            MESSAGE += codeAndDecode.code_map(s);
        MESSAGE = Config.REGISTRY_HEADER + person +
                activity + MESSAGE + "#";


        try {
            sendSms(MESSAGE, mobile);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "send_registery_data: ", e);
        }

        return MESSAGE;
    }

    public String send_user_update(String mobile, String person, String activity, String per_level, String phone_number) {

        MESSAGE = "";
        String Temp = "";
        Temp =
                per_level +
                        phone_number.substring(1, 11);

        if (Temp.length() % 2 != 0)
            Temp += "0";
        for (String s : codeAndDecode.split_two_by_two(Temp))
            MESSAGE += codeAndDecode.code_map(s);
        MESSAGE = Config.REGISTRY_HEADER + person +
                activity + MESSAGE + "#";


        try {
            sendSms(MESSAGE, mobile);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "send_registery_data: ", e);
        }

        return MESSAGE;
    }

    public void send_direct_command(String phone_number, String order_type, String diffrast_time) {


        Date currentTime = Calendar.getInstance().getTime();
        String date = Utility.getCurrentShamsidate();

        MESSAGE = "";
        String tmp = "";
        tmp =
                (phone_number.substring(1, 11)) +
                        (order_type) +
                        (diffrast_time);
        //date
        tmp += date.substring(0, 4)
                + date.substring(5, 7)
                + date.substring(8, 10);
        //time
        tmp += codeAndDecode.truecent_length(currentTime.getHours() + "", 2);
        tmp += codeAndDecode.truecent_length(currentTime.getMinutes() + "", 2);

        for (String s : codeAndDecode.split_two_by_two(tmp))
            MESSAGE += codeAndDecode.code_map(s);
        MESSAGE = Config.DIRECT_COMMAND_HEADER + MESSAGE + "#";

        RoomModel room = db.getRoom(Config.currentRoomID);
        String mobile = room.get_number();
        try {
            sendSms(MESSAGE, mobile);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "send_direct_command: ", e);
        }
        Global_Message = MESSAGE;
    }

    public void send_direct_ovapertor(String phone_number, String order_type) {

        Date currentTime = Calendar.getInstance().getTime();
        String date = Utility.getCurrentShamsidate();

        MESSAGE = "";
        String tmp = "";
        tmp = (phone_number.substring(1, 11)) + (order_type);
       /* tmp += codeAndDecode.truecent_length((String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V1))).replace(".", "")), 3);
        tmp += codeAndDecode.truecent_length((String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V2))).replace(".", "")), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V3), 1);//reset mode
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V4), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V5), 2);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V6), 2);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V7), 2);*/


        //date
        tmp += date.substring(0, 4)
                + date.substring(5, 7)
                + date.substring(8, 10);
        //time
        tmp += codeAndDecode.truecent_length(currentTime.getHours() + "", 2);
        tmp += codeAndDecode.truecent_length(currentTime.getMinutes() + "", 2);


        for (String s : codeAndDecode.split_two_by_two(tmp))
            MESSAGE += codeAndDecode.code_map(s);
        MESSAGE = Config.DIRECT_COMMAND_HEADER + MESSAGE + "#";

        RoomModel room = db.getRoom(Config.currentRoomID);
        String mobile = room.get_number();
        try {
            sendSms(MESSAGE, mobile);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "send_direct_command: ", e);
        }
        Global_Message = MESSAGE;

    }

    public void send_direct_compressor(String phone_number, String order_type) {

        Date currentTime = Calendar.getInstance().getTime();
        String date = Utility.getCurrentShamsidate();

        MESSAGE = "";
        String tmp = "";
        tmp = (phone_number.substring(1, 11)) + (order_type);
/*        tmp += codeAndDecode.truecent_length((String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V1))).replace(".", "")), 3);
        tmp += codeAndDecode.truecent_length((String.format("%.1f", Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V2))).replace(".", "")), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V3), 1);//reset
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V4), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V5), 2);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V6), 2);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V7), 2);*/


        //date
        tmp += date.substring(0, 4)
                + date.substring(5, 7)
                + date.substring(8, 10);
        //time
        tmp += codeAndDecode.truecent_length(currentTime.getHours() + "", 2);
        tmp += codeAndDecode.truecent_length(currentTime.getMinutes() + "", 2);


        for (String s : codeAndDecode.split_two_by_two(tmp))
            MESSAGE += codeAndDecode.code_map(s);
        MESSAGE = Config.DIRECT_COMMAND_HEADER + MESSAGE + "#";

        RoomModel room = db.getRoom(Config.currentRoomID);
        String mobile = room.get_number();
        try {
            sendSms(MESSAGE, mobile);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "send_direct_command: ", e);
        }
        Global_Message = MESSAGE;

    }

    public void send_direct_highgas(String phone_number, String order_type) {

        Date currentTime = Calendar.getInstance().getTime();
        String date = Utility.getCurrentShamsidate();
        MESSAGE = "";
        String tmp = "";
        tmp = (phone_number.substring(1, 11)) + (order_type);
       /* tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V1), 1);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V2), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V3), 2);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V4), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V5), 2);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V6), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V7), 3);*/


        //date
        tmp += date.substring(0, 4)
                + date.substring(5, 7)
                + date.substring(8, 10);
        //time
        tmp += codeAndDecode.truecent_length(currentTime.getHours() + "", 2);
        tmp += codeAndDecode.truecent_length(currentTime.getMinutes() + "", 2);

        for (String s : codeAndDecode.split_two_by_two(tmp))
            MESSAGE += codeAndDecode.code_map(s);
        MESSAGE = Config.DIRECT_COMMAND_HEADER + MESSAGE + "#";

        RoomModel room = db.getRoom(Config.currentRoomID);
        String mobile = room.get_number();
        try {
            sendSms(MESSAGE, mobile);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "send_direct_command: ", e);
        }
        Global_Message = MESSAGE;

    }

    public void send_direct_lowgas(String phone_number, String order_type) {
        Date currentTime = Calendar.getInstance().getTime();
        String date = Utility.getCurrentShamsidate();
        MESSAGE = "";
        String tmp = "";
        tmp = (phone_number.substring(1, 11)) + (order_type);
       /* tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V1), 1);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V2), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V3), 2);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V4), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V5), 2);*/

        //date
        tmp += date.substring(0, 4)
                + date.substring(5, 7)
                + date.substring(8, 10);
        //time
        tmp += codeAndDecode.truecent_length(currentTime.getHours() + "", 2);
        tmp += codeAndDecode.truecent_length(currentTime.getMinutes() + "", 2);

        for (String s : codeAndDecode.split_two_by_two(tmp))
            MESSAGE += codeAndDecode.code_map(s);
        MESSAGE = Config.DIRECT_COMMAND_HEADER + MESSAGE + "#";

        RoomModel room = db.getRoom(Config.currentRoomID);
        String mobile = room.get_number();
        try {
            sendSms(MESSAGE, mobile);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "send_direct_command: ", e);
        }
        Global_Message = MESSAGE;

    }

    public void send_direct_oil(String phone_number, String order_type) {

        Date currentTime = Calendar.getInstance().getTime();
        String date = Utility.getCurrentShamsidate();
        MESSAGE = "";
        String tmp = "";
        tmp = (phone_number.substring(1, 11)) + (order_type);
       /* tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V1), 1);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V2), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V3), 2);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V4), 3);
        tmp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V5), 2);*/


        //date
        tmp += date.substring(0, 4)
                + date.substring(5, 7)
                + date.substring(8, 10);
        //time
        tmp += codeAndDecode.truecent_length(currentTime.getHours() + "", 2);
        tmp += codeAndDecode.truecent_length(currentTime.getMinutes() + "", 2);

        for (String s : codeAndDecode.split_two_by_two(tmp))
            MESSAGE += codeAndDecode.code_map(s);
        MESSAGE = Config.DIRECT_COMMAND_HEADER + MESSAGE + "#";

        RoomModel room = db.getRoom(Config.currentRoomID);
        String mobile = room.get_number();
        try {
            sendSms(MESSAGE, mobile);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "send_direct_command: ", e);
        }
        Global_Message = MESSAGE;

    }

    public void getInformation(String data) {
        if (data.length() > 0) {
            String header = "";
            String footer = "";

            header = data.substring(0, 2);
            footer = data.substring(data.length() - 1, data.length());


            // FIXME: 3/17/2018 response of registry sms
            if (header.equals("#I")) {
                data = data.substring(2, data.length() - 1);
                data = codeAndDecode.decompress(data);
                //دیفراست حذف شد ☻ و 48 تا از همه کم شد ☻
                int nothing = 48;

                Config.INFORMATION_LAT = data.substring(0, 8);
                Config.INFORMATION_LONG = data.substring(8, 16);
                Config.INFORMATION_YEAR = data.substring(16, 20);
                Config.INFORMATION_MONTH = data.substring(20, 22);
                Config.INFORMATION_DAY = data.substring(22, 24);
                Config.INFORMATION_HOUR = data.substring(24, 26);
                Config.INFORMATION_MINUTE = data.substring(26, 28);
                Config.INFORMATION_ERRORTYPE = data.substring(28, 30);
                Config.INFORMATION_WORKMODE = data.substring(30, 31);
                Config.INFORMATION_TEMPRETUREV1 = codeAndDecode.ewq_float(data.substring(31, 35)) + "";
                Config.INFORMATION_WETV1 = codeAndDecode.ewq_float(data.substring(35, 39)) + "";
                Config.INFORMATION_DEFROSTTIME = data.substring(39, 43);
//                Config.INFORMATION_DEFROST = codeAndDecode.defrostToBin(data.substring(43, 91));
                Config.INFORMATION_RTON = data.substring(91 - nothing, 94 - nothing);
                Config.INFORMATION_STON = data.substring(94 - nothing, 97 - nothing);
                Config.INFORMATION_TTON = data.substring(97 - nothing, 100 - nothing);
                Config.INFORMATION_CURRENTCOMPRESSOR_R = new StringBuffer(data.substring(100 - nothing, 103 - nothing)).insert(2, '.').toString();
                Config.INFORMATION_CURRENTCOMPRESSOR_S = new StringBuffer(data.substring(103 - nothing, 106 - nothing)).insert(2, '.').toString();
                Config.INFORMATION_CURRENTCOMPRESSOR_T = new StringBuffer(data.substring(106 - nothing, 109 - nothing)).insert(2, '.').toString();
                Config.INFORMATION_CURRENTOVAPERATOR_R = new StringBuffer(data.substring(109 - nothing, 112 - nothing)).insert(2, '.').toString();
                Config.INFORMATION_CURRENTOVAPERATOR_S = new StringBuffer(data.substring(112 - nothing, 115 - nothing)).insert(2, '.').toString();
                Config.INFORMATION_CURRENTOVAPERATOR_T = new StringBuffer(data.substring(115 - nothing, 118 - nothing)).insert(2, '.').toString();
                Config.INFORMATION_HIGHGAS = data.substring(118 - nothing, 121 - nothing);
                Config.INFORMATION_LOWHGAS = data.substring(121 - nothing, 124 - nothing);
                Config.INFORMATION_OIL = data.substring(124 - nothing, 127 - nothing);

            } else {
                if (data.equals(db.getFirstUser_for_room(Config.currentRoomID).get_mobile())) {
                    // TODO: 3/17/2018 OK
                }
            }
        }
    }

    public String get_update() {

        HEADER = "";
        MESSAGE = "";

        RoomModel room = db.getRoom(Config.currentRoomID);
        String mobile = room.get_number();

        String date = Utility.getCurrentShamsidate();
        Date currentTime = Calendar.getInstance().getTime();

        temp =
                db.getFirstUser_for_room(Config.currentRoomID).get_mobile().substring(1, 11)
                        + date.substring(0, 4)
                        + date.substring(5, 7)
                        + date.substring(8, 10)
                        + codeAndDecode.truecent_length(currentTime.getHours() + "", 2)
                        + codeAndDecode.truecent_length(currentTime.getMinutes() + "", 2);

        temp += HEADER;

        /*//Temperature OK
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_float(Float.parseFloat(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V1)), 3), 4);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V2), 1);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V3))), 1);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V5))), 2);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V6))), 2);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V4))), 2);

        //Humour OK
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V1))), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_HUMOUR_V2), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V3)) * -1), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V5))), 2);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V6)) * -1), 2);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V4))), 2);

        //Defrost OK
        temp += codeAndDecode.defrostToDec(Config.general_data_setting.get(Config.KEY_DEFROST_V1));
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_DEFROST_V2), 2);

        //Voltage OK
        temp += Config.general_data_setting.get(Config.KEY_VOLTAGE_V1); // onePhase and threePhase
        temp += String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2)));
        temp += String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3)));
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)))), 3);
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)))), 2);
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)))), 2);

        //Current OK
        temp += codeAndDecode.truecent_length((String.valueOf(Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V1))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length((String.valueOf(Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V2))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V3), 1);//reset mode
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V6), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V7), 2);

        //Current OK
        temp += codeAndDecode.truecent_length((String.valueOf(Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V1))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length((String.valueOf(Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V2))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V3), 1);//reset
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V6), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V7), 2);

        //pressure
        temp += Config.general_data_setting.get(Config.KEY_PRESSURE_V1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_V2), 2);

        //Pressure High Gas OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V6), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V7), 3);

        //Pressure Low Gas OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V5), 2);

        //Pressure Oil OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V5), 2);
*/

        //Temperature OK
        temp += codeAndDecode.truecent_length((codeAndDecode.qwe_float(Float.parseFloat(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V1)), 3)), 4);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V2), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V3)) * -1), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V5))), 2);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V6)) * -1), 2);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TEMPERATURE_V4))), 2);

        //Humour OK
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V1))), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_HUMOUR_V2), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V3)) * -1), 1);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V5))), 2);
        temp += codeAndDecode.truecent_length(String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V6)) * -1), 2);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_HUMOUR_V4))), 2);


        //Defrost OK
        temp += codeAndDecode.defrostToDec(Config.general_data_setting.get(Config.KEY_DEFROST_V1));
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_DEFROST_V2), 2);

        //Voltage OK
        temp += Config.general_data_setting.get(Config.KEY_VOLTAGE_V1); // onePhase and threePhase
        temp += String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V2)));
        temp += String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V3)));
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V4)))), 3);
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V5)))), 2);
        temp += codeAndDecode.truecent_length((String.valueOf(Integer.parseInt(Config.general_data_setting.get(Config.KEY_VOLTAGE_V6)))), 2);

        //Current OK
        temp += codeAndDecode.truecent_length((String.valueOf(Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V1))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length((String.valueOf(Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V2))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V3), 1);//reset mode
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V6), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_OVAPERATOR_V7), 2);

        //Current OK
        temp += codeAndDecode.truecent_length((String.valueOf(Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V1))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length((String.valueOf(Float.parseFloat(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V2))).replace(".", "")), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V3), 1);//reset
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V6), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_CURRENT_COMPRESOR_V7), 2);

        //pressure
        temp += Config.general_data_setting.get(Config.KEY_PRESSURE_V1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_V2), 2);

        //Pressure High Gas OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V5), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V6), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_HIGH_GAS_V7), 3);

        //Pressure Low Gas OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_LOW_GAS_V5), 2);

        //Pressure Oil OK
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V1), 1);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V2), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V3), 2);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V4), 3);
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_PRESSURE_OIL_V5), 2);


        //TermoDisk
        temp += codeAndDecode.truecent_length(Config.general_data_setting.get(Config.KEY_TERMODISK_V1), 1);
        temp += codeAndDecode.truecent_length(codeAndDecode.qwe_integer(Integer.parseInt(Config.general_data_setting.get(Config.KEY_TERMODISK_V2))), 3);


        if (temp.length() % 2 != 0)
            temp += "0";

        Log.d(getClass().getName(), "send_total_data: " + temp);


        for (String s : codeAndDecode.split_two_by_two(temp))
            MESSAGE += codeAndDecode.code_map(s);

        MESSAGE = "#U" + MESSAGE + "#";

        sendSms(MESSAGE, room.get_number());

        //wrapping with signs
        return MESSAGE;
    }

    public void get_Setting(String Setting_msg) {
        String decompressed = "";
        if (Setting_msg.substring(0, 2).equals("#S")) {

            Setting_msg = Setting_msg.substring(0, 2) + Setting_msg.substring(5, Setting_msg.length());
            SmsCodeAndDecode smsCodeAndDecode = new SmsCodeAndDecode();
            decompressed = codeAndDecode.decompress(Setting_msg.substring(2, Setting_msg.length() - 1));
            if (/*decompressed.length() == 189*/true) {
                decompressed = "paddd" + decompressed;
                //data recived in good format
                //#S 0-2
                // 2-5 version code
                // number 5 - 15
                //year 15 - 19
                //month 19 - 21
                //day 21 - 23
                //hour 23 - 25
                //minute 25 - 27

                //temperature
                Integer ppp = 27;
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V1, "" + smsCodeAndDecode.ewq_float(decompressed.substring(ppp, (ppp += 4))));
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V2, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V3, "-" + (decompressed.substring(ppp, (ppp += 1))));
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V5, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V6, "-" + (decompressed.substring(ppp, (ppp += 2))));
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V4, "" + smsCodeAndDecode.ewq_integer(decompressed.substring(ppp, (ppp += 2))));
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V7, "-");
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V8, "-");
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V9, "-");
                Config.general_data_setting.put(Config.KEY_TEMPERATURE_V10, "-");

                //humor
                Config.general_data_setting.put(Config.KEY_HUMOUR_V1, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_HUMOUR_V2, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_HUMOUR_V3, "-" + (decompressed.substring(ppp, (ppp += 1))));
                Config.general_data_setting.put(Config.KEY_HUMOUR_V5, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_HUMOUR_V6, "-" + (decompressed.substring(ppp, (ppp += 2))));
                Config.general_data_setting.put(Config.KEY_HUMOUR_V4, "" + smsCodeAndDecode.ewq_integer(decompressed.substring(ppp, (ppp += 2))));
                Config.general_data_setting.put(Config.KEY_HUMOUR_V7, "-");
                Config.general_data_setting.put(Config.KEY_HUMOUR_V8, "-");
                Config.general_data_setting.put(Config.KEY_HUMOUR_V9, "-");
                Config.general_data_setting.put(Config.KEY_HUMOUR_V10, "-");


                //defrost
                Config.general_data_setting.put(Config.KEY_DEFROST_V1, codeAndDecode.defrostToBin(decompressed.substring(ppp, (ppp += 48))));
                Config.general_data_setting.put(Config.KEY_DEFROST_V2, decompressed.substring(ppp, (ppp += 2)));

                //دیفراست مربوط به اطلاهات را پر می کند - در اطلاعات دیفراست دیگر ارسال نمی شود.☺
                Config.INFORMATION_DEFROST = (Config.general_data_setting.get(Config.KEY_DEFROST_V1));


                //phase for information that load from setting

                Config.general_data_setting.put(Config.INFORMATION_PHASE_COUNT, decompressed.substring(ppp, ppp + 1));

                //voltage

                Config.general_data_setting.put(Config.KEY_VOLTAGE_V1, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V2, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V3, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V4, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V5, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V6, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V7, "-");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V8, "-");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V9, "-");
                Config.general_data_setting.put(Config.KEY_VOLTAGE_V10, "-");


                //ovaperator                insert float dot in number for v1 and v2 in  offset 2
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V1, (new StringBuffer(decompressed.substring(ppp, (ppp += 3)))).insert(2, ".").toString());
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V2, (new StringBuffer(decompressed.substring(ppp, (ppp += 3)))).insert(2, ".").toString());
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V3, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V4, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V5, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V6, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V7, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V8, "0");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V9, "0");
                Config.general_data_setting.put(Config.KEY_CURRENT_OVAPERATOR_V10, "0");


                //Compresor
                Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V1, (new StringBuffer(decompressed.substring(ppp, (ppp += 3)))).insert(2, ".").toString());
                Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V2, (new StringBuffer(decompressed.substring(ppp, (ppp += 3)))).insert(2, ".").toString());
                Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V3, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V4, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V5, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V6, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_CURRENT_COMPRESOR_V7, decompressed.substring(ppp, (ppp += 2)));

                //pressure
                Config.general_data_setting.put(Config.KEY_PRESSURE_V1, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_V2, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_V3, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_V4, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_V5, "0");

                //high gas
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V1, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V2, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V3, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V4, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V5, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V6, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V7, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V8, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V9, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_HIGH_GAS_V10, "0");

                //low gas
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V1, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V2, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V3, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V4, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V5, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V6, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V7, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V8, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V9, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_LOW_GAS_V10, "0");

                //oil
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V1, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V2, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V3, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V4, decompressed.substring(ppp, (ppp += 3)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V5, decompressed.substring(ppp, (ppp += 2)));
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V6, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V7, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V8, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V9, "0");
                Config.general_data_setting.put(Config.KEY_PRESSURE_OIL_V10, "0");


                Config.general_data_setting.put(Config.KEY_TERMODISK_V1, decompressed.substring(ppp, (ppp += 1)));
                Config.general_data_setting.put(Config.KEY_TERMODISK_V2, "" + smsCodeAndDecode.ewq_integer(String.valueOf(Integer.parseInt(decompressed.substring(ppp, (ppp += 3))))));

                for (String resu : Config.general_data_setting.keySet()) {
                    Log.e(TAG, "getSetting() ALL Setting: " + resu + " : " + Config.general_data_setting.get(resu));
                }


            } else {
                //bad message
            }
        }
    }

    public void sendSms(String messageContent, final String room_number) {

        String DELIVERED = "SMS_DELIVERED";
        _context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        if (!Telephony.Sms.getDefaultSmsPackage(_context).equals(_context.getPackageName())) {
                            removesentMessage(_context, room_number);
                        }
                    } else {

                    }
                } catch (Exception e) {
                    Log.e(TAG, "onReceive: ", e);
                }

                switch (getResultCode()) {
                    case Activity.RESULT_OK: {
//                        Toast.makeText(_context, "پیام ارسال شد.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(_context, Splash.class);
                        PendingIntent pIntent = PendingIntent.getActivity(_context, (int) System.currentTimeMillis(), intent, 0);

                        Notification n = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            //yes intent
                            Intent yesReceive = new Intent();
                            yesReceive.setAction(Config.YES_ACTION);
                            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(_context, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
                            //no intent
                            Intent yesReceive2 = new Intent();
                            yesReceive2.setAction(Config.STOP_ACTION);
                            PendingIntent pendingIntentno = PendingIntent.getBroadcast(_context, 12345, yesReceive2, PendingIntent.FLAG_CANCEL_CURRENT);


                            n = new Notification.Builder(_context)
                                    .setContentTitle("موفق")
                                    .setContentText("پیام با موفقیت به سردخانه ارسال شد.")
                                    .setSmallIcon(R.drawable.splogo1)
                                    .setContentIntent(pIntent)
                                    .setAutoCancel(true)
                                    .addAction(R.drawable.ic_eye, "باز کردن برنامه", pendingIntentYes)
                                    .addAction(R.drawable.ic_close_black_24dp, "حذف این پیام", pendingIntentno)
                                    .build();

                        }


                        NotificationManager notificationManager =
                                (NotificationManager) _context.getSystemService(NOTIFICATION_SERVICE);

                        notificationManager.notify(1, n);


                        String unique_key = db.getLastUniqueKey();
                        if (unique_key != null) {
                            db.RoomSmsDelivery(unique_key);

                        }

                        break;
                    }
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(_context, "پیام ارسال نشد.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(_context, Splash.class);
                        PendingIntent pIntent = PendingIntent.getActivity(_context, (int) System.currentTimeMillis(), intent, 0);

                        Notification n = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            //yes intent
                            Intent yesReceive = new Intent();
                            yesReceive.setAction(Config.YES_ACTION);
                            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(_context, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
                            //no intent
                            Intent yesReceive2 = new Intent();
                            yesReceive2.setAction(Config.STOP_ACTION);
                            PendingIntent pendingIntentno = PendingIntent.getBroadcast(_context, 12345, yesReceive2, PendingIntent.FLAG_CANCEL_CURRENT);


                            n = new Notification.Builder(_context)
                                    .setContentTitle("ناموفق")
                                    .setContentText("پیام به سردخانه ارسال نشد.")
                                    .setSmallIcon(R.drawable.splogo1)
                                    .setContentIntent(pIntent)
                                    .setAutoCancel(true)
                                    .addAction(R.drawable.ic_eye, "باز کردن برنامه", pendingIntentYes)
                                    .addAction(R.drawable.ic_close_black_24dp, "حذف این پیام", pendingIntentno)
                                    .build();

                        }


                        NotificationManager notificationManager =
                                (NotificationManager) _context.getSystemService(NOTIFICATION_SERVICE);

                        notificationManager.notify(1, n);
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        String SENT = "SMS_SENT";

        PendingIntent sentPI = PendingIntent.getBroadcast(_context, 0, new Intent(SENT), 0);

        _context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                int resultCode = getResultCode();
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        if (!FLAG_TOAST) {
                            Toast.makeText(_context, "در حال بررسی", Toast.LENGTH_LONG).show();
                            Toast.makeText(_context, "پیام ارسال شد.", Toast.LENGTH_LONG).show();
                            makeNotification("موفق", "پیام شما با موفقیت ارسال شد.");
                            FLAG_TOAST = true;
                        }
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        if (!FLAG_TOAST) {
                            makeNotification("خطا - پیام ارسال نشد.", "ممکن است شارژ پولی شما به اتمام رسیده باشد.");
                            FLAG_TOAST = true;
                        }
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        makeNotification("خطا - شبکه در دسترس نیست", "شما در محلی هستید که سیم کارت شما از شبکه خارج است.");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        if (!FLAG_TOAST) {
                            Toast.makeText(_context, "شبکه در دسترس نیست !", Toast.LENGTH_LONG).show();
                            makeNotification("خطا - شبکه در دسترس نیست", "شما در محلی هستید که سیم کارت شما از شبکه خارج است.");
                            FLAG_TOAST = true;
                        }
                        break;
                }
            }
        }, new IntentFilter(SENT));


        PendingIntent deliveredPI = PendingIntent.getBroadcast(_context, 0, new Intent(DELIVERED), 0);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(room_number, null, messageContent, sentPI, deliveredPI);

        FLAG_TOAST = false;
    }

    private void removesentMessage(Context context, String fromAddress) {

        Uri uriSMS = Uri.parse("content://sms/");
        Cursor cursor = context.getContentResolver().query(uriSMS, null,
                null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            int ThreadId = cursor.getInt(1);
            Log.d("Thread Id", ThreadId + " id - " + cursor.getInt(0));
            Log.d("contact number", cursor.getString(2));
            Log.d("column name", cursor.getColumnName(2));

            context.getContentResolver().delete(Uri.
                            parse("content://sms/" + ThreadId), "address=?",
                    new String[]{fromAddress});
            Log.d("Message Thread Deleted", fromAddress);
        }
        cursor.close();
    }

    public void makeNotification(String title, String message) {
        Intent intent = new Intent(_context, Splash.class);
        PendingIntent pIntent = PendingIntent.getActivity(_context, (int) System.currentTimeMillis(), intent, 0);
        Notification n = null;
        //yes intent
        Intent yesReceive = new Intent();
        yesReceive.setAction(Config.YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(_context, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        //no intent
        Intent yesReceive2 = new Intent();
        yesReceive2.setAction(Config.STOP_ACTION);
        PendingIntent pendingIntentno = PendingIntent.getBroadcast(_context, 12345, yesReceive2, PendingIntent.FLAG_CANCEL_CURRENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = new Notification.Builder(_context)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.splogo1)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_eye, "باز کردن برنامه", pendingIntentYes)
                    .addAction(R.drawable.ic_close_black_24dp, "حذف این پیام", pendingIntentno)
                    .build();
        }
        NotificationManager notificationManager =
                (NotificationManager) _context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1, n);

    }


}


