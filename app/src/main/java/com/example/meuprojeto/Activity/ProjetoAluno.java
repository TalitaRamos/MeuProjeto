package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;

import com.example.meuprojeto.AlunoAdapter;
import com.example.meuprojeto.Model.Professor;
import com.example.meuprojeto.Model.Projeto;
import com.example.meuprojeto.R;
import com.google.firebase.auth.FirebaseAuth;
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
                // a lista foi clicada, na posição indicada pelo parâmetro i
                // vamos então pegar o objeto professor correspndente a esta posição
                // na lista de professores
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
                // se entrou aqui, é porque mudou alguma coisa nos professores do banco
                // vamos limpar a lista
                //  para recuperar esses dados
                // novamente
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

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
}
