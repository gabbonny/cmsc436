package Utils;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Stefani Moore on 11/26/2017.
 */

public class Story {

    private static final String TAG = "Story Class";

    // Variables used for reading from life
    private final static String START_INPUT_OPTIONS = "*";
    private final static String INPUT_OPTIONS_SEPARATOR = ",";
    private InputStream inputStream;

    // The prompts will are stored in a linked list fashion
    private Prompt head;
    private Prompt tail;
    private Prompt currPrompt;
    private int size;

    // Flag to determine if the Story has been filled
    private boolean completed = false;

    public Story(InputStream inputStream) {
        this.inputStream = inputStream;
        try {

            //fill in the story from file
            readPromptsFromFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the next prompt in the queue
     * @return The next prompt or Null if queue is empty.
     */
    public Prompt getNextPrompt(){
        // Check if already iterated through all the prompts;
        if(!completed) {

            //if first prompt
            if(currPrompt == null) {
                currPrompt = head;
                return currPrompt;
            }

            currPrompt = currPrompt.getNextPrompt();

            // if just sent out a null result, this story has been completed.
            if(currPrompt == null) {
                completed = true;
            }
            return currPrompt;

        } else {
            return null;
        }
    }

    public Prompt getPreviousPrompt(){
        if(currPrompt.getPrevPrompt() == null) {
            Log.i(TAG, "Previous is null");
            return null;
        } else {

            currPrompt = currPrompt.getPrevPrompt();
            completed = false;
            return currPrompt;
        }
    }

    /**
     * This method returns an the completed prompts for this story if the story has been completed
     * @return String[] of the completed prompts, or null if the story is not finished.
     */
    public String[] getFinishedStory(){

        if(!completed){
            return null;
        }

        String[] finishedPrompts = new String[size];
        Prompt curr = head;
        int i = 0;

        while (curr != null){
            finishedPrompts[i] = curr.toString();
            curr = curr.getNextPrompt();
            i++;
        }

        return finishedPrompts;
    }

    public void setCurrPrompt(Prompt prompt) {
        currPrompt = prompt;

        if(currPrompt.getID() < size) {
            completed = false;
        }
    }

    private void readPromptsFromFile() throws IOException {
        BufferedReader promptReader = new BufferedReader(new InputStreamReader(inputStream));

        Log.i(TAG, "Loading Story");

        String[] options = new String[Prompt.MAX_NUM_OPTIONS];
        String defaultFillInText = null;
        String[] promptTexts = new String[Prompt.MAX_NUM_STRINGS];

        String mLine;
        int currText = 0;

        //keep reading till it sees the empty line which marks the end of the file
        while((mLine = promptReader.readLine()) != null) {

            //finished reading the prompt
            if(mLine.equals("")){

                //add new prompt to the prompts list
                Prompt prompt = new Prompt(promptTexts, options, defaultFillInText);

                //adding prompt to list
                addPrompt(prompt);

                //reset variables
                options = new String[Prompt.MAX_NUM_OPTIONS];
                defaultFillInText = null;
                promptTexts = new String[Prompt.MAX_NUM_STRINGS];
                currText = 0;
            }
            // Check if reading the input options
            else if (mLine.startsWith(START_INPUT_OPTIONS)) {

                // split the line to retrieve the otpions
                String[] lineContent = mLine.split(INPUT_OPTIONS_SEPARATOR);

                // save the default text which is always the first on the options list
                defaultFillInText = lineContent[0].substring(1).trim();

                // save options on the options array
                for(int i = 1; i < lineContent.length; i ++){
                    options[i - 1] = lineContent[i].trim();
                }

            } else {

                //Reading the prompt texts
                promptTexts[currText] = mLine.trim();
                currText++;

                // reset text counter
                if(currText == Prompt.MAX_NUM_STRINGS) {
                    currText = 0;
                }
            }
        }
    }

    private void addPrompt(Prompt prompt){

        if(head == null) {

            head = prompt;
            tail = head;

        } else {

            tail.setNextPrompt(prompt);
            prompt.setPrevPrompt(tail);
            tail = prompt;
        }

        size++;
    }

    public String toString(){
        StringBuffer story = new StringBuffer();
        Prompt curr = head;

        while(curr != null) {
            story.append(curr.toString()).append("\n");
            curr = curr.getNextPrompt();
        }

        return story.toString();

    }
}
