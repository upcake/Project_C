package com.example.auto_medic.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.auto_medic.Dto.KakaoMemberDTO;

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
import static com.example.auto_medic.SessionCallback.KakaoLoginDTO;

public class KakaoLogin extends AsyncTask<Void, Void, String> {
    String kakao_email, kakao_nickname;

    //생성자 만들기
    public KakaoLogin(String kakao_email, String kakao_nickname) {
        this.kakao_email = kakao_email;
        this.kakao_nickname = kakao_nickname;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            //문자열 및 데이터 추가
            builder.addTextBody("kakao_email", kakao_email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("kakao_nickname", kakao_nickname, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anKakaoLogin";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            KakaoLoginDTO = readMessage(inputStream);

            inputStream.close();
        } catch (Exception e) {
            Log.d("main:kakaologin", e.getMessage());
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

       return "Kakao login complete";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    public KakaoMemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        String kakao_email = "", kakao_nickname = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("kakao_email")) {
                kakao_email = reader.nextString();
            } else if (readStr.equals("kakao_nickname")) {
                kakao_nickname = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:Kakaologin : ", kakao_email + "," + kakao_nickname);
        return new KakaoMemberDTO(kakao_email, kakao_nickname);

    }
}
