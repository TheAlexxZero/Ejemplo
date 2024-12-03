package com.example.proyectov11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditarHabito extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextHoraDormir;
    private EditText editTextHoraDespertar;
    private EditText editTextFrecuencia;
    private EditText editTextTipoEspecifico;
    private Spinner spinnerTipoHabito;
    private RadioGroup radioGroupPlazo;
    private Button buttonGuardar;
    private int habitId; // ID del hábito que se va a editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_habitos);

        // Inicialización de los campos
        editTextNombre = findViewById(R.id.editarNombreHabito);
        editTextHoraDormir = findViewById(R.id.editarHoraDormir);
        editTextHoraDespertar = findViewById(R.id.editarHoraDespertar);
        editTextFrecuencia = findViewById(R.id.editarFrecuenciaHabito);
        editTextTipoEspecifico = findViewById(R.id.editarTipoEspecifico);
        spinnerTipoHabito = findViewById(R.id.spinnerTipoHabito);
        radioGroupPlazo = findViewById(R.id.radioGroupPlazo);
        buttonGuardar = findViewById(R.id.botonGuardarCambios);

        // Obtener el ID del hábito desde el Intent
        habitId = getIntent().getIntExtra("HABIT_ID", -1);
        cargarDatosHabit(habitId);

        // Manejo del botón de guardar
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoNombre = editTextNombre.getText().toString();
                String nuevaHoraDormir = editTextHoraDormir.getText().toString();
                String nuevaHoraDespertar = editTextHoraDespertar.getText().toString();
                String nuevaFrecuencia = editTextFrecuencia.getText().toString();
                String nuevoTipoHabito = spinnerTipoHabito.getSelectedItem().toString();
                String nuevoTipoEspecifico = editTextTipoEspecifico.getText().toString();

                // Obtener el valor de plazo (Corto Plazo o Largo Plazo)
                RadioButton selectedPlazoButton = findViewById(radioGroupPlazo.getCheckedRadioButtonId());
                String nuevoPlazo = selectedPlazoButton != null ? selectedPlazoButton.getText().toString() : "";

                DatabaseHelper db = new DatabaseHelper(EditarHabito.this);
                if (db.updateSleepHabit(habitId, nuevoNombre, nuevaHoraDormir, nuevaHoraDespertar,
                        nuevaFrecuencia, nuevoTipoHabito, nuevoTipoEspecifico, nuevoPlazo)) {
                    Toast.makeText(EditarHabito.this, "Hábito actualizado", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad después de actualizar
                } else {
                    Toast.makeText(EditarHabito.this, "Error al actualizar el hábito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Cargar los datos del hábito en los campos de edición
    private void cargarDatosHabit(int id) {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getSleepHabitById(id); // Método corregido para obtener el hábito por ID

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String nombreHabit = cursor.getString(cursor.getColumnIndex("nombre_habito"));
            @SuppressLint("Range") String horaDormir = cursor.getString(cursor.getColumnIndex("hora_dormir"));
            @SuppressLint("Range") String horaDespertar = cursor.getString(cursor.getColumnIndex("hora_despertar"));
            @SuppressLint("Range") String frecuenciaHabit = cursor.getString(cursor.getColumnIndex("frecuencia"));
            @SuppressLint("Range") String tipoHabito = cursor.getString(cursor.getColumnIndex("tipo_habito"));
            @SuppressLint("Range") String tipoEspecifico = cursor.getString(cursor.getColumnIndex("tipo_especifico"));
            @SuppressLint("Range") String plazoHabit = cursor.getString(cursor.getColumnIndex("plazo"));

            // Establecer los valores en los campos correspondientes
            editTextNombre.setText(nombreHabit);
            editTextHoraDormir.setText(horaDormir);
            editTextHoraDespertar.setText(horaDespertar);
            editTextFrecuencia.setText(frecuenciaHabit);
            editTextTipoEspecifico.setText(tipoEspecifico);

            // Configurar el Spinner para el tipo de hábito
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerTipoHabito.getAdapter();
            int tipoHabitoPosition = adapter.getPosition(tipoHabito);
            spinnerTipoHabito.setSelection(tipoHabitoPosition);

            // Configurar el RadioButton para el plazo
            if ("Corto Plazo".equals(plazoHabit)) {
                radioGroupPlazo.check(R.id.radioCortoPlazo);
            } else {
                radioGroupPlazo.check(R.id.radioLargoPlazo);
            }
        }

        if (cursor != null) {
            cursor.close(); // Cerrar el cursor después de usarlo
        }
    }
}
