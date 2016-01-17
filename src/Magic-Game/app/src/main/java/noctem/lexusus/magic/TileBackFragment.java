package noctem.lexusus.magic;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lexusus.magic.R;

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
            case WATER:
                layout = R.layout.water;
                break;
            case Earth:
                layout = R.layout.earth;
                break;
            default:
                layout = R.layout.air;
                break;
        }
        return inflater.inflate(layout, container, false);
    }
}
