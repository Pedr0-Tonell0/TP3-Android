package com.example.login_registro.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.login_registro.R;

import java.util.ArrayList;

public class ParqueosAdapter extends BaseAdapter {
    private ArrayList<Parqueos> elementos;
    private Context context;

    public ParqueosAdapter(Context context, ArrayList<Parqueos> elementos) {
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public Parqueos getItem(int i) {
        return elementos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = convertView;
        if (convertView == null){
            view = inflater.inflate(R.layout.grid_template,null);
        }

        TextView titulopatente = (TextView) view.findViewById(R.id.patente);
        TextView tiempo = (TextView) view.findViewById(R.id.tiempo);

        titulopatente.setText("Patente: "+ getItem(position).getPatente());
        tiempo.setText("Tiempo: "+ getItem(position).getTiempo());
        return view;
    }
}
