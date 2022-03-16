package com.example.cashcarry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
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

        holder.order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show();


                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long time = timestamp.getTime();
                String pID = "CC" + time;

                OrderModel orderModel = new OrderModel(pID,product.getpName(),product.getpType(),product.getpPrice(),"timeStamp","pending", FirebaseAuth.getInstance().getCurrentUser().getUid(),ShopID);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                db.collection("orders")
                        .document(mAuth.getCurrentUser().getUid())
                        .collection("myOrder")
                        .document(pID)
                        .set(orderModel);

                db.collection("orders")
                        .document(ShopID)
                        .collection("myOrder")
                        .document(pID)
                        .set(orderModel);

                holder.order_now.setEnabled(false);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pTitle,pPrice,order_now;
        ImageView product_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pTitle = itemView.findViewById(R.id.pTitle);
            pPrice = itemView.findViewById(R.id.pPrice);
            product_image = itemView.findViewById(R.id.product_image);
            order_now = itemView.findViewById(R.id.order_now);


        }
    }
}
