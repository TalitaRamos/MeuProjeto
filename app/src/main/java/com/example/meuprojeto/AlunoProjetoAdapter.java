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
 * Created by Talit on 24/01/2018.
 */

public class AlunoProjetoAdapter extends ArrayAdapter<Projeto>{
    private Activity context;
    private List<Projeto> projectList;

    public AlunoProjetoAdapter(Activity context, List<Projeto> projectList){
        super(context, R.layout.layout_list_aluno_projeto,projectList);
        this.context=context;
        this.projectList=projectList;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_list_aluno_projeto, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textStatus = (TextView) listViewItem.findViewById(R.id.textStatus);

        Projeto proj = projectList.get(position);

        textViewName.setText(proj.getNome());
        textStatus.setText(proj.getStatus());

        return  listViewItem;
    }
}
