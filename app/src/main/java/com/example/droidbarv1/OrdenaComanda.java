package com.example.droidbarv1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.droidbarv1.Model.Data.Comanda;
import com.example.droidbarv1.Model.Data.Producto;
import com.example.droidbarv1.View.MainViewModel;
import com.example.droidbarv1.View.ProductoAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class OrdenaComanda extends AppCompatActivity {

    private int idMesa;
    private long idFactura,idEmpleado;
    public static List<Producto> productos;
    public static ArrayList<Producto> productosFilter;
    private RecyclerView rvProductos;
    private MainViewModel viewModel;
    private GridLayoutManager layoutManager;
    private Button btComida, btBebida;
    private ProductoAdapter adapter;
    private int destino = 1;
    private View fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordena_comanda);

        idMesa = getIntent().getIntExtra("idMesa", 0);
        idFactura = getIntent().getLongExtra("idFactura", 0);
        idEmpleado = getIntent().getLongExtra("idEmpleado", 4);

        adapter = new ProductoAdapter(new ProductoAdapter.OnItemClickListenner() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(Producto producto, View v) {
                //Creamos comanda con producto pinchado
                creaComanda(producto);
                Snackbar.make(v, producto.getName(), Snackbar.LENGTH_LONG).show();

            }

        },this);

        viewModel =  ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getProductoList().observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                productosFilter=new ArrayList<>();
                for (Producto p:productos) {
                    if(p.getTarget()==destino){
                        productosFilter.add(p);
                    }
                }
                adapter.setProductoList(productosFilter);
            }
        });

        initComponents();
    }

    @Override
    public void onBackPressed() {
        new LoadViewTask().execute();
    }

    private void creaComanda(Producto producto) {
        Comanda newC = new Comanda();
        newC.setUnits(1);
        newC.setId_employee(idEmpleado);
        newC.setId_ticket(idFactura);
        newC.setId_product(producto.getId());
        newC.setPrice(producto.getPrice());
        newC.setServed(0);
        Log.v("product",newC.toString());
        viewModel.addComanda(newC);
    }


    private void initComponents() {

        rvProductos = findViewById(R.id.rvProductos);
        layoutManager = new GridLayoutManager(this, 3);
        rvProductos.setLayoutManager(layoutManager);
        rvProductos.setAdapter(adapter);



        btComida = findViewById(R.id.btComida);
        btComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = 1;
                viewModel.getProductoList().observe(OrdenaComanda.this, new Observer<List<Producto>>() {
                    @Override
                    public void onChanged(List<Producto> productos) {
                        ArrayList<Producto> comida = new ArrayList<>();
                        for (Producto p : productos
                        ) {
                            if (p.getTarget() == destino) {
                                comida.add(p);
                            }
                        }
                        OrdenaComanda.productos = comida;
                        adapter.setProductoList(comida);
                    }
                });
            }
        });

        btBebida = findViewById(R.id.btBebida);
        btBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destino = 0;
                System.out.println("Bebida "+destino);
                viewModel.getProductoList().observe(OrdenaComanda.this, new Observer<List<Producto>>() {
                    @Override
                    public void onChanged(List<Producto> productos) {
                        ArrayList<Producto> bebida=new ArrayList<>();
                        for (Producto p:productos
                        ) {
                            if(p.getTarget()==destino){
                                bebida.add(p);
                            }
                        }
                        OrdenaComanda.productos=bebida;
                        adapter.setProductoList(bebida);
                    }
                });
            }
        });

        fragment = findViewById(R.id.fragmentLoadingComanda);
        fragment.setVisibility(View.INVISIBLE);
    }

    private class LoadViewTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fragment.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {
            synchronized (this){
                try {
                    this.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
            return null;
        }
        protected void onPostExecute(Void result){
            fragment.setVisibility(View.INVISIBLE);
        }
    }


}
