package Stories;

import android.app.Fragment;
import android.app.PendingIntent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public class Sentence implements Iterable<Stories.Fragment>{
    private ArrayList<Stories.Fragment> fragments;

    public Sentence(){
        fragments = new ArrayList<Stories.Fragment>();
    }
    public void addFragment(Stories.Fragment fragment){
        if(fragment != null){
            fragments.add(fragment);
        } else {
            throw new IllegalArgumentException("Fragment argument can't be null");
        }
    }

    public Stories.Fragment getFragment(int fragmentNum){

        if(fragmentNum < 0 || fragmentNum > fragments.size()){
            throw new NoSuchElementException("Invalid fragmentNum");
        }

        return fragments.get(fragmentNum);
    }

    public ArrayList<Stories.Fragment> getFragments() {
        return new ArrayList<Stories.Fragment>(fragments);
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

        for(Stories.Fragment fragment : fragments){
            res.append(fragment).append(" ");
        }

        return res.toString();
    }

    public Iterator<Stories.Fragment> iterator(){
        Iterator<Stories.Fragment> iter = new Iterator<Stories.Fragment>() {

            private int next;

            @Override
            public boolean hasNext() {
               return (fragments != null && next < fragments.size());
            }

            @Override
            public Stories.Fragment next() {
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
