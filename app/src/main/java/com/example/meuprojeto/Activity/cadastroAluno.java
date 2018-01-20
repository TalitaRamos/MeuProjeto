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
import com.example.meuprojeto.Model.Aluno;
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

public class cadastroAluno extends AppCompatActivity {

    private EditText nome_Alun;
    private EditText email_Alun;
    private EditText curso_Alun;
    private EditText senha_Alun;
    private EditText repete_Alun;
    private Button button_cad_alun;
    private int tipo;

    private Acesso acesso;
    private Aluno aluno;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        tipo =2;// 2 porque é aluno
        //MESMO NOME DOOS CAMPOS DA ACTIVITY
        nome_Alun = (EditText)findViewById(R.id.nome_Alun);
        email_Alun = (EditText)findViewById(R.id.email_Alun);
        curso_Alun = (EditText)findViewById(R.id.curso_Alun);
        senha_Alun = (EditText)findViewById(R.id.senha_Alun);
        repete_Alun = (EditText)findViewById(R.id.repete_Alun);
        button_cad_alun = (Button) findViewById(R.id.button_cad_alun);

        //AÇÃO DO BUTTON

        button_cad_alun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (senha_Alun.getText().toString().equals(repete_Alun.getText().toString())){
                    acesso = new Acesso();
                    aluno = new Aluno();
                    acesso.setLogin(email_Alun.getText().toString());
                    acesso.setSenha(senha_Alun.getText().toString());
                    acesso.setTipo(tipo);
                    aluno.setNomeAluno(nome_Alun.getText().toString());
                    aluno.setEmailAluno(email_Alun.getText().toString());
                    aluno.setCursoAluno(curso_Alun.getText().toString());
                    cadastrarAluno();
                }else{
                    Toast.makeText(cadastroAluno.this,"As senhas não correspondem!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cadastrarAluno(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(acesso.getLogin(),acesso.getSenha()).addOnCompleteListener(cadastroAluno.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(cadastroAluno.this,"Cadastro efetuado com sucesso!",Toast.LENGTH_SHORT).show();
                    //----SALVAR ACESSO-----//
                    String identificadorUsuario = Base64custom.codificarBase64(acesso.getLogin());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    acesso.setIdAcesso(identificadorUsuario);
                    aluno.setFkAcessoAluno(identificadorUsuario);
                    acesso.salvar();

                    //---SALVAR ALUNO---//
                    String identificadorAluno= Base64custom.codificarBase64(aluno.getEmailAluno());
                    FirebaseUser usuariofire = task.getResult().getUser();
                    aluno.setIdAluno(identificadorAluno);
                    aluno.salvar();

                    //SALVAR USUARIO LOGADO
                    Preferencias preferenciaAlun = new Preferencias(cadastroAluno.this);
                    preferenciaAlun.salvarUsuarioPreferencias(identificadorUsuario, aluno.getNomeAluno());
                    abrirTelaAluno();
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

                    Toast.makeText(cadastroAluno.this,"Erro = "+error,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaAluno(){
        //Intent intent = new Intent(cadastroAluno.this,Aluno_projeto.class);
        //startActivity(intent);
    }
}
