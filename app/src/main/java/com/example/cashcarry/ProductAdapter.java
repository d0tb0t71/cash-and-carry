package com.example.cashcarry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductModel> list;
    String ShopID;

    public ProductAdapter(Context context, ArrayList<ProductModel> list,String ShopID) {
        this.context = context;
        this.list = list;
        this.ShopID = ShopID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductModel product = list.get(position);

        holder.pTitle.setText(product.getpName());
        holder.pPrice.setText(product.getpPrice()+"$");

        holder.product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,PackageDetails.class);
                intent.putExtra("id",product.getpID());
                intent.putExtra("ShopID",ShopID);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pTitle,pPrice;
        ImageView product_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pTitle = itemView.findViewById(R.id.pTitle);
            pPrice = itemView.findViewById(R.id.pPrice);
            product_image = itemView.findViewById(R.id.product_image);


        }
    }
}
