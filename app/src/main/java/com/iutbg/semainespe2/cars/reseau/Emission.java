/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iutbg.semainespe2.cars.reseau;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Marc-Antoine
 */
public class Emission {
    
    private Socket socket;
    private PrintWriter out;

    public Emission(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            out = null;
        }
    }
    
    public void emettre(String message){
        if(out != null){
            System.out.println("Envoi : " + message);
            out.println(message);
            out.flush();
        }
        
    }

    

    
}
