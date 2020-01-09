package com.lucas.whatsapp.activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.lucas.whatsapp.R;
import com.lucas.whatsapp.adapter.ContatosAdapter;
import com.lucas.whatsapp.adapter.GrupoSelecionadoAdapter;
import com.lucas.whatsapp.config.ConfiguracaoFirebase;
import com.lucas.whatsapp.helper.RecyclerItemClickListener;
import com.lucas.whatsapp.helper.UsuarioFirebase;
import com.lucas.whatsapp.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GrupoActivity extends AppCompatActivity {

    private RecyclerView recyclerMembrosSelecionados, recyclerMembros;

    private ContatosAdapter contatosAdapter;
    private GrupoSelecionadoAdapter grupoSelecionadoAdapter;

    private List<Usuario> listaMembros = new ArrayList<>();
    private List<Usuario> listaMembrosSelecionados = new ArrayList<>();

    private DatabaseReference usuariosRef;
    private FirebaseUser usuarioAtual;
    private ValueEventListener valueEventListenerMembros;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Grupo");

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurações iniciais
        recyclerMembros =findViewById(R.id.recyclerMembros);
        recyclerMembrosSelecionados =findViewById(R.id.recyclerMembrosSelecionados);
        usuariosRef = ConfiguracaoFirebase.getFirebaseDataBase().child("usuarios");
        usuarioAtual = UsuarioFirebase.getUsuarioAtual();

        //Configura o adapter contatos
        contatosAdapter = new ContatosAdapter(listaMembros, getApplicationContext());


        //Cnfigura Recycler view Contatos
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMembros.setLayoutManager(layoutManager);
        recyclerMembros.setHasFixedSize(true);
        recyclerMembros.setAdapter(contatosAdapter);

        recyclerMembros.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerMembros,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Usuario usuarioSelecionado= listaMembros.get(position);

                                //Remover usuario da lista de membros
                                listaMembros.remove(usuarioSelecionado);
                                contatosAdapter.notifyDataSetChanged();

                                //Adiciona o usuario removido na nova lista de seleção
                                listaMembrosSelecionados.add(usuarioSelecionado);
                                grupoSelecionadoAdapter.notifyDataSetChanged();

                                //Atualiza numero de contatos selecionados
                                atualizarMembrosToolbar();


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        //Configura o adapter grupo selecionado
        grupoSelecionadoAdapter = new GrupoSelecionadoAdapter(listaMembrosSelecionados, getApplicationContext());
        //Cnfigura Recycler view contato selecionado
        RecyclerView.LayoutManager  layoutManagerHorizontal = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        recyclerMembrosSelecionados.setLayoutManager(layoutManagerHorizontal);
        recyclerMembrosSelecionados.setHasFixedSize(true);
        recyclerMembrosSelecionados.setAdapter(grupoSelecionadoAdapter);

        recyclerMembrosSelecionados.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerMembrosSelecionados,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Usuario usuarioSelecionado = listaMembrosSelecionados.get(position);

                                //Remover da listagem de selecionados
                                listaMembrosSelecionados.remove(usuarioSelecionado);
                                grupoSelecionadoAdapter.notifyDataSetChanged();

                                //voltando pra lista de contatos não selecionados
                                listaMembros.add(usuarioSelecionado);
                                contatosAdapter.notifyDataSetChanged();

                                //Atualiza numero de contatos selecionados
                                atualizarMembrosToolbar();

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarContatos();
    }

    @Override
    public void onStop() {
        super.onStop();
        usuariosRef.removeEventListener(valueEventListenerMembros);
        listaMembros = new ArrayList<>();
    }

    public void recuperarContatos (){

        valueEventListenerMembros = usuariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Usuario usuario = dados.getValue(Usuario.class);

                    String emailUsuario =  usuarioAtual.getEmail();

                    if (!emailUsuario.equals(usuario.getEmail())){
                        listaMembros.add(usuario);
                    }
                }

                contatosAdapter.notifyDataSetChanged();

                //Atualiza numero de contatos selecionados
                atualizarMembrosToolbar();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void atualizarMembrosToolbar(){

        int totalSelecionados = listaMembrosSelecionados.size();
        int totalmembros = listaMembros.size() + totalSelecionados;

        toolbar.setSubtitle(totalSelecionados +" de " + totalmembros + " selecionados" );

    }

}
