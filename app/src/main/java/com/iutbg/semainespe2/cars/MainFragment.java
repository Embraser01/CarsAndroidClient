package com.iutbg.semainespe2.cars;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.iutbg.semainespe2.cars.reseau.Client;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{

    private ImageButton up;
    private ImageButton right;
    private ImageButton down;
    private ImageButton left;

    private volatile WebView cam;

    private Client mClient = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        up = (ImageButton) v.findViewById(R.id.up_arrow);
        right = (ImageButton) v.findViewById(R.id.right_arrow);
        down = (ImageButton) v.findViewById(R.id.down_arrow);
        left = (ImageButton) v.findViewById(R.id.left_arrow);
        cam = (WebView) v.findViewById(R.id.cam);


        up.setOnClickListener(this);
        up.setOnTouchListener(this);

        right.setOnClickListener(this);
        right.setOnTouchListener(this);

        down.setOnClickListener(this);
        down.setOnTouchListener(this);

        left.setOnClickListener(this);
        left.setOnTouchListener(this);

        mClient = new Client("192.168.43.126");
        cam.loadUrl("http://192.168.43.126/cam_pic.php");
        for(int i = 0; i < 50; i++)
            cam.zoomOut();

        Thread t = new Thread(mClient);
        t.start();

        Thread t2 = new Thread(new Camera(cam,"http://192.168.43.126/cam_pic.php"));
        t2.start();
        return v;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.up_arrow:
                up.setBackgroundColor(Color.TRANSPARENT);
                mClient.getCo().setUp(false);
                break;
            case R.id.right_arrow:
                right.setBackgroundColor(Color.TRANSPARENT);
                mClient.getCo().setRight(false);
                break;
            case R.id.down_arrow:
                down.setBackgroundColor(Color.TRANSPARENT);
                mClient.getCo().setDown(false);
                break;
            case R.id.left_arrow:
                left.setBackgroundColor(Color.TRANSPARENT);
                mClient.getCo().setLeft(false);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch(v.getId()){
            case R.id.up_arrow:
                up.setBackgroundColor(Color.GREEN);
                mClient.getCo().setUp(true);
                break;
            case R.id.right_arrow:
                right.setBackgroundColor(Color.GREEN);
                mClient.getCo().setRight(true);
                break;
            case R.id.down_arrow:
                down.setBackgroundColor(Color.GREEN);
                mClient.getCo().setDown(true);
                break;
            case R.id.left_arrow:
                left.setBackgroundColor(Color.GREEN);
                mClient.getCo().setLeft(true);
                break;
        }

        return false;
    }


    private class Camera implements Runnable {

        private String url;
        private WebView cam;

        public Camera(WebView cam, String url){
            this.cam = cam;
            this.url = url;
        }

        @Override
        public void run() {
            while(true) {
                cam.reload();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
