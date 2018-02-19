package com.example.meuprojeto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.meuprojeto.Model.Candidato;
import com.example.meuprojeto.Model.Projeto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Talit on 25/01/2018.
 */

public class SolicitAlunoAdapter extends ArrayAdapter<Candidato>{

    private Activity context;
    private List<Candidato> candidatoList;
    String identifica ="tina";
    String idProj ="d";

    public  SolicitAlunoAdapter(Activity context, List<Candidato>candidatoList){
        super(context,R.layout.layout_list_solicitacao_aluno,candidatoList);
        this.context=context;
        this.candidatoList=candidatoList;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();

        View listviewItem = inflater.inflate(R.layout.layout_list_solicitacao_aluno,null,true);

        final TextView textViewNameProjeto = (TextView) listviewItem.findViewById(R.id.textViewNameProjeto);
        TextView textStatusSoli = (TextView) listviewItem.findViewById(R.id.textStatusSoli);

        final Candidato candidato = candidatoList.get(position);
        //=======================================================

        final List<Projeto> projList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Projeto").child(candidato.getIdProfessor())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Projeto j = snapshot.getValue(Projeto.class);
                            if(j.getIdProjeto().equals(candidato.getIdProjeto())){
                                projList.add(j);
                            }

                        }

                        for(int i=0;i<projList.size();i++){
                            if(projList.get(i).getIdProjeto().equals(candidato.getIdProjeto())){
                               // System.out.println("info"+projList.get(i).getNome());
                                identifica=projList.get(i).getNome();
                                //System.out.println("Nome projeto--"+projList.get(i).getNome());
                                if(identifica.equals("tina")){
                                    //System.out.println("vazio");
                                    identifica = "O projeto não existe mais";
                                }
                                textViewNameProjeto.setText(identifica);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        //System.out.println("retorna"+identifica+"proj"+idProj);
        //=======================================================
        textStatusSoli.setText(candidato.getSituacao());


        return listviewItem;
    }
}
