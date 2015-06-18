package com.iutbg.semainespe2.cars;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    public static final String ADRESSE = "192.168.1.12";

    private MainFragment mainFragment;
    private MapsFragment mapsFragment;

    private boolean isMap = false;

    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_fragment);
        
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

        mapsFragment = new MapsFragment();
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
        if (id == R.id.action_map) {
            if(isMap){
                isMap = false;
                showFragment(mainFragment);
            }
            else {
                isMap = true;
                showFragment(mapsFragment);
            }
            return true;
        }

        return false;
    }

    public FragmentManager getFm() {
        return fm;
    }
}
