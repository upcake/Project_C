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

    //리스트 클릭 관련
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: position => "+position);

        MedicineListDTO item = items.get(position);
        holder.setItem(item);
        //여기서 이미지를 변경
        holder.imageView.setImageResource(image(position));
        holder.tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "클릭이름: "+items.get(position).getName());


                Intent intent = new Intent(context,MedicineInfoActivity.class);
                intent.putExtra("detailName",item.getName());
                Log.d(TAG, "클릭이름"+item.getName());
                context.startActivity(intent);
            }
        });


    }

    //이미지 변경 메소드
    public int image(int num){
        int[] image = {R.drawable.capsule_01,R.drawable.capsule_02,R.drawable.capsule_03,R.drawable.capsule_04,R.drawable.capsule_05};
        //if(num%5==0)
        switch (num%5){
            case 0:
                return image[0];
            case 1:
                return image[1];
            case 2:
                return image[2];
            case 3:
                return image[3];
            case 4:
                return image[4];

        }
        return num;
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
