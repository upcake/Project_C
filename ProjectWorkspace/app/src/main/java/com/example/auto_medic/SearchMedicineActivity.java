package com.example.auto_medic;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchMedicineActivity extends AppCompatActivity {
    SearchResultFragment Resultfragment;
    RecyclerView recyclerView;
    EditText searchText;
    Button searchBtn;
    Bundle bundle;
=======

import android.os.Bundle;

public class SearchMedicineActivity extends AppCompatActivity {

>>>>>>> 04e3b668224d3e3b32cd234fe5ca383bc30b842f
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);
<<<<<<< HEAD
        searchBtn = findViewById(R.id.searchBtn);




        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = findViewById(R.id.searchMedicineA_search);
                String data = String.valueOf(searchText.getText());
               // Intent intent = new Intent(SearchMedicineActivity.this,SearchResultFragment.class);
                bundle = new Bundle();
                bundle.putString("data",data);
               // intent.putExtra("data",data);
               // startActivity(intent);
               //Resultfragment.setArguments(bundle);

                Resultfragment = new SearchResultFragment();
                recyclerView = findViewById(R.id.recyclerView);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,Resultfragment).commit();
            }
        });

        Resultfragment = new SearchResultFragment();
        recyclerView = findViewById(R.id.recyclerView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,Resultfragment).commit();


    }
    public void fragBtnClick(Bundle bundle){
        this.bundle = bundle;
=======
>>>>>>> 04e3b668224d3e3b32cd234fe5ca383bc30b842f
    }
}
