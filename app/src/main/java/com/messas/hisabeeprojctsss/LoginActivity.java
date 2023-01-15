package com.messas.hisabeeprojctsss;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText editTextPhone;
    Button buttonContinue;
    UserSession session;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        editTextPhone=findViewById(R.id.editTextPhone);
        buttonContinue=findViewById(R.id.buttonContinue);
        session= new UserSession(getApplicationContext());
        //new CheckInternetConnection(this).checkConnection();
        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(LoginActivity.this);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=editTextPhone.getText().toString();
                String number_get=number+"@gmail.com";
                if (TextUtils.isEmpty(number)||number.length()<11) {
                    Toasty.error(getApplicationContext(),"Enter phone number",Toasty.LENGTH_SHORT,true).show();
                    return;
                }
                else {
                    firebaseFirestore.collection("Driver_Name_Exsiting")
                            .document(number)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {
                                            Toasty.error(getApplicationContext(),"This number already registered as a rider. Please try other.",Toasty.LENGTH_SHORT,true).show();
                                            return;
                                        }
                                        else {
                                            firebaseFirestore.collection("User2")
                                                    .document(number_get)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                if (task.getResult().exists()) {
                                                                    final KProgressHUD progressDialog=  KProgressHUD.create(LoginActivity.this)
                                                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                            .setLabel("Login User.....")
                                                                            .setCancellable(false)
                                                                            .setAnimationSpeed(2)
                                                                            .setDimAmount(0.5f)
                                                                            .show();
                                                                    firebaseFirestore.collection("BlockList")
                                                                            .document(number_get)
                                                                            .get()
                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        if (task.getResult().exists()) {
                                                                                            progressDialog.dismiss();
                                                                                            Toasty.error(getApplicationContext(),"You  are in block list.", Toast.LENGTH_SHORT,true).show();
                                                                                        }
                                                                                        else {
                                                                                            firebaseFirestore.collection("User2")
                                                                                                    .document(number_get)
                                                                                                    .get()
                                                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                            if(task.isSuccessful()) {
                                                                                                                if (task.getResult().exists()) {
                                                                                                                    firebaseAuth.signInWithEmailAndPassword(number.toLowerCase() +"@gmail.com","123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                                                                                                            if(task.isSuccessful()){
                                                                                                                                firebaseFirestore.collection("Password")
                                                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                        .get()
                                                                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                                            @Override
                                                                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                                    if (task.getResult().exists()) {
                                                                                                                                                        String pass=task.getResult().getString("uuid");
                                                                                                                                                        if (pass.equals(number.toLowerCase())) {
                                                                                                                                                            String userID = firebaseAuth.getCurrentUser().getUid();
                                                                                                                                                            firebaseFirestore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                                                                                                                                    if (task.isSuccessful()) {

                                                                                                                                                                        if (task.getResult().exists()) {


                                                                                                                                                                            String sessionname = task.getResult().getString("name");
                                                                                                                                                                            String sessionmobile = task.getResult().getString("number");
                                                                                                                                                                            String sessionphoto = task.getResult().getString("image");
                                                                                                                                                                            String sessionemail = task.getResult().getString("email");
                                                                                                                                                                            String sessionusername = task.getResult().getString("username");


                                                                                                                                                                            session.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);


                                                                                                                                                                            Toasty.success(getApplicationContext(), "Login Successfully .", Toasty.LENGTH_SHORT, true).show();

                                                                                                                                                                            Intent loginSuccess = new Intent(LoginActivity.this, SecondHome.class);

                                                                                                                                                                            startActivity(loginSuccess);
                                                                                                                                                                            finish();

                                                                                                                                                                        }
                                                                                                                                                                    } else {
                                                                                                                                                                        firebaseAuth.signOut();
                                                                                                                                                                        progressDialog.dismiss();
                                                                                                                                                                        Toast.makeText(LoginActivity.this, "Login Error. Please try again.", Toast.LENGTH_SHORT).show();
                                                                                                                                                                    }

                                                                                                                                                                }
                                                                                                                                                            });
                                                                                                                                                        }
                                                                                                                                                        else {
                                                                                                                                                            firebaseAuth.signOut();
                                                                                                                                                            progressDialog.dismiss();
                                                                                                                                                            Toasty.error(getApplicationContext(),"Password not match", Toast.LENGTH_SHORT,true).show();
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        });





                                                                                                                            } else {
                                                                                                                                firebaseAuth.signOut();
                                                                                                                                progressDialog.dismiss();
                                                                                                                                Toasty.error(LoginActivity.this,"Couldn't Log In. Please check your Email/Password",2000).show();
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                                else {
                                                                                                                    progressDialog.dismiss();
                                                                                                                    Toasty.error(getApplicationContext(),"You are not a valid user", Toast.LENGTH_SHORT,true).show();
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    });


                                                                                        }

                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                                else {
                                                                    Intent intent=new Intent(getApplicationContext(),Registered.class);
                                                                    intent.putExtra("number",number);
                                                                    startActivity(intent);
                                                                    //registered
                                                                }

                                                            }
                                                            else {
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });

                }
            }
        });

    }
}