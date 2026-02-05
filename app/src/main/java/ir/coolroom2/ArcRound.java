package ir.coolroom2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;

public class ArcRound extends android.support.v7.widget.AppCompatImageView {
    public int indexSaat;
    public int[] minute10;
    public Paint paint1;
    public Paint paint2;
    public Paint paint3;
    public RectF rect1;
    public RectF rect3;
    public int indicatorStart;
    public int strokeWidth = 10;

    public ArcRound(Context context) {
        super(context);
        this.indexSaat = 1;
        this.minute10 = new int[144];
        this.indicatorStart = 0;
        rect1 = new RectF();
        rect3 = new RectF();
        this.paint1 = new Paint();
        this.paint2 = new Paint();
        initialize(context);
    }

    public ArcRound(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.indexSaat = 1;
        this.minute10 = new int[144];
        this.indicatorStart = 0;
        rect1 = new RectF();
        rect3 = new RectF();
        this.paint1 = new Paint();
        this.paint2 = new Paint();
        initialize(context);
        this.strokeWidth = attrs.getAttributeIntValue(R.attr.stroke_width, 18);

    }

    public ArcRound(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.indexSaat = 1;
        this.minute10 = new int[144];
        this.indicatorStart = 0;
        rect1 = new RectF();
        rect3 = new RectF();
        this.paint1 = new Paint();
        this.paint2 = new Paint();
        initialize(context);
        this.strokeWidth = attrs.getAttributeIntValue(R.attr.stroke_width, 18);
    }

    private void initialize(Context context) {
        this.paint3 = new Paint();
        this.paint3.setColor(Color.parseColor("#88FFF200"));
        this.paint3.setAntiAlias(true);
        this.paint3.setStyle(Style.STROKE);
        this.paint3.setStrokeWidth((float) 3f);
        this.paint3.setStyle(Style.FILL);
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        this.paint1.setColor(Color.parseColor("#337dc2"));//blue
        this.paint1.setAntiAlias(true);
        this.paint1.setStyle(Style.STROKE);
        this.paint1.setStrokeWidth((float) this.strokeWidth);

        this.paint2.setColor(Color.parseColor("#ff0000"));
        this.paint2.setAntiAlias(true);
        this.paint2.setStyle(Style.STROKE);
        this.paint2.setStrokeWidth((float) this.strokeWidth);

        rect1.left = (float) (this.strokeWidth);
        rect1.right = (float) (getWidth() - this.strokeWidth);
        rect1.top = (float) (this.strokeWidth);
        rect1.bottom = (float) (getHeight() - this.strokeWidth);
        //INDICATOR ↓
        rect3.left = (float) (this.strokeWidth);
        rect3.right = (float) ((getWidth() - this.strokeWidth));
        rect3.top = (float) (this.strokeWidth);
        rect3.bottom = (float) ((getHeight() - this.strokeWidth));
        canvas.drawArc(rect3, (float) this.indicatorStart, 15.0f, true, this.paint3);
        for (int i = 0; i < 144; i++) {
            if (this.minute10[i] == 1) {
                canvas.drawArc(rect1, (float) ((((double) i) * 2.5d) - 90.0d), 2.5f, false, this.paint2);
            } else {
                canvas.drawArc(rect1, (float) ((((double) i) * 2.5d) - 90.0d), 2.5f, false, this.paint1);

            }
        }
    }
}
