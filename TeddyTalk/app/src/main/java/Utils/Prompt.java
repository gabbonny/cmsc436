package Utils;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

/**
 * This class represents a prompt with the prompt text, and the predefined choices.
 * E.g. prompt text[0] = "Once upon  a time, there was a hero named" //this is where the fill in the blank would go
 *      prompt text[1] = ". Who lived in a normal town."
 *      predefined choices = [Bob, Antony, Andrew, Jake]
 *
 */
public class Prompt  {

    // To standardize the number or options a prompt must have
    public final static int MAX_NUM_OPTIONS = 4;
    public final static int MIN_NUM_OPTIONS = 4;
    public final static int MAX_NUM_STRINGS = 2;
    public final static int MIN_NUM_STRINGS = 1;

    // Keeps track of how many prompts there are
    private static int NUM_PROMPTS = 1;

    // The current's prompt unique ID
    private final int ID;

    // The actual prompt text
    private String[] promptTexts;

    private String defaultFillInText;

    // The predefined options for this prompt
    private String[] fillInOptions;

    // The user userChoice or fillInOptions choice
    private String userChoice;

    // The next node
    private Prompt next;
    private Prompt prev;


    public Prompt(String[] promptTexts, String[] fillInOptions, String defaultFillInText){

        // Check for invalid input
        if(defaultFillInText == null || promptTexts == null || promptTexts.length < MIN_NUM_STRINGS  || promptTexts.length > MAX_NUM_STRINGS ||
                fillInOptions == null || fillInOptions.length < MIN_NUM_OPTIONS || fillInOptions.length > MAX_NUM_OPTIONS) {

            throw new IllegalArgumentException("Arguments are null or there are too many Prompts Texts or Options. The ranges are: " +
                    MIN_NUM_STRINGS + "-" + MAX_NUM_STRINGS + "Prompts Texts ," +
                    MIN_NUM_OPTIONS + "-" + MAX_NUM_OPTIONS + " Options" + "\n" +
                    "Given: defaultText : " + defaultFillInText + " promtTexts len: " + promptTexts.length +
                    " fillinOptions len: " + fillInOptions.length);

        }

        this.promptTexts = promptTexts;
        this.fillInOptions = fillInOptions;
        this.defaultFillInText = defaultFillInText;
        ID = NUM_PROMPTS;
        NUM_PROMPTS ++;
    }

    public String[] getPromptTexts(){
        return promptTexts;
    }

    public int getID() {
        return ID;
    }

    public void setUserChoice(String userChoice){
        this.userChoice = userChoice;
    }

    public String getUserChoice(){
        return userChoice;
    }

    public String getDefaultFillInText(){
        return defaultFillInText;
    }

    public String[] getFillInOptions(){
        return fillInOptions;
    }

    public void setNextPrompt(Prompt prompt) {
        next = prompt;
    }

    public Prompt getNextPrompt(){
        return next;
    }

    public void setPrevPrompt(Prompt prompt){
        prev = prompt;
    }

    public Prompt getPrevPrompt(){
       return prev;
    }

    public String toString() {
        return  promptTexts[0] + (userChoice == null ? " [no choice] " : " " + userChoice) + promptTexts[1];
     }

}
