package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meuprojeto.Model.Alocacao;
import com.example.meuprojeto.Model.Aluno;
import com.example.meuprojeto.Model.Candidato;
import com.example.meuprojeto.Model.Projeto;
import com.example.meuprojeto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.support.v7.app.AlertDialog.*;

public class detalhamentoSolicitacaoProf extends AppCompatActivity {

    TextView proje_name;
    TextView alu_name_curso;
    TextView aluno_email;
    TextView data;
    TextView status_soli;
    TextView alu_curso;
    ImageButton button_update;
    //----------
    String noAlun=";f;";
    String noProj=";q;";
    String curAl=";r;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhamento_solicitacao_prof);

        proje_name = (TextView)findViewById(R.id.proje_name);
        alu_name_curso = (TextView)findViewById(R.id.alu_name_curso);
        aluno_email = (TextView)findViewById(R.id.aluno_email);
        data = (TextView)findViewById(R.id.data);
        status_soli = (TextView)findViewById(R.id.status_soli);
        alu_curso = (TextView)findViewById(R.id.alu_curso);
        button_update = (ImageButton)findViewById(R.id.button_update);

        Intent intent = getIntent();

        String dataS = intent.getStringExtra("data");
        String email = intent.getStringExtra("emailAluno");
        final String idAluno = intent.getStringExtra("idAluno");
        String idCandidato = intent.getStringExtra("idCandidato");
        String idProfessor = intent.getStringExtra("idProfessor");
        final String idProjeto = intent.getStringExtra("idProjeto");
        String situacao = intent.getStringExtra("situacao");

        //proje_name.setText(idProjeto);
        //alu_name_curso.setText("Aluno:" +idAluno);
        //alu_curso.setText("Curso: ");
        aluno_email.setText("Email: "+email);
        data.setText("Solicitado em: "+dataS);
        status_soli.setText("Status: "+situacao);
        //===========================================================
        final List<Aluno> aluL = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Aluno")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Aluno al=snapshot.getValue(Aluno.class);
                            aluL.add(al);
                        }
                        for(int i=0;i<aluL.size();i++){
                            if(aluL.get(i).getIdAluno().equals(idAluno)){
                                noAlun=aluL.get(i).getNomeAluno();
                                curAl=aluL.get(i).getCursoAluno();
                                alu_name_curso.setText("Aluno: " +noAlun);
                                alu_curso.setText("Curso: "+curAl);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        //==================================================================
        final List<Projeto> projList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Projeto").child(idProfessor)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Projeto j = snapshot.getValue(Projeto.class);
                            projList.add(j);
                        }

                        for(int i=0;i<projList.size();i++){
                            if(projList.get(i).getIdProjeto().equals(idProjeto)){
                                // System.out.println("info"+projList.get(i).getNome());
                                noProj=projList.get(i).getNome();
                                proje_name.setText(noProj);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                String idCandidato = intent.getStringExtra("idCandidato");
                //System.out.println("candi2"+idCandidato);
                showUpdateDialog(idCandidato);
            }
        });
    }

    private boolean updateCandidato(String idcandidato,String situ){

        Intent intent = getIntent();

        String data = intent.getStringExtra("data");
        String emails = intent.getStringExtra("emailAluno");
        String idAl = intent.getStringExtra("idAluno");
        String idCandi = intent.getStringExtra("idCandidato");
        String idPro = intent.getStringExtra("idProfessor");
        String idProj = intent.getStringExtra("idProjeto");
        //===========================================================
        //System.out.println("candiupd: "+idCandi);
        //System.out.println("statusipd: "+situ);
        DatabaseReference database =FirebaseDatabase.getInstance().getReference("Candidato").child(idcandidato);
        Candidato candi = new Candidato(idcandidato,data,situ,idProj,idAl,emails,idPro);
        database.setValue(candi);

        if(situ.equals("Requerido")){
            salvarAlocado(idProj,idAl,idPro, idCandi);
        }else{
            Toast.makeText(detalhamentoSolicitacaoProf.this, "Status atualizado", Toast.LENGTH_LONG).show();
        }
        return  true;
    }

    private boolean salvarAlocado(String idProj, String idAlun, String idProf, String idCandi){
        if(!TextUtils.isEmpty(idProj)&&!TextUtils.isEmpty(idAlun)&&!TextUtils.isEmpty(idProf)){

            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy");
            String data = sdf.format(date);
            DatabaseReference alocacao = FirebaseDatabase.getInstance().getReference("Alocacao");
            String idAlocacao = alocacao.push().getKey();

            Alocacao aloca = new Alocacao(idAlocacao,data, idProj,idAlun,idProf,idCandi);
            alocacao.child(idAlocacao).setValue(aloca);
            Toast.makeText(detalhamentoSolicitacaoProf.this, "O candidato foi alocado ao projeto!", Toast.LENGTH_LONG).show();

            return true;
        }

        Toast.makeText(detalhamentoSolicitacaoProf.this, "O canditato não pode ser alocado ao projeto. tente novamente", Toast.LENGTH_LONG).show();
        return false;
    }

    private void showUpdateDialog(final String iCandidato){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_soli_candidato,null);
        dialogBuilder.setView(dialogView);

        final Spinner Status_soli_spinner = (Spinner)dialogView.findViewById(R.id.Status_soli_spinner);
        final Button button_update_soli = (Button) dialogView.findViewById(R.id.button_update_soli);

        dialogBuilder.setTitle("Escolha o status: ");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        button_update_soli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status = Status_soli_spinner.getSelectedItem().toString();
                //System.out.println("candi3"+iCandidato);
               // System.out.println("status"+status);
                if(!TextUtils.isEmpty(status)){
                    //System.out.println("candi1"+iCandidato);
                    updateCandidato(iCandidato,status);
                    b.dismiss();
                }
            }
        });
    }
}
