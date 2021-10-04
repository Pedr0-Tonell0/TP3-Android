package com.example.login_registro.ui.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Parqueos {
    private String Patente;
    private String Tiempo;
    private String Usuario;

    public Parqueos() {
    }

    public Parqueos(String patente, String tiempo, String usuario) {
        Patente = patente;
        Tiempo = tiempo;
        Usuario = usuario;
    }

    public String getPatente() {
        return Patente;
    }

    public void setPatente(String patente) {
        Patente = patente;
    }

    public String getTiempo() {
        return Tiempo;
    }

    public void setTiempo(String tiempo) {
        Tiempo = tiempo;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    @Override
    public String toString() {
        return "com.example.login_registro.ui.home.Parqueos{" +
                "Patente=" + Patente +
                ", Tiempo=" + Tiempo +
                ", Usuario=" + Usuario +
                '}';
    }

}
