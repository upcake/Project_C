package com.example.auto_medic.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto_medic.Dto.AlarmDTO;
import com.example.auto_medic.R;

import java.util.ArrayList;

import static com.example.auto_medic.AlarmFragment.selItem;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ItemViewHolder> {
   private static final String TAG = "AlarmAdapter";
   Context adapterContext;
   ArrayList<AlarmDTO> arrayList;


   public AlarmRecyclerViewAdapter(Context adapterContext, ArrayList<AlarmDTO> arrayList) {
       this.adapterContext = adapterContext;
       this.arrayList = arrayList;
   }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_alarm_item, parent, false);

        return new ItemViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Log.d("main:adater", "" + position);

        AlarmDTO item = arrayList.get(position);
        holder.setItem(item);

        if(arrayList.get(position).getAlarm_Times().equals("0")) {
            holder.item_Icon.setImageResource(R.drawable.ic_alarm_off_gray_48dp);
            holder.item_Title.setTextColor(R.color.colorTimeOff);
            holder.item_Time1.setTextColor(R.color.colorTimeOff);
            holder.item_Time2.setTextColor(R.color.colorTimeOff);
            holder.item_Time3.setTextColor(R.color.colorTimeOff);
        } else if(arrayList.get(position).getAlarm_Times().equals("1")) {
            holder.item_Time2.setTextColor(R.color.colorTimeOff);
            holder.item_Time3.setTextColor(R.color.colorTimeOff);
        } else if(arrayList.get(position).getAlarm_Times().equals("2")) {
            holder.item_Time3.setTextColor(R.color.colorTimeOff);
        }

        holder.item_Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + position);

                selItem = arrayList.get(position);

                Toast.makeText(adapterContext, "Onclick " + arrayList.get(position).getAlarm_Times(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // 어댑터에 매소드 만들기

    // 리사이클러뷰 내용 모두 지우기
    public void removeAllItem(){
        arrayList.clear();
    }

    // 특정 인덱스 항목 가져오기
    public AlarmDTO getItem(int position) {
        return arrayList.get(position);
    }

    // 특정 인덱스 항목 셋팅하기
    public void setItem(int position, AlarmDTO item){
        arrayList.set(position, item);
    }

    // arrayList 통째로 셋팅하기
    public void setItems(ArrayList<AlarmDTO> arrayList){
        this.arrayList = arrayList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public CardView item_CardView;
        public ConstraintLayout item_Parent;
        public ImageView item_Icon;
        public TextView item_Title;
        public TextView item_Time1;
        public TextView item_Time2;
        public TextView item_Time3;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            item_CardView = itemView.findViewById(R.id.item_CardView);
            item_Parent = itemView.findViewById(R.id.item_Parent);
            item_Icon = itemView.findViewById(R.id.item_Icon);
            item_Title = itemView.findViewById(R.id.item_Title);
            item_Time1 = itemView.findViewById(R.id.item_Time1);
            item_Time2 = itemView.findViewById(R.id.item_Time2);
            item_Time3 = itemView.findViewById(R.id.item_Time3);
        }

        @SuppressLint("ResourceAsColor")
        public void setItem(AlarmDTO item){
            item_Title.setText(item.getAlarm_Title());
            item_Time1.setText(item.getAlarm_Ringtime1_Hour() + ":" + item.getAlarm_Ringtime1_Minute());
            item_Time2.setText(item.getAlarm_Ringtime2_Hour() + ":" + item.getAlarm_Ringtime2_Minute());
            item_Time3.setText(item.getAlarm_Ringtime3_Hour() + ":" + item.getAlarm_Ringtime3_Minute());
        }
    }
}