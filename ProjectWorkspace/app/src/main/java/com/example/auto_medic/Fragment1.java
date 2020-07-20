package com.example.auto_medic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Fragment1 extends Fragment {
    MedicineInfoActivity activity;
    Bundle bundle;
    ArrayList<String> list = new ArrayList<>();
    WebView wv1;
    int Ccolor= Color.parseColor("#C6DFD6");
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MedicineInfoActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment1,container,false);
        //textViewv1 = rootview.findViewById(R.id.tv1);
        if (activity.bundle != null) {
            wv1 = rootview.findViewById(R.id.wv1);

            bundle = activity.bundle;
           list = bundle.getStringArrayList("array");

            wv1.loadData(list.get(0).replaceAll("<tbody>","<table border='1'><tbody>").replace("</tbody>","</tbody></table>"), "text/html", null);
            wv1.setBackgroundColor(Ccolor);
        }

        return rootview;
    }
}
