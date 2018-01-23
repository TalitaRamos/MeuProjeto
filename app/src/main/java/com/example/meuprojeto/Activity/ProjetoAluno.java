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
import android.widget.ListView;

import com.example.meuprojeto.AlunoAdapter;
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

    DatabaseReference databaseProjetos;
    // para acessar nossa nova ListView
    ListView listViewProjeto;

    // lista para armazenar os objetos "Projeto" que leremos do banco de dados
    // obs: por enquanto, só a referência...
    List<Projeto> projetoList;

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
        databaseProjetos= FirebaseDatabase.getInstance().getReference("Projeto");
        //acessando a nova LiestView no arquivo de layout
        listViewProjeto=(ListView)findViewById(R.id.listViewProject);

        // efetivamente criando a lista que vai armazenar os artistas
        // que leremos do banco de dados
        projetoList = new ArrayList<>();
        // lembrando o ciclo de vida de um app android, o primeiro método
        // que é executado é o onCreate, geralmente para carregar os layouts
        // e inicializar as coisas.
        //
        // logo a seguir, é chamado o método onStart. Este método também é chamado
        // quando o app estava em background e volta a ter foco. Logo, colocaremos aqui
        // o código para recuperar os dados do firebase e colocar na lista,
        // pois pode ter havido alteração nos dados quando o app estava em background
        // (por outra cópia em execução do nosso app, por exemplo).
    }

    @Override
    protected  void onStart(){
        super.onStart();

        // criando um tratador de eventos relacionado com nosso
        // banco de dados Firebase
        databaseProjetos.addValueEventListener(new ValueEventListener() {
            // método chamado automaticamente quando houver mudança nos dados
            // armazenados no firebase
            // lembre-se!! databaseArtist "aponta" para a chave "Projeto" no
            // JSON dos dados no firebase. Então, se algo mudar "ali dentro",
            // (isto é, dados de artistas), este método será chamado.
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // se entrou aqui, é porque mudou alguma coisa nos artistas
                // que estão no banco de dados. Então, vamos limpar a lista
                // que armazena esses artistas, para recuperar esses dados
                // novamente (já que não sabemos exatamente o que mudou)
                projetoList.clear();
                // Recebemos um objeto DataSnapshot, que tem os dados
                // apontados por nossa referencia no firebase, isto é,
                // os dados que estão "debaixo" da chave "Artists".
                // Vamos então "varrer" esse objeto, pegando os dados lá dentro
                // e criando objetos da nossa classe Artist, para colocar na lista
                for(DataSnapshot projetoSnapShot:dataSnapshot.getChildren()){
                    //artistSnapshot tem um dos "filhos" de "Projeto", isto é,
                    // tem os dados de um ptojeto.
                    // Vamos então criar um objeto artista, a partir desses dados
                    //
                    // ... getValue(Artist.class)
                    //     pegue os dados, e a partir deles crie um objeto da
                    //     classe Artist.
                    Projeto project = projetoSnapShot.getValue(Projeto.class);
                    // enfim, colocamos o objeto artista criado a partir dos dados lidos
                    // na nossa lista de artistas
                    projetoList.add(project);
                }

                // agora que temos nossa lista de artistas atualizada,
                // podemos criar o adapter que vai ser responsável por
                // colocar esses dados no ListView,
                // passando nossa lista para este adapter
                AlunoAdapter adapter = new AlunoAdapter(ProjetoAluno.this, projetoList);
                // finalmente, informamos ao ListView quem é o adapter que vai
                // exibir os dados
                listViewProjeto.setAdapter(adapter);
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
