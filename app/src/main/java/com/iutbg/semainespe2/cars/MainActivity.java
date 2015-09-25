package com.iutbg.semainespe2.cars;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.iutbg.semainespe2.cars.reseau.FindProtocol;


public class MainActivity extends AppCompatActivity {

    private String ip = "192.168.43.126";

    private MainFragment mainFragment;

    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_fragment);

        searchForCar();

        setupFragments();


        showFragment(mainFragment);

    }

    private void showFragment(Fragment fragment) {
        if(fragment == null)
            return;


        ft = fm.beginTransaction();

        ft.replace(R.id.RelativeLayout, fragment);
        ft.commit();

    }

    private void setupFragments() {
        fm = this.getSupportFragmentManager();

        mainFragment = new MainFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.search_car_btn){
            this.searchForCar();
            return true;
        }

        return false;
    }

    public FragmentManager getFm() {
        return fm;
    }


    public void setIp(String ip){

        if(ip == "127.0.0.1"){
            this.finish();
        } else {
            this.ip = ip;
            mainFragment.setURL("http:/" + ip +"/cam_pic.php");
        }
    }

    public void searchForCar(){
        new FindProtocol(this).execute();
    }

    public String getIp() {
        return ip;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainFragment.terminate();
    }
}
