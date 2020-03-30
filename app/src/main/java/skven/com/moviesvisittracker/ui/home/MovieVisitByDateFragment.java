package skven.com.moviesvisittracker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.constants.LoginConstants;
import skven.com.moviesvisittracker.date.DateUtil;
import skven.com.moviesvisittracker.helper.SharedPreferenceHelper;
import skven.com.moviesvisittracker.movieVisit.MovieVisitByDateFetcher;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMini;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMiniAdapter;

public class MovieVisitByDateFragment extends Fragment implements MovieVisitByDateFetcher.MovieVisitMiniListener {

    private static final String TAG = "MovieVisitByDateFragment";
    private RecyclerView recyclerView;
    private MovieVisitMiniAdapter mAdapter;
    private TextView numMoviesWatched;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        String userId = SharedPreferenceHelper.getKey(getActivity(), LoginConstants.USER_ID, LoginConstants.USER_ID);
        MovieVisitByDateFetcher.getMovieVisitByDate(getContext(), userId, startTime, endTime, this);


    }


    @Override
    public void update(MovieVisitMini[] movieVisitMini) {
        numMoviesWatched.setText("Number of movies watched: " + movieVisitMini.length);
        numMoviesWatched.setVisibility(View.VISIBLE);
        mAdapter.setMoviesArray(movieVisitMini);
        mAdapter.notifyDataSetChanged();

    }
}
