package com.lucas.whatsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.lucas.whatsapp.R;
import com.lucas.whatsapp.config.ConfiguracaoFirebase;
import com.lucas.whatsapp.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button buttonEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        buttonEntrar = findViewById(R.id.buttonEntrar);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoSenha = editSenha.getText().toString();
                String textoEmail = editEmail.getText().toString();

                //Validar campos
                if (!textoEmail.isEmpty()){
                    if (!textoSenha.isEmpty()){

                        usuario = new Usuario();
                        usuario.setSenha(textoSenha);
                        usuario.setEmail(textoEmail);

                        validarLogin();

                    }else{
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,
                            "Preencha o email!",
                            Toast.LENGTH_SHORT).show();
                }


            }

        });

    }

    public void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    abrirTelaPrincipal();
                }else{
                    //Tratando erros de login
                    String excecao = "";
                    try {
                        throw  task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Usuário ou senha não correspondem a nenhum usuário";
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuario não está cadastrado!";
                    }catch (Exception e){
                        excecao = "Erro ao fazer login: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excecao,
                            Toast.LENGTH_SHORT).show();

                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void abrirTelaLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
      if (FirebaseAuth.getInstance().getCurrentUser() != null){
          abrirTelaPrincipal();
      }
    }

    public void abrirTelaCadastro(View view){

        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);

    }
}
