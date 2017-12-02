package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

import Utils.MyBounceInterpolator;
import Utils.Story;

public class SelectGenreActivity extends Activity implements View.OnClickListener {
    private final String TAG = "SelectGenreActivity";
    private HashMap buttonList;
    private String chosen = "";

    // -------------------  Sound variables ---------------------

    // AudioManager
    private AudioManager mAudioManager;
    // SoundPool
    private SoundPool mSoundPool;
    // ID for the bubble popping sound
    private int mSoundID;
    // Audio volume
    private float mStreamVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_genre);

        buttonList = new HashMap();

        Button adventure = (Button) findViewById(R.id.adventure_button);
        buttonList.put("adventure",adventure);
        adventure.setOnClickListener(this);

        Button love = (Button) findViewById(R.id.love_button);
        buttonList.put("love",love);
        love.setOnClickListener(this);

        Button fantasy = (Button) findViewById(R.id.fantasy_button);
        buttonList.put("fantasy",fantasy);
        fantasy.setOnClickListener(this);

        Button funny = (Button) findViewById(R.id.funny_button);
        buttonList.put("funny",funny);
        funny.setOnClickListener(this);

        ImageButton next = findViewById(R.id.nextButton);
        next.setOnClickListener(this);

//        // Set animation and sound effects for story option buttons
//        setOptionOnClickListener(adventure);
//        setOptionOnClickListener(love);
//        setOptionOnClickListener(fantasy);
//        setOptionOnClickListener(funny);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // When Hero button gets pressed
            case R.id.adventure_button:
                Log.i(TAG, "Adventure chosen");
                Button b =(Button) buttonList.get("adventure");
                b.setBackgroundColor(Color.YELLOW);
                chosen = Story.STORY_GENRE_ADVENTURE;

                b = (Button) buttonList.get("love");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                b = (Button) buttonList.get("fantasy");
                b.setBackgroundColor(Color.parseColor("#FF9052"));

                // start the bounce animation
                startOnTapAnimation(v);

                // play the boing sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);

                break;

            // When Love button gets pressed
            case R.id.love_button:
                Log.i(TAG, "Love chosen");
                b =(Button) buttonList.get("love");
                b.setBackgroundColor(Color.YELLOW);
                chosen = Story.STORY_LOVE;

                b = (Button) buttonList.get("adventure");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                b = (Button) buttonList.get("fantasy");
                b.setBackgroundColor(Color.parseColor("#FF9052"));

                // start the bounce animation
                startOnTapAnimation(v);

                // play the boing sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                break;

            // When Fantasy button gets pressed
            case R.id.fantasy_button:
                Log.i(TAG, "Fantasy chosen");
                b =(Button) buttonList.get("fantasy");
                b.setBackgroundColor(Color.YELLOW);
                chosen = Story.STORY_GENRE_FANTASY;

                b = (Button) buttonList.get("love");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                b = (Button) buttonList.get("adventure");
                b.setBackgroundColor(Color.parseColor("#FF9052"));

                // start the bounce animation
                startOnTapAnimation(v);

                // play the boing sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                break;

            // When Fantasy button gets pressed
            case R.id.funny_button:
                Log.i(TAG, "Funny chosen");
                b =(Button) buttonList.get("funny");
                b.setBackgroundColor(Color.YELLOW);
                chosen = Story.STORY_GENRE_FUNNY;

                b = (Button) buttonList.get("love");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                b = (Button) buttonList.get("adventure");
                b.setBackgroundColor(Color.parseColor("#FF9052"));

                // start the bounce animation
                startOnTapAnimation(v);

                // play the boing sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                break;

            case R.id.nextButton:

                // PLAY pop sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);

                if (!chosen.equals("")) {

                    Log.i(TAG, "Story Genre Selected: " + chosen);

                    Intent storyPromptIntent = new Intent(this, StoryPromptActivity.class);
                    storyPromptIntent.putExtra(StoryPromptActivity.INTENT_DATA, chosen);
                    startActivity(storyPromptIntent);

                } else {
                    Toast.makeText(this,"Select a genre to continue. Go choose one!",Toast.LENGTH_SHORT).show();
                }

            default:
                break;
        }

    }

    /**
     * This method sets onClickListeners to the given button.
     * Actions include: onTapAnimation, and sound effects
     */
    private void setOptionOnClickListener(final Button button){

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // start the bounce animation
                startOnTapAnimation(v);


                // play the boing sound
             mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
            }
        });
    }

    /**
     * This method start the bouncing animation
     * @param button The button to apply the animation
     */
    private void startOnTapAnimation(View button) {

        Log.i(TAG, "Tapped Option Button " + button.getId());

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        mStreamVolume = (float) mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC)
                / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        mSoundID = mSoundPool.load(this, R.raw.bubble_pop, 1);
    }

    @Override
    protected void onPause() {
        mSoundPool.unload(mSoundID);
        mSoundPool.release();
        mSoundPool = null;
        super.onPause();
    }
}
