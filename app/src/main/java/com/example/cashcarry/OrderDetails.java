package com.example.cashcarry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class OrderDetails extends AppCompatActivity {

    TextView order_id,order_title,order_details,order_price,order_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        order_id =findViewById(R.id.order_id);
        order_title =findViewById(R.id.order_title);
        order_details =findViewById(R.id.order_details);
        order_price =findViewById(R.id.order_price);
        order_status =findViewById(R.id.order_status);


        String productID = getIntent().getStringExtra("id");
        String ShopID = getIntent().getStringExtra("ShopID");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DocumentReference documentReference1 = db.collection("orders").document(mAuth.getCurrentUser().getUid()).collection("myOrder").document(productID);
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                order_id.setText(""+value.getString("orderID"));
                order_title.setText(""+value.getString("orderTitle"));
                order_details.setText("Order Details : \n"+value.getString("orderDescription"));
                order_price.setText("Order Price : "+value.getString("orderPrice"));
                order_status.setText("Status : "+value.getString("orderStatus"));



            }
        })
        ;




//        db.collection("orders")
//                .document(mAuth.getCurrentUser().getUid())
//                .collection("myOrder")
//                .document(pID)
//                .set(orderModel);
//
//        db.collection("orders")
//                .document(ShopID)
//                .collection("myOrder")
//                .document(pID)
//                .set(orderModel);



    }
}