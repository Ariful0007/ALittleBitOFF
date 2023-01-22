package com.messas.hisabeeprojctsss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class SequrityFragment extends Fragment {

View view;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RecyclerView rreeeed;
    LottieAnimationView empty_cart;


    ///


    DocumentReference documentReference;

    myrquestedAdapter getDataAdapter1;
    List<Bookmodel> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    public SequrityFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sequrity, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        rreeeed=view.findViewById(R.id.rreeeed);
        empty_cart=view.findViewById(R.id.empty_cart);
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
                            if (count==0) {
                                empty_cart.setVisibility(View.VISIBLE);
                            }
                            else {
                                empty_cart.setVisibility(View.GONE);
                            }


                        }
                    }
                });

        getList = new ArrayList<>();
        getDataAdapter1 = new myrquestedAdapter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference  =    firebaseFirestore.collection("Requested_History").document(firebaseAuth.getCurrentUser().getEmail()).collection("List").document();

        rreeeed.setHasFixedSize(true);
        rreeeed.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rreeeed.setAdapter(getDataAdapter1);
        reciveData();
        return view;
    }
    private void reciveData() {

        firebaseFirestore.collection("Requested_History").document(firebaseAuth.getCurrentUser().getEmail()).collection("List")
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

    }
}