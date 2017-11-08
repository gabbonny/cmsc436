package Stories;

import java.util.ArrayList;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

/**
 * This class represents a fragment in a sentence.
 * E.g., any part of the sentence that is not a complete sentence
 */
public interface Fragment {

    public ArrayList<String> getFillInOptions();

    public void addFillInOption(String option);

    public void setFillInOptions(ArrayList<String> fillInOptions);

    public void setInput(String input);

    public String getInput();

    public String toString() ;
}
