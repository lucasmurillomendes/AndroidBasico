package com.example.passandodadosacitivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEnviar = findViewById(R.id.buttonEnviar);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SegundaActivity.class);

                Usuario usuario = new Usuario("Lucas", "lucas@lucas.com");

                //Passar dados
                intent.putExtra("nome", "Lucas Mendes");
                intent.putExtra("idade", 21);
                //Objeto precisa do Serializeble(converter em bytes)
                intent.putExtra("objeto", usuario);


                startActivity(intent);
            }
        });


    }
}
