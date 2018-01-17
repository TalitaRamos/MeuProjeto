package com.example.meuprojeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void cadProf(View v){
        Intent intent=new Intent(this,cadastroProfessor.class);
        startActivity(intent);
    }

    public void cadAlun(View c){
        Intent inte = new Intent(this,cadastroAluno.class);
        startActivity(inte);
    }
}
