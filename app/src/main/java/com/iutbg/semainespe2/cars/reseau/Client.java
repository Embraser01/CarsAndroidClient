/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Marc-Antoine
 */
public class Client implements Runnable {

    private String ip;

    private Socket socket = null;
    private Reception reception = null;
    private Emission emission = null;

    private Thread threadReception = null;

    private volatile boolean acceptResponse = false; // Response of the server just after you can control the car | Not In Use Now
    private volatile boolean closeDemand = false; // For end the connexion


    public Client(String ip) {
        this.ip = ip;
    }

    public void terminate() {
        this.closeDemand = true;
    }

    public void send(String message){
        if(emission != null){
            emission.send(message);
        }
    }

    @Override
    public void run() {

        /* At first, we try to connect if it succeed, we create the reception thread and emission object */
        ip = ip.substring(1);

        try {
            socket = new Socket(ip, 42424);
            Log.w("CARS", "Connexion établie à " + ip);

            emission = new Emission(socket);

            reception = new Reception(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            threadReception = new Thread(reception);
            threadReception.start();

        } catch (IOException ex) {
            Log.d("CARS", "Echec de connexion à " + ip);
            this.closeDemand = true;
        }

        /* We wait until the client want to disconnect */

        while(!closeDemand);

        /* We close the reception thread */

        if(threadReception != null){
            threadReception.interrupt();
        }
    }
}
