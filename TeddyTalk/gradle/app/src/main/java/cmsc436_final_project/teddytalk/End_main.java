package cmsc436_final_project.teddytalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class End_main extends AppCompatActivity {
    //Image view variables for possible animation transition on screen

    //transition from the left to right
    ImageView curtain;
    //stars will dangle down
    ImageView stars_1;
    ImageView stars_2;

    //transition from the bottom to up
    ImageView pop_up_bear;

    //make these clickable
    ImageView replay_button;
    ImageView save_button;
    ImageView rewrite_button;


    TextView end;
    TextView replay_txt;
    TextView save_txt;
    TextView rewrite_txt;


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

    }

    public void replay(){
        //CALLED IN XML
        //sends the user back to the Speech_To_Text activity to be read back the story
//        Intent go = new Intent(End_main.this,ReadBack_Actvity.class);
//        startActivity(go);

    }
    public void save(){
        //CALLED IN XML
        //optional save in possible library class stored at files

//        Intent go = new Intent(End_main.this,Save_Actvity.class);
//        startActivity(go);

    }
    public void rewrite(){
        //CALLED IN XML
        //Goes back to rewrite story, pick a genre and do prompt activities again
        Intent go = new Intent(End_main.this,SelectGenreActivity.class);
        startActivity(go);
    }
}
