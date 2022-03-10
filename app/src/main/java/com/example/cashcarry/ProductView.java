package com.example.cashcarry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ProductView extends AppCompatActivity {


    RecyclerView productRecyclerView;

    FloatingActionButton add_product_btn;
    TextView shop_name,shop_email,shop_mobile;
    ImageView call_now;

    ProductAdapter productAdapter;
    ArrayList<ProductModel> list;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        String ShopID = getIntent().getStringExtra("ShopID");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.mainBG));
        }

        add_product_btn = findViewById(R.id.add_product_btn);
        shop_name = findViewById(R.id.shop_name);
        shop_email = findViewById(R.id.shop_email);
        shop_mobile = findViewById(R.id.shop_mobile);
        call_now = findViewById(R.id.call_now);

        productRecyclerView = findViewById(R.id.productRecyclerView);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this,2));


        db = FirebaseFirestore.getInstance();

        list = new ArrayList<ProductModel>();

        productAdapter =new ProductAdapter(this,list,ShopID);

        productRecyclerView.setAdapter(productAdapter);


        System.out.println("=====================" + ShopID);

        DocumentReference documentReference = db.collection("users").document(ShopID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                shop_name.setText(value.getString("name"));
                shop_email.setText(value.getString("email"));
                shop_mobile.setText(value.getString("mobile"));


            }
        })
        ;

        System.out.println("=====================" + ShopID);


        getData(ShopID);



        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddProduct.class));
            }
        });

    }

    private void getData(String ShopID){

        db.collection("products").document(ShopID).collection("myProduct")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Toast.makeText(getApplicationContext(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                list.add(dc.getDocument().toObject(ProductModel.class));
                            }

                            productAdapter.notifyDataSetChanged();

                        }

                    }
                });

    }

}