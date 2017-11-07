package Stories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public abstract class Prompt implements Iterable {
    private final static boolean SUCCESS = true;
    private final static boolean FAILURE = false;

    //stores the sentence_frags for
    private ArrayList<Sentence> sentences;
    private int top;

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    public void addSentence(Sentence sentence){
        sentences.add(sentence);
    }

    public Sentence getSentence(int sentenceNum){
        if(sentenceNum < 0 || sentenceNum > sentences.size()){
            throw new NoSuchElementException("Ivalid sentenceNum provided");
        } else{
            return sentences.get(sentenceNum);
        }
    }


    /**
     * This method returns the prompt filled in with the answers.
     * @return String
     */
    public String getCompletedPrompt(){
        StringBuffer prompt = new StringBuffer();

        for(Sentence sentence : sentences) {
            prompt.append(sentence.toString());
        }
        return prompt.toString();
    }


    @Override
    public Iterator<Sentence> iterator(){
        Iterator<Sentence> iter = new Iterator<Sentence>() {
            private int next = 0;

            @Override
            public boolean hasNext() {
                return sentences != null && next < sentences.size();
            }

            @Override
            public Sentence next() {
               if(hasNext()){
                   return sentences.get(next++);
               } else {
                   throw new NoSuchElementException("No more Sentences");
               }
            }
        };

        return  iter;
    }
}
