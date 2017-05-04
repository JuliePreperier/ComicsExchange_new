package com.example.comicsexchange_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Julie on 04.05.2017.
 */

public class ComicSearch_Adapter extends BaseAdapter {

    private ArrayList<Comic> listData;
    private LayoutInflater layoutInflater;
    private ArrayList<Comic> copie;
    Context context;


    // constructor with a listData of comics and a copie of it
    public ComicSearch_Adapter(Context aContext, ArrayList<Comic> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        copie = new ArrayList<Comic>();
        copie.addAll(listData);
        this.context=aContext;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // getview method
    public View getView(int position, View convertView, ViewGroup parent) {
        // Creation of the holder to avoir the findViewById everytime we scroll
        ComicViewHolder holder;

        // if holder = null, create a new one with the 3 elements on it (titre, synopsis and picture)
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_explorer, null);
            holder = new ComicViewHolder();
            holder.titre = (TextView) convertView.findViewById(R.id.titreComic);
            holder.photo = (ImageView) convertView.findViewById(R.id.couverture);
            holder.text = (TextView) convertView.findViewById(R.id.synopsis);
            convertView.setTag(holder);
        } else {
            holder = (ComicViewHolder) convertView.getTag();
        }

        // get position f the item in the listData
        Comic comic = listData.get(position);
        // retrive id of the drawable picture
        int imageId = context.getResources().getIdentifier(comic.getPicture(),"drawable",context.getPackageName());

        // filling the viewHolder with info
        holder.titre.setText(comic.getTitre());
        holder.text.setText(comic.getText());
        holder.photo.setImageResource(imageId);

        return convertView;
    }

    // Filter Class to filter by charText
    public void filter(String charText) {
        charText = charText.toLowerCase();
        listData.clear(); // clear the listData

        if (charText.length() == 0) { // if nothing is written in the searchbar
            listData.addAll(copie); // refill the listData with all comic
        } else {
            for (Comic searchComic : copie) {
                // else, it will fill the lisData with the comic responding to the charText in th searchbar
                if (searchComic.getTitre().toLowerCase().contains(charText)) {
                    listData.add(searchComic);
                }
            }
        }
        notifyDataSetChanged(); // notify to the data that the list had changed
    }

    // viewHolder
    static class ComicViewHolder {
        TextView titre;
        ImageView photo;
        TextView text;

    }
}

