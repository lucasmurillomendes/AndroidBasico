package com.lucas.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.lucas.whatsapp.R;
import com.lucas.whatsapp.activity.ChatActivity;
import com.lucas.whatsapp.adapter.ConversasAdapter;
import com.lucas.whatsapp.config.ConfiguracaoFirebase;
import com.lucas.whatsapp.helper.RecyclerItemClickListener;
import com.lucas.whatsapp.helper.UsuarioFirebase;
import com.lucas.whatsapp.model.Conversa;
import com.lucas.whatsapp.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private RecyclerView recyclerViewConversas;
    private List<Conversa> listaConversas = new ArrayList<>();
    private ConversasAdapter adapter;

    private DatabaseReference database;
    private DatabaseReference conversasRef;

    private ChildEventListener childEventListenerConversas;



    public ConversasFragment() {    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        recyclerViewConversas = view.findViewById(R.id.recyclerConversas);

        //Configurar o adapter
        adapter = new ConversasAdapter(listaConversas, getActivity());

        //configurar o recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewConversas.setLayoutManager(layoutManager);
        recyclerViewConversas.setHasFixedSize(true);
        recyclerViewConversas.setAdapter(adapter);

        //Configura evento de click
        recyclerViewConversas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerViewConversas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                               Conversa conversaSelecionada =listaConversas.get(position);

                                Intent i = new Intent(getActivity(), ChatActivity.class);
                                i.putExtra("chatContato", conversaSelecionada.getUsuarioExibicao());

                                startActivity(i);
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

        //Configura conversaRef
        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        database = ConfiguracaoFirebase.getFirebaseDataBase();
        conversasRef = database.child("conversas")
                .child(identificadorUsuario);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarConversas();
    }

    @Override
    public void onStop() {
        super.onStop();
        conversasRef.removeEventListener(childEventListenerConversas);
        //Evita duplicar conversas na volta do botão voltar
        listaConversas = new ArrayList<>();
    }

    public void pesquisarConversas(String texto){

        List<Conversa> listaConversaBusca = new ArrayList<>();

        for (Conversa conversa: listaConversas){
            String nome = conversa.getUsuarioExibicao().getNome().toLowerCase();
            String ultimamensagem = conversa.getUltimaMensagem().toLowerCase();
            if (nome.contains(texto) || ultimamensagem.contains(texto)){
                listaConversaBusca.add(conversa);
            }
            adapter = new ConversasAdapter(listaConversaBusca, getActivity());
            recyclerViewConversas.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    public void recarregarConversas(){
        adapter = new ConversasAdapter(listaConversas, getActivity());
        recyclerViewConversas.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recuperarConversas(){

        childEventListenerConversas = conversasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Recuperando conversas
                Conversa conversa = dataSnapshot.getValue(Conversa.class);
                        listaConversas.add(conversa);
                        adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
