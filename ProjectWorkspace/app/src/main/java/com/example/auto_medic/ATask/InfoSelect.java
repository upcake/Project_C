package com.example.auto_medic.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.auto_medic.Dto.InfoDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.auto_medic.Common.CommonMethod.ipConfig;

public class InfoSelect extends AsyncTask<Void, Void, InfoDTO> {

    InfoDTO infoDTO = null;
    String info_email;

    //생성자 만들기
    public InfoSelect(String info_email) {
        this.info_email = info_email;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected InfoDTO doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("info_email", info_email, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/infoSelectMulti";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            infoDTO = readMessage(inputStream);
            inputStream.close();

        } catch (Exception e) {
            Log.d("main:infoselect", e.getMessage());
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

        return infoDTO;
    }

    @Override
    protected void onPostExecute(InfoDTO infoDTO) {

    }

    public InfoDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String info_email = "", info_pw = "", info_nickname = "", info_phonenum = "", info_profile = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("info_email")) {
                info_email = reader.nextString();
            } else if (readStr.equals("info_pw")) {
                info_pw = reader.nextString();
            } else if (readStr.equals("info_nickname")) {
                info_nickname = reader.nextString();
            } else if (readStr.equals("info_phonenum")) {
                info_phonenum = reader.nextString();
            } else if (readStr.equals("info_profile")) {
                info_profile = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:infoSelect : ", info_email + ", " + info_pw +  ", " + info_nickname + ", " + info_phonenum + ", " + info_profile);
        return new InfoDTO(info_email, info_pw, info_nickname, info_phonenum, info_profile);
    }
}