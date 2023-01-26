package com.messas.hisabeeprojctsss;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.myview> {
    public List<Bookmodel> data;
    FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    public MyBookAdapter(List<Bookmodel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyBookAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb,parent,false);
        return new MyBookAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookAdapter.myview holder, final int position) {
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

