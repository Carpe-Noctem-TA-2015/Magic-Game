package com.example.lexusus.magic_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    private int mStage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.mStage = 1;
        InitGame();
    }




    private void InitGame() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_game_area, new StageOne())
                .commit();
    }

    private void ChangeStage(){
        this.mStage += 1;
        if(mStage>3){
            this.mStage = 1;
        }

        switch (this.mStage){
            case 1:
                SwitchGameFragment(1);
                break;
            case 2:
                SwitchGameFragment(2);
                break;
            case 3:
                SwitchGameFragment(3);
                break;
        }
    }

    private void SwitchGameFragment(int i) {
        if(i==1){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_game_area, new StageOne())
                    .commit();
        }
        if(i==2){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_game_area, new StageTwo())
                    .commit();
        }
        if(i==3){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_game_area, new StageThree())
                    .commit();
        }
    }

    public void Test(View view) {
        ChangeStage();
    }
}
