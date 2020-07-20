package com.example.auto_medic;

import android.widget.ImageView;

public class MedicineListDTO {
    String name;
    int resID;

    public MedicineListDTO(String name, int resID) {
        this.name = name;
        this.resID = resID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}
