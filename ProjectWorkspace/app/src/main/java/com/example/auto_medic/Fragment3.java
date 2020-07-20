package com.example.auto_medic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment3 extends Fragment {
    MedicineInfoActivity activity;
    Bundle bundle;
    ArrayList<String> list = new ArrayList<>();
    WebView wv3;
    int Ccolor= Color.parseColor("#C6DFD6");

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MedicineInfoActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment3,container,false);
        if (activity.bundle != null) {
            wv3 = rootview.findViewById(R.id.wv3);

            bundle = activity.bundle;
            list = bundle.getStringArrayList("array");

            wv3.loadData(list.get(2).replaceAll("<tbody>","<table border='1'><tbody>").replace("</tbody>","</tbody></table>"), "text/html", null);
            wv3.setBackgroundColor(Ccolor);

        }
        return rootview;
    }
}
