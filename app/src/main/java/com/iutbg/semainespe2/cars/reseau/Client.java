/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;


import android.util.Log;

import com.iutbg.semainespe2.cars.MainActivity;
import com.iutbg.semainespe2.cars.Traitement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Marc-Antoine
 */
public class Client implements Runnable {

    private MainActivity activity;

    private volatile String ip = null;

    private Socket socket = null;

    private Traitement traitement = null;
    private Thread threadTraitement = null;

    private Emission emission = null;

    private Reception reception = null;
    private Thread threadReception = null;

    private volatile boolean acceptResponse = false; // Response of the server just after you can control the car | Not In Use Now
    private volatile boolean closeDemand = false; // For end the connexion


    public Client(MainActivity activity) {
        this.activity = activity;
    }

    public Client(String ip) {
        this.ip = ip;
    }

    public void stop() {
        this.closeDemand = true;
    }

    public void send(String message) {
        if (emission != null) {
            emission.send(message);
        }
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public Traitement getTraitement() {
        return traitement;
    }


    @Override
    public void run() {

        /* At first, we try to connect if it succeed, we create the reception thread and emission object */

        while (ip == null) ;

        try {
            socket = new Socket(ip, 42424);
            Log.w("CARS_DEBUG", "Connexion établie à " + ip);

            emission = new Emission(socket);

            traitement = new Traitement(emission);
            threadTraitement = new Thread(traitement);
            threadTraitement.start();

            reception = new Reception(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            threadReception = new Thread(reception);
            threadReception.start();



        } catch (IOException ex) {
            Log.d("CARS_DEBUG", "Echec de connexion à " + ip);
            this.closeDemand = true;
        }
        synchronized (activity) {
            activity.notify();
        }

        /* We wait until the client want to disconnect */

        while (!closeDemand) ;

        /* We close the reception thread */

        if (threadReception != null) {
            reception.stop();
        }
        if (threadTraitement != null) {
            traitement.stop();
        }
    }
}
