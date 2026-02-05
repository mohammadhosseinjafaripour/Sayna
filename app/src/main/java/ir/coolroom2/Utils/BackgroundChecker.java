package ir.coolroom2.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ir.coolroom2.Activity.MainActivity;
import ir.coolroom2.Config;
import ir.coolroom2.R;

public class BackgroundChecker extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
//            DefaultSms();
        } catch (Exception e) {

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            /*Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
        } catch (Exception e) {

        }
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
//        Toast.makeText(this, "removed !", Toast.LENGTH_SHORT).show();
        Log.e("ClearFromRecentService", "END");
        //Code here
        stopSelf();
    }

    @Override
    public boolean stopService(Intent name) {
                  try {
             /*   Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            } catch (Exception e) {

        }
        Log.d("ClearFromRecentService", "Service Destroyed");
        return super.stopService(name);
    }

    public void DefaultSms() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

            if (!Telephony.Sms.getDefaultSmsPackage(this).equals(getPackageName())) {

                final Dialog dialog = new Dialog(Config.myActivity);
                dialog.setContentView(R.layout.dialog_custom_alert);
                dialog.setCancelable(false);
                LinearLayout ok = dialog.findViewById(R.id.ok);
                LinearLayout cancel = dialog.findViewById(R.id.cancel);
                TextView title = dialog.findViewById(R.id.title);
                TextView description = dialog.findViewById(R.id.description);
                cancel.setVisibility(View.GONE);
                TextView tive_btn_text = dialog.findViewById(R.id.posetive_btn_text);
                tive_btn_text.setText("تایید");
                title.setText("هشدار");
                description.setText("برای کارایی درست برنامه لازم است دسترسی مربوط به پیام را که در ادامه از شما پرسیده می شود بدهید - در غیر این صورت برنامه به درستی کار نخواهد کرد.");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        MainActivity mainActivity = new MainActivity();
                        mainActivity.showDialog();
                    }
                });
                dialog.show();


            }
        }
    }


}