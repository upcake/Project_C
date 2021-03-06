package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class SearchMedicineActivity extends AppCompatActivity {
    MedicineInfoActivity test = new MedicineInfoActivity();
    SearchResultFragment Resultfragment;
    RecyclerView recyclerView;
    EditText searchText;
    //Button searchBtn;
    ImageView searchBtn;
    Bundle bundle;
    ProgressDialog dialog;
    private static final String TAG = "SearchMedicineActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);

        searchBtn = findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = findViewById(R.id.searchMedicineA_search);
                String data = String.valueOf(searchText.getText());
                if(data.equals("")){

                    Toast.makeText(SearchMedicineActivity.this, "검색할 약을 입력하세요!!!", Toast.LENGTH_SHORT).show();
                }else{
                    // Intent intent = new Intent(SearchMedicineActivity.this,SearchResultFragment.class);
                    bundle = new Bundle();
                    bundle.putString("data", data);
                    data = null;
                    // intent.putExtra("data",data);
                    // startActivity(intent);
                    //Resultfragment.setArguments(bundle);

                    Resultfragment = new SearchResultFragment();
                    recyclerView = findViewById(R.id.recyclerView);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, Resultfragment).commit();
                }

            }
        });

        Resultfragment = new SearchResultFragment();
        recyclerView = findViewById(R.id.recyclerView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, Resultfragment).commit();

    }

    public void fragBtnClick(Bundle bundle) {
        this.bundle = bundle;

    }
}