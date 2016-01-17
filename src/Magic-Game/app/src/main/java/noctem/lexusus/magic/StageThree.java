package noctem.lexusus.magic;


import android.app.Fragment;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lexusus.magic.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StageThree extends Fragment {


    public StageThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stage_three, container, false);

        GestureOverlayView gOverlay = (GestureOverlayView)
                view.findViewById(R.id.gOverlay);

        gOverlay.addOnGesturePerformedListener((GameActivity)getActivity());

        return view;
    }

}
