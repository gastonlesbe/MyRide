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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;


public class ListarAlertas extends Activity implements View.OnClickListener {

    ListView list1;


    SimpleCursorAdapter adapter;
    private DbHelper helper;
    private SQLiteDatabase db;
    private DataBaseManager manager;
    private Cursor cursor;
    private ImageButton imgBtnClose;
    private TextView txtListAlertAuto, txtKmAutoAlerta;
    private ImageView imgListAlertAuto;
    private String kilometros;
    private String[] arrData;
    private String kilometrosAlerta, nombre, kilometrosAuto, imagen;
    private Button btnListBy;

    private boolean listarPorKm = false;
    private long fAlerta;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyRide";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_activity);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        kilometrosAuto = sharedpreferences.getString("kilometros", kilometros);
        nombre = sharedpreferences.getString("nombre", nombre);
        imagen = sharedpreferences.getString("imagen", imagen);


        imgBtnClose = (ImageButton)findViewById(R.id.imgBtnClose);
        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarAlertas.this, Auto.class);
                startActivity(intent);
                finish();

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
        btnListBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            listar();
            }
        });

        manager = new DataBaseManager(this);
        int intKilometrosAlerta = 0;
        int intKilometrosAuto= 0;
        int intKilometros = 0;
        int alerta = 0;

        String[] from = new String[]{manager.CN_DATO, manager.CN_TIPO_DE_DATO};
        int[] to = new int[]{R.id.txtGastos2Fecha, R.id.txtGastos2Monto};

        cursor = manager.MostrarTodasLasAlertasxkm();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
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
                Intent intent = new Intent (ListarAlertas.this, EditarAlertas.class);

                intent.putExtra("tipoDeDato", tipoDeDato);
                intent.putExtra("kilometros", kilometros);
                intent.putExtra("fecha", fecha);
                intent.putExtra("nota", nota);
                intent.putExtra("id", alertaId);
                intent.putExtra("dato", dato);

                startActivity(intent);
                //finish();

            }
        });



    }

    private void listar() {
        if (listarPorKm != true) {
            btnListBy.setText(R.string.listarporkm);
            manager = new DataBaseManager(ListarAlertas.this);
            int intKilometrosAlerta = 0;
            int intKilometrosAuto = 0;
            int intKilometros = 0;
            int alerta = 0;

            String[] from = new String[]{manager.CN_FECHA, manager.CN_TIPO_DE_DATO};
            int[] to = new int[]{R.id.txtGastos2Fecha, R.id.txtGastos2Monto};

            cursor = manager.MostrarTodasLasAlertasxfecha();
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                arrData = new String[cursor.getColumnCount()];
                arrData[5] = cursor.getString(5); // dato
                LinearLayout linAlerta =(LinearLayout)findViewById(R.id.linAlerta);
                String date = new StringBuilder(arrData[5]).toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date fechaAlerta = sdf.parse(date);
                    fAlerta = fechaAlerta.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long msDiff = fAlerta - Calendar.getInstance().getTimeInMillis();
                long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                int days = (int) (msDiff / (1000 * 60 * 60 * 24));


                if(days < 20){
                    linAlerta.setBackgroundResource(R.drawable.amarilloiluminado);
                    //txtFechaProximaAlerta.setTextColor(Color.parseColor("#ffe500"));
                }
                if(days < 10){
                    linAlerta.setBackgroundResource(R.drawable.naranjailuminado);
                    //txtFechaProximaAlerta.setTextColor(Color.parseColor("#ff8c00"));
                }
                if(days < 5){
                    //linGastos.setBackgroundColor(Color.parseColor("#ff0000"));
                    linAlerta.setBackgroundResource(R.drawable.rojoiluminado);
                }
                adapter = new SimpleCursorAdapter(ListarAlertas.this, R.layout.lista_gastos2, cursor, from, to, 0);
                list1.setAdapter(adapter);

            }
            listarPorKm = true;

        } else {
            btnListBy.setText(R.string.listarPorMes);

            manager = new DataBaseManager(ListarAlertas.this);
            int intKilometrosAlerta = 0;
            int intKilometrosAuto = 0;
            int intKilometros = 0;
            int alerta = 0;

            String[] from = new String[]{manager.CN_DATO, manager.CN_TIPO_DE_DATO};
            int[] to = new int[]{R.id.txtGastos2Fecha, R.id.txtGastos2Monto};

            cursor = manager.MostrarTodasLasAlertasxkm();
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                adapter = new SimpleCursorAdapter(ListarAlertas.this, R.layout.lista_gastos2, cursor, from, to, 0);
                list1.setAdapter(adapter);


            }
            listarPorKm = false;

        }

    }

    @Override
    public void onClick(View view) {

    }
}