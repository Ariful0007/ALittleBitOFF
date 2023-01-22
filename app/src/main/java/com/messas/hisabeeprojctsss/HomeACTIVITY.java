package com.messas.hisabeeprojctsss;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class HomeACTIVITY extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar mainToolbar;
    private String current_user_id;
    private BottomNavigationView mainBottomNav;
    private DrawerLayout mainDrawer;
    private ActionBarDrawerToggle mainToggle;
    private NavigationView mainNav;

    FrameLayout frameLayout;
    private TextView drawerName,appname;
    private CircleImageView drawerImage;
    FirebaseAuth firebaseAuth;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestoreSettings settings;
    private DatabaseReference mUserRef;
    HomeFragment homeFragment;
    AdvanceFragment ourplanFragment;
    SequrityFragment profileFragment;
    ProfileFragment myprofile;



    KProgressHUD kProgressHUD;
    Long tsLong = System.currentTimeMillis()/1000;
    String ts = tsLong.toString();

    private UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile,username;
    private Drawer result;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    FirebaseFirestore db;

    FirebaseUser firebaseUser;
    String user1;

    IProfile profile;
    TextView nameTv,emailTv;
    CircleImageView profileImage;
    TextView coinsT1v;
    CardView dailyCheckCard,luckySpinCard,aboutCard1,aboutCard,redeemCard,referCard,taskCard;

    String sessionname,sessionmobile,sessionphoto,sessionemail,sessionusername;
    int count,count1,count2,count3;
    String package_actove;
    String daily_bonus;
    String incomeType="Daily Task";
    int main_account;
    int count12,count123;
    int main_refer;
    String main_task ;



    private TextView tvemail,tvphone;
    private HashMap<String, String> uaser;
    FloatingActionButton dialogClose;

    ; Dialog mDialog;
    @RequiresApi(api = Build.VERSION_CODES.N)
    TextView myamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_a_c_t_i_v_i_t_y);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("dfdfdf");
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10.0f);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        session = new UserSession(getApplicationContext());
        myamount=findViewById(R.id.myamount);
        getValues();
        mainBottomNav = findViewById(R.id.mainBottomNav);
        //BottomNavigationViewHelper.disableShiftMode(mainBottomNav);

        homeFragment=new HomeFragment();
        ourplanFragment=new AdvanceFragment();
        profileFragment=new SequrityFragment();
        myprofile=new ProfileFragment();


        mainBottomNav.setOnNavigationItemSelectedListener(selectlistner);
        initializeFragment();

        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        replaceFragment(homeFragment);

                        return true;

                    case R.id.bottom_chat:
                        replaceFragment(ourplanFragment);
                        return true;
                    case R.id.work:
                        replaceFragment(profileFragment);
                        return true;
                    case R.id.profile:
                        replaceFragment(myprofile);
                        return true;



                    default:
                        return false;
                }
            }
        });
        /////drawar
        mainDrawer=findViewById(R.id.main_activity);
        mainNav = findViewById(R.id.main_nav);
        mainNav.setNavigationItemSelectedListener(this);

        mainToggle = new ActionBarDrawerToggle(this,mainDrawer,toolbar,R.string.open,R.string.close);
        mainDrawer.addDrawerListener(mainToggle);
        mainDrawer.addDrawerListener(mainToggle);
        mainToggle.setDrawerIndicatorEnabled(true);
        mainToggle.syncState();
        //////textview count
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        totalamount=findViewById(R.id.totalamount);
        todays=findViewById(R.id.todays);
        todays___=findViewById(R.id.todays___);
        todayrequests=findViewById(R.id.todayrequests);
        ////
        firebaseFirestore.collection("Books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            int ncount=0;
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            totalamount.setText(""+count);


                        }
                    }
                });
        firebaseFirestore.collection("History").document(firebaseAuth.getCurrentUser().getEmail()).collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            int ncount=0;
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            todays.setText(""+count);


                        }
                    }
                });
        firebaseFirestore.collection("Request_History").document(firebaseAuth.getCurrentUser().getEmail()).collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            int ncount=0;
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            todays___.setText(""+count);


                        }
                    }
                });
        firebaseFirestore.collection("Requested_History").document(firebaseAuth.getCurrentUser().getEmail()).collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            int ncount=0;
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            todayrequests.setText(""+count);


                        }
                    }
                });



    }
    TextView totalamount,todays,todays___,todayrequests;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.bottom_chat:
                startActivity(new Intent(getApplicationContext(),ProfileActivity2.class));
            break;
            //2
            case R.id.bottom_home:
                startActivity(new Intent(getApplicationContext(),Addbooks.class));
                break;
                //3
            case R.id.work:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                break;
                //4
            case R.id.profile:
               AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);
               builder.setTitle("Confirmation")
                       .setMessage("Do you want to logout?")
                       .setPositiveButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                       finishAffinity();
                   }
               }).create();
               builder.show();
                break;
                //6
            case R.id.shared:
                startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                break;



        }

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


        return true;

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getValues() {
        //validating session


        try {
            //get User details if logged in
            session.isLoggedIn();
            user = session.getUserDetails();

            name = user.get(UserSession.KEY_NAME);
            email = user.get(UserSession.KEY_EMAIL);
            mobile = user.get(UserSession.KEY_MOBiLE);
            photo = user.get(UserSession.KEY_PHOTO);
            username=user.get(UserSession.Username);
           // Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
            DateFormat df = new SimpleDateFormat("h:mm:ss a");
            DateFormat df1 = new SimpleDateFormat("EEE, MMM d");
            String date = df.format(Calendar.getInstance().getTime());
            String date2 = df1.format(Calendar.getInstance().getTime());
            myamount.setText(name+"\n" +
                    ""+date2+" | "+date);

        }catch (Exception e) {
            //get User details if logged in
            session.isLoggedIn();
            user = session.getUserDetails();

            name = user.get(UserSession.KEY_NAME);
            email = user.get(UserSession.KEY_EMAIL);
            mobile = user.get(UserSession.KEY_MOBiLE);
            photo = user.get(UserSession.KEY_PHOTO);
            username=user.get(UserSession.Username);
            DateFormat df = new SimpleDateFormat("h:mm a");
            DateFormat df1 = new SimpleDateFormat("EEE, MMM d");
            String date = df.format(Calendar.getInstance().getTime());
            String date2 = df1.format(Calendar.getInstance().getTime());
            myamount.setText(name+"\n" +
                    ""+date2+" | "+date);
        }
        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(HomeACTIVITY.this)
                .setBackgroundColor(R.color.white)
                .setTextTitle("Exit")
                .setCancelable(false)
                .setTextSubTitle("Are you want to exit")
                .setBody("User is not stay at app when user click exit button.")
                .setPositiveButtonText("No")
                .setPositiveColor(R.color.toolbar)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButtonText("Exit")
                .setNegativeColor(R.color.colorPrimaryDark)
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        finishAffinity();

                    }
                })
                .setBodyGravity(FancyAlertDialog.TextGravity.CENTER)
                .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setCancelable(false)
                .build();
        alert.show();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectlistner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.bottom_home:
                            HomeFragment fragment2 = new HomeFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            break;



                    }
                    return false;
                }
            };

    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragment == homeFragment){
            fragmentTransaction.hide(ourplanFragment);
            fragmentTransaction.hide(profileFragment);
            fragmentTransaction.hide(myprofile);


        } else if (fragment == ourplanFragment){
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(profileFragment);
            fragmentTransaction.hide(myprofile);


        }

        else if(fragment==profileFragment) {
            fragmentTransaction.hide(ourplanFragment);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(myprofile);

        }
        else  if(fragment==myprofile) {
            fragmentTransaction.hide(ourplanFragment);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(profileFragment);
        }


        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
    public void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,homeFragment);
        fragmentTransaction.add(R.id.main_container,ourplanFragment);
        fragmentTransaction.add(R.id.main_container,profileFragment);
        fragmentTransaction.add(R.id.main_container,myprofile);

        fragmentTransaction.hide(homeFragment);

        fragmentTransaction.hide(profileFragment);
        fragmentTransaction.hide(myprofile);


        fragmentTransaction.commit();

    }



    public void logout(View view) {
      //  startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
    }

    public void profile(View view) {
    }
    private void check() {

        Map<String, String> userMap2 = new HashMap<>();

        userMap2.put("main_balance","0");
        userMap2.put("purches_balance","0");
        userMap2.put("giving_balance","0");
        userMap2.put("self_income","0");
        userMap2.put("sponsor_income","0");
        userMap2.put("first_level","0");
        userMap2.put("second_level","0");
        userMap2.put("third_level","0");
        userMap2.put("forth_level","0");
        userMap2.put("fifth_level","0");
        userMap2.put("shoping_balance","0");
        userMap2.put("cashwalet","0");
        userMap2.put("monthly_income","0");
        userMap2.put("leader_club","0");
        userMap2.put("gen_bonus","0");
        userMap2.put("ref_bonus","0");
        userMap2.put("daily_income","0");
        Calendar calendar = Calendar.getInstance();
        String current11 = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.getTime());
        String current1 = java.text.DateFormat.getDateInstance().format(calendar.getTime());
        firebaseFirestore.collection("DailyHisab")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection(current1)
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {

                            }
                            else {
                                firebaseFirestore.collection("DailyHisab")
                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                        .collection(current1)
                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                        .set(userMap2)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                }

                                            }
                                        });
                            }
                        }
                        else {

                        }
                    }
                });



    }

}