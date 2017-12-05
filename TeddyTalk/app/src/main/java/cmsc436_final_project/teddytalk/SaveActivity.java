package cmsc436_final_project.teddytalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.MyBounceInterpolator;

public class SaveActivity extends AppCompatActivity {

    private final String TAG = "Save Activity";

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private ListView listView;
    private List list = new ArrayList();
    private ArrayAdapter adapter;
    private HashMap storyMap = new HashMap();
    private int pos;
    private CharSequence options[] = new CharSequence[] {"Replay","Delete"};
    public final static String INTENT_DATA = "data";

    // ------------ Sound variables --------------------------

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
        setContentView(R.layout.activity_save);

        // Get shared pref data
        sharedPref = getSharedPreferences("savedStories",MODE_PRIVATE);
        editor = sharedPref.edit();

        listView = (ListView)findViewById(R.id.list_view);

        // Put shared pref data into list and map
        Map<String,?> keys = sharedPref.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            list.add(entry.getKey());
            storyMap.put(entry.getKey(),entry.getValue());
        }

        // Populate list with data
        adapter = new ArrayAdapter(SaveActivity.this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);


        // Create Prompt when clicking on an item
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Replay or delete story?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // When user clicks replay
                if (which == 0) {
                    Intent replay = new Intent(SaveActivity.this, StoryPlaybackActivity.class);
                    String story = (String)storyMap.get(adapter.getItem(pos));
                    replay.putExtra(INTENT_DATA,story.split("_"));
                    startActivity(replay);
                }
                // When user clicks delete
                else {
                    storyMap.remove(adapter.getItem(pos));
                    editor.remove((String)adapter.getItem(pos));
                    editor.commit();
                    adapter.remove(adapter.getItem(pos));
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Set function when item has been clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
                builder.show();
            }
        });

        Button toTitle = findViewById(R.id.toTitle);
        toTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSoundPool.play(mSoundID, mStreamVolume, mStreamVolume, 1, 0, 1.0f);
                startOnTapAnimation(view);
                Intent title = new Intent(SaveActivity.this,MainActivity.class);
                startActivity(title);
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
        super.onPause();
        mSoundPool.unload(mSoundID);
        mSoundPool.release();
        mSoundPool = null;
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


}
