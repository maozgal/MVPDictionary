package m.gal.mydictionary.AbstractMvpComponents;

/**
 * Prototype for models, Handles it's the life cycle.
 *
 * Created by gal on 26/02/2017.
 */

public abstract class AbstractModel implements MVP_Interface.Model {
    protected MVP_Interface.Presenter mPresenter;

    protected AbstractModel(MVP_Interface.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
        }
    }
}
