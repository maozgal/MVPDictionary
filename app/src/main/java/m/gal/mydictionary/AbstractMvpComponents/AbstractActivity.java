package m.gal.mydictionary.AbstractMvpComponents;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import m.gal.mydictionary.StateMaintainer;
import m.gal.mydictionary.translation.MainActivity;
import m.gal.mydictionary.translation.MainPresenter;

/**
 * Prototype for every activity under MVP design pattern.
 * It Handles the states of the activity and saves the original presenter and
 * model by using {@link StateMaintainer}
 *
 * Created by gal on 22/02/2017.
 */

public abstract class AbstractActivity extends AppCompatActivity implements MVP_Interface.View {

    private final StateMaintainer mStateMaintainer =
            new StateMaintainer(getFragmentManager(), MainActivity.class.getName());

    protected MVP_Interface.Presenter mPresenter;

    // The two abstract getters are for retrieving the actual instances at real time and
    // set the mvp components with them.
    protected abstract MVP_Interface.Presenter getPresenter();
    protected abstract MVP_Interface.Model getModel(MVP_Interface.Presenter presenter);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupMVP();
    }

    private void setupMVP() {
        // Check if StateMaintainer has been created
        if (mStateMaintainer.firstTimeIn()) {
            // Create the presenter
            MVP_Interface.Presenter presenter = getPresenter();
            // // Create the model
            MVP_Interface.Model model = getModel(presenter);
            // Set model inside hte mainPresenter
            presenter.setModel(model);
            // Add presenter and model to stateMaintainer
            mStateMaintainer.put(presenter);
            mStateMaintainer.put(model);
            // Limits the communication by setting the presenter as interface
            mPresenter = presenter;
        }
        // Get the Presenter from StateMaintainer
        else {
            // Get the Presenter
            mPresenter = mStateMaintainer.get(MainPresenter.class.getName());
            // Updated the View in Presenter
            mPresenter.setView(this);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }
}
