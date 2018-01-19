package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meuprojeto.R;

public class cadastroProjeto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_projeto);
    }

    //NAVEGAR PRA TELA DE PROJETOS
    public void VerProject(View c){
        Intent in = new Intent(this,Project_prof.class);
        startActivity(in);
    }
}
