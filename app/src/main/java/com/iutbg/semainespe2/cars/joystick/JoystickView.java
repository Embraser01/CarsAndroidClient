package com.iutbg.semainespe2.cars.joystick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Marc-Antoine on 13/09/2015.
 */


public class JoystickView extends View implements Runnable {

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JoystickView(Context context) {
        super(context);
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void run() {

    }
}
