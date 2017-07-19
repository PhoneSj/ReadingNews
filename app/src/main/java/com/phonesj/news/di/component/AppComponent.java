package com.phonesj.news.di.component;

import com.phonesj.news.app.App;
import com.phonesj.news.di.module.AppModule;
import com.phonesj.news.di.module.HttpModule;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.db.RealmHelper;
import com.phonesj.news.model.http.RetrofitHelper;
import com.phonesj.news.model.sp.ImplSPHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Phone on 2017/7/14.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    /**
     * 包的上下文
     *
     * @return
     */
    App getApplicationContext();

    /**
     * 数据管理类：包括网络、数据库、sp中数据的操作
     *
     * @return
     */
    DataManager getDataManager();

    /**
     * 网络请求
     *
     * @return
     */
    RetrofitHelper getRetrofitHelper();

    /**
     * 数据库
     *
     * @return
     */
    RealmHelper getRealmHelper();

    /**
     * SP访问
     *
     * @return
     */
    ImplSPHelper getImplSPHelper();
}
