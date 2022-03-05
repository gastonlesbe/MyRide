package myride.lesberweb.com.myride;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.startapp.sdk.adsbase.StartAppAd;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CargarNafta extends Activity implements OnClickListener, OnItemSelectedListener {


	private String[] listOfObjects;

	private TypedArray images;
	private int[] iconMantenimiento;
	private ImageView itemImage;

	private TextView txtFechaNafta;
	SimpleCursorAdapter adapter;
	private DbHelper helper;
	private SQLiteDatabase db;
	private DataBaseManager manager;
	private ImageButton btnGuardarNafta, btnCambiarFechaNafta;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES= "MyRide";

	private NumberPicker NumberPicker1;
	private NumberPicker NumberPicker2;
	private NumberPicker NumberPicker3;
	private NumberPicker NumberPicker4;
	private NumberPicker NumberPicker5;
	private NumberPicker NumberPicker6;

	private NumberPicker NumberPicker01Nafta;
	private NumberPicker NumberPicker02Nafta;
	private NumberPicker NumberPicker03Nafta;
	private NumberPicker NumberPicker04Nafta;
	private NumberPicker NumberPicker05Nafta;
	private NumberPicker NumberPicker06Nafta;

	private EditText edtNaftaMonto;

	DrawerLayout drawerLayout;
	Toolbar toolbar;
	ActionBar actionBar;

	String Logo;
	private ListView lista;
	private ListView ListaMenu;
	private String dato, tipoDeDato, fecha, tipo, imagen, nota, kilometros, nombre, km;
	private Double precio;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_nafta);

		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		View view = findViewById(android.R.id.content);
		Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		mLoadAnimation.setDuration(2000);
		view.startAnimation(mLoadAnimation);

		sharedpreferences  = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		nombre = sharedpreferences.getString("nombre", nombre);
		km = sharedpreferences.getString("kilometros", kilometros);


		Intent getDate = getIntent();
		String fecha = getDate.getStringExtra("strFecha");

		txtFechaNafta = (TextView) findViewById(R.id.txtFechaNafta);
		String fecha1 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
		if(fecha != null) {

			txtFechaNafta.setText(fecha);
		}else {
			txtFechaNafta.setText(fecha1);
		}
		//editTextAgregarFabricante = (EditText) findViewById(R.id.editTextAgregarFabricante);

		btnGuardarNafta=(ImageButton)findViewById(R.id.btnGuardarNafta);
		btnGuardarNafta.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view) {
			guardar();
			}
			});

		edtNaftaMonto = (EditText)findViewById(R.id.edtNaftaMonto);
		edtNaftaMonto.requestFocus();


		btnCambiarFechaNafta=(ImageButton)findViewById(R.id.btnCambiarFechaNafta);
		btnCambiarFechaNafta.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cambiarFecha();
			}
		});

		NumberPicker01Nafta = (NumberPicker) findViewById(R.id.NumberPicker01Nafta);
		NumberPicker02Nafta = (NumberPicker) findViewById(R.id.NumberPicker02Nafta);
		NumberPicker03Nafta = (NumberPicker) findViewById(R.id.NumberPicker03Nafta);
		NumberPicker04Nafta = (NumberPicker) findViewById(R.id.NumberPicker04Nafta);
		NumberPicker05Nafta = (NumberPicker) findViewById(R.id.NumberPicker05Nafta);
		NumberPicker06Nafta = (NumberPicker) findViewById(R.id.NumberPicker06Nafta);


		NumberPicker01Nafta.setMinValue(0);
		NumberPicker01Nafta.setMaxValue(9);
		NumberPicker02Nafta.setMinValue(0);
		NumberPicker02Nafta.setMaxValue(9);
		NumberPicker03Nafta.setMinValue(0);
		NumberPicker03Nafta.setMaxValue(9);
		NumberPicker04Nafta.setMinValue(0);
		NumberPicker04Nafta.setMaxValue(9);
		NumberPicker05Nafta.setMinValue(0);
		NumberPicker05Nafta.setMaxValue(9);
		NumberPicker06Nafta.setMinValue(0);
		NumberPicker06Nafta.setMaxValue(9);

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

		NumberPicker01Nafta.setValue(int_uno);
		NumberPicker02Nafta.setValue(int_dos);
		NumberPicker03Nafta.setValue(int_tres);
		NumberPicker04Nafta.setValue(int_cuatro);
		NumberPicker05Nafta.setValue(int_cinco);
		NumberPicker06Nafta.setValue(int_seis);

		itemImage = (ImageView) findViewById(R.id.imgMantenimiento);

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

					fecha = año+"/"+s+"/"+dia;
					txtFechaNafta.setText(año+"/"+s+"/"+dia);
					dialog.dismiss();


				}
			});
			dialog.show();
		}


	public void guardar(){

		//convierto bitmap en string
		itemImage.buildDrawingCache();
		Bitmap bitmap = itemImage.getDrawingCache();
		ByteArrayOutputStream stream=new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
		byte[] image=stream.toByteArray();
		String img_str = Base64.encodeToString(image, 0);

		//String nombre = txtFecha.getText().toString();
		tipo = "gasto";
		// String color = editTextAgregarColor.getText().toString();

		imagen = "img_str";
		//String modelo = editTextAgregarFabricante.getText().toString();
		tipoDeDato = "mantenimiento";

		String imagenInt = String.valueOf(itemImage.getDrawable());

		dato = getString(R.string.Nafta);

		fecha = txtFechaNafta.getText().toString();

		nota = "nota ";

		String kilometros = "" + NumberPicker01Nafta.getValue() + "" + NumberPicker02Nafta.getValue() +
				"" + NumberPicker03Nafta.getValue() + "" + NumberPicker04Nafta.getValue() +
				"" + NumberPicker05Nafta.getValue() +
				"" + NumberPicker06Nafta.getValue();

		precio = Double.valueOf(edtNaftaMonto.getText().toString());
		int intKm = Integer.parseInt(km);
		int intKilometros = Integer.parseInt(kilometros);


		manager = new DataBaseManager(this);
		manager.insertar(nombre, tipo, tipoDeDato, dato, fecha, nota, precio, kilometros, imagenInt);

		if(intKm < intKilometros) {
			manager.actualizarKmAlertas(kilometros);
			SharedPreferences.Editor editor = sharedpreferences.edit();
			editor.putString("kilometros", kilometros);
			//editor.apply();
			editor.commit();
		}
		Intent intent = new Intent(CargarNafta.this, Auto.class);
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




}