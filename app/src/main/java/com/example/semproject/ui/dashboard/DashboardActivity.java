package com.example.semproject.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.semproject.R;
import com.example.semproject.db.DatabaseHelper;
import com.example.semproject.ui.DrugSearchActivity;
import com.example.semproject.ui.home.AddDialog;
import com.example.semproject.ui.home.HomeFragment;
import com.example.semproject.ui.home.TimeItem;
import com.example.semproject.ui.info.MedicationTipsActivity;
import com.example.semproject.ui.medicines.ViewMedicinesActivity;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    public static ArrayList<TimeItem> timeItems= new ArrayList<>();
    CardView addMedicine, viewMedicines, historyimage, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addMedicine = findViewById(R.id.addMedicine);
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog addMedicineDialog = new AddDialog(DashboardActivity.this);
                addMedicineDialog.show(getSupportFragmentManager(), "Add_Dialog");
            }
        });

        viewMedicines = findViewById(R.id.viewMedicines);
        viewMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ViewMedicinesActivity.class));
            }
        });

        historyimage = findViewById(R.id.historyimage);
        historyimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MedicationTipsActivity.class));
            }
        });

        help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, DrugSearchActivity.class));
            }
        });

    }
}
