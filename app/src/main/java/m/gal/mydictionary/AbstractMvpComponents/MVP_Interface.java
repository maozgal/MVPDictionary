package m.gal.mydictionary.AbstractMvpComponents;

import android.content.Context;

/**
 * The big interface for MVP, it contains all the base methods for MVP based app.
 *
 * Created by gal on 22/02/2017.
 */

public interface MVP_Interface {
    interface Model{
        /**
         * Called by Presenter when View is destroyed
         * @param isChangingConfiguration   true configuration is changing
         */
        void onDestroy(boolean isChangingConfiguration);
    }
    interface View{

        Context getContext();
    }
    interface Presenter{
        void setModel(MVP_Interface.Model model);

        /**
         This method is part of the state maintainer mechanism.
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
}
