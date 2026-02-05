package ir.coolroom2.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PatternActivity extends AppCompatActivity {

    private String tmp = "";
    private DatabaseHandler db;
    private TextView createPattern, renewPattern, deletePattern;
    private TextView message;
    private PatternLockView lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        db = new DatabaseHandler(PatternActivity.this);

        createPattern = (TextView) findViewById(R.id.createPattern);
        renewPattern = (TextView) findViewById(R.id.renewPattern);
        deletePattern = (TextView) findViewById(R.id.deletePattern);
        message = (TextView) findViewById(R.id.message);
        lock = (PatternLockView) findViewById(R.id.lock);

        String p = db.getLockPattern();

        if (p != null){
            if (!p.equals("")) {
                createPattern.setVisibility(View.GONE);
                renewPattern.setVisibility(View.VISIBLE);
                deletePattern.setVisibility(View.VISIBLE);

            }else{
                createPattern.setVisibility(View.VISIBLE);
                renewPattern.setVisibility(View.GONE);
                deletePattern.setVisibility(View.GONE);
            }
        }else{
            createPattern.setVisibility(View.VISIBLE);
            renewPattern.setVisibility(View.GONE);
            deletePattern.setVisibility(View.GONE);
        }

        createPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock.setVisibility(View.VISIBLE);
                message.setText(getResources().getString(R.string.drawPattern));

                createPattern.setVisibility(View.GONE);
                renewPattern.setVisibility(View.GONE);
                deletePattern.setVisibility(View.GONE);
            }
        });
        renewPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock.setVisibility(View.VISIBLE);
                message.setText(getResources().getString(R.string.drawPattern));

                createPattern.setVisibility(View.GONE);
                renewPattern.setVisibility(View.GONE);
                deletePattern.setVisibility(View.GONE);
            }
        });
        deletePattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(PatternActivity.this);
                dialog.setContentView(R.layout.dialog_custom_alert);
                ((TextView) dialog.findViewById(R.id.title)).setText("توجه");
                ((TextView) dialog.findViewById(R.id.description)).setText("آیا از حذف الگو اطمینان دارید؟");
                ((TextView) dialog.findViewById(R.id.posetive_btn_text)).setText("بله");
                ((TextView) dialog.findViewById(R.id.negative_btn_text)).setText("خیر");
                ((LinearLayout)dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createPattern.setVisibility(View.GONE);
                        renewPattern.setVisibility(View.GONE);
                        deletePattern.setVisibility(View.GONE);
                        db.setLockPattern("");
                        dialog.dismiss();
                        PatternActivity.this.finish();
                    }
                });
                ((LinearLayout)dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });




        lock.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if (tmp.equals("")) {
                    tmp = PatternLockUtils.patternToString(lock, pattern);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.clearPattern();
                    message.setText(getResources().getString(R.string.repeatPattern));
                } else {
                    if (PatternLockUtils.patternToString(lock, pattern).equals(tmp)) {
                        db.setLockPattern(PatternLockUtils.patternToString(lock, pattern));
                        PatternActivity.this.finish();
                    } else {
                        tmp = "";
                        message.setText("لطفا از ابتدا سعی کنید.");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        lock.clearPattern();
                    }
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
