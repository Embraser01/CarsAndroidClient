package com.iutbg.semainespe2.cars;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.iutbg.semainespe2.cars.control.JoystickView;
import com.iutbg.semainespe2.cars.control.VerticalSeekBar;
import com.iutbg.semainespe2.cars.reseau.Client;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {


    private WebView img_cam = null;

    private JoystickView joystickView = null;
    private VerticalSeekBar verticalSeekBar = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        joystickView = (JoystickView) v.findViewById(R.id.joystick);
        verticalSeekBar = (VerticalSeekBar) v.findViewById(R.id.seekbar);
        img_cam = (WebView) v.findViewById(R.id.img_cam);
        img_cam.getSettings().setJavaScriptEnabled(true);

        return v;
    }

    public void disableJS() {
        img_cam.getSettings().setJavaScriptEnabled(false);
    }

    public void setURL(String url, Client client) {

        img_cam.loadUrl(url);
        joystickView.setClient(client);
        verticalSeekBar.setClient(client);
    }
}
