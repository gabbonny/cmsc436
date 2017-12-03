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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // When Hero button gets pressed
            case R.id.adventure_button:
                Log.i(TAG, "Adventure chosen");

                // Make this button look selected
                Button adv =(Button) buttonList.get("adventure");
                adv.setAlpha(1f);
                chosen = Story.STORY_GENRE_ADVENTURE;

                // Make the others look deselected
                Button love = (Button) buttonList.get("love");
                love.setAlpha(0.5f);

                Button fantasy = (Button) buttonList.get("fantasy");
                fantasy.setAlpha(0.5f);

                Button funny = (Button) buttonList.get("funny");
                funny.setAlpha(0.5f);

                // start the bounce animation
                startOnTapAnimation(v);

                // play the pop button sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);

                break;

            // When Love button gets pressed
            case R.id.love_button:
                Log.i(TAG, "Love chosen");

                love =(Button) buttonList.get("love");
                love.setAlpha(1f);
                chosen = Story.STORY_LOVE;

                adv = (Button) buttonList.get("adventure");
                adv.setAlpha(0.5f);

                fantasy = (Button) buttonList.get("fantasy");
                fantasy.setAlpha(0.5f);

                funny = (Button) buttonList.get("funny");
                funny.setAlpha(0.5f);

                // start the bounce animation
                startOnTapAnimation(v);

                // play the bubble pop sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                break;

            // When Fantasy button gets pressed
            case R.id.fantasy_button:
                Log.i(TAG, "Fantasy chosen");

                fantasy =(Button) buttonList.get("fantasy");
                fantasy.setAlpha(1f);
                chosen = Story.STORY_GENRE_FANTASY;

                love = (Button) buttonList.get("love");
                love.setAlpha(0.5f);

                adv = (Button) buttonList.get("adventure");
                adv.setAlpha(0.5f);

                funny = (Button) buttonList.get("funny");
                funny.setAlpha(0.5f);
                // start the bounce animation
                startOnTapAnimation(v);

                // play the pop bubble sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                break;

            // When Fantasy button gets pressed
            case R.id.funny_button:
                Log.i(TAG, "Funny chosen");

                funny =(Button) buttonList.get("funny");
                funny.setAlpha(1f);
                chosen = Story.STORY_GENRE_FUNNY;

                adv = (Button) buttonList.get("adventure");
                adv.setAlpha(0.5f);

                love = (Button) buttonList.get("love");
                love.setAlpha(0.5f);

                fantasy =(Button) buttonList.get("fantasy");
                fantasy.setAlpha(0.5f);

                // start the bounce animation
                startOnTapAnimation(v);

                // play the bubble pop sound
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
