package com.example.alertdialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirAlerta(View view){
        /*
        Criar Alert Dialog
         */

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //Configura titulo da mensagem
        dialog.setTitle("Titulo");
        dialog.setMessage("Mensagem");

        //configura o cancelamento
        dialog.setCancelable(false);

        //configura o icone da Dialog
        dialog.setIcon(android.R.drawable.ic_btn_speak_now);

        //Configura ação sim ou não
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                Toast.makeText(getApplicationContext(), "Sim foi pressionado", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getApplicationContext(), "Não foi pressionado", Toast.LENGTH_SHORT).show();
            }
        });

        //Criar e exibir AlertDialog
        dialog.create();
        dialog.show();
    }

}
