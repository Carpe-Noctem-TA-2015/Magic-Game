package com.example.lexusus.magic_game;

/**
 * Created by PavelPC on 13/01/2016.
 */
public class GameManager {

    private int currentGameStage;

    public GameManager() {
        this.currentGameStage = 1;
    }

    public int getCurrentGameStage() {
        return currentGameStage;
    }

    public void ProgressStage(){
        this.currentGameStage++;

        if(this.currentGameStage > 4){
            this.SendAttack();
        }
    }

    private void SendAttack(){
        this.currentGameStage = 1;
    }

}
