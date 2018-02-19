package com.example.meuprojeto.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.meuprojeto.AlocacaoAdapter;
import com.example.meuprojeto.Model.Alocacao;
import com.example.meuprojeto.Model.Candidato;
import com.example.meuprojeto.Model.Professor;
import com.example.meuprojeto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listarAlocacaoProfessor extends AppCompatActivity {

    ListView listViewAlocados;
    List<Alocacao> alocado;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alocacao_professor);

    database= FirebaseDatabase.getInstance().getReference("Alocacao");
    listViewAlocados = (ListView)findViewById(R.id.listViewAlocados);
    alocado= new ArrayList<>();

    listViewAlocados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("Entrei");
            Alocacao aloca = alocado.get(position);
            showDeleteDialog(aloca.getIdAlocacao(), aloca.getIdProjeto(),aloca.getIdAluno(),aloca.getIdCandidato());
        }
    });
    /*System.out.println("Entrei");
            Alocacao aloca = alocado.get(position);
            showDeleteDialog(aloca.getIdAlocacao(), aloca.getIdProjeto(),aloca.getIdAluno(),aloca.getIdCandidato());
            return true;*/

    }

    private void showDeleteDialog(final String IdAlocacao, final String idProj, final String idAluno ,final String idCandi){
        AlertDialog.Builder dialogBuider = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_delete_alocado,null);
        dialogBuider.setView(dialogView);

        final Button button_delete = (Button) dialogView.findViewById(R.id.button_delete);

        dialogBuider.setTitle("Deseja remover o aluno do projeto?");
        final AlertDialog b = dialogBuider.create();
        b.show();

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlocacao(IdAlocacao,idProj,idAluno, idCandi);
                b.dismiss();
            }
        });
    }

    private boolean deleteAlocacao(String id, final String idProj, String idAluno, final String idCandi){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Alocacao").child(id);
        database.removeValue();
        final String candifato=idCandi;
        final String projeto=idProj;
        String idprofe="nadinha";


        //======================================================================
        //PEGAR ID DO PROFESSOR LOGADO
        final List<Professor> profList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Professor").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String email="nada";
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Professor j = snapshot.getValue(Professor.class);
                            profList.add(j);
                        }

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            email = user.getEmail();
                            //System.out.println("logado"+email);
                        }

                        for(int i=0;i<profList.size();i++){
                            if(profList.get(i).getEmailProf().equals(email)){
                                // System.out.println("info"+projList.get(i).getNome());
                               // idprofe=profList.get(i).getIdProfessor();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        //======================================================================
        //ADD CANDIDATO NA LISTA
        final List<Candidato> candList = new ArrayList<>();
        ///System.out.println("idcandi2"+idCandi);
        FirebaseDatabase.getInstance().getReference().child("Candidato").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    candList.clear();
                    Candidato candi = snapshot.getValue(Candidato.class);
                    candList.add(candi);
                }


                for(int i=0;i<candList.size();i++){
                    if(candList.get(i).getIdCandidato().equals(candifato)&&candList.get(i).getIdProjeto().equals(projeto)){
                        DatabaseReference database =FirebaseDatabase.getInstance().getReference("Candidato").child(candifato);
                        Candidato candi = new Candidato(candifato,candList.get(i).getData(),"Indeferido",projeto,candList.get(i).getIdAluno(),candList.get(i).getEmailAluno(),candList.get(i).getIdProfessor());
                        database.setValue(candi);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=======================================================================

        Toast.makeText(getApplicationContext(), "Alocação deletada", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alocado.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Alocacao alo=snapshot.getValue(Alocacao.class);
                    alocado.add(alo);
                }

                AlocacaoAdapter adapter = new AlocacaoAdapter(listarAlocacaoProfessor.this, alocado);
                listViewAlocados.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

