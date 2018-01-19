package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private EditText login; //o login é um email
    private EditText senha;
    private Button button;
    private FirebaseAuth autenticacao;
    private Acesso acesso;

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
                    abrirTelaProjeto();
                    Toast.makeText(MainActivity.this,"Login efetuado com sucesso!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Usuário ou senha inválidos!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaProjeto(){
        Intent intentabrirTelaProjeto = new Intent(MainActivity.this, Project_prof.class);
        startActivity(intentabrirTelaProjeto);
    }
    //-------FIM NAVEGAR PRA TELA PROJETO------

    //NAVEGAR PRA TELA DE CADASTRO
    public void startActivity(View view) {
        Intent cadastro = new Intent(this, cadastro.class);
        startActivity(cadastro);
    }

}


