package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meuprojeto.DAO.ConfiguracaoFirebase;
import com.example.meuprojeto.Model.Acesso;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //TELA DE LOGIN

    private EditText login; //o login é um email
    private EditText senha;
    private Button button;
    private FirebaseAuth autenticacao;
    private Acesso acesso;
    List<Acesso> acessoLista;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText)findViewById(R.id.login);
        senha = (EditText)findViewById(R.id.senha);
        button = (Button)findViewById(R.id.button);

        //--------NAVEGAR PRA TELA DE PROJETO-------
        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(!login.getText().toString().equals("")&& !senha.getText().toString().equals("")){
                    acesso = new Acesso();
                    acesso.setLogin(login.getText().toString());
                    acesso.setSenha(senha.getText().toString());
                    validarLogin();
                }else{
                    Toast.makeText(MainActivity.this, "Preencha os campos de email e senha!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void validarLogin(){
        autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(acesso.getLogin(),acesso.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //INICIO Verificar tipo de conta
                    //criar lista
                    final List<Acesso> acessoList = new ArrayList<>();
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Acesso")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int tip=8; String email="r";
                                    //pegar email do usuario logado
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        email = user.getEmail();
                                    } else {
                                        // No user is signed in
                                    }

                                    //adicionar usuarios na lista
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        Acesso acess = snapshot.getValue(Acesso.class); // this is your user
                                        acessoList.add(acess); // add to list
                                    }

                                    //pegar tipo do usuario logado
                                    for(int i=0;i<acessoList.size();i++){
                                        if(acessoList.get(i).getLogin().equals(email)){
                                            tip=acessoList.get(i).getTipo();
                                        }
                                    }

                                    if(tip==1){
                                        abrirTelaProjetoProf();
                                    }else if(tip==2){
                                        abrirTelaProjetoAlun();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                    //fim verificar tipo de conta

                    Toast.makeText(MainActivity.this,"Login efetuado com sucesso!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Usuário ou senha inválidos!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaProjetoProf(){
        Intent intentabrirTelaProjeto = new Intent(MainActivity.this, Project_prof.class);
        startActivity(intentabrirTelaProjeto);
    }

    public void abrirTelaProjetoAlun(){
        Intent intentabrirTelaProjeto = new Intent(MainActivity.this, ProjetoAluno.class);
        startActivity(intentabrirTelaProjeto);
    }

    //-------FIM NAVEGAR PRA TELA PROJETO------


    //NAVEGAR PRA TELA DE CADASTRO
    public void startActivity(View view) {
        Intent cadastro = new Intent(this, cadastro.class);
        startActivity(cadastro);
    }

}


