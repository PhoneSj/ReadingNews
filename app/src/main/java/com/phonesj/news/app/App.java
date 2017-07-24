package com.phonesj.news.app;

import com.phonesj.news.di.component.AppComponent;
import com.phonesj.news.di.component.DaggerAppComponent;
import com.phonesj.news.di.module.AppModule;
import com.phonesj.news.di.module.HttpModule;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import io.realm.Realm;

/**
 * Created by Phone on 2017/7/14.
 */

public class App extends Application {

    private static App instance;
    private static AppComponent appComponent;
    private Set<Activity> allActivities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static synchronized App getInstance() {
        return instance;
    }

    //TODO 模式主题
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化屏幕宽高
        getScreenSize();
        //初始化数据库
        Realm.init(getApplicationContext());
        //TODO 其他初始化在子线程中完成
    }

    public void addActivity(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (allActivities != null) {
            allActivities.remove(activity);
        }
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);//屏幕尺寸信息写入参数中
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        //如果启动App时，手机整处于横屏，宽高值交换（这里保持宽<高）
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    /**
     * 强制退出app时，关闭所有Activity，杀死当前进程
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity activity : allActivities) {
                    activity.finish();
                }
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(instance))
                .httpModule(new HttpModule())
                .build();
        }
        return appComponent;
    }
}
