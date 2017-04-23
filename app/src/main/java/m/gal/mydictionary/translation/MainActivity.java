package m.gal.mydictionary.translation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import m.gal.mydictionary.AbstractMvpComponents.AbstractActivity;
import m.gal.mydictionary.AbstractMvpComponents.MVP_Interface;
import m.gal.mydictionary.R;
import m.gal.mydictionary.Word;
import m.gal.mydictionary.presentation.PresentationActivity;

public class MainActivity extends AbstractActivity implements MVP_Interface_Translation.View{
    private TextView tvTranslation;
    private EditText etWord;
    private Button btSubmit;
    private Button btGame;
    private ImageView ivInsert;
    private Word currentWord;

    @Override
    protected MVP_Interface.Presenter getPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected MVP_Interface.Model getModel(MVP_Interface.Presenter presenter) {
        return new MainModel(presenter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        etWord = (EditText) findViewById(R.id.et_word);
        tvTranslation = (TextView) findViewById(R.id.tv_translation);
        ivInsert = (ImageView) findViewById(R.id.iv_insert);
        btSubmit = (Button) findViewById(R.id.bt_submit);
        btGame = (Button) findViewById(R.id.bt_game);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTranslateRequest();
            }
        });

        ivInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertWordToDB();
            }
        });

        btGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), PresentationActivity.class));
            }
        });
    }

    private void insertWordToDB() {
        if(currentWord!=null) {
            ((MVP_Interface_Translation.Presenter.OpsOfferedByPresenter)mPresenter).insertWordToDB(currentWord);
        }
    }

    private void sendTranslateRequest() {
        String word = etWord.getText().toString();
        if(!TextUtils.isEmpty(word)){
            ((MVP_Interface_Translation.Presenter.OpsOfferedByPresenter)mPresenter).translate(word);
        }
    }

    @Override
    public void showTranslatedWord(final Word word) {
        currentWord = word;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTranslation.setText(word.getOriginalWord() +"-"+ word.getTranslation());
            }
        });

    }

    @Override
    public void finishedDBInsertion(Word currentWord) {
        View parentLayout = findViewById(R.id.activity_main);
        Snackbar.make(parentLayout,currentWord.getOriginalWord() + " : Inserted",Snackbar.LENGTH_LONG).show();
        etWord.setText("");
        tvTranslation.setText("");
    }
}
