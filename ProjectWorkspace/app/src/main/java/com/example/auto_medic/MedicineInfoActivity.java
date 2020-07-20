package com.example.auto_medic;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class MedicineInfoActivity extends AppCompatActivity {
    private static final String TAG = "MedicineInfoActivity";

    String[] array = new String[7];
    String[] title = {"외형정보","성분정보","보관방법","유효기간","효능효과","용법용량","주의사항"};
    private ImageView medicineImg;
    Bundle bundle;
    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
    TextView textView8,textView9,textView10,textView11,textView12,textView13,textView14;
    ProgressDialog dialog;
    WebView wv1,wv2,wv3,wv4;
    ViewPager pager;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);
        medicineImg = findViewById(R.id.medicineImg);

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(1);  //페이지 몇개를 보여줄것인가?

        //페이저스트립 부분
        PagerTitleStrip titleStrip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        titleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
        titleStrip.setTextColor(R.color.design_default_color_background);

        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        //객체생성
        Fragment fragment1 = new Fragment1();
        Fragment fragment2 = new Fragment2();
        Fragment fragment3 = new Fragment3();
        Fragment fragment4 = new Fragment4();
        Fragment fragment5 = new Fragment5();
        Fragment fragment6 = new Fragment6();
        Fragment fragment7 = new Fragment7();

        adapter.addItem(fragment1);
        adapter.addItem(fragment2);
        adapter.addItem(fragment3);
        adapter.addItem(fragment4);
        adapter.addItem(fragment5);
        adapter.addItem(fragment6);
        adapter.addItem(fragment7);

       /* pager.setAdapter(adapter);*/

        String urlImg = null;


        final String data;

         Intent intent = getIntent();
        data = String.valueOf(intent.getStringExtra("detailName"));
         Log.d(TAG, "파이널이름=>"+data);

        a.medicineName = data;
        a.start();
        try {
            a.join();
            b.medicineCode = a.medicineCode;
            b.start();
            b.join();
            urlImg = b.getImgUrl;
            Log.d(TAG, "onCreate: b쓰레드에서의 이미지"+urlImg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();

        dialog = new ProgressDialog(MedicineInfoActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("상세정보 가져오는 중입니다...");
        dialog.setCanceledOnTouchOutside(false);    //바깥쪽을 눌러도 없어지지 않음
        dialog.show();

        //final String[] imgurl = new String[1];
        String finalUrlImg = urlImg;
        Log.d(TAG, "onCreate: 파이널이미지"+finalUrlImg);
        new Thread() {

            @Override
            public void run() {
                Log.d(TAG, "run: 1.1단계");
                super.run();
                try {
                       array = searchMedicine(data);
                    bundle = new Bundle();
                    bundle.putString("data", data);
                    ArrayList<String> list = new ArrayList<>();
                    for(int i=0;i<array.length;i++){
                        list.add(array[i]);
                    }
                    bundle.putStringArrayList("array",list);



                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //약품 이미지

                            try {
                                new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();

                                        try {
                                         // Bitmap으로 변환
                                            Log.d(TAG, "비트맵이전");
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {

                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                        Log.d(TAG, "run: 익셉션발생");
                                                    }finally {
                                                        Log.d(TAG, "run: 익셉션 끝!");
                                                    }

                                                }
                                            });

                                            //  System.out.println("jsoup 진입" + data);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.d(TAG, "run: 안쪽익셉션");
                                        }
                                    }
                                }.start();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d(TAG, "익셉션발생!");
                            }
                            //System.out.println(source);
                            //================================================================
                            Log.d(TAG, "핸들러에서 사용: "+Arrays.toString(array));

                            dialog.cancel();


                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }.start();


        // Glide로 이미지 표시하기
        //사진이 있으면 해당 사진 출력 없으면 기본이미지 출력
        Log.d(TAG, "onCreate:이미지주소 "+finalUrlImg);
        if(finalUrlImg != null){
            Glide.with(this).load(finalUrlImg).into(medicineImg);
        }else{
            medicineImg.setImageResource(R.drawable.nodata);
        }
        pager.setAdapter(adapter);

    }


    //페이저 어댑터
    class MyPagerAdapter extends FragmentStatePagerAdapter { //리스트 컨트롤
        ArrayList<Fragment> items = new ArrayList<>();

        //생성자
        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    //쓰레드A => 약의 코드를 구하고
    public class ThreadA extends Thread{
        String medicineName;

        String[] array = new String[7];
        String medicineCode;
        //String setImgUrl;
        @Override
        public void run() {
            try {
                Log.d(TAG, "run: 쓰레드a돌아감");
                Log.d(TAG, "run: 네임값?"+medicineName);
                //=>네임값 널이 뜸
               array = searchMedicine(medicineName);
                Log.d(TAG, "run: 쓰레드a2돌아감");
                medicineCode = array[7];
                Log.d(TAG, "run: 쓰레드a3돌아감");

            }catch (Exception e){
                Log.d(TAG, "run:  쓰레드a에서 익셉션 발생!!!");
            }
        }
    }

    //쓰레드B => 쓰레드A에서 구한 약의 코드로 약의 이미지 주소를 뽑아온다.
    public class ThreadB extends Thread{
        String medicineCode;
        String getImgUrl;
        @Override
        public void run() {
            try {
                Log.d(TAG, "run: 쓰레드B돌아감");
                getImgUrl = searchCode(medicineCode);
                Log.d(TAG, "쓰레드안에서의 비의 이미지"+getImgUrl);
            }catch (Exception e){
                Log.d(TAG, "run:  쓰레드B에서 익셉션 발생!!!");
            }
        }
    }

    //약 이미지 주소 가져오기 메소드
    public String searchCode(String medicineCode) throws IOException, ParserConfigurationException, SAXException {
        //String servicekey = "MkSdYE%2Buv%2FKUgV4R8rVZr59Xn0XNCfABz8mQYa9xFwt%2BCigtvqcl2JmkBmpycrnmTdX%2B%2B50a8UNgMqqrB5qvOg%3D%3D";
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=MkSdYE%2Buv%2FKUgV4R8rVZr59Xn0XNCfABz8mQYa9xFwt%2BCigtvqcl2JmkBmpycrnmTdX%2B%2B50a8UNgMqqrB5qvOg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("item_seq","UTF-8") + "=" + medicineCode); /*품목명*/

        URL url = new URL(urlBuilder.toString());
        Log.d(TAG, "약 이미지 검색 해주는 주소: " + url);
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
        String xmlFile = sb.toString();
        //--------------------
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(urlBuilder.toString());

        NodeList list = doc.getElementsByTagName("item");

        String ITEM_IMAGE = null;

        for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
            if(node.getNodeName().equals("ITEM_IMAGE")) {
                ITEM_IMAGE = node.getTextContent();

                //Log.d(TAG, "외형정보 : "+ITEM_IMAGE);
            }

        }

        Log.d(TAG, "이미지주소:"+ITEM_IMAGE);
        return ITEM_IMAGE;
    }
    //약 검색 메소드
    public  String[] searchMedicine(String medicineName) throws IOException, ParserConfigurationException, SAXException {

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
        String xmlFile = sb.toString();
        //--------------------
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(urlBuilder.toString());

        NodeList list = doc.getElementsByTagName("item");

        String  chart = null;
        String material_name = null;
        String storage_method = null;
        String valid_term = null;
        String ee_doc_data = null;
        String ud_doc_data = null;
        String nb_doc_data = null;
        String ITEM_SEQ = null;

        for(Node node= list.item(0).getFirstChild() ; node !=null ; node=node.getNextSibling()) {
            if(node.getNodeName().equals("CHART")) {
                chart = node.getTextContent();

                Log.d(TAG, "외형정보 : "+chart);
            }
            else if(node.getNodeName().equals("MATERIAL_NAME")) {
                material_name = node.getTextContent();
                Log.d(TAG, "성분정보 : "+material_name);
            }
            else if(node.getNodeName().equals("STORAGE_METHOD")) {
                storage_method = node.getTextContent();
                System.out.println("보관방법 : "+storage_method);
            }
            else if(node.getNodeName().equals("VALID_TERM")) {
                valid_term = node.getTextContent();
                System.out.println("유효기간 : "+valid_term);
            }
            else if(node.getNodeName().equals("EE_DOC_DATA")) {
                ee_doc_data = node.getTextContent();
                ee_doc_data.trim();
                Spanned ex = Html.fromHtml(ee_doc_data);
                System.out.println("효능효과 : "+ee_doc_data);
            }
            else if(node.getNodeName().equals("UD_DOC_DATA")) {
                ud_doc_data = node.getTextContent();
                ud_doc_data.trim();
                System.out.println( "용법용량 : "+ud_doc_data);
                // Spanned ex = Html.fromHtml(nb_doc_data);
            }
            else if(node.getNodeName().equals("NB_DOC_DATA")) {
                nb_doc_data = node.getTextContent();
                nb_doc_data.trim();
                System.out.println("주의사항 : "+nb_doc_data);
                // Spanned ex = Html.fromHtml(nb_doc_data);
            }else if(node.getNodeName().equals("ITEM_SEQ")) {
                ITEM_SEQ = node.getTextContent();
                ITEM_SEQ.trim();
               // System.out.println("주의사항 : "+nb_doc_data);
                // Spanned ex = Html.fromHtml(nb_doc_data);
            }

        }
        String[] array = new String[0];
        array = new String[]{chart,material_name ,storage_method ,valid_term , ee_doc_data, ud_doc_data,nb_doc_data,ITEM_SEQ};

        BufferedReader br = null;

        try {


            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(xmlFile));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            // XPathExpression expr = xpath.compile("/response/body/items/item");
            XPathExpression expr = xpath.compile("//items/item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
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
            Log.d(TAG, "searchMedicine에서 익셉션 발생!!!");
        }
        return array;
    }
}