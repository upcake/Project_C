package com.example.auto_medic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        //intent로부터 전달받은 String
        String getExtraString = intent.getExtras().getString("extra");

        //RingtonePlayingService 인텐트 생성
        Intent service_Intent = new Intent(context, RingtonePlayingService.class);

        //RingtonePlayingService로 extra string 보내기
        service_Intent.putExtra("state", getExtraString);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            this.context.startForegroundService(service_Intent);
        }else{
            this.context.startService(service_Intent);
        }
    }
}
