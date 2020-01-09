package com.lucas.whatsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.lucas.whatsapp.R;
import com.lucas.whatsapp.config.ConfiguracaoFirebase;
import com.lucas.whatsapp.helper.Base64Custom;
import com.lucas.whatsapp.helper.UsuarioFirebase;
import com.lucas.whatsapp.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText editNome, editSenha, editEmail;
    private Button buttonCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editPerfilNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoNome = editNome.getText().toString();
                String textoSenha = editSenha.getText().toString();
                String textoEmail = editEmail.getText().toString();

                //Validar campos
                if (!textoNome.isEmpty()){
                    if (!textoEmail.isEmpty()){
                        if (!textoSenha.isEmpty()){

                            usuario = new Usuario();
                            usuario.setEmail(textoEmail);
                            usuario.setNome(textoNome);
                            usuario.setSenha(textoSenha);

                            cadastrarUsuario();

                        }else{
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    public void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()){
                          Toast.makeText(CadastroActivity.this, "Cadastro efetuado",
                                  Toast.LENGTH_SHORT).show();
                          UsuarioFirebase.atualizarNomeusuario(usuario.getNome());
                          finish();

                          try {
                              String identificadorUsuario = Base64Custom.codigicarBase64(usuario.getEmail());
                              usuario.setIdUsuario(identificadorUsuario);
                              usuario.salvar();
                          }catch (Exception e){
                              e.printStackTrace();
                          }

                      }else{
                          //tratando possiveis erros do firebase na aplicação na autenticação
                          String excecao = "";
                          try {
                              throw  task.getException();
                          }catch (FirebaseAuthWeakPasswordException e){
                              excecao = "Digite uma senha mais forte!";
                          }catch (FirebaseAuthInvalidCredentialsException e){
                              excecao = "Digite uma senha mais forte!";
                          }catch (FirebaseAuthUserCollisionException e){
                              excecao = "Essa conta ja existe!";
                          }catch (Exception e){
                              excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                              e.printStackTrace();
                          }
                          Toast.makeText(CadastroActivity.this, excecao,
                                  Toast.LENGTH_SHORT).show();
                      }
                    }
                });
    }
}
