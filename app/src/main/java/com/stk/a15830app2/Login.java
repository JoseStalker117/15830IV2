package com.stk.a15830app2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private SQLite db;
    private TextInputEditText etUsuario, etContrasena;
    private MaterialButton btnLogin;
    private TextView tvRecuperarContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar la base de datos
        db = new SQLite(this);

        // Inicializar vistas
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        tvRecuperarContrasena = findViewById(R.id.tvRecuperarContrasena);

        // Configurar botón de login
        btnLogin.setOnClickListener(v -> {
            String username = etUsuario.getText().toString().trim();
            String password = etContrasena.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.logearUsuario(username, password)) {
                // Login exitoso
                Intent intent = new Intent(Login.this, Aplicacion.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar navegación a recuperar contraseña
        tvRecuperarContrasena.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Recover.class);
            startActivity(intent);
        });

        // Configurar navegación a registro
        TextView tvRegistrarse = findViewById(R.id.tvRegistrarse);
        if (tvRegistrarse != null) {
            tvRegistrarse.setOnClickListener(v -> {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }
}