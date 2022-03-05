package myride.lesberweb.com.myride;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class ActualizarKm extends Activity implements View.OnClickListener {

    private NumberPicker numberPicker01Actualizar,numberPicker02Actualizar, numberPicker03Actualizar,
            numberPicker04Actualizar, numberPicker05Actualizar, numberPicker06Actualizar;
    private Button btnActualizar;


    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES= "MyRide";

    private String dato, marca, tipoDeDato, fecha, tipo, imagen, nota, kilometros, nombre, km;
    private Double precio;

    SimpleCursorAdapter adapter;
    private DbHelper helper;
    private SQLiteDatabase db;
    private DataBaseManager manager;
    private Cursor cursor;
    private InterstitialAd mInterstitialAd;
    private static final String AD_UNIT_ID = "ca-app-pub-9841764898906750/3961610223";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_km);
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this,
                "ca-app-pub-9841764898906750~2484877027");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9841764898906750/3961610223");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });




        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        km = sharedpreferences.getString("kilometros", kilometros);
        nombre = sharedpreferences.getString("nombre", nombre);
        marca = sharedpreferences.getString("marca", marca);
        fecha = sharedpreferences.getString("fecha", fecha);
        imagen = sharedpreferences.getString("imagen", imagen);


        manager = new DataBaseManager(ActualizarKm.this);

        numberPicker01Actualizar =(NumberPicker)findViewById(R.id.numberPicker01Actualizar);
        numberPicker02Actualizar =(NumberPicker)findViewById(R.id.numberPicker02Actualizar);
        numberPicker03Actualizar =(NumberPicker)findViewById(R.id.numberPicker03Actualizar);
        numberPicker04Actualizar =(NumberPicker)findViewById(R.id.numberPicker04Actualizar);
        numberPicker05Actualizar =(NumberPicker)findViewById(R.id.numberPicker05Actualizar);
        numberPicker06Actualizar =(NumberPicker)findViewById(R.id.numberPicker06Actualizar);

        numberPicker01Actualizar.setMinValue(0);
        numberPicker01Actualizar.setMaxValue(9);
        numberPicker02Actualizar.setMinValue(0);
        numberPicker02Actualizar.setMaxValue(9);
        numberPicker03Actualizar.setMinValue(0);
        numberPicker03Actualizar.setMaxValue(9);
        numberPicker04Actualizar.setMinValue(0);
        numberPicker04Actualizar.setMaxValue(9);
        numberPicker05Actualizar.setMinValue(0);
        numberPicker05Actualizar.setMaxValue(9);
        numberPicker06Actualizar.setMinValue(0);
        numberPicker06Actualizar.setMaxValue(9);

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

        numberPicker01Actualizar.setValue(int_uno);
        numberPicker02Actualizar.setValue(int_dos);
        numberPicker03Actualizar.setValue(int_tres);
        numberPicker04Actualizar.setValue(int_cuatro);
        numberPicker05Actualizar.setValue(int_cinco);
        numberPicker06Actualizar.setValue(int_seis);


        btnActualizar =(Button)findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });

    }

    public void guardar(){


        //String nombre = txtFecha.getText().toString();
        tipo = "auto";
        // String color = editTextAgregarColor.getText().toString();

        //imagen = "img_str";
        //String modelo = editTextAgregarFabricante.getText().toString();
        tipoDeDato = marca;

        //String imagenInt = " ";

        dato = "Kilometros";

        fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        nota = "nota ";

        kilometros = "" + numberPicker01Actualizar.getValue() + "" + numberPicker02Actualizar.getValue() +
                "" + numberPicker03Actualizar.getValue() + "" + numberPicker04Actualizar.getValue() +
                "" + numberPicker05Actualizar.getValue() + "" + numberPicker06Actualizar.getValue();

        precio = 0.00;

        manager = new DataBaseManager(this);
        manager.insertar(nombre, tipo, tipoDeDato, dato, fecha, nota, precio, kilometros, imagen);
        manager.actualizarKmAlertas(kilometros);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("kilometros", kilometros);
       // editor.apply();
        editor.commit();
      //  editor.apply();

        Intent intent = new Intent(ActualizarKm.this, Auto.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View view) {


    }
}
