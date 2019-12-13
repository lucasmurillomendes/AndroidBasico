package com.example.calculadoraimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void mostrarIMC (View view) {

        try {
        EditText peso = findViewById(R.id.valorPeso);
        EditText altura = findViewById(R.id.valorAltura);

        double valorPeso = Double.parseDouble(String.valueOf(peso.getText()));
        double valorAltura = Double.parseDouble(String.valueOf(altura.getText()));
        TextView texto = findViewById(R.id.textoResultado);

        Double resultado = calcularIMC(valorPeso, valorAltura);

        DecimalFormat df = new DecimalFormat("###.##");
        String resultFormatado = df.format(resultado);

        texto.setText("Seu imc é: " + resultFormatado);

        ImageView imagem = findViewById(R.id.imagemReferencia);

        TextView textoS = findViewById(R.id.textoSaudacao);

        if (resultado < 17.0) {
            imagem.setImageResource(R.drawable.ic_triste_demais_24dp);
               textoS.setText("Muito abaixo do peso!");
        } else if (resultado == 17.0 || resultado < 18.49) {
            imagem.setImageResource(R.drawable.ic_triste_24dp);
             textoS.setText("Abaixo do peso!");
        } else if (resultado == 18.50 || resultado < 24.99) {
            imagem.setImageResource(R.drawable.ic_perfeito_24dp);
            textoS.setText("Peso normal, parabéns!");
        } else if (resultado == 25.0 || resultado < 29.99) {
            imagem.setImageResource(R.drawable.ic_cuidado_24dp);
            textoS.setText("Acima do peso!");
        } else if (resultado == 30.0 || resultado < 34.99) {
            imagem.setImageResource(R.drawable.ic_triste_24dp);
            textoS.setText("Obesidade I, cuidado!");
        } else if (resultado == 35.0 || resultado < 39.99) {
            imagem.setImageResource(R.drawable.ic_surpreso_24dp);
            textoS.setText("Obesidade II!");
        } else if (resultado >= 40.0) {
            imagem.setImageResource(R.drawable.ic_triste_demais_24dp);
            textoS.setText("Obesidade III (mórbida)!");
        }

    }catch (NumberFormatException e){
          TextView texto = findViewById(R.id.textoResultado);
          texto.setText("Você não informou valores corretamente!");
     }

    }

    public Double  calcularIMC (Double peso, Double altura){
        Double resultado = peso / (altura*altura);
        return resultado;
    }


}
