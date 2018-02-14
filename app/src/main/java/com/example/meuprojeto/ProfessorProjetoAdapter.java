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
 * Created by Paloma on 25/01/2018.
 */

public  class ProfessorProjetoAdapter extends ArrayAdapter<Projeto> {


    private Activity context;
    private List<Projeto> projectList;

    public ProfessorProjetoAdapter(Activity context, List<Projeto> projectList){
        super(context, R.layout.layout_list_professor_projeto,projectList);
        this.context=context;
        this.projectList=projectList;
    }



    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_list_professor_projeto, null, true);

        TextView nomeProjeto = (TextView) listViewItem.findViewById(R.id.nomeProjeto);
        TextView statusProjeto = (TextView) listViewItem.findViewById(R.id.statusProjeto);

        Projeto proj = projectList.get(position);

        nomeProjeto.setText(proj.getNome());
        statusProjeto.setText(proj.getStatus());

        return  listViewItem;
    }

}
