package com.example.proyectov11;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EliminarHabitos extends AppCompatActivity {
    private Spinner spinnerEliminarHabito;
    private Button botonEliminarHabito;
    private DatabaseHelper databaseHelper;
    private String[] habitNames; // Array para guardar los nombres de los hábitos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_habitos); // Cambia el nombre del layout si es necesario

        spinnerEliminarHabito = findViewById(R.id.spinnerEliminarHabito);
        botonEliminarHabito = findViewById(R.id.botonEliminarHabito);
        databaseHelper = new DatabaseHelper(this);

        // Cargar los hábitos en el Spinner
        loadHabitNames();

        // Configurar el botón de eliminar
        botonEliminarHabito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedHabit = spinnerEliminarHabito.getSelectedItem().toString();
                if (deleteHabit(selectedHabit)) {
                    Toast.makeText(EliminarHabitos.this, "Hábito eliminado", Toast.LENGTH_SHORT).show();
                    loadHabitNames(); // Recargar los hábitos después de eliminar
                } else {
                    Toast.makeText(EliminarHabitos.this, "Error al eliminar el hábito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadHabitNames() {
        Cursor cursor = databaseHelper.getAllHabits();
        habitNames = new String[cursor.getCount()];

        int i = 0;
        while (cursor.moveToNext()) {
            habitNames[i] = cursor.getString(cursor.getColumnIndexOrThrow("nombre_habito"));
            i++;
        }
        cursor.close();

        // Configurar el adaptador del Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, habitNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEliminarHabito.setAdapter(adapter);
    }

    private boolean deleteHabit(String habitName) {
        return databaseHelper.deleteHabit(habitName); // Implementa este método en DatabaseHelper
    }
}
