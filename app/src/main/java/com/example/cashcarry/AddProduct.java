package com.example.cashcarry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;

public class AddProduct extends AppCompatActivity {


    EditText product_name,product_price,product_des;
    Button add_product_btn;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_des = findViewById(R.id.product_des);
        add_product_btn = findViewById(R.id.add_product_btn);



        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pName = ""+ product_name.getText().toString();
                String pPrice = "" + product_price.getText().toString();
                String pDes = "" + product_des.getText().toString();

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long time = timestamp.getTime();
                String pID = "CC" + time;

                ProductModel product = new ProductModel(pID,pName,pDes,pPrice);

                db.collection("products")
                        .document(mAuth.getCurrentUser().getUid())
                        .collection("myProduct")
                        .document(pID)
                        .set(product);

                startActivity(new Intent(getApplicationContext(), Profile.class));
                finish();


            }
        });





    }

}