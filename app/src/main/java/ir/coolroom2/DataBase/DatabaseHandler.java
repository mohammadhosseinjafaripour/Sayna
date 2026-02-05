package ir.coolroom2.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ir.coolroom2.Config;
import ir.coolroom2.Model.ReportModel;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.SettingModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.Sms.SmsCodeAndDecode;
import ir.coolroom2.Utils.Utility;

/**
 * Created by Mohammad on 8/27/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db";

    // Questions table name
    private static final String TABLE_ORDER = "myorder";
    private static final String TABLE_MANUAL_DEFROST = "manual_defrost";
    private static final String TEMP_TABLE_ORDER = "temp_myorder";
    private static final String TABLE_ROOM = "room";
    private static final String TABLE_USER = "user";
    private static final String TABLE_APPLICATION_SETTING = "application_setting";

    // Common column names
    private static final String KEY_ID = "id";

    // log Table Columns names
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATE = "state";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_REPORT_FROM = "report_from";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_CURRENT_ROOM = "current_room";

    private static final String KEY_TEMPERATURE_V1 = "temperature_V1";
    private static final String KEY_TEMPERATURE_V2 = "temperature_V2";
    private static final String KEY_TEMPERATURE_V3 = "temperature_V3";
    private static final String KEY_TEMPERATURE_V4 = "temperature_V4";
    private static final String KEY_TEMPERATURE_V5 = "temperature_V5";
    private static final String KEY_TEMPERATURE_V6 = "temperature_V6";
    private static final String KEY_TEMPERATURE_V7 = "temperature_V7";
    private static final String KEY_TEMPERATURE_V8 = "temperature_V8";
    private static final String KEY_TEMPERATURE_V9 = "temperature_V9";
    private static final String KEY_TEMPERATURE_V10 = "temperature_V10";


    private static final String KEY_HUMOUR_V1 = "humour_V1";
    private static final String KEY_HUMOUR_V2 = "humour_V2";
    private static final String KEY_HUMOUR_V3 = "humour_V3";
    private static final String KEY_HUMOUR_V4 = "humour_V4";
    private static final String KEY_HUMOUR_V5 = "humour_V5";
    private static final String KEY_HUMOUR_V6 = "humour_V6";
    private static final String KEY_HUMOUR_V7 = "humour_V7";
    private static final String KEY_HUMOUR_V8 = "humour_V8";
    private static final String KEY_HUMOUR_V9 = "humour_V9";
    private static final String KEY_HUMOUR_V10 = "humour_V10";

    private static final String KEY_DEFROST_V1 = "defrost_V1";

    private static final String KEY_VOLTAGE_V1 = "voltage_V1";
    private static final String KEY_VOLTAGE_V2 = "voltage_V2";
    private static final String KEY_VOLTAGE_V3 = "voltage_V3";
    private static final String KEY_VOLTAGE_V4 = "voltage_V4";
    private static final String KEY_VOLTAGE_V5 = "voltage_V5";
    private static final String KEY_VOLTAGE_V6 = "voltage_V6";
    private static final String KEY_VOLTAGE_V7 = "voltage_V7";
    private static final String KEY_VOLTAGE_V8 = "voltage_V8";
    private static final String KEY_VOLTAGE_V9 = "voltage_V9";
    private static final String KEY_VOLTAGE_V10 = "voltage_V10";

    private static final String KEY_CURRENT_V1 = "current_V1";
    private static final String KEY_CURRENT_V2 = "current_V2";
    private static final String KEY_CURRENT_V3 = "current_V3";
    private static final String KEY_CURRENT_V4 = "current_V4";
    private static final String KEY_CURRENT_V5 = "current_V5";
    private static final String KEY_CURRENT_V6 = "current_V6";
    private static final String KEY_CURRENT_V7 = "current_V7";
    private static final String KEY_CURRENT_V8 = "current_V8";
    private static final String KEY_CURRENT_V9 = "current_V9";
    private static final String KEY_CURRENT_V10 = "current_V10";
    private static final String KEY_CURRENT_V11 = "current_V11";
    private static final String KEY_CURRENT_V12 = "current_V12";
    private static final String KEY_CURRENT_V13 = "current_V13";
    private static final String KEY_CURRENT_V14 = "current_V14";
    private static final String KEY_CURRENT_V15 = "current_V15";
    private static final String KEY_CURRENT_V16 = "current_V16";
    private static final String KEY_CURRENT_V17 = "current_V17";
    private static final String KEY_CURRENT_V18 = "current_V18";
    private static final String KEY_CURRENT_V19 = "current_V19";
    private static final String KEY_CURRENT_V20 = "current_V20";

    private static final String KEY_PRESSURE_V1 = "pressure_V1";
    private static final String KEY_PRESSURE_V2 = "pressure_V2";
    private static final String KEY_PRESSURE_V3 = "pressure_V3";
    private static final String KEY_PRESSURE_V4 = "pressure_V4";
    private static final String KEY_PRESSURE_V5 = "pressure_V5";
    private static final String KEY_PRESSURE_V6 = "pressure_V6";
    private static final String KEY_PRESSURE_V7 = "pressure_V7";
    private static final String KEY_PRESSURE_V8 = "pressure_V8";
    private static final String KEY_PRESSURE_V9 = "pressure_V9";
    private static final String KEY_PRESSURE_V10 = "pressure_V10";

    private static final String KEY_TIMER = "timer";


    private static final String KEY_SIGNAL_V1 = "key_signal_v1";
    private static final String KEY_SIGNAL_V2 = "key_signal_v2";
    private static final String KEY_SIGNAL_V3 = "key_signal_v3";
    private static final String KEY_SIGNAL_V4 = "key_signal_v4";
    private static final String KEY_SIGNAL_V5 = "key_signal_v5";
    private static final String KEY_SIGNAL_V6 = "key_signal_v6";
    private static final String KEY_SIGNAL_V7 = "key_signal_v7";
    private static final String KEY_SIGNAL_V8 = "key_signal_v8";
    private static final String KEY_SIGNAL_V9 = "key_signal_v9";
    private static final String KEY_SIGNAL_V10 = "key_signal_v10";


    private static final String KEY_SIGNAL_ = "timer";


    private static final String KEY_UNIQUE = "key";
    private static final String KEY_DELIVERY = "key_delivery";


    // log Table Columns names


    private static final String KEY_FAMIY = "family";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_NAME = "name";
    private static final String KEY_WIDTH = "width";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_LENGTH = "length";
    private static final String KEY_THICKNESS = "thickness";
    private static final String KEY_COMPRESSOR = "compressor";
    private static final String KEY_CONDONSOR = "condonsor";
    private static final String KEY_OVAPERATOR = "ovaperator";
    private static final String KEY_PRODUCT_TYPE = "type";
    private static final String KEY_UNDER_ZERO = "under_zero";
    private static String KEY_STATUS = "key_status";

    private static String KEY_TYPE = "log_type";

    private static final String KEY_PERMISION_LEVEL = "permision_level";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ROOM_ID = "room_id";
    private static final String KEY_TIME_LOG = "key_time_log";

    /*user_table*/

    //// TODO: 12/11/2017  my user table

    private static String KEY_SETTING_LOCK_PATTERN = "setting_lock_pattern";
    private static String KEY_SETTING_ONE = "setting_one";
    private static final String CREATE_TABLE_APPLICATION_SETTING = "CREATE TABLE " + TABLE_APPLICATION_SETTING + " ( "
            + KEY_SETTING_LOCK_PATTERN + " TEXT ,"
            + KEY_SETTING_ONE + " TEXT ); ";

    private static final String CREATE_LOG_MANUAL_DEFROST = "CREATE TABLE " + TABLE_MANUAL_DEFROST + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY ,"
            + KEY_MESSAGE + " TEXT ,"
            + Config.KEY_DEFROST_V2 + " TEXT ,"
            + KEY_STATE + " TEXT ,"
            + KEY_NUMBER + " TEXT ,"
            + KEY_DATE + " DATE ,"
            + KEY_REPORT_FROM + " TEXT ,"
            + KEY_RESPONSE + " TEXT ,"
            + KEY_CURRENT_ROOM + " BOOL )";

    private static final String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_ORDER + "("
            + KEY_ID + " INTEGER PRIMARY KEY ,"
            + KEY_TYPE + " TEXT ,"
            + KEY_STATE + " TEXT ,"
            + KEY_NUMBER + " TEXT ,"
            + KEY_DATE + " DATE ,"
            + KEY_REPORT_FROM + " TEXT ,"
            + KEY_MESSAGE + " TEXT ,"
            + KEY_RESPONSE + " TEXT ,"
            + KEY_USER_ID + " TEXT ,"
            + KEY_ROOM_ID + " TEXT ,"

            + KEY_TEMPERATURE_V1 + " TEXT ,"
            + KEY_TEMPERATURE_V2 + " TEXT ,"
            + KEY_TEMPERATURE_V3 + " TEXT ,"
            + KEY_TEMPERATURE_V4 + " TEXT ,"
            + KEY_TEMPERATURE_V5 + " TEXT ,"
            + KEY_TEMPERATURE_V6 + " TEXT ,"
            + KEY_TEMPERATURE_V7 + " TEXT ,"
            + KEY_TEMPERATURE_V8 + " TEXT ,"
            + KEY_TEMPERATURE_V9 + " TEXT ,"
            + KEY_TEMPERATURE_V10 + " TEXT ,"

            + KEY_HUMOUR_V1 + " TEXT ,"
            + KEY_HUMOUR_V2 + " TEXT ,"
            + KEY_HUMOUR_V3 + " TEXT ,"
            + KEY_HUMOUR_V4 + " TEXT ,"
            + KEY_HUMOUR_V5 + " TEXT ,"
            + KEY_HUMOUR_V6 + " TEXT ,"
            + KEY_HUMOUR_V7 + " TEXT ,"
            + KEY_HUMOUR_V8 + " TEXT ,"
            + KEY_HUMOUR_V9 + " TEXT ,"
            + KEY_HUMOUR_V10 + " TEXT ,"

            + KEY_DEFROST_V1 + " TEXT ,"
            + Config.KEY_DEFROST_V2 + " TEXT ,"

            + KEY_VOLTAGE_V1 + " TEXT ,"
            + KEY_VOLTAGE_V2 + " TEXT ,"
            + KEY_VOLTAGE_V3 + " TEXT ,"
            + KEY_VOLTAGE_V4 + " TEXT ,"
            + KEY_VOLTAGE_V5 + " TEXT ,"
            + KEY_VOLTAGE_V6 + " TEXT ,"
            + KEY_VOLTAGE_V7 + " TEXT ,"
            + KEY_VOLTAGE_V8 + " TEXT ,"
            + KEY_VOLTAGE_V9 + " TEXT ,"
            + KEY_VOLTAGE_V10 + " TEXT ,"

            + KEY_CURRENT_V1 + " TEXT ,"
            + KEY_CURRENT_V2 + " TEXT ,"
            + KEY_CURRENT_V3 + " TEXT ,"
            + KEY_CURRENT_V4 + " TEXT ,"
            + KEY_CURRENT_V5 + " TEXT ,"
            + KEY_CURRENT_V6 + " TEXT ,"
            + KEY_CURRENT_V7 + " TEXT ,"
            + KEY_CURRENT_V8 + " TEXT ,"
            + KEY_CURRENT_V9 + " TEXT ,"
            + KEY_CURRENT_V10 + " TEXT ,"
            + KEY_CURRENT_V11 + " TEXT ,"
            + KEY_CURRENT_V12 + " TEXT ,"
            + KEY_CURRENT_V13 + " TEXT ,"
            + KEY_CURRENT_V14 + " TEXT ,"
            + KEY_CURRENT_V15 + " TEXT ,"
            + KEY_CURRENT_V16 + " TEXT ,"
            + KEY_CURRENT_V17 + " TEXT ,"
            + KEY_CURRENT_V18 + " TEXT ,"
            + KEY_CURRENT_V19 + " TEXT ,"
            + KEY_CURRENT_V20 + " TEXT ,"


            + KEY_PRESSURE_V1 + " TEXT ,"
            + KEY_PRESSURE_V2 + " TEXT ,"
            + KEY_PRESSURE_V3 + " TEXT ,"
            + KEY_PRESSURE_V4 + " TEXT ,"
            + KEY_PRESSURE_V5 + " TEXT ,"
            + KEY_PRESSURE_V6 + " TEXT ,"
            + KEY_PRESSURE_V7 + " TEXT ,"
            + KEY_PRESSURE_V8 + " TEXT ,"
            + KEY_PRESSURE_V9 + " TEXT ,"
            + KEY_PRESSURE_V10 + " TEXT , "

            + Config.KEY_PRESSURE_HIGH_GAS_V1 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V2 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V3 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V4 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V5 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V6 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V7 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V8 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V9 + " TEXT ,"
            + Config.KEY_PRESSURE_HIGH_GAS_V10 + " TEXT ,"

            + Config.KEY_PRESSURE_LOW_GAS_V1 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V2 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V3 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V4 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V5 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V6 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V7 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V8 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V9 + " TEXT ,"
            + Config.KEY_PRESSURE_LOW_GAS_V10 + " TEXT ,"

            + Config.KEY_PRESSURE_OIL_V1 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V2 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V3 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V4 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V5 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V6 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V7 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V8 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V9 + " TEXT ,"
            + Config.KEY_PRESSURE_OIL_V10 + " TEXT ,"

            + KEY_TIMER + " TEXT ,"
            + KEY_UNIQUE + " TEXT ,"
            + KEY_DELIVERY + " TEXT ,"
            + KEY_TIME_LOG + " TEXT )";

    //end table
    private static final String CREATE_ROOM_TABLE = "CREATE TABLE " + TABLE_ROOM + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NUMBER + " TEXT,"
            + KEY_NAME + " TEXT ,"
            + KEY_LOCATION + " TEXT,"
            + KEY_WIDTH + " TEXT ,"
            + KEY_HEIGHT + " TEXT ,"
            + KEY_LENGTH + " TEXT ,"
            + KEY_THICKNESS + " TEXT ,"
            + KEY_COMPRESSOR + " TEXT ,"
            + KEY_OVAPERATOR + " TEXT ,"
            + KEY_CONDONSOR + " TEXT ,"
            + KEY_PRODUCT_TYPE + " TEXT ,"
            + KEY_UNDER_ZERO + " TEXT ,"
            + KEY_DESCRIPTION + " TEXT ,"
            + KEY_STATUS + " TEXT DEFAULT ('KO') ,"
            + KEY_DATE + " TEXT " +
            " )";


    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT ,"
            + KEY_FAMIY + " TEXT ,"
            + KEY_MOBILE + " TEXT, "
            + KEY_PERMISION_LEVEL + " TEXT ,"
            + KEY_ROOM_ID + " INTEGER, "
            + KEY_STATUS + " TEXT DEFAULT ('KO') ,"
            + KEY_DATE + " TEXT " +
            " )";


    //create information table


    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LOG_TABLE);
        db.execSQL(CREATE_LOG_MANUAL_DEFROST);
        db.execSQL(CREATE_ROOM_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TABLE_APPLICATION_SETTING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(" DROP TABLE IF EXISTS myorder" + CREATE_LOG_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS manual_defrost" + CREATE_LOG_MANUAL_DEFROST);
        db.execSQL(" DROP TABLE IF EXISTS room" + CREATE_ROOM_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS user" + CREATE_USER_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS application_setting" + CREATE_TABLE_APPLICATION_SETTING);
        // Create tables again
        onCreate(db);
    }

    public void createOrder(String message, String number, String state, String report_from, String response, HashMap<String, String> data_value, String type) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_DATE, Utility.getCurrentShamsidate());

            long timeInMillis = System.currentTimeMillis();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(timeInMillis);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "HH:mm");
            String time = dateFormat.format(cal1.getTime());


            values.put(KEY_MESSAGE, message);
            values.put(KEY_NUMBER, number);
            values.put(KEY_STATE, state);
            values.put(KEY_TYPE, type);
            values.put(KEY_REPORT_FROM, report_from);
            values.put(KEY_RESPONSE, response);
            values.put(KEY_TIME_LOG, time);

            for (String key : data_value.keySet()) {
                values.put(key, data_value.get(key));
            }

            // Inserting Row
            db.insert(TABLE_ORDER, null, values);
            Log.d("MGH", "package added");
            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("addPackage", e.getMessage() + " error ");
        }
    }

    // with unique_key // TODO: 4/19/2018
    public void createOrder(String message, String number, String state, String report_from, String response, HashMap<String, String> data_value, String type, int user_id, int room_id, String unique_key, String Delivery) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();

            long timeInMillis = System.currentTimeMillis();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(timeInMillis);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "HH:mm");
            String time = dateFormat.format(cal1.getTime());

            ContentValues values = new ContentValues();

            values.put(KEY_DATE, Utility.getCurrentShamsidate());

            values.put(KEY_MESSAGE, message);
            values.put(KEY_NUMBER, number);
            values.put(KEY_STATE, state);
            values.put(KEY_TYPE, type);
            values.put(KEY_REPORT_FROM, report_from);
            values.put(KEY_RESPONSE, response);
            values.put(KEY_ROOM_ID, room_id);
            values.put(KEY_USER_ID, user_id);
            values.put(KEY_UNIQUE, unique_key);
            values.put(KEY_DELIVERY, Delivery);
            values.put(KEY_TIME_LOG, time);


            for (String key : data_value.keySet()) {
                values.put(key, data_value.get(key));
            }

            // Inserting Row
            db.insert(TABLE_ORDER, null, values);
            Log.d("MGH", "package added");
            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("addPackage", e.getMessage() + " error ");
        }
    }

    //  without with unique_key // TODO: 4/19/2018
    public void createOrder(String message, String number, String state, String report_from, String response, HashMap<String, String> data_value, String type, int user_id, int room_id) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put(KEY_DATE, Utility.getCurrentShamsidate());


            long timeInMillis = System.currentTimeMillis();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(timeInMillis);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String time = dateFormat.format(cal1.getTime());


            values.put(KEY_MESSAGE, message);
            values.put(KEY_NUMBER, number);
            values.put(KEY_STATE, state);
            values.put(KEY_TYPE, type);
            values.put(KEY_REPORT_FROM, report_from);
            values.put(KEY_RESPONSE, response);
            values.put(KEY_ROOM_ID, room_id);
            values.put(KEY_USER_ID, user_id);
            values.put(KEY_TIME_LOG, time);


            for (String key : data_value.keySet()) {
                values.put(key, data_value.get(key));
            }

            // Inserting Row
            db.insert(TABLE_ORDER, null, values);
            Log.d("MGH", "package added");
            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("addPackage", e.getMessage() + " error ");
        }
    }

    public void createManualDefrostOrder(String message, String defrost_v2, String number, String state, String date, String report_from, String response, Boolean current_state) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_MESSAGE, message);
            values.put(Config.KEY_DEFROST_V2, defrost_v2);
            values.put(KEY_NUMBER, number);
            values.put(KEY_STATE, state);
            values.put(KEY_DATE, date);
            values.put(KEY_REPORT_FROM, report_from);
            values.put(KEY_RESPONSE, response);
            values.put(KEY_CURRENT_ROOM, current_state);


            // Inserting Row
            db.insert(TABLE_MANUAL_DEFROST, null, values);
            Log.d("SUCCESS", "manual defrost log added");
            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage() + ":(");
        }
    }

    public String getLastDate() {

        String date = "";
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " order by id desc ;";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToNext()) {
                date = cursor.getString(4);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), "getLastDate: ", e);
        }
        return date;
    }

    public String getFirstDate() {

        String date = "";
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " order by id asc ;";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToNext()) {
                date = cursor.getString(4);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), "getLastDate: ", e);
        }
        return date;
    }


    public ArrayList<ReportModel> getLogs(String beginDate, String endDate,
                                          String type,
                                          String state,
                                          String report_from, String number) {
        ArrayList<ReportModel> arrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " WHERE ";
        selectQuery += KEY_DATE + " >= '" + beginDate + "' ";
        selectQuery += " AND " + KEY_DATE + " <= '" + endDate + "' ";
        selectQuery += " AND " + KEY_ROOM_ID + " = '" + Config.currentRoomID + "' ";


        if (!number.matches("") && number.length() > 0) {
            selectQuery += " AND " + KEY_NUMBER + " like '%" + number + "%' ";
        }


        if (!type.matches("") && type.length() > 0) {
            String[] tmp = type.split("-");
            if (tmp.length > 0) {
                selectQuery += " AND (";
                selectQuery += " " + KEY_TYPE + " = '" + tmp[0] + "' ";
                for (int i = 1; i < tmp.length; i++)
                    selectQuery += " OR " + KEY_TYPE + " = '" + tmp[i] + "' ";
                selectQuery += " ) ";
            }
        }
        if (!state.matches("") && state.length() > 0) {
            String[] tmp = state.split("-");
            if (tmp.length > 0) {
                selectQuery += " AND (";
                selectQuery += " " + KEY_STATE + " = '" + tmp[0] + "' ";
                for (int i = 1; i < tmp.length; i++)
                    selectQuery += " OR " + KEY_STATE + " = '" + tmp[i] + "' ";
                selectQuery += " ) ";
            }
        }
        if (!report_from.matches("") && report_from.length() > 0) {
            String[] tmp = report_from.split("-");
            if (tmp.length > 0) {
                selectQuery += " AND (";
                selectQuery += " " + KEY_REPORT_FROM + " = '" + tmp[0] + "' ";
                for (int i = 1; i < tmp.length; i++)
                    selectQuery += " OR " + KEY_REPORT_FROM + " = '" + tmp[i] + "' ";
                selectQuery += " ) ";
            }
        }
        selectQuery += " ;";
        ReportModel reportModel = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                reportModel = new ReportModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(cursor.getColumnIndex("key_time_log")));
                //here changed // TODO: 5/14/2018
                arrayList.add(reportModel);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), "getLastDate: ", e);
        }
        return arrayList;
    }

    public ArrayList<ReportModel> getLogs() {
        ArrayList<ReportModel> arrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " WHERE " + KEY_ROOM_ID + " = " + Config.currentRoomID + " ;";
        ReportModel reportModel = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                int count = cursor.getCount();
                reportModel = new ReportModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(cursor.getColumnIndex("key_time_log"))
                );
                arrayList.add(reportModel);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), "getLastDate: ", e);
        }
        return arrayList;
    }

    public ReportModel getLastLog(int room_id) {
        ReportModel reportModel = null;
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER
                + " WHERE " + KEY_ROOM_ID + " = '" + Config.currentRoomID + "' AND " + KEY_STATE + " = '" + Config._STATE_COMPLETED + "' AND " + KEY_MESSAGE + " LIKE '#I%' ORDER BY " + KEY_ID + " DESC ;";
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    if (cursor.getString(6).length() > 20) {
                        reportModel = new ReportModel(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(cursor.getColumnIndex("key_time_log"))
                        );
                        reportModel.message = cursor.getString(6);
                        break;
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), "getLastDate: ", e);
        }

        return reportModel;
    }


    public String getlastid() {
        String query = "SELECT * FROM " + TABLE_ORDER + " Where id = (SELECT MAX(id) FROM " + TABLE_ORDER + ")";
        String result = "";
        try {
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }

        } catch (Exception e) {
            Log.e(getClass().getName(), "getLastDate: ", e);
        }
        return result;

    }

    public String get_temp_lastid() {
        String query = "SELECT * FROM " + TEMP_TABLE_ORDER + " Where id = (SELECT MAX(id) FROM " + TEMP_TABLE_ORDER + ")";
        String result = "";
        try {
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), "getLastDate: ", e);
        }

        return result;

    }

    public void deleteAll() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_ORDER);
            db.execSQL("delete from " + TABLE_ROOM);
            db.execSQL("delete from " + TABLE_USER);

            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
    }

    public boolean roomExist(String number) {
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_NUMBER + " = '" + number + "' ;";
        boolean flag = false;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                flag = true;
            }
            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return flag;
    }

    public void deleteAlluser_for_cool_room(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_USER + " WHERE " + KEY_ROOM_ID + " = '" + id + "';");
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
    }

    public int createUser(UserModel userModel) {
        int count = 0;
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, userModel.get_name());
            values.put(KEY_PERMISION_LEVEL, userModel.get_permision_level());
            values.put(KEY_ROOM_ID, userModel.get_room_id());
            values.put(KEY_FAMIY, userModel.get_family());
            values.put(KEY_MOBILE, userModel.get_mobile());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            values.put(KEY_DATE, Utility.getCurrentShamsidate() + " " + sdf.format(new Date()));

            String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + KEY_MOBILE + " = '" + userModel.get_mobile() + "' AND " + KEY_ROOM_ID + " = '" + userModel.get_room_id() + "' ;";

            Cursor cursor = db.rawQuery(selectQuery, null);
            count = cursor.getCount();

            if (count == 0) {


                // Inserting Row
                db.insert(TABLE_USER, null, values);
                Log.d("MGH", "user added");
                db.close(); // Closing database connection

            } else {
                Toast.makeText(context, "این کاربر قبلا تعریف شده است.", Toast.LENGTH_SHORT).show();
                db.close(); // Closing database connection
            }
        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
        return count;
    }


    public void updateUser(UserModel newUser) {
        try {

            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, newUser.get_name());
            values.put(KEY_FAMIY, newUser.get_family());
            values.put(KEY_MOBILE, newUser.get_mobile());
            values.put(KEY_PERMISION_LEVEL, newUser.get_permision_level());

            db.update(TABLE_USER, values, " id =  " + newUser.get_id(), null);

            /*db.rawQuery("UPDATE `"+TABLE_USER+"` SET "+
                    KEY_NAME+" = '"+newUser.get_name()+"', "+
                    KEY_FAMIY+" = '"+newUser.get_mobile()+"', "+
                    KEY_MOBILE+" = '"+newUser.get_mobile()+
                    "' WHERE "+KEY_ID+" = "+newUser.get_id()+" ;", null);*/

            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public void activeUser(UserModel newUser) {
        try {

            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, newUser.get_name());
            values.put(KEY_FAMIY, newUser.get_family());
            values.put(KEY_MOBILE, newUser.get_mobile());
            values.put(KEY_PERMISION_LEVEL, newUser.get_permision_level());
            values.put(KEY_STATUS, "OK");

            db.update(TABLE_USER, values, " id =  " + newUser.get_id(), null);

            /*db.rawQuery("UPDATE `"+TABLE_USER+"` SET "+
                    KEY_NAME+" = '"+newUser.get_name()+"', "+
                    KEY_FAMIY+" = '"+newUser.get_mobile()+"', "+
                    KEY_MOBILE+" = '"+newUser.get_mobile()+
                    "' WHERE "+KEY_ID+" = "+newUser.get_id()+" ;", null);*/

            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public UserModel getUser(int id, String status) {
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + KEY_ID + " = '" + id + "' AND " + KEY_STATUS + " = '" + status + "'  ;";
        UserModel user = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                user = new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
            } else {
                Log.d("getUser", "No User Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "getUser: ", e);
        }
        return user;
    }

    public UserModel getUserByNumber(String phone, String status) {
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + KEY_MOBILE + " = '" + phone + "' AND " + KEY_STATUS + " = '" + status + "'  ;";
        UserModel user = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                user = new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(7));
            } else {
                Log.d("getUser", "No User Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "getUser: ", e);
        }
        return user;
    }

    public int getUserIdByPhonenumber(String phonenumber, String status) {
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + KEY_MOBILE + " = '" + phonenumber + "' AND " + KEY_STATUS + " = '" + status + "' ORDER BY " + KEY_MOBILE + " Desc ;";
        int user_id = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                user_id = Integer.parseInt(cursor.getString(0));
            } else {
                Log.d("getUser", "No User Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "getUser: ", e);
        }
        return user_id;
    }

    public List<UserModel> getUsers() {
        List<UserModel> res = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE  " + KEY_ROOM_ID + " = '" + Config.currentRoomID + "' ;"; // remove >>> AND " + KEY_STATUS + " = 'OK'

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            String user = "";
            while (cursor.moveToNext()) {
            /*
            id , name, family, mobile
             */
                res.add(new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6)));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return res;
    }

    public List<UserModel> getLastUsers() {
        List<UserModel> res = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE  " + KEY_ROOM_ID + " = " + "(SELECT MAX(id)  FROM " + TABLE_USER + ")" + " ;";

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            String user = "";
            while (cursor.moveToNext()) {
            /*
            id , name, family, mobile
             */
                res.add(new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return res;
    }

    public void deleteUser(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_USER + " WHERE id = '" + id + "';");
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
    }

    public void deleteUserByRoomId(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_USER + " WHERE " + KEY_ROOM_ID + " = '" + id + "';");
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
    }

    public void deleteUser(String room_number) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_USER + " WHERE mobile = '" + room_number + "' AND " + KEY_ROOM_ID + " = '" + Config.currentRoomID + "' ;");
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
    }

    public void deleteAllUserForRoom(String room_id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_USER + " WHERE " + KEY_ROOM_ID + " = '" + room_id + "' ;");
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
    }


    public UserModel getFirstUser() {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " ORDER BY " + KEY_ID + " ASC LIMIT 1";
        UserModel user = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                user = new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
            } else {
                Log.d("getFirstUser", "No User Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return user;
    }

    public UserModel getFirstUser_for_room(int Room_id) {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_ROOM_ID + "= " + Room_id + " ORDER BY " + KEY_ID + " ASC LIMIT 1";
        UserModel user = null;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                user = new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
            } else {
                Log.d("getFirstUser_for_room", "No User Found !!!");

            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return user;
    }

    public void resetAllRoomStatus() {
        try {

            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_STATUS, "0");

            db.update(TABLE_ROOM, values, " " + KEY_STATUS + " = '0' OR " + KEY_STATUS + " = '1' ", null);

            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public void activeRoomStatus(int room_id) {
        try {

            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            /*String query = "UPDATE room SET key_status = 1 WHERE id = "+room_id;
            db.rawQuery(query, null);*/

            ContentValues values = new ContentValues();
            values.put(KEY_STATUS, "1");
            db.update(TABLE_ROOM, values, KEY_ID + " = " + room_id, null);
            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public RoomModel getLastRoom() {
        String selectQuery = "SELECT * FROM " + TABLE_ROOM + " ORDER BY " + KEY_ID + " DESC LIMIT 1";
        RoomModel room = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                room = new RoomModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        Boolean.parseBoolean(cursor.getString(12)),
                        cursor.getString(13),
                        cursor.getString(14));
            } else {
                Log.d("getLastRoom", "No Room Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return room;
    }

    public RoomModel getSelectedRoom() {
        String selectQuery = "SELECT * FROM " + TABLE_ROOM + " WHERE " + KEY_STATUS + " = '1' ";
        RoomModel room = null;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                room = new RoomModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        Boolean.parseBoolean(cursor.getString(12)),
                        cursor.getString(13),
                        cursor.getString(14));
            } else {
                Log.d("getLastRoom", "No Room Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return room;
    }


    public int createRoom(RoomModel room) {
        int count = 0;
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();

            String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_NUMBER + " = '" + room.get_number() + "' ;";

            String nextSelectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + KEY_STATUS + " = 'KO' ;";

            String lastSelectQuery = "SELECT * FROM " + TABLE_ROOM + " WHERE "
                    + KEY_STATUS + " = '-1' AND " + KEY_NUMBER + " = '" + room.get_number() + "' ;";


            Cursor cursor = db.rawQuery(selectQuery, null);
            Cursor nextCursor = db.rawQuery(nextSelectQuery, null);
            Cursor lastCursor = db.rawQuery(lastSelectQuery, null);

            if ((nextCursor.getCount() > 0) && (lastCursor.getCount() > 0)) {
                nextCursor.moveToFirst();
                Log.d(getClass().getSimpleName(), "createRoom Error ☺: " + nextCursor.getColumnCount());
                if (nextCursor.getString(6).equals("KO")) {
                    String nextRoom_id = nextCursor.getString(0);
                    db.delete(TABLE_USER, " " + KEY_ID + " = '" + nextRoom_id + "' ", null);
                    db.delete(TABLE_ROOM, " " + KEY_ID + " = '" + nextRoom_id + "' ", null);
                    db.delete(TABLE_ORDER, " " + KEY_ROOM_ID + " = '" + nextRoom_id + "' ", null);
                }
            }
            count = cursor.getCount();
            if (count == 0) {
                ContentValues values = new ContentValues();
                values.put(KEY_LOCATION, room.get_location());
                values.put(KEY_NUMBER, room.get_number());
                values.put(KEY_WIDTH, room.get_width());
                values.put(KEY_HEIGHT, room.get_height());
                values.put(KEY_LENGTH, room.get_length());
                values.put(KEY_THICKNESS, room.get_thickness());
                values.put(KEY_NAME, room.get_name());
                values.put(KEY_COMPRESSOR, room.get_compressor());
                values.put(KEY_CONDONSOR, room.get_condonsor());
                values.put(KEY_OVAPERATOR, room.get_ovaperator());
                values.put(KEY_PRODUCT_TYPE, room.get_product_type());
                values.put(KEY_STATUS, room.get_status());
                values.put(KEY_DESCRIPTION, room.get_description());
                values.put(KEY_UNDER_ZERO, Boolean.toString(room.is_under_zero()));
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                values.put(KEY_DATE, Utility.getCurrentShamsidate() + " " + sdf.format(new Date()));

                // Inserting Row
                db.insert(TABLE_ROOM, null, values);
                Log.d("MGH", "room added");
                cursor.close();
                nextCursor.close();
                db.close(); // Closing database connection
            } else {
                Toast.makeText(context, "سردخانه با این شماره تلفن قبلا تعریف شده است.", Toast.LENGTH_SHORT).show();
                cursor.close();
                nextCursor.close();
                db.close();

            }

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
        return count;
    }

    public void updateRoom(RoomModel newRoom) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_LOCATION, newRoom.get_location());
            values.put(KEY_NUMBER, newRoom.get_number());
            values.put(KEY_WIDTH, newRoom.get_width());
            values.put(KEY_HEIGHT, newRoom.get_height());
            values.put(KEY_LENGTH, newRoom.get_length());
            values.put(KEY_THICKNESS, newRoom.get_thickness());
            values.put(KEY_CONDONSOR, newRoom.get_condonsor());
            values.put(KEY_OVAPERATOR, newRoom.get_ovaperator());
            values.put(KEY_COMPRESSOR, newRoom.get_compressor());
            values.put(KEY_PRODUCT_TYPE, newRoom.get_product_type());
            values.put(KEY_UNDER_ZERO, Boolean.toString(newRoom.is_under_zero()));

            db.update(TABLE_ROOM, values, " id =  " + newRoom.get_id(), null);

            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public RoomModel getRoom(String number) {
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_NUMBER + " = '" + number + "' ;";
        RoomModel room = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                room = new RoomModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        Boolean.parseBoolean(cursor.getString(12)),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getString(15)
                );
            } else {
                Log.d("create_room", "No Room Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return room;
    }

    public int getlsat_room_id() {
        int result = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_ID + " = " + "( SELECT MAX(" + KEY_ID + ")  FROM " + TABLE_ROOM + ")" + " AND " + KEY_STATUS  + " <> '-1' ;";
        RoomModel room = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                result = Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return result;
    }

    public RoomModel getRoom(int id/*room*/) {
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_ID + " = '" + id + "' ;";
        RoomModel room = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                room = new RoomModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        Boolean.parseBoolean(cursor.getString(12)),
                        cursor.getString(13),
                        cursor.getString(14)
                );


            } else {
                Log.d("noroomfoundwithid", "No Room Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return room;
    }

    public List<RoomModel> getRooms() {
        List<RoomModel> res = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " ;"; //" WHERE " + KEY_STATUS + " = '1' OR " + KEY_STATUS + " = '0' "

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            String user = "";
            while (cursor.moveToNext()) {
            /*
            id , name, family, mobile
             */
                res.add(new RoomModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        Boolean.parseBoolean(cursor.getString(12)),
                        cursor.getString(13),
                        cursor.getString(14)
                ));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return res;
    }

    public List<RoomModel> getSearched_rooms(Boolean _province, Boolean _name, Boolean _number, Boolean _desciption, String search_value) {
        List<RoomModel> res = new ArrayList<>();

        int or_step = 0;


        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE ";
//        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_NAME + " like '%" + search_value + "%' OR " + KEY_NUMBER + " like '%" + search_value + "%' OR " + KEY_DESCRIPTION + " like '%" + search_value + "%' ;";

        if (_name || _number || _province || _desciption) {

            if (_province) {
                selectQuery += " " + KEY_LOCATION + " like '%" + search_value + "%'";
                or_step = 1;
            }
            if (_number && or_step == 1) {
                selectQuery += " OR " + KEY_NUMBER + " like '%" + search_value + "%'";
                or_step = 2;
            } else if (_number) {
                selectQuery += " " + KEY_NUMBER + " like '%" + search_value + "%'";
                or_step = 2;
            }

            if (_name && or_step == 2) {
                selectQuery += " OR " + KEY_NAME + " like '%" + search_value + "%'";
                or_step = 3;
            } else if (_name && or_step == 1) {
                selectQuery += " OR " + KEY_NAME + " like '%" + search_value + "%'";

            } else if (_name) {
                selectQuery += " " + KEY_NAME + " like '%" + search_value + "%'";
                or_step = 3;

            }
            if (_desciption && or_step == 3) {
                selectQuery += " OR " + KEY_DESCRIPTION + " like '%" + search_value + "%'";

            } else if (_desciption && or_step == 1) {
                selectQuery += " OR " + KEY_DESCRIPTION + " like '%" + search_value + "%'";

            } else if (_desciption && or_step == 2) {
                selectQuery += " OR " + KEY_DESCRIPTION + " like '%" + search_value + "%'";

            } else if (_desciption) {
                selectQuery += " " + KEY_DESCRIPTION + " like '%" + search_value + "%'";

            }

            selectQuery += " ;";


        } else {
            selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_NAME + " like '%" + search_value + "%' OR " + KEY_NUMBER + " like '%" + search_value + "%' OR " + KEY_DESCRIPTION + " like '%" + search_value + "%' OR " + KEY_LOCATION + " like '%" + search_value + "%' ;";

        }

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            String user = "";
            while (cursor.moveToNext()) {
            /*
            id , name, family, mobile
             */
                res.add(new RoomModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        Boolean.parseBoolean(cursor.getString(12)),
                        cursor.getString(13),
                        cursor.getString(14)
                ));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return res;
    }

    public List<RoomModel> getSearched_rooms(String search_value) {
        List<RoomModel> res = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_NAME + " like '%" + search_value + "%' OR " + KEY_NUMBER + " like '%" + search_value + "%' OR " + KEY_DESCRIPTION + " like '%" + search_value + "%' ;";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            String user = "";
            while (cursor.moveToNext()) {
            /*
            id , name, family, mobile
             */
                res.add(new RoomModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        Boolean.parseBoolean(cursor.getString(12)),
                        cursor.getString(13),
                        cursor.getString(14)
                ));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return res;
    }

    public void deleteRoom(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + TABLE_ROOM + " WHERE id = '" + id + "';");
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
    }

    public int check_user_exsis() {
        int result = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + KEY_ID + " = " + "( SELECT MAX(" + KEY_ID + ")  FROM " + TABLE_USER + ")" + " ;";
        RoomModel room = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                result = Integer.parseInt(cursor.getString(0));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return result;
    }

    public boolean userExist(String user_number, String room_number) {
        boolean result = false;

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + KEY_MOBILE + " = '" + user_number + "' AND " + KEY_ROOM_ID + " = '" + getRoom(room_number).get_id() + "' " + " ;";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                result = true;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return result;
    }

    public String getLockPattern() {
        String selectQuery = "SELECT  " + KEY_SETTING_LOCK_PATTERN + " FROM " + TABLE_APPLICATION_SETTING + " ;";
        String lock_pattern = "";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                lock_pattern = cursor.getString(0);
            } else {
                Log.d("lock_pattern", "No pattern Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return lock_pattern;
    }

    public void setLockPattern(String pattern) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_SETTING_LOCK_PATTERN, pattern);

            // Inserting Row
            db.update(TABLE_APPLICATION_SETTING, values, null, null);
            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e(getClass().getName(), e.getMessage() + " !!! ");
        }
    }

    public void repairSettingTable() {
        try {

            if (getApplicationSettings() == null) {
                SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_SETTING_LOCK_PATTERN, "");
                //todo

                // Inserting Row
                db.insert(TABLE_APPLICATION_SETTING, null, values);
                Log.d(getClass().getName(), "setting ...");
                db.close();
            }

        } catch (Exception e) {
            Log.e(getClass().getName(), e.getMessage() + " !!!");
        }
    }

    public SettingModel getApplicationSettings() {
        String selectQuery = "SELECT  " + KEY_SETTING_LOCK_PATTERN + " FROM " + TABLE_APPLICATION_SETTING + " ;";
        SettingModel settingModel = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                settingModel = new SettingModel();
                settingModel.setSetting_lock_pattern(cursor.getString(0));
            } else {
                Log.d("lock_pattern", "No pattern Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return settingModel;
    }

    public boolean isNumberExist(String mobile_number) {

        String selectQuery = "SELECT  " + KEY_MOBILE + " FROM " + TABLE_USER + " ;";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToNext()) {
                if (cursor.getString(0).equals(mobile_number))
                    return true;
            } else {
                return false;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "isNumberExist: ", e);
        }
        return false;
    }

    public boolean isSettingLogExist(String log) {

        String unique_key = log.substring(2, 5);
        String selectQuery = "SELECT * FROM " + TABLE_ORDER + " WHERE " + KEY_UNIQUE + " = '" + unique_key + "' AND " + KEY_STATE + " = '" + Config._STATE_PENDING + "' AND " + KEY_TYPE + " = '" + Config._REPORT_SETTING + "' ;";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() != 0) {
                if (cursor.moveToNext()) {
                    if (cursor.getString(6).equals(log))
                        return true;
                } else {
                    return false;
                }
                cursor.close();
                db.close();
            } else {
                //
                DatabaseHandler _db = new DatabaseHandler(context);
                String decompressd = new SmsCodeAndDecode().decompress(log.substring(5, log.length() - 1));
                String number = "0" + decompressd.substring(0, 10);
                _db.createOrder(log, number,
                        Config._STATE_COMPLETED,
                        Config._REPORT_FROM_SERVER,
                        "",
                        new HashMap<String, String>(),
                        Config._TYPE_RECEIVED_MESSAGE,
                        1, Config.currentRoomID);
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "isNumberExist: ", e);
        }
        return false;
    }

    public void updateState(int id, String state) {
        try {

            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_STATE, state);

            db.update(TABLE_ORDER, values, " " + KEY_ID + " = " + id, null);

            db.close();

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public void updateUser(int id, String status) {
        try {

            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_STATUS, status);

            db.update(TABLE_USER, values, " " + KEY_ID + " = " + id, null);

            db.close();

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public boolean isDeletedUser(String pending, String userId) {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_STATE + " = '" + Config._STATE_PENDING + "' AND " + KEY_USER_ID + " = '" + userId + "' ASC LIMIT 1";

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                updateState(Integer.parseInt((cursor.getString(0))), Config._STATE_COMPLETED);
                cursor.close();
                db.close();
                return true;
            } else {
                cursor.close();
                db.close();
                return false;
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return true;
    }

    public boolean isNewSetting(String room_id) {
        String selectQuery = "SELECT * FROM " + TABLE_ORDER + " WHERE " + KEY_TYPE + " = '" + Config._REPORT_SETTING + "' AND " + KEY_ROOM_ID + " = '" + room_id + "' ORDER BY " + KEY_ID + " ASC LIMIT 1";

        Boolean result = false;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToNext()) {
                result = true;
            } else {
                result = false;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return result;
    }

    public String getLastSetting(String room_id) {
        String selectQuery = "SELECT * FROM " + TABLE_ORDER + " WHERE " + KEY_TYPE + " = '" + Config._REPORT_SETTING + "' AND " + KEY_ROOM_ID + " = '" + room_id + "' ORDER BY " + KEY_ID + " DESC LIMIT 1";
        String Result = "";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                Result = cursor.getString(6);
                cursor.close();
                db.close();
            } else {
                Result = "-1";
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return Result;
    }

    public boolean isInit() {
        String selectQuery = "SELECT * FROM " + TABLE_ORDER + " WHERE " + KEY_NUMBER + " = 09020000000 Order by " + KEY_ID + " ASC LIMIT 1";
        Boolean Result = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                Result = true;
                cursor.close();
                db.close();
            } else {
                Result = false;
                cursor.close();
                db.close();
            }
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
            cursor.close();
            db.close();

        }
        return Result;
    }

    public String getNameByNumber(String number) {

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_MOBILE + " = '" + number + "' ;";
        String result = "-1";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToNext()) {
                result = cursor.getString(1);
            } else {
                return result;
            }
            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.i(getClass().getSimpleName(), "getNameByNumber: ", e);
        }
        return result;
    }

    public String getLastUniqueKey() {

        String lastkey = "";
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " WHERE " + KEY_TYPE + " = '" + Config._TYPE_SEND_MESSAGE + "' AND " + KEY_REPORT_FROM + " = '" + Config._REPORT_FROM_CLIENT + "' AND " + KEY_UNIQUE + " Is not null ;";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                lastkey = cursor.getString((cursor.getColumnCount() - 2));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return lastkey;

    }

    public void RoomSmsDelivery(String Key) {
        try {

            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_DELIVERY, "true");

            db.update(TABLE_ORDER, values, KEY_TYPE + " = '" + Config._TYPE_SEND_MESSAGE + "' AND " + KEY_REPORT_FROM + " = '" + Config._REPORT_FROM_CLIENT + "' AND " + KEY_UNIQUE + " = '" + Key + "' ", null);
            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public String getLastInformation(int room_id) {
        String message = "";
        String time, date;
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " WHERE " + KEY_ROOM_ID + " = '" + room_id +
                "' order by " + KEY_ID + " desc  ;";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                message = cursor.getString(cursor.getColumnIndex("message"));
                if (!message.equals(""))
                    if (message.substring(0, 2).equals("#I")) {
                        time = cursor.getString(cursor.getColumnIndex(KEY_TIME_LOG));
                        date = cursor.getString(cursor.getColumnIndex(KEY_DATE));
                        return (time + "-" + date);
                    }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return "-1";
    }

    public int customActiveRoom() {
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_STATUS + " = '1' " + " ;";
        String id = "-1";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                id = cursor.getString(cursor.getColumnIndex("id"));
                if (!id.equals("")) {
                    activeRoomStatus(Integer.parseInt(id));
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return Integer.parseInt(id);
    }

    public String PermissionLevel(int user_id) {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_ID + " = '" + user_id + "' ; ";
        String result = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex(KEY_PERMISION_LEVEL));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return result;
    }

    public String getUserIdByNumber(String number) {
        String selectQuery = "SELECT * FROM " + TABLE_ROOM + " WHERE " + KEY_NUMBER + " = '" + number + "' ; ";
        String result = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex(KEY_ID));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return result;
    }

    public void updateRoomLimited(RoomModel newRoom) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_DESCRIPTION, newRoom.get_description());
            values.put(KEY_NAME, newRoom.get_name());

            db.update(TABLE_ROOM, values, " id =  " + newRoom.get_id(), null);

            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public void updateUserLimited(UserModel userModel) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, userModel.get_name());
            values.put(KEY_FAMIY, userModel.get_family());

            db.update(TABLE_USER, values, " id =  " + userModel.get_id(), null);

            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public void updateUserGlobal(UserModel userModel) {
        try {
            SQLiteDatabase db = DatabaseHandler.this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_PERMISION_LEVEL, userModel.get_permision_level());

            db.update(TABLE_USER, values, " id =  " + userModel.get_id(), null);

            db.close(); // Closing database connection

        } catch (Exception e) {
            Log.e("MGH", e.getMessage() + " error ");
        }
    }

    public String getRoomIdByNumber(String room_number) {
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_ROOM_ID + " = '" + room_number + "' AND key_status Is not '-1' ;";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(KEY_ID));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return "-1";

    }

    public ArrayList<RoomModel> getAllRoom() {
        String selectQuery = "SELECT  * FROM " + TABLE_ROOM + " WHERE " + KEY_STATUS + " <> '-1' ;";
        ArrayList<RoomModel> room = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToNext()) {
                room.add(new RoomModel(cursor.getInt(0)));
            } else {
                Log.d("create_room", "No Room Found !!!");
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), ": ", e);
        }
        return room;
    }

    public String getPermisionLevel(String mobile) {
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_MOBILE + " = '" + mobile + "' ; ";
        String result = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex(KEY_PERMISION_LEVEL));
            }

            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return result;
    }

}
