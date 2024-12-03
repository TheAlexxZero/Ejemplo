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
        setContentView(R.layout.activity_eliminar_habitos);

        spinnerEliminarHabito = findViewById(R.id.spinnerEliminarHabito);
        botonEliminarHabito = findViewById(R.id.botonEliminarHabito);
        databaseHelper = new DatabaseHelper(this);

        // Cargar los hábitos de sueño en el Spinner
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
        // Obtener todos los hábitos de sueño desde la base de datos
        Cursor cursor = databaseHelper.getAllSleepHabits();

        // Verificar si el cursor tiene datos
        if (cursor != null && cursor.getCount() > 0) {
            // Inicializar el array habitNames con el número de hábitos
            habitNames = new String[cursor.getCount()];

            int i = 0;
            while (cursor.moveToNext()) {
                // Comprobación si la columna 'nombre_habito' existe
                int columnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SLEEP_NAME);
                if (columnIndex != -1) {
                    habitNames[i] = cursor.getString(columnIndex);
                } else {
                    // Si no encuentra la columna, manejar el error
                    habitNames[i] = "Columna no encontrada";
                }
                i++;
            }
            cursor.close(); // Cerrar el cursor después de usarlo

            // Configurar el adaptador del Spinner con los nombres de los hábitos
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, habitNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerEliminarHabito.setAdapter(adapter); // Asignar el adaptador al Spinner
        } else {
            // Si no hay hábitos, mostrar un mensaje
            Toast.makeText(this, "No hay hábitos de sueño registrados", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean deleteHabit(String habitName) {
        // Eliminar el hábito de sueño de la base de datos
        return databaseHelper.deleteSleepHabit(habitName);
    }
}