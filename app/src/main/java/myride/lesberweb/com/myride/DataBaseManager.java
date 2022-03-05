package myride.lesberweb.com.myride;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View.OnClickListener;

public class DataBaseManager {

	//le doy un nombre a la tabla, y a cada una de las columnas
	public static final String TABLE_NAME = "tabla";
	public static final String CN_ID = "_id";
	public static final String CN_NOMBRE = "nombre";
	public static final String CN_TIPO = "tipo";
	public static final String CN_TIPO_DE_DATO ="tipoDeDato";
	public static final String CN_DATO = "dato";
	public static final String CN_FECHA = "fecha";
	public static final String CN_NOTA = "nota";
	public static final String CN_PRECIO = "precio";
	public static final String CN_KILOMETROS = "kilometros";
	public static final String CN_IMAGEN = "imagen";

	public static final String TABLE_NAME2 = "actividades";
	public static final String CN_ID2 = "_id";
	public static final String CN_NOMBRE2 = "nombre2";
	public static final String CN_TIPO_DE_ALERTA = "tipoDeAlerta";
	public static final String CN_KM_AUTO = "kmAuto";
	public static final String CN_KM_ALERTA = "kmAlerta";
	public static final String CN_FECHA_ALERTA = "fechaAlerta";
	public static final String CN_NOTA_ALERTA = "notaAlerta";



	private DbHelper helper;
	private SQLiteDatabase db;
	 
	 String mesEntero = "mesEntero";
        String mes1 = "mes";

	//creo la tabla
	public static final String CREATE_TABLE ="create table " +TABLE_NAME+ " ("
			+ CN_ID + " integer primary key autoincrement,"
			+ CN_NOMBRE + " text not null,"
			+ CN_TIPO + " text,"
			+ CN_TIPO_DE_DATO + " text,"
			+ CN_DATO + " text,"
			+ CN_FECHA + " text,"
			+ CN_NOTA + " text,"
			+ CN_PRECIO + " double,"
			+ CN_KILOMETROS + " text,"
			+ CN_IMAGEN + " text)";


	public static final String CREATE_TABLE2 ="create table " +TABLE_NAME2+ " ("
			+ CN_ID2 + " integer primary key autoincrement,"
			+ CN_NOMBRE2 + " text not null,"
			+ CN_TIPO_DE_ALERTA + " text,"
			+ CN_KM_AUTO + " integer,"
			+ CN_KM_ALERTA + " integer,"
			+ CN_FECHA_ALERTA + " text,"
			+ CN_NOTA_ALERTA  + " integer)";



	private static final String Where = null;
	
	
	public DataBaseManager(OnClickListener onClickListener){
		
		
		helper = new DbHelper((Context) onClickListener);
		db = helper.getWritableDatabase();
	}
	
	public ContentValues generarContentValues(String _id, String nombre, String tipo, String tipoDeDato, String dato,
                                              String fecha, String nota, Double precio, String kilometros, String imagen){
		ContentValues valores = new ContentValues();
		valores.put(CN_ID, _id);
		valores.put(CN_NOMBRE, nombre);
		valores.put(CN_TIPO, tipo);
		valores.put(CN_TIPO_DE_DATO, tipoDeDato);
		valores.put(CN_DATO, dato);
		valores.put(CN_FECHA, fecha);
		valores.put(CN_NOTA, nota);
		valores.put(CN_PRECIO, precio);
		valores.put(CN_KILOMETROS, kilometros);
		valores.put(CN_IMAGEN, imagen);

		return valores;
		
	}

	public ContentValues generarContentValues2(String _id, String nombre2, String tipoDeAlerta,
											   int kmAuto, int kmAlerta,	String fechaAlerta, String notaAlerta){
		ContentValues valores2 = new ContentValues();
		valores2.put(CN_ID2, _id);
		valores2.put(CN_NOMBRE2, nombre2);
		valores2.put(CN_TIPO_DE_ALERTA, tipoDeAlerta);
		valores2.put(CN_KM_AUTO, kmAuto);
		valores2.put(CN_KM_ALERTA, kmAlerta);
		valores2.put(CN_FECHA_ALERTA, fechaAlerta);
		valores2.put(CN_NOTA_ALERTA, notaAlerta);



		return valores2;

	}


	public void insertar(String nombre, String tipo, String tipoDeDato, String dato, String fecha, String nota, Double precio, String kilometros, String imagen)
    {
		db.insert(TABLE_NAME, null, generarContentValues(null,nombre, tipo, tipoDeDato, dato, fecha, nota, precio, kilometros, imagen));
		
	}

	public void insertarAlerta(String nombre2, String tipoDeAlerta, int kmAuto, int kmAlerta, String fechaAlerta,
							   String notaAlerta){
		db.insert(TABLE_NAME2, null, generarContentValues2(null, nombre2, tipoDeAlerta, kmAuto, kmAlerta,
				fechaAlerta, notaAlerta));

	}

	public void borrarTodo(){
		String sql = "DELETE FROM "+ TABLE_NAME;
		db.execSQL(sql);
	}
	public void eliminar(String _id){
		db.delete(TABLE_NAME, CN_ID +"="+ _id, null);	
	}

	public void eliminarAlerta(String _id){
		db.delete(TABLE_NAME, CN_ID +"="+ _id, null);
	}

	public void actualizarKm(String _id, String nombre, String tipo, String tipoDeDato, String dato, String fecha,
							 String nota, Double precio, String kilometros, String imagen){
		db.update(TABLE_NAME, generarContentValues(_id,nombre, tipo, tipoDeDato, dato, fecha, nota, precio, kilometros, imagen),
				CN_TIPO +"=" + "auto", null);
	}

	public void actualizar(String _id, String nombre, String tipo, String tipoDeDato, String dato, String fecha,
						  String nota, Double precio, String kilometros, String imagen){
		db.update(TABLE_NAME, generarContentValues(_id,nombre, tipo, tipoDeDato, dato, fecha, nota, precio, kilometros, imagen),
				CN_ID +"=" + _id, null);
	}
	public void actualizarKmAlertas(String kilometros){
		String sql ="UPDATE "+TABLE_NAME+" SET "+ CN_DATO+ " = "+ CN_KILOMETROS+" - "+ kilometros +" WHERE "+ CN_TIPO + "='alerta'";
		db.execSQL(sql);

	}

	public Cursor mostrarUltimosGastos(){

		//String sql="SELECT* FROM "+ TABLE_NAME +" WHERE " + CN_TIPO + " = 'gasto' ORDER BY fecha";
		return db.rawQuery("SELECT*, substr(fecha, 9,9)||substr(fecha, 5,4)||substr(fecha, 1,4) AS 'fecha'" +
				" FROM "+ TABLE_NAME +" WHERE " + CN_TIPO + "='gasto' ORDER BY " +CN_FECHA, null);
	}
	public Cursor gastoMensual(String mes){
		String sql = "SELECT SUM ("+ CN_PRECIO +") FROM "+ TABLE_NAME + " WHERE "+ CN_FECHA + " LIKE '%"+ mes +"%'";
		Cursor cursor = db.rawQuery(sql, null);

		return cursor;
	}

	public Cursor cargarCursorProductos(){
		
		String[] columnas = new String[]{CN_ID, CN_NOMBRE, CN_TIPO, CN_TIPO_DE_DATO, CN_DATO, CN_NOTA, CN_FECHA};
		
	return	db.query(TABLE_NAME, columnas, null, null, null, null, null, null);
		
	}
	public Cursor cuantoFalta(){
		String sql="SELECT* FROM actividades WHERE KmAlerta - KMAuto";
		return db.rawQuery(sql, null);
	}
	public Cursor AgruparPorTipo(){
		return db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE " + CN_TIPO + " != 'bebe' GROUP BY tipo", null);
	}
	public Cursor AgruparPorBebe(){
		String sql ="SELECT * FROM tabla GROUP BY bebe";
		
		return db.rawQuery(sql, null);
	}
	public Cursor AgruparPorNombre(){
		return db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE " + CN_TIPO +"='auto'GROUP BY nombre", null);
	}
	public Cursor MostrarNombres(){
		return db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE " +
				CN_TIPO +"='auto'GROUP BY nombre", null);
	}

	public Cursor AgruparPorContacto(){
		return db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE " + CN_TIPO +"='contacto'GROUP BY nombre", null);
	}
	public Cursor AgruparPorNombreYTipo(String nombre, String tipo){
		String sql ="SELECT * FROM tabla GROUP BY nombre, tipo";

		return db.rawQuery(sql, null);

	}
	public Cursor MostrarTodasLasAlertasxfecha(){
		return db.rawQuery("SELECT *, substr(fecha, 9,9)||substr(fecha, 5,4)||substr(fecha, 1,4) AS 'fecha'" +
				" FROM "+ TABLE_NAME +" WHERE " + CN_TIPO +"='alerta' ORDER BY "+ CN_FECHA, null);

	}
	public Cursor MostrarTodasLasAlertasxkm(){
		return db.rawQuery("SELECT *, substr(fecha, 9,9)||substr(fecha, 5,4)||substr(fecha, 1,4) AS 'fecha'" +
                " FROM "+ TABLE_NAME +" WHERE " + CN_TIPO +"='alerta' ORDER BY "+ CN_KILOMETROS + " DESC ", null);
	}
	public Cursor gastosPorMes(){
		String sql=("SELECT *,substr(fecha, 0, 8)"+CN_FECHA+", SUM ("+ CN_PRECIO +")"+CN_PRECIO+" FROM "+
				TABLE_NAME +" WHERE " + CN_TIPO + "= 'gasto' GROUP BY substr(fecha, 0, 8) ORDER BY "
				+CN_FECHA+" DESC");
		Cursor cursor = db.rawQuery(sql, null);

		return cursor;
	}
	public Cursor detallePorMes(String mes){

		String sql=("SELECT *,substr(fecha, 9,9)||substr(fecha, 5,3) AS 'fecha'" +
				" FROM "+ TABLE_NAME +" WHERE " + CN_TIPO + "= 'gasto' AND "
				+CN_FECHA + " LIKE  '"+ mes +"%'");

		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
		//Cursor cursor = db.query(TABLE_NAME, new String[] {CN_FECHA},
		//		CN_FECHA + " LIKE ?", new String[] 'mes + "%"},
		//		null, null, CN_FECHA);
		//return cursor;
	}
    public Cursor backup() {
        String sql=("SELECT * FROM tabla WHERE " + CN_TIPO + "= 'gasto'");
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
	public Cursor buscarPorNombre(String nombre) {
        String[] columnas = new String[]{CN_ID,CN_NOMBRE, CN_TIPO, CN_TIPO_DE_DATO, CN_DATO, CN_NOTA, CN_FECHA};
        return db.query(TABLE_NAME,columnas,CN_NOMBRE + "=? ",new String[]{nombre},null,null,null,null);
    }

    public Cursor listarAlertasMenoresA1000(){
        return db.rawQuery("SELECT * FROM tabla, actividades WHERE (actividades.kmAlerta-actividades.kmAuto)<1000 " +
                "AND (actividades.kmAlerta-actividades.kmAuto)>500 " +
                "AND tabla.nombre=actividades.nombre2", null);
        //return db.rawQuery("SELECT * FROM "+ TABLE_NAME2 +" WHERE  kmAlerta>kmAuto GROUP BY kmAlerta", null);
    }

    public Cursor listarAlertasMenoresA500(){
        return db.rawQuery("SELECT * FROM tabla, actividades WHERE (actividades.kmAlerta-actividades.kmAuto)<500 " +
                "AND (actividades.kmAlerta-actividades.kmAuto)>100 " +
                "AND tabla.nombre=actividades.nombre2", null);
        //return db.rawQuery("SELECT * FROM "+ TABLE_NAME2 +" WHERE  kmAlerta>kmAuto GROUP BY kmAlerta", null);
    }

    public Cursor listarAlertasMenoresA100(){
        return db.rawQuery("SELECT * FROM tabla, actividades WHERE (actividades.kmAlerta-actividades.kmAuto)<100 " +
                "AND (actividades.kmAlerta-actividades.kmAuto)>0 " +
                "AND tabla.nombre=actividades.nombre2", null);
        //return db.rawQuery("SELECT * FROM "+ TABLE_NAME2 +" WHERE  kmAlerta>kmAuto GROUP BY kmAlerta", null);
    }
	public Cursor buscarId(String _id){
		String[] columnas = new String[]{CN_ID,CN_NOMBRE, CN_TIPO, CN_TIPO_DE_DATO, CN_DATO, CN_NOTA, CN_FECHA};
		return db.query(TABLE_NAME,columnas,CN_ID + "=?",new String[]{_id},null,null,null);
	}
	
	public Cursor filtrarPorFecha(String fecha){
		String[] columnas  = new String[] {CN_ID, CN_NOMBRE, CN_TIPO, CN_TIPO_DE_DATO, CN_DATO, CN_NOTA, CN_FECHA};
		return db.query(TABLE_NAME,columnas,CN_FECHA + "=?", new String[]{fecha},null,null,null);
	}

	public Cursor listarAutos(){
		String sql=("SELECT * FROM tabla WHERE " + CN_TIPO + "= 'auto'");
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}
	
	public Cursor filtrarPorTipoDeDato(String tipoDeDato, String nombre){
		String[] columnas  = new String[] {CN_ID, CN_NOMBRE, CN_TIPO, CN_TIPO_DE_DATO, CN_DATO, CN_NOTA, CN_FECHA};
		return db.query(TABLE_NAME,columnas,CN_TIPO_DE_DATO +"=?"+ " AND " + CN_NOMBRE + "=?" + " AND " 
		+ CN_TIPO + " = 'actividad'",
		new String[]{tipoDeDato, nombre},null,null,null,null);
	}

	public Cursor filtrarPorNombre(String nombre){
		String[] columnas  = new String[] {CN_ID, CN_NOMBRE, CN_TIPO, CN_TIPO_DE_DATO, CN_DATO, CN_NOTA, CN_FECHA};
		return db.query(TABLE_NAME,columnas,CN_NOMBRE + "=?" + " AND " + CN_TIPO + " = 'actividad' "
				+ "GROUP BY tipoDeDato", new String[]{nombre},null,null,null);
	}



}
