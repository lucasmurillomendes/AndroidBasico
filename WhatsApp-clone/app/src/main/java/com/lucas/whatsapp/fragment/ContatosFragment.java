package com.lucas.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.lucas.whatsapp.R;
import com.lucas.whatsapp.activity.ChatActivity;
import com.lucas.whatsapp.activity.GrupoActivity;
import com.lucas.whatsapp.adapter.ContatosAdapter;
import com.lucas.whatsapp.config.ConfiguracaoFirebase;
import com.lucas.whatsapp.helper.RecyclerItemClickListener;
import com.lucas.whatsapp.helper.UsuarioFirebase;
import com.lucas.whatsapp.model.Usuario;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private RecyclerView recyclerListaContatos;
    private ContatosAdapter adapter;
    private ArrayList<Usuario> listaContatos = new ArrayList<>();

    private DatabaseReference usuariosRef;
    private FirebaseUser usuarioAtual;

    private ValueEventListener valueEventListenerContatos;


    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //Configurações iniciais
        recyclerListaContatos = view.findViewById(R.id.recyclerViewContatos);
        usuariosRef = ConfiguracaoFirebase.getFirebaseDataBase().child("usuarios");
        usuarioAtual = UsuarioFirebase.getUsuarioAtual();

        //Configurar adapter
        adapter = new ContatosAdapter(listaContatos, getActivity());

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerListaContatos.setLayoutManager(layoutManager);
        recyclerListaContatos.setHasFixedSize(true);
        recyclerListaContatos.setAdapter(adapter);

        //Evento de click no recycler view
        recyclerListaContatos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerListaContatos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Usuario usuarioSelecionado = listaContatos.get(position);
                                boolean cabecalho = usuarioSelecionado.getEmail().isEmpty();

                                if (cabecalho){
                                    Intent i = new Intent(getActivity(), GrupoActivity.class);
                                    startActivity(i);

                                }else {
                                    Intent i = new Intent(getActivity(), ChatActivity.class);
                                    i.putExtra("chatContato", usuarioSelecionado);

                                    startActivity(i);
                                }
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

        /**
         * Define usuário com e-mail vazio
         * em caso de e-mail vazio o usuário será utilizado como
         * cabeçalho, exibindo novo grupo
         */

        Usuario itemGrupo = new Usuario();
        itemGrupo.setNome("Novo Grupo");
        itemGrupo.setEmail("");

        listaContatos.add(itemGrupo);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarContatos();
    }

    @Override
    public void onStop() {
        super.onStop();
        usuariosRef.removeEventListener(valueEventListenerContatos);
        listaContatos = new ArrayList<>();
    }

    public void recuperarContatos (){

        valueEventListenerContatos = usuariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Usuario usuario = dados.getValue(Usuario.class);

                    String emailUsuario =  usuarioAtual.getEmail();

                    if (!emailUsuario.equals(usuario.getEmail())){
                        listaContatos.add(usuario);
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
