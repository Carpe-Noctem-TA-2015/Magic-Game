package com.example.lexusus.magic_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    TilePuzzleFragment tilePuzzle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.addMainFragment();

        this.tilePuzzle = new TilePuzzleFragment();
    }



    private void addMainFragment() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.game_container, new GameCommon())
                .commit();
    }
}
