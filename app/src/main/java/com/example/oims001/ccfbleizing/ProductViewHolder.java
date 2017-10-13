package com.example.oims001.ccfbleizing;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    TextView tv_product_name;
    NetworkImageView image_product;

    public ProductViewHolder(View itemView) {
        super(itemView);
        tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
        image_product = (NetworkImageView) itemView.findViewById(R.id.image_product);
    }
}