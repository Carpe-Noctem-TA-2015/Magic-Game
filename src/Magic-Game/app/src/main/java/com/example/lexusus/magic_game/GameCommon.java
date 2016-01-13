package com.example.lexusus.magic_game;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GameCommon extends Fragment {


    public GameCommon() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setGameFragments();
    }

    private void setGameFragments() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.top_bar, new GameTopBar())
                .add(R.id.game_area, new GameArea())
                .add(R.id.bottom_bar, new GameBottomBar())
                .commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_common, container, false);
    }

}
