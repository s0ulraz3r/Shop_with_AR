package com.example.kvaru.shoppingapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageSwitcher imageSwitcher;
    private int[] ads_Images;
    private int position_ads_Images = 0;
    private Timer timer = null;
    protected DrawerLayout drawer;
    public TextView tvNavHeaderEmail;
    public ImageView ivProfile;
    String fbName;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        tvNavHeaderEmail = (TextView)header.findViewById(R.id.tvNavHeaderEmail);
        ivProfile = (ImageView)header.findViewById(R.id.ivProfile);

        Intent fbIntent = getIntent();
        fbName = fbIntent.getStringExtra("fbName");
        tvNavHeaderEmail.setText(fbName);

        Intent userIntent = getIntent();
        email = userIntent.getStringExtra("userEmail");
        try{
            if (!email.isEmpty() && email != null){
                tvNavHeaderEmail.setText(email);
            }
        }catch (Exception e){

        }

        ads_Images = new int[]{R.drawable.deals_1,R.drawable.deals_2,R.drawable.deals_3,R.drawable.deals_4,R.drawable.deals_5};
        imageSwitcher = (ImageSwitcher)findViewById(R.id.ads_img);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });
        start();





    }

    public void intentEmail_userName(){
        Intent newIntent = new Intent(getApplicationContext(),MyAccountActivity.class);
        newIntent.putExtra("fbName",fbName);
        newIntent.putExtra("userEmail",email);
        startActivity(newIntent);
    }
    public void start(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                runOnUiThread(new Runnable(){
                    public void run() {
                        imageSwitcher.setImageResource(ads_Images[position_ads_Images]);
                        position_ads_Images++;
                        if (position_ads_Images == 5)
                        {
                            position_ads_Images = 0;
                        }
            }
        });
    }
    },0,2500);
}


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FacebookSdk.sdkInitialize(getApplicationContext());
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(MainActivity.this,LogInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_orders) {
            Toast.makeText(this,"No Orders placed",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_my_account) {
            //startActivity(new Intent(this,MyAccountActivity.class));
            intentEmail_userName();
        } else if (id == R.id.nav_deal) {
            startActivity(new Intent(MainActivity.this,ProductDisplayActivity.class));

        } else if (id == R.id.nav_settings) {
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_service) {
            startActivity(new Intent(MainActivity.this,CustomerServiceActivity.class));
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
