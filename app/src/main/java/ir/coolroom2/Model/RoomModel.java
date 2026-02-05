package ir.coolroom2.Model;

/**
 * Created by Mohammad on 12/6/2017.
 */

public class RoomModel {
    private int _id;
    private String _number;
    private String _name;
    private String _location;
    private String _width;
    private String _height;
    private String _length;
    private String _thickness;
    private String _compressor;
    private String _ovaperator;
    private String _condonsor;
    private String _product_type;
    private boolean _under_zero;
    private String _description;
    private String _status;
    private String _date;

    public RoomModel(int _id) {
        this._id = _id;
    }

    public RoomModel(int _id, String _number, String _name, String _location, String _width, String _height, String _length, String _thickness, String _compressor, String _ovaperator, String _condonsor, String _product_type, boolean _under_zero, String _description, String _status) {
        this._id = _id;
        this._number = _number;
        this._name = _name;
        this._location = _location;
        this._width = _width;
        this._height = _height;
        this._length = _length;
        this._thickness = _thickness;
        this._compressor = _compressor;
        this._ovaperator = _ovaperator;
        this._condonsor = _condonsor;
        this._product_type = _product_type;
        this._under_zero = _under_zero;
        this._description = _description;
        this._status = _status;
    }

    public RoomModel(int _id, String _number, String _name, String _location, String _width, String _height, String _length, String _thickness, String _compressor, String _ovaperator, String _condonsor, String _product_type, boolean _under_zero, String _description, String _status, String _date) {
        this._id = _id;
        this._number = _number;
        this._name = _name;
        this._location = _location;
        this._width = _width;
        this._height = _height;
        this._length = _length;
        this._thickness = _thickness;
        this._compressor = _compressor;
        this._ovaperator = _ovaperator;
        this._condonsor = _condonsor;
        this._product_type = _product_type;
        this._under_zero = _under_zero;
        this._description = _description;
        this._status = _status;
        this._date = _date;
    }

    public RoomModel(String _number, String _name, String _location, String _width, String _height, String _length, String _thickness, String _compressor, String _ovaperator, String _condonsor, String _product_type, boolean _under_zero, String _description, String _status) {
        this._number = _number;
        this._name = _name;
        this._location = _location;
        this._width = _width;
        this._height = _height;
        this._length = _length;
        this._thickness = _thickness;
        this._compressor = _compressor;
        this._ovaperator = _ovaperator;
        this._condonsor = _condonsor;
        this._product_type = _product_type;
        this._under_zero = _under_zero;
        this._description = _description;
        this._status = _status;
    }

    public RoomModel(int _id,
                     String _number,
                     String _location,
                     String _product_type,
                     boolean _under_zero,
                     String _length,
                     String _width,
                     String _height,
                     String _thickness,
                     String _compressor,
                     String _condonsor,
                     String _ovaperator) {
        this._id = _id;
        this._number = _number;
        this._location = _location;
        this._width = _width;
        this._height = _height;
        this._length = _length;
        this._thickness = _thickness;
        this._compressor = _compressor;
        this._ovaperator = _ovaperator;
        this._condonsor = _condonsor;
        this._product_type = _product_type;
        this._under_zero = _under_zero;
        this._description = _description;
    }

    public RoomModel(int _id, String _name, String _description) {
        this._id = _id;
        this._name = _name;
        this._description = _description;
    }


    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_number() {
        return _number;
    }

    public void set_number(String _number) {
        this._number = _number;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_product_type() {
        return _product_type;
    }

    public void set_product_type(String _product_type) {
        this._product_type = _product_type;
    }

    public boolean is_under_zero() {
        return _under_zero;
    }

    public void set_under_zero(boolean _under_zero) {
        this._under_zero = _under_zero;
    }

    public String get_width() {
        return _width;
    }

    public void set_width(String _width) {
        this._width = _width;
    }

    public String get_height() {
        return _height;
    }

    public void set_height(String _height) {
        this._height = _height;
    }

    public String get_length() {
        return _length;
    }

    public void set_length(String _length) {
        this._length = _length;
    }

    public String get_thickness() {
        return _thickness;
    }

    public void set_thickness(String _thickness) {
        this._thickness = _thickness;
    }

    public String get_compressor() {
        return _compressor;
    }

    public void set_compressor(String _compressor) {
        this._compressor = _compressor;
    }

    public String get_ovaperator() {
        return _ovaperator;
    }

    public void set_ovaperator(String _ovaperator) {
        this._ovaperator = _ovaperator;
    }

    public String get_condonsor() {
        return _condonsor;
    }

    public void set_condonsor(String _condonsor) {
        this._condonsor = _condonsor;
    }

    public String get_date() {
        return _date;
    }
}
