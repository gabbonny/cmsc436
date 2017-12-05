package cmsc436_final_project.teddytalk;

/**
 * Created by Stefani Moore on 11/25/2017.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import Utils.MyBounceInterpolator;

public class EndActivity extends Activity {

    private final String TAG = "End Activity";
    //Image view variables for possible animation transition on screen

    //transition from the left to right
    private ImageView curtain;
    //stars will dangle down
    private ImageView stars_1;
    private ImageView stars_2;

    //transition from the bottom to up
    private ImageView pop_up_bear;

    //make these clickable
    private ImageView replay_button;
    private ImageView save_button;
    private ImageView rewrite_button;


    private TextView end;
    private TextView replay_txt;
    private TextView save_txt;
    private TextView rewrite_txt;

    private int mCurrRotation = 0;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private int bear_outfit;
    private String[] story;

    //------------------ Sound variables below -------------------------------

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
        setContentView(R.layout.activity_end_main);

        bear_outfit = getIntent().getIntExtra(ChangeOutfitActivity.BEAR_OUTFIT, 0);


        end = (TextView) findViewById(R.id.end_txt);
        replay_txt = (TextView) findViewById(R.id.textView);
        save_txt = (TextView) findViewById(R.id.textView2);
        rewrite_txt = (TextView) findViewById(R.id.textView3);


        curtain = (ImageView) findViewById(R.id.curtain);
        stars_1 = (ImageView) findViewById(R.id.stars_1);
        stars_2 = (ImageView) findViewById(R.id.stars_2);

        replay_button = (ImageView) findViewById(R.id.replay);
        save_button = (ImageView) findViewById(R.id.save);
        rewrite_button = (ImageView) findViewById(R.id.rewrite);

        pop_up_bear = (ImageView) findViewById(R.id.popup_bear);

        replay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //animate button click
                startOnTapAnimation(view);
                // Play pop bubble sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                replay();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //animate button click
                startOnTapAnimation(view);
                // Play pop bubble sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                save();
            }
        });

        rewrite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //animate button click
                startOnTapAnimation(view);
                // Play pop bubble sound
                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                rewrite();
            }
        });

        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in_left);
        Animation animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.from_bottom_slide_up);

        curtain.startAnimation(animSlide);
        replay_button.startAnimation(animSlide);
        save_button.startAnimation(animSlide);
        rewrite_button.startAnimation(animSlide);


        if (bear_outfit == 0) {
            pop_up_bear.setImageResource(R.drawable.cut_popup_bear);
        }else if (bear_outfit == 1){
            pop_up_bear.setImageResource(R.drawable.cut_popup_bear);
        }else if (bear_outfit == 2){
            pop_up_bear.setImageResource(R.drawable.cut_popup_bear_glasses);
        }else if (bear_outfit == 3){
            pop_up_bear.setImageResource(R.drawable.cut_popup_bear_sailor);
        }else if (bear_outfit == 4){
            pop_up_bear.setImageResource(R.drawable.cut_popup_bear_wizard);
        }


        pop_up_bear.startAnimation(animSlideUp);

        sharedPref = getSharedPreferences("savedStories",MODE_PRIVATE);
        editor = sharedPref.edit();

        story = getIntent().getExtras().getStringArray(StoryPlaybackActivity.INTENT_DATA);

        //------------------------ Adding Animation to Stars ----------------------------//
        ImageView stars1 = findViewById(R.id.stars_1);
        ImageView stars2 = findViewById(R.id.stars_2);

        // setting animation effects for stars
        ScaleAnimation anim = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(4000);
        anim.setRepeatCount(Animation.INFINITE);

        stars1.startAnimation(anim);
        stars2.startAnimation(anim);

    }

    public void replay(){
        //CALLED IN XML
        //sends the user back to the Speech_To_Text activity to be read back the story
        Intent go = new Intent(EndActivity.this,StoryPlaybackActivity.class);
        go.putExtra(StoryPlaybackActivity.INTENT_DATA, story);
        go.putExtra(ChangeOutfitActivity.BEAR_OUTFIT, bear_outfit);
        startActivity(go);

    }
    public void save(){
        //CALLED IN XML
        //optional save in possible library class stored at files

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.save_prompt_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                // Need to convert string array to string
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < story.length; i++) {
                                    sb.append(story[i]).append("_");
                                }

                                // Put data into shared pref
                                editor.putString(userInput.getText().toString(),sb.toString());
                                editor.commit();

                                // Go to save activity
                                Intent i = new Intent(EndActivity.this,SaveActivity.class);
                                startActivity(i);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    public void rewrite(){
        //CALLED IN XML
        //Goes back to rewrite story, pick a genre and do prompt activities again
        Intent go = new Intent(EndActivity.this,SelectGenreActivity.class);
        go.putExtra(ChangeOutfitActivity.BEAR_OUTFIT, bear_outfit);
        startActivity(go);
    }

    //----------------------Methods Below Handle Button Animation ---------------------------

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

    //-------------------- Methods below handle sound effects --------------------------
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
