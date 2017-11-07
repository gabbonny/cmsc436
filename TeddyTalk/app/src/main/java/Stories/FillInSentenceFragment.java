package Stories;

import java.util.ArrayList;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public class FillInSentenceFragment implements Fragment {
    private String fragment;
    private String input;
    private ArrayList<String> fillInOptions;

    public FillInSentenceFragment(String fragment, ArrayList<String> fillInOptions){
        this.fragment = fragment;
        this.fillInOptions = fillInOptions;
    }

    public ArrayList<String> getFillInOptions(){
        return new ArrayList<>(fillInOptions);
    }

    public void addFillInOption(String option){
        fillInOptions.add(option);
    }

    public void setFillInOptions(ArrayList<String> fillInOptions){
        this.fillInOptions = new ArrayList<>(fillInOptions);
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
