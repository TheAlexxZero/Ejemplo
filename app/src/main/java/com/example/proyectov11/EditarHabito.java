package com.example.proyectov11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditarHabito extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextFrecuencia;
    private Button buttonGuardar;
    private int habitId; // ID del hábito que se va a editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_habitos);

        editTextNombre = findViewById(R.id.editarNombreHabito);
        editTextFrecuencia = findViewById(R.id.editarFrecuenciaHabito);
        buttonGuardar = findViewById(R.id.botonGuardarCambios);

        // Obtener el ID del hábito desde el Intent
        habitId = getIntent().getIntExtra("HABIT_ID", -1);
        cargarDatosHabit(habitId);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoNombre = editTextNombre.getText().toString();
                String nuevaFrecuencia = editTextFrecuencia.getText().toString();

                DatabaseHelper db = new DatabaseHelper(EditarHabito.this);
                if (db.actualizarHabit(habitId, nuevoNombre, nuevaFrecuencia)) {
                    Toast.makeText(EditarHabito.this, "Hábito actualizado", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad
                } else {
                    Toast.makeText(EditarHabito.this, "Error al actualizar el hábito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargarDatosHabit(int id) {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getHabitById(id); // Método corregido para obtener el hábito

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String nombreHabit = cursor.getString(cursor.getColumnIndex("nombre_habito"));
            @SuppressLint("Range") String frecuenciaHabit = cursor.getString(cursor.getColumnIndex("frecuencia"));

            editTextNombre.setText(nombreHabit);
            editTextFrecuencia.setText(frecuenciaHabit);
        }
        if (cursor != null) {
            cursor.close(); // Cierra el cursor después de usarlo
        }
    }
}
