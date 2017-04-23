package m.gal.mydictionary.AbstractMvpComponents;

import java.lang.ref.WeakReference;

import m.gal.mydictionary.translation.MVP_Interface_Translation;

/**
 * Prototype for presenters, Handles the life Cycle.
 * Holding weak reference to the view
 *
 * Created by gal on 23/02/2017.
 */

public abstract class AbstractPresenter implements MVP_Interface.Presenter {

    // View reference. We use as a WeakReference
    // because the Activity could be destroyed at any time
    // and without WeakReference it could create a memory leak
    private WeakReference<MVP_Interface.View> mView;
    protected MVP_Interface.Model mModel;

    protected AbstractPresenter(MVP_Interface.View view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void setModel(MVP_Interface.Model model) {
        mModel = model;
    }

    @Override
    public void setView(MVP_Interface.View view) {
        this.mView = new WeakReference<>(view);
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return  {@link MVP_Interface_Translation.View}
     * @throws NullPointerException when View is unavailable
     */
    protected MVP_Interface.View getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // View should be null every time onDestroy is called
        mView = null;
        // Inform Model about the event
        mModel.onDestroy(isChangingConfiguration);
        // When the activity is destroyed, isChangingConfiguration will be false
        if (!isChangingConfiguration) {
            // clears the model reference when the Activity destruction is permanent
            mModel = null;
        }
    }
}
