package com.stk.a15830app2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class Aplicacion extends AppCompatActivity {
    private SQLite db;
    private TextView userName, userEmail;
    private MaterialButton btnLogout, btnNuevaActividad, btnVerPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicacion);

        // Inicializar la base de datos
        db = new SQLite(this);

        // Inicializar vistas
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        btnLogout = findViewById(R.id.btnLogout);
        btnNuevaActividad = findViewById(R.id.btnNuevaActividad);
        btnVerPerfil = findViewById(R.id.btnVerPerfil);

        // Obtener el nombre de usuario del Intent
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            // Obtener información del usuario desde la base de datos
            SQLite.UserInfo userInfo = db.obtenerInfoUsuario(username);
            if (userInfo != null) {
                userName.setText("Bienvenido, " + userInfo.getName());
                userEmail.setText(userInfo.getEmail());
            } else {
                // Si no se encuentra la información, usar el username como respaldo
                userName.setText("Bienvenido, " + username);
                userEmail.setText(username + "@email.com");
            }
        }

        // Configurar botón de cerrar sesión
        btnLogout.setOnClickListener(v -> {
            // Cerrar sesión y volver a la pantalla de login
            Intent intent = new Intent(Aplicacion.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Configurar botón de nueva actividad
        btnNuevaActividad.setOnClickListener(v -> {
            Toast.makeText(Aplicacion.this, "Función en desarrollo", Toast.LENGTH_SHORT).show();
        });

        // Configurar botón de ver perfil
        btnVerPerfil.setOnClickListener(v -> {
            Toast.makeText(Aplicacion.this, "Función en desarrollo", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }
}