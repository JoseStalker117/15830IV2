package com.stk.a15830app2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

public class Register extends AppCompatActivity {
    private SQLite db;
    private TextInputEditText etNombre, etCorreo, etTelefono, etUsuario, etContrasena, etConfirmarContrasena;
    private MaterialButton btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar la base de datos
        db = new SQLite(this);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Configurar botón de registro
        btnRegistrar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String usuario = etUsuario.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();
            String confirmarContrasena = etConfirmarContrasena.getText().toString().trim();

            // Validar campos vacíos
            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || 
                usuario.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                Toast.makeText(Register.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar que las contraseñas coincidan
            if (!contrasena.equals(confirmarContrasena)) {
                Toast.makeText(Register.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            // Intentar registrar usuario
            if (db.registrarUsuario(nombre, correo, telefono, usuario, contrasena)) {
                Toast.makeText(Register.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                // Ir a la pantalla de login
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Register.this, "Error al registrar. El usuario o correo ya existe", Toast.LENGTH_SHORT).show();
            }
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