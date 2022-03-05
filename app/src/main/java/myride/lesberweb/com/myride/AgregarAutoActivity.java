package myride.lesberweb.com.myride;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Locale;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.MobileAds;



public class AgregarAutoActivity extends Activity implements OnClickListener, OnItemSelectedListener {



	private String[] listOfObjects;

	private TypedArray images;

	private ImageView itemImage;

	private EditText editTextAgregarPatente;
	private EditText editTextAgregarFabricante;
	private EditText editTextAgregarColor;
	private EditText editTextAgregarYear;
	private EditText editTextAgregarKm, editTextAgregarModelo, editTextAgregarAnio;
	SimpleCursorAdapter adapter;
	private DbHelper helper;
	private SQLiteDatabase db;
	private DataBaseManager manager;
	private Button btnGuardar;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES= "MyRide";

	private NumberPicker NumberPicker01;
	private NumberPicker NumberPicker02;
	private NumberPicker NumberPicker03;
	private NumberPicker NumberPicker04;
	private NumberPicker NumberPicker05;
	private NumberPicker NumberPicker06;
	private Spinner spinner;

	private LinearLayout linKm, linMarca;

	DrawerLayout drawerLayout;
	Toolbar toolbar;
	ActionBar actionBar;

	String Logo;
	private ListView lista;
	private ListView ListaMenu;

	private String nombre;
	private String kilometros;
	private String imagen;
	private String marca;
	private String modelo;
	private String anio;

	private static final String SHOWCASE_ID = "sequence example";

	@Override
	   public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.agregar_auto_activity);

            View view = findViewById(android.R.id.content);
		 Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		 mLoadAnimation.setDuration(2000);
		 view.startAnimation(mLoadAnimation);

		 sharedpreferences  = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		 nombre = sharedpreferences.getString("nombre", nombre);
		 kilometros = sharedpreferences.getString("kilometros", kilometros);
		 marca = sharedpreferences.getString("marca", marca);
		 imagen = sharedpreferences.getString("imagen", imagen);

		editTextAgregarPatente = (EditText) findViewById(R.id.editTextAgregarPatente);
		editTextAgregarPatente.requestFocus();
		editTextAgregarModelo = (EditText) findViewById(R.id.editTextAgregarModelo);
		editTextAgregarAnio = (EditText) findViewById(R.id.editTextAgregarAnio);

		 btnGuardar=(Button)findViewById(R.id.btnGuardar);
		 btnGuardar.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 guardar();
			 }
		 });
		 linKm = (LinearLayout)findViewById(R.id.linKm);
		 linMarca= (LinearLayout)findViewById(R.id.linMarca);
		//presentShowcaseSequence();

		 NumberPicker01 = (NumberPicker) findViewById(R.id.NumberPicker01);
		 NumberPicker02 = (NumberPicker) findViewById(R.id.NumberPicker02);
		 NumberPicker03 = (NumberPicker) findViewById(R.id.NumberPicker03);
		 NumberPicker04 = (NumberPicker) findViewById(R.id.NumberPicker04);
		 NumberPicker05 = (NumberPicker) findViewById(R.id.NumberPicker05);
		 NumberPicker06 = (NumberPicker) findViewById(R.id.NumberPicker06);


		 NumberPicker01.setMinValue(0);
		 NumberPicker01.setMaxValue(9);
		 NumberPicker02.setMinValue(0);
		 NumberPicker02.setMaxValue(9);
		 NumberPicker03.setMinValue(0);
		 NumberPicker03.setMaxValue(9);
		 NumberPicker04.setMinValue(0);
		 NumberPicker04.setMaxValue(9);
		 NumberPicker05.setMinValue(0);
		 NumberPicker05.setMaxValue(9);
		 NumberPicker06.setMinValue(0);
		 NumberPicker06.setMaxValue(9);


		 listOfObjects = getResources().getStringArray(R.array.marcas);
		 images = getResources().obtainTypedArray(R.array.marcasIconos);

		 itemImage = (ImageView) findViewById(R.id.imageView);
		 spinner = (Spinner) findViewById(R.id.spnAgregarMarca);

		 ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
				 R.layout.spinner, listOfObjects);

		 spinnerAdapter.setDropDownViewResource(R.layout.spinner);

		 spinner.setAdapter(spinnerAdapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


			 @Override
			 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


				itemImage.setImageResource(images.getResourceId(spinner.getSelectedItemPosition(), -1));

			 }



			 @Override
			 public void onNothingSelected(AdapterView<?> parent) {

			 }
		 });

		//
		if(nombre != null) {

//convierto String ti Bitmap
			byte[] imageAsBytes = Base64.decode(imagen.getBytes(), Base64.DEFAULT);

			itemImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

			char uno = kilometros.charAt(0);
			char dos = kilometros.charAt(1);
			char tres = kilometros.charAt(2);
			char cuatro = kilometros.charAt(3);
			char cinco = kilometros.charAt(4);
			char seis = kilometros.charAt(5);

			int int_uno = Character.getNumericValue(uno);
			int int_dos = Character.getNumericValue(dos);
			int int_tres = Character.getNumericValue(tres);
			int int_cuatro = Character.getNumericValue(cuatro);
			int int_cinco = Character.getNumericValue(cinco);
			int int_seis = Character.getNumericValue(seis);

			NumberPicker01.setValue(int_uno);
			NumberPicker02.setValue(int_dos);
			NumberPicker03.setValue(int_tres);
			NumberPicker04.setValue(int_cuatro);
			NumberPicker05.setValue(int_cinco);
			NumberPicker06.setValue(int_seis);
			spinner.setTag(marca);

		}

	}



	public void guardar(){
		String nombre = editTextAgregarPatente.getText().toString().toLowerCase();

		String tipo = "auto";
		String marca = ((Spinner)findViewById(R.id.spnAgregarMarca)).getSelectedItem().toString();
		String modelo = editTextAgregarModelo.getText().toString().toLowerCase();
		String anio = editTextAgregarAnio.getText().toString();
		String tipoDeDato = marca +" "+modelo+" "+anio;
		 
		String imagen = "R.mipmap." + marca.toLowerCase(Locale.ROOT).trim();
		Double precio = 0.00;
		String dato = getResources().getString(R.string.Newcar);
		String fecha = String.valueOf(Calendar.getInstance().getTime());
		String nota = " ";

		 String kilometros = "" + NumberPicker01.getValue() + "" + NumberPicker02.getValue() +
				 "" + NumberPicker03.getValue() + "" + NumberPicker04.getValue() + "" + NumberPicker05.getValue() +
				 "" + NumberPicker06.getValue();

		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putString("nombre", nombre);
		editor.apply();
		editor.commit();

		manager = new DataBaseManager(this);
		//real
		manager.insertar(nombre, tipo, tipoDeDato, dato, fecha, nota, precio, kilometros, imagen);
		//test
		//manager.insertar(nombre, tipo, tipoDeDato, dato, fecha, nota, 0.0, kilometros, imagen);
if (TextUtils.isEmpty(editTextAgregarPatente.getText())){
		editTextAgregarPatente.setError(getText(R.string.agregapatente));
	}else{
		Intent intent = new Intent(AgregarAutoActivity.this, ListaDeAutos.class);
		startActivity(intent);
		finish();
	}


	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_agregar, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.linMarca || v.getId() == R.id.editTextAgregarPatente || v.getId() == R.id.linKm || v.getId() == R.id.btnGuardar) {

			//presentShowcaseSequence();

		} else if (v.getId() == R.id.btnGuardar) {

			//MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);
			Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show();
		}

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