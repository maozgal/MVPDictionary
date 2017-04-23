package m.gal.mydictionary.presentation;

import java.util.List;

import m.gal.mydictionary.AbstractMvpComponents.AbstractPresenter;
import m.gal.mydictionary.AbstractMvpComponents.MVP_Interface;

/**
 * Actual instace of {@link MVP_Interface_Presentation.Presenter}
 *
 * Created by gal on 28/02/2017.
 */

public class PresentationPresenter extends AbstractPresenter implements MVP_Interface_Presentation.Presenter.OpsOfferedByPresenter,  MVP_Interface_Presentation.Presenter.OpsRequiredFromPresenter{

    private static final int NUMBER_OF_QUESTIONS = 4;
    private int correctAnswerIndex;
    private WordReplica correctAnswer;

    public enum answerResults {
        CORRECT,FALSE,FINISHED_SESSION
    }

    public PresentationPresenter(MVP_Interface.View view) {
        super(view);
        correctAnswerIndex = -1;
        correctAnswer = null;
    }

    @Override
    public void setQuestion() {
        List<WordReplica> words = ((MVP_Interface_Presentation.Model)mModel).getAllWords();
        int listSize = words.size();
        int questionsLeft = words.size();

        correctAnswerIndex = (int)(Math.random() * Math.min(listSize, NUMBER_OF_QUESTIONS));
        WordReplica[] wordsForQuestion = new WordReplica[NUMBER_OF_QUESTIONS];

        if(isListTooShort(listSize)){
            // Take a random word form the original list.
            correctAnswerIndex = (int)(Math.random() * listSize);
            WordReplica correctWordReplica = words.get(correctAnswerIndex);
            // Pull the static list from shared prefs.
            words = ((MVP_Interface_Presentation.Model)mModel).getStaticWords(getView().getContext());
            listSize = words.size();
            if(isStaticListLargeEnough(listSize)) {
                fillWordsFromStaticList(words, listSize, wordsForQuestion, correctWordReplica);
            }
            else {
                ((MVP_Interface_Presentation.View)getView()).notifyLackOfWords();
            }
            // The classic case, when the DB returns enough words for the game to work with.
        } else {
            fillWordsFromDB(words, listSize, wordsForQuestion);
        }

        passParametersToView(questionsLeft, wordsForQuestion);
    }

    private void fillWordsFromDB(List<WordReplica> words, int listSize, WordReplica[] wordsForQuestion) {
        for (int i = 0; i < wordsForQuestion.length; i++) {
            int randomIndex = (int) (Math.random() * listSize);
            wordsForQuestion[i] = words.get(randomIndex);
            words.remove(randomIndex);
            listSize--;
        }
    }

    private void fillWordsFromStaticList(List<WordReplica> words, int listSize, WordReplica[] wordsForQuestion, WordReplica correctWordReplica) {
        for (int i = 0; i < wordsForQuestion.length && words.size() > 0; i++) {
            // Find a random translation which is not equals to correctWordReplica.translation.
            int randomIndex;
            do {
                randomIndex = (int) (Math.random() * listSize);
            }
            while (words.get(randomIndex).getTranslation().equals(correctWordReplica.getTranslation()));
            if (randomIndex != -1) {
                wordsForQuestion[i] = words.get(randomIndex);
                words.remove(randomIndex);
                listSize--;
            }
        }
        wordsForQuestion[correctAnswerIndex] = correctWordReplica;
    }

    private void passParametersToView(int questionsLeft, WordReplica[] wordsForQuestion) {
        if(wordsForQuestion[wordsForQuestion.length-1] != null){
            correctAnswer = wordsForQuestion[correctAnswerIndex];

            ((MVP_Interface_Presentation.View) getView()).setQuestionViews(wordsForQuestion, correctAnswerIndex, questionsLeft);
        }
    }

    private boolean isStaticListLargeEnough(int listSize) {
        // The static list has to contain at least (2 * NUMBER_OF_QUESTIONS - 1) words, so even the last word on the
        // original list will have a enough translations for the question.
        return listSize > (2 * NUMBER_OF_QUESTIONS - 1);
    }

    private boolean isListTooShort(int listSize) {
        return listSize < NUMBER_OF_QUESTIONS;
    }

    @Override
    public void submitAnswer(int selectedAnswer) {
        if (correctAnswerIndex == selectedAnswer){
            ((MVP_Interface_Presentation.Model)mModel).removeFromSessionDB(correctAnswer);
        }
        else{
            ((MVP_Interface_Presentation.View)getView()).showAnswerStatus(answerResults.FALSE);
        }
    }

    @Override
    public void onRemoveFromSessionDBFinished(long sessionDbCount) {
        if(sessionDbCount > 0) {
            setQuestion();
            ((MVP_Interface_Presentation.View) getView()).showAnswerStatus(answerResults.CORRECT);
        }
        else{
            ((MVP_Interface_Presentation.View) getView()).showAnswerStatus(answerResults.FINISHED_SESSION);
        }
    }
}
