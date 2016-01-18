package button.clicker;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * Created by Lexusus on 1/13/2016.
 */
public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,container, false);
        RelativeLayout rl = (RelativeLayout)view.findViewById(R.id.rl_avatar);
        MainMenuActivity activity = (MainMenuActivity)getActivity();
        activity.AddAnimatedAvatar(rl);
        return view;
    }
}
