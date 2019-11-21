 package com.example.droidbarv1.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* public class MainViewAdapter extends RecyclerView.Adapter <MainViewAdapter.ItemHolder>{
   private LayoutInflater inflater;
    private List<Type> typeList;


    public MainViewAdapter(Context context) {
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= inflater.inflate(R.layout.item,parent,false);

        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if(typeList !=null){
            Type curent = typeList.get(position);
            holder.tvItem.setText(curent.toString());
        }else {
            holder.tvItem.setText("No user available");
        }
    }
    public void setData(List<Type>typeList){
        this.typeList =typeList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int elements=0;
        if(typeList !=null){
            elements= typeList.size();
        }
        return elements;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=itemView.findViewById(R.id.tvItem);
        }
    }
}*/
