package com.example.meuprojeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //NAVEGAR PRA TELA DE CADASTRO
    public void startActivity(View view) {

        Intent cadastro = new Intent(this, cadastro.class);
        startActivity(cadastro);
    }

}


