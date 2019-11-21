package com.example.droidbarv1.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.droidbarv1.Model.Data.Producto;
import com.example.droidbarv1.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.MyViewHolder>{
    private LayoutInflater inflaterC;
    private OnItemClickListenner listener;
    private List<Producto> productos;
    private Context context;

    public interface OnItemClickListenner{
        void onItemClick(Producto producto, View v);
    }


    public ProductoAdapter( OnItemClickListenner listener,Context context) {
        this.listener=listener;
        inflaterC=LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflaterC.inflate(R.layout.item_producto,parent,false);
        ProductoAdapter.MyViewHolder vh = new ProductoAdapter.MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.MyViewHolder holder, int position) {
        if(productos != null){
            final Producto current = productos.get(position);
            holder.tvNombreProducto.setText(current.getName());
            holder.tvPrecio.setText(String.valueOf(current.getPrice()));
            holder.cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(current ,v);
                    Log.i("xxx",current.getName());
                }
            });
        }

    }
    public void setProductoList(List<Producto>productoList){
        this.productos=productoList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        int elementos=0;
        if(productos!=null){
            elementos=productos.size();
        }
        return elementos;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreProducto, tvPrecio;
        ConstraintLayout cl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvProductoNom);
            tvPrecio = itemView.findViewById(R.id.tvProductoPrecio);
            cl=itemView.findViewById(R.id.cl);

        }
    }
}
