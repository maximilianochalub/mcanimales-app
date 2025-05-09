package com.example.maximiliano.pfc;

import android.content.*;

public class UsuarioDatosLocales {

    public static final String SP_NAME = "detallesUsuario";

    //Preferencias compartidas nos permiten almacenar datos locales en nuestro dispositivo
    SharedPreferences bdUsuarioLocal;

    //Definimos constructor
    public UsuarioDatosLocales(Context contexto) {
        bdUsuarioLocal = contexto.getSharedPreferences(SP_NAME, 0);
    }

    public UsuarioDatosLocales() {

    }

    //Creamos un método para obtener el usuario que se encuentra logueado
    public Usuario usuarioLogueado() {
        String nombre = bdUsuarioLocal.getString("nombre", "");
        String email = bdUsuarioLocal.getString("email", "");
        String usuario = bdUsuarioLocal.getString("usuario", "");
        String pass = bdUsuarioLocal.getString("pass", "");

        Usuario usuarioLogueado = new Usuario(nombre, email, usuario, pass);

        return usuarioLogueado;
    }

    //Definimos métodos para obtener datos de usuario de la base de datos local de usuario

    public void guardarDatosLocales(Usuario usuario) {
        SharedPreferences.Editor spEditor = bdUsuarioLocal.edit();
        spEditor.putString("nombre", usuario.getNombre());
        spEditor.putString("email", usuario.getEmail());
        spEditor.putString("usuario", usuario.getUsuario());
        spEditor.putString("pass", usuario.getPass());
        spEditor.commit();
    }


    //Creamos un método que retornará true si el usuario está logueado
    //retornara false en caso contrario

    public void logueo(boolean estaLogueado) {
        SharedPreferences.Editor spEditor = bdUsuarioLocal.edit();
        //spEditor.putBoolean("logueado", usuarioLogueado.getNombre());
        spEditor.commit();
    }

    public void limpiarDatosUsuario() {
        SharedPreferences.Editor spEditor = bdUsuarioLocal.edit();
        spEditor.clear();
        spEditor.commit();
    }
    public boolean obtenerUsuarioLogueado() {
        if (bdUsuarioLocal.getBoolean("logueado", false)) {
            return true; }
        else{
            return false;
        }
    }

}








