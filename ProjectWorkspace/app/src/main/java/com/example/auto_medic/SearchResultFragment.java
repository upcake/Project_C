package com.example.auto_medic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SearchResultFragment extends Fragment {

    private static final String TAG = "SearchResultFragment";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MedicineListAdapter adapter;
    String data;
    TextView textView;
    Bundle bundle;
    Context context;
    ImageView noresult;
    ArrayList<MedicineListDTO> items;
    SearchMedicineActivity activity;
    Handler handler = new Handler();
    ProgressDialog dialog;

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
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.searchresultfragment, container, false);
        Log.d(TAG, "onCreateView: 1단계");
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new MedicineListAdapter(context, items);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        noresult = rootview.findViewById(R.id.searchresultF_image);
        textView = rootview.findViewById(R.id.noResultTv);
        //--------------------------------

        //데이터 받는 곳
        if (activity.bundle != null) {
            bundle = activity.bundle;
            data = bundle.getString("data");


            //textView.setText("");
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("약 검색 중입니다...");
            dialog.setCanceledOnTouchOutside(false);    //바깥쪽을 눌러도 없어지지 않음
            dialog.show();
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {

                        searchMedicine(data);


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(adapter.getItemCount()==0){
                                noresult.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);
                            }else{
                                noresult.setVisibility(View.GONE);
                                textView.setVisibility(View.GONE);
                            }

                            recyclerView.setAdapter(adapter);
                            dialog.cancel();
                        }
                    });
                }
            }.start();

            activity.bundle = null;
        }

        //데이터 보내 주는 곳
        Bundle bundle = new Bundle();
        bundle.putString("sendData", data);
        Log.d(TAG, "데이터에 든값=>"+data);

        //검색결과가 없을 때
        if (adapter.getItemCount() == 0) {
            noresult.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            /*textView = rootview.findViewById(R.id.searchresultF_tv);
            textView.setVisibility(View.VISIBLE);
            textView.setText("약을 입력하세요!");*/
        } else {          //검색 결과가 있을 때 새 객체 생성
            noresult.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
        return rootview;
    }

    /*//이미지 변경 메소드
    public int image(int num){
        int[] image = {R.drawable.capsule_01,R.drawable.capsule_02,R.drawable.capsule_03,R.drawable.capsule_04,R.drawable.capsule_05};
        //if(num%5==0)
        switch (num%5){
            case 0:
                return image[0];
            case 1:
                return image[1];
            case 2:
                return image[2];
            case 3:
                return image[3];
            case 4:
                return image[4];

        }
        return num;
    }*/
    //약 검색 메소드
    public void searchMedicine(String medicineName) throws IOException, ParserConfigurationException, SAXException {

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=MkSdYE%2Buv%2FKUgV4R8rVZr59Xn0XNCfABz8mQYa9xFwt%2BCigtvqcl2JmkBmpycrnmTdX%2B%2B50a8UNgMqqrB5qvOg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode("MkSdYE%2Buv%2FKUgV4R8rVZr59Xn0XNCfABz8mQYa9xFwt%2BCigtvqcl2JmkBmpycrnmTdX%2B%2B50a8UNgMqqrB5qvOg%3D%3D", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("item_name", "UTF-8") + "=" + URLEncoder.encode(medicineName, "UTF-8")); /*품목명*/


        URL url = new URL(urlBuilder.toString());
        Log.d(TAG, "주소: " + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
       // String xmlFile = sb.toString();
        //--------------------
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(urlBuilder.toString());

        NodeList list = doc.getElementsByTagName("item");
        String[] array = new String[list.getLength()];
        int cnt = 0;
        int nodeCnt = 0;
        String  item_name;
        Node node;
        //=====
        Log.d(TAG, "리스트의 크기!!"+list.getLength());   //여긴 문제없음
        for(int i=0;i<list.getLength();i++){

           // Log.d(TAG, "포문 몇번째?"+i);
            // node = list.item(i);
          /*  for( node= list.item(i).getFirstChild() ; node !=null ; node=node.getNextSibling()){    //38번정도
                Log.d(TAG, "돌림판"+cnt++);
               if(list.item(i).getChildNodes().item(nodeCnt).getNodeName().equals("ITEM_NAME")) {
                    item_name = node.getChildNodes().item(i).getTextContent();
                    array[cnt++] = item_name;

                    Log.d(TAG, "이프문 돌아감? : "+item_name+i);

                }
                nodeCnt++;
            }*/
          //리스트 크기만큼 반복함
          for(int k=0;k<list.getLength();k++){
              //한 리스트 안에 들어있는 자식 노드들의 갯수만큼 반복 함
              for(int j=0;j<list.item(k).getChildNodes().getLength();j++){
                    //리시트(k)번째의 자식 노드들의 이름이 item_name이면 if문 진입
                    if(list.item(k).getChildNodes().item(j).getNodeName().equals("ITEM_NAME")){
                        item_name = list.item(k).getChildNodes().item(j).getTextContent();
                        array[k]=item_name;

                    }
              }
              //Log.d(TAG, "배열에 든값=> "+array[k]);
          }


           // Log.d(TAG, "노드에 담긴 내용=>"+node);
        }
        /*for(int i=0;i<array.length;i++){
            Log.d(TAG, "반복문 안에서의 배열값: "+array[i]);
        }*/
        //=====
        /*for(Node node= list.item(cnt).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
            if(node.getNodeName().equals("ITEM_NAME")) {
                item_name = node.getTextContent();
                array[cnt++] = item_name;

                Log.d(TAG, "외형정보 : "+item_name);
            }

        }*/
        /*for (int i = 0;i<list.getLength();i++){
            Log.d(TAG, "searchMedicine:반복 "+i);
            Log.d(TAG, "searchMedicine:배열=> "+array[i]);
        }*/
        for (int i=0;i<array.length;i++){
            adapter.addItem(new MedicineListDTO(array[i],123));
        }

        adapter.notifyDataSetChanged();
      /*  try {


            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(xmlFile));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            // XPathExpression expr = xpath.compile("/response/body/items/item");
           // XPathExpression expr = xpath.compile("//items/item");
           // NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            NodeList child;
            Node node = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                child = nodeList.item(i).getChildNodes();
                //System.out.println("---------------------------------------차일드의 개수는"+child.getLength());
                for (int j = 0; j < child.getLength(); j++) {
                    node = child.item(j);

                }


            }



        } catch (Exception e) {
            System.out.println(e.getMessage());

        }*/
    }}
