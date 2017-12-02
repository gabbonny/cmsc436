package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation;

public class MainActivity extends Activity {

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

        ImageButton implicitActivationButton = findViewById(R.id.start_button);
        implicitActivationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // start genre activity
                Intent selectGenreIntent = new Intent(MainActivity.this, SelectGenreActivity.class);
                mSoundPool.play(mSoundID, mStreamVolume,
                        mStreamVolume, 1, 0, 1.0f);
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

}
