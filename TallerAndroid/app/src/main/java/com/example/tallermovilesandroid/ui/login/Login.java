package com.example.tallermovilesandroid.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallermovilesandroid.MainActivity;
import com.example.tallermovilesandroid.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button loginMocky;
    Button loginGoogle;
    EditText usuario;
    EditText clave;
    String usr;
    String correo;
    GoogleSignInAccount account;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean red = conexion();               // conexión
        if(red == true){
            usuario = (EditText) findViewById(R.id.usuario);
            clave = (EditText) findViewById(R.id.clave);
            loginMocky = (Button) findViewById(R.id.login);
            loginMocky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginMocky();
                }
            });
            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            findViewById(R.id.google).setOnClickListener(this);
        }
    }

    public void loginMocky(){
        Conexion conexion = new Conexion();
        String respuesta;
        try {
            List<String> con = new ArrayList<String>();
            respuesta = conexion.execute("http://www.mocky.io/v2/5ec6fd662f00006900426f31").get();
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray lista = jsonObject.optJSONArray("login");

            for (int i = 0; i < lista.length(); i++) {
                JSONObject json_data = lista.getJSONObject(i);
                if (usuario.getText().toString().equals("") && clave.getText().toString().equals("")) {
                    //System.out.println("error, campos vacios");
                    Toast.makeText(getApplicationContext(), "Error, campos vacios", Toast.LENGTH_LONG).show();
                }else{
                    if ((usuario.getText().toString().equals(json_data.getString("usuario"))) &&
                            (clave.getText().toString().equals(json_data.getString("clave")))) {
                        //System.out.println("soy un usuario, entre");
                        usr = json_data.getString("usuario");
                        correo = json_data.getString("correo");
                        goToMenu("mocky");
                        break;
                    } else {
                        //System.out.println("usuario no registrado");
                        Toast.makeText(getApplicationContext(), "Usuario no registrado", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //goToMenu();
    }


    public void showMessage(View obj){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Hola Boton positivo", Toast.LENGTH_LONG).show();
            }
        });
        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setMessage(usr);
        dialog.show();
    }


    public void goToMenu(String tipo){
        Intent ir = new Intent(this, MainActivity.class);
        if(tipo.equals("mocky")){
            Bundle data = new Bundle();
            data.putString("nombre",usr);
            data.putString("correo", correo);
            data.putString("foto",  "https://maslinux.es/wp-content/uploads/2018/03/router-keygen-para-android.jpg");

            ir.putExtras(data);
            startActivity(ir);
        }else{
            Bundle data = new Bundle();
            data.putString("foto",  account.getPhotoUrl().toString());
            data.putString("nombre",account.getDisplayName());
            data.putString("correo", account.getEmail());
            ir.putExtras(data);
            startActivity(ir);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            goToMenu("gulugulu");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
    }

    // ------------------------------------------ aqui se se realiza lo de la conexión ---------------------------------------------------------------
    public boolean conexion(){
        boolean con = false;

        ConnectivityManager connectivityManager= (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn=false;
        boolean isMobileConn=false;

        for(Network network: connectivityManager.getAllNetworks()){
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo.getType()== ConnectivityManager.TYPE_WIFI){
                isWifiConn |= networkInfo.isConnected();
            }
            if(networkInfo.getType()== ConnectivityManager.TYPE_MOBILE){
                isMobileConn |= networkInfo.isConnected();
            }
        }
        //System.out.println("ya verifico");
        if(isWifiConn){
            //System.out.println("wifi conectado  ");
            con=true;
            try {
                Toast.makeText(this, "Conexión Wi-Fi", Toast.LENGTH_LONG).show();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if(isMobileConn){
            //System.out.println("datos conectado   ");
            con=true;
            try {
                Toast.makeText(this, "Conexión Movil Datos", Toast.LENGTH_LONG).show();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            con=false;
            Toast.makeText(this, "Sin Conexión a la Red", Toast.LENGTH_LONG).show();
        }
        return con;
    }

}
