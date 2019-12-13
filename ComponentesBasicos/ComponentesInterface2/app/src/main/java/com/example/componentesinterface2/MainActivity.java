package com.example.componentesinterface2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private Switch swEstado;
    private ToggleButton tgEstado;
    private CheckBox chEstado;
    private TextView textResultado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swEstado = findViewById(R.id.swEstado);
        tgEstado = findViewById(R.id.tgEstado);
        chEstado = findViewById(R.id.chEstado);
        textResultado = findViewById(R.id.textResultado);
    }

    public void enviar(View view){
        //Todas as formas de ver se esta ligado são iguais ischecked();
        if ((tgEstado.isChecked())) {
            textResultado.setText("Toggle Ligado");
        } else {
            textResultado.setText("Toggle Desligado");
        }

    }
}
