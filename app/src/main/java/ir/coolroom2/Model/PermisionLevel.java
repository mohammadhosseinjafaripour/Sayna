package ir.coolroom2.Model;

/**
 * Created by Mohammad on 12/7/2017.
 */

public class PermisionLevel {
    int _user_id;
    int _room_id;
    int _permision_level;

    public PermisionLevel(int _user_id, int _room_id, int _permision_level) {
        this._user_id = _user_id;
        this._room_id = _room_id;
        this._permision_level = _permision_level;
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

    public int get_permision_level() {
        return _permision_level;
    }

    public void set_permision_level(int _permision_level) {
        this._permision_level = _permision_level;
    }
}
