package com.messas.hisabeeprojctsss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.myview> {
    public List<Bookmodel> data;
    FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    public BookAdapter(List<Bookmodel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public BookAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb,parent,false);
        return new BookAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.myview holder, final int position) {
        String im="https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
        String image2=data.get(position).getBookimage();
        holder.customer_name.setText(data.get(position).getBookname());
        holder.customer_number.setText(data.get(position).getAuthorname()+"\n" +
                ""+data.get(position).getCategory());
        if (im.equals(image2)) {
        }
        else {
            try {
                Picasso.get().load(data.get(position).getBookimage()).into(holder.image);
            }catch (Exception e) {
                Picasso.get().load(data.get(position).getBookimage()).into(holder.image);
            }
        }
        if (data.get(position).getFlag().equals("1")) {
            holder.card_view8.setVisibility(View.GONE);
        }
        else {
            holder.card_view8.setVisibility(View.VISIBLE);
        }
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        holder.card_view8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirmation")
                        .setMessage("Do you want this book?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final KProgressHUD progressDialog=  KProgressHUD.create(v.getContext())
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Data Uploading.....")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        Bookmodel bookmodel=new Bookmodel(data.get(position).getBookname(),data.get(position).getBookimage(),data.get(position).getAuthorname(),
                                data.get(position).getCategory(),data.get(position).getUuid(),data.get(position).getTime(),data.get(position).getEmail(),
                                data.get(position).getUseruid(),data.get(position).getFlag());
                        firebaseFirestore.collection("Request_History").document(data.get(position).getEmail()).collection("List")
                                .document(data.get(position).getTime()).set(bookmodel)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                        firebaseFirestore.collection("Requested_History").document(firebaseAuth.getCurrentUser().getEmail()).collection("List")
                                .document(data.get(position).getTime()).set(bookmodel)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                        firebaseFirestore.collection("User2")
                                .document(firebaseAuth.getCurrentUser().getEmail())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                Calendar calendar = Calendar.getInstance();
                                                String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                UserDetails userDetails=new UserDetails(task.getResult().getString("name"),task.getResult().getString("number"),task.getResult().getString("number"),
                                                        firebaseAuth.getCurrentUser().getUid(),data.get(position).getBookname(),data.get(position).getAuthorname(),
                                                        current1,data.get(position).getTime()
                                                );
                                                firebaseFirestore.collection("ListBorrow")
                                                        .document(data.get(position).getTime())
                                                        .set(userDetails)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(v.getContext(), "Done", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                });

                    }
                }).create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        ImageView image;
        CardView card_view8;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            image=itemView.findViewById(R.id.image);
            card_view8=itemView.findViewById(R.id.card_view8);



        }
    }
}

