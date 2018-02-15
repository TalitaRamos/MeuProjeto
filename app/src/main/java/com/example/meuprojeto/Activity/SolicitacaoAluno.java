package com.example.meuprojeto.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meuprojeto.Model.Candidato;
import com.example.meuprojeto.R;
import com.example.meuprojeto.SolicitAlunoAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SolicitacaoAluno extends AppCompatActivity {

    DatabaseReference database;
    List<Candidato> canditList;
    TextView name_proje;
    final String identifica ="tina";

    ListView listViewSolicitacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacao_aluno);

        name_proje = (TextView) findViewById(R.id.name_proje);
        database= FirebaseDatabase.getInstance().getReference("Candidato");
                //.child("-L3aRT3ciz6oL_UJZrLZ");
        listViewSolicitacao=(ListView)findViewById(R.id.listViewSolicitacao);

        canditList=new ArrayList<>();
        name_proje.setText("Solicitações");
    }

    @Override
    protected  void onStart(){
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                email = user.getEmail();
                canditList.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){

                    Candidato candit = snap.getValue(Candidato.class);
                    if(candit.getEmailAluno().equals(email)){
                        System.out.println("candidato logado");
                        canditList.add(candit);
                    }else{
                        System.out.println("não é candidato logado");
                    }
                }
                SolicitAlunoAdapter adapter = new SolicitAlunoAdapter(SolicitacaoAluno.this,canditList);
                listViewSolicitacao.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
