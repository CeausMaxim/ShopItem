package com.example.shopitem.listener;

import com.example.shopitem.model.ProductModel;

import java.util.List;

public interface IDProductLoadListener {
    void onProductLoadSuccess(List<ProductModel> productModelList);
    void onProductLoadFailed(String message);
}
