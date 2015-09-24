package com.iutbg.semainespe2.cars.reseau;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Marc-Antoine on 24/09/2015.
 */
public class FindProtocol extends AsyncTask<Void, Integer, String> {

    public FindProtocol(Context context){

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            DatagramSocket socket = new DatagramSocket();

            // send request
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 42425);
            socket.send(packet);

            // get response
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            // display response
            String received = new String(packet.getData(), 0, packet.getLength());
            Log.d("CARS", received);
            socket.close();
            return packet.getAddress().toString();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "127.0.0.1";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
