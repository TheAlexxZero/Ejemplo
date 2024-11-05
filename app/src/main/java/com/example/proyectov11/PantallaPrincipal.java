package com.example.proyectov11;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PantallaPrincipal extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter; // Asegúrate de tener una clase adaptadora

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla);

        // Inicializa el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewHabitos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar hábitos desde la base de datos
        loadHabits();

        // Configurar el botón "Agregar Hábito"
        Button botonAgregar = findViewById(R.id.botonAgregar);
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, AgregarHabitos.class);
                startActivity(intent);
            }
        });

        // Configurar el botón "eliminar Hábito"
        Button botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, EliminarHabitos.class);
                startActivity(intent);
            }
        });

        //configurar el boton de la ventana flotante
        FloatingActionButton fabVerVideo = findViewById(R.id.fab_ver_video);
        fabVerVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaPrincipal.this, videoActivity.class);
                startActivity(intent);
            }
        });

        //configurar el boton editar
        Button botonEditar = findViewById(R.id.botoneditar);
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, EditarHabito.class);
                startActivity(intent);
            }
        });
    }

    private void loadHabits() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getAllHabits();

        // Configura el adaptador con el cursor
        habitAdapter = new HabitAdapter(this, cursor);
        recyclerView.setAdapter(habitAdapter);
    }
}
