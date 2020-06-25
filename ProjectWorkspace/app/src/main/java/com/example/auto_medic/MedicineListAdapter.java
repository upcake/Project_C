package com.example.auto_medic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.ViewHolder>
implements  OnMedicineItemClickListener{
    private static final String TAG = "MedicineListAdapter";
    Context context;

    OnMedicineItemClickListener listener;
    ArrayList<MedicineListDTO> items;

    public MedicineListAdapter(Context context, ArrayList<MedicineListDTO> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item,parent,false);

        return new ViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: position => "+position);

        MedicineListDTO item = items.get(position);
        holder.setItem(item);

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+items.get(position).getName());


                Intent intent = new Intent(context,MedicineInfoActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    
    public void addItem(MedicineListDTO item){
        items.add(item);
    }

    public MedicineListDTO getItem(int position){
        return items.get(position);
    }
    public void setOnitemClickListener(OnMedicineItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holderm, View view, int position) {
        if(listener != null){
            listener.onItemClick(holderm,view,position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvClick;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView, final OnMedicineItemClickListener listener) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvClick = itemView.findViewById(R.id.tvClick);
            imageView = itemView.findViewById(R.id.list_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,view,position);
                    }
                    //Log.d(TAG, "onClick: 클릭~!!!!");

                }
            });

        }

        public void setItem(MedicineListDTO item){
            tvName.setText(item.getName());
           /* tvPhoneNum.setText(item.getPhoneNum());
            imageView.setImageResource(item.getResId());*/
        }
    }
}
