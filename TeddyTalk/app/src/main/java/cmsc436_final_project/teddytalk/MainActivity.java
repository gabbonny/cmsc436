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
        ColorDrawable colorDraw = new ColorDrawable(Color.parseColor("#882472"));//#20a780

        getSupportActionBar().setBackgroundDrawable(colorDraw);
        ImageButton implicitActivationButton = (ImageButton) findViewById(R.id.start_button);
        implicitActivationButton.setOnClickListener(new View.OnClickListener() {

            // Call startImplicitActivation() when pressed
            @Override
            public void onClick(View v) {

                // start genre activity

                Toast.makeText(MainActivity.this,
                        "START!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
