package com.phonesj.news.di.module;

import com.phonesj.news.app.App;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.db.DBHelper;
import com.phonesj.news.model.db.RealmHelper;
import com.phonesj.news.model.http.HttpHelper;
import com.phonesj.news.model.http.RetrofitHelper;
import com.phonesj.news.model.sp.ImplSPHelper;
import com.phonesj.news.model.sp.SPHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Phone on 2017/7/14.
 */

@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelpter(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(RealmHelper realmHelper) {
        return realmHelper;
    }

    @Provides
    @Singleton
    SPHelper provideSPHelper(ImplSPHelper implSPHelper) {
        return implSPHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DBHelper dbHelper, SPHelper spHelper) {
        return new DataManager(httpHelper, dbHelper, spHelper);
    }
}
