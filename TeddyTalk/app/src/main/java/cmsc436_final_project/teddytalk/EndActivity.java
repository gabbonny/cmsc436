package cmsc436_final_project.teddytalk;

/**
 * Created by Stefani Moore on 11/25/2017.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {
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

    private String[] story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_main);

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
                replay();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        rewrite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        pop_up_bear.startAnimation(animSlideUp);

        sharedPref = getSharedPreferences("savedStories",MODE_PRIVATE);
        editor = sharedPref.edit();

        story = getIntent().getExtras().getStringArray("story");

    }

    public void replay(){
        //CALLED IN XML
        //sends the user back to the Speech_To_Text activity to be read back the story
        Intent go = new Intent(EndActivity.this,StoryPlaybackActivity.class);
        go.putExtra("data",story);
        startActivity(go);

    }
    public void save(){
        //CALLED IN XML
        //optional save in possible library class stored at files

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.save_prompt, null);

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
                                    sb.append(story[i]).append(",");
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





//        Intent go = new Intent(End_main.this,Save_Actvity.class);
//        startActivity(go);

    }
    public void rewrite(){
        //CALLED IN XML
        //Goes back to rewrite story, pick a genre and do prompt activities again
        Intent go = new Intent(EndActivity.this,SelectGenreActivity.class);
        startActivity(go);
    }
}
