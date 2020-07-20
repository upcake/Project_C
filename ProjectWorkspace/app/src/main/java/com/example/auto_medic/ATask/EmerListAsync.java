package com.example.auto_medic.ATask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;


import com.example.auto_medic.Dto.EmergencyDTO;
import com.example.auto_medic.EmergencyAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.auto_medic.Common.CommonMethod.ipConfig;



// doInBackground 파라미터 타입, onProgressUpdate파라미터 타입, onPostExecute 파라미터 타입 순서
// AsyncTask <Params, Progress, Result> 순서임
public class EmerListAsync extends AsyncTask<Void, Void, Void> {
    // 생성자
    ArrayList<EmergencyDTO> items;
    EmergencyAdapter adapter;
    ProgressDialog progressDialog;

    public EmerListAsync(ArrayList<EmergencyDTO> items, EmergencyAdapter adapter, ProgressDialog progressDialog) {
        this.items = items;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
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
        items.clear();
        String result = "";
        String postURL = ipConfig + "/app/EmerSelectMulti";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();

            inputStream.close();*/

        } catch (Exception e) {
            Log.d("Sub1", e.getMessage());
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

        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("Sub1", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                items.add(readMessage(reader));


            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public EmergencyDTO readMessage(JsonReader reader) throws IOException {
        String emergency_name = "", emergency_phonenum = "", emergency_email = "";
        int alarm =0, alarmperiod=0, message=0, tel=0;

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("emergency_name")) {
                emergency_name = reader.nextString();
            } else if (readStr.equals("emergency_phonenum")) {
                emergency_phonenum = reader.nextString();
            } else if (readStr.equals("emergency_email")) {
                emergency_email = reader.nextString();
            }else if (readStr.equals("alarm")) {
                alarm = Integer.parseInt(reader.nextString());
            }else if (readStr.equals("alarmperiod")) {
                alarmperiod = Integer.parseInt(reader.nextString());
            }else if (readStr.equals("message")) {
                message = Integer.parseInt(reader.nextString());
            }else if (readStr.equals("tel")) {
                tel = Integer.parseInt(reader.nextString());
            }else {
                reader.skipValue();
            }

        }
        reader.endObject();
        Log.d("EmergencyDTO:myitem", emergency_name + "," + emergency_phonenum + "," + emergency_email);
        return new EmergencyDTO(emergency_name, emergency_phonenum, emergency_email, alarm,
                               alarmperiod, message, tel );

    }

    /*public List<Double> readDoublesArray(JsonReader reader) throws IOException {
        List<Double> doubles = new ArrayList<Double>();

        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextDouble());
        }
        reader.endArray();
        return doubles;
    }

    public User readUser(JsonReader reader) throws IOException {
        String username = null;
        int followersCount = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                username = reader.nextString();
            } else if (name.equals("followers_count")) {
                followersCount = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(username, followersCount);
    }*/

}
