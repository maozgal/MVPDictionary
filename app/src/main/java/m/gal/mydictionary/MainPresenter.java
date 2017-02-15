package m.gal.mydictionary;

import java.lang.ref.WeakReference;

/**
 * This class is implementing two interfaces,  MVP_Interface.Presenter.OpsOfferedByPresenter and MVP_Interface.Presenter.OpsRequiredFromPresenter.
 * It passes messages and events from both, the view and the model, does some business logic, and deliver it to the destination.
 *
 * Created by gal on 15/01/2017.
 */

public class MainPresenter implements MVP_Interface.Presenter.OpsOfferedByPresenter,MVP_Interface.Presenter.OpsRequiredFromPresenter {

    // View reference. We use as a WeakReference
    // because the Activity could be destroyed at any time
    // and without WeakReference it could create a memory leak
    private WeakReference<MVP_Interface.View> mView;
    private MVP_Interface.Model mModel;

    public MainPresenter(MVP_Interface.View view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void translate(String word) {
        mModel.translate(word);
    }

    @Override
    public void insertWordToDB(Word currentWord) {
        mModel.insertWordToDB(currentWord);
    }

    @Override
    public void setView(MVP_Interface.View view) {
        this.mView = new WeakReference<>(view);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // View should be null every time onDestroy is called
        mView = null;
        // Inform Model about the event
        mModel.onDestroy(isChangingConfiguration);
        // When the activity is destroyed, isChangingConfiguration will be false
        if ( !isChangingConfiguration ) {
            // clears the model reference when the Activity destruction is permanent
            mModel = null;
        }
    }


    public void setModel(MVP_Interface.Model model) {
        mModel = model;
    }

    @Override
    public void returnTranslatedWord(Word word) {
        getView().showTranslatedWord(word);
    }

    @Override
    public void finishedDBInsertion() {
        getView().finishedDBInsertion();
    }

    /**
     * Return the View reference.
     * Could throw an exception if the View is unavailable.
     *
     * @return  {@link MVP_Interface.View}
     * @throws NullPointerException when View is unavailable
     */
    private MVP_Interface.View getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

}
