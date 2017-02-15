package m.gal.mydictionary;


/**
 * This class is a implementation of MVP_Interface.Model.
 * It is responsible for the heavy work (e.g web requests, DB queries ect.), it holds
 * a reference to the presenter for notifying when the heavy work been done, and the process should
 * go on.
 *
 * Created by gal on 15/01/2017.
 */

public class MainModel implements MVP_Interface.Model {
    private MVP_Interface.Presenter.OpsRequiredFromPresenter mPresenter;

    public MainModel(MVP_Interface.Presenter.OpsRequiredFromPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void translate(final String word) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO add logic for asking the server for translation

                // Notify the presenter that the translation has finished
                mPresenter.returnTranslatedWord(new Word(word,"translated_word"));
            }
        });
        t.start();


    }

    @Override
    public void insertWordToDB(Word currentWord) {
        // TODO add logic for DB insertion

        // Notify the presenter that the insertion has finished
        mPresenter.finishedDBInsertion();
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
        }
    }
}
