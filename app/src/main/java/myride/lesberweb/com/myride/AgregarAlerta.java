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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AgregarAlerta extends Activity implements OnClickListener, OnItemSelectedListener {


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
	private String tipoDeAlerta;
	private String fecha, fecha3, nota, id;
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

		ibtnOk =(ImageButton)findViewById(R.id.ibtnOk);
		edtNotaMantenimiento = (EditText) findViewById(R.id.edtNotaMantenimiento);

		txtFecha = (TextView) findViewById(R.id.txtFecha);
		String fecha1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
		//txtFecha.setText(String.valueOf(fecha));
		//editTextAgregarFabricante = (EditText) findViewById(R.id.editTextAgregarFabricante);

		txtFecha.setText(fecha1);

		ibtnOk.setVisibility(View.INVISIBLE);

		btnGuardarMantenimiento = (ImageButton) findViewById(R.id.btnGuardarMantenimiento);
		btnGuardarMantenimiento.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				guardar();

			}
		});


		ibtnBorrar = (ImageButton)findViewById(R.id.ibtnBorrar);
		ibtnBorrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				borrar();
			}
		});
		ibtnFecha = (ImageButton) findViewById(R.id.ibtnFecha);
		ibtnFecha.setOnClickListener(new OnClickListener() {
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


		list2 = getResources().getStringArray(R.array.mantenimiento);
		listOfObjects = getResources().getStringArray(R.array.gastos);

		iconMantenimiento = getResources().getIntArray(R.array.gastosIconos);

		images = getResources().obtainTypedArray(R.array.gastosIconos);

		itemImage = (ImageView) findViewById(R.id.imgMantenimiento);

		final Spinner spinner = (Spinner) findViewById(R.id.spnAgregarMantenimiento);

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.spinner, list2);

		spinnerAdapter.setDropDownViewResource(R.layout.spinner);

		spinner.setAdapter(spinnerAdapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {


			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//				txtTipo.setText(" ");
				tipoDeAlerta = ((Spinner) findViewById(R.id.spnAgregarMantenimiento)).getSelectedItem().toString();
				//itemImage.setImageResource(images.getResourceId(spinner.getSelectedItemPosition(), -1));
			}


			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			if(tipoDeAlerta!=null){
				txtTipo.setText(tipoDeAlerta);

			}
			}
		});
	}

	private void borrar() {

		Intent intent = new Intent(AgregarAlerta.this, Auto.class);
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
			int intFecha = 0;
		String nota = edtNotaMantenimiento.getText().toString();

		String dato = "" + numberPicker01.getValue() + "" + numberPicker02.getValue() +
				"" + numberPicker03.getValue() + "" + numberPicker04.getValue() + "" + numberPicker05.getValue() +
				"" + numberPicker06.getValue();

		int intKilometros = Integer.parseInt(kilometros);
		int intkmAlerta = Integer.parseInt(dato);

		int kmAuto = intKilometros + intkmAlerta;
		String kilometros = String.valueOf(kmAuto);

		manager = new DataBaseManager(this);
		manager.insertar(nombre, tipo, tipoDeDato, dato, fecha1, nota, precio, kilometros, imagen);

		Intent intent = new Intent(AgregarAlerta.this, Auto.class);
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
				txtFecha.setText(fecha);
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
		Intent intent = new Intent(AgregarAlerta.this, Auto.class);
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