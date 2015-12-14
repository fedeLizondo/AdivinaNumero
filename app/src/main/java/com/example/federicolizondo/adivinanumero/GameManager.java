package com.example.federicolizondo.adivinanumero;

import java.util.ArrayList;

/**
 * Created by federicolizondo on 04/08/15.
 * Esta clase se encarga de administrar el juego
 */
public class GameManager {

    private ArrayList<Numero> lNumeros;

    private int unidadMil;
    private int centena;
    private int decena;
    private int unidad;
    private boolean win;

    public GameManager() {
        this.lNumeros = new ArrayList<Numero>();
        int[] a = convertirAUnidades(nroRandom());
        this.unidadMil = a[0];
        this.centena = a[1];
        this.decena = a[2];
        this.unidad = a[3];
        this.win = false;
    }

    //METODOS STATICOS
    public static int convertirANumero(int unidadMil, int centena, int decena, int unidad) {
        return unidadMil * 1000 + centena * 100 + decena * 10 + unidad;
    }

    public static int[] convertirAUnidades(int nro) {
        int[] aNro = new int[4];
        //unidad
        aNro[3] = nro % 10;
        //decena
        aNro[2] = (nro % 100 - aNro[3]) / 10;
        //centena
        aNro[1] = (nro % 1000 - (aNro[2] * 10 + aNro[3])) / 100;
        //unidad de Mil
        aNro[0] = (nro - aNro[1] * 100 + aNro[2] * 10 + aNro[3]) / 1000;
        return aNro;
    }
    //-----Getters and Setters

    //Metodos publicos
    public void intento(Numero n) {
        n.verificar(unidadMil, centena, decena, unidad);
        this.win = n.sosElNumero(unidadMil, centena, decena, unidad);
        lNumeros.add(n);
    }

    public boolean estaIngresado(int unidadMil, int centena, int decena, int unidad) {
        int size = lNumeros.size(),
                cont = 0;
        while (cont < size && !lNumeros.get(cont).sosElNumero(unidadMil, centena, decena, unidad)) {
            cont++;
        }
        return (cont < size);
    }

    public int darNumero() {
        return convertirANumero(this.unidadMil, this.centena, this.decena, this.unidad);
    }

    public boolean Gane() {
        return win;
    }

    //METODOS PRIVADOS

    public int cantidadIntentos() {
        return lNumeros.size();
    }

    public ArrayList<Numero> darListaNumeros() {
        return (ArrayList<Numero>) lNumeros.clone();
    }

    private int nroRandom() {
        int um = 0,
                c = 0,
                d = 0,
                u = 0,
                aux = 0;
        while (aux < 1024) {
            while (um == 0) {
                um = (int) ((Math.random() * 10) % 10);
            }
            do {
                c = (int) ((Math.random() * 10) % 10);
            } while (c == um);
            do {
                d = (int) ((Math.random() * 10) % 10);
            } while (d == c || d == um);
            do {
                u = (int) ((Math.random() * 10) % 10);
            } while (u == um || u == c || u == d);
            aux = convertirANumero(um, c, d, u);
        }
        return aux;
    }


/*  public void guardarEnBD()
    {
        //Guardaria el estado actual para recuperar en caso de salir
        //Mejor Pongo los Scores :D
        //que DIA , Con que Numero y con
    }
*/

}
