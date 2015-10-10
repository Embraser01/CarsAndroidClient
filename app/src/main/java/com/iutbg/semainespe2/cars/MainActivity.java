package com.iutbg.semainespe2.cars;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.iutbg.semainespe2.cars.reseau.Client;
import com.iutbg.semainespe2.cars.reseau.FindProtocol;


public class MainActivity extends AppCompatActivity {

    private String ip = "192.168.43.126";

    private MainFragment mainFragment;

    private Client client = null;
    private Thread threadClient = null;

    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_fragment);

        setupFragments();
        showFragment(mainFragment);

        client = new Client(this);
        threadClient = new Thread(client);

        searchForCar();
    }

    private void showFragment(Fragment fragment) {
        if (fragment == null) return;

        ft = fm.beginTransaction();

        ft.replace(R.id.RelativeLayout, fragment);
        ft.commit();
    }

    private void setupFragments() {
        fm = this.getSupportFragmentManager();

        mainFragment = new MainFragment();
    }


    public void setIp(String ip) {
        if (ip != "127.0.0.1") {
            this.ip = ip;
            client.setIp(this.ip);
            threadClient.start();

            synchronized (this){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mainFragment.setURL("http://" + this.ip + "/view.php", client);
        } else {
            this.finish();
        }
    }

    public void searchForCar() {
        new FindProtocol(this).execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mainFragment.disableJS();
        if (client != null)
            client.stop();
    }
}
