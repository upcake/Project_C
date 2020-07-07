package com.example.auto_medic.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;


import com.example.auto_medic.Dto.MemberDTO;

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
import static com.example.auto_medic.LoginActivity.loginDTO;


public class LoginSelect extends AsyncTask<Void, Void, Void> {

    String member_email, member_password;

    //생성자 만들기
    public LoginSelect(String member_email, String member_password) {
        this.member_email = member_email;
        this.member_password = member_password;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("member_email", member_email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_password", member_password, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anLogin";
            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            loginDTO = readMessage(inputStream);


            /*StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();*/

            inputStream.close();

        } catch (Exception e) {
            Log.d("main:loginselect", e.getMessage());
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

    }

    public MemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String member_email = "", member_password = "", member_nickname = "", member_phonenum = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("member_email")) {
                member_email = reader.nextString();
            } else if (readStr.equals("member_password")) {
                member_password = reader.nextString();
            } else if (readStr.equals("member_nickname")) {
                member_nickname = reader.nextString();
            } else if (readStr.equals("member_phonenum")) {
                member_phonenum = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:loginselect : ", member_email + "," + member_password + "," + member_nickname + "," + member_phonenum);
        return new MemberDTO(member_email, member_password, member_nickname, member_phonenum);

    }

}
