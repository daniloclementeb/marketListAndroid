package com.clemente.danilo.androiddevelopment.adapter;

import com.clemente.danilo.androiddevelopment.model.Compra;
import com.clemente.danilo.androiddevelopment.model.ListaDeCompra;


/**
 * Created by logonrm on 26/06/2017.
 */

public interface OnListaItemClickListener {
    void onItemClick(ListaDeCompra item);
    void onDeleteClick(ListaDeCompra item);
    void onShareItemClick(ListaDeCompra item);
    void onEditItemClick(ListaDeCompra item);

}
