package com.example.maximiliano.pfc;

/**
 * Created by Maximiliano on 02/10/2015.
 */

import android.app.*;
import android.content.*;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.*;
import com.google.gson.*;

/**
 * Clase SolicitudesServidor
 * Esta clase implementa los métodos necesarios para realizar cualquier tipo de solcitud al servidor
 * donde se alojará la base de datos de la app
 */

public class SolicitudesServidor {

    //Creamos un cuadro de dialogo que aparecerá mientras se carga una transacción
    ProgressDialog pd;

    //Creamos una constante tiempo de conexión en ms
    public static final int TIEMPO_CONEXION = 1000 * 15;
    //Creamos una constante para poder acceder a los archivos php del servidor
    //public static final String DIRECCION_SERVIDOR = "server46.000webhost.com/";
    public static final String DIRECCION_SERVIDOR = "http://maxxc.uphero.com/";

    public static int tipoUsuario = 0;

    //Definimos el constructor
    public SolicitudesServidor(Context contexto) {

        //Instanciamos el ProgressDialog
        pd = new ProgressDialog(contexto);
        //Que el usuario no pueda cancelar un ProgressDialog
        pd.setCancelable(false);
        //Establecemos titulo y mensaje del ProgressDialog
        pd.setTitle("Procesando");
        pd.setMessage("Espere un momento...");
    }

    public int obtenerTipoUsuario() {
        return this.tipoUsuario;
    }

    /**
     * Método "guardarDatUsuarioSegPlano" ==> EJECUTA TAREA ASINCRONA "guardarDatUsuarioSegPlanoAt"
     * Se lo llamará cuando un usuario se registre
     * Este método almacenará los datos del usuario en segundo plano
     *
     * @param usuario
     * @param callback
     */
    public void guardarDatUsuarioSegPlano(Usuario usuario, UsuarioCallback callback) {
        //Mostramos mensaje de espera
        pd.show();
        //Se llama a la clase para ejecutar la tarea en segundo plano, indicando su ejecución
        new guardarDatUsuarioSegPlanoAt(usuario, callback).execute();
    }

    /**
     * Método "capturarDatosUsuarioSegPlano" ==> EJECUTA TAREA ASÍNCRONA "capturarDatosUsuarioSegPlanoAt"
     * Se lo llamará cuando un usuario inicie sesión
     * Capturará los datos del usuario en segundo plano
     *
     * @param usuario
     * @param callback
     */
    public void capturarDatUsuarioSegPlano(Usuario usuario, UsuarioCallback callback) {
        //Mostramos mensaje de espera
        pd.show();
        //Se llama a la clase para ejecutar la tarea en segundo plano, indicando su ejecución
        new capturarDatUsuarioSegPlanoAt(usuario, callback).execute();
    }

    /**
     * SUBCLASE guardarDatUsuarioSegPlanoAt
     * Esta subclase implementará una tarea asíncrona (segundo plano)
     * Se llamará cuando el usuario se REGISTRE desde guardarDtUsuarioSegPlano
     */
    public class guardarDatUsuarioSegPlanoAt extends AsyncTask<Void, Void, Void> {

        //Atributos
        Usuario usuario;
        UsuarioCallback callback;

        //Creamos constructor
        public guardarDatUsuarioSegPlanoAt(Usuario usuario, UsuarioCallback callback) {
            this.usuario = usuario;
            this.callback = callback;
        }

        /**
         * Método "doInBackGround"
         * Incluira todo lo que se ejecutará en segundo plano
         * Se ejecutará en otro hilo (no en el principal)
         *
         * @param p
         * @return
         */
        protected Void doInBackground(Void... p) {
            //Almacenamos los datos a enviar al servidor en un ArrayList
            ArrayList<NameValuePair> datosEnviar = new ArrayList<>();
            //Cargamos los datos
            datosEnviar.add(new BasicNameValuePair("nombre", this.usuario.nombre));
            datosEnviar.add(new BasicNameValuePair("email", this.usuario.email));
            datosEnviar.add(new BasicNameValuePair("usuario", this.usuario.usuario));
            datosEnviar.add(new BasicNameValuePair("pass", this.usuario.pass));

            //Configuramos servidor http
            //Instanciamos objeto que almacenará los parámetros de solicitud http
            HttpParams ParamSolicitudHttp = new BasicHttpParams();
            //Seteamos tiempo límite de conexión al servidor
            HttpConnectionParams.setConnectionTimeout(ParamSolicitudHttp, TIEMPO_CONEXION);
            //Seteamos tiempo límite de respuesta desde el servidor a cliente
            HttpConnectionParams.setSoTimeout(ParamSolicitudHttp, TIEMPO_CONEXION);
            //Instanciamos un cliente http y le pasamos los parámetros de solicitud
            HttpClient clienteHttp = new DefaultHttpClient(ParamSolicitudHttp);
            //Instanciamos objeto que almacenará los DATOS a enviar al servidor
            //Le pasamos la dirección del servidor y el script php de registro
            HttpPost post = new HttpPost(DIRECCION_SERVIDOR + "Registrarse.php");
            try {
                //????????????????????
                post.setEntity(new UrlEncodedFormEntity(datosEnviar));
                //El cliente ejecuta el post
                clienteHttp.execute(post);
            } catch (Exception e) {
                //Imprimimos error si existe
                e.printStackTrace();
            }
            return null;
        } //doInBackground
        //Termina doInBackground() y se ejecuta onPostExecute()

        /**
         * Método "onPostExecute"
         * Se ejecutará cuando finalice la tarea asíncrona
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            //Detenemos el ProgressDialog
            pd.dismiss();
            //Informamos que la tarea segundo plano se ha realizado y finalizado, llamando al método "hecho"
            this.callback.hecho(null);
            super.onPostExecute(aVoid);
        }
    }

    /**
     * SUBCLASE capturarDatUsuarioSegPlanoAt
     * Esta subclase implementará una tarea asíncrona (segundo plano)
     * Se llamará cuando el usuario INICIE SESION desde capturaDatUsuarioSegPlano
     */
    public class capturarDatUsuarioSegPlanoAt extends AsyncTask<Void, Void, Usuario> {
        Usuario usuario;
        UsuarioCallback callback;


        //Definimos constructor
        public capturarDatUsuarioSegPlanoAt(Usuario usuario, UsuarioCallback callback) {
            this.usuario = usuario;
            this.callback = callback;
        }

        @Override
        protected Usuario doInBackground(Void... params) {
            //Almacenamos los datos a enviar al servidor en un ArrayList
            ArrayList<NameValuePair> datosEnviar = new ArrayList<>();
            //Cargamos los datos
            datosEnviar.add(new BasicNameValuePair("usuario", this.usuario.usuario));
            datosEnviar.add(new BasicNameValuePair("pass", this.usuario.pass));

            //Configuramos servidor http
            //Instanciamos objeto que almacenará los parámetros de solicitud http
            HttpParams ParamSolicitudHttp = new BasicHttpParams();
            //Seteamos tiempo límite de conexión al servidor
            HttpConnectionParams.setConnectionTimeout(ParamSolicitudHttp, TIEMPO_CONEXION);
            //Seteamos tiempo límite de respuesta desde el servidor a cliente
            HttpConnectionParams.setSoTimeout(ParamSolicitudHttp, TIEMPO_CONEXION);
            //Instanciamos un cliente http y le pasamos los parámetros de solicitud
            HttpClient clienteHttp = new DefaultHttpClient(ParamSolicitudHttp);
            //Instanciamos objeto que almacenará los DATOS a enviar al servidor
            //Le pasamos la dirección del servidor y el script php de registro
            HttpPost post = new HttpPost(DIRECCION_SERVIDOR + "Capturar.php");

            //Definimos un usuario
            Usuario usuarioRetorno = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(datosEnviar));
                //Instanciamos un objeto para la respuesta del servidor
                HttpResponse respuestaHttp = clienteHttp.execute(post);
                //Instanciamos una entidad de respuesta
                HttpEntity entidad = respuestaHttp.getEntity();
                //Almacenamos dicha respuesta en un string
                String resultado = EntityUtils.toString(entidad);
                //Definimos un objeto JSON y le pasamos dicho string
                JSONObject jo = new JSONObject(resultado);

                IniciarSesion is = new IniciarSesion();

                is.jos = "hello!!!!!";

                //Si la longitud del objeto es vacia
                if (jo.length() == 0) {
                    usuarioRetorno = null;
                } else {
                    //Obtenemos nombre y email de usuario
                    String nombre = jo.getString("nombre");
                    String email = jo.getString("email");
                    //Almacenamos en var el id tipo usuario
                    //tipoUsuario = jo.getInt("tipo_usuario");
                    //Definimos el nuevo usuario
                    usuarioRetorno = new Usuario(nombre, email, usuario.usuario, usuario.pass);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Devolvemos el usuario
            return usuarioRetorno;
            //Termina doInBackground() y se ejecuta onPostExecute()

        }

        /**
         * Método "onPostExecute"
         * Se ejecutará cuando finalice la tarea asíncrona (en segundo plano - doInBackground)
         *
         * @param
         */
        protected void onPostExecute(Usuario usuarioRetorno) {
            //Detenemos el ProgressDialog
            pd.dismiss();


            //Informamos que la tarea segundo plano se ha realizado y finalizado, llamando al método "hecho"
            this.callback.hecho(usuarioRetorno);
            super.onPostExecute(usuarioRetorno);
        }
    }
}



/**


        public class obtenerUsuario extends AsyncTask<String, String, String> {

            protected String doInBackground(String... params) {


                return "hey";
            }

            private String mostrar() {
                HttpClient clienteHttp = new DefaultHttpClient();
                HttpPost post = new HttpPost(DIRECCION_SERVIDOR + "CapturarDatosUsuario2.php");
                String resultado = "";
                HttpResponse rptaHttp;

                try {
                    rptaHttp = clienteHttp.execute(post);
                    HttpEntity entidad = rptaHttp.getEntity();
                    InputStream instream = entidad.getContent();
                    resultado = convertStringToStream(instream);
                }
                catch (ClientProtocolException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                return resultado;

            } //mostrar

            private String convertStringToStream(InputStream is) throws IOException {
                if (is != null) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                    }
                    finally { is.close(); }
                    return sb.toString();
                }
                else { return ""; }
            } //convertStringToStream

            private boolean obtenerDatos() {
                String datos = mostrar();
                if (!datos.equalsIgnoreCase("")) {
                    try {
                        JSONObject json = new JSONObject(datos);
                        JSONArray jsona = new json.optJSONArray("");
                        for (int i = 0; i < jsona.length(); i++) {
                            JSONObject _json = jsona.getJSONObject(i);
                            String usuario = _json.optString("usuario");
                            String pass = _json.optString("pass");
                            int tipo = _json.optInt("tipo_id");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }

     }
}

*/



