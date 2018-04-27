package com.example.kvaru.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ProductActivity extends AppCompatActivity {

    private ImageView imgUrl;
    private TextView tvName, tvPrice, tvDesc;
    private Button viewARButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        imgUrl = (ImageView) findViewById(R.id.ivDisplay);
        tvName = (TextView) findViewById(R.id.tVName);
        tvDesc = (TextView) findViewById(R.id.tVDescription);
        tvPrice = (TextView) findViewById(R.id.tVPrice);
        viewARButton = (Button) findViewById(R.id.btnAR);
        Intent i = getIntent();
        final String pName = i.getExtras().get("pName").toString();
        String pPrice = i.getExtras().getString("pPrice");
        String pDescription = i.getExtras().getString("pDescription");
        String pImageUrl = i.getExtras().getString("pImgUrl");
        final int pPosition = i.getExtras().getInt("pKey");

        try{
            Glide.with(this).load(pImageUrl).into(imgUrl);
            tvName.setText(pName);
            tvDesc.setText(pDescription);
            tvPrice.setText(pPrice);
        }catch (Exception e){

        }

        viewARButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pPosition)
                {
                    case 0:
                        Intent("com.varun.lignesofa");
                        break;

                    case 1:
                        Intent("com.varun.armchairwiki");
                        break;

                    case 2:
                        Intent("com.varun.imola");
                        break;

                    case 3:
                        Intent("com.varun.blacksofa");
                        break;
                    case 4:
                        Intent("com.varun.slamwiki");
                        break;
                    case 5:
                        Intent("com.varun.table");
                        break;
                    case 6:
                        Intent("com.varun.circularchair");
                        break;

                    default:
                        Toast.makeText(getApplicationContext(),"AR NotFound",Toast.LENGTH_SHORT).show();
                        break;
                }


            }

    public void Intent(String packageName){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }
        });
    }
}
