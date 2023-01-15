package com.messas.hisabeeprojctsss;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 3000;

    //to get user session data
    private UserSession session;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        session =new UserSession(MainActivity.this);
        firebaseFirestore=FirebaseFirestore.getInstance();

        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);

        TextView appname= findViewById(R.id.appname);
        appname.setTypeface(typeface);

        YoYo.with(Techniques.Bounce)
                .duration(7000)
                .playOn(findViewById(R.id.logo));

        YoYo.with(Techniques.FadeInUp)
                .duration(5000)
                .playOn(findViewById(R.id.appname));
       /*
        NotificationManager notificationManager=(NotificationManager)getApplicationContext().getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setComponent(new ComponentName("com.meass.go_bandarbondriver",
                "com.meass.go_bandarbondriver.MainActivity"));
        intent.putExtra("yes",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Intent intent2=new Intent(Intent.ACTION_VIEW);
        intent2.setComponent(new ComponentName("com.meass.go_bandarbondriver",
                "com.meass.go_bandarbondriver.MainActivity"));
        intent2.putExtra("no",false);
        intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent2=PendingIntent.getActivity(MainActivity.this,1,intent2,PendingIntent.FLAG_ONE_SHOT);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        */

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (firebaseAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));
                    finish();
                }
                else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}

