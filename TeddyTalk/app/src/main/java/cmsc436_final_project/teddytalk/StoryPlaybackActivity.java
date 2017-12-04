package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    private ImageView bear;
    private int bear_outfit = 0;


    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story_playback);

        //to keep passing on the outfit across the app
        bear_outfit = getIntent().getIntExtra(ChangeOutfit.BEAR_OUTFIT, 0);


        try {
            speechContainer.initialize(this);
        } catch (RuntimeException e) {

        }

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

                    updatePlayButton(false);
                    updateStopButton(true);

                    startAnimation(findViewById(R.id.speach_bubble), R.anim.from_top_slide_down);
                    startAnimation(findViewById(R.id.prompt_text), R.anim.from_top_slide_down);
                }

                updateBackButtonStatus();
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
                    //finish speechContainer
                    speechContainer.stop();

                    //no more prompts to process
                    //go to EndActivity
                    Intent end = new Intent(getApplicationContext(),EndActivity.class);
                    end.putExtra(INTENT_DATA,getIntent().getExtras().getStringArray(INTENT_DATA));
                    end.putExtra(ChangeOutfit.BEAR_OUTFIT, bear_outfit);
                    startActivity(end);

                } else {

                    //display and play next prompt
                    displayPrompt(prompt);


                    //set enablePlay


                    speechContainer.speak(prompt);
                    updatePlayButton(false);
                    updateStopButton(true);
                    startAnimation(findViewById(R.id.speach_bubble), R.anim.from_top_slide_down);
                    startAnimation(findViewById(R.id.prompt_text), R.anim.from_top_slide_down);

                }

                updateBackButtonStatus();

            }
        });


        //Set up Next Button
        ImageButton playButton = findViewById(R.id.play_btn);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Play pop sound
                playPopSoundEffect();

                //get the next prompt
                String prompt = getCurrPrompt();

                //check if there are not more prompts to process
                if(prompt != null) {

                    //read prompt
                    speechContainer.speak(prompt);
                    updatePlayButton(false);
                    updateStopButton(true);
                }
            }
        });


        //Set up Next Button
        ImageButton stopButton = findViewById(R.id.stop_btn);
        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Play pop sound
                playPopSoundEffect();
                if(speechContainer.stop()){
                    updatePlayButton(true);
                    updateStopButton(false);
                }
            }
        });

        updateBackButtonStatus();
        updatePlayButton(false);
        updateStopButton(true);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE) {
            Log.i(TAG, "text to speach returnerd with code = " + requestCode);

            // Set onClickListener for the Back and Next buttons
            setControlButtonsOnClickListener();
            setUpLoadAnimation();

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

    private String getCurrPrompt(){
        if(currPrompt >= 0 && currPrompt < prompts.length){
            return prompts[currPrompt];
        } else {
            return null;
        }
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

    private void updateBackButtonStatus(){

        ImageButton backButton = findViewById(R.id.back_btn);

        //toggle back button
        if(currPrompt <= 0){
            backButton.setEnabled(false);
            backButton.setAlpha(0.4F);
        } else {
            backButton.setEnabled(true);
            backButton.setAlpha(1F);
        }

    }

    private void updatePlayButton(boolean enable){
        ImageButton playButton = findViewById(R.id.play_btn);

        if(enable){
            playButton.setEnabled(true);
            playButton.setAlpha(1F);

        } else {
            playButton.setEnabled(false);
            playButton.setAlpha(0.4F);
        }

    }

    private void updateStopButton(boolean enable){
        ImageButton stopButton = findViewById(R.id.stop_btn);

        if(enable){
            stopButton.setEnabled(true);
            stopButton.setAlpha(1F);

        } else {
            stopButton.setEnabled(false);
            stopButton.setAlpha(0.4F);
        }
    }

    private void startAnimation(View view, int animationType){
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                animationType);

        view.startAnimation(animSlide);
    }

    private void setUpLoadAnimation(){
        LinearLayout controlButtons =  findViewById(R.id.control_btns_wrapper);
        startAnimation(controlButtons, R.anim.from_bottom_slide_up);

        //animates and changes outfit when needed
        bear = findViewById(R.id.talking_bear);
        startAnimation(bear, R.anim.slide_in_right);
        Drawable[] outfit_umd = {getResources().getDrawable(R.drawable.talking_bear_1_umd),
                getResources().getDrawable(R.drawable.talking_bear_umd_1),
                getResources().getDrawable(R.drawable.talking_bear_umd_2)};

        Drawable[] outfit_glasses = {getResources().getDrawable(R.drawable.talking_bear_1_glasses),
                getResources().getDrawable(R.drawable.talking_bear_glasses_1),
                getResources().getDrawable(R.drawable.talking_bear_glasses_2)};
        Drawable[] outfit_sailor = {getResources().getDrawable(R.drawable.talking_bear_1_sailor),
                getResources().getDrawable(R.drawable.talking_bear_sailor_1),
                getResources().getDrawable(R.drawable.talking_bear_sailor_2)};
        Drawable[] outfit_wizard = {getResources().getDrawable(R.drawable.talking_bear_1_wizard),
                getResources().getDrawable(R.drawable.talking_bear_wizard_1),
                getResources().getDrawable(R.drawable.talking_bear_wizard_2)};
        Drawable[] outfit_none = {getResources().getDrawable(R.drawable.talking_bear_1),
                getResources().getDrawable(R.drawable.talking_bear),
                getResources().getDrawable(R.drawable.talking_bear_3)};

        if (bear_outfit == 0){
            AnimationDrawable animation = new AnimationDrawable();
            animation.addFrame(outfit_none[0], 100);
            animation.addFrame(outfit_none[1], 500);
            animation.addFrame(outfit_none[2], 300);
            animation.setOneShot(false);

            bear.setBackgroundDrawable(animation);

            // start the animation!
            animation.start();
        }else if (bear_outfit == 1){
            AnimationDrawable animation = new AnimationDrawable();
            animation.addFrame(outfit_umd[0], 100);
            animation.addFrame(outfit_umd[1], 500);
            animation.addFrame(outfit_umd[2], 300);
            animation.setOneShot(false);

            bear.setBackgroundDrawable(animation);

            // start the animation!
            animation.start();
        }else if (bear_outfit == 2){
            AnimationDrawable animation = new AnimationDrawable();
            animation.addFrame(outfit_glasses[0], 100);
            animation.addFrame(outfit_glasses[1], 500);
            animation.addFrame(outfit_glasses[2], 300);
            animation.setOneShot(false);

            bear.setBackgroundDrawable(animation);

            // start the animation!
            animation.start();
        }else if (bear_outfit == 3){
            AnimationDrawable animation = new AnimationDrawable();
            animation.addFrame(outfit_sailor[0], 100);
            animation.addFrame(outfit_sailor[1], 500);
            animation.addFrame(outfit_sailor[2], 300);
            animation.setOneShot(false);

            bear.setBackgroundDrawable(animation);

            // start the animation!
            animation.start();
        }else if (bear_outfit == 4){
            AnimationDrawable animation = new AnimationDrawable();
            animation.addFrame(outfit_wizard[0], 100);
            animation.addFrame(outfit_wizard[1], 500);
            animation.addFrame(outfit_wizard[2], 300);
            animation.setOneShot(false);

            bear.setBackgroundDrawable(animation);

            // start the animation!
            animation.start();
        }

        ImageView speechBubble = findViewById(R.id.speach_bubble);
        startAnimation(speechBubble, R.anim.from_top_slide_down);

        TextView promptText = findViewById(R.id.prompt_text);
        startAnimation(promptText, R.anim.from_top_slide_down);
    }

}