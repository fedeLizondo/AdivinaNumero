package com.example.federicolizondo.adivinanumero;

/**
 * Created by federicolizondo on 04/08/15.
 * Es una clase que contiene todos los datos del numero ingresado;
 */
public class Numero {

    private int unidad;
    private int decena;
    private int centena;
    private int uMil;//Unidad de Mil

    private short bien;
    private short regular;

    public Numero(int unidadDeMil, int centena, int decena, int unidad) {
        this.uMil = unidadDeMil;
        this.centena = centena;
        this.decena = decena;
        this.unidad = unidad;

        this.bien = 0;
        this.regular = 0;

    }

    public void verificar(int vUnidadDeMil, int vCentena, int vDecena, int vUnidad) {
        /*
        Esta función compara una unidad contra las pasadas
        modifica la cantidad de  BIEN y las REGULARES
        */
        if (uMil == vUnidadDeMil)
            bien++;
        else if (uMil == vCentena || uMil == vDecena || uMil == vUnidad)
            regular++;
        /////////////////////////////////////////////////////////////
        if (centena == vCentena)
            bien++;
        else if (centena == vUnidadDeMil || centena == vDecena || centena == vUnidad)
            regular++;
        ////////////////////////////////////////////////////////////
        if (decena == vDecena)
            bien++;
        else if (decena == vUnidadDeMil || decena == vCentena || decena == vUnidad)
            regular++;
        ////////////////////////////////////////////////////////////
        if (unidad == vUnidad)
            bien++;
        else if (unidad == vUnidadDeMil || unidad == vCentena || unidad == vDecena)
            regular++;
    }

    public int darCantidadBien() {
        return bien;
    }

    public int darCantidadRegular() {
        return regular;
    }

    public int darNro() {
        return uMil * 1000 + centena * 100 + decena * 10 + unidad;
    }

    public boolean sosElNumero(int vUnidadDeMil, int vCentena, int vDecena, int vUnidad) {
        return uMil == vUnidadDeMil && centena == vCentena && decena == vDecena && unidad == vUnidad;
    }

    public String darInfo() {
        return "Numero: " + this.darNro() + "\nCantidad Bien: " + bien + "\nCantidad Regular:" + regular;
    }

    @Override
    public String toString() {
        //return super.toString();
        return this.darNro() + ":  " + bien + " bien  " + regular + " regular.";
    }
}
