package com.example.login_registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username1, password1;
    Button login1, registrarse1;
    DBHelper DB;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username1 = (EditText) findViewById(R.id.username1);
        password1 = (EditText) findViewById(R.id.password1);
        login1 = (Button) findViewById(R.id.login1);
        registrarse1 = (Button) findViewById(R.id.registrarse1);
        pref = getSharedPreferences("usuariologueado",MODE_PRIVATE);
        DB = new DBHelper(this);
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username1.getText().toString();
                String pass = password1.getText().toString();
                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Complete todos los campos",Toast.LENGTH_SHORT).show();
                else{
                    boolean checkuser = DB.checkuserandpass(user, pass);
                    if(checkuser == false)
                    {
                        Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Bienvenido " + user,Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username",user);
                        editor.putString("password",pass);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        registrarse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}