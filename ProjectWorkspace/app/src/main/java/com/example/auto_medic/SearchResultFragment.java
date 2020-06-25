package com.example.auto_medic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class SearchResultFragment extends Fragment implements Runnable {

    private static final String TAG = "SearchResultFragment";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MedicineListAdapter adapter;
    String data;
    TextView textView;
    Bundle bundle;
    Context context;
    ArrayList<MedicineListDTO> items;
    SearchMedicineActivity activity ;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        items = new ArrayList<>();
        this.context = context;
        activity = (SearchMedicineActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //rootview = 프래그먼트 xml파일을 말함.
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.searchresultfragment,container,false);
        Log.d(TAG, "onCreateView: 1단계");
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new MedicineListAdapter(context,items);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        textView = rootview.findViewById(R.id.searchresultF_tv);
        //--------------------------------

        //데이터 받는 곳
        if(activity.bundle != null){
            bundle = activity.bundle;
            data=bundle.getString("data");

            textView.append(data);


            activity.bundle = null;
            Log.d(TAG, "onCreateView: "+data);
        }

        //Intent intent =
        //String data =
        Log.d(TAG, "onCreateView: 2단계");
        final String medicineName = "게보린";

        Log.d(TAG, "onCreateView: 3단계");

        Log.d(TAG, "onCreateView: 4단계");



        //검색결과가 없을 때
        if(adapter.getItemCount()==0){
            textView = rootview.findViewById(R.id.searchresultF_tv);
            textView.setVisibility(View.VISIBLE);
        }

        recyclerView.setAdapter(adapter);


        return rootview;
    }

    //약 검색 메소드
  public  void searchMedicine(String medicineName) throws IOException {
      Log.d(TAG, "onCreateView: 5단계");
      StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem"); /*URL*/
      urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=MkSdYE%2Buv%2FKUgV4R8rVZr59Xn0XNCfABz8mQYa9xFwt%2BCigtvqcl2JmkBmpycrnmTdX%2B%2B50a8UNgMqqrB5qvOg%3D%3D"); /*Service Key*/
      urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("MkSdYE%2Buv%2FKUgV4R8rVZr59Xn0XNCfABz8mQYa9xFwt%2BCigtvqcl2JmkBmpycrnmTdX%2B%2B50a8UNgMqqrB5qvOg%3D%3D", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
      urlBuilder.append("&" + URLEncoder.encode("item_name","UTF-8") + "=" + URLEncoder.encode(medicineName, "UTF-8")); /*품목명*/
      Log.d(TAG, "onCreateView: 6단계");
      URL url = new URL(urlBuilder.toString());
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      Log.d(TAG, "onCreateView: 7단계");
      conn.setRequestMethod("GET");
      Log.d(TAG, "onCreateView: 8단계");
      conn.setRequestProperty("Content-type", "application/json");
      Log.d(TAG, "onCreateView: 9단계");
      System.out.println("Response code: " + conn.getResponseCode());
      Log.d(TAG, "onCreateView: 10단계");
      BufferedReader rd;
      Log.d(TAG, "onCreateView: 11단계");
      if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
          rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } else {
          rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
      }

      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = rd.readLine()) != null) {
          sb.append(line);

      }
      rd.close();
      conn.disconnect();
      System.out.println(sb.toString());
      String xmlFile = sb.toString();


      BufferedReader br = null;
      //DocumentBuilderFactory 생성
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder builder;
      Document doc = null;

      try {
          //OpenApi호출

            /*HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();

            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line2;
            while ((line = br.readLine()) != null) {
                result = result + line.trim();// result = URL로 XML을 읽은 값
            }*/
          Log.d(TAG, "onCreateView: 7단계");
          // xml 파싱하기
          InputSource is = new InputSource(new StringReader(xmlFile));
          builder = factory.newDocumentBuilder();
          doc = builder.parse(is);
          XPathFactory xpathFactory = XPathFactory.newInstance();
          XPath xpath = xpathFactory.newXPath();
          // XPathExpression expr = xpath.compile("/response/body/items/item");
          XPathExpression expr = xpath.compile("//items/item");
          NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

          for (int i = 0; i < nodeList.getLength(); i++) {
              NodeList child = nodeList.item(i).getChildNodes();
              //System.out.println("---------------------------------------차일드의 개수는"+child.getLength());
              for (int j = 0; j < child.getLength(); j++) {
                  Node node = child.item(j);
                 // System.out.print(node.getNodeName()+":");		//이거는 그냥 참고용...
                 // System.out.print(node.getTextContent());	//이것을 가져와야 도움이 될거같다...
                 // System.out.println("");
                  Log.d(TAG, node.getNodeName()+":"+node.getTextContent());
                  Log.d(TAG, "rkrkrkrkrk");
              }
          }
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }

  }

    @Override
    public void run() {

    }



}
