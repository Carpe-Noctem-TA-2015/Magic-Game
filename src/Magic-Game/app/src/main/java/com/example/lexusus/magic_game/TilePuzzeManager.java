package com.example.lexusus.magic_game;

public class TilePuzzeManager {

    private int size;

    private Tiles[] tiles;
    private boolean[] flipped;


    public TilePuzzeManager(int size){

        flipped = new boolean[size*size];
        tiles =  new Tiles[size*size];

        initTiles();

    }

    private void initTiles() {
        Tiles currentTileType = Tiles.FIRE;

        for (int i = 0; i < tiles.length; i++) {

            if(i == 4){
                currentTileType = Tiles.WATER;
            }
            if(i == 8){
                currentTileType = Tiles.AIR;
            }
            if(i == 12){
                currentTileType = Tiles.Earth;
            }

            tiles[i] = currentTileType;
        }

        TilePuzzeManager.shuffle(tiles);
    }

    public Tiles[] getTiles() {
        return tiles;
    }

    static void shuffle(Tiles[] array) {
        int n = array.length;
        for (int i = 0; i < array.length; i++) {
            // Get a random index of the array past i.
            int random = i + (int) (Math.random() * (n - i));
            // Swap the random element with the present element.
            Tiles randomElement = array[random];
            array[random] = array[i];
            array[i] = randomElement;
        }
    }

    public boolean[] getFlipped() {
        return flipped;
    }
}
