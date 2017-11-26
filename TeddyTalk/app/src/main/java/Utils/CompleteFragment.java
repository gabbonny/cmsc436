package Utils;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

/**
 * This class represents a fragment of a sentence that does not need any input.
 */
public class CompleteFragment implements Fragment {
    private String fragment;

    public CompleteFragment(String fragment){
        if(fragment == null) {
            throw new IllegalArgumentException("Fragment can't be null");
        }
        this.fragment = fragment;
    }

    public String getFragment(){
        return fragment;
    }

    @Override
    public String[] getFillInOptions() {
        return null;
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
