package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.IOException;


import Utils.*;

import android.app.FragmentManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Stefani Moore on 11/12/2017.
 */

public class StoryPromptActivity extends Activity {

    private final String TAG = "storyPromptActivity";
    public final static String INTENT_DATA = "data";

    // ----- Variables for sound functionality

    // AudioManager
    private AudioManager mAudioManager;
    // SoundPool
    private SoundPool mSoundPool;
    // ID for the bubble popping and boing sound
    private int mPopSoundID;
    private int mBoingSoundID;
    // Audio volume
    private float mStreamVolume;

    // ---- Variables for managing Fragments
    private FragmentManager mFragmentManager;
    private PromptFragment mPromptFragment;

    // ---- Holds the story chosen by the user
    private Story story;

    private int bear_outfit;

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story_prompt);

        bear_outfit = getIntent().getIntExtra(ChangeOutfit.BEAR_OUTFIT, 0);

        // Get reference to fragment manager
        mFragmentManager = getFragmentManager();

        //get story filename from previous activity
        String fileName = getIntent().getExtras().getString(INTENT_DATA);

        //create the new story based on user choice
        try {
            story = new Story(fileName, this.getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set onClickListener for the Back and Next buttons
        setControlButtonsOnClickListener();

        //set progress bar
        setProgressBar();

        startAnimation(findViewById(R.id.control_btns_wrapper), R.anim.from_bottom_slide_up);
        //display first fragment
        setupFragment(story.getNextPrompt());
    }


    @Override
    protected void onResume() {
        super.onResume();

        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        mStreamVolume = (float) mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC)
                / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        mPopSoundID = mSoundPool.load(this, R.raw.bubble_pop, 1);
        mBoingSoundID = mSoundPool.load(this, R.raw.boing, 1);

    }

    @Override
    protected void onPause() {
        mSoundPool.unload(mPopSoundID);
        mSoundPool.release();
        mSoundPool = null;
        super.onPause();
    }

    /**
     * This method sets onClickListeners to the Back and Next Buttons
     */
    private void setControlButtonsOnClickListener(){

        //Set up Back Button
        ImageButton backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // Play pop sound
                playPopSoundEffect();

                //get the previous prompt
                Prompt prevPrompt = story.getPreviousPrompt();

                //no previous prompt just finish activity
                if(prevPrompt == null) {

                    Log.i(TAG, "Finishing activity");

                    finish();

                } else {

                    Log.i(TAG, "Loading previous fragment.");

                    mPromptFragment.setPrompt(prevPrompt);
                    mPromptFragment.setPromptContent();
                    updateProgressBar();
                }

            }
        });

        //Set up Next Button
        ImageButton nextButton = findViewById(R.id.next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                // Play pop sound
                playPopSoundEffect();

                //check if current prompt has been completed
                if(mPromptFragment.isCompleted()){

                    //Prompt compelted
                    Log.i(TAG, "Next Prompt pressed. State: Current Prompt COMPLETED");

                    Prompt nextPrompt = story.getNextPrompt();

                    //check if nextPrompt is null.
                    if(nextPrompt == null){

                        //Story is completed. Send story to the next activity
                        //move to the next activity
                        Log.i(TAG, "Story Completed moving to the next activity");

                        //get the finished story
                        String[] finishedStory = story.getFinishedStory();

                        //Check if story is actually completed
                        if(finishedStory != null){
                            Intent storyPlaybackActivity = new Intent(getApplicationContext(), StoryPlaybackActivity.class);
                            storyPlaybackActivity.putExtra(INTENT_DATA, finishedStory);
                            storyPlaybackActivity.putExtra(ChangeOutfit.BEAR_OUTFIT, bear_outfit);
                            startActivity(storyPlaybackActivity);
                        }

                    } else {

                        //Story is no complete yet. Load the next prompt in the fragment
                        Log.i(TAG, "Loading new fragment. New prompt: " + nextPrompt.toString());

                        // set the new fragment
                        mPromptFragment.setPrompt(nextPrompt);
                        // load the new prompt
                        mPromptFragment.setPromptContent();
                        updateProgressBar();
                    }

                } else {

                    //Prompt has not been completed yet
                    Log.i(TAG, "Next Prompt pressed. State: Current Prompt NOT COMPLETED");

                    Toast.makeText(getApplicationContext(),"Complete this prompt to continue!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setupFragment(Prompt prompt){


        if (null == mFragmentManager.findFragmentById(R.id.prompt_fragment_container)) {

            mPromptFragment = new PromptFragment();

            mPromptFragment.setPrompt(prompt);

            mFragmentManager.beginTransaction()
                    .add(R.id.prompt_fragment_container, mPromptFragment)
                    .commit();
        } else {

            mPromptFragment = (PromptFragment) mFragmentManager.findFragmentById(R.id.prompt_fragment_container);
        }

    }

    private void playPopSoundEffect(){
        mSoundPool.play(mPopSoundID, mStreamVolume,
                mStreamVolume, 1, 0, 1.0f);
    }

    private void startAnimation(View view, int animationType){
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                animationType);

        view.startAnimation(animSlide);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateProgressBar(){
        Log.i(TAG, "Update Progress Bar by " + (mPromptFragment.getCurrPromptID() - 1) + "/" + story.getSize() );
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setProgress(mPromptFragment.getCurrPromptID() - 1, true);
    }


    private void setProgressBar(){
        Log.i(TAG, "Update Progress Bar MAX " + story.getSize());
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setMax(story.getSize());
    }
}
