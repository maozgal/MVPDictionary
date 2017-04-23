package m.gal.mydictionary;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import m.gal.mydictionary.presentation.DaoMaster;
import m.gal.mydictionary.presentation.DaoSession;

/**
 * Application instance for GreenDAO initialize
 * Created by gal on 19/02/2017.
 */

public class MyDictionaryApplication extends Application {
    public static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "words-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
}
