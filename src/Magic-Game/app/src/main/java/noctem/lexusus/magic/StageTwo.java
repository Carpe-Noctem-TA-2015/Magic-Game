package noctem.lexusus.magic;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lexusus.magic.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StageTwo extends Fragment {


    public StageTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stage_two, container, false);
    }

}
