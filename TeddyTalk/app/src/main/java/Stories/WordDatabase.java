package Stories;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public class  WordDatabase {
    private final static Random RANDOM_NUM_GENERATOR = new Random();

    private ArrayList<String> words;

    public WordDatabase(ArrayList<String> words){
        this.words = new ArrayList<>(words);

    }

    public WordDatabase(String fileName){
        //TODO read the file and input words in the database
    }

    public void addWord(String word) {
        words.add(word);
    }

    public void removeWord(String word) {
        words.remove(word);
    }

    public String getRandomWord() {
        int pos = RANDOM_NUM_GENERATOR.nextInt(words.size());
        return words.get(pos);
    }

    public   ArrayList<String>  getRandomWords(int numWords) {
        int pos = 0;

        ArrayList<String> result = new ArrayList<String>();

        while (pos < numWords) {
            String word = getRandomWord();

            while (result.contains(word)) {
                word = getRandomWord();
            }

            result.add(getRandomWord());
        }

        return result;
    }
}
