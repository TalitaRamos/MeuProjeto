package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.meuprojeto.AlunoProjetoAdapter;
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
        String name= intent.getStringExtra("nomeProf");
        String email= intent.getStringExtra("emailProf");

        database = FirebaseDatabase.getInstance().getReference("Projeto").child(id);

        projList = new ArrayList<>();

        //colocando o nome na interface
        nameP.setText(name);

        
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

                AlunoProjetoAdapter adapter = new AlunoProjetoAdapter(ListAlunoProjeto.this, projList);
                listViewTracks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
