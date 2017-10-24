package com.example.oims001.ccfbleizing;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragement extends Fragment {

    private static final String TAG = "ProductFragment";

    private ArrayList<Product> productArrayList;

    private RequestQueue requestQueue;

    private ProductAdapter productAdapter;


    public ProductFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_fragement, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).setActionBarTitle("Products");

        productArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getActivity());

        getDataFromServer();

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.product_recycler_view);
        productAdapter = new ProductAdapter(productArrayList, getActivity());
        recyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new MyClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = productAdapter.getProductArrayList().get(position);
                Toast.makeText(getActivity(), product.getProductName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getDataFromServer() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://api.azeano.com/api/atoz/index", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.w(TAG, response.toString());

                try {
                    JSONArray products = response.getJSONArray("products");

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject jsonObject = products.getJSONObject(i);
                        String productName = jsonObject.getString("productName");
                        String productImage = "http://s3-ap-southeast-1.amazonaws.com/azeano/uploads/" + jsonObject.getString("productImages");
                        Product product = new Product(productName,productImage);
                        productArrayList.add(product);
                    }
                    productAdapter.setProductArrayList(productArrayList);
                    productAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }
}
