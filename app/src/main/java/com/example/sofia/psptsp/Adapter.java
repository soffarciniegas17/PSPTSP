package com.example.sofia.psptsp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item_proyecto> list;
    private  int plantilla;

    public Adapter(Context context, ArrayList<Item_proyecto> list, int plantilla) {
        this.context = context;
        this.list = list;
        this.plantilla = plantilla;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= convertView;
        Holder holder = new Holder();
        if (view==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           view= layoutInflater.inflate(plantilla, null);

            holder.nombre= view.findViewById(R.id.name);
            holder.id= view.findViewById(R.id.numero);

            view.setTag(holder);
        } else {
            holder= (Holder) view.getTag();
        }


        Item_proyecto item = list.get(position);

        holder.nombre.setText(item.getName());
        holder.id.setText("ID: "+item.getId());

        return view;
    }

    public class Holder{
        TextView nombre, id;
    }
}
