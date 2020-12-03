package com.example.senierproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.senierproject.R;
import com.example.senierproject.Data.MiseInfoRecycleObject;

import java.util.ArrayList;

public class MiseInfoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MiseInfoViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView status;
        TextView pm10;
        TextView pm25;
        TextView o3;
        TextView no2;
        TextView co;
        TextView so3;

        MiseInfoViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.mise_image);
            status =view.findViewById(R.id.mise_status);
            pm10 = view.findViewById(R.id.pm10);
            pm25 = view.findViewById(R.id.pm25);
            o3 = view.findViewById(R.id.o3);
            no2 = view.findViewById(R.id.no2);
            co = view.findViewById(R.id.co);
            so3 = view.findViewById(R.id.so3);
        }
    }

    private ArrayList<MiseInfoRecycleObject> miseInfoArrayList;
    public MiseInfoRecyclerAdapter(ArrayList<MiseInfoRecycleObject> adtionInfoArrayList){
        this.miseInfoArrayList = adtionInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.miseinfo_template, parent, false);

        return new MiseInfoRecyclerAdapter.MiseInfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MiseInfoRecyclerAdapter.MiseInfoViewHolder miseInfoViewHolder = (MiseInfoRecyclerAdapter.MiseInfoViewHolder) holder;

        miseInfoViewHolder.image.setImageResource(miseInfoArrayList.get(position).image);
        miseInfoViewHolder.status.setText(miseInfoArrayList.get(position).status);
        miseInfoViewHolder.pm10.setText(miseInfoArrayList.get(position).pm10);
        miseInfoViewHolder.pm25.setText(miseInfoArrayList.get(position).pm25);
        miseInfoViewHolder.o3.setText(miseInfoArrayList.get(position).o3);
        miseInfoViewHolder.no2.setText(miseInfoArrayList.get(position).no2);
        miseInfoViewHolder.co.setText(miseInfoArrayList.get(position).co);
        miseInfoViewHolder.so3.setText(miseInfoArrayList.get(position).so3);

        miseInfoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Context context = view.getContext();
//                Toast.makeText(context, position+" ",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return miseInfoArrayList.size();
    }
}
