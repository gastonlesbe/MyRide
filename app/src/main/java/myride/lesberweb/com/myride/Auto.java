package myride.lesberweb.com.myride;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import android.os.Environment;
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
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import static android.database.DatabaseUtils.dumpCursorToString;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class Auto extends Activity implements View.OnClickListener {

    ImageView imgLogo;
    TextView txtNombre, txtKilometros, txtGastoMensual, txtAlertaKm, txtFechaProximaAlerta;
    FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5;
    private Button btnEditCar;
    private ListView listUltimosGastos, lista;
    private LinearLayout linGastos, linMensual;
    Context context;
    private byte[] image1;
    private String[] gasto, detalle;

    private ImageButton imageButton;
    private String nombre;
    private String kilometros;
    private String imagen;
    private String marca, meses;
    private boolean isFABOpen;

    private TypedArray images;
    private long fAlerta;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyRide";

    SimpleCursorAdapter adapter;
    private DbHelper helper;
    private SQLiteDatabase db;
    private DataBaseManager manager;
    private Cursor cursor, cursor2;
    private String[] arrData;
    private String parsedDate;

    private AdView adView;
    private AdView mAdView;

    private Date date;

    private static final String SHOWCASE_ID = "sequence example1";
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

        }

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Mostrar diálogo explicativo
            }

            if (Build.VERSION.SDK_INT < 23) {
                //Do not need to check the permission

            } else {
                if (checkAndRequestPermissions()) {
                    //If you have already permitted the permission

                }
            }

            //Banner
            AdView adView = new AdView(this);
            adView.setAdSize(AdSize.BANNER);
            //test
            // adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            //verdadero
            adView.setAdUnitId("ca-app-pub-9841764898906750/1715278568");

            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);


            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

            List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();


            imgLogo = (ImageView) findViewById(R.id.imgLogo);

            nombre = sharedpreferences.getString("nombre", nombre);
            kilometros = sharedpreferences.getString("kilometros", kilometros);
            marca = sharedpreferences.getString("marca", marca);
            imagen = sharedpreferences.getString("imagen", imagen);
            if (marca != null) {
                String txtMarca = marca.replaceAll("\\s", "");
                String txtMarca2 = txtMarca.toLowerCase();

//convierto String ti Bitmap
                byte[] imageAsBytes = Base64.decode(imagen.getBytes(), Base64.DEFAULT);


                imgLogo.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
                );
            }


            //Toast toast1 = Toast.makeText(getApplicationContext(), marca, Toast.LENGTH_SHORT);

            //toast1.show();
            txtKilometros = (TextView) findViewById(R.id.txtKilometros);
            txtKilometros.setText(kilometros + " " + getString(R.string.km));

            txtNombre = (TextView) findViewById(R.id.txtNombre);
            txtNombre.setText(nombre);

            linGastos = (LinearLayout) findViewById(R.id.linGastos);
            linGastos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Auto.this, ListarAlertas.class);
                    startActivity(i);
                    finish();

                }
            });

            linMensual = (LinearLayout) findViewById(R.id.linMensual);
            linMensual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Auto.this, ListarGastos.class);
                    startActivity(i);
                    finish();

                }
            });
            txtAlertaKm = (TextView) findViewById(R.id.txtAlertaKM);
            manager = new DataBaseManager(this);

            cursor = manager.MostrarTodasLasAlertasxkm();
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                arrData = new String[cursor.getColumnCount()];

                arrData[0] = cursor.getString(0); // DeviceID
                arrData[1] = cursor.getString(1); // nombre
                arrData[2] = cursor.getString(2); // tipo
                arrData[3] = cursor.getString(3); // tipoDeAlerta
                arrData[4] = cursor.getString(4); // dato
                arrData[5] = cursor.getString(5); //fecha
                arrData[6] = cursor.getString(6); // nota
                arrData[7] = cursor.getString(7); //precio
                arrData[8] = cursor.getString(8); //km
                int intKmAuto = Integer.parseInt(arrData[8]);
                int intKilometros = Integer.parseInt(kilometros);
                int Alerta = intKmAuto - intKilometros;

                if (Alerta < 200) {
                    linGastos.setBackgroundResource(R.mipmap.amarilloiluminado);
                }
                if (Alerta < 100) {
                    linGastos.setBackgroundResource(R.drawable.naranjailuminado);
                }
                if (Alerta < 5) {
                    linGastos.setBackgroundResource(R.drawable.rojoiluminado);
                }
                txtAlertaKm.setText(Alerta + " " + getString(R.string.km));
            } else {

                txtAlertaKm.setText(R.string.nohayalertas);
            }
            txtFechaProximaAlerta = (TextView) findViewById(R.id.txtFechaProximaAlerta);


            cursor = manager.MostrarTodasLasAlertasxfecha();
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                arrData = new String[cursor.getColumnCount()];

                arrData[0] = cursor.getString(0); // DeviceID
                arrData[1] = cursor.getString(1); // nombre2
                arrData[2] = cursor.getString(2); // tipodeAlerta
                arrData[3] = cursor.getString(3); // kmAuto
                arrData[4] = cursor.getString(4); // kmAlerta
                arrData[5] = cursor.getString(5); //fecha

                String date = new StringBuilder(arrData[5]).toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    Date fechaAlerta = sdf.parse(date);
                    fAlerta = fechaAlerta.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long msDiff = fAlerta - Calendar.getInstance().getTimeInMillis();
                long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                int days = (int) (msDiff / (1000 * 60 * 60 * 24));


                if (days < 20) {
                    linGastos.setBackgroundResource(R.drawable.amarilloiluminado);
                    //txtFechaProximaAlerta.setTextColor(Color.parseColor("#ffe500"));
                }
                if (days < 10) {
                    linGastos.setBackgroundResource(R.drawable.naranjailuminado);
                    //txtFechaProximaAlerta.setTextColor(Color.parseColor("#ff8c00"));
                }
                if (days < 5) {
                    //linGastos.setBackgroundColor(Color.parseColor("#ff0000"));
                    linGastos.setBackgroundResource(R.drawable.rojoiluminado);
                }
                txtFechaProximaAlerta.setText(days + " " + getString(R.string.dias));
            } else {

                txtFechaProximaAlerta.setText(" ");
            }

            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab1 = (FloatingActionButton) findViewById(R.id.fab1);
            fab2 = (FloatingActionButton) findViewById(R.id.fab2);
            fab3 = (FloatingActionButton) findViewById(R.id.fab3);
            fab4 = (FloatingActionButton) findViewById(R.id.fab4);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isFABOpen) {
                        showFABMenu();
                    } else {
                        closeFABMenu();
                    }
                }
            });
            txtGastoMensual = (TextView) findViewById(R.id.txtGastoMensual);
            Intent getDate = getIntent();

            String fecha1 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
            String mes = fecha1.substring(5, 7);


            //txtGastoMensual.setText(mes);
            manager = new DataBaseManager(this);


            cursor = manager.gastoMensual(mes);
            if (cursor.getCount() !=0) {
                cursor.moveToFirst();
                txtGastoMensual.setText("$ " + cursor.getString(0));
            } else {
                txtGastoMensual.setText(" ");
            }

            btnEditCar = (Button) findViewById(R.id.btnEditCar);
            btnEditCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actualizarKm();

                }
            });


            imageButton = (ImageButton) findViewById(R.id.imageButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(Auto.this);
                    dialog.setContentView(R.layout.activity_menu);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    final ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.imageButton5);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Use the Builder class for convenient dialog construction
                            share();
                            dialog.dismiss();


                        }
                    });

                    ImageButton dialogButton2 = (ImageButton) dialog.findViewById(R.id.imageButton3);
                    // if button is clicked, close the custom dialog
                    dialogButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            rateMe();
                            dialog.dismiss();
                        }
                    });
                    ImageButton dialogButton3 = (ImageButton) dialog.findViewById(R.id.imageButton2);
                    // if button is clicked, close the custom dialog
                    dialogButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mandarEmail();
                            dialog.dismiss();

                        }
                    });
                    ImageButton dialogButton4 = (ImageButton) dialog.findViewById(R.id.imageButton);
                    // if button is clicked, close the custom dialog
                    dialogButton4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            borrarTodo();
                            //recreate
                            dialog.dismiss();
                        }
                    });

                    ImageButton dialogButton5 = (ImageButton) dialog.findViewById(R.id.imageButton6);
                    // if button is clicked, close the custom dialog
                    dialogButton5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            backup();
                            //recreate
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
            });

        }

        private void backup () {
            String nombre = getIntent().getStringExtra("nombre");
            manager = new DataBaseManager(this);
            cursor = manager.backup();


            String data = dumpCursorToString(manager.backup());
            String carpeta = getResources().getString(R.string.app_name);
            try {
                File root = Environment.getExternalStorageDirectory();
                Log.i(TAG, "path.." + root.getAbsolutePath());

                //check sdcard permission
                if (root.canWrite()) {
                    File fileDir = new File(root.getAbsolutePath() + "/" + carpeta + "/");
                    fileDir.mkdirs();

                    //   Log.d("DATABASE", db.getAllBYname());

                    File file = new File(fileDir, carpeta + ".csv");
                    FileWriter filewriter = new FileWriter(file);
                    BufferedWriter out = new BufferedWriter(filewriter);
                    out.write(String.valueOf(data));
                    out.close();
                    Toast.makeText(getBaseContext(), "exportado en " + carpeta, Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                Log.e("ERROR:---", "Could not write file to SDCard" + e.getMessage());
            }
        }

        private void share () {
            Intent share = new Intent(Intent.ACTION_SEND);

            // If you want to share a png image only, you can do:
            // setType("image/png"); OR for jpeg: setType("image/jpeg");
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=myride.lesberweb.com.myride");

            startActivity(Intent.createChooser(share, "¡¡¡Compartime!!!"));


        }


        private void rateMe () {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=myride.lesberweb.com.myride")));

        }

        private void mandarEmail () {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lesberweb@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Desde My Ride");
            startActivity(Intent.createChooser(intent, ""));
        }

        private void borrarTodo () {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_DarkActionBar);
            //dialog.setContentView(R.layout.activity_help);
            dialog.setTitle("Borrar Todo");
            dialog.setMessage("¿Estas seguro que queres borrar todos los datos?");

            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //pass
                }
            });
            dialog.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final AlertDialog.Builder dialog2 = new AlertDialog.Builder(Auto.this, R.style.Theme_AppCompat_DayNight_DarkActionBar);
                    //dialog.setContentView(R.layout.activity_help);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();

                    manager.borrarTodo();
                    Intent in = new Intent(Auto.this, AgregarAutoActivity.class);
                    startActivity(in);
                    finish();
                    // custom dialog
                }
            });
            dialog.show();
        }

        private void showFABMenu () {
            isFABOpen = true;
            fab.setImageResource(R.drawable.deletebutton);
            fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarMantenimiento();
                }
            });
            fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarGasto();
                }
            });
            fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
            fab3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarAlerta();
                }
            });
            fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
            fab4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cargarNafta();
                }
            });

        }

        private void actualizarKm () {
            Intent i = new Intent(this, ActualizarKm.class);
            i.putExtra("kilometros", kilometros);
            startActivity(i);
            finish();
        }

        private void cargarNafta () {
            Intent i = new Intent(this, CargarNafta.class);
            i.putExtra("nombre", nombre);
            startActivity(i);
            finish();
        }

        private void agregarAlerta () {
            Intent i = new Intent(this, AgregarAlerta.class);
            //i.putExtra("foto", foto);
            startActivity(i);
            finish();
        }

        private void agregarMantenimiento () {
            Intent i = new Intent(this, AgregarMantenimiento.class);
            //i.putExtra("foto", foto);
            startActivity(i);
            finish();
        }

        private void agregarGasto () {
            Intent i = new Intent(this, AgregarGasto.class);
            //i.putExtra("foto", foto);
            startActivity(i);
            //finish();

        }


        private void closeFABMenu () {
            isFABOpen = false;
            fab.setImageResource(R.drawable.plus_sign);
            fab1.animate().translationY(0);
            fab2.animate().translationY(0);
            fab3.animate().translationY(0);
            fab4.animate().translationY(0);
        }


        @Override
        public void onClick (View v){

            // TODO Auto-generated method stub
            if (v.getId() == R.id.txtGastoMensual || v.getId() == R.id.txtAlertaKM || v.getId() == R.id.btnEditCar || v.getId() == R.id.fab) {

                //presentShowcaseSequence();

            } else if (v.getId() == R.id.fab4) {

                // MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);
                Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show();
            }


        }

        private class LoadDataTask extends AsyncTask {

            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }

            @Override
            protected void onPreExecute() {
                Toast.makeText(getApplicationContext(), "Loading data...", Toast.LENGTH_SHORT).show();
            }

            protected void onPostExecute(Void unused) {
                adapter.changeCursor(cursor);
            }
        }


        private boolean checkAndRequestPermissions () {

            int storagePermission = ContextCompat.checkSelfPermission(this,


                    android.Manifest.permission.READ_EXTERNAL_STORAGE);


            int storagePermission1 = ContextCompat.checkSelfPermission(this,


                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE);


            List<String> listPermissionsNeeded = new ArrayList<>();
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (storagePermission1 != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,


                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
                return false;
            }

            return true;

        }

    }
