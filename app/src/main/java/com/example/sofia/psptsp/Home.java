package com.example.sofia.psptsp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    View vista;
    TextView tista;
    GridView grid;
    private Dialog menuoption;
    private Animation cargar;
    private  Adapter adapter;
    private String nombre;
    private ArrayList<Item_proyecto> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menuoption= new Dialog(this);
        menuoption.setContentView(R.layout.dialog_menu);
        menuoption.setCancelable(false);
        menuoption.setCanceledOnTouchOutside(false);
        menuoption.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        cargar= AnimationUtils.loadAnimation(this, R.anim.menu_anim);
        cargar.setFillAfter(true);


        grid= findViewById(R.id.grid);

        buscar();
    }


    public void buscar(){
        DataBase db = new DataBase(this);
        lista= new ArrayList<>();
        try {
            adapter= new Adapter(this, lista, R.layout.item);

           Cursor cursor= db.cargar();
            grid.setAdapter(adapter);

            if(cursor.moveToFirst()){

                int id= 0;
                tista= findViewById(R.id.tista);
                vista= findViewById(R.id.vista);
                tista.setVisibility(View.INVISIBLE);
                vista.setVisibility(View.INVISIBLE);
                do {
                    id++;

                    db.ingresarId(id);

                    lista.add(new Item_proyecto(cursor.getString(0),cursor.getInt(1)));

                }while (cursor.moveToNext());
            }

            adapter.notifyDataSetChanged();

        } catch (Exception e ){ }

        menu();
    }

    public void createProject(View v){
        DataBase db = new DataBase(this);

        EditText editText = findViewById(R.id.edit);
        nombre= editText.getText().toString();


        if(nombre.equalsIgnoreCase("")) {
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Error");
            dialog.setMessage("El campo està vacio, intente de nuevo");
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } else {
            db.traer(nombre);

        }

        buscar();
        editText.setText("");

    }

    Button b1, b2, b3;
    TextView back, title;

    public void menu(){

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item_proyecto item = lista.get(position);
                final int envio = item.getId();
                final String nombre= item.getName();


                b1= menuoption.findViewById(R.id.timelog);
                b2= menuoption.findViewById(R.id.defect);
                b3= menuoption.findViewById(R.id.sumary);
                back= menuoption.findViewById(R.id.salir);
                title= menuoption.findViewById(R.id.muestra);

                title.setText(nombre);
                b1.startAnimation(cargar);
                b2.startAnimation(cargar);
                b3.startAnimation(cargar);

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Home.this, TimeLog.class);
                        i.putExtra("id", envio);
                        i.putExtra("nombre", nombre);
                        startActivity(i);

                    }
                });

                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, DefectLog.class);
                        i.putExtra("id", envio);
                        i.putExtra("nombre", nombre);
                        startActivity(i);

                    }
                });

                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Home.this, PlanSumary.class);
                        i.putExtra("id", envio);
                        i.putExtra("nombre", nombre);
                        startActivity(i);

                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuoption.dismiss();
                    }
                });

                menuoption.show();
            }
        });
    }



    // ACTIVITY METHODS

    @Override
    protected void onRestart() {


        super.onRestart();
    }
}
