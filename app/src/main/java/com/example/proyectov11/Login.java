package com.example.proyectov11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Importa Log para usarlo
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText etUsuario, etPassword;
    private Button btnLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Cambia esto por el nombre de tu layout de login

        // Inicializa los elementos de la UI
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Inicializa el DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Configura el evento click del botón de login
        btnLogin.setOnClickListener(v -> {
            String email = etUsuario.getText().toString(); // Usa email en lugar de username
            String password = etPassword.getText().toString();

            // Verifica si el usuario existe
            if (databaseHelper.checkUser(email, password)) { // Cambia a email
                // Inicia la actividad principal si el usuario existe
                Log.d("LoginActivity", "Usuario autenticado: " + email); // Agrega el log aquí
                Intent intent = new Intent(Login.this, PantallaPrincipal.class); // Cambia PantallaPrincipal por el nombre de tu clase de la actividad principal
                startActivity(intent);
                finish(); // Finaliza la actividad de login para que no se pueda volver atrás
            } else {
                // Muestra un mensaje de error si las credenciales son incorrectas
                Toast.makeText(Login.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
