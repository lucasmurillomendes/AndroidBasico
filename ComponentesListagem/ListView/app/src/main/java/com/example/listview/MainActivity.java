package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listlocais;
    private String [] itens = {
            "Nova Olímpia", "Umuarama", "São Paulo",
            "Maringá", "Janiópolis", "São Bernardo",
            "Minas Gerais", "Presidente Prudente",
            "São João", "Caldas Novas", "Goiania"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listlocais = findViewById(R.id.listLocais);

        //Criando Adaptador para a lista
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );

        //Adiciona adaptador na lista
        listlocais.setAdapter( adaptador );

        //Adiciona click na lista
        listlocais.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String valorSelecionado = listlocais.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), valorSelecionado, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
