package com.stk.a15830app2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class Transicion extends AppCompatActivity {
    private static final int DELAY_MILLIS = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transicion);

        // Crear un Handler para el retardo
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Crear el Intent para ir a Login
            Intent intent = new Intent(Transicion.this, Login.class);
            startActivity(intent);
            
            // Finalizar esta actividad
            finish();
        }, DELAY_MILLIS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar cualquier referencia pendiente
        getWindow().getDecorView().post(() -> {
            if (!isFinishing()) {
                finish();
            }
        });
    }
}