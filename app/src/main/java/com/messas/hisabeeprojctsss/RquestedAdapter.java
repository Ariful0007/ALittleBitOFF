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
import com.squareup.picasso.Picasso;

import java.util.List;

public class RquestedAdapter extends RecyclerView.Adapter<RquestedAdapter.myview> {
    public List<Bookmodel> data;
    FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    public RquestedAdapter(List<Bookmodel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RquestedAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb,parent,false);
        return new RquestedAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RquestedAdapter.myview holder, final int position) {
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
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        holder.card_view8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shh[]={"View Details","Delete"};
                AlertDialog.Builder mybuilder=new AlertDialog.Builder(v.getContext());
                mybuilder.setTitle("Options")
                        .setItems(shh, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0) {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                                    builder.setTitle("Confirmation")
                                            .setMessage("Do you want see full details of book request that you given?")
                                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            firebaseFirestore.collection("Booklist")
                                                    .document(data.get(position).getUseruid())
                                                    .collection("List")
                                                    .document(data.get(position).getTime())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                if (task.getResult().exists()) {
                                                                    String flag=task.getResult().getString("flag");
                                                                    if (Integer.parseInt(flag)==0) {
                                                                        Toast.makeText(v.getContext(), "Request pending", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    else {


                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });


                                        }
                                    }).create().show();
                                }
                                else {
                                    firebaseFirestore.collection("Request_History").document(firebaseAuth.getCurrentUser().getEmail()).collection("List")
                                            .document(data.get(position).getTime())
                                            .delete()
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
                        }).create();
                mybuilder.show();

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

