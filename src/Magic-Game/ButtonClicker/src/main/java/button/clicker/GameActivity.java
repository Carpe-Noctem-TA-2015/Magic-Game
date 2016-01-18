package button.clicker;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class GameActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SensorEventListener {
    private int mStage;
    private int firstOpenId = -1;
    private int firstOpenTag = -1;
    private int secondOpenId = -1;
    private int secondOpenTag = -1;
    private boolean lockGame = false;
    private boolean[] isFlipped;
    private boolean isShakeable = false;
    private Tiles[] tiles;
    private int[] power;
    private int pairsLeft;
    private GestureLibrary gLibrary;
    private StaticAnimateGameView gameView;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Context mContext;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();


        this.mStage = 1;
        InitGame();
        AddAnimatedAvatar();
        senSensorManager = (SensorManager) getSystemService(mContext.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onResume() {
        super.onResume();

        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        senSensorManager.unregisterListener(this);

        // Tell the gameView pause method to execute
        gameView.pause();
    }

    private void AddAnimatedAvatar() {
        LinearLayout rl = (LinearLayout)findViewById(R.id.ll_topbar);

        // Initialize gameView and set it as the view
        gameView = new StaticAnimateGameView(this,R.drawable.cute,82, 118, 8);

        rl.addView(gameView,0);
       /* View view = getLayoutInflater().inflate(R.layout.forces_thumbnail_cv, null);
        rl.addView(view);*/
    }

    private void InitGame() {
        InitStageOne();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_game_area, new StageOne())
                .commit();

    }

    //Speech
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String spell = result.get(0);
                    Log.d("test",result.get(0));
                    if(Constants.SPELLS.contains(spell)){
                        this.ChangeStage();
                    }
                }
                break;
            }

        }
    }

    //Tile Match Game
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

                    switch (tiles[firstOpenTag]) {
                        case FIRE:
                            power[0] += 1;
                            break;
                        case WATER:
                            power[1] += 1;
                            break;
                        case AIR:
                            power[2] += 1;
                            break;
                        case Earth:
                            power[3] += 1;
                            break;
                    }
                    pairsLeft--;
                }

                isFlipped[firstOpenTag] = !isFlipped[firstOpenTag];
                isFlipped[secondOpenTag] = !isFlipped[secondOpenTag];
                firstOpenTag = -1;
                firstOpenId = -1;
                secondOpenId = -1;
                secondOpenTag = -1;
                lockGame = false;
                if (pairsLeft == 0) {
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
        if(mStage>4){
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
            case 4:
                SwitchGameFragment(4);
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
            InitStageThree();
        }
        if(i==4) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_game_area, new StageFour())
                    .commit();
            InitStageFour();
        }
    }

    // Register

    private void InitStageFour() {
        isShakeable = true;
    }

    private void InitStageThree() {

        gLibrary = GestureLibraries.fromRawResource(this,
                R.raw.gestures);
        if (!gLibrary.load()) {
            finish();
        }
    }

    private void InitStageOne() {
        this.power = new int[4];
        this.pairsLeft = 8;
        TilePuzzeManager puzzle = new TilePuzzeManager(4);
        this.isFlipped = puzzle.getFlipped();
        this.tiles = puzzle.getTiles();
        isShakeable = false;

    }

    public void Test(View view) {
        ChangeStage();
    }

    public void Listen(View view) {
        this.promptSpeechInput();
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions =
                gLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {

            String action = predictions.get(0).name;

            Map<String,String> spellSymbols = Constants.init();

            if(spellSymbols.containsValue(action)){
                this.ChangeStage();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(isShakeable) {
            Sensor mySensor = sensorEvent.sensor;

            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                long curTime = System.currentTimeMillis();

                if ((curTime - lastUpdate) > 100) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;

                    float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                    if (speed > SHAKE_THRESHOLD) {
                        ChangeStage(); // moves to the next stage
                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
