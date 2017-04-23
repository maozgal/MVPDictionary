package m.gal.mydictionary.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import m.gal.mydictionary.AbstractMvpComponents.AbstractActivity;
import m.gal.mydictionary.AbstractMvpComponents.MVP_Interface;
import m.gal.mydictionary.R;

public class PresentationActivity extends AbstractActivity implements MVP_Interface_Presentation.View {

    private TextView tvWord;
    private TextView tvCounter;
    private RadioGroup rgAnswers;
    private Button btSubmit;

    @Override
    protected MVP_Interface.Presenter getPresenter() {
        return new PresentationPresenter(this);
    }

    @Override
    protected MVP_Interface.Model getModel(MVP_Interface.Presenter presenter) {
        return new PresentationModel(presenter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentaation);

        setupViews();
    }

    private void setupViews() {
        tvWord = (TextView) findViewById(R.id.tv_word_presentation);
        tvCounter = (TextView) findViewById(R.id.tv_counter);
        rgAnswers = (RadioGroup) findViewById(R.id.rg_answers);
        btSubmit = (Button) findViewById(R.id.bt_submit_presentation);
        
        setQuestion();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedAnswer  = getSelectedAnswer();
                if(selectedAnswer != -1){
                    ((MVP_Interface_Presentation.Presenter.OpsOfferedByPresenter)mPresenter).submitAnswer(selectedAnswer);
                }
            }
        });
    }

    private int getSelectedAnswer() {
        switch (rgAnswers.getCheckedRadioButtonId()){
            case R.id.rb_translation_presentation1:
                return 0;
            case R.id.rb_translation_presentation2:
                return 1;
            case R.id.rb_translation_presentation3:
                return 2;
            case R.id.rb_translation_presentation4:
                return 3;
            default:
                return -1;
        }
    }

    private void setQuestion() {
        ((MVP_Interface_Presentation.Presenter.OpsOfferedByPresenter)mPresenter).setQuestion();
    }

    @Override
    public void setQuestionViews(final WordReplica[] wordsForQuestion, final int correctAnswerIndex,final int numOfWordsLeft) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWord.setText(wordsForQuestion[correctAnswerIndex].getOriginalWord());

                for (int i = 0; i < rgAnswers.getChildCount(); i++) {
                    String translation = wordsForQuestion[i] != null ? wordsForQuestion[i].getTranslation() : "";
                    ((RadioButton) rgAnswers.getChildAt(i)).setText(translation);
                }
                rgAnswers.clearCheck();
                tvCounter.setText(String.valueOf(numOfWordsLeft));
            }
        });
    }

    @Override
    public void showAnswerStatus(PresentationPresenter.answerResults status) {
        final String answer;
        switch (status){
            case CORRECT:
                answer = "Great Job!";
                break;
            case FALSE:
                answer = "Wrong Answer";
                break;
            case FINISHED_SESSION:
                answer = "Well Done! You have finished the game";
                finish();
                break;
            default:
                answer = "";
                break;
        }

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getBaseContext(),answer,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void notifyLackOfWords() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
                Toast.makeText(getBaseContext(),"You need at least 7 words",Toast.LENGTH_LONG).show();
            }
        });

    }

}
