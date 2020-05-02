package skven.com.moviesvisittracker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import skven.com.moviesvisittracker.Application;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.date.DateUtil;

import skven.com.moviesvisittracker.movieVisit.MovieVisitByDateFetcher;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitMini;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMiniAdapter;

public class MovieVisitByDateFragment extends Fragment implements MovieVisitByDateFetcher.MovieVisitMiniListener {

    private static final String TAG = "MovieVisitByDateFragment";
    private RecyclerView recyclerView;
    private MovieVisitMiniAdapter mAdapter;
    private TextView numMoviesWatched;

    @Inject
    MovieVisitByDateFetcher movieVisitByDateFetcher;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Application.getAppComponent().inject(this);
        View root = inflater.inflate(R.layout.fragment_movie_visit_by_date, container, false);

        Log.i(TAG, "onCreateView: ");

        numMoviesWatched = root.findViewById(R.id.moviesWatchedCount);
        recyclerView =  root.findViewById(R.id.movie_visits_recycler_view);
        mAdapter = new MovieVisitMiniAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        long startTime, endTime;

        Bundle bundle = getArguments();
        Log.i(TAG, "onCreateView: bundle:  " + bundle);
        if (getArguments() == null) {

            startTime = DateUtil.getMonthStartInMilliSeconds();
            endTime = System.currentTimeMillis();

        } else {
            startTime = bundle.getLong("startTime");
            endTime = bundle.getLong("endTime");

        }
        populateVisitedMovies(startTime, endTime);

        return root;


    }

    private void populateVisitedMovies(long startTime, long endTime) {
        Log.i(TAG, "populateVisitedMovies: starting to populate movie for startDate: " + startTime + " endTime: " + endTime);
        movieVisitByDateFetcher.getMovieVisitByDate(startTime, endTime, this);


    }


    @Override
    public void update(MovieVisitMini[] movieVisitMini) {
        numMoviesWatched.setText("Number of movies watched: " + movieVisitMini.length);
        numMoviesWatched.setVisibility(View.VISIBLE);
        mAdapter.setMoviesArray(movieVisitMini);
        mAdapter.notifyDataSetChanged();

    }
}
