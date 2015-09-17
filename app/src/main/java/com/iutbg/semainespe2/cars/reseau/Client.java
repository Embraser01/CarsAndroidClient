/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;


import com.iutbg.semainespe2.cars.MainActivity;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Marc-Antoine
 */
public class Client implements Runnable {

    private final String ip;


    private static Socket socket = null;
    private static Thread threadCo;
    private volatile Connexion co;

    private boolean isCo = false;

    public Client(String ip) {
        this.ip = ip;
    }


    public Connexion getCo() {
        return co;
    }

    public boolean isCo() {
        return isCo;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(MainActivity.ADRESSE, 42424);
            System.out.println("Connexion établie a " + MainActivity.ADRESSE + ", authentification :");
            co = new Connexion(socket);
            threadCo = new Thread(co);
            threadCo.start();

            isCo = true;


        } catch (IOException ex) {
            isCo = false;
        }
        notify();

    }
}
