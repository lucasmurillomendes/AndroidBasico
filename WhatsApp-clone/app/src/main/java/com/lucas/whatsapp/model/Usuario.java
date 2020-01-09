package com.lucas.whatsapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.lucas.whatsapp.config.ConfiguracaoFirebase;
import com.lucas.whatsapp.helper.UsuarioFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String foto;



    public Usuario() {

    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void salvar(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference usuario = firebase.child("usuarios").child(getIdUsuario());

        usuario.setValue(this);

    }

    public void atualizar(){
        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        DatabaseReference dataBase = ConfiguracaoFirebase.getFirebaseDataBase();

        DatabaseReference usuariosRef = dataBase.child("usuarios")
                .child(identificadorUsuario);

        Map<String,Object> valoresUsuario = converterParaMap();

        usuariosRef.updateChildren(valoresUsuario);
    }
    //converte o objeto usuario para map
    @Exclude
    public Map<String, Object> converterParaMap(){
        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", getEmail());
        usuarioMap.put("nome", getNome());
        usuarioMap.put("foto", getFoto());

        return usuarioMap;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
