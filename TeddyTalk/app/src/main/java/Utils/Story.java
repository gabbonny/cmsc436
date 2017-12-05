package Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Created by Stefani Moore on 11/26/2017.
 */

public class Story implements Serializable {

    private static final String TAG = "Story Class";

    // ---- Story Types
    public static final String STORY_GENRE_FANTASY = "fantasy_story.txt";
    public static final String STORY_GENRE_ADVENTURE = "adventure_story.txt";
    public static final String STORY_GENRE_FUNNY = "funny_story.txt";
    public static final String STORY_LOVE = "love_story.txt";


    // The prompts will are stored in a linked list fashion
    private Prompt head;
    private Prompt tail;
    private Prompt currPrompt;
    private int size;

    // Flag to determine if the Story has been filled
    private boolean completed = false;

    public Story(String fileName, InputStream inputStream) {
        try {

            //fill in the story from file
            loadStoryFromFile(fileName, inputStream);

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

            //first prompt being read
            if(currPrompt == null) {
                currPrompt = head;
                return currPrompt;
            }

            //if not the last prompt move to the next
            if(currPrompt.getNextPrompt() != null){
                currPrompt = currPrompt.getNextPrompt();
                completed = false;
                return currPrompt;
            } else {

                //last prompt, don't update the prompt, just return null
                completed = true;
                return null;
            }
        } else {
            return null;
        }
    }

    public Prompt getPreviousPrompt(){
        if(currPrompt != null) {

            if (currPrompt.getPrevPrompt() == null) {
                Log.i(TAG, "Previous is null");
                return null;
            } else {
                currPrompt = currPrompt.getPrevPrompt();
                completed = false;
                return currPrompt;
            }
        }
        return null;
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


    private void loadStoryFromFile(String fileName, InputStream inputStream) throws IOException {

        Log.i(TAG, "Loading Story " + fileName);

        final BufferedReader promptReader = new BufferedReader(new InputStreamReader(inputStream));
        final String START_INPUT_OPTIONS = "*";
        final String INPUT_OPTIONS_SEPARATOR = ",";

        String[] options = new String[Prompt.MAX_NUM_OPTIONS];
        String defaultFillInText = null;
        String[] promptTexts = new String[Prompt.MAX_NUM_STRINGS];

        String mLine;
        int currText = 0;

        //keep reading till it sees the empty line which marks the end of the file
        while((mLine = promptReader.readLine()) != null) {
            Log.i(TAG, "reading + " + mLine);
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

    public int getSize(){
        return size;
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

