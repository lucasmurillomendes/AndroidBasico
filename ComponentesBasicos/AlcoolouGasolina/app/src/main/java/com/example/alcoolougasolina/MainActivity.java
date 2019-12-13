package com.example.alcoolougasolina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

        private EditText editPrecoAlcool;
        private EditText editPrecoGaslina;
        private TextView textResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editPrecoAlcool = findViewById(R.id.editPrecoAlcool);
        editPrecoGaslina = findViewById(R.id.editPrecoGasolina);
        textResultado = findViewById(R.id.textResultado);
    }

    public void calcularPreco(View view){
    //Recupera o valor digitado
        String precoAlcool = editPrecoAlcool.getText().toString().replaceAll(",", ".");
        String precoGasolina = editPrecoGaslina.getText().toString().replaceAll(",", ".");
    // Valida campos
        Boolean camposValidados = this.validaCampo(precoAlcool,precoGasolina);
        if ( camposValidados ){
            this.calcularMelhorPreco(precoAlcool,precoGasolina);
        }else {
            textResultado.setText("Preencha os campos corretamente");
        }

        editPrecoAlcool.setText("");
        editPrecoGaslina.setText("");

    }

    public void calcularMelhorPreco(String pAlcool, String pGasolina){

        //Converter valores para double

        Double precoAlcool = Double.parseDouble(pAlcool);
        Double precoGasolina = Double.parseDouble(pGasolina);

        Double resultado = precoAlcool/precoGasolina;
        if ( resultado >= 0.7){
            textResultado.setText("Melhor utilizar: Gasolina!");
        }else{
            textResultado.setText("Melhor utilizar: Álcool!");
        }

    }

    public Boolean validaCampo(String pAlcool, String pGasolina){

        Boolean camposValidados = true;

        if ( pAlcool == null || pAlcool.equals("") ){
             camposValidados = false;
        }else if ( pGasolina == null || pGasolina.equals("")){
            camposValidados = false;
        }
        return camposValidados;
    }
}
