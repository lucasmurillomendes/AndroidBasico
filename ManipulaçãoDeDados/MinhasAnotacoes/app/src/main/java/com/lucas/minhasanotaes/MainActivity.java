package com.lucas.minhasanotaes;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private AnotacaoPreferencia preferencia;
    private EditText editAnotacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editAnotacao = findViewById(R.id.editAnotacao);

        //passando contexto da aplicação para a anotaçãopreferencia
        preferencia = new AnotacaoPreferencia(getApplicationContext());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoRecuperado = editAnotacao.getText().toString();
                //Validar se foi digitado algo
                if (textoRecuperado.equals("")){
                    Snackbar.make(view, "Informe uma anotação!", Snackbar.LENGTH_LONG).show();
                }else{
                    preferencia.salvaAnotacao( textoRecuperado );
                    Snackbar.make(view, "Anotação salva com sucesso!", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        String anotacao = preferencia.recuperarAnotacao();

        if ( !anotacao.equals("") ) {
            editAnotacao.setText(anotacao);
        }

    }

}
