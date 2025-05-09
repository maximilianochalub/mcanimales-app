package com.example.maximiliano.pfc;

/**
 * Interfaz UsuarioCallback
 * Created by Maximiliano on 02/10/2015.
 */
interface UsuarioCallback {

    //Método que se llamará cuando alguna solicitud del servidor se complete
    public abstract void hecho(Usuario usuarioRetorno);
}
