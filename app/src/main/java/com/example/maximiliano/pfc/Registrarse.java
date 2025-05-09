package com.example.maximiliano.pfc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import android.content.*;

/**
 * Actividad "Registrarse"
 * Esta clase realiza lo necesario para permitir al usuario registrarse
 * @author Maximiliano
 */
public class Registrarse extends AppCompatActivity implements View.OnClickListener {

    //Declaramos controles utilizados en esta interfaz
    EditText etNombre, etEmail, etUsuario, etPass;
    Button bRegistro;

    /**
     * Método onCreate. Método ejecutado a la hora de iniciar esta actividad
     * @param savedInstanceState
     * Castea los controles utilizados y pone al control del botón
     * "registrarse" en modo de espera de clicks
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        //Casteo controles
        etNombre = (EditText) findViewById(R.id.etNombre);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etPass= (EditText) findViewById(R.id.etPass);
        bRegistro = (Button) findViewById(R.id.bRegistro);
        //En espera de clicks
        bRegistro.setOnClickListener(this);
    }

    /**
     * Método "onClick"
     * Este método se ejecutará cuando se haga un click sobre algunos de los controles seteados en clickListener
     * Ejecutará lo que deba hacerse cuando alguno de dichos botones sean presionados (registro)
     * @param v
     */
    public void onClick(View v) {
        //Dependiendo que botón se haya presionado...
        switch(v.getId()) {
            //Si se presionó el botón registro
            case R.id.bRegistro:
                //Capturamos los datos ingresados
                String nombre = etNombre.getText().toString();
                String email = etEmail.getText().toString();
                String usuario = etUsuario.getText().toString();
                String pass = etPass.getText().toString();

                boolean camposCorrectos = false;

                if (camposCorrectos(nombre, email, usuario, pass, camposCorrectos)) {
                    //Creamos una nueva instancia de usuario
                    Usuario nuevoUsuario = new Usuario(nombre, email, usuario, pass);
                    //Llamamos al método que registra usuario
                    mensajeError("Se ha registrado en el sistema con éxito!");
                    registrarUsuario(nuevoUsuario);
                }
                break;
        }
    }

    private boolean camposCorrectos(String nombre, String email, String nombreUsuario, String password, boolean camposCorrectos) {

            //Primero verificamos que todos los campos esten completos
            if ((nombre.equals("")) || (email.equals("")) || (nombreUsuario.equals("")) || (password.equals(""))) {
                mensajeError("Complete todos los campos antes de continuar!");
            } else {
                //Segundo verificamos que los campos tengan mayor de 5 caracteres
                if ((nombre.length() < 5) || (nombreUsuario.length() < 5) || (password.length() < 5)) {
                    mensajeError("Su nombre, usuario y contraseña deben contener 5 o más caracteres");
                } else {
                    //Tercero verificamos que solo se usen A-Z;a-z;0-9;_ en usuario y contraseña
                    if ((!(verificarCaracteres(nombreUsuario))) || (!(verificarCaracteres(password)))) {
                        mensajeError("Utilice solo letras, números o guión bajo para su usuario y contraseña");
                    } else {
                        //Cuarto verificamos que el nombre se usen solo A-Z;a-z;
                        if (!(verificarCaracteresSoloLetras(nombre))) {
                            mensajeError("Utilice solo letras para su nombre");
                        } else {
                            camposCorrectos = true;
                        }
                    }
                }
            }
        return camposCorrectos;
    }

    /**
     * Método "mensajeError"
     * Este método se encarga de mostrrar un mensaje de error al usuario
     */
    private void mensajeError(String mensaje) {
        //Establecemos cuadro de dialogo
        AlertDialog.Builder dialogoError = new AlertDialog.Builder(Registrarse.this);
        //Establecemos mensaje
        dialogoError.setMessage(mensaje);
        //Establecemos botón
        dialogoError.setPositiveButton("Ok", null);
        //Mostramos
        dialogoError.show();
    }

    /**
     * Método verificarCaracteres
     * Este método evalua que solo existan letras, números y guión bajo en los campos
     * @param cadena
     * @return
     */
    private boolean verificarCaracteres(String cadena) {
        //Variable que almacena cada caracter de la cadena
        char caracter;
        //Variable que almacena el código ASCII del caracter a evaluar
        int ascii_cod;
        //Variable que toma el valor false cuando al menos un caracter de la cadena no cumpla con lo válido
        boolean caracteresCorrectos = true;

        //Recorremos la cadena
        for (int i=0; i<cadena.length(); i++) {
            //Almacenamos el i-esimo caracter y obtenemos el código en ASCII
            caracter = cadena.charAt(i);
            ascii_cod = (int) caracter;

            //Evaluamos el caracter
            //Ascii: (97-122: a-z) - Letras minusculas
            if (!((ascii_cod >= 97) && (ascii_cod <= 122))) {
                //Ascii: (65-90: A-Z) - Letras mayusculas
                if (!((ascii_cod >= 65) && (ascii_cod <= 90))) {
                    //Ascii: (48-57 0-9) - Números
                    if (!((ascii_cod >= 48) && (ascii_cod <= 57))) {
                        //Ascii: (95 _) - Guión bajo
                        if (!(ascii_cod == 95)) {
                            caracteresCorrectos = false;
                        }
                    }
                }
            }
        }
        //Retornamos la variable
        return caracteresCorrectos;
    }

    /**
     * Método verificarCaracteres
     * Este método evalua que solo existan letras, números y guión bajo en los campos
     * @param cadena
     * @return
     */
    private boolean verificarCaracteresSoloLetras(String cadena) {
        //Variable que almacena cada caracter de la cadena
        char caracter;
        //Variable que almacena el código ASCII del caracter a evaluar
        int ascii_cod;
        //Variable que toma el valor false cuando al menos un caracter de la cadena no cumpla con lo válido
        boolean caracteresCorrectos = true;

        //Recorremos la cadena
        for (int i=0; i<cadena.length(); i++) {
            //Almacenamos el i-esimo caracter y obtenemos el código en ASCII
            caracter = cadena.charAt(i);
            ascii_cod = (int) caracter;

            //Evaluamos el caracter
            //Ascii: (97-122: a-z) - Letras minusculas
            if (!((ascii_cod >= 97) && (ascii_cod <= 122))) {
                //Ascii: (65-90: A-Z) - Letras mayusculas
                if (!((ascii_cod >= 65) && (ascii_cod <= 90))) {
                    caracteresCorrectos = false;
                }
            }
        }
        //Retornamos la variable
        return caracteresCorrectos;
    }

    /**
     * Método registrarUsuario
     * Este método se encarga de registrar el usuario
     * @param nuevoUsuario
     */
    private void registrarUsuario(Usuario nuevoUsuario) {
        //Instanciamos una solicitud al servidor, le pasamos el contexto
        SolicitudesServidor ss = new SolicitudesServidor(this);
        //Se guardan los datos del nuevo usuario en segundo plano
        ss.guardarDatUsuarioSegPlano(nuevoUsuario, new UsuarioCallback() {
            @Override
            public void hecho(Usuario usuarioRetorno) {
                startActivity(new Intent(Registrarse.this, IniciarSesion.class));
            }
        });
    }
} //class
