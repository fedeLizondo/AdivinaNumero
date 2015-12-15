package com.example.federicolizondo.adivinanumero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    cDataBase miDd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miDd = new cDataBase(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Btn_JuegoNuevo(View view) {
        Intent i = new Intent(this, gameActivity.class);
        startActivityForResult(i, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {   //Es adivinar el numero
            Intent i = data;
            boolean gameWin = i.getBooleanExtra("Game", false);
            if (gameWin) {
                Integer intentos = i.getIntExtra("CantidadIntentos", 0);
                Integer numero = i.getIntExtra("Numero", 0);
                i = new Intent(this, ingresarNombreActivity.class);
                i.putExtra("CantidadIntentos", intentos);
                i.putExtra("Numero", numero);
                startActivityForResult(i, 2);
            }
        }
        if (requestCode == 2) {
            Intent i = data;
            String texto = i.getStringExtra("Nombre");
            miDd.guardarDatosScore(i.getIntExtra("Numero", 0), i.getIntExtra("CantidadIntentos", 0), texto);
        }
    }


    public void IniciarInstrucciones(View view) {
        Intent i = new Intent(this, InstruccionesActivity.class);
        startActivity(i);
    }

    public void IniciarScores(View view) {
        Intent i = new Intent(this, ScoreActivity.class);
        i.putStringArrayListExtra("Scores", miDd.cargarDatosScore());
        startActivity(i);
    }

    public void Salir(View view) {
        this.finish();
    }

}
