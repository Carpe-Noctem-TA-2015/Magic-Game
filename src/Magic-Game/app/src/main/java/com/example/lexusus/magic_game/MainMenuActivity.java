package com.example.lexusus.magic_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.menu_container, new MainMenuButtonFragment())
                .commit();
    }
}
