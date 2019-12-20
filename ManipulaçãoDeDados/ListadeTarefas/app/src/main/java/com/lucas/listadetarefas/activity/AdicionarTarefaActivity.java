package com.lucas.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lucas.listadetarefas.R;
import com.lucas.listadetarefas.helper.TarefaDAO;
import com.lucas.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.editTarefa);

        //Recuperar tarefa caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Setar o texto da tarefa na caixa de texto
        if (tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemSalvar:
                //Executa ação para salvar o item
                TarefaDAO dao = new TarefaDAO(getApplicationContext());
                String nomeTarefa = editTarefa.getText().toString();

                if (tarefaAtual != null){

                    if (!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());
                        if (dao.atualizar( tarefa )){
                            finish();
                            Toast.makeText(getApplicationContext(), "Tarefa alterada com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Informe uma tarefa válida!",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //Caso tarefa atual seja nula estamos salvando um novo registro
                    if (!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        if ( dao.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(), "Tarefa salva com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Informe uma tarefa válida!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
