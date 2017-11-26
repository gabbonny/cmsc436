package Utils;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public class FillInTheBlankFragment implements Fragment {

    private String fragment;
    private String input;

    public FillInTheBlankFragment(String fragment){
        this.fragment = fragment;
    }

    public String[] getFillInOptions(){
        return null;
    }

    public void setInput(String input){
        if(input == null){
            throw new IllegalArgumentException("Input can not be null");
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
