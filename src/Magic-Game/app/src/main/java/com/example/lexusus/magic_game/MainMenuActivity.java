package com.example.lexusus.magic_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    public void onClickFindOpponent(View view){
        Log.d("my app", "find opponent");
    }
    public void onClickRanking(View view){
        Log.d("my app", "ranking");
    }
    public void onClickProfile(View view){
        getFragmentManager()
                .beginTransaction()
                .add(R.id.menu_container, new ProfileFragment())
                .commit();
    }
    public void onClickGameRules(View view){
        Log.d("my app", "game rules");
    }
}
