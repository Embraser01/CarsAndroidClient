/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Marc-Antoine
 */

public class Reception implements Runnable {

    private BufferedReader in;
    private Connexion co;
    private String message = null;

    public Reception(BufferedReader in, Connexion co){
        this.co = co;
        this.in = in;
    }

    @Override
    public void run() {

        while(true){
            try {

                message = in.readLine();
                System.out.println(message);
                System.out.println("Fin du message");
                if("00".equals(message.substring(0, 2))){
                    co.start();
                }

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
    }

}
