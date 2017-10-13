package com.example.oims001.ccfbleizing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private ArrayList<Product> productArrayList;
    private Context context;
    private ImageLoader imageLoader;

    public ProductAdapter(ArrayList<Product> productArrayList, Context context) {
        this.productArrayList = productArrayList;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cardview, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Product product = productArrayList.get(position);
        imageLoader = VolleyImageRequest.getInstance(context).getImageLoader();
        String url = product.getProductImage();
        imageLoader.get(url, ImageLoader.getImageListener(holder.image_product, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        holder.image_product.setImageUrl(url, imageLoader);
        holder.tv_product_name.setText(product.getProductName());
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }
}