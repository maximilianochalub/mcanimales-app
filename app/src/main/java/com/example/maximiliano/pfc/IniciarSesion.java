package com.example.maximiliano.pfc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.app.AlertDialog;

/**
 * Clase "IniciarSesion"
 * @author Maximiliano
 * Esta clase
 */
public class IniciarSesion extends AppCompatActivity implements View.OnClickListener {

    //Declaramos controles utilizados en esta interfaz
    Button bIniciarSesion, bRegistro;
    EditText etUsuario, etPass;
    UsuarioDatosLocales usuarioDatosLocales;
    public String jos;

    /**
     * Método onCreate. Método ejecutado a la hora de iniciar esta actividad
     * @param savedInstanceState
     * Castea los controles utilizados y pone a los controles de botones
     * "iniciar sesión" y "registrarse" en modo de espera de clicks
     * Además crea una instancia de la clase "UsuarioDatosLocales"
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        //Casteo
        bIniciarSesion = (Button) findViewById(R.id.bIniciarSesion);
        bRegistro = (Button) findViewById(R.id.bRegistro);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etPass = (EditText) findViewById(R.id.etPass);
        //En escucha de clicks
        bIniciarSesion.setOnClickListener(this);
        bRegistro.setOnClickListener(this);
        //Instanciamos
        usuarioDatosLocales = new UsuarioDatosLocales(this);
    }

    /**
     * Método "onClick"
     * Este método se ejecutará cuando se haga un click sobre algunos de los controles seteados en clickListener
     * Ejecutará lo que deba hacerse cuando alguno de dichos botones sean presionados
     * @param v
     */
    public void onClick(View v) {
        //Obtenemos el id del boton
        switch(v.getId()) {
            //Si es el de iniciar sesión
            case R.id.bIniciarSesion:
                //Obtenemos nombre de usuario y contraseña ingresados
                String nombreUsuario = etUsuario.getText().toString();
                String password = etPass.getText().toString();
                //mensajeusu(nombreUsuario);
                //mensajepass(password);

                boolean camposNoVacios = false;

                if (!(camposNoVacios(nombreUsuario, password, camposNoVacios))) {
                    //Verificación campos no vacíos
                    camposNoVacios(nombreUsuario, password, camposNoVacios);
                }
                else {
                    //Creamos una instancia de usuario
                    Usuario usuario = new Usuario(nombreUsuario, password);
                    //Autenticamos el usuario y contraseña llamando al método "autenticar"
                    this.autenticar(usuario);
                }

                break;
            //Si es el de registrarse
            case R.id.bRegistro:
                //Iniciamos la actividad de registro
                startActivity(new Intent(this, Registrarse.class));
                break;
        }
    }

    private boolean camposNoVacios(String nombreUsuario, String password, boolean camposNoVacios) {

            //Primero verificamos que los campos esten completos
            if ((nombreUsuario.equals("")) || (password.equals(""))) {
                mensajeError("Complete todos los campos antes de continuar!");
            } else {
                camposNoVacios = true;
            }

        return camposNoVacios;
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
     * Método "autenticar"
     * Este método se ejecuta cuando se quiere verificar si el usuario existe o no
     * @param usuario
     */
    public void autenticar(Usuario usuario) {
        //Creamos una instancia de la clase SolicitudesServidor para realizar una solicitud
        SolicitudesServidor ss = new SolicitudesServidor(this);
        //Capturamos los datos en segundo plano y verificamos si el usuario existe
        ss.capturarDatUsuarioSegPlano(usuario, new UsuarioCallback() {
            @Override
            public void hecho(Usuario usuarioRetorno) {
                //Si retorna null es porque el usuario no existe
                if (usuarioRetorno == null) {
                    //Envia un mensaje de error de logueo
                    mensajeError(jos);
                }
                //Sino el usuario existe
                else {
                    mensajeIngresoCorrectamente();
                    ingresoCorrecto(usuarioRetorno);
                }
            }
        });
    }

    /**
     * Método "mensajeError"
     * Este método se encarga de mostrrar un mensaje de error al usuario
     */
    public void mensajeError(String mensaje) {
        //Establecemos cuadro de dialogo
        AlertDialog.Builder dialogoError = new AlertDialog.Builder(IniciarSesion.this);
        //Establecemos mensaje
        dialogoError.setMessage(mensaje);
        //Establecemos botón
        dialogoError.setPositiveButton("Ok", null);
        //Mostramos
        dialogoError.show();
    }

    private void mensajeusu(String us) {
        //Establecemos cuadro de dialogo
        AlertDialog.Builder dialogoError = new AlertDialog.Builder(IniciarSesion.this);
        //Establecemos mensaje
        dialogoError.setMessage("usuario: " + us);
        //Establecemos botón
        dialogoError.setPositiveButton("OK!!", null);
        //Mostramos
        dialogoError.show();
    }

    private void mensajepass(String ps) {
        //Establecemos cuadro de dialogo
        AlertDialog.Builder dialogoError = new AlertDialog.Builder(IniciarSesion.this);
        //Establecemos mensaje
        dialogoError.setMessage("pass: " + ps);
        //Establecemos botón
        dialogoError.setPositiveButton("OK!!", null);
        //Mostramos
        dialogoError.show();
    }

    private void mensajeIngresoCorrectamente() {
        //Establecemos cuadro de dialogo
        AlertDialog.Builder dialogoError = new AlertDialog.Builder(IniciarSesion.this);
        //Establecemos mensaje
        dialogoError.setMessage("Es usuario! Ingresó correctamente!");
        //Establecemos botón
        dialogoError.setPositiveButton("Ok", null);
        //Mostramos
        dialogoError.show();
    }

    private void mensajeIngresoErroneo() {
        //Establecemos cuadro de dialogo
        AlertDialog.Builder dialogoError = new AlertDialog.Builder(IniciarSesion.this);
        //Establecemos mensaje
        dialogoError.setMessage("NO es usuario! Ingreso erroneo!");
        //Establecemos botón
        dialogoError.setPositiveButton("Ok", null);
        //Mostramos
        dialogoError.show();
    }

    /**
     * Método "ingresoCorrecto"
     * Este método se ejecuta cuando el ingreso usuario contraseña haya sido correcto
     * @param usuarioRetorno
     */
    private void ingresoCorrecto(Usuario usuarioRetorno) {
        //Instanciamos un objeto de esta clase para obtener el tipo de usuario logueado
        SolicitudesServidor ss = new SolicitudesServidor(this);
        //Guardamos datos de usuario de forma local
        usuarioDatosLocales.guardarDatosLocales(usuarioRetorno);
        usuarioDatosLocales.logueo(true);
        //Iniciamos actividad según tipo de usuario
        switch(ss.obtenerTipoUsuario()) {
            //Usuario administrador
            case 1:
                startActivity(new Intent(this, MainActivity.class));
                break;
            //Usuario socio
            case 2:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    public int obtenerTipoUsuario(int tipoUsuario) {

        return tipoUsuario;
    }




}