package skven.com.moviesvisittracker.di;


import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import skven.com.moviesvisittracker.constants.LoginConstants;

@Module
public class AppModule {

    private final Context context;

    public AppModule(final Context context) {
        this.context = context;

    }


    @Provides
    @Singleton
    public Context getContext() {
        return context;
    }


    @Provides
    @Singleton
    @Named("loginSharedPreference")
    public SharedPreferences getLoginSharedPreference(final Context context) {
        return context.getSharedPreferences(LoginConstants.USER_ID, Context.MODE_PRIVATE);
    }
}
