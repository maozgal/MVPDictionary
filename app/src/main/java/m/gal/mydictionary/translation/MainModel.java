package m.gal.mydictionary.translation;

import android.content.Context;

import java.io.IOException;

import m.gal.mydictionary.AbstractMvpComponents.AbstractModel;
import m.gal.mydictionary.AbstractMvpComponents.MVP_Interface;
import m.gal.mydictionary.MyDictionaryApplication;
import m.gal.mydictionary.Word;
import m.gal.mydictionary.WordSharedPrefs;
import m.gal.mydictionary.serverModel.Retro;
import m.gal.mydictionary.serverModel.Yundex;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This class is a implementation of MVP_Interface.Model.
 * It is responsible for the heavy work (e.g web requests, DB queries ect.), it holds
 * a reference to the presenter for notifying when the heavy work been done, and the process should
 * go on.
 *
 * Created by gal on 15/01/2017.
 */

public class MainModel extends AbstractModel implements MVP_Interface_Translation.Model {

    public MainModel(MVP_Interface.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void translate(final String word) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Retro r = retrofit.create(Retro.class);
                Call<Yundex> call = r.translate("trnsl.1.1.20170125T064849Z.4fbcbbfcd9f7b505.4d5f9e960dfe6d5183de77f4a020a8c86f55370d",word,"en-he");

                try {
                    Yundex l = call.execute().body();
                    String translated = l.text[0];

                    //Notify the presenter that the translation has finished
                    ((MVP_Interface_Translation.Presenter.OpsRequiredFromPresenter)mPresenter).returnTranslatedWord(new Word(word,translated));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();


    }

    @Override
    public void insertWordToDB(Word currentWord, Context context) {
        MyDictionaryApplication.daoSession.getWordDao().insert(currentWord);
        WordSharedPrefs wordSharedPrefs = new WordSharedPrefs(context);
        wordSharedPrefs.addFirstWords(currentWord.getTranslation());
        // Notify the presenter that the insertion has finished
        ((MVP_Interface_Translation.Presenter.OpsRequiredFromPresenter)mPresenter).finishedDBInsertion(currentWord);
    }
}
