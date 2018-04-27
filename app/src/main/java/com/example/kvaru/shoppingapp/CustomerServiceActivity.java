package com.example.kvaru.shoppingapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerServiceActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private ImageButton imgButtonCall, imgButtonMail;
    private TextView tVPhone,tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        imgButtonCall = (ImageButton)findViewById(R.id.imgButtonCall);
        tVPhone = (TextView) findViewById(R.id.tvPhone);
        imgButtonMail = (ImageButton) findViewById(R.id.imMail);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        imgButtonCall.setOnClickListener(this);
        imgButtonMail.setOnClickListener(this);

    }

    public void checkSdkVersion(){
        if(Build.VERSION.SDK_INT < 23){
        makeCall();
        }else {
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }else{
                final String[] PERMISSIONS_STORAGE = {android.Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted){
            makeCall();
        }else {
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }

    private void makeCall() {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            String phoneNumber = tVPhone.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
            startActivity(callIntent);
            }else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imMail:
                String emailStr = tvEmail.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{emailStr});
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent,"Send Mail"));
                break;
            case R.id.imgButtonCall:
                checkSdkVersion();
                break;
        }

    }
}
