package cmsc436_final_project.teddytalk;

/**
 * Created by gabby on 12/4/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.*;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


public class ChangeOutfitActivity extends AppCompatActivity {
    public static final String BEAR_OUTFIT = "bear_outfit";
    private ImageView bear;
    private ImageView none;
    private ImageView sailor;
    private ImageView umd;
    private ImageView glasses;
    private ImageView wizard;
    private Button change;

    public int chosen = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_outfit_layout);

        bear = (ImageView) findViewById(R.id.outfit_bear);
        none = (ImageView) findViewById(R.id.none);
        sailor = (ImageView) findViewById(R.id.sailor);
        umd = (ImageView) findViewById(R.id.umd);
        glasses = (ImageView) findViewById(R.id.glasses);
        wizard = (ImageView) findViewById(R.id.wizard);


        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bear.setImageResource(R.drawable.outfit_bear);
                chosen = 0;
            }
        });
        sailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bear.setImageResource(R.drawable.outfit_bear_sailor);
                chosen = 3;

            }
        });
        umd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bear.setImageResource(R.drawable.outfit_bear_umd);
                chosen = 1;
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bear.setImageResource(R.drawable.outfit_bear_glasses);
                chosen = 2;
            }
        });
        wizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bear.setImageResource(R.drawable.outfit_bear_wizard);
                chosen = 4;
            }
        });

        change = (Button) findViewById(R.id.confirm_outfit);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sends back an int to the main activity of which bear outfit was chosen
                Intent choice = new Intent();
                choice.putExtra(BEAR_OUTFIT,chosen);
                setResult(Activity.RESULT_OK,choice);
                finish();
            }
        });
    }

}
