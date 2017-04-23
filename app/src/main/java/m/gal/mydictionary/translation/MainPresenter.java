package m.gal.mydictionary.translation;

import m.gal.mydictionary.AbstractMvpComponents.AbstractPresenter;
import m.gal.mydictionary.AbstractMvpComponents.MVP_Interface;
import m.gal.mydictionary.Word;

/**
 * This class is implementing two interfaces,  MVP_Interface.Presenter.OpsOfferedByPresenter and MVP_Interface.Presenter.OpsRequiredFromPresenter.
 * It passes messages and events from both, the view and the model, does some business logic, and deliver it to the destination.
 *
 * Created by gal on 15/01/2017.
 */

public class MainPresenter extends AbstractPresenter implements MVP_Interface_Translation.Presenter.OpsOfferedByPresenter,MVP_Interface_Translation.Presenter.OpsRequiredFromPresenter {

    public MainPresenter(MVP_Interface.View view) {
        super(view);
    }

    @Override
    public void translate(String word) {
        ((MVP_Interface_Translation.Model)mModel).translate(word);
    }

    @Override
    public void insertWordToDB(Word currentWord) {
        ((MVP_Interface_Translation.Model)mModel).insertWordToDB(currentWord,getView().getContext());
    }

    @Override
    public void returnTranslatedWord(Word word) {
        ((MVP_Interface_Translation.View)getView()).showTranslatedWord(word);
    }

    @Override
    public void finishedDBInsertion(Word currentWord) {
        ((MVP_Interface_Translation.View)getView()).finishedDBInsertion(currentWord);
    }

}
