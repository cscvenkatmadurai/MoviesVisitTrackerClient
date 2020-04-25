package skven.com.moviesvisittracker.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import skven.com.moviesvisittracker.imdb.autocomplete.ImdbAutoCompleteFetcher;

@Module
public class ImdbAutoCompleteModule {

    @Provides
    @Singleton
    public ImdbAutoCompleteFetcher getImdbAutoCompleteFetcher(final Context context ) {
        return new ImdbAutoCompleteFetcher(context);
    }

}
