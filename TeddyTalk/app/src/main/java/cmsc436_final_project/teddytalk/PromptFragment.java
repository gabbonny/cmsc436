package cmsc436_final_project.teddytalk;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Utils.MyBounceInterpolator;
import Utils.Prompt;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by Stefani Moore on 11/16/2017.
 */

public class PromptFragment extends Fragment{

    private static final String TAG = "PromptFragment";

    // Variables for sound functionality

    // AudioManager
    private AudioManager mAudioManager;
    // SoundPool
    private SoundPool mSoundPool;
    // ID for the bubble popping and boing sound
    private int mBoingSoundID;
    // Audio volume
    private float mStreamVolume;

    private RelativeLayout mOptionsContainer;

    // Variables used to keep track of the content of the fragment
    private Prompt mPrompt;


    public void setPrompt(Prompt prompt){
        mPrompt = prompt;
    }

    /**
     * This method fills in the content for the current fragment
     */
    public void setPromptContent(){

        Log.i(TAG, "Setting Prompt Content");

        // Set the prompt texts
        setPromptTexts();

        // Check if the current prompt alredy has a user choice
        if(mPrompt.getUserChoice() != null) {

            //display saved user choice
            updateFillInOption(mPrompt.getUserChoice());
        } else {
            // Set the fill in option text
            updateFillInOptionHint();
        }


        // Set onClickListener for the fill in option
        final EditText fillInOption = getActivity().findViewById(R.id.fillInOption);
//        fillInOption.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //do nothing
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                Log.i(TAG, "afterTextChanged");
//
//                if(mPrompt.getUserChoice() == null || !mPrompt.getUserChoice().equals(s.toString())) {
//                    updateFillInOption(fillInOption.getText().toString().trim());
//                }
//
//            }
//        });

        fillInOption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "onFocusChange");

                if(mPrompt.getUserChoice() == null || !mPrompt.getUserChoice().equals(fillInOption.getText().toString())) {
                    updateFillInOption(fillInOption.getText().toString().trim());
                }
            }
        });

        String[] options = mPrompt.getFillInOptions();

        // Set the options choices
        for(int i = 0; i < mOptionsContainer.getChildCount(); i++) {
            Button mView = (Button) mOptionsContainer.getChildAt(i);
            mView.setText(options[i]);
            setOptionOnClickListener(mView);
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");


        // Inflate the layout defined in quote_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.prompt_fragment,
                container, false);
    }

    // Set up some information about the mQuoteView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");

        super.onActivityCreated(savedInstanceState);

        // Save instances to layout that holds the options
        mOptionsContainer = getActivity().findViewById(R.id.options_wrapper);

        //display the prompt content
        setPromptContent();

    }



    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
    }

    @Override
    public void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();

        //Set up Audio stuff
        mAudioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);

        mStreamVolume = (float) mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC)
                / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        mBoingSoundID = mSoundPool.load(getActivity(), R.raw.boing, 1);
    }


    @Override
    public void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
        super.onDestroyView();
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
                playBoingSoundEffect();

                //replace fill in blank with the selected option
                updateFillInOption(button.getText().toString());
            }
        });
    }

    private void setPromptTexts(){

        Log.i(TAG, "Setting PromptText");

        // Get the TextView references
        TextView mPromptText1 = getActivity().findViewById(R.id.prompt_text1);
        TextView mPromptText2 = getActivity().findViewById(R.id.prompt_text2);

        // Get the prompt texts
        String[] promptTexts = mPrompt.getPromptTexts();

        // Set the prompt text
        mPromptText1.setText(promptTexts[0]);

        if(promptTexts[1] != null){
            mPromptText2.setText(promptTexts[1]);
            mPromptText2.setVisibility(EditText.VISIBLE);
        } else {
            mPromptText2.setVisibility(EditText.INVISIBLE);
        }

    }

    /**
     * This method updates the fillInOption EditText with the given option.
     * @param option The new text for the fillInOption View
     */
    private void updateFillInOption(String option){

        Log.i(TAG, "Updating fillInOption with \"" + option);

        // check if option is null (first time loading the fragment)
        // or if user entered blank spaces only
        if(option == null || option.length() == 0){

            updateFillInOptionHint();

            //reset user choice
            mPrompt.setUserChoice(null);

        } else {

            // get instance of the view
            EditText fillInOption = getActivity().findViewById(R.id.fillInOption);

            // grab the color for when the view is filled in
            int color = getResources().getColor(R.color.filledInOption);

            // Save the user option
            mPrompt.setUserChoice(option);

            // update bg back to white
            fillInOption.setBackgroundColor(color);

            //fill int the blank with the input
            fillInOption.setText(option);
            fillInOption.setTextColor(getResources().getColor(R.color.colorAccent));

        }


    }

    private void updateFillInOptionHint(){
        // get instance of the view
        EditText fillInOption = getActivity().findViewById(R.id.fillInOption);
        fillInOption.setHint(mPrompt.getDefaultFillInText());
        // update bg back to white
        fillInOption.setBackgroundColor(Color.WHITE);
        fillInOption.getText().clear();
        fillInOption.setTextColor(Color.GRAY);
    }


    /**
     * This method start the bouncing animation
     * @param button The button to apply the animation
     */
    private void startOnTapAnimation(View button) {

        Log.i(TAG, "Tapped Option Button " + button.getId());

        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
    }


    /**
     * This method plays the boing sound effect
     */
    private void playBoingSoundEffect(){
        mSoundPool.play(mBoingSoundID, mStreamVolume,
                mStreamVolume, 1, 0, 1.0f);
    }

    public boolean isCompleted(){
        return mPrompt.getUserChoice() != null;
    }


}
