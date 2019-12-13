package com.example.pedrapapeltesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selecionarPedra(View view){
    this.opcaoSelecionada("pedra");
    }
    public void selecionarPapel(View view){
    this.opcaoSelecionada("papel");
    }
    public void selecionarTesoura(View view){
    this.opcaoSelecionada("tesoura");
    }

    public void opcaoSelecionada(String escolhaUsuario){

        ImageView imagemApp = (ImageView) findViewById(R.id.imageResultado);
        TextView  texto = (TextView) findViewById(R.id.textResultado);
        ImageView imagemUsuario = (ImageView) findViewById(R.id.escolhaUsuario);

        if (escolhaUsuario == "pedra"){
            imagemUsuario.setImageResource(R.drawable.pedra);
        }else if(escolhaUsuario == "papel"){
            imagemUsuario.setImageResource(R.drawable.papel);
        }else if(escolhaUsuario == "tesoura"){
            imagemUsuario.setImageResource(R.drawable.tesoura);
        }else{
            texto.setText("Nenhuma Opção Selelcionada");
        }

    //gerar randomico
        String[] opcoes = {"pedra", "papel", "tesoura"};
        int numero = new Random().nextInt(3);
        String escolhaApp = opcoes[numero];

      switch (escolhaApp) {
          case "pedra":
              imagemApp.setImageResource(R.drawable.pedra);
              break;
          case "papel":
                imagemApp.setImageResource(R.drawable.papel);
              break;
          case "tesoura":
              imagemApp.setImageResource(R.drawable.tesoura);
              break;
      }

      if (
              (escolhaApp == "pedra" && escolhaUsuario == "tesoura") ||
              (escolhaApp =="papel" && escolhaUsuario == "pedra")||
              (escolhaApp == "tesoura" && escolhaUsuario == "papel")
      ){//app ganhador
          texto.setText("Você Perdeu!");

      }else if (
              (escolhaUsuario == "pedra" && escolhaApp == "tesoura") ||
              (escolhaUsuario =="papel" && escolhaApp == "pedra")||
              (escolhaUsuario == "tesoura" && escolhaApp == "papel")

      ){//Usuario ganhador
            texto.setText("Você ganhou!");
      }else {// Empate
            texto.setText("Empate!");
      }
    }
}
