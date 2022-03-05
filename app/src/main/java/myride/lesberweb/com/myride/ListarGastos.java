package myride.lesberweb.com.myride;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class ListarGastos extends Activity implements View.OnClickListener {

    ListView list1;


    SimpleCursorAdapter adapter;
    private DbHelper helper;
    private SQLiteDatabase db;
    private DataBaseManager manager;
    private Cursor cursor;
    private ImageButton imgBtnClose;
    private TextView txtListAlertAuto, txtKmAutoAlerta, textView6;
    private ImageView imgListAlertAuto;
    private String kilometros;
    private String[] arrData;
    private String kilometrosAlerta, nombre, kilometrosAuto, imagen;
    private Button btnListBy;

    private boolean listarPorKm = true;
    private InterstitialAd mInterstitialAd;


    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyRide";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_activity);
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
        kilometrosAuto = sharedpreferences.getString("kilometros", kilometros);
        nombre = sharedpreferences.getString("nombre", nombre);
        imagen = sharedpreferences.getString("imagen", imagen);

        textView6 = (TextView)findViewById(R.id.textView6);
        textView6.setText(R.string.gastomensual);


        imgBtnClose = (ImageButton)findViewById(R.id.imgBtnClose);
        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarGastos.this, Auto.class);
                startActivity(intent);
                finish();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });

        txtListAlertAuto= (TextView)findViewById(R.id.txtListAlertAuto);
        txtListAlertAuto.setText(nombre);
        txtKmAutoAlerta=(TextView)findViewById(R.id.txtKmAutoAlerta);
        txtKmAutoAlerta.setText(kilometrosAuto + "  KM");

        imgListAlertAuto=(ImageView)findViewById(R.id.imgListAlertAuto);
        byte[] imageAsBytes = Base64.decode(imagen.getBytes(), Base64.DEFAULT);


        imgListAlertAuto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        list1 = (ListView)findViewById(R.id.list1);

        btnListBy = (Button)findViewById(R.id.btnListarPor);
        btnListBy.setVisibility(View.INVISIBLE);

        manager = new DataBaseManager(this);
        int intKilometrosAlerta = 0;
        int intKilometrosAuto= 0;
        int intKilometros = 0;
        int alerta = 0;

        String dato = "dato";
        String km = "kilometros";
        String[] from = new String[]{manager.CN_FECHA, manager.CN_PRECIO};
        int[] to = new int[]{R.id.txtGastos2Fecha, R.id.txtGastos2Monto};

        cursor = manager.gastosPorMes();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            dato = cursor.getString(cursor.getColumnIndexOrThrow("dato"));

            adapter = new SimpleCursorAdapter(this, R.layout.lista_gastos2, cursor, from, to, 0);
            list1.setAdapter(adapter);


        }
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                //obtener el cursor con el id correspondiente
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                String tipoDeDato = cursor.getString(cursor.getColumnIndexOrThrow("tipoDeDato"));
                String kilometros = cursor.getString(cursor.getColumnIndexOrThrow("kilometros"));
                String dato = cursor.getString(cursor.getColumnIndexOrThrow("dato"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                String nota = cursor.getString(cursor.getColumnIndexOrThrow("nota"));
                String alertaId = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                //lo mandamos a la actividad edit_activity
                Intent intent = new Intent (ListarGastos.this, ListarGastosDetalle.class);

                intent.putExtra("tipoDeDato", tipoDeDato);
                intent.putExtra("kilometros", kilometros);
                intent.putExtra("fecha", fecha);
                intent.putExtra("nota", nota);
                intent.putExtra("id", alertaId);
                intent.putExtra("dato", dato);

                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    public void onClick(View view) {

    }
}
