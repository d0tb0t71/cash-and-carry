package com.example.cashcarry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    SliderView sliderView;
    RecyclerView recyclerView;

    ArrayList<CategoryModel> categoryModels;
    CategoryAdapter categoryAdapter;

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

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        recyclerView = findViewById(R.id.categoryRecyclerView);

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




    }
}