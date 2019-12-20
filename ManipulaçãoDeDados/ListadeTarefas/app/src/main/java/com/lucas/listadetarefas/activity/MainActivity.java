package com.lucas.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lucas.listadetarefas.R;
import com.lucas.listadetarefas.adapter.TarefaAdapter;
import com.lucas.listadetarefas.helper.DbHelper;
import com.lucas.listadetarefas.helper.RecyclerItemClickListener;
import com.lucas.listadetarefas.helper.TarefaDAO;
import com.lucas.listadetarefas.model.Tarefa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurando REcycler View
        recyclerView = findViewById(R.id.recyclerView);

        //Inserindo dados na tabela tarefas
        DbHelper db = new DbHelper(getApplicationContext());


        //Folating Action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity( intent );

            }
        });

        //Adicionando Evento de click
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                //Recuperando a tarefa para edição
                                tarefaSelecionada = listaTarefas.get(position);

                                // Enviando tarefa para a outra activity
                                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                                startActivity( intent );

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recuperar a tarefa selecionada
                                tarefaSelecionada = listaTarefas.get(position);


                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                //Configura titulo e mensagem
                                dialog.setTitle("Exclusão de tarefa");
                                dialog.setMessage("Deseja realmente excluir '" + tarefaSelecionada.getNomeTarefa() + "' ?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    TarefaDAO dao = new TarefaDAO(getApplicationContext());
                                    if (dao.deletar(tarefaSelecionada)){

                                        carregarListaTarefas();
                                        Toast.makeText(getApplicationContext(), "Tarefa excluída com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Erro ao excluir tarefa!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Exibir Dialog
                                dialog.create();
                                dialog.show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    public void carregarListaTarefas(){

        //Listar Tarrefas
        TarefaDAO dao = new TarefaDAO(getApplicationContext());

        listaTarefas = dao.listar();

        //Exibe a lista

        //configurar adapter
        tarefaAdapter = new TarefaAdapter( listaTarefas );

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter( tarefaAdapter );

    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();

    }

}
