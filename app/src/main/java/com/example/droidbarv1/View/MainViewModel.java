package com.example.droidbarv1.View;

import android.app.Application;

import com.example.droidbarv1.Model.Data.Comanda;
import com.example.droidbarv1.Model.Data.Empleado;
import com.example.droidbarv1.Model.Data.Factura;
import com.example.droidbarv1.Model.Data.Producto;
import com.example.droidbarv1.Model.Repository;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class MainViewModel extends AndroidViewModel {
    private Repository repository;

    public MainViewModel(@NonNull Application application){
        super(application);
        repository= new Repository();

    }

    public MutableLiveData<List<Empleado>>getEmpleadosList(){
        return repository.getLiveEmpleadoList();
    }
    public MutableLiveData<List<Factura>>getFacturaList(){
        return repository.getLiveFacturaList();
    }
    public MutableLiveData<List<Comanda>> getComandaList(){
        return repository.getLiveComandaList();
    }
    public MutableLiveData<List<Producto>>getProductoList(){
        return repository.getLiveProductoList();
    }
    public void addFactura(Factura factura){
        repository.addFactura(factura);
    }

    public void setUrl(String url) {
        repository.setUrl(url);
    }

    public void updateFactura(Factura finF) {
        repository.updateFactura(finF);
    }

    public void addComanda(Comanda comanda){
        repository.addComanda(comanda);
    }

    public void updateComanda(Comanda finC){
        repository.updateComanda(finC);
    }
}
