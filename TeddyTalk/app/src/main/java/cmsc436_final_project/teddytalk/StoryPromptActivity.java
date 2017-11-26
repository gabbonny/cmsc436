package cmsc436_final_project.teddytalk;

import android.app.Activity;
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

/**
 * Created by Stefani Moore on 11/12/2017.
 */

public class StoryPromptActivity extends Activity implements OnDataPass{

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

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story_prompt);



        // Set onClickListener for the Back and Next buttons
        setControlButtonsOnClickListener();

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
        ImageButton nextButton = findViewById(R.id.next_btn);

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Play pop sound
                playPopSoundEffect();

                //TODO Add back functionallity
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Play pop sound
                playPopSoundEffect();

                // TODO add going to next fragment functionality
            }
        });

    }

    /**
     * Add a fragment on top of the current tab
     */
    public void addFragmentOnTop(PrompFragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.prompt_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }


    private void playPopSoundEffect(){
        mSoundPool.play(mPopSoundID, mStreamVolume,
                mStreamVolume, 1, 0, 1.0f);
    }

    @Override
    public void onDataPass(String data) {
        Log.d("LOG","hello " + data);
    }

}
