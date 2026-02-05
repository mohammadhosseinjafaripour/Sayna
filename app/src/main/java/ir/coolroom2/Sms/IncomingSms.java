package ir.coolroom2.Sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

import ir.coolroom2.Activity.AlertActivity;
import ir.coolroom2.Activity.LoginActivity;
import ir.coolroom2.Activity.MainActivity;
import ir.coolroom2.Activity.Splash;
import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;


/**
 * Created by jefferson on 4/25/2017.
 */

public class IncomingSms extends BroadcastReceiver {

    private Intent intent1;
    private PrepareDataAndSendSms prepareDataAndSendSms;
    private SmsCodeAndDecode smsCodeAndDecode;

    private String user_number = "";
    private String user_permision = "";
    private String data = "";
    private HashMap<String, String> error_messages = new HashMap<>();
    private Boolean is_error = true;


    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        DatabaseHandler db = new DatabaseHandler(context);
        prepareDataAndSendSms = new PrepareDataAndSendSms(context);
        smsCodeAndDecode = new SmsCodeAndDecode();

        error_messages.put("01", "\"قطع کامل برق\"");
        error_messages.put("02", "\"خاموش دستی\"");
        error_messages.put("03", "\"قطع فاز R\"");
        error_messages.put("04", "\"قطع فاز S\"");
        error_messages.put("05", "\"قطع فاز T\"");
        error_messages.put("06", "\"عدم توالی فاز\"");
        error_messages.put("07", "\"عدم تقارن فاز\"");
        error_messages.put("08", "\"افزایش ولتاژ R\"");
        error_messages.put("09", "\"افزایش ولتاژ S\"");
        error_messages.put("10", "\"افزایش ولتاژ T\"");
        error_messages.put("11", "\"کاهش ولتاژ R\"");
        error_messages.put("12", "\"کاهش ولتاژ S\"");
        error_messages.put("13", "\"کاهش ولتاژ T\"");
        error_messages.put("14", "\"اورلود کمپرسور\"");
        error_messages.put("15", "\"افزایش جریان کمپرسور\"");
        error_messages.put("16", "\"کاهش جریان کمپرسور\"");
        error_messages.put("17", "\"عدم تقارن جریان کمپرسور\"");
        error_messages.put("18", "\"افزایش جریان اواپراتور\"");
        error_messages.put("19", "\"کاهش جریان اواپراتور\"");
        error_messages.put("20", "\"عدم تقارن جریان اواپراتور\"");
        error_messages.put("21", "\"فشار گاز بالا\"");
        error_messages.put("22", "\"فشار گاز پایین\"");
        error_messages.put("23", "\"فشار روغن\"");


        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                String sender_number = "";
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    sender_number = currentMessage.getDisplayOriginatingAddress();
                    String message_content = currentMessage.getDisplayMessageBody();

                    sender_number = "0" + sender_number.substring(3, 13);
                    RoomModel room = db.getRoom(sender_number);


                    if (room != null) {
                        // TODO: 07/12/2017 checking

                        if (message_content.charAt(0) != '#' && message_content.charAt(message_content.length() - 1) != '#') {
                            Intent view = new Intent(context, AlertActivity.class);
                            view.putExtra("title", "خطا");
                            view.putExtra("msg", "پیام به درستی دریافت نشد.");
                            view.putExtra("btn", "تایید");
                            view.putExtra("action", "close");
                            view.setAction(Intent.ACTION_VIEW);
                            view.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(view);
                        } else if (message_content.substring(0, 2).equals("#C") && message_content.charAt(message_content.length() - 1) == '#') {
                            boolean mode_show = true;
                            String user_num = "";
                            String decompressed_data = smsCodeAndDecode.decompress(message_content.substring(2, message_content.length() - 1));
                            user_num = decompressed_data.substring(0, 10);
                            String check_db = "0" + decompressed_data.substring(0, 10);
                            String type_of_manual = decompressed_data.substring(10, 12);
                            String manual = "";
                            String show_type = "";
                            switch (type_of_manual) {
                                case "00": {
                                    manual = Config._MANUAL_DEFROST;
                                    show_type = "دیفراست دستی";
                                    break;
                                }
                                case "01": {
                                    manual = Config._MANUAL_OVAPERATOR;
                                    show_type = "اواپراتور دستی";
                                    break;
                                }
                                case "02": {
                                    manual = Config._MANUAL_COMPRESSOR;
                                    show_type = "کمپرسور دستی";
                                    break;
                                }
                                case "03": {
                                    manual = Config._MANUAL_HIGHGAS;
                                    show_type = "فشار گاز بالا دستی";
                                    break;
                                }
                                case "04": {
                                    manual = Config._MANUAL_LOWGAS;
                                    show_type = "فشار گاز پایین دستی";
                                    break;
                                }
                                case "05": {
                                    manual = Config._MANUAL_OIL;
                                    show_type = "فشار روغن دستی";
                                    break;
                                }
                            }
                            db.createOrder(message_content,
                                    db.getRoom(Config.currentRoomID).get_number(),
                                    Config._STATE_COMPLETED,
                                    Config._REPORT_FROM_SERVER,
                                    "",
                                    new HashMap<String, String>(),
                                    manual,
                                    Config.currentRoomID,
                                    db.getFirstUser_for_room(Config.currentRoomID).get_id());


                            if (db.isNumberExist(check_db)) {
                                mode_show = false;
                                db.resetAllRoomStatus();
                                db.activeRoomStatus(room.get_id());
                                showAlert(context, "فرمان مستقیم" + " - " + show_type, "تغییرات توسط " + db.getNameByNumber(check_db) + " برای سرخانه " + room.get_name() + " انجام شده است. ", "باز کردن برنامه", "open");
                            } else {
                                //other permission for another user
                                if (user_num.equals("0000000000")) {
                                    mode_show = false;
                                    db.resetAllRoomStatus();
                                    db.activeRoomStatus(room.get_id());
                                    showAlert(context, "فرمان مستقیم", "تغییرات توسط مدیر" + "برای سردخانه " + room.get_name() + " انجام شده است. ", "باز کردن برنامه", "open");
                                } else if (user_num.equals("1111111111")) {
                                    // TODO: 4/30/2018 iam here :D ☺
                                    mode_show = false;
                                    db.resetAllRoomStatus();
                                    db.activeRoomStatus(room.get_id());
                                    showAlert(context, "فرمان مستقیم", "تغییرات توسط تکنسین" + "برای سردخانه " + room.get_name() + " انجام شده است. ", "باز کردن برنامه", "open");

                                }
                            }
                            if (mode_show) {
                                db.resetAllRoomStatus();
                                db.activeRoomStatus(room.get_id());
                                showAlert(context, "فرمان مستقیم", "فرمان مستقیم" + " برای سردخانه " + room.get_name() + " تایید شد.  ", "باز کردن برنامه", "open");
                            }
                        } else if (message_content.substring(0, 2).equals("#R") && message_content.charAt(message_content.length() - 1) == '#') {

                            data = message_content.substring(4, message_content.length() - 1);
                            data = smsCodeAndDecode.decompress(data);
                            user_permision = getUserPermission(data.substring(0, 1));
                            user_number = "0" + data.substring(1, 11);
                            int userids = db.getUserIdByPhonenumber(user_number, "OK");
                            UserModel userModelD = db.getUser(userids, "OK");
                            if (db.userExist(user_number, room.get_number()))
                                if (message_content.substring(3, 4).equals("D")) {
                                    db.createOrder("",
                                            db.getRoom(Config.currentRoomID).get_number(),
                                            Config._STATE_COMPLETED,
                                            Config._REPORT_FROM_SERVER,
                                            "", new HashMap<String, String>(),
                                            Config._TYPE_DELETE_USER,
                                            Config.currentRoomID,
                                            db.getFirstUser_for_room(Config.currentRoomID).get_id());

                                    String coded = message_content;
                                    coded = coded.substring(4, coded.length() - 1);
                                    coded = smsCodeAndDecode.decompress(coded);
                                    user_number = "0" + coded.substring(1, 11);


                                    String value = "";
                                    String modir = message_content.substring(2, 3);
                                    if (modir.equals("M")) {
                                        if (db.getPermisionLevel(user_number).equals("one")) {
                                            db.deleteAllUserForRoom(String.valueOf(db.getRoom(sender_number).get_id()));
                                            db.deleteRoom(db.getRoom(sender_number).get_id());
                                            value = "سرخانه";
                                        } else {
                                            db.deleteUser(user_number);
                                            value = "کاربر";

                                        }

                                    } else {
                                        value = "کاربر";
                                        db.deleteUser(user_number);
                                        db.deleteRoom(db.getRoom(sender_number).get_id());

                                    }


                                    if (db.getFirstUser_for_room(Config.currentRoomID) == null) {
                                        if (db.getAllRoom().size() != 0) {
                                            db.resetAllRoomStatus();
                                            db.deleteRoom(Config.currentRoomID);
                                            db.activeRoomStatus(db.getlsat_room_id());
                                        } else {
                                            deleteCache(context);
                                            clearApplicationData(context);
                                        }
                                    }
                                    showAlert(context, "حذف", " " + value + " " + userModelD.get_name() + " " + userModelD.get_family() + " برای سردخانه " + room.get_name() + " حذف شد.", "باز کردن برنامه", "open");


                                } else if (message_content.substring(3, 4).equals("P")) {
                                    // TODO: 19/04/2018 ممکنه اخرین اطلاعات سردخانه رو برامون بفرسته و اپدیت کنیم
//                                    prepareDataAndSendSms.get_register(message_content);
                                    db.createOrder(
                                            message_content,
                                            sender_number,
                                            Config._STATE_COMPLETED,
                                            Config._REPORT_FROM_SERVER,
                                            "",
                                            new HashMap<String, String>(),
                                            Config._TYPE_EDIT_USER,
                                            db.getFirstUser_for_room(room.get_id()).get_id(),
                                            room.get_id());

                                    // FIXME: 4/19/2018 db.updateUser()
                                    int user_id = db.getUserIdByPhonenumber(user_number, "KO");
                                    UserModel userModel = db.getUser(user_id, "KO");
                                    db.activeUser(new UserModel(
                                            user_id,
                                            userModel.get_name(),
                                            userModel.get_family(),
                                            user_number,
                                            user_permision,
                                            userModel.get_room_id()
                                    ));
                                    db.resetAllRoomStatus();
                                    db.activeRoomStatus(room.get_id());
                                    showAlert(context, "دسترسی", "به کاربر " + userModel.get_name() + " " + userModel.get_family() + " برای سرخانه " + room.get_name() + " دسترسی داده شد ", "باز کردن برنامه", "open");

                                } else if (message_content.substring(3, 4).equals("A")) {

                                    int user_id = db.getUserIdByPhonenumber(user_number, "KO");
                                    UserModel userModel = db.getUser(user_id, "KO");
                                    db.activeUser(new UserModel(
                                            user_id,
                                            userModel.get_name(),
                                            userModel.get_family(),
                                            user_number,
                                            userModel.get_permision_level(),
                                            userModel.get_room_id()
                                    ));

                                    db.createOrder(
                                            message_content,
                                            sender_number,
                                            Config._STATE_COMPLETED,
                                            Config._REPORT_FROM_SERVER,
                                            "",
                                            new HashMap<String, String>(),
                                            Config._TYPE_CREATE_USER,
                                            db.getFirstUser_for_room(room.get_id()).get_id(),
                                            room.get_id()
                                    );
                                    // FIXME: 4/12/2018 update
                                    db.resetAllRoomStatus();
                                    db.activeRoomStatus(room.get_id());
                                    showAlert(context, "کاربر جدید", "یک کاربر جدید به سردخانه " + room.get_name() + " اضافه شد ", "باز کردن برنامه", "open");
                                } else if (message_content.substring(3, 4).equals("E")) {
                                    String temp_data = smsCodeAndDecode.decompress(message_content.substring(4, message_content.length() - 1));
                                    String user_id = db.getUserIdByNumber(sender_number);
                                    RoomModel tempmodel = db.getRoom(Integer.parseInt(user_id));

                                    String stringHashMap = temp_data.substring(15, 16);

                                    Boolean underZero = false;
                                    if (temp_data.substring(15, 16).equals("1")) {
                                        underZero = true;
                                    } else {
                                        underZero = false;
                                    }

                                    String location = temp_data.substring(11, 13);
                                    RoomModel result_model = new RoomModel(
                                            tempmodel.get_id(),
                                            tempmodel.get_number(),
                                            Config.provincial_code_reverese.get(location), //location
                                            temp_data.substring(13, 15),//type of product
                                            underZero,//under_zero
                                            temp_data.substring(16, 19),
                                            temp_data.substring(19, 22),
                                            temp_data.substring(22, 24),
                                            temp_data.substring(24, 26),
                                            temp_data.substring(26, 28),
                                            temp_data.substring(28, 30),
                                            temp_data.substring(30, 32));

                                    db.updateRoom(result_model);


                                    db.createOrder(message_content,
                                            db.getRoom(Config.currentRoomID).get_number(),
                                            Config._STATE_COMPLETED,
                                            Config._REPORT_FROM_SERVER,
                                            "",
                                            new HashMap<String, String>(),
                                            Config._TYPE_EDIT_ROOM,
                                            Config.currentRoomID,
                                            db.getFirstUser_for_room(Config.currentRoomID).get_id());

                                    showAlert(context, "ویرایش سرخانه", " سردخانه  " + room.get_name() + " ویرایش شد ", "باز کردن برنامه", "open");


                                } else if (message_content.substring(3, 4).equals("C")) {
                                    String temp_data = smsCodeAndDecode.decompress(message_content.substring(4, message_content.length() - 1));
                                    String user_phonenumber = temp_data.substring(1, 11);
                                    String room_id = db.getUserIdByNumber("0" + user_phonenumber);
                                    int user_id = db.getFirstUser_for_room(Integer.parseInt(room_id)).get_id();
                                    UserModel userModel = db.getUser(user_id, "OK");

                                    String permission = "";
                                    String chk = temp_data.substring(0, 1);
                                    switch (chk) {
                                        case "1":
                                            permission = "one";
                                            break;
                                        case "2":
                                            permission = "two";
                                            break;
                                        case "3":
                                            permission = "three";
                                            break;
                                    }
                                    UserModel tempUser = new UserModel(userModel.get_id(), permission);
                                    db.updateUserGlobal(tempUser);

                                    db.createOrder("",
                                            db.getRoom(Config.currentRoomID).get_number(),
                                            Config._STATE_COMPLETED,
                                            Config._REPORT_FROM_SERVER,
                                            "",
                                            new HashMap<String, String>(),
                                            Config._TYPE_EDIT_USER,
                                            Config.currentRoomID,
                                            db.getFirstUser_for_room(Config.currentRoomID).get_id());

                                    showAlert(context, "ویرایش کاربر", " کاربر سردخانه  " + room.get_name() + " ویرایش شد ", "باز کردن برنامه", "open");


                                } else {
                                    Toast.makeText(context, "ورود نامعتبر بود!", Toast.LENGTH_LONG).show();
                                }
                        } else if (message_content.substring(0, 2).equals("#U") && message_content.charAt(message_content.length() - 1) == '#') {


                        } else if (message_content.substring(0, 2).equals("#S") && message_content.charAt(message_content.length() - 1) == '#') {
                            boolean most_start = true;
                            String coded = message_content;
                            boolean flag_clean_msg = false;
                            if (coded.length() == 189) {
                                flag_clean_msg = true;
                            }
                            coded = coded.substring(5, coded.length() - 1);
                            coded = smsCodeAndDecode.decompress(coded);
                            user_number = "0" + coded.substring(0, 10);
                            String db_check_user = "0" + coded.substring(0, 10);


                            if (!db.isSettingLogExist(message_content)) {
                                db.resetAllRoomStatus();
                                db.activeRoomStatus(room.get_id());
                                if (flag_clean_msg) {
                                    db.createOrder(
                                            message_content,
                                            sender_number,
                                            Config._STATE_COMPLETED,
                                            Config._REPORT_FROM_SERVER,
                                            "",
                                            new HashMap<String, String>(),
                                            Config._REPORT_SETTING,
                                            db.getFirstUser_for_room(room.get_id()).get_id(),
                                            room.get_id());
                                } else {
                                    Toast.makeText(context, "داده به درستی دریافت نشده است.", Toast.LENGTH_SHORT).show();
                                }
                                if (db.isNumberExist(db_check_user)) {
                                    most_start = false;
                                    db.resetAllRoomStatus();
                                    db.activeRoomStatus(room.get_id());
                                    showAlert(context, "اعمال تغییرات", " تغییرات توسط " + db.getNameByNumber(db_check_user) + " برای سردخانه " + room.get_name() + " انجام شده است. ", "باز کردن برنامه", "open");
                                } else {
                                    //other permission for another user
                                    if (user_number.equals("0000000000")) {
                                        most_start = false;
                                        db.resetAllRoomStatus();
                                        db.activeRoomStatus(room.get_id());
                                        showAlert(context, "اعمال تغییرات", " تغییرات توسط مدیر " + " برای سردخانه " + room.get_name() + " انجام شده است. ", "باز کردن برنامه", "open");
                                    } else if (user_number.equals("1111111111")) {
                                        // TODO: 4/30/2018 iam here :D ☺
                                        most_start = false;
                                        db.resetAllRoomStatus();
                                        db.activeRoomStatus(room.get_id());
                                        showAlert(context, "اعمال تغییرات", "تغییرات توسط تکنسین" + " برای سردخانه " + room.get_name() + " انجام شده است. ", "باز کردن برنامه", "open");
                                    } else if (user_number.equals("222222222")) {
                                        most_start = false;
                                        db.resetAllRoomStatus();
                                        db.activeRoomStatus(room.get_id());
                                        showAlert(context, "اعمال تغییرات", "تغییرات توسط پنل دستگاه " + " برای سردخانه " + room.get_name() + " تغییر یافت. ", "باز کردن برنامه", "open");

                                    }
                                }
                                if (most_start) {
                                    db.resetAllRoomStatus();
                                    db.activeRoomStatus(room.get_id());
                                    showAlert(context, "تنظیمات", " تنظیمات جدید " + " برای سردخانه " + room.get_name() + " دریافت شد. ", "باز کردن برنامه", "open");
                                }
                            }

                        } else if (message_content.substring(0, 2).equals("#I") && message_content.charAt(message_content.length() - 1) == '#') {
                            String decoded_string = smsCodeAndDecode.decompress(message_content.substring(2, message_content.length() - 1));
                            String error_code = decoded_string.substring(28, 30);
                            String mode_kari = decoded_string.substring(30, 31);
                            db.createOrder(
                                    message_content,
                                    sender_number,
                                    Config._STATE_COMPLETED,
                                    Config._REPORT_FROM_SERVER,
                                    "",
                                    new HashMap<String, String>(),
                                    error_code,
                                    db.getFirstUser_for_room(room.get_id()).get_id(),
                                    room.get_id()
                            );
                            if (mode_kari.equals("1")) {
                                if (!error_code.equals("00")) {
                                    is_error = false;
                                    db.resetAllRoomStatus();
                                    db.activeRoomStatus(room.get_id());
                                    showAlert(context, "خطا", " خطای " + error_messages.get(error_code) + " برای سردخانه " + room.get_name() + " رخ داده است. ", "باز کردن برنامه", "open");
                                }
                            }

                            if (is_error) {
                                db.resetAllRoomStatus();
                                db.activeRoomStatus(room.get_id());
                                showAlert(context, "اطلاعات", " اطلاعات جدید از سردخانه  " + room.get_name() + " دریافت شد.  ", "باز کردن برنامه", "open");

                            }

                        } else {
                                /*db.createOrder(
                                        message_content,
                                        sender_number,
                                        Config._STATE_COMPLETED,
                                        Config._REPORT_FROM_SERVER,
                                        "",
                                        new HashMap<String, String>(),
                                        Config._TYPE_RECEIVED_MESSAGE,
                                        db.getFirstUser_for_room(room.get_id()).get_id(),
                                        room.get_id());
                                db.resetAllRoomStatus();
                                db.activeRoomStatus(room.get_id());*/
                        }
                        Config.server_response = true;

                        Log.i("SmsReceiver", "sender_number: " + sender_number + "; message_content: " + message_content);
                    }
                } // end for loop
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    if (!Telephony.Sms.getDefaultSmsPackage(context).equals(context.getPackageName())) {
                        removeMessage(context, sender_number);
                    }
                } else {

                }
            } // bundle is null

        } catch (Exception
                e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }

    }

    private void removeMessage(Context context, String fromAddress) {

        Uri uriSMS = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(uriSMS, null,
                null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            int ThreadId = cursor.getInt(1);
            Log.d("Thread Id", ThreadId + " id - " + cursor.getInt(0));
            Log.d("contact number", cursor.getString(2));
            Log.d("column name", cursor.getColumnName(2));

            context.getContentResolver().delete(Uri.
                            parse("content://sms/conversations/" + ThreadId), "address=?",
                    new String[]{fromAddress});
            Log.d("Message Thread Deleted", fromAddress);
//            Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
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


    public void clearApplicationData(Context context) {
        File cacheDirectory = context.getCacheDir();
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

    public void startActivityInComing(Context context) {
        if (MainActivity.instance != null)
            MainActivity.instance.finish();
        if (LoginActivity.instance != null)
            LoginActivity.instance.finish();
        intent1 = new Intent(context, Splash.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent1);
    }

    public String getUserPermission(String permission) {
        String result = "";
        switch (Integer.parseInt(permission)) {
            case 1:
                result = "one";
                break;
            case 2:
                result = "two";
                break;
            case 3:
                result = "three";
                break;
        }
        return result;
    }

    public void showAlert(Context context, String title, String msg, String btn, String action) {
        Intent view = new Intent(context, AlertActivity.class);
        view.putExtra("title", title);
        view.putExtra("msg", msg);
        view.putExtra("btn", btn);
        view.putExtra("action", action);
        view.setAction(Intent.ACTION_VIEW);
        view.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(view);
    }


}