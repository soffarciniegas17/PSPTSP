package com.example.sofia.psptsp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeLog extends AppCompatActivity {

    private Chronometer cronometro;
    private Dialog save;
    Spinner spinner;
    TextView texinicio, fin;
    Button binicio, bstop;
    private String[] lista= new String[]{"PLAN", "DLD", "CODE", "COMPILE", "UT", "UP"};
    private Calendar calendar;
    private int delta, tiempo=0, menos=0;
    private String inicio, comentarios, Final, phase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_log);

        //INIT
        texinicio= findViewById(R.id.inicio);
        fin= findViewById(R.id.finciclo);
        binicio= findViewById(R.id.binicio);
        bstop= findViewById(R.id.bfinal);

     // spinner= findViewById(R.id.spinner);
      cronometro= findViewById(R.id.crono);
        save= new Dialog(this);
        save.setContentView(R.layout.dialog_saved);
        save.setCancelable(false);
        save.setCanceledOnTouchOutside(false);
        save.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


      // spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.activity_list_item, lista));
    }

    public void iniciar(View view) {
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date= new Date();
        inicio= hourdateFormat.format(date);

        texinicio.setText(inicio);
        binicio.setEnabled(false);

        cronometro.start();

        Intent servicio = new Intent(this, ServiceApi.class);
        servicio.putExtra("actividad", 1);
        startService(servicio);
    }

   public void agregar(View view) {
        EditText editText = findViewById(R.id.tiempomas);
        menos=+ Integer.parseInt(editText.getText().toString());

        editText.setText("");

    }
    public void cerrar(View view) {
        DateFormat formato = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date= new Date();
        Final= formato.format(date);

        fin.setText(Final);
        bstop.setEnabled(false);

        cronometro.stop();
        Intent servicio = new Intent(this, ServiceApi.class);
        stopService(servicio);

        Long elapsed= cronometro.getBase() - SystemClock.elapsedRealtime();

        tiempo= (int) (elapsed/1000)/60;
        delta= tiempo-menos;
    }

    public void guardar(View view) {
        EditText editText = findViewById(R.id.editar);
        comentarios= editText.getText().toString();

     /* spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                phase= (String) parent.getItemAtPosition(position);
            }
        });*/

        DataBase dataBase= new DataBase(this);
        dataBase.insertTime(inicio, Final,"dah",delta, comentarios);
        show();

    }

    @SuppressLint("SetTextI18n")
    private void show(){
        Button salir = save.findViewById(R.id.cancel);
        final TextView t1, t2, t3, t4, t5, t6;
        t1= save.findViewById(R.id.text1);
        t2= save.findViewById(R.id.text2);
        t3= save.findViewById(R.id.text3);
        t4= save.findViewById(R.id.text4);
        t5= save.findViewById(R.id.text5);
        t6= save.findViewById(R.id.text6);

        t1.setText("Inicio de fase \n"+ inicio);
        t2.setText("Fase: \n"+ phase);
        t3.setText("Interrupciones: \n"+ menos);
        t4.setText("Delta:\n "+ delta);
        t5.setText("Fin de la fase:\n "+ Final);
        t6.setText("Comentarios: \n"+ comentarios);

        binicio.setEnabled(true);
        bstop.setEnabled(true);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.dismiss();
            }
        });

        save.show();
    }

    public void salir(View view) {
    }
}
