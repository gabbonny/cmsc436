package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation;

import Utils.MyBounceInterpolator;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    // Sound variables

    // AudioManager
    private AudioManager mAudioManager;
    // SoundPool
    private SoundPool mSoundPool;
    // ID for the bubble popping sound
    private int mSoundID;
    // Audio volume
    private float mStreamVolume;

    //checks to see if main_bear outfit was switched
    private int outfit;

    //result code from change in outfit
    static final int CHANGED_OUTFIT = 1;
    ImageView bear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpLoadAnimation();

        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#882472"));//#20a780
//        getSupportActionBar().setBackgroundDrawable(colorDraw);

        ImageView stars1 = findViewById(R.id.stars1);
        ImageView stars2 = findViewById(R.id.stars2);

        // setting animation effects for stars
        ScaleAnimation anim = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(4000);
        anim.setRepeatCount(Animation.INFINITE);

        stars1.startAnimation(anim);
        stars2.startAnimation(anim);

        final ImageButton implicitActivationButton = findViewById(R.id.start_button);
        implicitActivationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startOnTapAnimation(implicitActivationButton);

                // start genre activity
                Intent selectGenreIntent = new Intent(MainActivity.this, SelectGenreActivity.class);
                mSoundPool.play(mSoundID, mStreamVolume,
                        mStreamVolume, 1, 0, 1.0f);
                selectGenreIntent.putExtra(ChangeOutfitActivity.BEAR_OUTFIT, outfit);
                startActivity(selectGenreIntent);
            }
        });

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

    private void startAnimation(View view, int animationType){
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                animationType);

        view.startAnimation(animSlide);
    }

    private void setUpLoadAnimation(){
        ImageButton startButton =  findViewById(R.id.start_button);
        startAnimation(startButton, R.anim.from_bottom_slide_up);

        bear = findViewById(R.id.bear);
        startAnimation(bear, R.anim.slide_in_right);

        bear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOutfit();
            }
        });

        ImageView start1= findViewById(R.id.stars1);
        startAnimation(start1, R.anim.from_bottom_slide_up);

        ImageView start2= findViewById(R.id.stars2);
        startAnimation(start2, R.anim.from_bottom_slide_up);

        ImageView speechBubble = findViewById(R.id.speech_bubble_main);
        startAnimation(speechBubble, R.anim.from_top_slide_down);
    }

    private void changeOutfit() {
        Intent go = new Intent(MainActivity.this, ChangeOutfitActivity.class);
        startActivityForResult(go, CHANGED_OUTFIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CHANGED_OUTFIT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //give the outfit the values from the intent data
                Bundle b = data.getExtras();
                outfit = (int) b.get(ChangeOutfitActivity.BEAR_OUTFIT);
                if (outfit == 0) {
                    bear.setImageResource(R.drawable.main_bear);
                }else if (outfit == 1){
                    bear.setImageResource(R.drawable.main_bear_1);
                }else if (outfit == 2){
                    bear.setImageResource(R.drawable.main_bear_2);
                }else if (outfit == 3){
                    bear.setImageResource(R.drawable.main_bear_3);
                }else if (outfit == 4){
                    bear.setImageResource(R.drawable.main_bear_4);
                }
            }
        }
    }
}