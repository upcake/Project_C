package com.example.auto_medic.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.nio.charset.Charset;

import static com.example.auto_medic.Common.CommonMethod.ipConfig;

public class AlarmUpdate extends AsyncTask<Void, Void, Void> {

    CharSequence alarm_Email, alarm_Id, alarm_Title, alarm_Sunday, alarm_Monday, alarm_Tuesday, alarm_Wednesday,
            alarm_Thursday, alarm_Friday, alarm_Saturday, alarm_Times, alarm_Ringtime1_Hour, alarm_Ringtime1_Minute,
            alarm_Ringtime2_Hour, alarm_Ringtime2_Minute, alarm_Ringtime3_Hour, alarm_Ringtime3_Minute, alarm_Volume, alarm_Bell, alarm_Vib, alarm_Repeat;

    public AlarmUpdate(CharSequence alarm_Email, CharSequence alarm_Id, CharSequence alarm_Title, CharSequence alarm_Sunday, CharSequence alarm_Monday, CharSequence alarm_Tuesday, CharSequence alarm_Wednesday, CharSequence alarm_Thursday, CharSequence alarm_Friday, CharSequence alarm_Saturday, CharSequence alarm_Times, CharSequence alarm_Ringtime1_Hour, CharSequence alarm_Ringtime1_Minute, CharSequence alarm_Ringtime2_Hour, CharSequence alarm_Ringtime2_Minute, CharSequence alarm_Ringtime3_Hour, CharSequence alarm_Ringtime3_Minute, CharSequence alarm_Volume, CharSequence alarm_Bell, CharSequence alarm_Vib, CharSequence alarm_Repeat) {
        this.alarm_Email = alarm_Email;
        this.alarm_Id = alarm_Id;
        this.alarm_Title = alarm_Title;
        this.alarm_Sunday = alarm_Sunday;
        this.alarm_Monday = alarm_Monday;
        this.alarm_Tuesday = alarm_Tuesday;
        this.alarm_Wednesday = alarm_Wednesday;
        this.alarm_Thursday = alarm_Thursday;
        this.alarm_Friday = alarm_Friday;
        this.alarm_Saturday = alarm_Saturday;
        this.alarm_Times = alarm_Times;
        this.alarm_Ringtime1_Hour = alarm_Ringtime1_Hour;
        this.alarm_Ringtime1_Minute = alarm_Ringtime1_Minute;
        this.alarm_Ringtime2_Hour = alarm_Ringtime2_Hour;
        this.alarm_Ringtime2_Minute = alarm_Ringtime2_Minute;
        this.alarm_Ringtime3_Hour = alarm_Ringtime3_Hour;
        this.alarm_Ringtime3_Minute = alarm_Ringtime3_Minute;
        this.alarm_Volume = alarm_Volume;
        this.alarm_Bell = alarm_Bell;
        this.alarm_Vib = alarm_Vib;
        this.alarm_Repeat = alarm_Repeat;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            String postURL = "";
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("alarm_Email", (String) alarm_Email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Id", (String) alarm_Id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Title", (String) alarm_Title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Sunday", (String) alarm_Sunday, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Monday", (String) alarm_Monday, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Tuesday", (String) alarm_Tuesday, ContentType.create("Multipart/related", "UTF-8"));

            builder.addTextBody("alarm_Wednesday", (String) alarm_Wednesday, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Thursday", (String) alarm_Thursday, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Friday", (String) alarm_Friday, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Saturday", (String) alarm_Saturday, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Times", (String) alarm_Times, ContentType.create("Multipart/related", "UTF-8"));

            builder.addTextBody("alarm_Ringtime1_Hour", (String) alarm_Ringtime1_Hour, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Ringtime1_Minute", (String) alarm_Ringtime1_Minute, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Ringtime2_Hour", (String) alarm_Ringtime2_Hour, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Ringtime2_Minute", (String) alarm_Ringtime2_Minute, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Ringtime3_Hour", (String) alarm_Ringtime3_Hour, ContentType.create("Multipart/related", "UTF-8"));

            builder.addTextBody("alarm_Ringtime3_Minute", (String) alarm_Ringtime3_Minute, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Volume", (String) alarm_Volume, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Bell", (String) alarm_Bell, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Vib", (String) alarm_Vib, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm_Repeat", (String) alarm_Repeat, ContentType.create("Multipart/related", "UTF-8"));

            postURL = ipConfig + "/app/alarmUpdateMulti";

            // 전송
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}