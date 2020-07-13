package com.example.auto_medic;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.log4j.jmx.LoggerDynamicMBean;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class MedicineInfoActivity extends AppCompatActivity {
    private static final String TAG = "MedicineInfoActivity";
    Bitmap bitmap;
    ImageView medicineImg;
    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
    TextView textView8,textView9,textView10,textView11,textView12,textView13,textView14;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);
        medicineImg = findViewById(R.id.medicineImg);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);


        final String data;
        final String[] array = new String[7];
        Intent intent = getIntent();
        data = String.valueOf(intent.getStringExtra("detailName"));
        Log.d(TAG, "파이널이름=>"+data);

        final Handler handler = new Handler();

        dialog = new ProgressDialog(MedicineInfoActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("상세정보 가져오는 중입니다...");
        dialog.setCanceledOnTouchOutside(false);    //바깥쪽을 눌러도 없어지지 않음
        dialog.show();
        new Thread() {

            @Override
            public void run() {
                Log.d(TAG, "run: 1.1단계");
                super.run();
                try {
                    final String[] array = searchMedicine(data);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //약품 이미지 변경


                           // String juso = "https://nedrug.mfds.go.kr/pbp/CCBBB01/getItemDetail?itemSeq=197900277";
                            String juso = "data:image/jpeg;base64,/9j/" +
                                    "4QraRXhpZgAATU0AKgAAAAgABwESAAMAAAABAAEAAAEaAAUAAAABAAAAYgEbAAUAAAABAAAAagEo" +
                                    "AAMAAAABAAIAAAExAAIAAAAiAAAAcgEyAAIAAAAUAAAAlIdpAAQAAAABAAAAqAAAANQALcbAAAAnEAAtxsAAACcQ" +
                                    "QWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpADIwMjA6MDM6MzAgMTc6Mzc6NDQAAAOgAQADAAAAAQABAAC" +
                                    "gAgAEAAAAAQAAAwygAwAEAAAAAQAAAaoAAAAAAAAABgEDAAMAAAABAAYAAAEaAAUAAAABAAABIgEbAAUAAAABAAABKgE" +
                                    "oAAMAAAABAAIAAAIBAAQAAAABAAABMgICAAQAAAABAAAJoAAAAAAAAABIAAAAAQAAAEgAAAAB/9j/7QAMQWRvYmVfQ00AAf" +
                                    "/uAA5BZG9iZQBkgAAAAAH/2wCEAAwICAgJCAwJCQwRCwoLERUPDAwPFRgTExUTExgRDAwMDAwMEQwMDAwMDAwMDAwMDAwMDAw" +
                                    "MDAwMDAwMDAwMDAwBDQsLDQ4NEA4OEBQODg4UFA4ODg4UEQwMDAwMEREMDAwMDAwRDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwM" +
                                    "DAwMDP/AABEIAFcAnwMBIgACEQEDEQH/3QAEAAr/xAE/AAABBQEBAQEBAQAAAAAAAAADAAECBAUGBwgJCgsBAAEFAQEBAQEBA" +
                                    "AAAAAAAAAEAAgMEBQYHCAkKCxAAAQQBAwIEAgUHBggFAwwzAQACEQMEIRIxBUFRYRMicYEyBhSRobFCIyQVUsFiMzRygtFDBy" +
                                    "WSU/Dh8WNzNRaisoMmRJNUZEXCo3Q2F9JV4mXys4TD03Xj80YnlKSFtJXE1OT0pbXF1eX1VmZ2hpamtsbW5vY3R1dnd4eXp7f" +
                                    "H1+f3EQACAgECBAQDBAUGBwcGBTUBAAIRAyExEgRBUWFxIhMFMoGRFKGxQiPBUtHwMyRi4XKCkkNTFWNzNPElBhaisoMHJjXC0" +
                                    "kSTVKMXZEVVNnRl4vKzhMPTdePzRpSkhbSVxNTk9KW1xdXl9VZmdoaWprbG1ub2JzdHV2d3h5ent8f/2gAMAwEAAhEDEQA/AOp" +
                                    "Q230PtfQyxrrqgDZWD7mg/RL2/wApEQayTmZInQMx4H/sQtAtVMkkpVVPteGMEk/cB+85ImlAXoGKcNedQ0keQJWlTjU0jgPf3" +
                                    "e4T/m/uInqE91Cc4vQWzR5c9TTkHQwdD4HRJbBLHjbYA4eDtQqeXgbB6lIJb3ZyR/VTo5QdCKRPDKIsatNJJJSMSkkkklKTd47+" +
                                    "Cs4uI673vltXiOXf1f8Aya0GCukba2hg77efm76TlHPKImtyyQwykL2DkFjwJLXAeO0/3KMzwtsWnxP3lM7Gx8kRYPd+8NHj4O/" +
                                    "8mmjN3C48uQNDbjJI2Vi2Yz9r/c0/QeOD/wCRf/IQVKCCLDCQRoVJJJIqf//Q6lAr/puV/Ux//dhHQK/6blf1Mf8A92FfPRqp2t" +
                                    "c5wa0S5xgDzWpTS3Hr2CC46vd4n/yKB06ka3uHkz/vzlYe4yoM07PCNhu2cEKHEdzt5LF2qjPfwUXPbMSCe4US7uVEGekzT2R63" +
                                    "xoeFVa8CNYnhFY7/UIoLU6hiil/qM/m3ngdj/5kqi3HVNvpdU7hw0PgViOa5ri12jmkgjzCsYpWKO4amaHCbGx/NZGxcc32Qf5t" +
                                    "uryPwb/aQfhqewWtTWMegVjV3Lj4u/OSyz4Y6bnZWKHHLXYbsnGPa0QAAABoAB2ChISJUCdVVbbMOU2ujUIIKm1ycFNlzK8ip1d" +
                                    "glruQOR/Kb/KasS+l9Frqn8t4PYg/ReP6y2K3GUHqlHqUC9v0qufNhOv+Z9JSY5Ua6FgzQscQ3H5OUkkkrDXf/9Hoc5llmOa6mu" +
                                    "e9+4BrbnY8+x8776mvd6f7yVAcMq9pB3CrGBBduMxeNbD/ADn/ABn+EUsnEoygwXB01Euqcxxa5rnNNW9jm/nbHuT9PqrZ1B9LB" +
                                    "FddeK1reYaxuRtbr/JYrx01awF6d3b2iultY/NEfcqt5e91NDHbHZD/AE947CHWWO/remz2qxa78iq2lwdVfWNz8ewWBo/ObDq7" +
                                    "WD+V6b/YqjfiP7P+5ZP+y1ja1ga380yd39YuP5yRc13uaNDwAhWPY7QVG8iXVtgyTHHbb/aS91ddNTiHPqrax7hwXAe//pJJ/lq" +
                                    "lte37RjVPaC00veRwZDw0flSyRXUKb6gWWm1tZbJIcx305b/IQbHE5WO7wxrIP/XGot9u3JqyeaLwKzp/N2ARs/qWIq7eRdCt0O" +
                                    "0Wf1SrZk7xxYJ+Y9rlbY78EPqommlx5BI+8KTGakPFgzC4Hw1amAwPymEiQyXkfD6P/TV+w6/BVumiPVf/AFWj57nf99RXu1Kbm" +
                                    "Nzrsnlx6L7lYnsoucoueAhl+qYzJQURrlV3x31RGPRCKbjHd0dm1zHMdqCCCPI8qnW5WaXax96K0hxLKzVY6p3Nbi0/IwmVnqIH" +
                                    "2x5H5wa75loVZWomwD3DRIokdi//0upQaHbc/Id4Mxz/AO3CMgV/03K/qY//ALsK+WqNHUdZuYCOwQH2eCE17mat1Hdv/kU3qNd" +
                                    "9E/Luqs4GJo/Qt/HMTFj6hIb7IIBInQnuoSoE6pSU1el9RxABMgcIjLHAR2JBI8wq6m2U5TfoeTohdRuD3MrBkMkn4nRB+0BgIZ" +
                                    "q7x7BBJJJJ1J5UuKOvE1uYmAOEbndPiW+m5zTw4A/Np/8AIlGtePvVJTN8D3cePZDNA3xD6q5eYrgO/wCizc9DLlEunhMD4KFss" +
                                    "5ThyFKkCipt12GRqrlVgDdx8FmscJkmB3JT2ZBe3Y3RqdGJkaDHkkIiywvsFtz3jgmG/Ae1qgkkrQFCmiTZvu//0+luycfHDDkW" +
                                    "tqFh2s3mJd+61BxbWXZWRdTdTdS/06orLi9r2b9rXz+j/Seq/wCgjXY+PkBovrFgYSWzOhP0uCFAYOE2t1QoZ6Ty1z2EbmuLDLN" +
                                    "zX7t21XzbVbGx/wC6fuUX0b9XMM/vCQf84IH7O6d/3Dx/+2a//IJfs7p3/cPH/wC2a/8AyCR13AUDRsEjySnGt/Nc8eRG7+5N9n" +
                                    "yRwQfiw/3oY6d02ROHjx3/AENf/kFTqw9oByMDGI9Cwu9LEaXeu20sp217/dXZjfpGUb/+MyUwwj+79hZBmyfvfaA6XoZHd0fBn" +
                                    "95T+g/87e/46D/oqlh4OK/Epfk4VDb3Nmxpx2MIMmGvr2e1+3+c/wAHv/mv0SN+zunf9w8f/tmv/wAgiIR/dCjlyHeR+" +
                                    "+c2CW9wDO2R/K2qv+zunf9w8f/tmv/wAgp04uNjl5x6mU+pt3itoaDt3bPayG/nuTtWNKkkkipgaWHUS0/wAkx+H0VH0njiz72/" +
                                    "3FFSTTjgen7F4y5BtI/mj2W/vt+4/3pxW/vYfkAP8AySmkh7UOyTnyH9L8AxDGjxJ8SZUkiCDBBBHI7pJwAGwpjJJNkk+akkkkV" +
                                    "P8A/9TqUl4CktBqvvyS8BSSU+/JLwFJJT78kvAUklPvyS8BSSU+/JLwFJJT78kvAUklPvynS4tta4N38gskiQQWuhzfc32/nL5+";

                            try {
                                new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();

                                        try {
                                            URL url = new URL(juso);
                                            Source source = null;
                                            source = new Source(url);

                                            Log.d(TAG, "run: " + source);
                                            List<Element> list = source.getAllElements(HTMLElementName.IMG);
                                            Log.d(TAG, "리스트크기: " + list.size());

                                            Log.d(TAG, "리스트(0)에 든값:" + list.get(0).getAttributeValue("src"));
                                            Log.d(TAG, "리스트(1)에 든값:" + list.get(1).getAttributeValue("src"));
                                            String data = list.get(1).getAttributeValue("src");
                                            URL url2 = new URL(data);
                                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                                            conn.setDoInput(true); // 서버로 부터 응답 수신
                                            conn.connect();


                                            InputStream is = conn.getInputStream();
                                            bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환
                                            Log.d(TAG, "비트맵이전");
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Log.d(TAG, "run: 제2으 핸들러");
                                                    medicineImg.setImageBitmap(bitmap);
                                                }
                                            });
                                                    
                                           
                                            

                                            Log.d(TAG, "비트맵다음");
                                            System.out.println("jsoup 진입" + data);


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
                            textView2.setText(array[0]);
                            textView4.setText(array[1].replace("|","\n"));
                            textView6.setText(array[2]);
                            textView8.setText(array[3]);
                            textView10.setText(array[4]);
                            textView12.setText(array[5]);
                            textView14.setText(array[6]);

                            dialog.cancel();
                        }
                    });


                } catch (IOException | ParserConfigurationException | SAXException e) {
                    e.printStackTrace();
                }

            }

        }.start();


    }

    //jsoup
    public void jsoup() throws IOException, ParserConfigurationException, SAXException {
        System.out.println("jsoup 진입");
        String juso = "https://nedrug.mfds.go.kr/pbp/CCBBB01/getItemDetail?itemSeq=197900277";

        try {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    URL url = null;
                    try {
                        url = new URL(juso);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Source source = null;
                    try {
                        source = new Source(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(source);
                    List<Element> list = source.getAllElements(HTMLElementName.IMG);

                    System.out.println("반복문진입전");
                    for(int i = 0; i < list.size(); i++) {
                        String data = list.get(1).getAttributeValue("src");
                        System.out.println("jsoup 진입"+ data);
                    }
                }

            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            }

        }
        String[] array = new String[0];
        array = new String[]{chart,material_name ,storage_method ,valid_term , ee_doc_data, ud_doc_data,nb_doc_data};

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

        }
        return array;
    }
}
