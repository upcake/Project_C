package com.example.auto_medic.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.auto_medic.Adapter.AlarmRecyclerViewAdapter;
import com.example.auto_medic.Dto.AlarmDTO;

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

public class AlarmSelect extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "AlarmSelect";
    //생성자
    ArrayList<AlarmDTO> dtoArrayList;
    AlarmRecyclerViewAdapter adapter;

    public AlarmSelect(ArrayList<AlarmDTO> dtoArrayList, AlarmRecyclerViewAdapter adapter) {
        this.dtoArrayList = dtoArrayList;
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
        dtoArrayList.clear();
        String result = "";
        String postURL = ipConfig + "/app/alarmSelectMulti";

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

        Log.d("AlarmSelect", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                Log.d(TAG, "readJsonStream: ");
                dtoArrayList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public AlarmDTO readMessage(JsonReader reader) throws IOException {
        String alarm_Email = "", alarm_Title = "", alarm_Sunday = "", alarm_Monday = "", alarm_Tuesday = "";
        String alarm_Wednesday = "", alarm_Thursday = "", alarm_Friday = "", alarm_Saturday = "", alarm_Times ="";
        String alarm_Ringtime1_Hour = "", alarm_Ringtime1_Minute = "", alarm_Ringtime2_Hour = "", alarm_Ringtime2_Minute = "", alarm_Ringtime3_Hour = "";
        String alarm_Ringtime3_Minute = "", alarm_Volume = "", alarm_Bell = "", alarm_Vib = "", alarm_Repeat = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("alarm_Email")) {
                alarm_Email = reader.nextString();
            } else if (readStr.equals("alarm_Title")) {
                alarm_Title = reader.nextString();
            } else if (readStr.equals("alarm_Sunday")) {
                alarm_Sunday = reader.nextString();
            } else if (readStr.equals("alarm_Monday")) {
                alarm_Monday = reader.nextString();
            } else if (readStr.equals("alarm_Tuesday")) {
                alarm_Tuesday = reader.nextString();
            } else if (readStr.equals("alarm_Wednesday")) {
                alarm_Wednesday = reader.nextString();
            } else if (readStr.equals("alarm_Thursday")) {
                alarm_Thursday = reader.nextString();
            } else if (readStr.equals("alarm_Friday")) {
                alarm_Friday = reader.nextString();
            } else if (readStr.equals("alarm_Saturday")) {
                alarm_Saturday = reader.nextString();
            } else if (readStr.equals("alarm_Times")) {
                alarm_Times = reader.nextString();
            } else if (readStr.equals("alarm_Ringtime1_Hour")) {
                alarm_Ringtime1_Hour = reader.nextString();
            } else if (readStr.equals("alarm_Ringtime1_Minute")) {
                alarm_Ringtime1_Minute = reader.nextString();
            } else if (readStr.equals("alarm_Ringtime2_Hour")) {
                alarm_Ringtime2_Hour = reader.nextString();
            } else if (readStr.equals("alarm_Ringtime2_Minute")) {
                alarm_Ringtime2_Minute = reader.nextString();
            } else if (readStr.equals("alarm_Ringtime3_Hour")) {
                alarm_Ringtime3_Hour = reader.nextString();
            } else if (readStr.equals("alarm_Ringtime3_Minute")) {
                alarm_Ringtime3_Minute = reader.nextString();
            } else if (readStr.equals("alarm_Volume")) {
                alarm_Volume = reader.nextString();
            } else if (readStr.equals("alarm_Bell")) {
                alarm_Bell = reader.nextString();
            } else if (readStr.equals("alarm_Vib")) {
                alarm_Vib = reader.nextString();
            } else if (readStr.equals("alarm_Repeat")) {
                alarm_Repeat = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("listselect: dto", alarm_Email + "," + alarm_Title + "," + alarm_Sunday + "," + alarm_Monday + "," + alarm_Tuesday + "," +
                alarm_Wednesday + "," + alarm_Thursday + "," + alarm_Friday + "," + alarm_Saturday + "," + alarm_Times + "," +
                alarm_Ringtime1_Hour + "," + alarm_Ringtime1_Minute + "," + alarm_Ringtime2_Hour + "," + alarm_Ringtime2_Minute + "," + alarm_Ringtime3_Hour + "," +
                alarm_Ringtime3_Minute + "," + alarm_Volume + "," + alarm_Bell + "," + alarm_Vib + "," + alarm_Repeat);
        return new AlarmDTO(alarm_Email, alarm_Title, alarm_Sunday, alarm_Monday, alarm_Tuesday,
                alarm_Wednesday, alarm_Thursday, alarm_Friday, alarm_Saturday, alarm_Times,
                alarm_Ringtime1_Hour, alarm_Ringtime1_Minute, alarm_Ringtime2_Hour, alarm_Ringtime2_Minute, alarm_Ringtime3_Hour,
                alarm_Ringtime3_Minute, alarm_Volume, alarm_Bell, alarm_Vib, alarm_Repeat);
    }
}