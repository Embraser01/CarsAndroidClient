/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marc-Antoine
 */
public class Connexion implements Runnable{
    
    private Socket socket = null;

    private Scanner scan = null;
    private String adresseIp = null;
    private String numeroPort = null;
    
    private volatile boolean up = false;
    private volatile boolean right = false;
    private volatile boolean down = false;
    private volatile boolean left = false;
    
    private volatile boolean modif = true;
    private volatile boolean actif = false;
    

    public Connexion(Socket socket) {
        this.socket = socket;
        
    }
    
    public void start(){
        this.actif = true;
    }

    @Override
    public void run() {
        
        Emission emission = new Emission(socket);
        
        Thread threadReception = null;
        
        try {
            threadReception = new Thread(new Reception(new BufferedReader(new InputStreamReader(socket.getInputStream())),this));
        } catch (IOException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        threadReception.start();
        
        while(true){
            if(actif)
                break;
        }
        System.out.println("Activation communication");
        while(true){
            if(modif){
                modif = false;
                
                if(up){
                    emission.emettre("10 - Avancer");
                }
                else{
                    emission.emettre("20 - Stop Avancer");
                }
                
                if(right){
                    emission.emettre("11 - Droite");
                }
                else{
                    emission.emettre("21 - Stop Droite");
                }
                
                if(down){
                    emission.emettre("12 - Arrière");
                }
                else{
                    emission.emettre("22 - Stop Arrière");
                }
                
                if(left){
                    emission.emettre("13 - Gauche");
                }
                else{
                    emission.emettre("23 - Stop Gauche");
                }
            }
        }
    }
    
    
    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
        modif = true;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
        modif = true;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
        modif = true;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
        modif = true;
    }
}
