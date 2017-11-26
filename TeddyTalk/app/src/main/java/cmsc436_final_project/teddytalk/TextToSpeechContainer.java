package cmsc436_final_project.teddytalk;

/**
 * Created by Nick on 11/18/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.content.Intent;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class TextToSpeechContainer extends Activity implements OnInitListener{
    private static boolean initialized = false;
    private static TextToSpeech TTS;
    private static Intent onInitializationIntent;
    private static int resultCodeContainer;
    private static Context appliedContext;

    private static final int DATA_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitializationIntent = new Intent();

        if(initialized) {
            Intent checkTTSIntent = new Intent();
            checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(checkTTSIntent, DATA_CODE);
        }
        else {
            Log.e("TTS Module", "Container was not initialized");
            finish();
        }
    }

    public static boolean speak(String words) {
        Log.i("TTS Module","inSpeakFunction");
        if (TTS != null) {
            Log.i("TTS Module","Speaking the words");
            TTS.speak(words, TextToSpeech.QUEUE_FLUSH, null);
            return true;
        }
        return false;
    }

    public static void initialize(Context context) {
        if(!initialized) {
            Log.i("TTS Module", "Initializing TTS Module");
            initialized = true;
            appliedContext = context;
        }
        else
            throw(new RuntimeException("Text to speech container is already initialized"));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resultCodeContainer = resultCode;

        initialized = true;

        if (requestCode == DATA_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                TTS = new TextToSpeech(appliedContext, this);
                Log.i("TTS Module", "TTS pointer: " + TTS);
            }
            else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }

        Log.i("TTS Module", "Activity completed with status code: " + resultCode);
    }

    @Override
    public void onInit(int i) {
        TTS.setLanguage(Locale.US);

        //Utterance listener doesn't seem to work for now...
        TTS.setOnUtteranceProgressListener(new UtteranceProgressListener(){
            @Override
            public void onStart(String s) {
                Log.i("App", "Starting speech");
            }

            @Override
            public void onDone(String s) {
                Log.i("App", "Finishing speech");
            }

            @Override
            public void onError(String s) {

            }
        });

        setResult(resultCodeContainer, onInitializationIntent);

        finish();
    }
}
