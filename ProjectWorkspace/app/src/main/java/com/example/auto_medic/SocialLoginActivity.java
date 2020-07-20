package com.example.auto_medic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.auto_medic.ATask.JoinInsert;
import com.example.auto_medic.ATask.LoginSelect;
import com.example.auto_medic.ATask.NaverJoin;
import com.example.auto_medic.ATask.NaverLogin;
import com.example.auto_medic.Dto.MemberDTO;
import com.example.auto_medic.Dto.NaverMemberDTO;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import static com.example.auto_medic.LoginActivity.loginDTO;


public class SocialLoginActivity extends AppCompatActivity {
    private static final String TAG = "mainSocialLoginActivity";

    ImageView social_NaverBtn, social_KakaoBtn;
    Button social_BackBtn, social_NlogOutBtn, social_KlogOutBtn;

    //카카오로그인 필요부분
    SessionCallback sessionCallback;
    Session session;

    //네이버로그인 필요부분
    public static NaverMemberDTO NaverLoginDTO = null;

    private static String OAUTH_CLIENT_ID = "przzu6N6M4M9BvGyF5QG";
    private static String OAUTH_CLIENT_SECRET = "1lG22iUMlW";
    private static String OAUTH_CLIENT_NAME = "Auto_medic";

    public static OAuthLogin mOAuthLoginInstance;
    public static Map<String,String> mUserInfoMap;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        sessionCallback = new SessionCallback(getApplicationContext());

        social_NaverBtn = findViewById(R.id.social_NaverBtn);
        social_KakaoBtn = findViewById(R.id.social_KakaoBtn);
        social_BackBtn = findViewById(R.id.social_BackBtn);
        social_NlogOutBtn = findViewById(R.id.social_NlogOutBtn);
        social_KlogOutBtn = findViewById(R.id.social_KlogOutBtn);

        /****************************************************************************************************************
         * Naver
         ****************************************************************************************************************/

        //네이버 로그인 참고 : (http://blog.naver.com/PostView.nhn?blogId=waitout1&logNo=221421665687)
        //context 저장
        mContext = SocialLoginActivity.this;

        //1. 초기화
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        //네이버로그인 이미지 클릭시
        social_NaverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginInstance.startOauthLoginActivity(SocialLoginActivity.this, mOAuthLoginHandler);


            }

        });

        /*//네이버 로그아웃 버튼 클릭시
        //로그아웃 관련
        //네이버 로그아웃에 대한 별도의 api가 없으며 사용자가 직접 네이버 서비스에서 로그아웃 하도록 처리하셔야 합니다.
        //이유는 이용자 보호를 위해 정책상 네이버 이외의 서비스에서 네이버 로그아웃을 수행하는 것을 허용하지 않고 있는 점 양해 부탁드립니다.
        social_NlogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginInstance.logout(mContext);

                boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(mContext);

                if (!isSuccessDeleteToken) {
                    // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                    // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                    Log.d(TAG, "errorCode:" + mOAuthLoginInstance.getLastErrorCode(mContext));
                    Log.d(TAG, "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(mContext));

                }
            }
        });*/

        /****************************************************************************************************************
         * Kakao
         ****************************************************************************************************************/

        // 카카오세션 콜백 등록
        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        //카카오로그인 이미지 클릭시
        social_KakaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.open(AuthType.KAKAO_LOGIN_ALL, SocialLoginActivity.this);
                Log.d(TAG, "onClick: 카카오로그인완료");

                /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);*/

               // finish();
            }
        });

        //카카오 로그아웃 버튼 클릭시
        social_KlogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Log.d(TAG, "onCompleteLogout: 로그아웃완료");
                                Toast.makeText(SocialLoginActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        /****************************************************************************************************************
         * 뒤로가기버튼
         ****************************************************************************************************************/

        //뒤로가기 버튼 클릭시
        social_BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /****************************************************************************************************************
     * Kakao
     ****************************************************************************************************************/

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 카카오세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /****************************************************************************************************************
     * Naver
     ****************************************************************************************************************/


    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            //로그인 인증 성공
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                Log.d(TAG,1 + accessToken);
                Log.d(TAG,2 + refreshToken);
                Log.d(TAG,3 + String.valueOf(expiresAt));
                Log.d(TAG,4 + tokenType);
                Log.d(TAG,5 + mOAuthLoginInstance.getState(mContext).toString());
                RequestApiTask rat = new RequestApiTask();
                rat.execute();
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private class RequestApiTask extends AsyncTask<Void, Void, Void> {
        String naver_email, naver_nickname;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            mUserInfoMap = requestNaverUserInfo(mOAuthLoginInstance.requestApi(mContext, at, url));
            return null;
        }

        protected void onPostExecute(Void content) {

            if (mUserInfoMap.get("email") == null) {
                Toast.makeText(mContext, "로그인 실패하였습니다.  잠시후 다시 시도해 주세요!!", Toast.LENGTH_SHORT).show();
            } else {

                //네이버 로그인 성공
                Log.d(TAG, 6+ String.valueOf(mUserInfoMap));
                naver_nickname = mUserInfoMap.get("nickname");
                naver_email = mUserInfoMap.get("email");
                Log.d(TAG, "onPostExecute: "+ naver_email);
                Log.d(TAG, "onPostExecute: "+ naver_nickname);

                NaverLogin naverLogin = new NaverLogin(naver_email, naver_nickname);
                try {
                    String result = naverLogin.execute().get();
                    Log.d(TAG, "onSessionOpened: " + result);
                    Log.d(TAG, "onSessionOpened: " + NaverLoginDTO);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(SocialLoginActivity.this, "네이버 로그인 됐습니다...", Toast.LENGTH_SHORT).show();

                if(NaverLoginDTO == null){
                    NaverJoin naverJoin = new NaverJoin(naver_email, naver_nickname);
                    try {
                        String result = naverJoin.execute().get();
                        Log.d(TAG, "onSessionOpened: " + result);
                    } catch (Exception e) {
                        Log.d(TAG, "onPostExecute: " + e.getMessage());
                    }

                    Toast.makeText(SocialLoginActivity.this, "네이버 로그인 됐습니다...", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess: 네이버로그인됐습니다");

                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();


                /*LoginSelect loginSelect = new LoginSelect(member_email, "0000");
                try {
                    String result = loginSelect.execute().get();
                    Log.d(TAG, "onSessionOpened: " + result);
                    Log.d(TAG, "onSessionOpened: " + loginDTO);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                /*if(loginDTO == null){

                    JoinInsert joinInsert = new JoinInsert(member_email, "0000", member_nickname,"");
                    try {
                        String result = joinInsert.execute().get();
                        Log.d(TAG, "onSessionOpened: " + result);
                    } catch (Exception e) {
                        Log.d(TAG, "onPostExecute: " + e.getMessage());
                    }
                    loginDTO = new MemberDTO(member_email, member_nickname, null);
                    Log.d(TAG, "onSuccess: 네이버로그인됐습니다"+loginDTO.getMember_email());

                }*/


            }

        }
    }
    private static Map<String,String> requestNaverUserInfo(String data) { // xml 파싱
        String f_array[] = new String[9];

        try {
            XmlPullParserFactory parserCreator = XmlPullParserFactory
                    .newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            InputStream input = new ByteArrayInputStream(
                    data.getBytes("UTF-8"));
            parser.setInput(input, "UTF-8");

            int parserEvent = parser.getEventType();
            String tag;
            boolean inText = false;
            boolean lastMatTag = false;

            int colIdx = 0;

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.compareTo("xml") == 0) {
                            inText = false;
                        } else if (tag.compareTo("data") == 0) {
                            inText = false;
                        } else if (tag.compareTo("result") == 0) {
                            inText = false;
                        } else if (tag.compareTo("resultcode") == 0) {
                            inText = false;
                        } else if (tag.compareTo("message") == 0) {
                            inText = false;
                        } else if (tag.compareTo("response") == 0) {
                            inText = false;
                        } else {
                            inText = true;

                        }
                        break;
                    case XmlPullParser.TEXT:
                        tag = parser.getName();
                        if (inText) {
                            if (parser.getText() == null) {
                                f_array[colIdx] = "";
                            } else {
                                f_array[colIdx] = parser.getText().trim();
                            }

                            colIdx++;
                        }
                        inText = false;
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        inText = false;
                        break;

                }

                parserEvent = parser.next();
            }
        } catch (Exception e) {
            Log.e("dd", "Error in network call", e);
        }
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("email"           ,f_array[1]);
        resultMap.put("nickname"        ,f_array[2]);

        return resultMap;
    }

}

