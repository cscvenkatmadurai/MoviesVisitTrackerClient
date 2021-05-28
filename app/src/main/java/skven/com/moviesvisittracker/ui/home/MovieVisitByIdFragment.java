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
import skven.com.moviesvisittracker.movieVisit.MovieVisitByIdAdapter;
import skven.com.moviesvisittracker.movieVisit.MovieVisitByIdFetcher;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitByIdResponse;

public class MovieVisitByIdFragment extends Fragment implements MovieVisitByIdFetcher.MovieVisitByIdResponseListener {
    private TextView numberOfMoviesWatched, numberOfDistinctMoviesWatched;
    RecyclerView recyclerView;
    MovieVisitByIdAdapter movieVisitByIdAdapter;
    @Inject
    MovieVisitByIdFetcher movieVisitByIdFetcher;

    private static final String TAG = "MovieVisitByIdFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Application.getAppComponent().inject(this);
        View root = inflater.inflate(R.layout.fragment_movie_visit_by_id, container, false);
        numberOfMoviesWatched = root.findViewById(R.id.total_movies_watched);
        numberOfDistinctMoviesWatched = root.findViewById(R.id.total_distinct_movies_watched);

        Bundle bundle = getArguments();
        long startTime, endTime;
        String by;
        if (bundle == null) {

            startTime = DateUtil.getYearStartInMilliSeconds();
            endTime = System.currentTimeMillis();
            by = "lang";

        } else {
            startTime = bundle.getLong("startTime");
            endTime = bundle.getLong("endTime");
            by = bundle.getString("by");

        }
        movieVisitByIdAdapter = new MovieVisitByIdAdapter(by);
        recyclerView = root.findViewById(R.id.fragment_movie_visits_by_id_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieVisitByIdAdapter);

        Log.i(TAG, "populateVisitedMovies: starting to populate movie for startDate: " + startTime + " endTime: " + endTime + " by: " + by );

        movieVisitByIdFetcher.getMovieVisitByIdResponse(startTime, endTime, by, this);

        return root;
    }

    @Override
    public void update(MovieVisitByIdResponse movieVisitByIdResponse) {

        numberOfMoviesWatched.setText(getString(R.string.num_movies_watched) + movieVisitByIdResponse.getNumberOfMovieOfMoviesWatched());
        numberOfDistinctMoviesWatched.setText("Number of distinct movies watched: " + movieVisitByIdResponse.getNumberOfDistinctMoviesWatched());
        movieVisitByIdAdapter.setMovieVisitByIds(movieVisitByIdResponse.getMovieVisitByIdList());
        movieVisitByIdAdapter.notifyDataSetChanged();


    }
}


