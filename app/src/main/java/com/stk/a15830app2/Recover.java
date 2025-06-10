package com.stk.a15830app2;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

public class Recover extends AppCompatActivity {
    private SQLite db;
    private TextInputEditText etEmail;
    private MaterialButton btnRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        // Inicializar la base de datos
        db = new SQLite(this);

        // Inicializar vistas
        etEmail = findViewById(R.id.emailInput);
        btnRecuperar = findViewById(R.id.recoverButton);

        // Configurar botón de recuperación
        btnRecuperar.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(Recover.this, "Por favor ingrese su correo electrónico o usuario", Toast.LENGTH_SHORT).show();
                return;
            }

            // Aquí normalmente se enviaría un correo electrónico con la nueva contraseña
            // Por ahora, solo mostraremos un mensaje
            Toast.makeText(Recover.this, 
                "Se ha enviado un correo con instrucciones para recuperar su contraseña", 
                Toast.LENGTH_LONG).show();
            
            // Volver a la pantalla de login
            finish();
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