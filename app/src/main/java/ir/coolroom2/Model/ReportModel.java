package ir.coolroom2.Model;

/**
 * Created by Mohammad on 1/24/2018.
 */

public class ReportModel {

    private int _id;
    private String _type;
    private String _state;
    private String _number;
    private String _date;
    private String _report_from;
    private int _user_id;
    private int _room_id;
    public String message;
    public String timelog;


    public ReportModel(int _id, String _type, String _state, String _number, String _date, String _report_from) {
        this._id = _id;
        this._type = _type;
        this._state = _state;
        this._number = _number;
        this._date = _date;
        this._report_from = _report_from;
    }

    public ReportModel(int _id, String _type, String _state, String _number, String _date, String _report_from, String _message) {
        this._id = _id;
        this._type = _type;
        this._state = _state;
        this._number = _number;
        this._date = _date;
        this._report_from = _report_from;
        this.message = _message;
    }

    public ReportModel(int _id, String _type, String _state, String _number, String _date, String _report_from, int _user_id, int _room_id) {
        this._id = _id;
        this._type = _type;
        this._state = _state;
        this._number = _number;
        this._date = _date;
        this._report_from = _report_from;
        this._user_id = _user_id;
        this._room_id = _room_id;
    }


    public ReportModel(int _id, String _type, String _state, String _number, String _date, String _report_from, String _message, String time) {
        this._id = _id;
        this._type = _type;
        this._state = _state;
        this._number = _number;
        this._date = _date;
        this._report_from = _report_from;
        this.message = _message;
        this.timelog = time;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }

    public String get_number() {
        return _number;
    }

    public void set_number(String _number) {
        this._number = _number;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_report_from() {
        return _report_from;
    }

    public void set_report_from(String _report_from) {
        this._report_from = _report_from;
    }

    public int get_user_id() {
        return _user_id;
    }

    public void set_user_id(int _user_id) {
        this._user_id = _user_id;
    }

    public int get_room_id() {
        return _room_id;
    }

    public void set_room_id(int _room_id) {
        this._room_id = _room_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimelog() {
        return timelog;
    }

    public void setTimelog(String timelog) {
        this.timelog = timelog;
    }

}
