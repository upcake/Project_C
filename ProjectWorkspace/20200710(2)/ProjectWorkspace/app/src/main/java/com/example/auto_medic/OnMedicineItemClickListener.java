package com.example.auto_medic;

import android.view.View;

public interface OnMedicineItemClickListener {
    public void onItemClick(MedicineListAdapter.ViewHolder holderm, View view, int position);
}
