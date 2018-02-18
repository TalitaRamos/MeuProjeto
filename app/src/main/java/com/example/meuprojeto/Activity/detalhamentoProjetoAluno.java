package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meuprojeto.Model.Alocacao;
import com.example.meuprojeto.Model.Aluno;
import com.example.meuprojeto.Model.Candidato;
import com.example.meuprojeto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class detalhamentoProjetoAluno extends AppCompatActivity {
    //Mostra os detalhes do projeto quando o aluno seleciona um
    //atraves dessa tela o aluno também pode enviar uma solicitação para o professor
    //http://starfightercarlao.blogspot.com.br/2017/06/crud-basico-com-android-e-firebase_17.html

    TextView name_proje;
    TextView desc_proje;
    TextView status_proje;
    Button button_candidatar;

    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhamento_projeto_aluno);

        name_proje = (TextView)findViewById(R.id.name_proje);
        desc_proje = (TextView)findViewById(R.id.desc_proje);
        status_proje = (TextView)findViewById(R.id.status_proje);
        button_candidatar = (Button)findViewById(R.id.button_candidatar);

        Intent intent = getIntent();

        String nomeProj = intent.getStringExtra("nome");
        String desc = intent.getStringExtra("descricao");
        String idProj = intent.getStringExtra("idProjeto");
        String idProf = intent.getStringExtra("idProfessor");
        final String status = intent.getStringExtra("status");

        name_proje.setText("Projeto: "+nomeProj);
        desc_proje.setText("Descrição: "+desc);
        status_proje.setText("Status: "+status);



        button_candidatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("Proj. Alocado")){
                    Toast.makeText(detalhamentoProjetoAluno.this,"Impossível realizar solicitação. O projeto já está alocado.",Toast.LENGTH_SHORT).show();

                }else {
                    //INICIO ALOCAR O ALUNO COM SITUAÇÃO "SOLICITADO"
                    final List<Aluno> alunoLista = new ArrayList<>();
                    FirebaseDatabase.getInstance().getReference().child("Aluno")
                            .addListenerForSingleValueEvent(new ValueEventListener(){
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String identiAlun="f"; String email="f";

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if(user!=null){
                                        email=user.getEmail();
                                        // System.out.println("email"+email);
                                    }

                                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        Aluno alun = snapshot.getValue(Aluno.class);
                                        alunoLista.add(alun);
                                    }

                                    for(int i=0; i<alunoLista.size();i++){
                                        //System.out.println("emails"+alunoLista.get(i).getEmailAluno());
                                        if(alunoLista.get(i).getEmailAluno().equals(email)){
                                            // System.out.println("entrei2");
                                            identiAlun = alunoLista.get(i).getIdAluno();
                                            //System.out.println("testantoid"+identi);

                                            Intent intent = getIntent();
                                            String idProj = intent.getStringExtra("idProjeto");
                                            String idProf = intent.getStringExtra("idProfessor");
                                            // System.out.println("testantoid"+idProj);
                                            database = FirebaseDatabase.getInstance().getReference("Candidato");
                                            //.child(identi);
                                            String id=database.push().getKey();
                                            String situacao = "Solicitado";

                                            long date = System.currentTimeMillis();
                                            SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy");
                                            String data = sdf.format(date);
                                            //System.out.println("testanto"+data +"projeto"+idProj);

                                            Candidato candidato = new Candidato(id,data,situacao,idProj,identiAlun,email,idProf);
                                            database.child(id).setValue(candidato);
                                            Toast.makeText(detalhamentoProjetoAluno.this,"Você se candidatou ao projeto!",Toast.LENGTH_SHORT).show();

                                            //FIM ADICIONAR O ALUNO
                                        }else{
                                           // Toast.makeText(detalhamentoProjetoAluno.this,"Email inválido",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
            }
        });
    }
}
