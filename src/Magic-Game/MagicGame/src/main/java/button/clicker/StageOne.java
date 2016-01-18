package button.clicker;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




/**
 * A simple {@link Fragment} subclass.
 */
public class StageOne extends Fragment {


    public StageOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tile_puzzle, container, false);

        this.initTiles();
        return view;
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

}
