package com.lucas.bancodedadossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //Criando banco de dados
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (nome VARCHAR, idade INT(3) )");

            // Inserir dados
            // bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES ('Lucas', 21) ");
            // bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES ('José', 18) ");
            // bancoDados.execSQL("INSERT INTO pessoas(nome, idade) VALUES ('Jéssica', 36) ");

            //Alterar dados
               bancoDados.execSQL("UPDATE pessoas SET idade = 19 WHERE nome = 'Lucas'");

            //Recuperar Pessoas
           /* String consulta =
                    "SELECT nome, idade FROM pessoas " +
                    "WHERE nome = 'Lucas' AND idade = 21";*/

           /* String consulta =
                    "SELECT nome, idade FROM pessoas " +
                            "WHERE nome IN('Lucas', 'José')";*/

           /* String consulta =
                    "SELECT nome, idade FROM pessoas " +
                            "WHERE idade BETWEEN 30 AND 36";*/

           /* String consulta =
                    "SELECT nome, idade FROM pessoas " +
                            "WHERE nome LIKE 'lu%' "; */
            String consulta =
                    "SELECT * FROM pessoas";


            Cursor cursor = bancoDados.rawQuery(consulta, null);

            //REcuperar os indices da tabela
            int indiceNome = cursor.getColumnIndex("nome");
            int indiceIdade = cursor.getColumnIndex("idade");

            //pega o primeiro indice do cursor
            cursor.moveToFirst();
            while (cursor != null){

                String nome = cursor.getString(indiceNome);
                String idade = cursor.getString(indiceIdade);

                Log.i("RESULTADO - NOME ",  nome + "| IDADE: " + idade);
                //pega o proximo indice do cursor como se fosse o ++
                cursor.moveToNext();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
