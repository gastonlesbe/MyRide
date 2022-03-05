package myride.lesberweb.com.myride;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	private DbHelper helper;
	private SQLiteDatabase db;
	
	
	
	//le doy nombre a la base de datos  y version de la misma
	private static final String DB_NAME = "Productos.sqlite";
	private static final int DB_SCHEME_VERSION = 12;
	
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_SCHEME_VERSION);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		//creo la base y la tabla
		db.execSQL(DataBaseManager.CREATE_TABLE);
		db.execSQL(DataBaseManager.CREATE_TABLE2);


		// insert default values
	    
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		  if (newVersion > oldVersion) {


          }
	    
		
	}

}
