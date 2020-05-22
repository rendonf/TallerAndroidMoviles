package com.example.tallermovilesandroid;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    String nombre="nada";
    String correo="nada";
    String foto;
    TextView editUsuario;
    TextView editcCorreo;
    TextView pUsuario;
    TextView pCorreo;
    ImageView fotoperfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_perfil, R.id.nav_autoevaluacion, R.id.nav_reportes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Bundle data = getIntent().getExtras();
        nombre = data.getString("nombre");
        correo = data.getString("correo");
        foto = data.getString("foto");
        System.out.println(nombre);
        System.out.println(correo);
        System.out.println(foto);





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        editcCorreo = (TextView) findViewById(R.id.nombre);
        editUsuario = (TextView) findViewById(R.id.correo);
        editUsuario.setText(nombre);
        editcCorreo.setText(correo);
        fotoperfil = (ImageView) findViewById(R.id.imagefoto);
        pCorreo = (TextView) findViewById(R.id.textViewcorreo);
        pUsuario = (TextView) findViewById(R.id.textViewnombre);
        Picasso.get()
                .load(foto)
                .into(fotoperfil);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
