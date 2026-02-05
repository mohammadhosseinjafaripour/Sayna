package ir.coolroom2;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TabHost;

import java.util.HashMap;

import ir.coolroom2.Activity.AlertActivity;
import ir.coolroom2.Activity.LoginActivity;
import ir.coolroom2.Activity.MainActivity;

/**
 * Created by JFP on 12/4/2017.
 */

public class Config {

    public static float Current_counter = 1f;
    public static boolean server_response = false;
    public static String permision_level;
    public static int currentRoomID;
    public static Boolean ForceToCreateRoom = false;
    public static View Pressure_view = null;
    public static RadioButton Pressure_radio_btn_low_gas_manual = null;
    public static RadioButton Pressure_radio_btn_low_gas_automatic = null;
    public static String currentUserName = "";
    public static AlertActivity alertActivity = null;
    public static TabHost mainTabHost = null;
    public static int setting_tab_pressure_index = 1;
    public static int setting_tab_pressure_checked_radio_btn_id = -1;

    public static MainActivity myActivity = null;
    public static LoginActivity loginActivity = null;


    public static String INFORMATION_LAT = "1";
    public static String INFORMATION_LONG = "2";
    public static String INFORMATION_YEAR = "3";
    public static String INFORMATION_MONTH = "4";
    public static String INFORMATION_DAY = "5";
    public static String INFORMATION_HOUR = "6";
    public static String INFORMATION_MINUTE = "7";
    public static String INFORMATION_ERRORTYPE = "8";
    public static String INFORMATION_WORKMODE = "9";
    public static String INFORMATION_TEMPRETUREV1 = "10";
    public static String INFORMATION_WETV1 = "11";
    public static String INFORMATION_DEFROSTTIME = "1224";
    public static String INFORMATION_DEFROST = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
    public static String INFORMATION_RTON = "14";
    public static String INFORMATION_STON = "15";
    public static String INFORMATION_TTON = "16";
    public static String INFORMATION_PHASE_COUNT = "1";
    public static String INFORMATION_CURRENTCOMPRESSOR_R = "17";
    public static String INFORMATION_CURRENTCOMPRESSOR_S = "18";
    public static String INFORMATION_CURRENTCOMPRESSOR_T = "19";
    public static String INFORMATION_CURRENTOVAPERATOR_R = "20";
    public static String INFORMATION_CURRENTOVAPERATOR_S = "21";
    public static String INFORMATION_CURRENTOVAPERATOR_T = "22";
    public static String INFORMATION_HIGHGAS = "23";
    public static String INFORMATION_LOWHGAS = "24";
    public static String INFORMATION_OIL = "25";


    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATE = "date";
    public static final String KEY_STATE = "state";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_REPORT_FROM = "report_from";
    public static final String KEY_RESPONSE = "response";
    public static final String KEY_CURRENT_ROOM = "false";

    public static final String KEY_TEMPERATURE_V1 = "temperature_V1";
    public static final String KEY_TEMPERATURE_V2 = "temperature_V2";
    public static final String KEY_TEMPERATURE_V3 = "temperature_V3";
    public static final String KEY_TEMPERATURE_V4 = "temperature_V4";
    public static final String KEY_TEMPERATURE_V5 = "temperature_V5";
    public static final String KEY_TEMPERATURE_V6 = "temperature_V6";
    public static final String KEY_TEMPERATURE_V7 = "temperature_V7";
    public static final String KEY_TEMPERATURE_V8 = "temperature_V8";
    public static final String KEY_TEMPERATURE_V9 = "temperature_V9";
    public static final String KEY_TEMPERATURE_V10 = "temperature_V10";


    public static final String KEY_HUMOUR_V1 = "humour_V1";
    public static final String KEY_HUMOUR_V2 = "humour_V2";
    public static final String KEY_HUMOUR_V3 = "humour_V3";
    public static final String KEY_HUMOUR_V4 = "humour_V4";
    public static final String KEY_HUMOUR_V5 = "humour_V5";
    public static final String KEY_HUMOUR_V6 = "humour_V6";
    public static final String KEY_HUMOUR_V7 = "humour_V7";
    public static final String KEY_HUMOUR_V8 = "humour_V8";
    public static final String KEY_HUMOUR_V9 = "humour_V9";
    public static final String KEY_HUMOUR_V10 = "humour_V10";

    public static final String KEY_DEFROST_V1 = "defrost_V1";
    public static final String KEY_DEFROST_V2 = "defrost_V2";

    public static final String KEY_VOLTAGE_V1 = "voltage_V1";
    public static final String KEY_VOLTAGE_V2 = "voltage_V2";
    public static final String KEY_VOLTAGE_V3 = "voltage_V3";
    public static final String KEY_VOLTAGE_V4 = "voltage_V4";
    public static final String KEY_VOLTAGE_V5 = "voltage_V5";
    public static final String KEY_VOLTAGE_V6 = "voltage_V6";
    public static final String KEY_VOLTAGE_V7 = "voltage_V7";
    public static final String KEY_VOLTAGE_V8 = "voltage_V8";
    public static final String KEY_VOLTAGE_V9 = "voltage_V9";
    public static final String KEY_VOLTAGE_V10 = "voltage_V10";

    public static final String KEY_CURRENT_OVAPERATOR_V1 = "current_V1";
    public static final String KEY_CURRENT_OVAPERATOR_V2 = "current_V2";
    public static final String KEY_CURRENT_OVAPERATOR_V3 = "current_V3";
    public static final String KEY_CURRENT_OVAPERATOR_V4 = "current_V4";
    public static final String KEY_CURRENT_OVAPERATOR_V5 = "current_V5";
    public static final String KEY_CURRENT_OVAPERATOR_V6 = "current_V6";
    public static final String KEY_CURRENT_OVAPERATOR_V7 = "current_V7";
    public static final String KEY_CURRENT_OVAPERATOR_V8 = "current_V8";
    public static final String KEY_CURRENT_OVAPERATOR_V9 = "current_V9";
    public static final String KEY_CURRENT_OVAPERATOR_V10 = "current_V10";

    public static final String KEY_CURRENT_COMPRESOR_V1 = "current_V11";
    public static final String KEY_CURRENT_COMPRESOR_V2 = "current_V12";
    public static final String KEY_CURRENT_COMPRESOR_V3 = "current_V13";
    public static final String KEY_CURRENT_COMPRESOR_V4 = "current_V14";
    public static final String KEY_CURRENT_COMPRESOR_V5 = "current_V15";
    public static final String KEY_CURRENT_COMPRESOR_V6 = "current_V16";
    public static final String KEY_CURRENT_COMPRESOR_V7 = "current_V17";
    public static final String KEY_CURRENT_COMPRESOR_V8 = "current_V18";
    public static final String KEY_CURRENT_COMPRESOR_V9 = "current_V19";
    public static final String KEY_CURRENT_COMPRESOR_V10 = "current_V20";

    public static final String KEY_PRESSURE_V1 = "pressure_V1";
    public static final String KEY_PRESSURE_V2 = "pressure_V2";
    public static final String KEY_PRESSURE_V3 = "pressure_V3";
    public static final String KEY_PRESSURE_V4 = "pressure_V4";
    public static final String KEY_PRESSURE_V5 = "pressure_V5";


    public static final String KEY_PRESSURE_HIGH_GAS_V1 = "pressure_high_gaz_V1";
    public static final String KEY_PRESSURE_HIGH_GAS_V2 = "pressure_high_gaz_V2";
    public static final String KEY_PRESSURE_HIGH_GAS_V3 = "pressure_high_gaz_V3";
    public static final String KEY_PRESSURE_HIGH_GAS_V4 = "pressure_high_gaz_V4";
    public static final String KEY_PRESSURE_HIGH_GAS_V5 = "pressure_high_gaz_V5";
    public static final String KEY_PRESSURE_HIGH_GAS_V6 = "pressure_high_gaz_V6";
    public static final String KEY_PRESSURE_HIGH_GAS_V7 = "pressure_high_gaz_V7";
    public static final String KEY_PRESSURE_HIGH_GAS_V8 = "pressure_high_gaz_V8";
    public static final String KEY_PRESSURE_HIGH_GAS_V9 = "pressure_high_gaz_V9";
    public static final String KEY_PRESSURE_HIGH_GAS_V10 = "pressure_high_gaz_V10";

    public static final String KEY_PRESSURE_LOW_GAS_V1 = "pressure_low_gaz_v1";
    public static final String KEY_PRESSURE_LOW_GAS_V2 = "pressure_low_gaz_v2";
    public static final String KEY_PRESSURE_LOW_GAS_V3 = "pressure_low_gaz_v3";
    public static final String KEY_PRESSURE_LOW_GAS_V4 = "pressure_low_gaz_v4";
    public static final String KEY_PRESSURE_LOW_GAS_V5 = "pressure_low_gaz_v5";
    public static final String KEY_PRESSURE_LOW_GAS_V6 = "pressure_low_gaz_v6";
    public static final String KEY_PRESSURE_LOW_GAS_V7 = "pressure_low_gaz_v7";
    public static final String KEY_PRESSURE_LOW_GAS_V8 = "pressure_low_gaz_v8";
    public static final String KEY_PRESSURE_LOW_GAS_V9 = "pressure_low_gaz_v9";
    public static final String KEY_PRESSURE_LOW_GAS_V10 = "pressure_low_gaz_v10";

    public static final String KEY_PRESSURE_OIL_V1 = "pressure_oil_v1";
    public static final String KEY_PRESSURE_OIL_V2 = "pressure_oil_v2";
    public static final String KEY_PRESSURE_OIL_V3 = "pressure_oil_v3";
    public static final String KEY_PRESSURE_OIL_V4 = "pressure_oil_v4";
    public static final String KEY_PRESSURE_OIL_V5 = "pressure_oil_v5";
    public static final String KEY_PRESSURE_OIL_V6 = "pressure_oil_v6";
    public static final String KEY_PRESSURE_OIL_V7 = "pressure_oil_v7";
    public static final String KEY_PRESSURE_OIL_V8 = "pressure_oil_v8";
    public static final String KEY_PRESSURE_OIL_V9 = "pressure_oil_v9";
    public static final String KEY_PRESSURE_OIL_V10 = "pressure_oil_v10";

    public static final String KEY_TIMER = "timer";

    public static final String KEY_INFO_CURRENT_V1 = "key_info_current_v1";
    public static final String KEY_INFO_CURRENT_V2 = "key_info_current_v2";
    public static final String KEY_INFO_CURRENT_V3 = "key_info_current_v3";
    public static final String KEY_INFO_CURRENT_V4 = "key_info_current_v4";
    public static final String KEY_INFO_CURRENT_V5 = "key_info_current_v5";
    public static final String KEY_INFO_CURRENT_V6 = "key_info_current_v6";
    public static final String KEY_INFO_CURRENT_V7 = "key_info_current_v7";
    public static final String KEY_INFO_CURRENT_V8 = "key_info_current_v8";
    public static final String KEY_INFO_CURRENT_V9 = "key_info_current_v9";
    public static final String KEY_INFO_CURRENT_V10 = "key_info_current_v10";


    public static final String KEY_TERMODISK_V1 = "key_teromdisk_v1";
    public static final String KEY_TERMODISK_V2 = "key_teromdisk_v2";

    public static final String _TYPE_SEND_MESSAGE = "a";
    public static final String _TYPE_RECEIVED_MESSAGE = "b";
    public static final String _TYPE_CREATE_USER = "c";
    public static final String _TYPE_EDIT_USER = "d";
    public static final String _TYPE_DELETE_USER = "e";
    public static final String _TYPE_CREATE_ROOM = "f";
    public static final String _TYPE_EDIT_ROOM = "g";
    public static final String _TYPE_DELETE_ROOM = "h";
    public static final String _STATE_COMPLETED = "i";
    public static final String _STATE_PENDING = "j";
    public static final String _STATE_CANCEL = "k";
    public static final String _REPORT_FROM_CLIENT = "l";
    public static final String _REPORT_FROM_SERVER = "m";
    public static String _REPORT_REGISTER = "n";
    public static final String _REPORT_SETTING = "o";
    public static final String _MANUAL_DEFROST = "p";
    public static final String _MANUAL_OVAPERATOR = "q";
    public static final String _MANUAL_COMPRESSOR = "r";
    public static final String _MANUAL_HIGHGAS = "s";
    public static final String _MANUAL_LOWGAS = "t";
    public static final String _MANUAL_OIL = "u";
    public static final String _MANUAL_TERMODISK = "y";

    public static final String ERROR_Full_DISCONNECT = "01";
    public static final String ERROR_MANUAL_DISCONNECT = "02";
    public static final String ERROR_Disconnect_PHASE_R = "03";
    public static final String ERROR_Disconnect_PHASE_S = "04";
    public static final String ERROR_Disconnect_PHASE_T = "05";
    public static final String ERROR_PHASE_FAILURE = "06";
    public static final String ERROR_PHASE_ASYMMETRY = "07";
    public static final String ERROR_INCREASE_VOLTAGE_R = "08";
    public static final String ERROR_INCREASE_VOLTAGE_S = "09";
    public static final String ERROR_INCREASE_VOLTAGE_T = "10";
    public static final String ERROR_DECREASE_VOLTAGE_R = "11";
    public static final String ERROR_DECREASE_VOLTAGE_S = "12";
    public static final String ERROR_DECREASE_VOLTAGE_T = "13";
    public static final String ERROR_COMPRESSOR_OVERLOAD = "14";
    public static final String ERROR_INCREASE_COMPRESSOR_CURRENT = "15";
    public static final String ERROR_DECREASE_COMPRESSOR_CURRENT = "16";
    public static final String ERROR_COMPRESSOR_ASYMMETRY = "17";
    public static final String ERROR_INCREASE_OVAPRATOR_CURRENT = "18";
    public static final String ERROR_DECREASE_OVAPRATOR_CURRENT = "19";
    public static final String ERROR_OVAPRATOR_FLOW_ASYMMETRY = "20";
    public static final String ERROR_HIGH_GAS = "21";
    public static final String ERROR_LOW_GAS = "22";
    public static final String ERROR_OIL = "23";


    public static final String INFORMATION_END_HEADER = "#";
    public static final String INFORMATION_START_HEADER = "#I";


    public static final String YES_ACTION = "YES_ACTION";
    public static final String STOP_ACTION = "STOP_ACTION";


    public static String REGISTRY_HEADER = "#R";

    public static String DIRECT_COMMAND_HEADER = "#C";
    public static String DIRECT_COMMAND_ORDER_TYPE = "00";

    public static HashMap<String, String> provincial_code = new HashMap<String, String>();
    public static HashMap<String, String> provincial_code_reverese = new HashMap<String, String>();


    public static HashMap<String, String> general_data_setting = new HashMap<String, String>();
    public static final HashMap<String, String> ERROR_MAP = new HashMap<>();

}
