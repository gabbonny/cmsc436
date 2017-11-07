package Stories;

import java.util.ArrayList;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public interface Fragment {

    public ArrayList<String> getFillInOptions();

    public void addFillInOption(String option);

    public void setFillInOptions(ArrayList<String> fillInOptions);

    public void setInput(String input);

    public String getInput();

    public String toString() ;
}
