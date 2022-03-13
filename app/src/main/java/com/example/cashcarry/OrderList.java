package com.example.cashcarry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity {

    RecyclerView order_recycler;
    String status = "";

    OrderAdapter orderAdapter;
    ArrayList<OrderModel> list;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        order_recycler = findViewById(R.id.order_recycler);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference1 = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String st = value.getString("userStatus");

                if (st.equals("Admin")) {
                    status = "Admin";
                } else if (st.equals("Customer")) {
                    status = "Customer";
                }

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        order_recycler.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        orderAdapter = new OrderAdapter(this,list);
        order_recycler.setAdapter(orderAdapter);

        db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        db.collection("orders")
                .document(mAuth.getCurrentUser().getUid())
                .collection("myOrder")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Toast.makeText(getApplicationContext(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){

                                OrderModel orderModel = dc.getDocument().toObject(OrderModel.class);

//                                if(status.equals("Admin")){
//                                    list.add(orderModel);
//                                }
                                list.add(orderModel);
                            }

                            orderAdapter.notifyDataSetChanged();

                        }

                    }
                });

    }
}