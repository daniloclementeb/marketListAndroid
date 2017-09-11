package com.clemente.danilo.androiddevelopment.model;

/**
 * Created by HP on 07/08/2017.
 */

public class Compra {

    /**"CREATE TABLE ITEM_COMPRA (" +
     "ID_LISTA NUMBER NOT NULL, " +
     "NOME_ITEM TEXT NOT NULL, " +
     "FLAG_COMPRADO NUMBER NOT NULL, " +
     "PRECO NUMBER, " +
     "QUANTIDADE NUMBER NOT NULL);");
     **/
    private int idLista;
    private int rowid;
    private String nomeItem;
    private int flagComprado;
    private double preco;
    private int quantidade;

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int id_lista) {
        this.idLista = id_lista;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public int getFlagComprado() {
        return flagComprado;
    }

    public void setFlagComprado(int flagComprado) {
        this.flagComprado = flagComprado;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
