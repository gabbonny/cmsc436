package cmsc436_final_project.teddytalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton implicitActivationButton = (ImageButton) findViewById(R.id.start_button);
        implicitActivationButton.setOnClickListener(new View.OnClickListener() {

            // Call startImplicitActivation() when pressed
            @Override
            public void onClick(View v) {

                // start genre activity
                Intent selectGenreIntent = new Intent(MainActivity.this, SelectGenreActivity.class);

                startActivity(selectGenreIntent);

                Toast.makeText(MainActivity.this,
                        "START!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}