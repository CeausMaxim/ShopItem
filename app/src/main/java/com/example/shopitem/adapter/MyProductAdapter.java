package com.example.shopitem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopitem.R;
import com.example.shopitem.model.ProductModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyProductViewHolder> {

    private Context context;
    private List<ProductModel> productModelList;

    public MyProductAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public MyProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyProductViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductViewHolder holder, int position) {
        Glide.with(context)
                .load(productModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("$").append(productModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(productModelList.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class MyProductViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView)
        public ImageView imageView;
        @BindView(R.id.txtName)
        public TextView txtName;
        @BindView(R.id.txtPrice)
        public TextView txtPrice;

        private Unbinder unbinder;
        public MyProductViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

        }
    }
}
