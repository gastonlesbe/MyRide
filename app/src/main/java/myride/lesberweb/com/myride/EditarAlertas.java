package myride.lesberweb.com.myride;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EditarAlertas extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private String[] listOfObjects, list2, list3;

    private TypedArray images;
    private int[] iconMantenimiento;
    private ImageView itemImage, ibtnFecha, ibtnBorrar, ibtnOk, btnGuardarMantenimiento;

    private TextView txtFecha, txtTipo;
    SimpleCursorAdapter adapter;
    private DbHelper helper;
    private SQLiteDatabase db;
    private DataBaseManager manager;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyRide";
    private Spinner spnAgregarMantenimiento;

    private NumberPicker numberPicker01;
    private NumberPicker numberPicker02;
    private NumberPicker numberPicker03;
    private NumberPicker numberPicker04;
    private NumberPicker numberPicker05;
    private NumberPicker numberPicker06;

    private EditText edtNotaMantenimiento;
    private Context mContext;
    private String[] projection;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;

    String Logo;
    private ListView lista;
    private ListView ListaMenu;
    private String nombre, km, marca, kilometros;
    private String tipoDeDato;
    private String fecha, fecha3, nota, id, dato;
    private char uno, dos, tres, cuatro, cinco, seis;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_alerta);

        View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mLoadAnimation.setDuration(2000);
        view.startAnimation(mLoadAnimation);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        nombre = sharedpreferences.getString("nombre", nombre);
        km = sharedpreferences.getString("kilometros", kilometros);
        marca = sharedpreferences.getString("marca", marca);

        Intent getDate = getIntent();
        //fecha = getDate.getStringExtra("strFecha");

        Intent intent = getIntent();
        fecha = getIntent().getStringExtra("fecha");
        /*
        String año = fecha1.substring(0,4);
        String mes = fecha1.substring(5,6);
        String dia = fecha1.substring(8,9);
        fecha = dia + "/" + mes +"/"+ año;
*/
        tipoDeDato = getIntent().getStringExtra("tipoDeDato");
        dato = getIntent().getStringExtra("dato");
        kilometros = ("000000" + dato).substring(dato.length());
        nota = getIntent().getStringExtra("nota");
        id = getIntent().getStringExtra("id");

        ibtnOk =(ImageButton)findViewById(R.id.ibtnOk);
        edtNotaMantenimiento = (EditText) findViewById(R.id.edtNotaMantenimiento);

        edtNotaMantenimiento.setText(nota);

        txtFecha = (TextView) findViewById(R.id.txtFecha);

        txtFecha.setText(fecha);

        txtTipo = (TextView)findViewById(R.id.txtTipo);
        txtTipo.setText(tipoDeDato);

        
        spnAgregarMantenimiento = (Spinner)findViewById(R.id.spnAgregarMantenimiento);
        spnAgregarMantenimiento.setVisibility(View.INVISIBLE);

        btnGuardarMantenimiento = (ImageButton) findViewById(R.id.btnGuardarMantenimiento);
        btnGuardarMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                           	actualizar();

            }
        });


        ibtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOk();
            }
        });
        ibtnBorrar = (ImageButton)findViewById(R.id.ibtnBorrar);
        ibtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                borrar();
            }
        });
        ibtnFecha = (ImageButton) findViewById(R.id.ibtnFecha);
        ibtnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarFecha();
            }
        });


        numberPicker01 = (NumberPicker) findViewById(R.id.numberPicker01Alerta);
        numberPicker02 = (NumberPicker) findViewById(R.id.numberPicker02Alerta);
        numberPicker03 = (NumberPicker) findViewById(R.id.numberPicker03Alerta);
        numberPicker04 = (NumberPicker) findViewById(R.id.numberPicker04Alerta);
        numberPicker05 = (NumberPicker) findViewById(R.id.numberPicker05Alerta);
        numberPicker06 = (NumberPicker) findViewById(R.id.numberPicker06Alerta);

        numberPicker01.setMinValue(0);
        numberPicker01.setMaxValue(9);
        numberPicker02.setMinValue(0);
        numberPicker02.setMaxValue(9);
        numberPicker03.setMinValue(0);
        numberPicker03.setMaxValue(9);
        numberPicker04.setMinValue(0);
        numberPicker04.setMaxValue(9);
        numberPicker05.setMinValue(0);
        numberPicker05.setMaxValue(9);
        numberPicker06.setMinValue(0);
        numberPicker06.setMaxValue(9);



            uno = kilometros.charAt(0);
            dos = kilometros.charAt(1);
            tres = kilometros.charAt(2);
            cuatro = kilometros.charAt(3);
            cinco = kilometros.charAt(4);
            seis = kilometros.charAt(5);



            int int_uno = Character.getNumericValue(uno);
            int int_dos = Character.getNumericValue(dos);
            int int_tres = Character.getNumericValue(tres);
            int int_cuatro = Character.getNumericValue(cuatro);
            int int_cinco = Character.getNumericValue(cinco);
            int int_seis = Character.getNumericValue(seis);

            numberPicker01.setValue(int_uno);
            numberPicker02.setValue(int_dos);
            numberPicker03.setValue(int_tres);
            numberPicker04.setValue(int_cuatro);
            numberPicker05.setValue(int_cinco);
            numberPicker06.setValue(int_seis);


    }

    private void btnOk() {

        String tipodeGasto ="gasto";

        String tipodeDato = txtTipo.getText().toString();
        String imagen = dato;
        String fecha = txtFecha.getText().toString();
        Double precio = 0.00;


        //Date mDate = new Date (fecha.getT

        String nota = edtNotaMantenimiento.getText().toString();

        String kilometros = km;

        Intent intent = new Intent(EditarAlertas.this, EditarMantenimiento.class);
        intent.putExtra("fecha", fecha);
        intent.putExtra("dato", tipodeDato);
        //intent.putExtra("dato", dato);
        intent.putExtra("nota", nota);
       // intent.putExtra("kilometros", km);
        startActivity(intent);
        finish();


        manager = new DataBaseManager(this);
        manager.eliminarAlerta(id);


    }

    private void borrar() {


        manager = new DataBaseManager(this);
        manager.eliminarAlerta(id);
        Intent intent = new Intent(EditarAlertas.this, Auto.class);
        startActivity(intent);
        finish();



    }

    private void actualizar() {
        kilometros = sharedpreferences.getString("kilometros", kilometros);
        tipoDeDato = txtTipo.getText().toString();

        String nombre = txtFecha.getText().toString();
        String tipo = "alerta";

        String fecha = txtFecha.getText().toString();
        String dia = fecha.substring(0,2);
        String mes = fecha.substring(3,5);
        String año = fecha.substring(6,10);
        String fecha1 = año +"/"+ mes +"/"+ dia;

        String nota = edtNotaMantenimiento.getText().toString();

        int kilometrosAlerta = Integer.parseInt("" + numberPicker01.getValue() + "" + numberPicker02.getValue() +
                "" + numberPicker03.getValue() + "" + numberPicker04.getValue() + "" + numberPicker05.getValue() +
                "" + numberPicker06.getValue());

        int intKm = Integer.parseInt(kilometros);
        dato = String.valueOf(kilometrosAlerta);
        String imagen = " ";
        double precio = 0.00;

        int kmAuto = intKm + kilometrosAlerta;
        kilometros = String.valueOf(kmAuto);

        manager = new DataBaseManager(this);
        manager.actualizar(id,nombre, tipo, tipoDeDato, dato, fecha1, nota, precio, kilometros, imagen);

        Intent intent = new Intent(EditarAlertas.this, Auto.class);
        startActivity(intent);
        finish();


    }

    public void guardar() {
        kilometros = sharedpreferences.getString("kilometros", kilometros);

        String tipo ="alerta";

        String tipoDeDato = ((Spinner) findViewById(R.id.spnAgregarMantenimiento)).getSelectedItem().toString();
        String imagen = tipoDeDato;
        String nombre = txtFecha.getText().toString();

        Double precio = 0.00;
        String fecha = txtFecha.getText().toString();
        String dia = fecha.substring(0,2);
        String mes = fecha.substring(3,5);
        String año = fecha.substring(6,10);
        String fecha1 = año +"/"+ mes +"/"+ dia;


        //Date mDate = new Date (fecha.getT

        int intFecha = 0;
        String nota = edtNotaMantenimiento.getText().toString();

        String kmAlerta = "" + numberPicker01.getValue() + "" + numberPicker02.getValue() +
                "" + numberPicker03.getValue() + "" + numberPicker04.getValue() + "" + numberPicker05.getValue() +
                "" + numberPicker06.getValue();

        int intKilometros = Integer.parseInt(kilometros);
        int intkmAlerta = Integer.parseInt(kmAlerta);
        String dato = kmAlerta;

        int kmAuto = intKilometros + intkmAlerta;
        String kilometros = String.valueOf(kmAuto);

        manager = new DataBaseManager(this);
        manager.insertar(nombre, tipo, tipoDeDato, dato, fecha1, nota, precio, kilometros, imagen);

        Intent intent = new Intent(EditarAlertas.this, Auto.class);
        startActivity(intent);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar, menu);
        return true;
    }

    private void cambiarFecha() {

        //fecha = txtFecha.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cambiar_fecha);
        dialog.setTitle("Seleccione una Fecha");
        final CalendarView calendarView = (CalendarView) dialog.findViewById(R.id.calendarView);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int dayOfMonth) {
                int d = dayOfMonth;
                int m = month;
                int y = year;

                String año = String.valueOf(year);
                int mesPrev = (month+1);
                NumberFormat formatter = new DecimalFormat("00");
                String s = formatter.format(mesPrev);
                String dia = formatter.format(dayOfMonth);

                fecha = dia+"/"+s+"/"+año;
                txtFecha.setText(dia+"/"+s+"/"+año);
                dialog.dismiss();


            }
        });
        dialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = txtFecha.getText().toString();
        String tipo = "auto";


        String marca = ((Spinner)findViewById(R.id.spnAgregarMarca)).getSelectedItem().toString();

        String tipoDeDato = marca;

        String dato = " ";

        String imagen = itemImage.toString();

        String nota = txtFecha.getText().toString();
        Double precio = 0.00;
        String kilometros = "" + numberPicker01.getValue() + "" + numberPicker02.getValue() +
                "" + numberPicker03.getValue() + "" + numberPicker04.getValue() + "" + numberPicker05.getValue() +
                "" + numberPicker06.getValue();

        manager = new DataBaseManager(this);
        manager.insertar(nombre, tipo, tipoDeDato, dato, fecha, nota, precio, kilometros, imagen);
        Intent intent = new Intent(EditarAlertas.this, Auto.class);
        startActivity(intent);
        finish();


        return false;

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        //guardar1();
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        // TODO Auto-generated method stub
        String marca = ((Spinner)findViewById(R.id.spnAgregarMarca)).getSelectedItem().toString();

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }




}
