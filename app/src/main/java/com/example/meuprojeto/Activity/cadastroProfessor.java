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
import com.example.meuprojeto.HELPER.Base64custom;
import com.example.meuprojeto.HELPER.Preferencias;
import com.example.meuprojeto.Model.Acesso;
import com.example.meuprojeto.Model.Professor;
import com.example.meuprojeto.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class cadastroProfessor extends AppCompatActivity {

    private EditText nome_Prof;
    private EditText email_Prof;
    private EditText area_Prof;
    private EditText senha_Prof;
    private EditText repete_Prof;
    private Button button2;
    private int tipo;

    private Acesso acesso;
    private Professor professor;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);

        //databaseRef= FirebaseDatabase.getInstance().getReference("Professor");
        tipo = 1; // 1 porque é professor
        //MESMO NOME DOOS CAMPOS DA ACTIVITY
        nome_Prof = (EditText)findViewById(R.id.nome_Prof);
        email_Prof = (EditText)findViewById(R.id.email_Prof);
        area_Prof = (EditText)findViewById(R.id.area_Prof);
        senha_Prof = (EditText)findViewById(R.id.senha_Prof);
        repete_Prof = (EditText)findViewById(R.id.repete_Prof);
        button2 = (Button) findViewById(R.id.button2);

        //AÇÃO DO BUTTON
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(senha_Prof.getText().toString().equals(repete_Prof.getText().toString())){
                    acesso = new Acesso();
                    professor = new Professor();

                    acesso.setLogin(email_Prof.getText().toString().trim());
                    acesso.setSenha(senha_Prof.getText().toString().trim());
                    acesso.setTipo(tipo);

                    professor.setNomeProf(nome_Prof.getText().toString().trim());
                    professor.setEmailProf(email_Prof.getText().toString().trim());
                    professor.setAreaProf(area_Prof.getText().toString().trim());
                    cadastrarProfessor();
                }else{
                    Toast.makeText(cadastroProfessor.this,"As senhas não correspondem!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cadastrarProfessor(){
        autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(acesso.getLogin(), acesso.getSenha()).addOnCompleteListener(cadastroProfessor.this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(cadastroProfessor.this,"Cadastro efetuado com sucesso!",Toast.LENGTH_SHORT).show();
                    //---SALVAR ACESSO---///
                    String identificadorUsuario = Base64custom.codificarBase64(acesso.getLogin());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    acesso.setIdAcesso(identificadorUsuario);
                    professor.setIdAcessoProf(identificadorUsuario);
                    acesso.salvar();

                    //---SALVAR PROFESSOR---///T
                    String identificadorProfessor= Base64custom.codificarBase64(professor.getEmailProf());
                    FirebaseUser usuarioFire = task.getResult().getUser();
                    professor.setIdProfessor(identificadorProfessor);
                    professor.salvar();

                    //SALVAR USUARIO LOGADO
                    Preferencias preferencias = new Preferencias(cadastroProfessor.this);

                    preferencias.salvarUsuarioPreferencias(identificadorUsuario, professor.getNomeProf());
                    System.out.println("paeei por aqui1");
                    abrirTelaProfessor();
                    System.out.println("paeei por aqui");
                    //VERIFICANDO SE O USUÁRIO ESTÁ LOGADO
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String email = user.getEmail();
                        System.out.println("logado"+email);
                    } else {
                        // No user is signed in
                        System.out.println("nao logado");
                    }
                    //FIM VERIFICANDO SE O USUUÁRIO ESTÁ LOGADO
                }else{
                    String error = "";
                    try {
                        throw  task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        error = "Digite uma senha mais forte com no mínimo 8 caracteres de letras e números";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        error = "O email digitado é inválido";
                    }catch (FirebaseAuthUserCollisionException e){
                        error = "Email já cadastrado";
                    }catch (Exception e){
                        error = "Error ao efetuar o cadastro";
                        e.printStackTrace();
                    }

                    Toast.makeText(cadastroProfessor.this,"Erro = "+error,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaProfessor(){
        Intent intentabrirTelaProfessor = new Intent(cadastroProfessor.this, cadastroProjeto.class);
        startActivity(intentabrirTelaProfessor);
        //finish();
    }
}
