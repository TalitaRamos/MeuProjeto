package com.example.meuprojeto.Model;

import com.example.meuprojeto.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Talit on 18/01/2018.
 */

public class Professor {
    private String IdProfessor;
    private String NomeProf;
    private String EmailProf;
    private String AreaProf;
    private String IdAcessoProf; //fk de acesso

    //talvez precise se uma variavel tipo ACESSO

    public Professor() {
    }

    public String getIdProfessor() {
        return IdProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        IdProfessor = idProfessor;
    }

    public String getNomeProf() {
        return NomeProf;
    }

    public void setNomeProf(String nomeProf) {
        NomeProf = nomeProf;
    }

    public String getEmailProf() {
        return EmailProf;
    }

    public void setEmailProf(String emailProf) {
        EmailProf = emailProf;
    }

    public String getAreaProf() {
        return AreaProf;
    }

    public void setAreaProf(String areaProf) {
        AreaProf = areaProf;
    }

    public String getIdAcessoProf() {
        return IdAcessoProf;
    }

    public void setIdAcessoProf(String idAcessoProf) {
        IdAcessoProf = idAcessoProf;
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Professor").child(String.valueOf(getIdProfessor())).setValue(this);
    }
    @Exclude
    public Map<String, Object> toMappin(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("IdProfessor",getIdProfessor());
        hashMapUsuario.put("NomeProf",getNomeProf());
        hashMapUsuario.put("EmailProf",getEmailProf());
        hashMapUsuario.put("AreaProf",getAreaProf());
        hashMapUsuario.put("fkAcessoProf",getIdAcessoProf());

        return  hashMapUsuario;
    }
}
