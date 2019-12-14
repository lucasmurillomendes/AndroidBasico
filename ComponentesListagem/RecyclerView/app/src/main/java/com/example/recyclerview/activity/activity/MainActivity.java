package com.example.recyclerview.activity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recyclerview.R;
import com.example.recyclerview.activity.RecyclerItemClickListener;
import com.example.recyclerview.activity.adapter.Adapter;
import com.example.recyclerview.activity.model.Filme;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Filme> filmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        //Listagem de filmes
        this.criarFilmes();

        //Configurando o adapter
        Adapter adapter = new Adapter( filmes );


        //Configurar Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

        //Evento de click na lista
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Filme filme = filmes.get(position); //pega a posição clicada e retorna objeto
                                Toast.makeText(getApplicationContext(),
                                        "Item pressionado: " + filme.getTituloFilme(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Filme filme = filmes.get(position);//pega a posição clicada e retorna objeto
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Click Longo: " + filme.getTituloFilme(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    public void criarFilmes(){
        Filme filme = new Filme("Homem Aranha", "Aventura", "2017");
        this.filmes.add(filme);

        filme = new Filme("Mulher Maravilha", "Fantasia", "2018");
        this.filmes.add(filme);

        filme = new Filme("Liga da Justiça", "Ficção", "2015");
        this.filmes.add(filme);

        filme = new Filme("Capitão América", "Aventura", "2016");
        this.filmes.add(filme);

        filme = new Filme("It: A coisa", "Terror", "2012");
        this.filmes.add(filme);

        filme = new Filme("Pica-Pau: O filme", "Desenho", "2010");
        this.filmes.add(filme);

        filme = new Filme("Bob Esponja: O filme", "Desenho", "2010");
        this.filmes.add(filme);

        filme = new Filme("Tom e Jerry: O filme", "Desenho", "2016");
        this.filmes.add(filme);

        filme = new Filme("A múmia", "Terror", "2015");
        this.filmes.add(filme);

        filme = new Filme("Carros 3", "Desenho", "2017");
        this.filmes.add(filme);
    }
}
