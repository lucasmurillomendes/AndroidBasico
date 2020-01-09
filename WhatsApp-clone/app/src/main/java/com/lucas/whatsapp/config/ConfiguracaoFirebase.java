package com.lucas.whatsapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;
    private static StorageReference storage;

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

    public static StorageReference getFirebaseStorage (){
        if (storage == null){
            storage = FirebaseStorage.getInstance().getReference();
        }

        return storage;
    }

}
