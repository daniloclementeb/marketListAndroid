package com.clemente.danilo.androiddevelopment.adapter;

import com.clemente.danilo.androiddevelopment.model.Compra;


/**
 * Created by logonrm on 26/06/2017.
 */

public interface OnItemClickListener {
    void onItemClick(Compra item);
    void onDeleteClick(Compra item);
    void onEditItemClick(Compra item);
}
