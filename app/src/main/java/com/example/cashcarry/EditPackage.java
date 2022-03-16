package com.example.cashcarry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EditPackage extends AppCompatActivity {

    EditText p_name,p_price,p_des;
    Button p_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_package);

        String ShopID = getIntent().getStringExtra("ShopID");
        String ProductID = getIntent().getStringExtra("ProductID");


        p_name = findViewById(R.id.p_name);
        p_price = findViewById(R.id.p_price);
        p_des = findViewById(R.id.p_des);
        p_update = findViewById(R.id.p_update);




        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("products")
                .document(ShopID)
                .collection("myProduct")
                .document(ProductID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                p_name.setText("" + value.getString("pName"));
                p_des.setText("" + value.getString("pType"));
                p_price.setText("" + value.getString("pPrice"));

//


            }
        });


        p_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = p_name.getText().toString();
                String des = p_des.getText().toString();
                String price = p_price.getText().toString();

                db.collection("products")
                        .document(mAuth.getCurrentUser().getUid())
                        .collection("myProduct")
                        .document(ProductID)
                        .update(
                                "pName",name,
                                "pType",des,
                                "pPrice",price
                        );

                startActivity(new Intent(getApplicationContext(),PackageDetails.class));
                finish();

            }
        });






    }
}