package com.example.maximiliano.pfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PantallaLauncher extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_launcher);
        //Iniciamos la actividad de Iniciar Sesión
        startActivity(new Intent(this, IniciarSesion.class));
    }
}
