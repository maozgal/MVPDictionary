package m.gal.mydictionary.translation;

import android.content.Context;

import m.gal.mydictionary.AbstractMvpComponents.MVP_Interface;
import m.gal.mydictionary.Word;

/**
 * Translation interface, include all the business logic for translating.
 *
 * Created by gal on 15/01/2017.
 */

public interface MVP_Interface_Translation extends MVP_Interface {

    interface Model{
        /**
         * This method does the real translation work.
         * It opens communication on a background thread,
         * and pass the work the the translation server.
         * @param word a word for translation
         */
        void translate(String word);

        /**
         * This method handles the insertion to DB
         * @param currentWord the Word Object to be inserted inside the DB
         */
        void insertWordToDB(Word currentWord,Context context);
    }

    interface View extends MVP_Interface.View{
        /**
         * This method handles the end of the translation event.
         * When the word has been translated and we want to show it
         * to the user, this method is been called and shows the result
         * on the screen.
         * @param word the word to be shown.
         */
        void showTranslatedWord(Word word);

        /**
         * This method handles the end of the insertion to DB event.
         * It notify the user that the process has finished.
         * @param currentWord - The word which was inserted
         */
        void finishedDBInsertion(Word currentWord);
    }

    interface Presenter{
        /**
         * This interface offers the view a way to communicate with the presenter.
         * It forwards operations form the view to the model
         */
        interface OpsOfferedByPresenter {
            /**
             * This method takes a word and passes it to the model for translation
             * @param word the word to be translated.
             */
            void translate(String word);

            /**
             * This method takes a Word and passes it to the model for storing in the DB
             * @param currentWord the word to be stored.
             */
            void insertWordToDB(Word currentWord);
        }

        /**
         * This interface offers the model a way to communicate with the presenter.
         * It forwards operations form the model to the view.
         */
        interface OpsRequiredFromPresenter {
            /**
             * This method used by the model to notify the view that the translation has finished.
             * @param word The translated word
             */
            void returnTranslatedWord(Word word);

            /**
             * This method used by the model to notify the view that the insertion has finished.
             * @param currentWord The words that was just inserted
             */
            void finishedDBInsertion(Word currentWord);
        }
    }
}
