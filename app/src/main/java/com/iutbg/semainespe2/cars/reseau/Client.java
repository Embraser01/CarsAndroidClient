/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Marc-Antoine
 */
public class Client implements Runnable{

    private static Socket socket = null;
    private static Thread t1;
    private String adresse;
    private volatile Connexion co;

    public Client() {
        System.out.println("Adresse IP du Robot ?");
        this.adresse = (new Scanner(System.in)).next();
    }

    public Client(String adresse) {
        this.adresse = adresse;

    }

    public Connexion getCo() {
        return co;
    }


    @Override
    public void run() {
        try {
            socket = new Socket(adresse,42424);
            System.out.println("Connexion établie a " + adresse + ", authentification :");
            co = new Connexion(socket);
            t1 = new Thread(co);
            t1.start();
            //socket.close();


        } catch (IOException ex) {
            System.out.println("Impossible de se connecter au robot (mauvaise adresse ?)");
        }
    }
}
