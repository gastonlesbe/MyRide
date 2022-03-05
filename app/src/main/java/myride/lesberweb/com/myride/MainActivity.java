package myride.lesberweb.com.myride;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 1;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES= "MyRide";
    String nombre;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        if (Build.VERSION.SDK_INT < 23) {
            //Do not need to check the permission
         
        } else {
            if (checkAndRequestPermissions()) {
                //If you have already permitted the permission
         
            }
        }
        sharedpreferences  = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        nombre = sharedpreferences.getString("nombre", null);
        if(nombre == null){
            agregarAuto();

        } else{
            Intent a = new Intent (MainActivity.this, ListaDeAutos.class);
            startActivity(a);
            finish();


        }

    }

    private boolean checkAndRequestPermissions() {

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

    private void agregarAuto() {
        //addCalendar();
        Intent i = new Intent (MainActivity.this, AgregarAutoActivity.class);
        startActivity(i);
        finish();

        }

    public void addCalendar() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarContract.Calendars.ACCOUNT_NAME, "myride@gmail.com");
        contentValues.put(CalendarContract.Calendars.ACCOUNT_TYPE, "myride.com");
        contentValues.put(CalendarContract.Calendars.NAME, "MyRide calendar");
        contentValues.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "myride Calendar");
        contentValues.put(CalendarContract.Calendars.CALENDAR_COLOR, "232323");
        contentValues.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        contentValues.put(CalendarContract.Calendars.OWNER_ACCOUNT, "myride@gmail.com");
        contentValues.put(CalendarContract.Calendars.ALLOWED_REMINDERS, "METHOD_ALERT, METHOD_EMAIL, METHOD_ALARM");
        contentValues.put(CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES, "TYPE_OPTIONAL, TYPE_REQUIRED, TYPE_RESOURCE");
        contentValues.put(CalendarContract.Calendars.ALLOWED_AVAILABILITY, "AVAILABILITY_BUSY, AVAILABILITY_FREE, AVAILABILITY_TENTATIVE");


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, LOCATION_REQUEST_CODE);
        }

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        uri = uri.buildUpon().appendQueryParameter(android.provider.CalendarContract.CALLER_IS_SYNCADAPTER,"true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "My Ride")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "myride.com").build();
        getContentResolver().insert(uri, contentValues);
    }


}

