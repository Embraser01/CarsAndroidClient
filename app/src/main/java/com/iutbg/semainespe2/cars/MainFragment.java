package com.iutbg.semainespe2.cars;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.iutbg.semainespe2.cars.reseau.Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {


    private ImageView img_cam;

    private Client mClient = null;

    private String URL = "http://" + MainActivity.ADRESSE +"/cam_pic.php";
    private URL img_stream = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);



        img_cam = (ImageView) v.findViewById(R.id.img_cam);


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


    private class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {

        protected Bitmap doInBackground(Void... voids) {
            if(img_stream == null){
                try {
                    img_stream = new URL(URL);
                    Log.d("CARS","Stream open webcam");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                return BitmapFactory.decodeStream(img_stream.openStream());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
}
