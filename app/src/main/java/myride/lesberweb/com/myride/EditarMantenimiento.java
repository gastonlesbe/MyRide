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

public class EditarMantenimiento extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {



    private String[] listOfObjects;

    private TypedArray images;
    private int[] iconMantenimiento;
    private ImageView itemImage;

    private TextView txtFecha, txtTipoMantenimiento;
    SimpleCursorAdapter adapter;
    private DbHelper helper;
    private SQLiteDatabase db;
    private DataBaseManager manager;
    private ImageButton btnGuardarMantenimiento, btnFechaMantenimiento, ibtnBorrarmantenimiento;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES= "MyRide";

    private NumberPicker NumberPicker1;
    private NumberPicker NumberPicker2;
    private NumberPicker NumberPicker3;
    private NumberPicker NumberPicker4;
    private NumberPicker NumberPicker5;
    private NumberPicker NumberPicker6;


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;

    String Logo;
    private ListView lista;
    private ListView ListaMenu;
    private EditText edtNotaAgregarMantenimiento;
    private EditText edtPrecioMantenimiento;
    private Spinner spnAgregarMantenimiento;

    private String marca, nombre, kilometros, km, nota, tipoDeDato, dato, id, precio, fecha;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_mantenimiento);

        View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mLoadAnimation.setDuration(2000);
        view.startAnimation(mLoadAnimation);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        nombre = sharedpreferences.getString("nombre", nombre);
        km = sharedpreferences.getString("kilometros", kilometros);
        marca = sharedpreferences.getString("marca", marca);

        Intent getDate = getIntent();
        fecha = getDate.getStringExtra("fecha");
        tipoDeDato = getIntent().getStringExtra("tipoDeDato");
        kilometros = getIntent().getStringExtra("kilometros");
        nota = getIntent().getStringExtra("nota");
        dato = getIntent().getStringExtra("dato");
        id = getIntent().getStringExtra("id");
        precio = getIntent().getStringExtra("precio");


        edtNotaAgregarMantenimiento = (EditText) findViewById(R.id.edtNotaAgregarMantenimiento);
        edtPrecioMantenimiento = (EditText) findViewById(R.id.edtPrecioMantenimiento);
        edtPrecioMantenimiento.setText(precio);

        listOfObjects = getResources().getStringArray(R.array.mantenimiento);
        iconMantenimiento = getResources().getIntArray(R.array.mantenIconos);
        images = getResources().obtainTypedArray(R.array.mantenIconos);


        txtFecha = (TextView) findViewById(R.id.txtFecha);

        txtFecha.setText(fecha);
        edtNotaAgregarMantenimiento.setText(nota);

        //txtFecha.setText(String.valueOf(Calendar.getInstance().getTime()));
        //editTextAgregarFabricante = (EditText) findViewById(R.id.editTextAgregarFabricante);

        txtTipoMantenimiento = (TextView) findViewById(R.id.txtTipoMantenimiento);
        txtTipoMantenimiento.setVisibility(View.VISIBLE);
        txtTipoMantenimiento.setText(dato);


        spnAgregarMantenimiento = (Spinner) findViewById(R.id.spnAgregarMantenimiento2);
        spnAgregarMantenimiento.setVisibility(View.INVISIBLE);


        btnGuardarMantenimiento = (ImageButton) findViewById(R.id.btnGuardarMantenimiento);
        btnGuardarMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });

        btnFechaMantenimiento = (ImageButton) findViewById(R.id.btnCambiarFechaMantenimiento);
        btnFechaMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarFecha();
            }
        });
        ibtnBorrarmantenimiento = (ImageButton) findViewById(R.id.ibtnBorrarMantenimiento);
        ibtnBorrarmantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar();
            }
        });

        NumberPicker1 = (NumberPicker) findViewById(R.id.NumberPicker01Nafta);
        NumberPicker2 = (NumberPicker) findViewById(R.id.NumberPicker02Nafta);
        NumberPicker3 = (NumberPicker) findViewById(R.id.NumberPicker03Nafta);
        NumberPicker4 = (NumberPicker) findViewById(R.id.NumberPicker04Nafta);
        NumberPicker5 = (NumberPicker) findViewById(R.id.NumberPicker05Nafta);
        NumberPicker6 = (NumberPicker) findViewById(R.id.NumberPicker06Nafta);


        NumberPicker1.setMinValue(0);
        NumberPicker1.setMaxValue(9);
        NumberPicker2.setMinValue(0);
        NumberPicker2.setMaxValue(9);
        NumberPicker3.setMinValue(0);
        NumberPicker3.setMaxValue(9);
        NumberPicker4.setMinValue(0);
        NumberPicker4.setMaxValue(9);
        NumberPicker5.setMinValue(0);
        NumberPicker5.setMaxValue(9);
        NumberPicker6.setMinValue(0);
        NumberPicker6.setMaxValue(9);


        char uno = km.charAt(0);
        char dos = km.charAt(1);
        char tres = km.charAt(2);
        char cuatro = km.charAt(3);
        char cinco = km.charAt(4);
        char seis = km.charAt(5);

        int int_uno = Character.getNumericValue(uno);
        int int_dos = Character.getNumericValue(dos);
        int int_tres = Character.getNumericValue(tres);
        int int_cuatro = Character.getNumericValue(cuatro);
        int int_cinco = Character.getNumericValue(cinco);
        int int_seis = Character.getNumericValue(seis);

        NumberPicker1.setValue(int_uno);
        NumberPicker2.setValue(int_dos);
        NumberPicker3.setValue(int_tres);
        NumberPicker4.setValue(int_cuatro);
        NumberPicker5.setValue(int_cinco);
        NumberPicker6.setValue(int_seis);


        itemImage = (ImageView) findViewById(R.id.imgMantenimiento2);
        itemImage.setImageResource(R.drawable.tool_symbol);

    }

    private void borrar() {

        manager = new DataBaseManager(this);
        manager.eliminar(id);
        Intent intent = new Intent(EditarMantenimiento.this, Auto.class);
        startActivity(intent);
        finish();


    }

    private void cambiarFecha() {


        //fecha = txtFecha.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cambiar_fecha);
        dialog.setTitle("Seleccione una Fecha");
        final CalendarView calendarView = (CalendarView) dialog.findViewById(R.id.calendarView);


        //	calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(fecha).getTime(), true, true);
        //} catch (ParseException e) {
        //	e.printStackTrace();
        //}

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                int d = dayOfMonth;
                int m = month;
                int y = year;
                String año = String.valueOf(year);
                int mesPrev = (month+1);
                NumberFormat formatter = new DecimalFormat("00");
                String s = formatter.format(mesPrev);
                String dia = formatter.format(dayOfMonth);

                String fecha = dia+"/"+s+"/"+año;
                txtFecha.setText(fecha);
                dialog.dismiss();

            }
        });
        dialog.show();

    }

    public void guardar(){
        id= id;
        nombre = nombre;
        String tipo = "gasto";
        String tipoDeDato = "mantenimiento";
        String imagen =  String.valueOf(itemImage.getDrawable().toString());
        double precios = Double.valueOf(edtPrecioMantenimiento.getText().toString());
        String dato = txtTipoMantenimiento.getText().toString();
        String fecha = txtFecha.getText().toString();
        String nota = edtNotaAgregarMantenimiento.getText().toString();

        String kilometros = "" + NumberPicker1.getValue() + "" + NumberPicker2.getValue() +
                "" + NumberPicker3.getValue() + "" + NumberPicker4.getValue() + "" + NumberPicker5.getValue() +
                "" + NumberPicker6.getValue();


        String dia = fecha.substring(0,2);
        String mes = fecha.substring(3,5);
        String año = fecha.substring(6,10);
        String fecha1 = año +"/"+ mes +"/"+ dia;


        manager = new DataBaseManager(this);
        manager.actualizar(id, nombre, tipo, tipoDeDato, dato, fecha1, nota, precios, kilometros, imagen);


        Intent intent = new Intent(EditarMantenimiento.this, Auto.class);
        startActivity(intent);
        finish();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = txtFecha.getText().toString();
        String tipo = "auto";


        String marca = ((Spinner)findViewById(R.id.spnAgregarMarca)).getSelectedItem().toString();

        String tipoDeDato = marca;

        String dato = " ";

        String imagen = itemImage.toString();

        String fecha = " ";
        String nota = txtFecha.getText().toString();
        double precios = Double.valueOf(edtPrecioMantenimiento.getText().toString());
        String kilometros = "" + NumberPicker1.getValue() + "" + NumberPicker2.getValue() +
                "" + NumberPicker3.getValue() + "" + NumberPicker4.getValue() + "" + NumberPicker5.getValue() +
                "" + NumberPicker6.getValue();



        manager = new DataBaseManager(this);
        manager.actualizar(id, nombre, tipo, tipoDeDato, dato, fecha, nota, precios, kilometros, imagen);
        Intent intent = new Intent(EditarMantenimiento.this, MainActivity.class);
        startActivity(intent);
        finish();


        return false;

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        guardar();
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