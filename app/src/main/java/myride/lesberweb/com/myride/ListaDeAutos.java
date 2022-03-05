package myride.lesberweb.com.myride;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaDeAutos extends AppCompatActivity implements View.OnClickListener {

    ListView list_autos;


    SimpleCursorAdapter adapter;
    private DbHelper helper;
    private SQLiteDatabase db;
    private DataBaseManager manager;
    private Cursor cursor;
    private ImageButton imageButton;
    private String nombre, kilometros, imagen, marca, meses, kilometrosAuto;
    private FloatingActionButton fabAddCar;
    private ImageView imagenLogo;


    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyRide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_autos);


        fabAddCar = (FloatingActionButton) findViewById(R.id.fabAddCar);
        fabAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (ListaDeAutos.this, AgregarAutoActivity.class);
                startActivity(i);
                finish();
            }
        });
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        kilometrosAuto = sharedpreferences.getString("kilometros", kilometros);
       // nombre = sharedpreferences.getString("nombre", nombre);
        imagen = sharedpreferences.getString("imagen", imagen);


        list_autos = (ListView)findViewById(R.id.list_autos);

        manager = new DataBaseManager(this);
        int intKilometrosAlerta = 0;
        int intKilometrosAuto= 0;
        int intKilometros = 0;
        int alerta = 0;

        //String logo = getLogo();
        //String nombre = manager.CN_NOMBRE;
        Bitmap bmImg = BitmapFactory.decodeFile("manager.CN_IMAGEN");
        String km = "kilometros";
        //imagenLogo = (ImageView)findViewById(R.id.imgListLogo);
        String[] from = new String[]{manager.CN_TIPO_DE_DATO, manager.CN_NOMBRE };
        int[] to = new int[]{R.id.txtAutoMarca, R.id.txtAutoNombre};

        cursor = manager.listarAutos();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            adapter = new SimpleCursorAdapter(this, R.layout.listado_autos, cursor, from, to, 0);
            list_autos.setAdapter(adapter);

           // Toast.makeText(getApplicationContext(), km, Toast.LENGTH_SHORT).show();
        }
       // Toast.makeText(getApplicationContext(), "nada", Toast.LENGTH_SHORT).show();

        list_autos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                //obtener el cursor con el id correspondiente
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String tipoDeDato = cursor.getString(cursor.getColumnIndexOrThrow("tipoDeDato"));
                String kilometros = cursor.getString(cursor.getColumnIndexOrThrow("kilometros"));
                String imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"));

                Intent intent = new Intent (ListaDeAutos.this, Auto.class);

                intent.putExtra("nombre", nombre);
                intent.putExtra("tipoDeDato", tipoDeDato);
                intent.putExtra("kilometros", kilometros);
                intent.putExtra("imagen", imagen);

                startActivity(intent);
                finish();


            }

});

    }

    private void getLogo(){
        if(imagen.equals("fiat")){
            imagenLogo.setBackgroundResource(R.mipmap.fiat);
        }else if(imagen.equals("alfaromeo"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.alfaromeo);
        }else if(imagen.equals("asia"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.asia);
        }else if(imagen.equals("audi"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.audi);
        }else if(imagen.equals("bentley"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.bentley);
        }else if(imagen.equals("bmw"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.bmw);
        }else if(imagen.equals("chery"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.chery);
        }else if(imagen.equals("chevrolet"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.chevrolet);
        }else if(imagen.equals("chrysler"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.chrysler);
        }else if(imagen.equals("citroen"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.citroen);
        }else if(imagen.equals("daewo"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.daewo);
        }else if(imagen.equals("daihatsu"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.daihatsu);
        }else if(imagen.equals("dodge"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.dodge);
        }else if(imagen.equals("ferrari"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.ferrari);
        }else if(imagen.equals("ford"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.ford);
        }else if(imagen.equals("honda"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.honda);
        }else if(imagen.equals("hummer"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.hummer);
        }else if(imagen.equals("hyundai"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.hyundai);
        }else if(imagen.equals("isuzu"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.isuzu);
        }else if(imagen.equals("jaguar"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.jaguar);
        }else if(imagen.equals("jeep"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.jeep);
        }else if(imagen.equals("kia"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.kia);
        }else if(imagen.equals("ktm"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.ktm);
        }else if(imagen.equals("lada"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.lada);
        }else if(imagen.equals("landrover"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.landrover);
        }else if(imagen.equals("mazda"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.mazda);
        }else if(imagen.equals("mercedes"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.mercedes);
        }else if(imagen.equals("mini"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.mini);
        }else if(imagen.equals("mitsubishi"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.mitsubishi);
        }else if(imagen.equals("motomel"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.motomel);
        }else if(imagen.equals("nissan"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.nissan);
        }else if(imagen.equals("peugeot"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.peugeot);
        }else if(imagen.equals("porsche"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.porsche);
        }else if(imagen.equals("renault"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.renault);
        }else if(imagen.equals("rollsroyce"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.rollsroyce);
        }else if(imagen.equals("rover"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.rover);
        }else if(imagen.equals("seat"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.seat);
        }else if(imagen.equals("smart"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.smart);
        }else if(imagen.equals("ssangyong"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.ssangyong);
        }else if(imagen.equals("subaru"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.subaru);
        }else if(imagen.equals("suzuki"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.suzuki);
        }else if(imagen.equals("tata"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.tata);
        }else if(imagen.equals("tesla"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.tesla);
        }else if(imagen.equals("toyota"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.toyota);
        }else if(imagen.equals("volvo"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.volvo);
        }else if(imagen.equals("vw"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.vw);
        }else if(imagen.equals("yamaha"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.yamaha);
        }else if(imagen.equals("zanella"))
        {
            imagenLogo.setBackgroundResource(R.mipmap.zanella);
        }
    }

    @Override
    public void onClick(View view) {

    }
}