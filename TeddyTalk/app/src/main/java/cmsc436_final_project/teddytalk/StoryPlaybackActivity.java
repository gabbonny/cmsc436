package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;


import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Stefani Moore on 11/12/2017.
 */

public class StoryPlaybackActivity extends Activity {

    private final static String TAG = "StoryPlaybackActivity";
    private static final int REQUEST_CODE = 0;
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


    // ----- Variables for using the TextToSpeech functionality
    private TextToSpeechContainer speechContainer;

    // ----- Variables for displaying promtps
    //All the prompts filled in from previous activity
    private  String[] prompts;
    //to keep track of current prompt being displayed
    private int currPrompt = -1;


    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story_playback);

        speechContainer.initialize(this);
        Intent go = new Intent(this, TextToSpeechContainer.class);
        startActivityForResult(go, REQUEST_CODE);

        //get story filename from previous activity
        prompts = getIntent().getExtras().getStringArray(INTENT_DATA);

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

                //get the previous prompt
                String prompt = getPrevPrompt();

                //check if there are not more prompts to process
                if(prompt == null) {

                    //no more prompts to process
                    //terminate activity and go back to previous activity
                    finish();

                } else {

                    //display and play prev prompt
                    displayPrompt(prompt);
                    speechContainer.speak(prompt);
                }


            }
        });

        //Set up Next Button
        ImageButton nextButton = findViewById(R.id.next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Play pop sound
                playPopSoundEffect();

                //get the next prompt
                String prompt = getNextPrompt();

                //check if there are not more prompts to process
                if(prompt == null) {

                    //no more prompts to process
                    //go to EndActivity
                    startActivity(new Intent(getApplicationContext(), EndActivity.class));

                } else {

                    //display and play next prompt
                    displayPrompt(prompt);
                    speechContainer.speak(prompt);

                }

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE) {
            Log.i(TAG, "text to speach returnerd with code = " + requestCode);

            // Set onClickListener for the Back and Next buttons
            setControlButtonsOnClickListener();

            //display first fragment
            String prompt = getNextPrompt();
            displayPrompt(prompt);
            speechContainer.speak(prompt);
        }
    }

    private void playPopSoundEffect(){
        mSoundPool.play(mPopSoundID, mStreamVolume,
                mStreamVolume, 1, 0, 1.0f);
    }


    private void displayPrompt(String prompt){
        TextView promptText = findViewById(R.id.prompt_text);
        promptText.setText(prompt);
    }

    private String getNextPrompt(){
        if(currPrompt < prompts.length - 1){
            return prompts[++currPrompt];
        } else {
            return null;
        }
    }

    private String getPrevPrompt(){
        if(currPrompt > 0){
           return prompts[--currPrompt];
        } else {
            return null;
        }
    }
}
