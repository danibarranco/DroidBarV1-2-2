package com.example.droidbarv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.droidbarv1.Model.Data.Comanda;
import com.example.droidbarv1.Model.Data.Empleado;
import com.example.droidbarv1.Model.Data.Factura;
import com.example.droidbarv1.Model.Data.Producto;
import com.example.droidbarv1.View.MainViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private Button btSing;
    private TextView tvUsu,tvPass;
    private TextInputLayout textInputLayout,textInputLayout2;
    private MainViewModel viewModel;
    private static List<Empleado>empleados;
    private long idEmpleado=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        cargaDatos();

    }

    private void cargaDatos() {
        viewModel =  ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getEmpleadosList().observe(this, new Observer<List<Empleado>>() {
            @Override
            public void onChanged(List<Empleado> empleados) {
                LoginActivity.empleados=empleados;
            }
        });
        viewModel.getFacturaList().observe(this, new Observer<List<Factura>>() {
            @Override
            public void onChanged(List<Factura> facturas) {
                MainActivity.facturas=facturas;
            }
        });
        viewModel.getComandaList().observe(this, new Observer<List<Comanda>>() {
            @Override
            public void onChanged(List<Comanda> comandas) {
                Ticket.comandas=comandas;
            }
        });
        viewModel.getProductoList().observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                OrdenaComanda.productos=productos;
            }
        });
    }


    private void initComponents() {
        textInputLayout= findViewById(R.id.textInputLayout);
        textInputLayout2=findViewById(R.id.textInputLayout2);
        tvUsu=findViewById(R.id.tvUsu);
        tvPass=findViewById(R.id.tvPass);
        btSing=findViewById(R.id.btSing);
        initEvents();


    }

    private void initEvents() {
        tvUsu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout2.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btSing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validaUsu(tvUsu.getText().toString())&&validaPass(tvPass.getText().toString())){
                    InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("idEmpleado",idEmpleado));
                }else {
                    if(tvPass.getText().toString().equalsIgnoreCase("")) {
                        textInputLayout2.setError("Contraseña no puede estar vacia");
                    }
                    else{
                        textInputLayout.setError(getText(R.string.errorUsu));
                    }
                }
            }

        });
    }

    private boolean validaUsu(String usu) {
        for (Empleado e:empleados) {
            if (e.getLogin().equalsIgnoreCase(usu)){
                idEmpleado=e.getId();
                return true;
            }
        }
        return false;
    }


    private boolean validaPass(String pass) {
        //controlar contraseña
        for (Empleado e:empleados
        ) {
            if (e.getPassword().equalsIgnoreCase(pass)){
                return true;
            }
        }
        return false;

    }
}
