package com.example.meuprojeto;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.meuprojeto.Model.Aluno;
import com.example.meuprojeto.Model.Candidato;
import com.example.meuprojeto.Model.Projeto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Talit on 27/01/2018.
 */

public class SolicitProfessorAdapter extends ArrayAdapter<Candidato> {
    private Activity context;
    private List<Candidato> candidatoList;
    String nomeprojeto="rd";
    String nomeAluno="rd";
    String cursoAluno="rf";


    public SolicitProfessorAdapter(Activity context, List<Candidato> candidatoList) {
        super(context,R.layout.layout_list_solicitacao_professor,candidatoList);
        this.context = context;
        this.candidatoList = candidatoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewItem = inflater.inflate(R.layout.layout_list_solicitacao_professor, null,true);

        final TextView textViewNameProjeto = (TextView) listviewItem.findViewById(R.id.textViewNameProjeto);
        final TextView NomeCurso = (TextView) listviewItem.findViewById(R.id.NomeCurso);
        TextView StatusSoliProf = (TextView)listviewItem.findViewById(R.id.StatusSoliProf);

        final Candidato candidato = candidatoList.get(position);



        StatusSoliProf.setText(candidato.getSituacao());

        //==============INICIO LISTA ALUNO=============
        final List<Aluno>alunoLista = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Aluno")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap:dataSnapshot.getChildren()){
                            Aluno alun = snap.getValue(Aluno.class);
                            alunoLista.add(alun);
                        }

                        for(int i=0; i<alunoLista.size();i++){
                            if(candidato.getIdAluno().equals(alunoLista.get(i).getIdAluno())){
                                nomeAluno=alunoLista.get(i).getNomeAluno();
                                cursoAluno =alunoLista.get(i).getCursoAluno();
                                //System.out.println("nomeAluno: "+nomeAluno+"===curso: "+cursoAluno);
                                //COLOCANDO O NOME DO ALUNO E DO CURSO NO TEXTVIEW
                                NomeCurso.setText(nomeAluno +" ("+cursoAluno+")");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        //==============FIM LISTA ALUNO=============

        //==============INICIO LISTA PROJETO=============

        final List<Projeto> projList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Projeto").child(candidato.getIdProfessor())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap:dataSnapshot.getChildren()){
                            Projeto j = snap.getValue(Projeto.class);
                            projList.add(j);
                        }

                        for(int i=0;i<projList.size();i++){
                            if(candidato.getIdProjeto().equals(projList.get(i).getIdProjeto())){
                                nomeprojeto=projList.get(i).getNome();
                                //System.out.println("nomeProjeto: "+nomeprojeto);
                                //COLOCANDO O NOME DO PROJETO NO TEXTVIEW
                                textViewNameProjeto.setText(nomeprojeto);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        //==============FIM LISTA PROJETO================
        return listviewItem;
    }
}
