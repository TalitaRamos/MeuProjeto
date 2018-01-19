package com.example.meuprojeto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meuprojeto.R;

public class cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    //NAVEGAR PRA TELA DE CADASTRO DE PROFESSORES
    public void cadProf(View v){
        Intent intent=new Intent(this,cadastroProfessor.class);
        startActivity(intent);
    }

    //NAVEGAR PRA TELA DE CADASTRO DE ALUNOS
    public void cadAlun(View c){
        Intent inte = new Intent(this,cadastroAluno.class);
        startActivity(inte);
    }

    public static class Splash extends AppCompatActivity {
        private TextView tv;
        private ImageView iv;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            tv=(TextView) findViewById(R.id.tv);
            iv=(ImageView) findViewById(R.id.iv);
            Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
            tv.startAnimation(myanim);
            iv.startAnimation(myanim);
            final Intent i = new Intent(this,MainActivity.class);
            Thread timer = new Thread(){
                public void run(){
                    try{
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        startActivity(i);
                        finish();
                    }
                }
            };
            timer.start();
        }
    }
}
