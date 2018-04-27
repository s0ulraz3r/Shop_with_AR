package com.example.kvaru.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyAccountActivity extends AppCompatActivity {

    protected TextView tvUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        tvUser = (TextView) findViewById(R.id.tvUser);
        Intent newIntent = getIntent();
        String fbName = newIntent.getStringExtra("fbName");
        String userEmail = newIntent.getStringExtra("userEmail");

        if (fbName != null){
            tvUser.setText(fbName);
        }else {
            tvUser.setText(userEmail);
        }

    }
}
