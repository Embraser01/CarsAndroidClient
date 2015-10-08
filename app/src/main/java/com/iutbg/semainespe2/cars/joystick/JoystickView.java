package com.iutbg.semainespe2.cars.joystick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.iutbg.semainespe2.cars.Traitement;

/**
 * Created by Marc-Antoine on 13/09/2015.
 */


public class JoystickView extends View {

    private int diameter = -1;
    private int small_diameter = -1;
    private int cx = 0;
    private int cy = 0;

    private int position_x = 0;
    private int position_y = 0;

    private Paint paint;

    private volatile Traitement traitement = null;


    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public JoystickView(Context context) {
        super(context);
        paint = new Paint();
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public void setTraitement(Traitement traitement) {
        this.traitement = traitement;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.sqrt(Math.pow((event.getX() - cx), 2) + Math.pow((event.getY() - cy), 2)) < diameter / 2.0) {
                position_x = (int) event.getX();
                position_y = (int) event.getY();
            }
        } else {
            position_x = cx;
            position_y = cy;
        }

        if (traitement != null) {
            int x = ((position_x * 2 *Traitement.MAX_TURN_VALUE) / diameter) - Traitement.MAX_TURN_VALUE;
            int y = ((position_y * 2 *Traitement.MAX_SPEED_VALUE) / diameter) - Traitement.MAX_SPEED_VALUE;
            traitement.updateValues(x, y);
        }

        this.invalidate();
        super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.GRAY);
        paint.setAlpha(150);
        canvas.drawCircle(cx, cy, diameter / 2, paint);

        paint.setAlpha(0);
        canvas.drawCircle(cx, cy, diameter / 2 - 5, paint);

        paint.setColor(Color.DKGRAY);
        canvas.drawCircle(position_x, position_y, small_diameter / 2, paint);

        paint.setColor(Color.GRAY);
        paint.setAlpha(100);
        canvas.setDensity(5);
        canvas.drawLine(cx - diameter / 18, cy, cx + diameter / 18, cy, paint);
        canvas.drawLine(cx, cy - diameter / 18, cx, cy + diameter / 18, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // setting the measured values to resize the view to a certain width and
        // height
        diameter = Math.min(measure(widthMeasureSpec), measure(heightMeasureSpec));

        small_diameter = diameter / 5;

        cx = diameter / 2;
        cy = diameter / 2;

        position_x = cx;
        position_y = cy;

        setMeasuredDimension(diameter, diameter);
    }


    private int measure(int measureSpec) {
        int result = 0;

        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            // Return a default size of 200 if no bounds are specified.
            result = 200;
        } else {
            // As you want to fill the available space
            // always return the full available bounds.
            result = specSize;
        }
        return result;
    }
}
