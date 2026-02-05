package ir.coolroom2.Model;

/**
 * Created by Mohammad on 12/6/2017.
 */

public class UserModel {
    int _id = -1;
    String _name = "";
    String _family = "";
    String _mobile = "";
    String _permision_level = "";
    int _room_id = -1;
    String date = "";
    String status;


    public UserModel(int _id, String _name, String _family, String _mobile, String _permision_level, int _room_id) {
        this._id = _id;
        this._name = _name;
        this._family = _family;
        this._mobile = _mobile;
        this._permision_level = _permision_level;
        this._room_id = _room_id;
    }

    public UserModel(int _id, String _name, String _family, String _mobile, String _permision_level, int _room_id, String Status) {
        this._id = _id;
        this._name = _name;
        this._family = _family;
        this._mobile = _mobile;
        this._permision_level = _permision_level;
        this._room_id = _room_id;
        this.status = Status;
    }


    public UserModel(String _name, String _family, String _mobile, String _permision_level, int _room_id) {
        this._name = _name;
        this._family = _family;
        this._mobile = _mobile;
        this._permision_level = _permision_level;
        this._room_id = _room_id;
    }

    public UserModel(String _name, String _family, String _mobile, String _permision_level) {
        this._name = _name;
        this._family = _family;
        this._mobile = _mobile;
        this._permision_level = _permision_level;
    }

    public UserModel(int _id, String _name, String _family) {
        this._id = _id;
        this._name = _name;
        this._family = _family;
    }

    public UserModel(int _id, String _permision_level) {
        this._id = _id;
        this._permision_level = _permision_level;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_family() {
        return _family;
    }

    public void set_family(String _family) {
        this._family = _family;
    }

    public String get_mobile() {
        return _mobile;
    }

    public void set_mobile(String _mobile) {
        this._mobile = _mobile;
    }

    public String get_permision_level() {
        return _permision_level;
    }

    public void set_permision_level(String _permision_level) {
        this._permision_level = _permision_level;
    }

    public int get_room_id() {
        return _room_id;
    }

    public void set_room_id(int _room_id) {
        this._room_id = _room_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
