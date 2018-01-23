package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.meuprojeto.Model.Professor;
import com.example.meuprojeto.Model.Projeto;
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

public class cadastroProjeto extends AppCompatActivity {
    private EditText  nomeProj;
    private EditText descrProj;
    private Spinner status_spinner;
    private Button cadaProjec;

    private Projeto projeto;
    DatabaseReference meuProjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_projeto);
        meuProjeto = FirebaseDatabase.getInstance().getReference("Projeto");

        nomeProj = (EditText) findViewById(R.id.nomeProj);
        descrProj = (EditText) findViewById(R.id.descrProj);
        status_spinner = (Spinner)findViewById(R.id.Status_spinner);
        cadaProjec = (Button)findViewById(R.id.cadaProjec);

        cadaProjec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final List<Professor> profList = new ArrayList<>();
                FirebaseDatabase.getInstance().getReference().child("Professor")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String iden="f"; String email="r";
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if(user !=null){
                                    email = user.getEmail();
                                }
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    Professor prof = snapshot.getValue(Professor.class);
                                    profList.add(prof);
                                }
                                for(int i=0;i<profList.size();i++){
                                    if(profList.get(i).getEmailProf().equals(email)){
                                        iden = profList.get(i).getIdProfessor();
                                        String nome = nomeProj.getText().toString().trim();
                                        String descr = descrProj.getText().toString().trim();
                                        String status = status_spinner.getSelectedItem().toString();

                                        if(!TextUtils.isEmpty(nome)){
                                            String id = meuProjeto.push().getKey();
                                            Projeto projeto = new Projeto(id,nome,descr,status,iden);
                                            meuProjeto.child(id).setValue(projeto);
                                            VerProject();
                                            Toast.makeText(cadastroProjeto.this, "Projeto Adicionado", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(cadastroProjeto.this, "Você deve digitar um nome", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }


    //NAVEGAR PRA TELA DE PROJETOS
    public void VerProject(){
        Intent in = new Intent(cadastroProjeto.this,Project_prof.class);
        startActivity(in);
    }
}
