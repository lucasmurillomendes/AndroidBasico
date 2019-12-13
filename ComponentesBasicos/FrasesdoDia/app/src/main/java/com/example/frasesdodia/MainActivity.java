package com.example.frasesdodia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gerarNovaFrase (View view) {

        String[] frases = {
                "O sucesso nasce do querer, da determinação e persistência em se chegar a um" +
                        " objetivo. Mesmo não atingindo o alvo, quem busca e vence obstáculos, no " +
                        "mínimo fará coisas admiráveis.",
                "Lute. Acredite. Conquiste. Perca. Deseje. Espere. Alcance. Invada. Caia." +
                        " Seja tudo o quiser ser, mas acima de tudo, seja você sempre.",
                "Só existe um êxito: a capacidade de levar a vida que se quer.",
                "A vitalidade é demonstrada não apenas pela persistência, mas pela capacidade de " +
                        "começar de novo.",
                "A coragem não é ausência do medo; é a persistência apesar do medo."
        };
            int numero = new Random().nextInt(frases.length);

        TextView texto = (TextView) findViewById(R.id.text_resultado);
        texto.setText(frases[numero]);

    }

}
