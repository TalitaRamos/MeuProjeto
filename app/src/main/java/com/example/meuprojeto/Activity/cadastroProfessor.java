package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.meuprojeto.R;

public class cadastroProfessor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);
    }

    //NAVEGAR PRA TELA DE CADASTRO DE PROJETOS
    public void cadProj(View c){
        Intent inten = new Intent(this,cadastroProjeto.class);
        startActivity(inten);
    }
}
