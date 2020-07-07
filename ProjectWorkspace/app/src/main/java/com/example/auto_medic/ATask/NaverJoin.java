package com.example.auto_medic.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.auto_medic.Dto.MemberDTO;
import com.example.auto_medic.Dto.NaverMemberDTO;

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
import static com.example.auto_medic.SocialLoginActivity.NaverLoginDTO;

public class NaverLogin extends AsyncTask<Void, Void, String> {
    String naver_email, naver_nickname;

    //생성자 만들기
    public NaverLogin(String naver_email, String naver_nickname) {
        this.naver_email = naver_email;
        this.naver_nickname = naver_nickname;
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
            builder.addTextBody("naver_email", naver_email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("naver_nickname", naver_nickname, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anNaverLogin";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            NaverLoginDTO = readMessage(inputStream);

            inputStream.close();
        } catch (Exception e) {
            Log.d("main:naverlogin", e.getMessage());
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

       return "Naver login complete";
    }

    @Override
    protected void onPostExecute(String result) {

    }

    public NaverMemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        String naver_email = "", naver_nickname = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("naver_email")) {
                naver_email = reader.nextString();
            } else if (readStr.equals("naver_nickname")) {
                naver_nickname = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:Naverlogin : ", naver_email + "," + naver_nickname);
        return new NaverMemberDTO(naver_email, naver_nickname);

    }
}
