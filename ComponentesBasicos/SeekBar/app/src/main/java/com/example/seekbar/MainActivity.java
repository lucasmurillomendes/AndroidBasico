package com.example.seekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textResultado = findViewById(R.id.textResultado);

        //ouvinte para as mudanças de posição
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // quando muda o progresso da barra
                textResultado.setText("Progresso" + seekBar.getProgress() + " / " + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // clica em cima e faz o primeiro movimento
                Toast.makeText(getApplicationContext(), "SeekBar alterado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // solta o seek e para de movimentar
                Toast.makeText(getApplicationContext(),"SeekBar não esta pressionado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
