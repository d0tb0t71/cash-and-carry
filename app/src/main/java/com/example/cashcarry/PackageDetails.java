package com.example.cashcarry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PackageDetails extends AppCompatActivity {

    TextView item_title, item_price, item_description;
    Button order_now, delete_item, edit_item;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);


        item_title = findViewById(R.id.item_title);
        item_price = findViewById(R.id.item_price);
        item_description = findViewById(R.id.item_description);
        order_now = findViewById(R.id.order_now);
        linearLayout = findViewById(R.id.l1);


        String itemId = getIntent().getStringExtra("id");
        String ShopID = getIntent().getStringExtra("ShopID");


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference1 = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String st = value.getString("userStatus");

                if (st.equals("Admin")&& ShopID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    linearLayout.setVisibility(View.VISIBLE);
                    System.out.println("--------------------------" + itemId);
                } else if (st.equals("Customer")) {
                    order_now.setVisibility(View.VISIBLE);
                }


            }
        })
        ;


        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DocumentReference documentReference = db.collection("products")
                .document(ShopID)
                .collection("myProduct")
                .document(itemId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                item_title.setText("" + value.getString("pName"));
                item_description.setText("Package Description : \n" + value.getString("pType"));
                item_price.setText("Package Price : " + value.getString("pPrice") + "$");

//                itemName = ""+value.getString("item_name");
//                itemDes = ""+value.getString("item_des");
//                itemPrice = ""+value.getString("item_price");

            }
        })
        ;




    }
}