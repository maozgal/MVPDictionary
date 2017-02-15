package m.gal.mydictionary;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * This class responsible for preserving the state when the activity is destroyed for configuration change.
 * It holds a fragment that kept alive for all the life cycle of the screen, and stores the presenter inside it.
 * When the activity comes back from recreation, it will not build a new presenter, but will get it from this class.
 * The presenter holds reference to the model, so all in all, the whole state being preserved.
 *
 * One can also use this class to maintain any data he wants, since the preserving methods are generics.
 *
 * Created by gal on 05/02/2017.
 */

public class StateMaintainer {
    private final String mStateMaintainerTagOfCallerView;
    private final WeakReference<FragmentManager> mFragmentManager;
    private StateMngFragment mStateMaintainerFrag;

    public StateMaintainer(FragmentManager fragmentManager, String stateMaintainerTagOfCallerView) {
        mFragmentManager = new WeakReference<>(fragmentManager);
        mStateMaintainerTagOfCallerView = stateMaintainerTagOfCallerView;
    }

    /**
     * This method determines if the view has just created now or it
     * already have crated.
     * If the view has just created the method creates a new
     * fragment that maintain the state of the view.
     * Else, the method find is since it was added earlier to the fragment manager.
     * @return  true: view just created
     */
    public boolean firstTimeIn() {
        try {
            mStateMaintainerFrag = (StateMngFragment)
                    mFragmentManager.get().findFragmentByTag(mStateMaintainerTagOfCallerView);

            if (mStateMaintainerFrag == null) {
                createMaintainerFragment();
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * This method creates the StateMngFragment and adds it to  the fragment manager, so
     * it will be retained as long as the app is alive.
     */
    private void createMaintainerFragment() {
        mStateMaintainerFrag = new StateMngFragment();
        mFragmentManager.get().beginTransaction()
                .add(mStateMaintainerFrag, mStateMaintainerTagOfCallerView).commit();
    }

    /**
     * Insert the object to be preserved.
     * @param obj   object to maintain
     */
    public void put(Object obj) {
        mStateMaintainerFrag.put(obj.getClass().getName(), obj);
    }

    /**
     * Recovers the object saved
     * @param key   Object's TAG
     * @param <T>   Object type
     * @return      Object saved
     */
    public <T> T get(String key)  {
        return mStateMaintainerFrag.get(key);
    }

    /**
     * Fragment responsible to preserve objects.
     * Instantiated only once. Uses a hashmap to save objs
     */
    public static class StateMngFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // This line is mandatory, it grants that the fragment will be preserved
            // even when the activity is destroyed for configuration changes. This allows us to store the preserve the
            // presenter. So this fragment is used here as data fragment.
            setRetainInstance(true);
        }

        /**
         * Insert objects on the hashmap
         * @param key   Reference key
         * @param obj   Object to be saved
         */
        public void put(String key, Object obj) {
            mData.put(key, obj);
        }

        /**
         * Recovers saved object
         * @param key   Reference key
         * @param <T>   Object type
         * @return      Object saved
         */
        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }
}
