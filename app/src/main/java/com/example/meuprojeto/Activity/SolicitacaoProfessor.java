package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meuprojeto.Model.Candidato;
import com.example.meuprojeto.Model.Professor;
import com.example.meuprojeto.R;
import com.example.meuprojeto.SolicitProfessorAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SolicitacaoProfessor extends AppCompatActivity {

    DatabaseReference database;
    List<Candidato> canditList;
    TextView titlePag;

    ListView listSolicitacaoProf;
    String identifica="tina";
    String email = "f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacao_professor);

        titlePag = (TextView) findViewById(R.id.titlePag);
        titlePag.setText("Solicitações");

        database = FirebaseDatabase.getInstance().getReference("Candidato");

        listSolicitacaoProf = (ListView)findViewById(R.id.listSolicitacaoProf);
        canditList= new ArrayList<>();

        final List<Professor> profList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Professor")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Professor prof = snapshot.getValue(Professor.class);
                            profList.add(prof);
                        }

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user!=null){
                            email=user.getEmail();
                            // System.out.println("email"+email);
                        }

                        for(int i=0;i<profList.size();i++){
                            if(profList.get(i).getEmailProf().equals(email)){
                                identifica=profList.get(i).getIdProfessor();
                                System.out.println("identificador"+identifica);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        listSolicitacaoProf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Candidato candidato =canditList.get(position);
                Intent intent = new Intent(getApplicationContext(),detalhamentoSolicitacaoProf.class);
                intent.putExtra("data",candidato.getData());
                intent.putExtra("emailAluno",candidato.getEmailAluno());
                intent.putExtra("idAluno",candidato.getIdAluno());
                intent.putExtra("idCandidato",candidato.getIdCandidato());
                intent.putExtra("idProfessor",candidato.getIdProfessor());
                intent.putExtra("idProjeto",candidato.getIdProjeto());
                intent.putExtra("situacao",candidato.getSituacao());
                startActivity(intent);
            }
        });
    }

    @Override
    protected  void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                canditList.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Candidato candit = snap.getValue(Candidato.class);
                    System.out.println("identificador2"+identifica);
                    System.out.println("candit"+candit.getIdProfessor());
                    if(candit.getIdProfessor().equals(identifica)){
                        canditList.add(candit);
                    }
                }
                SolicitProfessorAdapter adapter = new SolicitProfessorAdapter(SolicitacaoProfessor.this,canditList);
                listSolicitacaoProf.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
