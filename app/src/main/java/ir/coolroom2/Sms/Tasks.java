package ir.coolroom2.Sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jefferson on 5/23/2017.
 */

public class Tasks extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, IncomingSms.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.sendBroadcast(i);
    }
}