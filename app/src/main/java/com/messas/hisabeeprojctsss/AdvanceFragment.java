package com.messas.hisabeeprojctsss;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AdvanceFragment extends Fragment {

 View view;

    public AdvanceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_advance, container, false);
        CardView dailyCheckCard=view.findViewById(R.id.dailyCheckCard);
        dailyCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BooksList.class);
                intent.putExtra("name","Eninerr");
                getContext().startActivity(intent);
            }
        });
        //2
        CardView luckySpinCard=view.findViewById(R.id.luckySpinCard);
        luckySpinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BooksList.class);
                intent.putExtra("name","Science");
                getContext().startActivity(intent);
            }
        });
        //3
        CardView referCard=view.findViewById(R.id.referCard);
        referCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BooksList.class);
                intent.putExtra("name","Fiction");
                getContext().startActivity(intent);
            }
        });
        //4
        CardView watchCard=view.findViewById(R.id.watchCard);
        watchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BooksList.class);
                intent.putExtra("name","Poem");
                getContext().startActivity(intent);
            }
        });
        //5
        CardView taskCard=view.findViewById(R.id.taskCard);
        taskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),BooksList.class);
                intent.putExtra("name","Others");
                getContext().startActivity(intent);
            }
        });


        return view;
    }
}