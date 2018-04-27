package com.example.kvaru.shoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by varun on 2/20/2018.
 */

public class MyBaseAdapter extends BaseAdapter {

    ArrayList<ProductDataModel> myList = new ArrayList();
    LayoutInflater inflater;
    Context context;

    public MyBaseAdapter(Context context, ArrayList<ProductDataModel> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_listview_items_layout, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        final ProductDataModel currentListData =(ProductDataModel) this.getItem(position);

        mViewHolder.textView_product_name.setText(currentListData.getName());
        mViewHolder.textView_product_description.setText(currentListData.getDescription());
        mViewHolder.textView_product_rating.setText(currentListData.getRating());
        mViewHolder.textView_product_price.setText(currentListData.getPrice());
        Glide.with(getApplicationContext()).load(currentListData.getImageUrl()).into(mViewHolder.imageView_product);
        mViewHolder.ratingBar_product.setRating(Float.parseFloat(currentListData.getRating()));
        return convertView;
    }

    private class MyViewHolder {
        TextView textView_product_name, textView_product_price,textView_product_description,textView_product_rating;
        ImageView imageView_product;
        RatingBar ratingBar_product;

        public MyViewHolder(View item) {
            textView_product_name = (TextView) item.findViewById(R.id.textView_product_name);
            textView_product_price= (TextView) item.findViewById(R.id.textView_product_price);
            textView_product_rating= (TextView) item.findViewById(R.id.textView_product_rating);
            textView_product_description= (TextView) item.findViewById(R.id.textView_product_description);
            imageView_product = (ImageView) item.findViewById(R.id.imageView_product);
            ratingBar_product = (RatingBar) item.findViewById(R.id.ratingBar_product);

        }
    }
}

