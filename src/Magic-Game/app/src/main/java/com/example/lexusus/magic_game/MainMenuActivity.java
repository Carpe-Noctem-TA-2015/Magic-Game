package com.example.lexusus.magic_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        Log.d("my app", "find opponent");
    }
    public void onClickRanking(View view){
        Log.d("my app", "ranking");
    }
    public void onClickProfile(View view){
        Log.d("my app", "profile");
    }
    public void onClickGameRules(View view){
        Log.d("my app", "game rules");
    }
}
