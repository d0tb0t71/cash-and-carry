package com.example.cashcarry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class OrderDetails extends AppCompatActivity {

    TextView order_id,order_title,order_details,order_price,order_status,order_uid,order_mobile;
    Button confirmed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        order_id =findViewById(R.id.order_id);
        order_title =findViewById(R.id.order_title);
        order_details =findViewById(R.id.order_details);
        order_price =findViewById(R.id.order_price);
        order_status =findViewById(R.id.order_status);
        order_uid =findViewById(R.id.order_uid);
        order_mobile =findViewById(R.id.order_mobile);
        confirmed =findViewById(R.id.confirmed);


        String productID = getIntent().getStringExtra("id");
        String ShopID = getIntent().getStringExtra("ShopID");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        DocumentReference documentReference = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String St = ""+value.getString("userStatus");

                if(St.equals("Seller") || St.equals("Admin")){
                    confirmed.setVisibility(View.VISIBLE);


                }


            }
        });

        DocumentReference documentReference1 = db.collection("orders").document(mAuth.getCurrentUser().getUid()).collection("myOrder").document(productID);
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                order_id.setText(""+value.getString("orderID"));
                order_title.setText(""+value.getString("orderTitle"));
                order_details.setText("Order Details : \n"+value.getString("orderDescription"));
                order_price.setText("Order Price : "+value.getString("orderPrice"));
                order_status.setText("Status : "+value.getString("orderStatus"));


                String s = value.getString("orderStatus");

                if(s.equals("confirmed")){
                    confirmed.setVisibility(View.GONE);
                }

                String buyerUID = ""+value.getString("buyerUID");

                DocumentReference documentReference = db.collection("users").document(buyerUID);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        order_uid.setText("Order from : "+value.getString("name"));
                        order_mobile.setText("Mobile : "+value.getString("mobile"));

                    }
                });


            }
        })
        ;




        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.collection("orders")
                        .document(mAuth.getCurrentUser().getUid())
                        .collection("myOrder")
                        .document(productID)
                        .update(
                                "orderStatus","confirmed"
                        );

                db.collection("orders")
                        .document(ShopID)
                        .collection("myOrder")
                        .document(productID)
                        .update(
                                "orderStatus","confirmed"
                        );
            }
        });



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