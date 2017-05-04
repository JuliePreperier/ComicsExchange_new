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

    Context context;

    // constructor
    public ComicAdapter_Explorer(Context context, List<Comic> comics){
        super(context, 0, comics);
        this.context=context;
    }

    // getview method
    public View getView(int position, View convertView, ViewGroup parent) {
        // if the convertView equals to null, inflate a layout based on row_explorer
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_explorer, parent, false); // changer le row_explorer si on veut changer le design
        }

        // Creation of the holder to avoir the findViewById everytime we scroll
        ComicViewHolder viewHolder = (ComicViewHolder) convertView.getTag();

        // if holder = null, create a new one with the 3 elements on it (titre, synopsis and picture)
        if(viewHolder==null){
            viewHolder = new ComicViewHolder();
            // ici on reprend les info dans le layout row_explorer
            viewHolder.titre = (TextView) convertView.findViewById(R.id.titreComic);
            viewHolder.text = (TextView) convertView.findViewById(R.id.synopsis);
            viewHolder.photos = (ImageView) convertView.findViewById(R.id.couverture);
            convertView.setTag(viewHolder);
        }

        // retrive the item[position] in the ArrayList<Comic> comics
        Comic comic = getItem(position);

        // retrive id of the drawable picture
        int imageId = context.getResources().getIdentifier(comic.getPicture(),"drawable",context.getPackageName());

        // filling the viewHolder with info
        viewHolder.titre.setText(comic.getTitre());
        viewHolder.text.setText(comic.getText());
        viewHolder.photos.setImageResource(imageId);

        return convertView;
    }

    // viewHolder
    private class ComicViewHolder{
        public TextView titre;
        public TextView text;
        public ImageView photos;
    }


}
