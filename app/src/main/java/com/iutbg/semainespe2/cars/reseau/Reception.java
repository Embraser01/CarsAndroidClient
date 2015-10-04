/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Marc-Antoine
 */

public class Reception implements Runnable {

    private BufferedReader in = null;
    private String message = null;

    public Reception(BufferedReader in) {
        this.in = in;
    }


    @Override
    public void run() {

        while (true) {
            try {
                message = in.readLine();
                Log.d("CARS", "New message from server : " + message);

                // TODO Gestion message retour ( pas besoin pour l'instant )

            } catch (IOException e) {
                Log.d("CARS", e.getMessage());
            }
        }
    }

}
