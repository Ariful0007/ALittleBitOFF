package com.messas.hisabeeprojctsss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ProfileActivity2 extends AppCompatActivity {
    private TextView namebutton;
    private CircleImageView primage;
    private TextView updateDetails;
    private LinearLayout wishlistView;
    private ImageSlider imageSlider;

    //to get user session data
    private UserSession session;
    private TextView tvemail, tvphone;
    private HashMap<String, String> user;
    private String name, email, photo, mobile;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;



    //
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;

    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_myarrow);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_myarrow);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        initialize();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //check Internet Connection
      //  new CheckInternetConnection(this).checkConnection();

        //retrieve session values and display on listviews
        getValues();

        //ImageSLider
        inflateImageSlider();
        //
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


    }

    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(getApplicationContext());

        //validating session
        session.isLoggedIn();

        //get User details if logged in
        user = session.getUserDetails();

        name = user.get(UserSession.KEY_NAME);
        email = user.get(UserSession.KEY_EMAIL);
        mobile = user.get(UserSession.KEY_MOBiLE);
        photo = user.get(UserSession.KEY_PHOTO);
        String upload="https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
        if (photo.equals(upload)) {
            Picasso.get().load(R.drawable.beard).into(primage);
        }
        else {
            Picasso.get().load(photo).into(primage);
        }
        //setting values
        tvemail.setText(email);
        tvphone.setText(mobile);

        namebutton.setText(name);
    }

    private void inflateImageSlider() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
      /*
        slideModels.add(new SlideModel("https://m.media-amazon.com/images/G/31/img19/Wireless/Apple/iPhone11/RiverImages/11Pro/IN_iPhone11Pro_DESKTOP_01._CB437064827_.jpg"));
        slideModels.add(new SlideModel("https://piunikaweb.com/wp-content/uploads/2019/08/oneplus_7_pro_5g_experience_the_power_of_5g_banner-750x354.jpg"));
        slideModels.add(new SlideModel("https://lh3.googleusercontent.com/RSyeouwiFX4XVq6iw3H94al0VcXD693tBy2MxhBKCxAHCIfIpdt7wDV47_j2HanPSnTli7JgZ0fYHxESjz0uvVgeCBT3=w1000"));
        slideModels.add(new SlideModel("https://cdn.metrobrands.com/mochi/media/images/content/Homepage/HOTTMARZZ-BANNER-MOCHI.webp"));
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/b2/78/7c/b2787cea792bff7d2c33e26ada6436bb.jpg"));
        slideModels.add(new SlideModel("https://cdnb.artstation.com/p/assets/images/images/016/802/459/large/shuja-shuaib-banner.jpg?1553535424"));
       */
        slideModels.add(new SlideModel(R.drawable.globallife, ScaleTypes.FIT));
        //slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/salary.png?alt=media&token=627d0a8e-2bf4-4e84-84e9-5574d9b8fbaa"));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));
    }

    private void initialize() {

        imageSlider = findViewById(R.id.slider);

        primage = findViewById(R.id.profilepic);
        tvemail = findViewById(R.id.emailview);
        tvphone = findViewById(R.id.mobileview);
        namebutton = findViewById(R.id.name_button);


    }

    public void myprofile2(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 21);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                // Toast.makeText(this, ""+PICK_IMAGE_REQUEST, Toast.LENGTH_SHORT).show();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), second);
                primage.setImageBitmap(bitmap);
                uploadImage();
                // nid.setImageBitmap(bitmap1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Spinner spinnerTextSize,spinnerTextSize1,spinnerTextSize2;
    EditText Email_Log;
    String valueFromSpinner;
    String valueFromSpinner1;
    String valueFromSpinner2;
    EditText Email_Log4;

    //
    TextView changeProfilePhoto;
    ImageButton image_button;
    ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath,second,third,vechileimage,vechilelicesse;//Firebase

    Button floatingActionButton;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int CAMERA_REQUEST = 1888;
    Button generate_btn;
    //doctor
    private static final int READCODE = 1;
    private static final int WRITECODE = 2;

    private Uri mainImageUri = null;
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference ref = storageReference.child("ProfileImage/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            final Uri downloadUri=uriTask.getResult();



                            if (uriTask.isSuccessful()) {

                                firebaseFirestore.collection("Users")
                                        .document(firebaseAuth.getCurrentUser().getUid())
                                        .update("image",downloadUri.toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    firebaseAuth.signOut();

                                                    Toasty.success(getApplicationContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                }
                                            }
                                        });


                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity2.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
