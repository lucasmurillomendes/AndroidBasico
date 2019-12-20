package com.lucas.listadetarefas.helper;

import com.lucas.listadetarefas.model.Tarefa;

import java.util.List;

public interface DAO {

    public boolean salvar (Tarefa tarefa);
    public boolean atualizar (Tarefa tarefa);
    public boolean deletar (Tarefa tarefa);
    public List<Tarefa> listar();

}
