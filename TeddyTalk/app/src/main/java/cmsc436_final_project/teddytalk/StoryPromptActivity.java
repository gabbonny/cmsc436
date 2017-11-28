package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.app.Fragment;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import Utils.*;

import android.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Stefani Moore on 11/12/2017.
 */

public class StoryPromptActivity extends Activity implements OnDataPass{

    private final String TAG = "storyPromptActivity";
    private final String BACK_STACK_ROOT_TAG = "root_fragment";

    //variables used for reading/parsing story from file
    private final String PROMPT_FRAG_SEPARATOR = "|";
    private final String PROMPT_FILLIN_TAG = "<F>";
    private final String PROMPT_INPUT_OPTIONS_SEPARATOR = ",";

    //used to read file from assets
    private BufferedReader promptReader;

    // The story choosen by the user
    private Story story;

    // Variables for sound functionality

    // AudioManager
    private AudioManager mAudioManager;
    // SoundPool
    private SoundPool mSoundPool;
    // ID for the bubble popping and boing sound
    private int mPopSoundID;
    private int mBoingSoundID;
    // Audio volume
    private float mStreamVolume;

    //Fragments
    private FragmentManager mFragmentManager;
    private PromptFragment mPromptFragment;

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story_prompt);

        // Get reference to fragement manager
        mFragmentManager = getFragmentManager();

        // TODO get story type from previous activity (should be as an extra from bundle intent)
        String fileName = "fantasy_story.txt";

        //create the new story
        try {
            story = new Story(this.getAssets().open(fileName));
            Log.i(TAG, story.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Set onClickListener for the Back and Next buttons
        setControlButtonsOnClickListener();

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

        ImageButton backButton = findViewById(R.id.back_btn);
        final ImageButton nextButton = findViewById(R.id.next_btn);

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Play pop sound
                playPopSoundEffect();

                //todo ADD BACK FUNCTIONALITY
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
                }

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Play pop sound
                playPopSoundEffect();

                //check if current prompt has been completed
                if(mPromptFragment.isCompleted()){

                    Log.i(TAG, "Next button pressed. State: current prompt COMPLETED");

                    Prompt nextPrompt = story.getNextPrompt();

                    //TODO check if last prompt (returns null ?)
                    if(nextPrompt == null){
                        //todo move to the next activity
                    } else {

                        Log.i(TAG, "Loading new fragment. New prompt: " + nextPrompt.toString());
                        //Create the new fragement
                        mPromptFragment.setPrompt(nextPrompt);
                        mPromptFragment.setPromptContent();

                    }

                } else {

                    Log.i(TAG, "Next button pressed. State: current prompt NOT COMPLETED");

                    Toast.makeText(getApplicationContext(),"You must complete this prompt to continue.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void setupFragment(Prompt prompt){


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

    @Override
    public void onDataPass(String data, int promptId) {
        Log.d("LOG","hello " + data);
    }

}
