package com.example.lexusus.magic_game;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    private int mStage;
    private int firstOpenId = -1;
    private int firstOpenTag = -1;
    private int secondOpenId = -1;
    private int secondOpenTag = -1;
    private boolean lockGame = false;
    private boolean[] isFlipped;
    private Tiles[] tiles;
    private int[] power;
    private int pairsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.mStage = 1;
        InitGame();
    }

    private void InitGame() {
        InitStageOne();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_game_area, new StageOne())
                .commit();

    }

    //Flip Animations
    public void Flip(View view){

        String tag = (String)view.getTag();
        int id = view.getId();

        Log.w("myApp", tag);
        FlipSpecificTitle(tag, id);
        //flipCard();
    }
    private void FlipSpecificTitle(String tag, int id){

        if(lockGame){
            return;
        }

        int tagValue = Integer.parseInt(tag);
        boolean closeAfterOpening = false;


        if(firstOpenId == -1){
            firstOpenId = id;
            firstOpenTag = tagValue;
        }
        else if(secondOpenId == -1 && firstOpenId!=id ){
            secondOpenId = id;
            secondOpenTag = tagValue;
            closeAfterOpening = true;
        }
        else{
            return;
        }

        int index = tagValue;
        boolean currentCardIsFacedUp = isFlipped[index];


        Fragment fragmentToBeUsed;

        if(!currentCardIsFacedUp){
            fragmentToBeUsed = new TileBackFragment(tiles[index]);
        } else {
            fragmentToBeUsed = new TileFrontFragment();
        }

        FlipAnimation(id, fragmentToBeUsed);

        isFlipped[index] = !isFlipped[index];



        if(!closeAfterOpening)
        {
            return;
        }


        this.lockGame = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {

                FlipAnimation(firstOpenId, new TileFrontFragment());
                FlipAnimation(secondOpenId, new TileFrontFragment());

                if (secondOpenTag > 0 && tiles[firstOpenTag] == tiles[secondOpenTag]) {
                    View view1 = (View) findViewById(firstOpenId);

                    view1.setVisibility(View.INVISIBLE);

                    View view2 = (View) findViewById(secondOpenId);

                    view2.setVisibility(View.INVISIBLE);

                    switch (tiles[firstOpenTag]){
                        case FIRE:
                            power[0]+=1;
                            break;
                        case WATER:
                            power[1]+=1;
                            break;
                        case AIR:
                            power[2]+=1;
                            break;
                        case Earth:
                            power[3]+=1;
                            break;
                    }
                    pairsLeft --;
                }

                isFlipped[firstOpenTag] = !isFlipped[firstOpenTag];
                isFlipped[secondOpenTag] = !isFlipped[secondOpenTag];
                firstOpenTag = -1;
                firstOpenId = -1;
                secondOpenId = -1;
                secondOpenTag = -1;
                lockGame = false;
                if(pairsLeft == 0){
                    ChangeStage();
                }
            }
        }, 500);




    }
    private void FlipAnimation(int id, Fragment fragmentToBeUsed) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(id, fragmentToBeUsed)
                .commit();
    }


    //Navigation Between Stages
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

            InitStageOne();
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

    private void InitStageOne() {
        this.power = new int[4];
        this.pairsLeft = 8;
        TilePuzzeManager puzzle = new TilePuzzeManager(4);
        this.isFlipped = puzzle.getFlipped();
        this.tiles = puzzle.getTiles();

    }

    public void Test(View view) {
        ChangeStage();
    }
}
