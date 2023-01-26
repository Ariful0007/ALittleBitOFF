package com.messas.hisabeeprojctsss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class BooksList extends AppCompatActivity {
    String main;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    BookAdapter getDataAdapter1;
    List<Bookmodel> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Book List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_myarrow);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationIcon(R.drawable.ic_myarrow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_myarrow);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        try {
            main=getIntent().getStringExtra("name");
        }catch (Exception e) {
            main=getIntent().getStringExtra("name");
        }
        getList = new ArrayList<>();
        getDataAdapter1 = new BookAdapter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference  =   firebaseFirestore.collection("Booklist")
                .document(main)
                .collection("List").document();
        recyclerView =findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BooksList.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();


    }
    private void reciveData() {

        firebaseFirestore.collection("Booklist")
                .document(main)
                .collection("List")
                .orderBy("time", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        Bookmodel get = ds.getDocument().toObject(Bookmodel.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });
        ///searchview
        name=findViewById(R.id.serachname);
        name.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //fullsearch(query);

                //phoneSerach(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchAllUser(newText);

                //phoneSerach1(newText);
                return false;
            }
        });

    }
    private void searchAllUser(String newText) {
        firebaseFirestore.collection("Booklist")
                .document(main)
                .collection("List")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        getList.clear();
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            String dta = documentSnapshot.getString("bookname");
                            if (dta.toLowerCase().toString().contains(newText.toLowerCase().toString())) {
                                Bookmodel add_customer=new Bookmodel(documentSnapshot.getString("bookname"),
                                        documentSnapshot.getString("bookimage")
                                        , documentSnapshot.getString("authorname"),
                                        documentSnapshot.getString("category"),
                                        documentSnapshot.getString("uuid"),
                                        //1
                                        documentSnapshot.getString("time")
                                        , documentSnapshot.getString("email"),
                                        documentSnapshot.getString("useruid"),
                                        documentSnapshot.getString("flag")

                                );
                                getList.add(add_customer);

                            }
                            getDataAdapter1 = new BookAdapter(getList);
                            recyclerView.setAdapter(getDataAdapter1);


                        }
                    }
                });
    }

    TextView userlisst;

    SearchView name;
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
    }
}