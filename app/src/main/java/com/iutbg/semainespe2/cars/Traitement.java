package com.iutbg.semainespe2.cars;

import com.iutbg.semainespe2.cars.reseau.Client;

/**
 * Created by Marc-Antoine on 24/09/2015.
 */
public class Traitement {

    public static final int MAX_SPEED_VALUE = 255;

    private Client mClient = null;

    private int left_motor = 0;
    private int turn_motor = 0; // -100 < left < 0 < right < 100
    private int right_motor = 0;

    public Traitement(Client mClient) {
        this.mClient = mClient;
    }

    public boolean updateValues(int x, int y){
        if(x > 100 || x < -100 || y > 100 || y < -100) return false;

        int turn_percent = x;

        left_motor = (int) (y*(float)(MAX_SPEED_VALUE/100));
        right_motor = left_motor;

        if(turn_percent < 0){
            left_motor -= x/5.0;
        }else if(turn_percent > 0){
            right_motor -= x/5.0;
        }

        return true;
    }

    public void send(){
        mClient.send(Integer.toString(left_motor) + '/' + Integer.toString(turn_motor) + '/' + Integer.toString(right_motor));
    }
}
