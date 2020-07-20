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

public class Fragment4 extends Fragment {
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


        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment4,container,false);
        if (activity.bundle != null) {
            wv1 = rootview.findViewById(R.id.wv4);

            bundle = activity.bundle;
            list = bundle.getStringArrayList("array");

            wv1.loadData(list.get(3).replaceAll("<tbody>","<table border='1'><tbody>").replace("</tbody>","</tbody></table>"), "text/html", null);
            wv1.setBackgroundColor(Ccolor);
        }
        return rootview;
    }
}
