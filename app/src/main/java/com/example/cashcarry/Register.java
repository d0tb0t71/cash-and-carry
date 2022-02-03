package com.example.cashcarry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    Button register;
    EditText register_email,register_name,register_pass,register_c_pass,register_mobile,register_address;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        register = findViewById(R.id.register);
        register_email = findViewById(R.id.register_email);
        register_name = findViewById(R.id.register_name);
        register_pass = findViewById(R.id.register_pass);
        register_c_pass = findViewById(R.id.register_c_pass);
        register_mobile = findViewById(R.id.register_mobile);
        register_address = findViewById(R.id.register_address);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = register_email.getText().toString();
                String name = register_name.getText().toString();
                String pass = register_pass.getText().toString();
                String c_pass = register_c_pass.getText().toString();
                String mobile = register_mobile.getText().toString();
                String address = register_address.getText().toString();


                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){


                            FirebaseUser user = mAuth.getCurrentUser();

                            UserModel userModel = new UserModel(user.getUid(),"",name,email,mobile,"admin","false","Not Provided Yet");

                            db = FirebaseFirestore.getInstance();


                            db.collection("users")
                                    .document(user.getUid())
                                    .set(userModel);

                            startActivity(new Intent(getApplicationContext(),VerifyEmail.class));

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Registration Failed\n"+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Registration Failed !\n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                startActivity(new Intent(getApplicationContext(),Home.class));

            }
        });

    }
}