package com.example.comicsexchange_new;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ExplorerDefaultFragment extends Fragment {

    private ListView listView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    public ExplorerDefaultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explorer_default, container, false);
        getActivity().setTitle("News");
        listView = (ListView) view.findViewById(R.id.listViewExplorer);

        List<Comic> comics = generateComics();

        ComicAdapter_Explorer adapter = new ComicAdapter_Explorer(this.getContext(),comics);
        listView.setAdapter(adapter);


        // permet de passer de la news au details d'un comic
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int comicId = ((Comic) listView.getItemAtPosition(position)).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("SelectedComicId", comicId);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new ExplorerDetailsFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

            }
        });

        return view;
    }

    private List<Comic> generateComics(){
        List<Comic> comics = new ArrayList<Comic>();
        comics.add(new Comic(1, Color.BLACK, "Hunger Game","premier d'une grande trilogie"));
        comics.add(new Comic(2, Color.BLUE,"Harry Potter", "Troisième volet de la sage"));
        comics.add(new Comic(3, Color.RED, "Iron man","Incontournable de la license Marvel"));
        comics.add(new Comic(4, Color.BLACK, "Hunger Game","premier d'une grande trilogie"));
        comics.add(new Comic(5, Color.BLUE,"Harry Potter", "Troisième volet de la sage"));
        comics.add(new Comic(6, Color.RED, "Iron man","Incontournable de la license Marvel"));
        comics.add(new Comic(7, Color.BLACK, "Hunger Game","premier d'une grande trilogie"));
        comics.add(new Comic(8, Color.BLUE,"Harry Potter", "Troisième volet de la sage"));
        comics.add(new Comic(9, Color.RED, "Iron man","Incontournable de la license Marvel"));

        return comics;
    }
}
