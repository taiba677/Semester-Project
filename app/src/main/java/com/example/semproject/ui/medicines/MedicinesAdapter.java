package com.example.semproject.ui.medicines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semproject.R;
import com.example.semproject.db.DatabaseHelper;
import com.example.semproject.model.Medicine;
import com.example.semproject.ui.home.HomeItem;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.ViewHolder> {

    private List<Medicine> medicinesList;
    private OnDeleteClickListener onDeleteClickListener;
    private OnEditClickListener onEditClickListener;

    public interface OnEditClickListener {
        void onEditClick(Medicine medicine);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String medicineId);
    }

    public MedicinesAdapter(List<Medicine> medicinesList, OnDeleteClickListener onDeleteClickListener, OnEditClickListener onEditClickListener) {
        this.medicinesList = medicinesList;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEditClickListener = onEditClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine medicine = medicinesList.get(position);
        holder.textMedicine.setText(medicine.getMedicineName());
        holder.textDosageSummary.setText("No. of doses: "+medicine.getNoOfDoses()+"");
        holder.imageButtonDelete.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(medicine.getId()));
        holder.imageButtonEdit.setOnClickListener(v -> onEditClickListener.onEditClick(medicine));
    }

    @Override
    public int getItemCount() {
        return medicinesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        MaterialTextView textMedicine, textDosageSummary;
        MaterialCardView cardView;
        MaterialCheckBox checkBox;
        ImageButton imageButtonDelete, imageButtonEdit;

        ViewHolder(View itemView)
        {
            super(itemView);

            textDosageSummary = itemView.findViewById(R.id.dosage_text_view);
            textMedicine = itemView.findViewById(R.id.medicine_name_text_view);
            cardView = itemView.findViewById(R.id.card_view_medicine);
            checkBox = itemView.findViewById(R.id.medicine_checkbox);
            imageButtonDelete = itemView.findViewById(R.id.medicine_delete_button);
            imageButtonEdit = itemView.findViewById(R.id.medicine_edit_button);

        }
    }
}
