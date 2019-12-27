package com.lucas.organizze.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.lucas.organizze.config.ConfiguracaoFirebase;

public class Usuario {
    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private Double DespesaTotal = 0.00;
    private Double ReceitaTotal = 0.00;



    public Usuario() {

    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void salvar(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDataBase();
        firebase.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);

    }


    public Double getDespesaTotal() {
        return DespesaTotal;
    }

    public void setDespesaTotal(Double despesaTotal) {
        DespesaTotal = despesaTotal;
    }

    public Double getReceitaTotal() {
        return ReceitaTotal;
    }

    public void setReceitaTotal(Double receitaTotal) {
        ReceitaTotal = receitaTotal;
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
