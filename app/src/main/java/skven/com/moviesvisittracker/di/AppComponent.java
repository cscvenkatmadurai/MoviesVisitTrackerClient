package skven.com.moviesvisittracker.di;

import javax.inject.Singleton;

import dagger.Component;
import skven.com.moviesvisittracker.activity.LoginActivity;
import skven.com.moviesvisittracker.ui.addMovieVisit.AddMovieVisitFragment;
import skven.com.moviesvisittracker.ui.home.MovieVisitByDateFragment;
import skven.com.moviesvisittracker.ui.home.MovieVisitByIdFragment;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(final LoginActivity loginActivity);
    void inject(final MovieVisitByDateFragment o);
    void inject(final MovieVisitByIdFragment o);
    void inject(final AddMovieVisitFragment o);





}
