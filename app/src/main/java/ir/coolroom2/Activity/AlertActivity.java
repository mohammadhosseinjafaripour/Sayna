package ir.coolroom2.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.PrepareDataAndSendSms;

import android.widget.ImageView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AlertActivity extends AppCompatActivity {

    Button btn_ok;
    TextView msg, title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);


        ImageView linableimg = (ImageView) findViewById(R.id.linableimg);
        linableimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.atis-cool.ir"));
                startActivity(intent);
            }
        });


        Config.alertActivity = AlertActivity.this;

        findView();
        final Bundle bundle = getIntent().getExtras();
        title.setText(bundle.getString("title"));
        msg.setText(bundle.getString("msg"));
        btn_ok.setText(bundle.getString("btn"));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle.getString("action", "close").equals("open")) {
                    finish();
                    Intent intent = new Intent(AlertActivity.this, Splash.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });
    }


    public void findView() {
        title = (TextView) findViewById(R.id.title);
        msg = (TextView) findViewById(R.id.msg);
        btn_ok = (Button) findViewById(R.id.ok);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
