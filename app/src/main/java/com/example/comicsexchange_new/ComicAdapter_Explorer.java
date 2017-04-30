package com.example.comicsexchange_new;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Julie on 30.04.2017.
 */

public class ComicAdapter_Explorer extends ArrayAdapter<Comic>{

    public ComicAdapter_Explorer(Context context, List<Comic> comics){
        super(context, 0, comics);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_explorer, parent, false); // changer le row_explorer si on veut changer le design
        }

        ComicViewHolder viewHolder = (ComicViewHolder) convertView.getTag();

        if(viewHolder==null){
            viewHolder = new ComicViewHolder();
            // ici on reprend les info dans le layout row_explorer
            viewHolder.titre = (TextView) convertView.findViewById(R.id.titreComic);
            viewHolder.text = (TextView) convertView.findViewById(R.id.synopsis);
            viewHolder.photos = (ImageView) convertView.findViewById(R.id.couverture);
            convertView.setTag(viewHolder);
        }

        // va récupérer l'item [position] dans la list<Comic> comics
        Comic comic = getItem(position);

        // on va maintenant remplir la vue
        viewHolder.titre.setText(comic.getTitre());
        viewHolder.text.setText(comic.getText());
        viewHolder.photos.setImageDrawable(new ColorDrawable(comic.getColor()));

        return convertView;
    }


    private class ComicViewHolder{
        public TextView titre;
        public TextView text;
        public ImageView photos;
    }


}
