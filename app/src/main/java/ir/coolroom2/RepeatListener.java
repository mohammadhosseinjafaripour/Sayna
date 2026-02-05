package ir.coolroom2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RepeatListener implements View.OnTouchListener {

    private Handler handler = new Handler();

    private int initialInterval;
    private final int normalInterval;
    private final View.OnClickListener clickListener;
    public Boolean up_down;
    public Float Min_num, Max_num;
    public Float Count_num;
    public Float result = 0f;
    public float start_num = 0f;
    Float temp;
    Context context;

    public boolean distance_flag = true;

    private Runnable handlerRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, normalInterval);
            clickListener.onClick(downView);
        }
    };

    private View downView;
    Boolean flag;

    public RepeatListener(int initialInterval, int normalInterval, Float Max_num, Float Min_num, Float Count_num, Boolean up_down, float start_num,
                          View.OnClickListener clickListener, Context context) {
        if (clickListener == null)
            throw new IllegalArgumentException("null runnable");
        if (initialInterval < 0 || normalInterval < 0)
            throw new IllegalArgumentException("negative interval");

        this.initialInterval = 1000;
        this.normalInterval = normalInterval;
        this.clickListener = clickListener;
        this.Count_num = Count_num;
        this.Max_num = Max_num;
        this.Min_num = Min_num;
        this.up_down = up_down;
        this.context = context;
        result = start_num;
        temp = Count_num;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                handler.removeCallbacks(handlerRunnable);
                handler.postDelayed(handlerRunnable, initialInterval);
                downView = view;
                downView.setPressed(true);
                clickListener.onClick(view);

                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacks(handlerRunnable);
                downView.setPressed(false);
                downView = null;
                return true;
        }

        return false;
    }

    boolean show_one_time = true;
    boolean show_one_time_second = true;

    @SuppressLint("ResourceAsColor")
    public void check(Float Max_num, Float Min_num, Float Count_num, Boolean up_down, Context context) {
        distance_flag = true;
        if (up_down) {
            if (Max_num <= result) {
                if (show_one_time) {
                    show_one_time = false;
                    distance_flag = false;
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
                    top.setBackgroundColor(Color.parseColor("#ffa600"));
                    title.setText("هشدار");
                    title.setTextColor(Color.parseColor("#000000"));
                    Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    title.setTypeface(typeface);
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    String result_to_show = "";
                    if (Max_num >= 0) {
                        //positve
                        result_to_show = Math.round(Max_num) + "";
                    } else {
//                        negetive
                        result_to_show = Math.abs(Math.round(Max_num)) + "-";
                    }
                    description.setText("بیشترین مقدار وارد شده می تواند " + result_to_show + " باشد. ");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    posetive_btn_text.setText("تایید");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            show_one_time = true;
                        }
                    });

                    cancel.setVisibility(View.GONE);

                    dialog.setCancelable(false);
                    dialog.show();
                }
            } else {
                result += Count_num++;
            }
        } else {
            distance_flag = true;
            if (Min_num >= result) {
                if (show_one_time_second) {
                    show_one_time_second = false;
                    distance_flag = false;
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    LinearLayout top = (LinearLayout) dialog.findViewById(R.id.top);
                    top.setBackgroundColor(Color.parseColor("#ffa600"));
                    title.setText("اخطار");
                    title.setTextColor(Color.parseColor("#000000"));
                    Typeface typeface = Typeface.defaultFromStyle(Typeface.BOLD);
                    title.setTypeface(typeface);
                    TextView description = (TextView) dialog.findViewById(R.id.description);

                    String result_to_show = "";
                    if (Min_num >= 0) {
                        //positve
                        result_to_show = Math.round(Min_num) + "";
                    } else {
//                        negetive
                        result_to_show = Math.abs(Math.round(Min_num)) + "-";
                    }

                    description.setText("کمترین مقدار وارد شده می تواند " + result_to_show + " باشد. ");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    posetive_btn_text.setText("تایید");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            show_one_time_second = true;
                        }
                    });

                    cancel.setVisibility(View.GONE);

                    dialog.setCancelable(false);
                    dialog.show();
                }


            } else {
                result -= Count_num--;
            }
        }

    }

}