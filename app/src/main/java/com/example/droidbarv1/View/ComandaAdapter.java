package com.example.droidbarv1.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.droidbarv1.Model.Data.Comanda;
import com.example.droidbarv1.Model.Data.Producto;
import com.example.droidbarv1.OrdenaComanda;
import com.example.droidbarv1.R;
import com.example.droidbarv1.Ticket;

import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ComandaAdapter extends RecyclerView.Adapter<ComandaAdapter.MyViewHolder> {

    private LayoutInflater inflaterC;
    private List<Comanda> misComandas;
    private ComandaAdapter.OnItemClickListenner listener;
    private List<Producto> productos = OrdenaComanda.productos;
    private Context context;

    public interface OnItemClickListenner{
        void onItemClick(Comanda comanda, View v);
    }

    public ComandaAdapter(ComandaAdapter.OnItemClickListenner listener, Context context) {
        this.listener=listener;
        inflaterC=LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ComandaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflaterC.inflate(R.layout.item_comanda,parent,false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ComandaAdapter.MyViewHolder holder, int position) {
        String nombreP = "";
        int unidades;

        boolean encontrado = false;
        if(misComandas != null){
            final Comanda current = misComandas.get(position);
            Log.v("xxxRecycler",current.toString());
            for (Producto p:productos) {
                if(!encontrado&&current.getId_product() == p.getId()){
                    nombreP = p.getName();
                    encontrado = true;
                }
            }
            unidades = current.getUnits();
            holder.tvNombreProducto.setText(nombreP);
            holder.tvNumeroUnidades.setText(String.valueOf(unidades));
            holder.ivMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(current,v);
                   /* current.setUnits(current.getUnits()+1);
                    //actualizar en base de datos.
                    */
                }
            });
            holder.ivMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(current,v);
                    /*if (current.getUnits() == 1){
                        //No se pueden quitar mas Poner sanck bar que diga desliza comanda hacia la izq para borrar.
                    }else{
                        current.setUnits(current.getUnits()-1);
                        notifyDataSetChanged();
                    }*/
                }
            });
        }

    }
    public void setComandaList(List<Comanda>comandaList){
        this.misComandas=comandaList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        int elementos=0;
        if(misComandas!=null){
            elementos=misComandas.size();
        }
        return elementos;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvProducto, tvNombreProducto, tvUnidades, tvNumeroUnidades;
        ImageView ivMas, ivMenos;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvUnidades = itemView.findViewById(R.id.tvUnidades);
            tvNumeroUnidades = itemView.findViewById(R.id.tvNumeroUnidades);
            ivMas = itemView.findViewById(R.id.ivMas);
            ivMenos = itemView.findViewById(R.id.ivMenos);
        }
    }
}
