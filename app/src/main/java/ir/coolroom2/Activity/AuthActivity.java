package ir.coolroom2.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AuthActivity extends AppCompatActivity {

    DatabaseHandler db;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        db = new DatabaseHandler(AuthActivity.this);
        message = (TextView) findViewById(R.id.message);

        if (db.getLockPattern() == null) {
            startActivity(new Intent(AuthActivity.this, PatternActivity.class));
            AuthActivity.this.finish();
        } else {
            if (db.getLockPattern().equals("")) {
                startActivity(new Intent(AuthActivity.this, PatternActivity.class));
                AuthActivity.this.finish();
            }
        }

        final PatternLockView lock = (PatternLockView) findViewById(R.id.lock);
        lock.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                if (db.getLockPattern().equals(PatternLockUtils.patternToString(lock, pattern))) {
                    lock.clearPattern();
                    startActivity(new Intent(AuthActivity.this, PatternActivity.class));
                    AuthActivity.this.finish();
                } else {
                    message.setText("دوباره تلاش کنید.");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.clearPattern();
                }


            }

            @Override
            public void onCleared() {

            }
        });
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
