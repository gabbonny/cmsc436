package StoryUtil;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public class Sentence implements Iterable<StoryUtil.Fragment>{
    private ArrayList<StoryUtil.Fragment> fragments;

    public Sentence(){

        fragments = new ArrayList<StoryUtil.Fragment>();
    }

    public void addFragment(StoryUtil.Fragment fragment){
        if(fragment != null){
            fragments.add(fragment);
        } else {
            throw new IllegalArgumentException("Fragment argument can't be null");
        }
    }

    public StoryUtil.Fragment getFragment(int fragmentNum){

        if(fragmentNum < 0 || fragmentNum > fragments.size()){
            throw new NoSuchElementException("Invalid fragmentNum");
        }

        return fragments.get(fragmentNum);
    }

    public ArrayList<StoryUtil.Fragment> getFragments() {
        return new ArrayList<StoryUtil.Fragment>(fragments);
    }

    public void removeFragment(Fragment fragment){
        if (fragment != null) {
            fragments.remove(fragment);
        } else {
            throw new IllegalArgumentException("Fragment argument can't be null");
        }
    }
    public String toString(){
        StringBuffer res = new StringBuffer();

        for(StoryUtil.Fragment fragment : fragments){
            res.append(fragment).append(" ");
        }

        return res.toString();
    }

    public Iterator<StoryUtil.Fragment> iterator(){
        Iterator<StoryUtil.Fragment> iter = new Iterator<StoryUtil.Fragment>() {

            private int next;

            @Override
            public boolean hasNext() {
               return (fragments != null && next < fragments.size());
            }

            @Override
            public StoryUtil.Fragment next() {
                if(hasNext()){
                    return fragments.get(next++);
                } else {
                    throw new NoSuchElementException("No more fragments");
                }
            }
        };

        return iter;
    }

}
