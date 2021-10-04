package com.example.login_registro;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login_registro.ui.home.Parqueos;
import com.example.login_registro.ui.home.ParqueosAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_registro.databinding.ActivityNavigationBinding;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    SharedPreferences prf;
    DBHelper DB;
    GridView Grid;
    ArrayList<Parqueos> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarNavigation.toolbar);
        binding.appBarNavigation.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCustomDialog().show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        TextView username, email;
        prf = getSharedPreferences("usuariologueado",MODE_PRIVATE);
        username = (TextView) findViewById(R.id.nombreusuario);
        email = (TextView) findViewById(R.id.email);
        username.setText(prf.getString("username",null));
        DB = new DBHelper(this);
        String correo = DB.getemail(prf.getString("username",null));
        email.setText(correo);

        Grid = (GridView) findViewById(R.id.grid);
        ParqueosAdapter adapter = new ParqueosAdapter(this, listar());
        Grid.setAdapter(adapter);
        
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                prf = getSharedPreferences("usuariologueado",MODE_PRIVATE);
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(NavigationActivity.this, "Hasta luego", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public AlertDialog createCustomDialog() {
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.activity_registrarparqueos, null);
        //builder.setView(inflater.inflate(R.layout.dialog_signin, null))
        Button btnFire = (Button) v.findViewById(R.id.btn_fire);
        Button btnCancel = (Button) v.findViewById(R.id.btn_cancel);
        EditText matricula = (EditText) v.findViewById(R.id.matricula);
        EditText tiempo = (EditText) v.findViewById(R.id.tiempo);
        DB = new DBHelper(this);
        builder.setView(v);
        alertDialog = builder.create();
        // Add action buttons

        btnFire.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String patente = matricula.getText().toString();
                        String horario = tiempo.getText().toString();
                        if(patente.equals("")||horario.equals("")) {
                            Toast.makeText(NavigationActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            prf = getSharedPreferences("usuariologueado",MODE_PRIVATE);
                            String usuariologueado = prf.getString("username",null);
                            DB.insertParqueo(patente,horario,usuariologueado);
                            Toast.makeText(NavigationActivity.this, "Parqueo registrado correctamente", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                }
        );
        return alertDialog;
    }
    private ArrayList<Parqueos> listar(){
        prf = getSharedPreferences("usuariologueado",MODE_PRIVATE);
        String usuariologueado = prf.getString("username",null);
        ArrayList<Parqueos> lista = DB.getlistparqueos(usuariologueado);
        return lista;
    }
}
