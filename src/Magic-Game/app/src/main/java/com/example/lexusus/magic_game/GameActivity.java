package com.example.lexusus.magic_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.addMainFragment();
    }

    private void addMainFragment() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.game_container, new GameCommon())
                .commit();
    }
}
