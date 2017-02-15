package m.gal.mydictionary;

/**
 * Created by gal on 15/01/2017.
 */

public interface MVP_Interface {

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
        void insertWordToDB(Word currentWord);

        /**
         * Called by Presenter when View is destroyed
         * @param isChangingConfiguration   true configuration is changing
         */
        void onDestroy(boolean isChangingConfiguration);
    }

    interface View{
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
         */
        void finishedDBInsertion();
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

            /**
             * This method is part of the state maintainer mechanism.
             * It charges on setting a new instance of the view inside the
             * presenter, so the presenter will not hold a reference to null.
             * @param view the view to set.
             */
            void setView(MVP_Interface.View view);

            /**
             * This method is part of the state maintainer mechanism.
             * It been called by the view when it is being destroyed.
             * @param isChangingConfigurations   true: is changing configuration and will be recreated
             */
            void onDestroy(boolean isChangingConfigurations);
        }

        /**
         * This interface offers the model a way to communicate with the presenter.
         * It forwards operations form the model to the view.
         */
        interface OpsRequiredFromPresenter {
            /**
             * This method used by the model to notify the view that the translation has finished.
             * @param word the translated word
             */
            void returnTranslatedWord(Word word);

            /**
             * This method used by the model to notify the view that the insertion has finished.
             */
            void finishedDBInsertion();
        }
    }
}
