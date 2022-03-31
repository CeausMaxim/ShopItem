package com.example.shopitem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopitem.adapter.MyProductAdapter;
import com.example.shopitem.listener.ICartLoadListener;
import com.example.shopitem.listener.IDProductLoadListener;
import com.example.shopitem.model.CartModel;
import com.example.shopitem.model.ProductModel;
import com.example.shopitem.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements IDProductLoadListener, ICartLoadListener{
    @BindView(R.id.recycler_products)
    RecyclerView recyclerProducts;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.badge)
    NotificationBadge badge;
    @BindView(R.id.btnCart)
    FrameLayout btnCart;

    IDProductLoadListener productLoadListener;
    ICartLoadListener cartLoadListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_product);


        init();
        loadProductFromFirebase();
    }

    private void loadProductFromFirebase() {
        List<ProductModel> productModels = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Drink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            for (DataSnapshot productSnapshot:snapshot.getChildren())
                            {
                                ProductModel productModel = productSnapshot.getValue(ProductModel.class);
                                productModel.setKey(productSnapshot.getKey());
                                productModels.add(productModel);
                            }
                            productLoadListener.onProductLoadSuccess(productModels);
                        }
                        else
                            productLoadListener.onProductLoadFailed("Can't find Product");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        productLoadListener.onProductLoadFailed(error.getMessage());
                    }
                });
    }

    private void init() {
        ButterKnife.bind(this);

        productLoadListener = this;
        cartLoadListener = this;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerProducts.setLayoutManager(gridLayoutManager);
        recyclerProducts.addItemDecoration(new SpaceItemDecoration());
    }

    @Override
    public void onProductLoadSuccess(List<ProductModel> productModelList) {
        MyProductAdapter adapter = new MyProductAdapter(this, productModelList);
        recyclerProducts.setAdapter(adapter);
    }

    @Override
    public void onProductLoadFailed(String message) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {

    }

    @Override
    public void onCartLoadFailed(String message) {

    }
}
