package com.clemente.danilo.androiddevelopment.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clemente.danilo.androiddevelopment.activity.ListasActivity;
import com.clemente.danilo.androiddevelopment.activity.R;
import com.clemente.danilo.androiddevelopment.model.Compra;
import com.clemente.danilo.androiddevelopment.model.ListaDeCompra;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 07/08/2017.
 */

public class ListasAdapter extends RecyclerView.Adapter<ListasAdapter.ListaViewHolder>  {
    private ArrayList<ListaDeCompra> listaDeCompras;
    private OnListaItemClickListener listener;

    public ListasAdapter(ArrayList<ListaDeCompra> items, OnListaItemClickListener onItemClickListener) {
        this.listaDeCompras = items;
        this.listener = onItemClickListener;
    }

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View meuLayout = inflater.inflate(R.layout.lista_row, viewGroup, false);
        return new ListaViewHolder(meuLayout);
    }

    @Override
    public void onBindViewHolder(final ListaViewHolder holder, final int position) {
        holder.tvNomeLista.setText(listaDeCompras.get(position).getNome());
        holder.tvCont.setText(String.valueOf(position+1));
        holder.tvQuantidadeLista.setText(String.valueOf(listaDeCompras.get(position).getItems().size()));
        if (listaDeCompras.get(position).getAlerta() > 0) {
            holder.ivAlerta.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                listener.onItemClick(listaDeCompras.get(position));
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
                        holder.deleteLista.setVisibility(View.VISIBLE);
                        holder.shareLista.setVisibility(View.INVISIBLE);
                        holder.ivEdit.setVisibility(View.INVISIBLE);
                        onBindViewHolder(holder, position);
                    }
                });
                holder.ivAlerta.setVisibility(View.INVISIBLE);
                holder.deleteLista.setVisibility(View.INVISIBLE);
                holder.shareLista.setVisibility(View.VISIBLE);
                holder.ivEdit.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.deleteLista.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                listener.onDeleteClick(listaDeCompras.get(position));
            }
        });
        holder.shareLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShareItemClick(listaDeCompras.get(position));
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditItemClick(listaDeCompras.get(position));
            }
        });
    }



    @Override
    public int getItemCount() {
        if (listaDeCompras != null)
            return listaDeCompras.size();
        return 0;
    }

    public class ListaViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCont;
        public TextView tvNomeLista;
        public TextView tvQuantidadeLista;
        public ImageView deleteLista;
        public ImageView ivAlerta;
        public ImageView ivEdit;
        public ImageView shareLista;


        public ListaViewHolder(final View itemView) {
            super(itemView);
            tvCont = (TextView) itemView.findViewById(R.id.tvCont);
            tvNomeLista = (TextView) itemView.findViewById(R.id.tvNomeLista);
            tvQuantidadeLista = (TextView) itemView.findViewById(R.id.tvQuantidadeLista);
            deleteLista = (ImageView) itemView.findViewById(R.id.deleteLista);
            ivAlerta = (ImageView) itemView.findViewById(R.id.ivAlerta);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);
            shareLista = (ImageView) itemView.findViewById(R.id.shareLista);
        }
    }
    public void update(List<ListaDeCompra> listaDeCompras) {
        this.listaDeCompras = (ArrayList<ListaDeCompra>) listaDeCompras;
        notifyDataSetChanged();
    }
}
