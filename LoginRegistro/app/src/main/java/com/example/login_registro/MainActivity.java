package com.example.login_registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText username, email, password, repassword;
    Button registrarse, login;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        registrarse = (Button) findViewById(R.id.registrarse);
        login = (Button) findViewById(R.id.login);
        DB = new DBHelper(this);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String correo = email.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals("")||correo.equals(""))
                    Toast.makeText(MainActivity.this, "Complete todos los campos",Toast.LENGTH_SHORT).show();
                else if(pass.equals(repass)==false)
                {
                    Toast.makeText(MainActivity.this, "Las contraseñas deben coincidir",Toast.LENGTH_SHORT).show();
                }
                else if (!validarEmail(correo)){
                    Toast.makeText(MainActivity.this, "Debe introducir un correo valido",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    boolean checkuser = DB.checkuser(user);
                    if(checkuser == false)
                    {
                        boolean checkemail = DB.checkemail(correo);
                        if(checkemail == false)
                        {
                            Boolean insert = DB.insertData(correo, pass, user);
                            if(insert == true)
                            {
                                Toast.makeText(MainActivity.this, "Se ha registrado exitosamente",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "No se ha podido registrar",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "El correo ya se encuentra registrado",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "El usuario ya existe",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    private boolean validarEmail(String e) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(e).matches();
    }
}