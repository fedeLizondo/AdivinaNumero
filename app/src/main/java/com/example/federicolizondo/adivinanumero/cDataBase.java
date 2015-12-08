package com.example.federicolizondo.adivinanumero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by federicolizondo on 08/12/15.
 */
public class cDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "juego.db";
    private static final String TABLE_NAME = "Score";

    private static final String COLUMNA_0 = "Id";//TIMEUNIX
    private static final String COLUMNA_1 = "Numero";
    private static final String COLUMNA_2 = "nroIntentos";


    String sqlCreate = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMNA_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMNA_1 + " INTEGER, " + COLUMNA_2 + " INTEGER );";


    public cDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    public static void validarBaseDatos() {

    }

    public StringBuffer cargarDatosScore() {
        String Consulta = "SELECT * FROM " + TABLE_NAME + "ORDER BY " + COLUMNA_2 + " desc LIMIT(10);";
        SQLiteDatabase db = this.getWritableDatabase();
        this.getReadableDatabase();
        Cursor res = db.rawQuery(Consulta, null);
        int tam = res.getCount();
        StringBuffer buffer = new StringBuffer();

        if (tam != 0) //Si tengo Algo
        {
            while (res.moveToNext()) {
                buffer.append(res.getString(1) + "    " + res.getString(2) + "\n");
            }
        }

        return buffer;
    }

    public boolean guardarDatosScore(int nro, int cantidadIntentos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNA_1, nro);
        contentValues.put(COLUMNA_2, cantidadIntentos);
        boolean valorVerdad = db.insert(TABLE_NAME, null, contentValues) == -1;
        db.close();
        return valorVerdad;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

}
