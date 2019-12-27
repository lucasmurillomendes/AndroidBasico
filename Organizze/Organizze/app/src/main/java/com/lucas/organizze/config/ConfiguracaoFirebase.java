package com.lucas.organizze.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;

    //Retorna a instancia do firebase auth
    public static FirebaseAuth getFirebaseAutenticacao(){
        //caso não tenha instancia inicia uma
        if (autenticacao == null){

            autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;
     }

     //Retorna a instancia do firebase Database
    public static DatabaseReference getFirebaseDataBase(){
        if (firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
             return firebase;
    }

}
