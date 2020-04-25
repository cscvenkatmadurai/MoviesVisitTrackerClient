package skven.com.moviesvisittracker.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {


    public static void addKey(final Context contentWrapper, final String fileName, final String keyName, final String value) {
        final SharedPreferences sharedPreferences = contentWrapper.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(keyName, value).apply();
    }

    public static String getKey(final ContextWrapper contentWrapper, final String fileName, final String keyName) {
        final SharedPreferences sharedPreferences = contentWrapper.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyName, null);
    }
}
