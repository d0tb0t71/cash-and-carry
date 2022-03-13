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


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    ArrayList<OrderModel> list;

    public OrderAdapter(Context context, ArrayList<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderModel order = list.get(position);

        holder.orderID.setText(order.getOrderID());
        holder.orderTitle.setText(order.getOrderTitle());
        holder.orderDate.setText("Status : "+order.getOrderStatus());

        holder.orderID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,OrderDetails.class);
                intent.putExtra("id",order.getOrderID());
                intent.putExtra("ShopID",order.getShopID());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderID,orderTitle,orderDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderID = itemView.findViewById(R.id.orderID);
            orderTitle = itemView.findViewById(R.id.orderTitle);
            orderDate = itemView.findViewById(R.id.orderDate);


        }
    }
}
