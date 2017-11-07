package Stories;

import java.util.ArrayList;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public class CompleteSentenceFragment implements Fragment {
    private String fragment;

    public CompleteSentenceFragment(String fragment){
        if(fragment == null) {
            throw new IllegalArgumentException("Fragment can't be null");
        }
        this.fragment = fragment;
    }

    public String getFragment(){
        return fragment;
    }

    @Override
    public ArrayList<String> getFillInOptions() {
        return null;
    }

    @Override
    public void addFillInOption(String option) {
        return;
    }

    @Override
    public void setFillInOptions(ArrayList<String> fillInOptions) {
        return;
    }

    @Override
    public void setInput(String input) {
        return;
    }

    @Override
    public String getInput() {
        return null;
    }

    public String toString(){
        return fragment;
    }
}
