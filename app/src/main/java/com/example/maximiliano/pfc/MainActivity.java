package com.example.maximiliano.pfc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import android.content.*;

/**
 * Actividad Principal
 * @author Maximiliano
 * Esta actividad representará la interfaz principal del usuario SOCIO
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Definimos controles
    Button bCerrarSesion;
    UsuarioDatosLocales usuarioDatosLocales;

    /**
     * Método onCreate
     * En este método casteamos los controles y seteamos al botón cerrar sesion en espera de clicks
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Casteo
        bCerrarSesion = (Button) findViewById(R.id.bCerrarSesion);
        //En espera de clicks
        bCerrarSesion.setOnClickListener(this);
        usuarioDatosLocales = new UsuarioDatosLocales(this);
    }

    /**
     * Método onStart
     */
    protected void onStart() {
        super.onStart();
        //Si el usuario esta autenticado
        if (this.autenticado()) {
            //Se muestran sus datos
            this.mostrarDatosUsuario();
        }
        //Caso contrario
        else {
            //Se muestra la pantalla de iniciar sesión (iniciamos actividad)
            startActivity(new Intent(MainActivity.this, IniciarSesion.class));
        }
    }

    private void mostrarDatosUsuario() {


    }

    private boolean autenticado() {
        return usuarioDatosLocales.obtenerUsuarioLogueado();
    }

    public void onClick(View v) {
        //Dependiendo que botón se haya presionado...
        switch(v.getId()) {
            //Si se presiona el botón cerrar sesión
            case R.id.bCerrarSesion:
                //Limpiamos datos del usuario
                usuarioDatosLocales.limpiarDatosUsuario();
                usuarioDatosLocales.logueo(false);
                break;
        }
    }
}