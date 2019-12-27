package com.lucas.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.lucas.organizze.R;
import com.lucas.organizze.config.ConfiguracaoFirebase;
import com.lucas.organizze.helper.Base64Custom;
import com.lucas.organizze.helper.DateCustom;
import com.lucas.organizze.model.Movimentacao;
import com.lucas.organizze.model.Usuario;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campodescricao;
    private EditText campoValor;
    private FloatingActionButton fabsalvar;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        campoCategoria = findViewById(R.id.editCategoria);
        campoValor = findViewById(R.id.editValor);
        campoData = findViewById(R.id.editData);
        campodescricao = findViewById(R.id.editDescricao);

        //Preenche campo data com data atual
        campoData.setText(DateCustom.dataAtual());

        recuperarReceitaTotal();
    }

    public void salvarReceita(View view) {

        if (validarCamposreceitas()) {
            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campodescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("r");

            Double receitaAtualizada = receitaTotal + valorRecuperado;
            atualizarReceita(receitaAtualizada);

            movimentacao.salvar(data);

            finish();
        }
    }



    public Boolean validarCamposreceitas (){

        String textoValor =  campoValor.getText().toString();
        String textoData =  campoData.getText().toString();
        String textoCategoria =  campoCategoria.getText().toString();
        String textoDescricao =  campodescricao.getText().toString();

        //Validar campos
        if (!textoValor.isEmpty()){
            if (!textoData.isEmpty()){
                if (!textoCategoria.isEmpty()){
                    if (!textoDescricao.isEmpty()){
                        return true;
                    }else{
                        Toast.makeText(ReceitasActivity.this,
                                "Preencha a descrição!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{
                    Toast.makeText(ReceitasActivity.this,
                            "Preencha a categoria!",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(ReceitasActivity.this,
                        "Preencha a data!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(ReceitasActivity.this,
                    "Preencha o valor!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void recuperarReceitaTotal(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codigicarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef
                .child("usuarios")
                .child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue( Usuario.class );
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void atualizarReceita (Double receita){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codigicarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef
                .child("usuarios")
                .child(idUsuario);

        usuarioRef.child("receitaTotal").setValue(receita);

    }
}
