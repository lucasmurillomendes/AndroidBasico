package com.example.recyclerview.activity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.recyclerview.R;
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

    }

    public void criarFilmes(){
        Filme filme = new Filme("Homem Aranha", "Aventura", "2017");
        this.filmes.add(filme);

        Filme filme1 = new Filme("Mulher Maravilha", "Fantasia", "2018");
        this.filmes.add(filme1);

        Filme filme2 = new Filme("Liga da Justiça", "Ficção", "2015");
        this.filmes.add(filme2);

        Filme filme3 = new Filme("Capitão América", "Aventura", "2016");
        this.filmes.add(filme3);

        Filme filme4 = new Filme("It: A coisa", "Terror", "2012");
        this.filmes.add(filme4);

        Filme filme5 = new Filme("Pica-Pau: O filme", "Desenho", "2010");
        this.filmes.add(filme5);

        Filme filme6 = new Filme("Bob Esponja: O filme", "Desenho", "2010");
        this.filmes.add(filme6);

        Filme filme7 = new Filme("Tom e Jerry: O filme", "Desenho", "2016");
        this.filmes.add(filme7);

        Filme filme8 = new Filme("A múmia", "Terror", "2015");
        this.filmes.add(filme8);

        Filme filme9 = new Filme("Carros 3", "Desenho", "2017");
        this.filmes.add(filme9);
    }
}
