/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;


import android.util.Log;

import com.iutbg.semainespe2.cars.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Marc-Antoine
 */
public class Client implements Runnable {

    public static final int MAX_SPEED_VALUE = 255;
    public static final int MAX_TURN_VALUE = 120;


    private MainActivity activity;

    private volatile String ip = null;

    private Socket socket = null;

    private Emission emission = null;

    private Reception reception = null;
    private Thread threadReception = null;

    private volatile boolean running = true; // For end the connexion

    private volatile int old_left_motor = -1;
    private volatile int old_turn_motor = -1;
    private volatile int old_right_motor = -1;
    private volatile int old_inc = -1;

    private volatile int left_motor = 0;
    private volatile int turn_motor = 0; // -100 < left < 0 < right < 100
    private volatile int right_motor = 0;
    private volatile int inc = 0;


    public Client(MainActivity activity) {
        this.activity = activity;
    }

    public void stop() {
        this.running = false;
    }


    public void setIp(String ip) {
        this.ip = ip;
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

    public void updateIncl(int progress, int max) {
        inc = (progress * 100) / max;
    }


    @Override
    public void run() {

        /* At first, we try to connect if it succeed, we create the reception thread and emission object */

        while (ip == null) ;

        try {
            socket = new Socket(ip, 42424);
            Log.w("CARS_DEBUG", "Connexion établie à " + ip);

            emission = new Emission(socket);

            reception = new Reception(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            threadReception = new Thread(reception);
            threadReception.start();

        } catch (IOException ex) {
            Log.d("CARS_DEBUG", "Echec de connexion à " + ip);
            this.running = false;
        }
        synchronized (activity) {
            activity.notify();
        }

        /* We wait until the client want to disconnect while sending new value */

        while (running) {
            if (left_motor != old_left_motor || right_motor != old_right_motor || turn_motor != old_turn_motor || inc != old_inc) {
                emission.send(Integer.toString(left_motor) + '/' + Integer.toString(turn_motor) + '/' + Integer.toString(right_motor) + '/' + Integer.toString(old_inc));

                old_left_motor = left_motor;
                old_right_motor = right_motor;
                old_turn_motor = turn_motor;
                old_inc = inc;
            }
        }

        /* We close the reception thread */

        if (threadReception != null) {
            reception.stop();
        }
    }
}
