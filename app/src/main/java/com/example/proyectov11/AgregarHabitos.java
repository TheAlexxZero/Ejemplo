package com.example.proyectov11;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AgregarHabitos extends AppCompatActivity {

    private EditText editTextNombreHabito, editTextTipoEspecifico, editTextHoraDormir, editTextHoraDespertar;
    private Spinner spinnerFrecuencia, spinnerTipoHabito;
    private RadioGroup radioGroupPlazo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_habitos);

        // Inicialización de los campos
        editTextNombreHabito = findViewById(R.id.editTextNombreHabito);
        editTextTipoEspecifico = findViewById(R.id.editTextTipoEspecifico);
        editTextHoraDormir = findViewById(R.id.editTextHoraDormir);
        editTextHoraDespertar = findViewById(R.id.editTextHoraDespertar);
        spinnerFrecuencia = findViewById(R.id.spinnerFrecuencia);
        spinnerTipoHabito = findViewById(R.id.spinnerTipoHabito);
        radioGroupPlazo = findViewById(R.id.radioGroupPlazo);
        Button botonGuardarHabito = findViewById(R.id.botonGuardarHabito);

        // Configura los adaptadores para los Spinners
        ArrayAdapter<CharSequence> adapterFrecuencia = ArrayAdapter.createFromResource(this,
                R.array.frecuencias_array, android.R.layout.simple_spinner_item);
        adapterFrecuencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrecuencia.setAdapter(adapterFrecuencia);

        ArrayAdapter<CharSequence> adapterTipoHabito = ArrayAdapter.createFromResource(this,
                R.array.tipos_habito_array, android.R.layout.simple_spinner_item);
        adapterTipoHabito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoHabito.setAdapter(adapterTipoHabito);

        // Manejo del botón de guardar
        botonGuardarHabito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarHabito();
            }
        });
    }

    private void guardarHabito() {
        String nombreHabito = editTextNombreHabito.getText().toString();
        String horaDormir = editTextHoraDormir.getText().toString();
        String horaDespertar = editTextHoraDespertar.getText().toString();
        String frecuencia = spinnerFrecuencia.getSelectedItem().toString();
        String tipo = spinnerTipoHabito.getSelectedItem().toString();
        String tipoEspecifico = editTextTipoEspecifico.getText().toString();
        String plazo = ((RadioButton) findViewById(radioGroupPlazo.getCheckedRadioButtonId())).getText().toString();

        // Llama al método de base de datos para guardar el hábito de sueño
        DatabaseHelper db = new DatabaseHelper(this);
        boolean resultado = db.addSleepHabit(nombreHabito, horaDormir, horaDespertar, frecuencia, tipo, tipoEspecifico, plazo);

        if (resultado) {
            Toast.makeText(this, "Hábito de sueño guardado exitosamente", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad después de guardar
        } else {
            Toast.makeText(this, "Error al guardar el hábito de sueño", Toast.LENGTH_SHORT).show();
        }
    }
}
