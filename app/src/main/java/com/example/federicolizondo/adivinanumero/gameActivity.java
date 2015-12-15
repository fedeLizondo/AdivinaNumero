package com.example.federicolizondo.adivinanumero;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.federicolizondo.adivinanumero.dummy.DummyContent;

import java.util.Locale;


public class gameActivity extends ActionBarActivity implements ActionBar.TabListener {

    public TextView tUM, //TextView Unidad de Mil
            tC,  //TextView Centena
            tD,  //TextView Decena
            tU;  //TextView Unidad
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    GameManager gm;
    //ArrayList<Integer> UnidadMil,Centena,Decena,Unidad;
    int UM, C, D, U;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Inicializo el Game Manager
        gm = new GameManager();

        //Cargo los valores de las Unidades
        UM = 1;
        C = 0;
        D = 2;
        U = 4;
       /* //Inicializo un ArrayList Aux
        ArrayList<Integer> aux = new ArrayList<>();
        aux.add(0);aux.add(1);aux.add(2);aux.add(3);aux.add(4);
        aux.add(5);aux.add(6);aux.add(7);aux.add(8);aux.add(9);
        //Incializo los ArrayList UnidadMil,centena,decena,unidad
        UnidadMil = (ArrayList<Integer>) aux.clone();
        Centena = (ArrayList<Integer>) aux.clone();
        Decena = (ArrayList<Integer>) aux.clone();
        Unidad = (ArrayList<Integer>) aux.clone();
        //Acomodo a los valores correctos  las listas
        UnidadMil.remove(0);
        UnidadMil.remove(UM);
        Centena.remove(C);
        Decena.remove(D);
        Unidad.remove(U);*/

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);


            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        //Cargo los TextView
        tUM = (TextView) mViewPager.findViewById(R.id.Txt_UnidadMil);
        tC = (TextView) mViewPager.findViewById(R.id.Txt_Centena);
        tD = (TextView) mViewPager.findViewById(R.id.Txt_Decena);
        tU = (TextView) mViewPager.findViewById(R.id.Txt_Unidad);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public void finish() {
        DummyContent.removeAllItems();
        super.finish();
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void verificarNumero(View view) {
        String texto = "";
        int nro = GameManager.convertirANumero(UM, C, D, U);
        if (repetidos(UM) || repetidos(C) || repetidos(D)) {
            texto = this.getString(R.string.valoresRepetidos);
        } else {
            if (nro <= 1023 || nro >= 9877) {
                texto = this.getString(R.string.valoresMayores);
                if (nro > 1023)
                    texto = this.getString(R.string.valoresMenores);

            } else {
                if (gm.estaIngresado(UM, C, D, U)) {
                    texto = this.getString(R.string.numerosDuplicados);
                } else {

                    Numero n = new Numero(UM, C, D, U);

                    gm.intento(n);

                    if (gm.Gane()) {
                        getIntent().putExtra("CantidadIntentos", gm.cantidadIntentos());
                        getIntent().putExtra("Numero", gm.darNumero());
                        getIntent().putExtra("Game", true);

                        texto = this.getString(R.string.ganaste) + gm.darNumero() + this.getString(R.string.ganasteII) + gm.cantidadIntentos();
                        if (gm.cantidadIntentos() == 1)
                            texto += this.getString(R.string.ganasteIIIunIntento);
                        else
                            texto += this.getString(R.string.ganasteIIIvariosIntentos);
                        //NO DEBO MODIFICAR ESTE TOAST
                        setResult(RESULT_OK, this.getIntent());
                        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
                        this.finish();//Como encontre el numero se acabo el juego
                    } else {
                        texto += this.getString(R.string.intento) + GameManager.convertirANumero(UM, C, D, U) + this.getString(R.string.intentoII);
                        texto += n.darCantidadBien() + this.getString(R.string.intentoBien) + this.getString(R.string.intentoY);
                        texto += n.darCantidadRegular() + this.getString(R.string.intentoRegular);
                        String ContenidoAdapter = nro + " : " + n.darCantidadBien() + " " + this.getString(R.string.intentoBien) + " " + this.getString(R.string.intentoY) + " " + n.darCantidadRegular() + " " + this.getString(R.string.intentoRegular);
                        //DummyContent.DummyItem i = new DummyContent.DummyItem(Integer.toString(gm.cantidadIntentos()), ContenidoAdapter);
                        //DummyContent.addI(i);
                        DummyContent.DummyItem i = new DummyContent.DummyItem(Integer.toString(n.darNro()), ContenidoAdapter);
                        DummyContent.addI(i);
                        if (NumeroFragment.nroFragment != null)
                            (NumeroFragment.nroFragment).Actualizar();

                    }
                }//Es el Número a adivinar
            }//Hay valores mayores a 1024 y menores a 9876
        }//Hay repetidos


        Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();

    }

    public void volverGameActivity(View view) {

        Toast.makeText(this, this.getString(R.string.darsePorVencido) + gm.darNumero(), Toast.LENGTH_SHORT).show();
        getIntent().putExtra("Game", false);
        setResult(RESULT_OK, this.getIntent());
        this.finish();
    }

    ////////////////////////FUNCIONES////////////////////////////////////////////

    public void bpUM(View view) {
        if (UM < 9) {
            UM++;
            updateColor();
        }
    }//Boton Arriba Unidad de Mil

    public void bpC(View view) {
        if (C < 9) {
            C++;
            updateColor();
        }

    }//Boton Arriba Centena

    public void bpD(View view) {
        if (D < 9) {
            D++;
            updateColor();
        }
    }//Boton Arriba Decena

    public void bpU(View view) {

        if (U < 9) {
            U++;
            updateColor();
        }
    }//Boton Arriba Unidad

    public void bDownUM(View view) {
        if (UM > 1) {
            UM--;
            updateColor();
        }

    }//Boton Abajo Unidad de Mil

    public void bDownC(View view) {
        if (C > 0) {
            C--;
            updateColor();
        }
    }//Boton Abajo Centena

    public void bDownD(View view) {
        if (D > 0) {
            D--;
            updateColor();
        }
    }//Boton Abajo Decena

    public void bDownU(View view) {
        if (U > 0) {
            U--;
            updateColor();
        }
    }//Boton Abajo Unidad

    private void updateColor() {
        //Cargo los TextView
        if (tUM == null)
            tUM = (TextView) this.findViewById(R.id.Txt_UnidadMil);
        tC = (TextView) this.findViewById(R.id.Txt_Centena);
        tD = (TextView) this.findViewById(R.id.Txt_Decena);
        tU = (TextView) this.findViewById(R.id.Txt_Unidad);

        tUM.setText("" + UM);
        tC.setText("" + C);
        tD.setText("" + D);
        tU.setText("" + U);

        tUM.setTextColor(repetidos(UM) ? Color.RED : Color.BLACK);
        tC.setTextColor(repetidos(C) ? Color.RED : Color.BLACK);
        tD.setTextColor(repetidos(D) ? Color.RED : Color.BLACK);
        tU.setTextColor(repetidos(U) ? Color.RED : Color.BLACK);


    }

    private boolean repetidos(int ValorAComparar) {
        int contador = 0;
        if (ValorAComparar == UM)
            contador++;
        if (ValorAComparar == C)
            contador++;
        if (ValorAComparar == D)
            contador++;
        if (ValorAComparar == U)
            contador++;
        return contador >= 2;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_game, container, false);


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position) {
                case 0:
                    return PlaceholderFragment.newInstance(position);
                case 1:
                    return NumeroFragment.newInstance("asdasd", "asdasd");
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:

                    return getString(R.string.title_section2).toUpperCase(l);
                /*case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                */
            }
            return null;
        }
    }

}
