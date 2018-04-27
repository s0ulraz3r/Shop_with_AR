package com.example.kvaru.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductDisplayActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private ArrayList<ProductDataModel> product_list = new ArrayList<>();
    private ListView listView;
    
    Context context = ProductDisplayActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById(R.id.listView_catalog);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDataModel productDataModel = (ProductDataModel) parent.getItemAtPosition(position);
                Intent i = new Intent(ProductDisplayActivity.this,ProductActivity.class);
                i.putExtra("pName", productDataModel.getName());
                i.putExtra("pPrice", productDataModel.getPrice());
                i.putExtra("pDescription", productDataModel.getDescription());
//                i.putExtra("pRating", productDataModel.getRating());
                i.putExtra("pImgUrl", productDataModel.getImageUrl());
                i.putExtra("pKey", position);
                startActivity(i);
            }
        });

    mDatabase.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            fetchData(dataSnapshot);

            listView.setAdapter(new MyBaseAdapter(context,product_list));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            fetchData(dataSnapshot);
            listView.setAdapter(new MyBaseAdapter(context,product_list));
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


    }

    private void fetchData(DataSnapshot dataSnapshot) {
        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            ProductDataModel productDataModel = postSnapshot.getValue(ProductDataModel.class);
            product_list.add(productDataModel);
        }
    }
}
