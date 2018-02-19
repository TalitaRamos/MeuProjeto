package com.example.meuprojeto.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.meuprojeto.AlunoAdapter;
import com.example.meuprojeto.Model.Acesso;
import com.example.meuprojeto.Model.Aluno;
import com.example.meuprojeto.Model.Candidato;
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

public class ProjetoAluno extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //TELA PRINCIPAL DO ALUNO

    DatabaseReference databaseProjetos;
    // para acessar nossa nova ListView
    ListView listViewProf;

    // lista para armazenar os objetos "Professor" que leremos do banco de dados
    // obs: por enquanto, só a referência...
    List<Professor> profList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_aluno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.

       /* FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email="e";
        if(user !=null){
            email = user.getEmail();
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(email);*/
        //apontando para o nó projeto
        databaseProjetos= FirebaseDatabase.getInstance().getReference("Professor");
        //acessando a nova LiestView no arquivo de layout
        listViewProf=(ListView)findViewById(R.id.listViewProject);

        //lista com os professores
        profList = new ArrayList<>();

        // definindo um listener para chamar a activity dos projetos, quando
        // for clicado um Professor na lista de Professores

        listViewProf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Professor professor = profList.get(i);
                // vamos chamar a nova activity, para trabalhar com os projetos
                Intent intent = new Intent(getApplicationContext(),ListAlunoProjeto.class);
                // guardando o nome, id e email do professor na intent, para ser recuperada
                // pela nova activity
                intent.putExtra("nomeProf", professor.getNomeProf());
                intent.putExtra("idProfessor", professor.getIdProfessor());
                intent.putExtra("emailProf", professor.getEmailProf());

                startActivity(intent);
            }
        });


    }

    @Override
    protected  void onStart(){
        super.onStart();

        // criando um tratador de eventos relacionado com nosso
        // banco de dados Firebase
        databaseProjetos.addValueEventListener(new ValueEventListener() {
            // método chamado automaticamente quando houver mudança nos dados
            // armazenados no firebase
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // vamos limpar a lista
                //  para recuperar esses dados novamente
                profList.clear();

                // Vamos então "varrer" esse objeto, pegando os dados lá dentro
                // e criando objetos da nossa classe Artist, para colocar na lista
                for(DataSnapshot projetoSnapShot:dataSnapshot.getChildren()){
                    Professor prof = projetoSnapShot.getValue(Professor.class);
                    profList.add(prof);
                }
                AlunoAdapter adapter = new AlunoAdapter(ProjetoAluno.this, profList);
                listViewProf.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //ALTERAR CADASTRO DE ALUNO
    private boolean updateProjeto(final String idAluno, String nomeAluno, final String emailAluno, String cursoAluno, final String fkAcessoAluno, String matricula, String senha){
        DatabaseReference database= FirebaseDatabase.getInstance().getReference("Aluno").child(idAluno);
        Aluno alun = new Aluno(idAluno,nomeAluno,emailAluno,cursoAluno,fkAcessoAluno,matricula);
        database.setValue(alun);

        if(TextUtils.isEmpty(senha)){
            System.out.println("campo vazio");

            final List<Acesso> acesList = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("Acesso").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Acesso acs = snapshot.getValue(Acesso.class);
                                acesList.add(acs);
                            }

                            for(int i=0;i<acesList.size();i++){
                                if(acesList.get(i).getIdAcesso().equals(idAluno)){
                                    // System.out.println("info"+projList.get(i).getNome());
                                    String sen = acesList.get(i).getSenha();
                                    System.out.println("senha-"+sen);
                                    final DatabaseReference data= FirebaseDatabase.getInstance().getReference("Acesso").child(fkAcessoAluno);
                                    Acesso acesso = new Acesso(fkAcessoAluno, emailAluno,sen, 2);
                                    data.setValue(acesso);
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    user.updatePassword(sen);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }else{
            System.out.println("campo preenchido");
            final DatabaseReference data= FirebaseDatabase.getInstance().getReference("Acesso").child(fkAcessoAluno);
            Acesso acesso = new Acesso(fkAcessoAluno, emailAluno,senha, 2);
            data.setValue(acesso);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.updatePassword(senha);
        }


        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(emailAluno);
        user.sendEmailVerification();


        //ALTERAR O EMAIL DO CANDIDATO LOGADO
        final List<Candidato> candList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Candidato").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Candidato cand = snapshot.getValue(Candidato.class);
                            candList.add(cand);
                        }

                        for(int i=0;i<candList.size();i++){
                            if(candList.get(i).getIdAluno().equals(idAluno)){
                                DatabaseReference dase= FirebaseDatabase.getInstance().getReference("Candidato").child(candList.get(i).getIdCandidato());
                                Candidato cad = new Candidato(candList.get(i).getIdCandidato(), candList.get(i).getData(), candList.get(i).getSituacao(),
                                        candList.get(i).getIdProjeto(), candList.get(i).getIdAluno(), emailAluno, candList.get(i).getIdProfessor());
                                dase.setValue(cad);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        return true;
    }
    //EXIBE E CHAMA A FUNÇÃO DE ATUALIZAÇÃO DO CADASTRO
    private void showUpdateAluno(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_cad_aluno,null);
        dialogBuilder.setView(dialogView);

        final EditText nome_Alun_alt= (EditText)dialogView.findViewById(R.id.nome_Alun_alt);
        final EditText email_Alun_alt = (EditText) dialogView.findViewById(R.id.email_Alun_alt);
        final EditText matric_Alun_alt = (EditText) dialogView.findViewById(R.id.matric_Alun_alt);
        final EditText curso_Alun_alt = (EditText) dialogView.findViewById(R.id.curso_Alun_alt);
        final EditText senha_Alun_alt = (EditText) dialogView.findViewById(R.id.senha_Alun_alt);
        final EditText repete_Alun_alt = (EditText)dialogView.findViewById(R.id.repete_Alun_alt);
        final Button button_up_alun_alt = (Button) dialogView.findViewById(R.id.button_up_alun_alt);

        final List<Aluno>aluLista = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Aluno")
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
                            Aluno alu = snapshot.getValue(Aluno.class);
                            aluLista.add(alu);
                        }

                        for(int i=0; i<aluLista.size();i++){
                            //
                            //System.out.println("emails--"+profLista.get(i).getEmailProf()+"--email--"+email);
                            if(aluLista.get(i).getEmailAluno().equals(email)){
                                // System.out.println("entrei if");
                                nome_Alun_alt.setText(aluLista.get(i).getNomeAluno());
                                email_Alun_alt.setText(email);
                                matric_Alun_alt.setText(aluLista.get(i).getMatricula());
                                curso_Alun_alt.setText(aluLista.get(i).getCursoAluno());

                                final String idalun = aluLista.get(i).getIdAluno();
                                final String idAces = aluLista.get(i).getFkAcessoAluno();

                                dialogBuilder.setTitle("Alterar cadastro");
                                final AlertDialog b = dialogBuilder.create();
                                b.show();

                                button_up_alun_alt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String name = nome_Alun_alt.getText().toString().trim();
                                        String ema = email_Alun_alt.getText().toString().trim();
                                        String mat = matric_Alun_alt.getText().toString().trim();
                                        String cur = curso_Alun_alt.getText().toString().trim();
                                        String senh = senha_Alun_alt.getText().toString().trim();
                                        String repete = repete_Alun_alt.getText().toString().trim();
                                        //if(TextUtils.isEmpty(senh)||TextUtils.isEmpty(repete)){


                                        //}
                                        if (senh.equals(repete)) {
                                            updateProjeto(idalun, name, ema, cur, idAces, mat, senh);
                                            b.dismiss();
                                            Toast.makeText(ProjetoAluno.this,"Cadastro atualizado",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(ProjetoAluno.this,"As senhas não correspondem!",Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.projeto_aluno, menu);
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
            //alterar cadastro
            showUpdateAluno();
        } else if (id == R.id.nav_gallery) {
            //deletar cadastro

        } else if (id == R.id.nav_slideshow) {
           // solicitaçoes
            alunoSoli();
        } else if (id == R.id.nav_manage) {

            //share
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
        Intent intent = new Intent(ProjetoAluno.this, MainActivity.class);
        startActivity(intent);
        //finish();
    }

    public void alunoSoli(){

        Intent intent = new Intent(ProjetoAluno.this, SolicitacaoAluno.class);

        startActivity(intent);
        //finish();
    }

    public void info(){
        Intent intent = new Intent(ProjetoAluno.this, InfoAluno.class);
        //System.out.println("entrei");
        startActivity(intent);
        //System.out.println("entrei2");
    }
}
