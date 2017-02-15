package m.gal.mydictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MVP_Interface.View{
    // This member is responsible to maintain the object's integrity
    // during configurations change
    private final StateMaintainer mStateMaintainer =
            new StateMaintainer(getFragmentManager(), MainActivity.class.getName());
    private MVP_Interface.Presenter.OpsOfferedByPresenter mPresenter;
    private TextView tvTranslation;
    private EditText etWord;
    private Button btSubmit;
    private ImageView ivInsert;
    private Word currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupMVP();

    }

    private void setupMVP() {
        // Check if StateMaintainer has been created
        if (mStateMaintainer.firstTimeIn()) {
            // Create the presenter
            MainPresenter mainPresenter = new MainPresenter(this);
            // Create the model
            MainModel model = new MainModel(mainPresenter);
            // Set model inside hte mainPresenter
            mainPresenter.setModel(model);
            // Add presenter and model to stateMaintainer
            mStateMaintainer.put(mainPresenter);
            mStateMaintainer.put(model);
            // Limits the communication by setting the presenter as interface
            mPresenter = mainPresenter;
        }
        // Get the Presenter from StateMaintainer
        else {
            // Get the Presenter
            mPresenter = mStateMaintainer.get(MainPresenter.class.getName());
            // Updated the View in Presenter
            mPresenter.setView(this);
        }
    }

    private void setupViews() {
        etWord = (EditText) findViewById(R.id.et_word);
        tvTranslation = (TextView) findViewById(R.id.tv_translation);
        ivInsert = (ImageView) findViewById(R.id.iv_insert);
        btSubmit = (Button) findViewById(R.id.bt_submit);

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
    }

    private void insertWordToDB() {
        if(currentWord!=null) {
            mPresenter.insertWordToDB(currentWord);
        }
    }

    private void sendTranslateRequest() {
        String word = etWord.getText().toString();
        if(!TextUtils.isEmpty(word)){
            mPresenter.translate(word);
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
    public void finishedDBInsertion() {
        //TODO notify the user
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }
}
