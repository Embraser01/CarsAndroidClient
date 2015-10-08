package com.iutbg.semainespe2.cars;

import com.iutbg.semainespe2.cars.reseau.Emission;

/**
 * Created by Marc-Antoine on 24/09/2015.
 */
public class Traitement implements Runnable{

    public static final int MAX_SPEED_VALUE = 255;
    public static final int MAX_TURN_VALUE = 120;

    private volatile Emission emission = null;

    private volatile int old_left_motor = -1;
    private volatile int old_turn_motor = -1;
    private volatile int old_right_motor = -1;

    private volatile int left_motor = 0;
    private volatile int turn_motor = 0; // -100 < left < 0 < right < 100
    private volatile int right_motor = 0;

    private volatile boolean running = true;

    public Traitement(Emission emission) {
        this.emission = emission;
    }

    public void stop(){
        this.running = false;
    }

    public void updateValues(int x, int y) {

        right_motor = left_motor = y % (MAX_SPEED_VALUE + 1);
        turn_motor = x % (MAX_TURN_VALUE + 1);

        if (turn_motor < 0) {
            left_motor -= turn_motor / 8.0;
        } else if (turn_motor > 0) {
            right_motor -= turn_motor / 8.0;
        }
    }

    @Override
    public void run() {

        while(running) {
            if(left_motor != old_left_motor || right_motor != old_right_motor || turn_motor != old_turn_motor){
                emission.send(Integer.toString(left_motor) + '/' + Integer.toString(turn_motor) + '/' + Integer.toString(right_motor));

                old_left_motor = left_motor;
                old_right_motor = right_motor;
                old_turn_motor = turn_motor;
            }
        }
    }
}
