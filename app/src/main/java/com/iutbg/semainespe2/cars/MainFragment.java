package com.iutbg.semainespe2.cars;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iutbg.semainespe2.cars.reseau.Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{

    private ImageButton up;
    private ImageButton right;
    private ImageButton down;
    private ImageButton left;

    private volatile WebView cam;

    private ImageView img_cam;
    private DownloadImageTask dlTask;

    private Client mClient = null;

    private String URL = "http://" + MainActivity.ADRESSE +"/cam_pic.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);


        img_cam = (ImageView) v.findViewById(R.id.img_cam);

        up = (ImageButton) v.findViewById(R.id.up_arrow);
        right = (ImageButton) v.findViewById(R.id.right_arrow);
        down = (ImageButton) v.findViewById(R.id.down_arrow);
        left = (ImageButton) v.findViewById(R.id.left_arrow);
        //cam = (WebView) v.findViewById(R.id.cam);

        up.setOnClickListener(this);
        up.setOnTouchListener(this);

        right.setOnClickListener(this);
        right.setOnTouchListener(this);

        down.setOnClickListener(this);
        down.setOnTouchListener(this);

        left.setOnClickListener(this);
        left.setOnTouchListener(this);

        /*mClient = new Client();
        cam.loadUrl("http://"+ MainActivity.ADRESSE +"/cam_pic.php");


        mClient.start();


        synchronized (mClient) {
            TextView txt = null;
            txt = new TextView(this.getActivity());
            txt.setText("Connexion en cours");
            txt.setVisibility(View.VISIBLE);
            txt.setGravity(View.TEXT_ALIGNMENT_CENTER);
            ((RelativeLayout) v.findViewById(R.id.relativeLayout)).addView(txt);

            try {
                mClient.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            txt.setVisibility(View.INVISIBLE);

            if(mClient.isCo()){
                Toast.makeText(this.getActivity(),"Client connecté à " + MainActivity.ADRESSE,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this.getActivity(),"Impossible de se connecter à " + MainActivity.ADRESSE,Toast.LENGTH_SHORT).show();
                this.getActivity().finish();
            }
        }*/

        ValueAnimator animation = ValueAnimator.ofInt(0, 1);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(100);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    img_cam.setImageBitmap(new DownloadImageTask().execute().get());
                    img_cam.invalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (img_cam.getVisibility() != View.VISIBLE) {
                    animation.cancel();
                }
            }

        });
        animation.start();

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


    private class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {

        protected Bitmap doInBackground(Void... voids) {
            try {
                return BitmapFactory.decodeStream(new java.net.URL(URL).openStream());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
}
