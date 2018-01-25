package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.meuprojeto.AlunoProjetoAdapter;
import com.example.meuprojeto.Model.Professor;
import com.example.meuprojeto.Model.Projeto;
import com.example.meuprojeto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAlunoProjeto extends AppCompatActivity {
    //EXIBE A LISTA DOS PROJETOS DE UM DETERMINADO PROFESSOR PARA UMA ALUNO
    //QUANDO UM PROFESSOR É SELECIONADO NO LISTVIEW OS SEUS PROJETOS SÃO EXIBIDOS

    TextView nameP;
    SeekBar seekBarRating;
    ListView listViewTracks;
    DatabaseReference database;

    List<Projeto> projList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_aluno_projeto);

        nameP=(TextView)findViewById(R.id.nameP);
        //seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
        listViewTracks = (ListView) findViewById(R.id.listViewTracks);

        // recuperando o intent que foi passado
        Intent intent = getIntent();
        //recuperando os dados da intent
        String id= intent.getStringExtra("idProfessor");
        //System.out.println("idpf"+id);
        String name= intent.getStringExtra("nomeProf");
        String email= intent.getStringExtra("emailProf");

        //colocando o nome na interface
        nameP.setText(name);

        database = FirebaseDatabase.getInstance().getReference("Projeto").child(id);

        projList = new ArrayList<>();

        //INICIO PASSOS PARA IR PARA OUTRA TELA QUANDO CLICAR EM UM PROJETO
        listViewTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Projeto project = projList.get(i);
                Intent intent = new Intent(getApplicationContext(),detalhamentoProjetoAluno.class);
                intent.putExtra("nome",project.getNome());
                intent.putExtra("descricao",project.getDescricao());
                intent.putExtra("idProjeto",project.getIdProjeto());
                intent.putExtra("idProfessor",project.getIdProfessor());
                intent.putExtra("status",project.getStatus());
                //intent.putExtra("");
                //intent.putExtra("emailProf",email);
                startActivity(intent);
            }
        });
        //FIM PASSOS
    }

    @Override
    protected  void onStart(){
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projList.clear();
                for(DataSnapshot projSnapchot : dataSnapshot.getChildren()){
                    Projeto pjct = projSnapchot.getValue(Projeto.class);
                    projList.add(pjct);
                }

                AlunoProjetoAdapter adapter = new AlunoProjetoAdapter(ListAlunoProjeto.this, projList);
                listViewTracks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
