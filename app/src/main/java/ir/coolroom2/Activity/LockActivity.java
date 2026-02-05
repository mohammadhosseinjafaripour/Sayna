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

public class LockActivity extends AppCompatActivity {

    PatternLockView lock;
    String lock_pattern;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        db = new DatabaseHandler(LockActivity.this);
        lock_pattern = db.getLockPattern();

        final TextView message = (TextView) findViewById(R.id.message);


        lock = (PatternLockView) findViewById(R.id.lock);
        lock.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if (lock_pattern.equals(PatternLockUtils.patternToString(lock, pattern))) {
                    message.setText("خوش آمدید!");
                    Intent mainIntent = new Intent(LockActivity.this, MainActivity.class);
                    LockActivity.this.startActivity(mainIntent);
                    LockActivity.this.finish();
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.clearPattern();
                    message.setText("نا معتبر!\nدوباره سعی کنید.");
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
