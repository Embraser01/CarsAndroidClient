/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Marc-Antoine
 */

public class Emission {

    private volatile PrintWriter out;

    public Emission(Socket socket) {
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            out = null;
        }
    }

    public void send(String message) {
        if (out != null) {
            Log.d("CARS_DEBUG", "Envoi au serveur : " + message);

            out.println(message);
            out.flush();
        }

    }
}
