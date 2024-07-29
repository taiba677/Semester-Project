package com.example.semproject.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;

import com.example.semproject.R;
import com.example.semproject.model.Medicine;

public class MedicineEditDialog extends Dialog {

    private Medicine medicine;
    private OnMedicineUpdatedListener onMedicineUpdatedListener;
    private EditText editTextMedicineName;

    public interface OnMedicineUpdatedListener {
        void onMedicineUpdated(Medicine updatedMedicine);
    }

    public MedicineEditDialog(@NonNull Context context, Medicine medicine, OnMedicineUpdatedListener onMedicineUpdatedListener) {
        super(context);
        this.medicine = medicine;
        this.onMedicineUpdatedListener = onMedicineUpdatedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_medicine);

        editTextMedicineName = findViewById(R.id.edit_text_medicine_name);
        editTextMedicineName.setText(medicine.getMedicineName());

        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonCancel = findViewById(R.id.button_cancel);

        buttonUpdate.setOnClickListener(v -> {
            String updatedName = editTextMedicineName.getText().toString().trim();
            if (!updatedName.isEmpty()) {
                medicine.setMedicineName(updatedName);
                onMedicineUpdatedListener.onMedicineUpdated(medicine);
                dismiss();
            } else {
                editTextMedicineName.setError("Medicine name cannot be empty");
            }
        });

        buttonCancel.setOnClickListener(v -> dismiss());
    }
}
