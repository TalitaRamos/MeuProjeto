package com.example.meuprojeto.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meuprojeto.Model.Acesso;
import com.example.meuprojeto.Model.Professor;
import com.example.meuprojeto.Model.Projeto;
import com.example.meuprojeto.ProfessorAdapter;
import com.example.meuprojeto.ProfessorProjetoAdapter;
import com.example.meuprojeto.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class Project_prof extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference databaseProjetos;
    DatabaseReference database;
    // para acessar nossa nova ListView
    ListView listViewProf;
//para atualizar proj
    // ListView listViewProjetos;

    // lista para armazenar os objetos "Professor" que leremos do banco de dados

    List<Projeto>projetoList;
    String iden="e";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_prof);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();

                Intent it = new Intent(Project_prof.this, cadastroProjeto.class);
                startActivity(it);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        databaseProjetos = FirebaseDatabase.getInstance().getReference("Projeto");





        //apontando para o nó projeto
        //databaseProjetos= FirebaseDatabase.getInstance().getReference("Projeto").child(id);
        //acessando a nova ListView no arquivo de layout
        listViewProf=(ListView)findViewById(R.id.listViewProjectProf);

        //lista com os professores
        projetoList = new ArrayList<>();


        listViewProf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Projeto projeto = projetoList.get(i);
                showUpdateDeleteDialog(projeto.getIdProjeto(), projeto.getNome(),projeto.getIdProfessor(),projeto.getStatus(),projeto.getDescricao());

            }
        });
    }


    @Override
    protected void onStart() {

        super.onStart();

        final List<Professor> profList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Professor")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //String iden="f";
                        String email="r";
                        //pegar email do usuario logado
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user !=null){
                            email = user.getEmail();
                        }
                        //adicionar professores na lista
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Professor prof = snapshot.getValue(Professor.class);
                            profList.add(prof);
                        }

                        for(int i=0;i<profList.size();i++){
                            //se o email for igual ao email do usuario logado
                            //então é
                            if(profList.get(i).getEmailProf().equals(email)){
                                //pegar informações do projeto
                                iden = profList.get(i).getIdProfessor();
                                System.out.println("identif1"+iden);
                                //definir que quando o projeto for gravado vai ser gravado abaixo do id do professor
                                database = FirebaseDatabase.getInstance().getReference("Projeto").child(iden);

                                ///===============================================================================
                                // criando um tratador de eventos relacionado com nosso
                                // banco de dados Firebase
                                database.addValueEventListener( new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        projetoList.clear();

                                        for(DataSnapshot projetoSnapShot : dataSnapshot.getChildren()) {
                                            Projeto proj = projetoSnapShot.getValue(Projeto.class);

                                            // enfim, colocamos o objeto trilha criado a partir dos dados lidos
                                            // na nossa lista de trilhas
                                            projetoList.add(proj);
                                        }

                                        ProfessorProjetoAdapter adapter = new ProfessorProjetoAdapter(Project_prof.this,projetoList);

                                        // finalmente, informamos ao ListView quem é o adapter que vai
                                        // exibir os dados
                                        listViewProf.setAdapter(adapter);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }

                                });
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //System.out.println("identif2"+iden);


    }

    private boolean updateProjeto(String id, String name, String descr, String status, String idProf) {
        //pegando a referncia de projeto
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Projeto").child(idProf).child(id);

        //atualizando Projeto
        Projeto projeto = new Projeto(id, name,descr, status,idProf);
        dR.setValue(projeto);
        Toast.makeText(getApplicationContext(), "Projeto Atualizado", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteProj(String id, String name, String descr, String status, String idProf) {
        //Pegando a referencia de projeto
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Projeto").child(idProf).child(id);

        //removing projeto
        dR.removeValue();

        //pegando ref pra candidatodo projeto
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("Candidato").child(idProf).child(id);

        //removing all tracks
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Projeto Deletado!", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showUpdateDeleteDialog(final String projetoId, String projetoName, final String isProfessor,String status, String descricao) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextDescr = (EditText) dialogView.findViewById(R.id. editTextDescr);
        final Spinner spinner_status = (Spinner) dialogView.findViewById(R.id.Status_spinner);

        editTextName.setText(projetoName);
        editTextDescr.setText(descricao);

        if(status.equals("Proj. Alocado")){
            spinner_status.setSelection(0);
        }else if(status.equals("Proj. Não Alocado")){
            spinner_status.setSelection(1);
        }

        ///----------------------------------

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(projetoName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String descr = editTextDescr.getText().toString().trim();

                String status=  spinner_status.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateProjeto(projetoId,name,descr,status,isProfessor);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String descr = editTextDescr.getText().toString().trim();

                String status = spinner_status.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {

                    deleteProj(projetoId, name, descr, status, isProfessor);
                    b.dismiss();
                }

            }
        });
    }

    private boolean updateProfCad(String idProfessor, String nomeProf, String emailProf, String areaProf, String idAcessoProf, String matricula, String senha){
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Professor").child(idProfessor);
        Professor prof = new Professor(idProfessor, nomeProf,emailProf,areaProf,idAcessoProf,matricula);
        database.setValue(prof);

        DatabaseReference data= FirebaseDatabase.getInstance().getReference("Acesso").child(idAcessoProf);
        Acesso acesso = new Acesso(idAcessoProf, emailProf,senha, 1);
        data.setValue(acesso);

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(emailProf);
        user.sendEmailVerification();
        user.updatePassword(senha);

        return true;
    }


    private void showUpdateProf(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updat_cad_prof,null);
        dialogBuilder.setView(dialogView);

        final EditText nome_Prof_alt= (EditText)dialogView.findViewById(R.id.nome_Prof_alt);
        final EditText email_Prof_alt = (EditText) dialogView.findViewById(R.id.email_Prof_alt);
        final EditText matric_Prof_alt = (EditText) dialogView.findViewById(R.id.matric_Prof_alt);
        final EditText area_Prof_alt = (EditText) dialogView.findViewById(R.id.area_Prof_alt);
        final EditText senha_Prof_alt = (EditText) dialogView.findViewById(R.id.senha_Prof_alt);
        final EditText repete_Prof_alt = (EditText)dialogView.findViewById(R.id.repete_Prof_alt);
        final Button button_up_prof = (Button) dialogView.findViewById(R.id.button_up_prof);

        final List<Professor> profLista = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Professor")
                .addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String email="f";

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user!=null){
                            email=user.getEmail();
                            System.out.println("email"+email);

                        }

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Professor prof = snapshot.getValue(Professor.class);
                            profLista.add(prof);
                        }

                        for(int i=0; i<profLista.size();i++){
                            //
                            //System.out.println("emails--"+profLista.get(i).getEmailProf()+"--email--"+email);
                            if(profLista.get(i).getEmailProf().equals(email)){
                               // System.out.println("entrei if");
                                nome_Prof_alt.setText(profLista.get(i).getNomeProf());
                                email_Prof_alt.setText(email);
                                matric_Prof_alt.setText(profLista.get(i).getMatricula());
                                area_Prof_alt.setText(profLista.get(i).getAreaProf());

                                final String idpr = profLista.get(i).getIdProfessor();
                                final String idAces = profLista.get(i).getIdAcessoProf();

                                dialogBuilder.setTitle("Alterar cadastro");
                                final AlertDialog b = dialogBuilder.create();
                                b.show();

                                button_up_prof.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String name = nome_Prof_alt.getText().toString().trim();
                                        String ema = email_Prof_alt.getText().toString().trim();
                                        String mat = matric_Prof_alt.getText().toString().trim();
                                        String area = area_Prof_alt.getText().toString().trim();
                                        String senh = senha_Prof_alt.getText().toString().trim();
                                        String repete = repete_Prof_alt.getText().toString().trim();
                                        if (senh.equals(repete)) {
                                            updateProfCad(idpr, name, ema, area, idAces, mat, senh);
                                            b.dismiss();
                                            Toast.makeText(Project_prof.this,"Cadastro atualizado",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Project_prof.this,"As senhas não correspondem!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                //FIM ADICIONAR O ALUNO
                            }else{
                                //Toast.makeText(Project_prof.this,"Não foi possível atualizar o seu cadastro. Tente novamente.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }



    private boolean deleteProfCad(String idProf, String idAceProf){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Professor").child(idProf);
        dR.removeValue();
        DatabaseReference dRr = FirebaseDatabase.getInstance().getReference("Acesso").child(idAceProf);
        dRr.removeValue();

        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("Projeto").child(idProf);
        drTracks.removeValue();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete();

        Toast.makeText(getApplicationContext(), "Conta deletada", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project_prof, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            showUpdateProf();
        } else if (id == R.id.nav_gallery) {
            //solicitaçoes
            abrirSolicitacao();
        } else if (id == R.id.nav_slideshow) {
            //aluno alocado
            listarAlocado();
        } else if (id == R.id.nav_manage) {

            //deletar conta
        } else if (id == R.id.nav_share) {
            info();

        } else if (id == R.id.nav_send) {
            //CHECAR SE ESTÁ FUNCIONANDO
            FirebaseAuth.getInstance().signOut();
            sair();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sair(){
        Intent intent = new Intent(Project_prof.this, MainActivity.class);
        startActivity(intent);
        //finish();
    }

    public void abrirTelaProjeto(){
        Intent intent = new Intent(Project_prof.this, cadastroProjeto.class);
        //System.out.println("entrei");
        startActivity(intent);
       // System.out.println("entrei2");
    }
    public void abrirSolicitacao(){
        Intent intent = new Intent(Project_prof.this, SolicitacaoProfessor.class);
        //System.out.println("entrei");
        startActivity(intent);
        //System.out.println("entrei2");
    }

    public void listarAlocado(){
        Intent intent = new Intent(Project_prof.this, listarAlocacaoProfessor.class);
        //System.out.println("entrei");
        startActivity(intent);
        //System.out.println("entrei2");
    }

    public void info(){
        Intent intent = new Intent(Project_prof.this, Info.class);
        //System.out.println("entrei");
        startActivity(intent);
        //System.out.println("entrei2");
    }

}
