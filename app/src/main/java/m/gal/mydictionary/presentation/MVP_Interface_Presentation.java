package m.gal.mydictionary.presentation;

import android.content.Context;

import java.util.List;

import m.gal.mydictionary.AbstractMvpComponents.MVP_Interface;

/**
 * Presentation interface, include all the business logic for presenting the game.
 *
 * Created by gal on 15/01/2017.
 */

public interface MVP_Interface_Presentation extends MVP_Interface {

    interface Model{
        /**
         *  This method retrieves all the words from the DB for the current session of
         *  quest. If there is no session, a new one will be created.
         * @return list of all the words in the current session
         */
        List<WordReplica> getAllWords();

        /**
         * Removes the word from session DB. Call this method after user answered correctly to the
         * question
         * @param correctAnswer The answer to be remove
         */
        void removeFromSessionDB(WordReplica correctAnswer);

        /**
         * Retrieves a list of words that were saved firstly. Use this when you need to complete
         * the question and you don't have enough word on the session.
         * @param context context
         * @return  List of words that were saved firstly.
         */
        List<WordReplica> getStaticWords(Context context);
    }

    interface View{
        /**
         * Sets the views with a new question parameters.
         * @param wordsForQuestion Array of words
         * @param correctAnswerIndex The correct index on the array
         * @param numOfWordsLeft The number of words until the session is over.
         */
        void setQuestionViews(WordReplica[] wordsForQuestion, int correctAnswerIndex,final int numOfWordsLeft);

        /**
         * Present a message to the user regarding to the status of his answer.
         * @param status The status of the answer.
         */
        void showAnswerStatus(PresentationPresenter.answerResults status);

        /**
         * Notifies the user when there are not enough words to start a session
         */
        void notifyLackOfWords();
    }

    interface Presenter{
        /**
         * This interface offers the view a way to communicate with the presenter.
         * It forwards operations form the view to the model
         */
        interface OpsOfferedByPresenter {
            /**
             * Start the process of getting a new question.
             */
            void setQuestion();

            /**
             * Start the process of submitting an answer.
             * @param selectedAnswer the selected answer id.
             */
            void submitAnswer(int selectedAnswer);
        }

        /**
         * This interface offers the model a way to communicate with the presenter.
         * It forwards operations form the model to the view.
         */
        interface OpsRequiredFromPresenter {
            /**
             * Callback, when the word has removed from the session DB, it forwards the process
             * of deleting a word and notifying the view.
             * @param sessionDbCount The new number of word on the session DB.
             */
            void onRemoveFromSessionDBFinished(long sessionDbCount);
        }
    }
}
