package com.sreesha.android.villgro;

/**
 * Created by Arun on 09-07-2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ritu on 22-02-2016.
 */
public class NewAdap extends ArrayAdapter {
    Context cont;
    String[] s;

    public NewAdap(Context context,int resource,Object[] objects){
        super(context,resource,objects);
        this.cont=context;
        this.s=(String[])objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(cont).inflate(R.layout.question_row,parent,false);

        }
        TextView tx=(TextView)convertView.findViewById(R.id.question_text);
        tx.setText(s[position]);

        return convertView;
    }
}
