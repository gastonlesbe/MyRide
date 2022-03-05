package myride.lesberweb.com.myride;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.widget.Button;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CambiarFecha extends Activity {

    private CalendarView calendarView;
    private Button btnCalendarCancel, btnCalendarSave;
    private Class goBack;
    private String strFecha;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiar_fecha);

        Intent incomingIntent = getIntent();
         String nombreActivity = incomingIntent.getStringExtra("nombreActivity");
         String fecha = incomingIntent.getStringExtra("fecha");
        try {
           goBack = Class.forName(nombreActivity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         final String goBack = nombreActivity +".class";
            //final Activity atras = goBack;


        calendarView = (CalendarView) findViewById(R.id.calendarView);
       // calendarView.setDate(Long.parseLong(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date())));
        String selectedDate = "30/09/2016";
        try {
            calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(fecha).getTime(), true, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int ii1, int ii2) {
                strFecha= ii2 + "/"+(ii1 +1)+"/"+i;

                //paso la fecha dd/mm/YYYY
                Intent f = new Intent(CambiarFecha.this, CargarNafta.class);
                f.putExtra("strFecha", strFecha);
                startActivity(f);
            }
        });

    }
}
