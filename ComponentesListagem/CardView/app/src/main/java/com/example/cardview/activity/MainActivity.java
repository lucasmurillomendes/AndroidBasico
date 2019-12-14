package com.example.cardview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.cardview.R;
import com.example.cardview.adapter.PostagemAdapter;
import com.example.cardview.model.Postagem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPostagem;
    private List<Postagem> postagens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPostagem = findViewById(R.id.recyclerPostagem);

        //Definir um layout
       // LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //layoutManager.setOrientation(LinearLayout.HORIZONTAL);
      //  RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPostagem.setLayoutManager(layoutManager);

        //Definir um adapter
        this.prepararPostagem();
        PostagemAdapter adapter = new PostagemAdapter (postagens );
        recyclerPostagem.setAdapter(adapter);

    }

    public void prepararPostagem(){

        Postagem p = new Postagem("lucas Murillo", "#tbt dessa viagem linda", R.drawable.imagem1);
        this.postagens.add(p);
        p = new Postagem("Maria Julia", "Viaje aproveite nosos desontos", R.drawable.imagem2);
        this.postagens.add(p);
        p = new Postagem("Roberto Silveira", "#Paris #França", R.drawable.imagem3);
        this.postagens.add(p);
        p = new Postagem("Paulo Pedroso", "#Que foto maravilhosa", R.drawable.imagem4);
        this.postagens.add(p);
    }
}
