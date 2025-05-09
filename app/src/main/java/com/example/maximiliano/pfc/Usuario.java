package com.example.maximiliano.pfc;

/**
 * Clase Usuario
 * Esta clase implementa los métodos constructores de instanciación de un Usuario
 */
public class Usuario {

    //Atributos de un usuario
    String nombre, usuario, pass, email;

    //Constructor
    public Usuario(String nombre, String email, String usuario, String pass) {
        this.setNombre(nombre);
        this.setUsuario(usuario);
        this.setPass(pass);
        this.setEmail(email);
    }

    //Constructor que será llamado para verificar usuario al iniciar sesión
    public Usuario(String usuario, String pass) {
        this.setUsuario(usuario);
        this.setPass(pass);
    }

    public Usuario() {
        this.setNombre("");
        this.setUsuario("");
        this.setPass("");
        this.setEmail("");
    }

    //Métodos seters
    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private void setPass(String pass) {
        this.pass = pass;
    }

    //Métodos geters
    public String getNombre() {
        return this.nombre;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getPass() {
        return this.pass;
    }

    public String getEmail() {
        return this.email;
    }

}