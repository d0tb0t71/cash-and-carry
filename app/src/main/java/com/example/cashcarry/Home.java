package com.example.cashcarry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    RecyclerView shop_recyclerview;
    ImageView my_profile_img;
    int[] images = {R.drawable.c1 ,R.drawable.c2,R.drawable.c3,R.drawable.c4};

    UserAdapter userAdapter;
    ArrayList<UserModel> list;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.mainBG));
        }

        shop_recyclerview = findViewById(R.id.shop_recyclerview);
        my_profile_img = findViewById(R.id.my_profile_img);


        LinearLayoutManager layoutManager = new LinearLayoutManager(Home.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        shop_recyclerview.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        userAdapter = new UserAdapter(this,list);
        shop_recyclerview.setAdapter(userAdapter);

        db = FirebaseFirestore.getInstance();


        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Toast.makeText(getApplicationContext(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){

                                UserModel userModel = dc.getDocument().toObject(UserModel.class);

                                if(userModel.getUserStatus().equals("Admin")){
                                    list.add(userModel);
                                }
                            }

                            userAdapter.notifyDataSetChanged();

                        }

                    }
                });



        my_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Profile.class));

            }
        });




        sliderView = findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}