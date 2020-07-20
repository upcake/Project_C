package com.example.auto_medic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto_medic.Dto.EmergencyDTO;

import java.util.ArrayList;


public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder> {
    ArrayList<EmergencyDTO> items;
    Context context;
    private static final String TAG = "EmergencyAdapter";

    public EmergencyAdapter(ArrayList<EmergencyDTO> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.emergency_item,parent,false);


        return new ViewHolder(itemView);//.itemView를 ViewHolder로 전달
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final EmergencyDTO item=items.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: adapter");
                Intent intent = new Intent(context,
                        AddressSettingActivity.class);
                intent.putExtra("emergency_name", item.getEmergency_name());
                intent.putExtra("emergency_phoneNum", item.getEmergency_phonenum());
                intent.putExtra("emergency_email",item.getEmergency_email());
                intent.putExtra("alarm",item.getAlarm());
                intent.putExtra("alarmperiod",item.getAlarmperiod());
                intent.putExtra("message",item.getMessage());
                intent.putExtra("tel",item.getTel());

                context.startActivity(intent);



            }
        });

    }



    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(EmergencyDTO item){
        items.add(item);
    }

    public void removeall(){
        items.clear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvPhoneNum,tvEmail;
        /*ImageView imageView;*/
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);  //사용할 화면의 뷰
            tvName=itemView.findViewById(R.id.tvName);
            tvPhoneNum=itemView.findViewById(R.id.tvPhoneNum);
            tvEmail=itemView.findViewById(R.id.tvEmail);
            /*imageView=itemView.findViewById(R.id.imageView);*/
            parentLayout=itemView.findViewById(R.id.parentLayout);
        }
        public void setItem(EmergencyDTO item){ // 데이터 붙이는 메소드 만듦
            tvName.setText(item.getEmergency_name());
            tvPhoneNum.setText(item.getEmergency_phonenum());
            tvEmail.setText(item.getEmergency_email());
            item.getAlarm();
            item.getAlarmperiod();
            item.getMessage();
            item.getTel();

           /* imageView.setImageResource(item.getResId());*/


        }
    }
}
