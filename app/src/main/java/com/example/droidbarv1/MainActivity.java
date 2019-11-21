package com.example.droidbarv1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.droidbarv1.Model.Data.Factura;
import com.example.droidbarv1.View.MainViewModel;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="xxx";

    public static List<Factura> facturas;
    private ArrayList<Integer> mesasLLenas;

    private MainViewModel viewModel;

    private long idEmpleado=0;
    private int idMesa;

    private Intent i;

    private ImageView iv;
    private View fragmento;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        //pintamos mesas llenas o vacias segun si tienen facturas abiertas->Empleado de cierre=4
        System.out.println("Facturas Actuales "+facturas.toString());
        putTables();
        System.out.println("MESAS LLENAS "+mesasLLenas.toString());
    }
    @Override
    public void onBackPressed() {
        //Redefinido para evitar volver al login
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel =  ViewModelProviders.of(this).get(MainViewModel.class);
        initNavigationDrawer();
        //Recogemos el id del empleado logeado
        this.idEmpleado=getIntent().getLongExtra("idEmpleado",0);
        //pintamos mesas llenas o vacias segun si tienen facturas abiertas->Empleado de cierre=4
        putTables();
        //Fragmento loafing screen
        fragmento = findViewById(R.id.fragmentLoadingMain);
        fragmento.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void putTables() {
        //Array para saber los numeros de las mesas llenas
        mesasLLenas=new ArrayList<>();
        for (Factura f:facturas) {
            if(f.getId_employee_finish()==4){
                iv=findViewById(R.id.clMain).findViewWithTag(String.valueOf(f.getTable()));
                mesasLLenas.add(f.getTable());
                if(f.getTable()<10){
                    iv.setImageDrawable(getDrawable(R.drawable.table));
                }else{
                    iv.setImageDrawable(getDrawable(R.drawable.stool));
                }
            }else{
                iv=findViewById(R.id.clMain).findViewWithTag(String.valueOf(f.getTable()));
                if(f.getTable()<10){
                    iv.setImageDrawable(getDrawable(R.drawable.table_empty));
                }else{
                    iv.setImageDrawable(getDrawable(R.drawable.stool_empty));
                }
            }
        }
    }

    private void initNavigationDrawer() {
        //Crear toggle para abrir el navigation drawer
        DrawerLayout drawerLayout =findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(
                this,drawerLayout,null,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView= findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case (R.id.menu_history):
                        //intent history
                    case (R.id.menu_logout):
                        //intent logOut
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
                return false;
            }
        });
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v) {
        //Metodo onclick para los ImageView de las mesas, asignado en layout para evitar codigo
        idMesa =Integer.parseInt(v.getTag().toString());
        //Creamos intent para lanzar la sig actividad
        i = new Intent(this,Ticket.class).putExtra("idMesa",idMesa).putExtra("idEmpleado",idEmpleado);
        //Metodo de creacion de factura, si la mesa esta vacia es decir no esta en el array de mesasLLenas
        if(!mesasLLenas.contains(idMesa)){
            creaFactura(idMesa);
            new LoadViewTask().execute();
        }else {
            //Metodo para poner la factura correspondiente en la activity de Ticket
            obtenFactura();
            //Lanzamos activity de ticket, con un boolean indicando que no se crea ticket
            startActivity(i.putExtra("yaExiste",true));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void creaFactura(int idMesa) {
        //Creamos nueva Factura
            Factura newF = new Factura();
            newF.setTable(idMesa);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            newF.setStart_time(LocalDateTime.now().format(formatter));
            newF.setId_employee_start(idEmpleado);
            System.out.println(newF.toString());
            viewModel.addFactura(newF);
            viewModel.getFacturaList().observe(this, new Observer<List<Factura>>() {
                @Override
                public void onChanged(List<Factura> facturas) {
                    MainActivity.facturas=facturas;
                    Ticket.facturas=facturas;
                }
            });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void obtenFactura() {
        for (Factura f:facturas) {
            if(f.getTable()==idMesa&&f.getId_employee_finish()==4){
                Ticket.facturaActual = f;
            }
        }
    }

    private class LoadViewTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fragmento.setVisibility(View.VISIBLE);

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... params) {
            synchronized (this){
                try {
                    this.wait(3000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                obtenFactura();
                startActivity(i);

            }
            return null;
        }
        protected void onPostExecute(Void result){
            fragmento.setVisibility(View.INVISIBLE);
        }
    }

}
