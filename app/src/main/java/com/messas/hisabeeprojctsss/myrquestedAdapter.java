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

public class myrquestedAdapter extends RecyclerView.Adapter<myrquestedAdapter.myview> {
    public List<Bookmodel> data;
    FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    public myrquestedAdapter(List<Bookmodel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myrquestedAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb,parent,false);
        return new myrquestedAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myrquestedAdapter.myview holder, final int position) {
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
///////
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        holder.card_view8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request[]={"Show Details","Delete Request"};
                AlertDialog.Builder buiiii=new AlertDialog.Builder(v.getContext());
                buiiii.setTitle("Options")
                        .setItems(request, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0) {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                                    builder.setTitle("Confirmation")
                                            .setMessage("Do you want see full details of book request?")
                                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            firebaseFirestore.collection("ListBorrow")
                                                    .document(data.get(position).getTime())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                if (task.getResult().exists()) {
                                                                    AlertDialog.Builder builder1=new AlertDialog.Builder(v.getContext());
                                                                    builder1.setTitle("Details")
                                                                            .setMessage("Name : "+task.getResult().getString("name")+"\n" +
                                                                                    "Number : "+task.getResult().getString("phone")+"\n" +
                                                                                    "Book Name : "+task.getResult().getString("bookname")+"\n" +
                                                                                    "Book Author name : "+task.getResult().getString("authername")+"\n" +
                                                                                    "Request date : "+task.getResult().getString("date"));
                                                                    builder1.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    }).setNegativeButton("Approve Request", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                            firebaseFirestore.collection("Booklist")
                                                                                    .document(data.get(position).getUseruid())
                                                                                    .collection("List")
                                                                                    .document(data.get(position).getTime())
                                                                                    .update("flag","1")
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                Toast.makeText(v.getContext(), "Done", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        }
                                                                                    });

                                                                        }
                                                                    }).create().show();
                                                                }
                                                            }
                                                        }
                                                    });


                                        }
                                    }).create().show();
                                }
                                else {
                                    firebaseFirestore.collection("Requested_History").document(firebaseAuth.getCurrentUser().getEmail()).collection("List")
                                            .document(data.get(position).getTime()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                                    firebaseFirestore.collection("Booklist")
                                            .document(data.get(position).getUseruid())
                                            .collection("List")
                                            .document(data.get(position).getTime())
                                            .update("flag","0")
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(v.getContext(), "Done", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }
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

