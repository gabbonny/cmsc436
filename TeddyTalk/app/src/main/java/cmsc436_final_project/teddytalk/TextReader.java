package cmsc436_final_project.teddytalk;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by gabby on 11/24/2017.
 */

public class TextReader extends Activity {

    //this will be the main activity that the story fragments will cling onto
    //this will contain only the back and next buttons that will help navigate through each
    //of the sentense fragments being used.
    //at the end of the story and the next button is pressed, the whole story string will
    //be sent to TextReader activity where the strings will be read one at a time using the
    //TexttoSpeechContainer speak method

    private final String TAG = "textReader";
    TextToSpeechContainer speechContainer;
    EditText input;
    Button speakIt;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG,"inOnCreate method");
        setContentView(R.layout.textreader_layout);


//        input = (EditText) findViewById(R.id.speakIt);
//        speakIt = (Button) findViewById(R.id.button3);
//        speechContainer.initialize(this);
//        Intent go = new Intent(StoryPromptActivity.this, TextToSpeechContainer.class);
//        startActivity(go);
//
//        speakIt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                readText(input.getText().toString());
//            }
//        });

    }

    //    private void readText(String s) {
//        if (s != null){
//            Log.i(TAG,"Button clicked");
//            speechContainer.speak(s);
//        }
//    }

}

