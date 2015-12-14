package com.example.federicolizondo.adivinanumero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by federicolizondo on 08/12/15.
 */
public class cDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "juego.db";
    private static final String TABLE_NAME = "Score";

    private static final String COLUMNA_0 = "Id";//TIMEUNIX
    private static final String COLUMNA_1 = "Numero";
    private static final String COLUMNA_2 = "nroIntentos";
    private static final String COLUMNA_3 = "nombre";


    String sqlCreate = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMNA_0 + " INTEGER AUTOINCREMENT NOT NULL, " + COLUMNA_1 + " INTEGER, " + COLUMNA_2 + " INTEGER, " + COLUMNA_3 + " TEXT NULL);";


    public cDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    public ArrayList<String> cargarDatosScore() {
        Log.i("", "Entre en Funcion de Cargar datos");
        ArrayList<String> datos = new ArrayList<String>();
        //TODO borre el desc
        String Consulta = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMNA_2 + " LIMIT(10);";
        SQLiteDatabase db = this.getWritableDatabase();
        this.getReadableDatabase();
        Cursor res = db.rawQuery(Consulta, null);
        int tam = res.getCount();

        if (tam != 0) //Si tengo Algo
        {
            while (res.moveToNext()) {
                datos.add(res.getString(1) + "    " + res.getString(2) + "    " + res.getString(3));
            }
        }
        res.close();
        db.close();
        return datos;
    }

    public boolean guardarDatosScore(int nro, int cantidadIntentos, String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNA_1, nro);
        contentValues.put(COLUMNA_2, cantidadIntentos);
        contentValues.put(COLUMNA_3, nombre);
        long valorVerdad = 3;
        try {

            valorVerdad = db.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            Log.e("", e.toString());
        }
        db.close();
        Log.e("", "El valor de verdad dentro de la funcion " + valorVerdad);
        return (valorVerdad > -1);

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

}
