package com.example.lexusus.magic_game;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TilePuzzleFragment extends Fragment {


    TilePuzzeManager puzzle;

    Tiles[] tiles;
    boolean[] isFlipped;

    private int firstOpenId = -1;
    private int firstOpenTag = -1;
    private int secondOpenTag = -1;
    private int secondOpenId = -1;

    public TilePuzzleFragment() {
        // Required empty public constructor
        puzzle = new TilePuzzeManager(4);

        tiles = puzzle.getTiles();
        isFlipped = puzzle.getFlipped();

        initTiles();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void initTiles() {

        getFragmentManager()
                .beginTransaction()
                .add(R.id.container_0, new TileFrontFragment())
                .add(R.id.container_1, new TileFrontFragment())
                .add(R.id.container_2, new TileFrontFragment())
                .add(R.id.container_3, new TileFrontFragment())
                .add(R.id.container_4, new TileFrontFragment())
                .add(R.id.container_5, new TileFrontFragment())
                .add(R.id.container_6, new TileFrontFragment())
                .add(R.id.container_7, new TileFrontFragment())
                .add(R.id.container_8, new TileFrontFragment())
                .add(R.id.container_9, new TileFrontFragment())
                .add(R.id.container_10, new TileFrontFragment())
                .add(R.id.container_11, new TileFrontFragment())
                .add(R.id.container_12, new TileFrontFragment())
                .add(R.id.container_13, new TileFrontFragment())
                .add(R.id.container_14, new TileFrontFragment())
                .add(R.id.container_15, new TileFrontFragment())
                .commit();

    }



    public void Flip(View view){

        String tag = (String)view.getTag();
        int id = view.getId();

        Log.w("myApp", tag);
        FlipSpecificTitle(tag, id);
        //flipCard();
    }
    public void FlipSpecificTitle(String tag, int id){

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


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                FlipAnimation(firstOpenId, new TileFrontFragment());
                FlipAnimation(secondOpenId, new TileFrontFragment());

                if (secondOpenTag > 0 && tiles[firstOpenTag] == tiles[secondOpenTag]) {
                    View view1 = (View) getActivity().findViewById(firstOpenId);

                    view1.setVisibility(View.INVISIBLE);

                    View view2 = (View) getActivity().findViewById(secondOpenId);

                    view2.setVisibility(View.INVISIBLE);
                }

                isFlipped[firstOpenTag] = !isFlipped[firstOpenTag ];
                isFlipped[secondOpenTag] = !isFlipped[secondOpenTag ];
                firstOpenTag = -1;
                firstOpenId = -1;
                secondOpenId = -1;
                secondOpenTag = -1;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tile_puzzle, container, false);
    }

    public class TileFrontFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.front, container, false);
        }
    }

    /**
     * A fragment representing the air of the card.
     */
    public class TileBackFragment extends Fragment {
        private Tiles tile;

        public TileBackFragment(Tiles tile)
        {
            this.tile = tile;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int layout;

            switch (tile){
                case FIRE:
                    layout = R.layout.fire;
                    break;
                case AIR:
                    layout = R.layout.air;
                    break;
                default:
                    layout = R.layout.air;
                    break;
            }
            return inflater.inflate(layout, container, false);
        }
    }

}
