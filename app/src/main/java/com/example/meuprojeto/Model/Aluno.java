package com.example.meuprojeto.Model;

import com.example.meuprojeto.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Talit on 18/01/2018.
 */

public class Aluno {
    private String IdAluno;
    private String NomeAluno;
    private String EmailAluno;
    private String CursoAluno;
    private String fkAcessoAluno; //fk de acesso
    private String matricula;
    //talvez precise se uma variavel tipo ACESSO


    public Aluno() {
    }

    public Aluno(String idAluno, String nomeAluno, String emailAluno, String cursoAluno, String fkAcessoAluno, String matricula) {
        IdAluno = idAluno;
        NomeAluno = nomeAluno;
        EmailAluno = emailAluno;
        CursoAluno = cursoAluno;
        this.fkAcessoAluno = fkAcessoAluno;
        this.matricula = matricula;
    }

    public String getIdAluno() {
        return IdAluno;
    }

    public void setIdAluno(String idAluno) {
        IdAluno = idAluno;
    }

    public String getNomeAluno() {
        return NomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        NomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return EmailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        EmailAluno = emailAluno;
    }

    public String getCursoAluno() {
        return CursoAluno;
    }

    public void setCursoAluno(String cursoAluno) {
        CursoAluno = cursoAluno;
    }

    public String getFkAcessoAluno() {
        return fkAcessoAluno;
    }

    public void setFkAcessoAluno(String fkAcessoAluno) {
        this.fkAcessoAluno = fkAcessoAluno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Aluno").child(String.valueOf(getIdAluno())).setValue(this);
    }
    @Exclude
    public Map<String, Object> toMappin(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("IdAluno",getIdAluno());
        hashMapUsuario.put("NomeAluno",getNomeAluno());
        hashMapUsuario.put("EmailAluno",getEmailAluno());
        hashMapUsuario.put("CursoAluno",getCursoAluno());
        hashMapUsuario.put("fkAcessoAluno",getFkAcessoAluno());
        hashMapUsuario.put("matricula",getMatricula());
        return  hashMapUsuario;
    }
}
