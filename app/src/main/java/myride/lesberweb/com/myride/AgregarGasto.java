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
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.startapp.sdk.adsbase.StartAppAd;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AgregarGasto extends Activity implements OnClickListener, OnItemSelectedListener {



	private String[] listOfObjects;

	private TypedArray images;
	private int[] iconMantenimiento;
	private ImageView itemImage;

	private TextView txtFecha, txtEdtGasto;
	SimpleCursorAdapter adapter;
	private DbHelper helper;
	private SQLiteDatabase db;
	private DataBaseManager manager;
	private ImageButton btnGuardarMantenimiento, btnFechaMantenimiento, ibtnBorrarGasto;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES= "MyRide";


	private EditText edtGasto;
	private EditText edtNotaMantenimiento;
	private String nombre, fecha, tipoDeAlerta, id, nota, dato, precio;

	DrawerLayout drawerLayout;
	Toolbar toolbar;
	ActionBar actionBar;

	private String Logo, kilometros, marca;
	private ListView lista;
	private ListView ListaMenu;


	 @Override
	   public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.agregar);

		 View view = findViewById(android.R.id.content);
		 Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		 mLoadAnimation.setDuration(2000);
		 view.startAnimation(mLoadAnimation);

		 sharedpreferences  = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		 edtGasto = (EditText)findViewById(R.id.edtGasto);

		 edtNotaMantenimiento =(EditText)findViewById(R.id.edtNotaMantenimiento);

		 listOfObjects = getResources().getStringArray(R.array.gastos);
		 iconMantenimiento = getResources().getIntArray(R.array.gastosIconos);


		 nombre = sharedpreferences.getString("nombre", nombre);
		 kilometros = sharedpreferences.getString("kilometros", kilometros);
		 marca = sharedpreferences.getString("marca", marca);

		 Intent intent = getIntent();
		 fecha = getIntent().getStringExtra("fecha");
		 tipoDeAlerta = getIntent().getStringExtra("tipoDeDato");
		 kilometros = getIntent().getStringExtra("kilometros");
		 nota = getIntent().getStringExtra("nota");
		 id = getIntent().getStringExtra("_id");
		 dato = getIntent().getStringExtra("dato");
		 precio = getIntent().getStringExtra("precio");


		 txtEdtGasto=(TextView)findViewById(R.id.txtEdtGasto);
		 txtEdtGasto.setVisibility(View.INVISIBLE);
		 txtFecha = (TextView) findViewById(R.id.txtFecha);

		 String fecha1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
		 if(fecha != null) {

			 txtFecha.setText(fecha);
			 edtGasto.setText(precio);
			 edtNotaMantenimiento.setText(nota);
			 listOfObjects[0] = dato;

		 }else {
			 txtFecha.setText(fecha1);
		 }

		 //editTextAgregarFabricante = (EditText) findViewById(R.id.editTextAgregarFabricante);

		 btnGuardarMantenimiento=(ImageButton)findViewById(R.id.btnGuardarMantenimiento);
		 btnGuardarMantenimiento.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 guardar();
			 }
		 });

		 btnFechaMantenimiento=(ImageButton)findViewById(R.id.btnFechaMantenimiento);
		 btnFechaMantenimiento.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 cambiarFecha();
			 }
		 });

		 ibtnBorrarGasto=(ImageButton)findViewById(R.id.ibtnBorrarGasto);
		 ibtnBorrarGasto.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 borrar();
			 }
		 });
		 edtGasto = (EditText)findViewById(R.id.edtGasto);

		 edtNotaMantenimiento =(EditText)findViewById(R.id.edtNotaMantenimiento);

		 //images = getResources().obtainTypedArray(R.array.gastosIconos);

		 itemImage = (ImageView) findViewById(R.id.imgMantenimiento);

		 final Spinner spinner = (Spinner) findViewById(R.id.spnAgregarMantenimiento);

		 ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>( getApplicationContext(),
				 R.layout.spinner, listOfObjects);

		 spinnerAdapter.setDropDownViewResource(R.layout.spinner);

		 spinner.setAdapter(spinnerAdapter);

		 spinner.setOnItemSelectedListener(new OnItemSelectedListener() {


			 @Override
			 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				//itemImage.setImageResource(images.getResourceId(spinner.getSelectedItemPosition(), -1));
			 }



			 @Override
			 public void onNothingSelected(AdapterView<?> parent) {

			 }
		 });
	 }

	private void borrar() {
		manager = new DataBaseManager(this);
		manager.eliminar(id);
		Intent intent = new Intent(AgregarGasto.this, Auto.class);
		startActivity(intent);
		finish();

	}

	public void guardar(){

		 String tipo = "gasto";
		// String color = editTextAgregarColor.getText().toString();
		 
		 String dato = ((Spinner)findViewById(R.id.spnAgregarMantenimiento)).getSelectedItem().toString();
		 //String modelo = editTextAgregarFabricante.getText().toString();
		 String tipoDeDato = "gastos";


		 Double precio = Double.valueOf(edtGasto.getText().toString());


		String imagenInt = String.valueOf(itemImage.getDrawable());

		String fecha = txtFecha.getText().toString();
		String dia = fecha.substring(0,2);
		String mes = fecha.substring(3,5);
		String año = fecha.substring(6,10);
		String fecha1 = año +"/"+ mes +"/"+ dia;


		String nota = edtNotaMantenimiento.getText().toString();


		manager = new DataBaseManager(this);
		manager.insertar(nombre, tipo, tipoDeDato, dato, fecha1, nota, precio, kilometros, imagenInt);


		 Intent intent = new Intent(AgregarGasto.this, Auto.class);
		 startActivity(intent);
		 finish();
		StartAppAd.showAd(this);
		 


	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_agregar, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

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
	public void cambiarFecha(){

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
				txtFecha.setText(fecha);
				dialog.dismiss();



			}
		});
		dialog.show();
	}




}