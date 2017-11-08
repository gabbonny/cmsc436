package Stories;

import java.util.ArrayList;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public class FillInTheBlankSentenceFragment implements Fragment {

    private String fragment;
    private String input;

    public FillInTheBlankSentenceFragment(String fragment){
        this.fragment = fragment;
    }

    public ArrayList<String> getFillInOptions(){
        return null;
    }

    public void addFillInOption(String option){
        return;
    }

    public void setFillInOptions(ArrayList<String> fillInOptions){ return;
    }

    public void setInput(String input){
        if(input == null){
            throw new IllegalArgumentException("input can not be invalid");
        }

        this.input = input;
    }

    public String getInput(){
        return input;
    }

    public String toString() {
        return  fragment + (input == null ? "" : " " + input);
    }

}
