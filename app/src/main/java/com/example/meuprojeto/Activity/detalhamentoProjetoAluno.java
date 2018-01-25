package com.example.meuprojeto.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.meuprojeto.R;

public class detalhamentoProjetoAluno extends AppCompatActivity {
    //Mostra os detalhes do projeto quando o aluno seleciona um
    //atraves dessa tela o aluno também pode enviar uma solicitação para o professor
    //http://starfightercarlao.blogspot.com.br/2017/06/crud-basico-com-android-e-firebase_17.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhamento_projeto_aluno);
    }
}
