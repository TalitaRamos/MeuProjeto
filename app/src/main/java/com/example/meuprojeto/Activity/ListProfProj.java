package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.meuprojeto.Model.Projeto;
import com.example.meuprojeto.ProfessorProjetoAdapter;
import com.example.meuprojeto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paloma on 25/01/2018.
 */



public class ListProfProj extends AppCompatActivity  {

       private TextView namePr;
       private SeekBar seekBarRating;
      private   ListView listViewprojs;
    private DatabaseReference database;

        List<Projeto> projList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_professor_projeto);

            namePr=(TextView)findViewById(R.id.namePr);
            //seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
            listViewprojs = (ListView) findViewById(R.id.listViewprojs);

            // recuperando o intent que foi passado
            Intent intent = getIntent();
            //recuperando os dados da intent
            String id= intent.getStringExtra("idProfessor");
            String name= intent.getStringExtra("nomeProf");
            String email= intent.getStringExtra("emailProf");

            database = FirebaseDatabase.getInstance().getReference("Projeto").child(id);

            projList = new ArrayList<>();

            //colocando o nome na interface
            namePr.setText(name);


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
                    }

              ProfessorProjetoAdapter adapter = new ProfessorProjetoAdapter(com.example.meuprojeto.Activity.ListProfProj.this, projList);
                    listViewprojs.setAdapter((ListAdapter) adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


