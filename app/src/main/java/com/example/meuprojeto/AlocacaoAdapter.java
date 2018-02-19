package com.example.meuprojeto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meuprojeto.Model.Alocacao;
import com.example.meuprojeto.Model.Aluno;
import com.example.meuprojeto.Model.Projeto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Talit on 30/01/2018.
 */

public class AlocacaoAdapter extends ArrayAdapter<Alocacao>{
    private Activity context;
    private List<Alocacao> alocacaoList;
    String nomeProj="d";
    String nomeAl="q";

    public AlocacaoAdapter(Activity context, List<Alocacao> alocacaoList){
        super(context, R.layout.layout_list_alocado,alocacaoList);
        this.context = context;
        this.alocacaoList = alocacaoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_list_alocado,null,true);

        final TextView tprojeto = (TextView)listViewItem.findViewById(R.id.tprojeto);
        final TextView taluno = (TextView)listViewItem.findViewById(R.id.taluno);
        TextView data = (TextView)listViewItem.findViewById(R.id.data);

        final Alocacao alocado = alocacaoList.get(position);

        data.setText("Data de Início: "+alocado.getDataInicio());

        //====================================================================================
        final List<Projeto> projList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Projeto").child(alocado.getIdProfessor())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Projeto j = snapshot.getValue(Projeto.class);
                            projList.add(j);
                        }

                        for(int i=0;i<projList.size();i++){
                            if(projList.get(i).getIdProjeto().equals(alocado.getIdProjeto())){
                                // System.out.println("info"+projList.get(i).getNome());
                                nomeProj=projList.get(i).getNome();
                                tprojeto.setText(nomeProj);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
       // System.out.println("retorna"+identifica+"proj"+idProj);
        //================================================================================
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
                            if(alocado.getIdAluno().equals(alunoLista.get(i).getIdAluno())){
                                nomeAl=alunoLista.get(i).getNomeAluno();
                                //COLOCANDO O NOME DO ALUNO E DO CURSO NO TEXTVIEW
                                taluno.setText(nomeAl);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        //==============FIM LISTA ALUNO=============

        return listViewItem;
    }
}
