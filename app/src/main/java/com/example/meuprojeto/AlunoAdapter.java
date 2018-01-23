package com.example.meuprojeto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.meuprojeto.Model.Projeto;

import java.util.List;

/**
 * Created by Talit on 23/01/2018.
 */

public class AlunoAdapter extends ArrayAdapter<Projeto> {
    private Activity context;
    private List<Projeto> projetoList;

    public AlunoAdapter(Activity context, List<Projeto> projetoList){
        super(context, R.layout.main_line_view,projetoList);
        this.context=context;
        this.projetoList=projetoList;
    }

    // método que é chamado para fornecer cada item da lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // criando um objeto "inflador"
        LayoutInflater inflater = context.getLayoutInflater();

        // usando o inflador para criar uma View a partir do arquivo de layout
        // que fizemos definindo os itens da lista
        View listViewItem =inflater.inflate(R.layout.main_line_view,null, true);

        // pegando referências para as views que definimos dentro do item da lista,
        // isto é, os 2 textviews
        TextView textTitle=(TextView)listViewItem.findViewById(R.id.main_line_title);
        TextView textProf=(TextView) listViewItem.findViewById(R.id.main_line_prof);

        // a posição do projeto na lista (armazenamento) é a mesma na lista (listview)
        // então usamos esse valor (position) para acessar o objeto "Projeto" correto
        // dentro da lista projetoList
        Projeto projeto = projetoList.get(position);

        // finalmente, colocamos os valores do objeto artista recuperado
        // nas views que formam nosso item da lista
        textTitle.setText(projeto.getNome());
        textProf.setText(projeto.getIdProfessor());

        // a view está pronta! É só devolver para quem pediu
        return listViewItem;
    }
}
