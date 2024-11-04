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

    private EditText editTextNombreHabito, editTextTipoEspecifico;
    private Spinner spinnerFrecuencia, spinnerTipoHabito;
    private RadioGroup radioGroupPlazo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_habitos);

        editTextNombreHabito = findViewById(R.id.editTextNombreHabito);
        editTextTipoEspecifico = findViewById(R.id.editTextTipoEspecifico);
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
        String frecuencia = spinnerFrecuencia.getSelectedItem().toString();
        String tipo = spinnerTipoHabito.getSelectedItem().toString();
        String tipoEspecifico = editTextTipoEspecifico.getText().toString();
        String plazo = ((RadioButton) findViewById(radioGroupPlazo.getCheckedRadioButtonId())).getText().toString();

        // Llama a tu método de base de datos para guardar el hábito
        DatabaseHelper db = new DatabaseHelper(this);
        boolean resultado = db.addHabit(nombreHabito, frecuencia, tipo, tipoEspecifico, plazo);

        if (resultado) {
            Toast.makeText(this, "Hábito guardado exitosamente", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad después de guardar
        } else {
            Toast.makeText(this, "Error al guardar el hábito", Toast.LENGTH_SHORT).show();
        }
    }
}
