package com.clemente.danilo.androiddevelopment.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clemente.danilo.androiddevelopment.activity.R;
import com.clemente.danilo.androiddevelopment.model.Compra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 07/08/2017.
 */

public class ComprasAdapter extends RecyclerView.Adapter<ComprasAdapter.CompraViewHolder> {
    private ArrayList<Compra> compras;
    private OnItemClickListener listener;

    public ComprasAdapter(ArrayList<Compra> items, OnItemClickListener onItemClickListener) {
        this.compras = items;
        this.listener = onItemClickListener;
    }

    @Override
    public CompraViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View meuLayout = inflater.inflate(R.layout.compra_row, viewGroup, false);
        return new CompraViewHolder(meuLayout);
    }

    @Override
    public void onBindViewHolder(final CompraViewHolder holder, final int position) {
        holder.tvNomeItem.setText(compras.get(position).getNomeItem());
        holder.tvPreco.setText(String.valueOf(compras.get(position).getPreco()));
        holder.tvQuantidade.setText(String.valueOf(compras.get(position).getQuantidade()));
        if (compras.get(position).getFlagComprado() > 0) {
            holder.ivBought.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listener.onItemClick(compras.get(position));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                v.setBackgroundColor(Color.parseColor("#666666"));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(Color.parseColor("#ffffff"));
                        holder.ivDeleteItem.setVisibility(View.VISIBLE);
                        holder.ivEditItem.setVisibility(View.INVISIBLE);
                        onBindViewHolder(holder, position);
                    }
                });
                holder.ivDeleteItem.setVisibility(View.INVISIBLE);
                holder.ivEditItem.setVisibility(View.VISIBLE);
                return true;

            }
        });
        holder.ivDeleteItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onDeleteClick(compras.get(position));
            }
        });
        holder.ivEditItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onEditItemClick(compras.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (compras != null)
            return compras.size();
        return 0;
    }

    public class CompraViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNomeItem;
        public TextView tvPreco;
        public TextView tvQuantidade;
        public ImageView ivBought;
        public ImageView ivDeleteItem;
        public ImageView ivEditItem;

        public CompraViewHolder(final View itemView) {
            super(itemView);
            ivBought = (ImageView) itemView.findViewById(R.id.ivBought);
            tvNomeItem = (TextView) itemView.findViewById(R.id.tvNomeItem);
            tvPreco = (TextView) itemView.findViewById(R.id.tvPreco);
            tvQuantidade = (TextView) itemView.findViewById(R.id.tvQuantidade);
            ivDeleteItem = (ImageView) itemView.findViewById(R.id.deleteItem);
            ivEditItem = (ImageView) itemView.findViewById(R.id.ivEditItem);
        }
    }

    public void update(List<Compra> compras) {
        this.compras = (ArrayList<Compra>) compras;
        notifyDataSetChanged();
    }
}
