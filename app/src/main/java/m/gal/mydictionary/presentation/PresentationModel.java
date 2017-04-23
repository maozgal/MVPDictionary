package m.gal.mydictionary.presentation;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import m.gal.mydictionary.AbstractMvpComponents.AbstractModel;
import m.gal.mydictionary.AbstractMvpComponents.MVP_Interface;
import m.gal.mydictionary.MyDictionaryApplication;
import m.gal.mydictionary.Word;
import m.gal.mydictionary.WordSharedPrefs;

/**
 * Actual instace of {@link MVP_Interface_Presentation.Model}. implementing the business logic of presentation.
 *
 * Created by gal on 28/02/2017.
 */

public class PresentationModel extends AbstractModel implements MVP_Interface_Presentation.Model {

    public PresentationModel(MVP_Interface.Presenter presenter) {
        super(presenter);
    }

    @Override
    public List<WordReplica> getAllWords() {
        List<WordReplica> wordsForGameSession;
        if (MyDictionaryApplication.daoSession.getWordReplicaDao().count() == 0){
            fillReplica();
        }
        wordsForGameSession = MyDictionaryApplication.daoSession.getWordReplicaDao().loadAll();
        return wordsForGameSession;
    }

    @Override
    public void removeFromSessionDB(WordReplica correctAnswer) {
        MyDictionaryApplication.daoSession.getWordReplicaDao().delete(correctAnswer);
        long numberOfEntitiesInSession = MyDictionaryApplication.daoSession.getWordReplicaDao().count();
        ((MVP_Interface_Presentation.Presenter.OpsRequiredFromPresenter)mPresenter).onRemoveFromSessionDBFinished(numberOfEntitiesInSession);
    }

    @Override
    public List<WordReplica> getStaticWords(Context context) {
        WordSharedPrefs wordSharedPrefs = new WordSharedPrefs(context);
        Set<String> wordsSet = wordSharedPrefs.getFirstWords();
        List<WordReplica> wordslist = new ArrayList<>();
        for(String wordTranslation : wordsSet){
            wordslist.add(new WordReplica(0L,"",wordTranslation));
        }
        return wordslist;
    }

    private void fillReplica() {
        List<Word> realWords = MyDictionaryApplication.daoSession.getWordDao().loadAll();
        for (int i = 0;i < realWords.size() ;i++){
            Word word = realWords.get(i);
            WordReplica wordReplica = new WordReplica();
            wordReplica.setOriginalWord(word.getOriginalWord());
            wordReplica.setTranslation(word.getTranslation());
            MyDictionaryApplication.daoSession.getWordReplicaDao().insert(wordReplica);
        }
    }
}
