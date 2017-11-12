package cmsc436_final_project.teddytalk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import StoryUtil.CompleteFragment;
import StoryUtil.FillInTheBlankFragment;
import StoryUtil.MultipleChoiceFragment;
import StoryUtil.Sentence;

/**
 * Created by Stefani Moore on 11/12/2017.
 */

public class StoryPromptActivity extends Activity{

    private final String TAG = "storyPromptActivity";

    //variables used for reading/parsing story from file
    private final String PROMPT_FRAG_SEPARATOR = "|";
    private final String PROMPT_FILLIN_TAG = "<F>";
    private final String PROMPT_INPUT_OPTIONS_SEPARATOR = ",";

    //used to read filef from assets
    private BufferedReader promptReader;


    private String promptFilename;
    private String storyTitle;
    private ArrayList<String> prompts;



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //TODO retrieve file name from intent extras
        // This file name should be passed
        // from the previous activity to this one depending on the user's choice
        // of story genre
        promptFilename = "prompts_heroStory.txt";

        //create the buffer to read the prompt
        try {
            promptReader = new BufferedReader (
                new InputStreamReader(getAssets().open(promptFilename)));

            //reade the title (It should always be the first line)
            storyTitle = promptReader.readLine();

            //move to the 3rd line which is the first prompt;
            promptReader.readLine();
        } catch (IOException e){
            Log.i(TAG, "Could not open file " + promptFilename);
        }

        //TODO get the view
        //TODO get the next button and set listener


    }

    private StoryUtil.Sentence readNextPrompt() throws IOException {
        Sentence sentence = null;

        String mLine;

        //keep reading till it sees the empty line which marks the end of the prompt
        while((mLine = promptReader.readLine()) != null && !mLine.equals(""))

            sentence = new Sentence();

            String[] lineContent = mLine.split(PROMPT_FRAG_SEPARATOR);

            // check what type of prompt
            // complete fragment
            if(lineContent.length == 1) {
                //add the first part of this sentence
                sentence.addFragment(new CompleteFragment(lineContent[0]));
            } else {
                //check what type of input this sentence takes

                //fill in the blank input
                if(lineContent[1].equals(PROMPT_FILLIN_TAG)) {
                    //add a the fillIn fragment;
                    sentence.addFragment(new FillInTheBlankFragment(
                            lineContent[0]));
                }
                //multiple choice
                else {
                    String[] inputList = lineContent[1].split(PROMPT_INPUT_OPTIONS_SEPARATOR);
                    sentence.addFragment(new MultipleChoiceFragment(lineContent[1], inputList));
                }
            }

            return sentence;
    };

}
