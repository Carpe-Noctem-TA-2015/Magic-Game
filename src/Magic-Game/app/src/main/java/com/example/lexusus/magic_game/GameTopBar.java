package com.example.lexusus.magic_game;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameTopBar extends Fragment {


    public GameTopBar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_top_bar, container, false);

      /*  View tb = view.findViewById(R.id.game_top_bar);
        StaticAnimateGameView avatar =  new StaticAnimateGameView(getActivity(),R.drawable.bob,200,200,5);

        ((LinearLayout)tb).addView(avatar);*/

        return  view;

    }

}
