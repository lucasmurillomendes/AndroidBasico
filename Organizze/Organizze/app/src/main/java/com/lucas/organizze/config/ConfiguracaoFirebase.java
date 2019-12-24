package com.lucas.organizze.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;

    //Retorna a instancia do firebase auth
    public static FirebaseAuth getFirebaseAutenticacao(){
        //caso não tenha instancia inicia uma
        if (autenticacao == null){

            autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;
     }
}
