package Utils;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Stefani Moore on 11/7/2017.
 */

public class Sentence implements Iterable<Utils.Fragment>{
    private ArrayList<Utils.Fragment> fragments;

    public Sentence(){

        fragments = new ArrayList<Utils.Fragment>();
    }

    public void addFragment(Utils.Fragment fragment){
        if(fragment != null){
            fragments.add(fragment);
        } else {
            throw new IllegalArgumentException("Fragment argument can't be null");
        }
    }

    public Utils.Fragment getFragment(int fragmentNum){

        if(fragmentNum < 0 || fragmentNum > fragments.size()){
            throw new NoSuchElementException("Invalid fragmentNum");
        }

        return fragments.get(fragmentNum);
    }

    public ArrayList<Utils.Fragment> getFragments() {
        return new ArrayList<Utils.Fragment>(fragments);
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

        for(Utils.Fragment fragment : fragments){
            res.append(fragment).append(" ");
        }

        return res.toString();
    }

    public Iterator<Utils.Fragment> iterator(){
        Iterator<Utils.Fragment> iter = new Iterator<Utils.Fragment>() {

            private int next;

            @Override
            public boolean hasNext() {
               return (fragments != null && next < fragments.size());
            }

            @Override
            public Utils.Fragment next() {
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
