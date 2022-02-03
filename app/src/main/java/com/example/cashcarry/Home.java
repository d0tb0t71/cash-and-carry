package com.example.cashcarry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    SliderView sliderView;
    RecyclerView recyclerView,productRecyclerView;

    FloatingActionButton add_product_btn;

    ArrayList<CategoryModel> categoryModels;
    CategoryAdapter categoryAdapter;

    ProductAdapter productAdapter;
    ArrayList<ProductModel> list;

    FirebaseFirestore db;


    int[] images = {R.drawable.c1 ,R.drawable.c2,R.drawable.c3,R.drawable.c4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.mainBG));
        }

        sliderView = findViewById(R.id.imageSlider);

        add_product_btn = findViewById(R.id.add_product_btn);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        recyclerView = findViewById(R.id.categoryRecyclerView);

        productRecyclerView = findViewById(R.id.productRecyclerView);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        int[]  categoryLogo = {R.drawable.baby,R.drawable.eye,R.drawable.fragrance,R.drawable.hair,R.drawable.makeup,R.drawable.skincare,R.drawable.others};

        String[] categoryName = {"Baby","Eye","Fragrance","Hair","Makeup","Skin Care","Others"};


        categoryModels = new ArrayList<>();

        for(int i=0;i<categoryLogo.length;i++){
            CategoryModel catModel = new CategoryModel(categoryLogo[i],categoryName[i]);
            categoryModels.add(catModel);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        categoryAdapter = new CategoryAdapter(this,categoryModels);

        recyclerView.setAdapter(categoryAdapter);



        db = FirebaseFirestore.getInstance();

        list = new ArrayList<ProductModel>();

        productAdapter =new ProductAdapter(this,list);

        productRecyclerView.setAdapter(productAdapter);

        getData();




        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddProduct.class));
            }
        });

    }

    private void getData(){

        db.collection("products")
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