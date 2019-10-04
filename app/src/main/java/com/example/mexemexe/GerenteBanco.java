package com.example.mexemexe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GerenteBanco extends SQLiteOpenHelper {

    public static String NomeTabela = "MAPA";

    public static String CREATE_TABLE = "CREATE TABLE "
                                        + NomeTabela
                                        + " (MOVIMENTO TEXT, DT TEXT);";

    public GerenteBanco(Context ctx) {
        super(ctx, "MOVIMENTOSDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insereMapa(Mapa mapa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("MOVIMENTO", mapa.movimento);
        //registro.put("DT", mapa.momento.toString());

        db.insert(NomeTabela, null, registro);
    }

    public void limparDados() {

        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM " + NomeTabela + ";";

        db.execSQL(sql);
    }

    public ArrayList<Mapa> listarMapas(ArrayList<Mapa> listaPosicoes) {

        listaPosicoes.clear();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor;

        String[] colunas = new String[1];

        colunas[0] = "MOVIMENTO";
        //colunas[1] = "DT";

        cursor = db.query(NomeTabela,colunas,null,null,null,null,null);

        boolean proximo = false;

        if(cursor == null){
            System.out.print("Erro de banco");
        }else{
            proximo = cursor.moveToFirst();
        }
        while(proximo){

            String movimento = cursor.getString(0);

            //String dataStr = cursor.getString(1);

            //Date data = SimpleDateFormat.parse(dataStr);

            Mapa mapa = new Mapa(movimento);
            listaPosicoes.add(mapa);
            proximo = cursor.moveToNext();
        }
        db.close();

        Collections.reverse(listaPosicoes);

        return listaPosicoes;
    }
}
