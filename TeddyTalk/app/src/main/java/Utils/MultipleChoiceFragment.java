package Utils;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

/**
 * This class represents a fragment of a sentence that needs input
 */
public class MultipleChoiceFragment implements Fragment {
    private String fragment;
    private String input;
    private String[] fillInOptions;

    public MultipleChoiceFragment(String fragment, String[] fillInOptions){
        this.fragment = fragment;
        this.fillInOptions = fillInOptions;
    }

    public String[] getFillInOptions(){
        return fillInOptions;
    }

    public void setInput(String input){
        this.input = input;
    }

    public String getInput(){
        return input;
    }

    public String toString() {
        return  fragment + (input == null ? "" : " " + input);
     }
}
