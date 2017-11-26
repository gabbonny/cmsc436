package cmsc436_final_project.teddytalk;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation;



public class MainActivity extends AppCompatActivity {

    // Sound variables

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
        setContentView(R.layout.activity_main);

        //setUpSound(); // set up sound stuff

        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#882472"));//#20a780

        getSupportActionBar().setBackgroundDrawable(colorDraw);
        ImageButton implicitActivationButton = (ImageButton) findViewById(R.id.start_button);
        implicitActivationButton.setOnClickListener(new View.OnClickListener() {

//            Animation anim = new ScaleAnimation(
//                    1f, 1f, // Start and end values for the X axis scaling
//                    startScale, endScale, // Start and end values for the Y axis scaling
//                    Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
//                    Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
//    anim.setFillAfter(true); // Needed to keep the result of the animation
//    anim.setDuration(1000);
//    v.startAnimation(anim);

            @Override
            public void onClick(View v) {

                // start genre activity
                Intent selectGenreIntent = new Intent(MainActivity.this, SelectGenreActivity.class);

                mSoundPool.play(mSoundID, mStreamVolume,
                        mStreamVolume, 1, 0, 1.0f);
                startActivity(selectGenreIntent);
//                Toast.makeText(MainActivity.this,
//                        "START!", Toast.LENGTH_SHORT).show();

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

}
