package com.lucas.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";


    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Chamado para criar as tabelas (Uma unica vez)
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL ); ";

        try{

            db.execSQL(sql);
            Log.i("INFO DB ", "Sucesso ao criar tabela ");
        }catch (Exception e){
            Log.i("INFO DB ", "Erro ao criar tabela " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Fazer um upgrade no banco, aumentar tabelas etc


    }
}
