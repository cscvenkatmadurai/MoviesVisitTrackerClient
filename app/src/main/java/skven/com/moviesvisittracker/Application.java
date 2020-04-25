package skven.com.moviesvisittracker;

import skven.com.moviesvisittracker.di.AppComponent;
import skven.com.moviesvisittracker.di.AppModule;
import skven.com.moviesvisittracker.di.DaggerAppComponent;

public class Application extends android.app.Application {

    static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().
                appModule(new AppModule(getApplicationContext())).
                build();

    }

}
