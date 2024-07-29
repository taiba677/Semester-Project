package com.example.semproject.ui.medicines;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semproject.R;
import com.example.semproject.model.Medicine;
import com.example.semproject.ui.MedicineEditDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMedicinesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MedicinesAdapter adapter;
    private List<Medicine> medicinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicines);

        recyclerView = findViewById(R.id.recycler_view_medicine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicinesList = new ArrayList<>();
        adapter = new MedicinesAdapter(medicinesList, this::deleteMedicine, this::editMedicine);
        recyclerView.setAdapter(adapter);

        fetchMedicines();

    }

    private void fetchMedicines() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicines").child(uid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    medicinesList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Medicine medicine = snapshot.getValue(Medicine.class);
                        if (medicine != null) {
                            medicine.setId(snapshot.getKey());
                            medicinesList.add(medicine);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ViewMedicinesActivity.this, "Failed to fetch medicines", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void deleteMedicine(String medicineId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicines").child(uid).child(medicineId);
            reference.removeValue().addOnSuccessListener(aVoid -> {
                Toast.makeText(ViewMedicinesActivity.this, "Medicine deleted successfully", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(ViewMedicinesActivity.this, "Failed to delete medicine: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void editMedicine(Medicine medicine) {
        MedicineEditDialog dialog = new MedicineEditDialog(this, medicine, updatedMedicine -> {
            updateMedicineInDatabase(updatedMedicine);
        });
        dialog.show();
    }

    private void updateMedicineInDatabase(Medicine medicine) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicines").child(uid).child(medicine.getId());
            reference.setValue(medicine).addOnSuccessListener(aVoid -> {
                Toast.makeText(ViewMedicinesActivity.this, "Medicine updated successfully", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(ViewMedicinesActivity.this, "Failed to update medicine: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}