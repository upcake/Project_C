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

import java.nio.charset.Charset;

import static com.example.auto_medic.Common.CommonMethod.ipConfig;


public class EmerUpdate extends AsyncTask<Void, Void, Void> {

    String Name,Phone;
    int  alarm, alarmperiod, message, tel;

    public EmerUpdate(String name, String phone, int alarm, int alarmperiod, int message, int tel) {
        Name = name;
        Phone = phone;
        this.alarm = alarm;
        this.alarmperiod = alarmperiod;
        this.message = message;
        this.tel = tel;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("emergency_name", Name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("emergency_phonenum", Phone, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarm", String.valueOf(alarm), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("alarmperiod", String.valueOf(alarmperiod), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("message", String.valueOf(message), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("tel", String.valueOf(tel), ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/emerUpdateMultiNo";

            Log.d("Sub1Update11", Name);
            Log.d("Sub1Update12", Phone);
            Log.d("Sub1Update12", ""+alarm+alarmperiod+message+tel);



            Log.d("Sub1Update:postURL", postURL);
            //postURL = ipConfig + "/app/anUpdateMulti";
            // 전송
            //InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //inputStream = httpEntity.getContent();
            Log.d("Sub1Update:postURL", postURL);

            // 응답
                /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }*/
            //inputStream.close();

            // 응답결과
               /* String result = stringBuilder.toString();
                Log.d("response", result);*/



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //dialog.dismiss();

    }


}
