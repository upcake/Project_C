package com.example.auto_medic.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.auto_medic.Adapter.AlarmRecyclerViewAdapter;
import com.example.auto_medic.Dto.AlarmDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.auto_medic.Common.CommonMethod.ipConfig;

public class AlarmDelete extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "AlarmDelete";
    //생성자
    Bundle bundle;
    AlarmRecyclerViewAdapter adapter;

    public AlarmDelete(Bundle bundle, AlarmRecyclerViewAdapter adapter) {
        this.bundle = bundle;
        this.adapter = adapter;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String postURL = ipConfig + "/app/alarmDeleteMulti";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("alarm_Id", bundle.getCharSequence("alarm_Id").toString(), ContentType.create("Multipart/related", "UTF-8"));

            // 전송
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();

        } catch (Exception e) {
            Log.d("AlarmDelete", e.getMessage());
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;

            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("AlarmDelete", "Delete Complete!!!");

        adapter.notifyDataSetChanged();
    }
}