package com.iutbg.semainespe2.cars.reseau;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.iutbg.semainespe2.cars.MainActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * Created by Marc-Antoine on 24/09/2015.
 */
public class FindProtocol extends AsyncTask<Void, Integer, String> {

    private ProgressDialog dialog = null;
    private MainActivity activity = null;
    private String ip = "127.0.0.1";

    public FindProtocol(MainActivity activity) {
        this.activity = activity;
        dialog = new ProgressDialog(activity);
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("Recherche en cours");
        dialog.setMessage("L'application se fermera si elle ne trouve pas la voiture");
        dialog.setCanceledOnTouchOutside(false);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {

        DatagramSocket socket = null;

        InetAddress address = null;
        DatagramPacket packet = null;
        byte[] buf = new byte[256];

        try {

            socket = new DatagramSocket();
            socket.setSoTimeout(3000);

            // send request
            address = InetAddress.getByName("255.255.255.255");
            packet = new DatagramPacket(buf, buf.length, address, 42425);
            socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }


        if (socket != null) {
            try {
                // get response
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);


                // display response
                String received = new String(packet.getData(), 0, packet.getLength());
                Log.d("CARS_DEBUG", received);


                ip = packet.getAddress().toString().substring(1);

                socket.close();

            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ip;
    }

    @Override
    protected void onPostExecute(String s) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        activity.setIp(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
