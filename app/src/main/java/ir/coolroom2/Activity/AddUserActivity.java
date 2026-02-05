package ir.coolroom2.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.PrepareDataAndSendSms;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AddUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText userName_et;
    EditText userFamily_et;
    EditText userMobile_et;

    RadioGroup permisionLevel;

    DatabaseHandler db;
    PrepareDataAndSendSms prepareDataAndSendSms;
    RoomModel roomModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        prepareDataAndSendSms = new PrepareDataAndSendSms(AddUserActivity.this);
        db = new DatabaseHandler(AddUserActivity.this);

        userName_et = (EditText) findViewById(R.id.userName_et);
        userFamily_et = (EditText) findViewById(R.id.userFamily_et);
        userMobile_et = (EditText) findViewById(R.id.userMobile_et);
        Button save = (Button) findViewById(R.id.save);
        permisionLevel = (RadioGroup) findViewById(R.id.permosionLevel);

        roomModel = db.getRoom(Config.currentRoomID);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!userName_et.getText().toString().matches("") &&
                        !userFamily_et.getText().toString().matches("") &&
                        !userMobile_et.getText().toString().matches("") && permisionLevel.getCheckedRadioButtonId() != -1) {

                    if (userMobile_et.getText().toString().substring(0, 2).equals("09") &&
                            userMobile_et.getText().toString().length() == 11) {
                        userMobile_et.setError(null);
                        String permision = "";
                        switch (permisionLevel.getCheckedRadioButtonId()) {
                            case R.id.one:
                                permision = "one";
                                break;
                            case R.id.two:
                                permision = "two";
                                break;
                            case R.id.three:
                                permision = "three";
                                break;
                        }

                        int count = db.createUser(new UserModel(
                                userName_et.getText().toString(),
                                userFamily_et.getText().toString(),
                                userMobile_et.getText().toString(),
                                permision,
                                Config.currentRoomID));


                        if (count == 0) {
                            prepareDataAndSendSms.send_registery_data(
                                    db.getRoom(Config.currentRoomID).get_number(),
                                    "M",
                                    "P",
                                    (permisionLevel.getCheckedRadioButtonId() == R.id.two) ? "2" : "3",
                                    userMobile_et.getText().toString(),
                                    Config.provincial_code.get(roomModel.get_location()),
                                    roomModel.get_product_type(),
                                    (roomModel.is_under_zero() ? "1" : "0"),
                                    roomModel.get_length(),
                                    roomModel.get_width(),
                                    roomModel.get_height(),
                                    roomModel.get_thickness(),
                                    roomModel.get_compressor(),
                                    roomModel.get_condonsor(),
                                    roomModel.get_ovaperator());


                            db.createOrder("",
                                    db.getRoom(Config.currentRoomID).get_number(),
                                    Config._STATE_COMPLETED,
                                    Config._REPORT_FROM_CLIENT,
                                    "",
                                    new HashMap<String, String>(),
                                    Config._TYPE_CREATE_USER,
                                    Config.currentRoomID,
                                    db.getFirstUser_for_room(Config.currentRoomID).get_id());
                            finish();

//                    startActivity(new Intent(AddUserActivity.this, UsersActivity.class));
                        }
                    } else {
                        userMobile_et.setError("شماره باید 11 رقم بوده و با 09 شروع شود.");
                    }
                } else {
                    Toast.makeText(AddUserActivity.this, "خطا لطفا تمامی فیلد ها رو پر کنید. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
