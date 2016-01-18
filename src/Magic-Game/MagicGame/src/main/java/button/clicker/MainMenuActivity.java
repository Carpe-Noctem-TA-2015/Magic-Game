package button.clicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class MainMenuActivity extends AppCompatActivity {

    private StaticAnimateGameView menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        menuView = new StaticAnimateGameView(this,R.drawable.cute,82,118,8);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.menu_container, new MainMenuButtonFragment())
                .commit();
    }

    public void onClickFindOpponent(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.d("my app", "find opponent");
    }
    public void onClickRanking(View view){
        Log.d("my app", "ranking");
    }
    public void onClickProfile(View view){
        getFragmentManager()
                .beginTransaction()
                .add(R.id.menu_container, new ProfileFragment())
                .commit();
    }
    public void onClickGameRules(View view){
        Log.d("my app", "game rules");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        menuView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        menuView.pause();
    }

    public void AddAnimatedAvatar(RelativeLayout rl) {
        // Initialize gameView and set it as the view

        rl.addView(menuView);
    }
}
