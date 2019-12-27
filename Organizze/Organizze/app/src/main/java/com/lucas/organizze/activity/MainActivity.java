package com.lucas.organizze.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.lucas.organizze.activity.LoginActivity;
import com.lucas.organizze.R;
import com.lucas.organizze.config.ConfiguracaoFirebase;

public class MainActivity extends IntroActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        //removendo botões
        setButtonNextVisible(false);
        setButtonBackVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .canGoBackward(false)
                .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuárioLogado();
    }

    public void cadastrar (View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
    public void entrar (View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void verificarUsuárioLogado(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //autenticacao.signOut();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }else{

        }

    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
    }

}
