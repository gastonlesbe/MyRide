package myride.lesberweb.com.myride;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public CustomArrayAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.lista_gastos, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.lista_gastos, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtAutoNombre);
       // ImageView imageView = (ImageView) rowView.findViewById(R.id.imgGastos);
        TextView extratxt = (TextView) rowView.findViewById(R.id.txtGastosMonto);

        txtTitle.setText(itemname[position]);
      //  imageView.setImageResource(imgid[position]);
        extratxt.setText("Description "+itemname[position]);
        return rowView;

    };
}
