package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.meuprojeto.R;

public class detalhamentoSolicitacaoProf extends AppCompatActivity {

    TextView proje_name;
    TextView alu_name_curso;
    TextView aluno_email;
    TextView data;
    TextView status_soli;
    TextView alu_curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhamento_solicitacao_prof);

        proje_name = (TextView)findViewById(R.id.proje_name);
        alu_name_curso = (TextView)findViewById(R.id.alu_name_curso);
        aluno_email = (TextView)findViewById(R.id.aluno_email);
        data = (TextView)findViewById(R.id.data);
        status_soli = (TextView)findViewById(R.id.status_soli);
        alu_curso = (TextView)findViewById(R.id.alu_curso);

        Intent intent = getIntent();

        String dataS = intent.getStringExtra("data");
        String email = intent.getStringExtra("emailAluno");
        String idAluno = intent.getStringExtra("idAluno");
        String idCandidato = intent.getStringExtra("idCandidato");
        String idProfessor = intent.getStringExtra("idProfessor");
        String idProjeto = intent.getStringExtra("idProjeto");
        String situacao = intent.getStringExtra("situacao");

        proje_name.setText(idProjeto);
        alu_name_curso.setText("Aluno:" +idAluno);
        alu_curso.setText("Curso: ");
        aluno_email.setText("Email: "+email);
        data.setText("Solicitado em: "+dataS);
        status_soli.setText("Status: "+situacao);
    }
}
