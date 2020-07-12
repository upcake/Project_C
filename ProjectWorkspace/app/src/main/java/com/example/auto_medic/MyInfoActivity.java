package com.example.auto_medic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.auto_medic.Common.CommonMethod;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfoActivity extends AppCompatActivity {
    //객체 선언
    CircleImageView myInfo_Profile;
    Button myInfo_Accept;
    EditText myInfo_Password, myInfo_Password_Re, myInfo_Nickname, myInfo_Phonenum;
    Intent intent;

    CommonMethod commonMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        //객체 초기화
        myInfo_Profile = findViewById(R.id.myInfo_Profile);
        myInfo_Accept = findViewById(R.id.myInfo_Accept);
        myInfo_Password = findViewById(R.id.myInfo_Password);
        myInfo_Password_Re = findViewById(R.id.myInfo_Password_Re);
        myInfo_Nickname = findViewById(R.id.myInfo_Nickname);
        myInfo_Phonenum = findViewById(R.id.myInfo_Phonenum);

        //프사 변경
        myInfo_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 102);
            }
        });


        //확인 버튼
        myInfo_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 102 && resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            myInfo_Profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
