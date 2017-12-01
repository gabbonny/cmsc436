package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

import Utils.Story;

public class SelectGenreActivity extends Activity implements View.OnClickListener {
    private final String TAG = "SelectGenreActivity";
    private HashMap buttonList;
    private String chosen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_genre);

        buttonList = new HashMap();

        Button hero = (Button) findViewById(R.id.hero_button);
        buttonList.put("hero",hero);
        hero.setOnClickListener(this);
        Button love = (Button) findViewById(R.id.love_button);
        buttonList.put("love",love);
        love.setOnClickListener(this);
        Button fantasy = (Button) findViewById(R.id.fantasy_button);
        buttonList.put("fantasy",fantasy);
        fantasy.setOnClickListener(this);
        ImageButton next = findViewById(R.id.nextButton);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // When Hero button gets pressed
            case R.id.hero_button:
                Button b =(Button) buttonList.get("hero");
                b.setBackgroundColor(Color.YELLOW);
                chosen = Story.STORY_GENRE_HERO;

                b = (Button) buttonList.get("love");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                b = (Button) buttonList.get("fantasy");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                break;

            // When Love button gets pressed
            case R.id.love_button:
                b =(Button) buttonList.get("love");
                b.setBackgroundColor(Color.YELLOW);
                chosen = "love";

                b = (Button) buttonList.get("hero");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                b = (Button) buttonList.get("fantasy");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                break;

            // When Fantasy button gets pressed
            case R.id.fantasy_button:
                b =(Button) buttonList.get("fantasy");
                b.setBackgroundColor(Color.YELLOW);
                chosen = Story.STORY_GENRE_FANTASY;

                b = (Button) buttonList.get("love");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                b = (Button) buttonList.get("hero");
                b.setBackgroundColor(Color.parseColor("#FF9052"));
                break;

            case R.id.nextButton:
                if (!chosen.equals("")) {

                    Log.i(TAG, "Story Genre Selected: " + chosen);

                    Intent storyPromptIntent = new Intent(this, StoryPromptActivity.class);
                    storyPromptIntent.putExtra(StoryPromptActivity.INTENT_DATA, chosen);
                    startActivity(storyPromptIntent);

                } else {
                    Toast.makeText(this,"Select a genre to continue. Go choose one!",Toast.LENGTH_SHORT).show();
                }

            default:
                break;
        }

    }
}
