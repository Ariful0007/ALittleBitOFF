package com.messas.hisabeeprojctsss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    View view;

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

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
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

        //
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        ///
        CardView dailyCheckCard=view.findViewById(R.id.dailyCheckCard);
        dailyCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                view.getContext().startActivity(new Intent(view.getContext(),Addbooks.class));
            }
        });

        CardView luckySpinCard=view.findViewById(R.id.luckySpinCard);
        luckySpinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                view.getContext().startActivity(new Intent(view.getContext(),HistoryActivity.class));
            }
        });
        return view;
    }
    private void initialize() {



        primage = view.findViewById(R.id.profilepic);
        tvemail = view.findViewById(R.id.emailview);
        tvphone = view.findViewById(R.id.mobileview);



    }
    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(view.getContext());

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


    }
}