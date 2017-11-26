package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import StoryUtil.CompleteFragment;
import StoryUtil.FillInTheBlankFragment;
import StoryUtil.MultipleChoiceFragment;
import StoryUtil.Sentence;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;

/**
 * Created by Stefani Moore on 11/12/2017.
 */

public class StoryPromptActivity extends Activity implements ListSelectionListener{

    private final String TAG = "storyPromptActivity";

    //variables used for reading/parsing story from file
    private final String PROMPT_FRAG_SEPARATOR = "|";
    private final String PROMPT_FILLIN_TAG = "<F>";
    private final String PROMPT_INPUT_OPTIONS_SEPARATOR = ",";

    //used to read file from assets
    private BufferedReader promptReader;


    private String promptFilename;
    private String storyTitle;
    private ArrayList<String> prompts;

    // variables for sound functioonality

    // AudioManager
    private AudioManager mAudioManager;
    // SoundPool
    private SoundPool mSoundPool;
    // ID for the bubble popping sound
    private int mSoundID;
    // Audio volume
    private float mStreamVolume;

    //Fragments
    private FragmentManager manager;

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story_prompt);

        // Get the fragment manager
        manager = getFragmentManager();

        // start transaction
        FragmentTransaction transaction = manager.beginTransaction();


        //TODO retrieve file name from intent extras
        // This file name should be passed in
        // from the previous activity to this one depending on the user's choice
        // of story genre
        promptFilename = "prompts_heroStory.txt";

        //create the buffer to read the prompt
        try {

            Log.i(TAG, "Create Buffer to read prompt " + promptFilename);


            promptReader = new BufferedReader (
                new InputStreamReader(getAssets().open(promptFilename)));

            //reade the title (It should always be the first line)
            storyTitle = promptReader.readLine();

            //move to the 3rd line which is the first prompt;
            promptReader.readLine();
        } catch (IOException e){
            Log.i(TAG, "Could not open file " + promptFilename);
        }

        //TODO get the view
        //TODO get the next button and set listener

        ImageButton backButton = findViewById(R.id.back_btn);
        ImageButton nextButton = findViewById(R.id.next_btn);

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mSoundPool.play(mSoundID, mStreamVolume,
                        mStreamVolume, 1, 0, 1.0f);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mSoundPool.play(mSoundID, mStreamVolume,
                        mStreamVolume, 1, 0, 1.0f);
            }
        });

    }

    private Sentence readNextPrompt() throws IOException {
        Sentence sentence = null;

        String mLine;

        //keep reading till it sees the empty line which marks the end of the prompt
        while((mLine = promptReader.readLine()) != null && !mLine.equals("")) {
            sentence = new Sentence();

            String[] lineContent = mLine.split(PROMPT_FRAG_SEPARATOR);

            // check what type of prompt
            // complete fragment
            if (lineContent.length == 1) {
                //add the first part of this sentence
                sentence.addFragment(new CompleteFragment(lineContent[0]));
            } else {
                //check what type of input this sentence takes

                //fill in the blank input
                if (lineContent[1].equals(PROMPT_FILLIN_TAG)) {
                    //add a the fillIn fragment;
                    sentence.addFragment(new FillInTheBlankFragment(
                            lineContent[0]));
                }
                //multiple choice
                else {
                    String[] inputList = lineContent[1].split(PROMPT_INPUT_OPTIONS_SEPARATOR);
                    sentence.addFragment(new MultipleChoiceFragment(lineContent[1], inputList));
                }
            }
        }

        return sentence;
    }

    public void onListSelection(int index){

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
