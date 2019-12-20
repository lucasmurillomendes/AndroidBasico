package com.lucas.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lucas.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements DAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context ) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le= db.getReadableDatabase();

    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{

            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv );
            Log.i("INFO ", "Tarefa salva com sucesso");

        }catch (Exception e){
            Log.i("INFO ", "Erro ao salvar registro" + e.getMessage());
            return  false;

        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{

            String args[] = {tarefa.getId().toString()};

            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO ", "Tarefa atualizada com sucesso");

        }catch (Exception e){
            Log.i("INFO ", "Erro ao atualizar registro" + e.getMessage());
            return  false;

        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try{

            String args[] = {tarefa.getId().toString()};

            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO ", "Tarefa excluída com sucesso");

        }catch (Exception e){
            Log.i("INFO ", "Erro ao excluir registro" + e.getMessage());
            return  false;

        }
        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ; ";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){

            Long id = c.getLong( c.getColumnIndex("id") );
            String nome = c.getString( c.getColumnIndex("nome") );

            Tarefa tarefa = new Tarefa();
            tarefa.setId(id);
            tarefa.setNomeTarefa(nome);

            tarefas.add(tarefa);
        }

        return tarefas;
    }
}
