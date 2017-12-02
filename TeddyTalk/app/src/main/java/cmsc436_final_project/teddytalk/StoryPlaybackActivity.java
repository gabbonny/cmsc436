package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;


import Utils.*;

import android.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Stefani Moore on 11/12/2017.
 */

public class StoryPlaybackActivity extends Activity {

    private final String TAG = "StoryPlaybackActivity";
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

    //All the prompts filled in from previous activity
    private  String[] prompts;
    //to keep track of current prompt being displayed
    private int nextPrompt;

    //Takes care of playing prompt
    private TextToSpeechContainer speechContainer;

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story_playback);

        speechContainer.initialize(this);
        Intent go = new Intent(this, TextToSpeechContainer.class);
        startActivity(go);

        //get story filename from previous activity
        prompts = getIntent().getExtras().getStringArray(INTENT_DATA);

        // Set onClickListener for the Back and Next buttons
        setControlButtonsOnClickListener();

        //display first fragment
        displayPrompt(getNextPrompt());
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

            @Override
            public void onClick(View v) {
                // Play pop sound
                playPopSoundEffect();

                //TODO make it display the previous prompt

            }
        });

        //Set up Next Button
        ImageButton nextButton = findViewById(R.id.next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Play pop sound
                playPopSoundEffect();

                //TODO make it display the next prompt

                String prompt = getNextPrompt();
                displayPrompt(prompt);

            }
        });

    }

    private void playPopSoundEffect(){
        mSoundPool.play(mPopSoundID, mStreamVolume,
                mStreamVolume, 1, 0, 1.0f);
    }


    private void displayPrompt(String prompt){
        TextView promptText = findViewById(R.id.prompt_text);
        promptText.setText(prompt);
        speechContainer.speak(prompt);
    }

    private String getNextPrompt(){
        if(nextPrompt < prompts.length){
            return prompts[nextPrompt++];
        } else {
            return null;
        }
    }

    private String getPrevPrompt(){
        if(nextPrompt > 0){
            return prompts[--nextPrompt];
        } else {
            return null;
        }
    }

}
