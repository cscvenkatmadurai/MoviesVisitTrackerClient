package skven.com.moviesvisittracker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.constants.LoginConstants;
import skven.com.moviesvisittracker.date.DateUtil;
import skven.com.moviesvisittracker.helper.SharedPreferenceHelper;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMini;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMiniAdapter;
import skven.com.moviesvisittracker.ui.addMovieVisit.AddMovieVisitFragment;

public class HomeFragment extends Fragment implements CustomDateRangeSelectorAlertDialog.CustomDateRangeSelectorDTO{


    private static final String TAG = "###HomeFragment";
    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;
    private MovieVisitMiniAdapter mAdapter;
    private MaterialButtonToggleGroup toggleGroup;

    private TextView numMoviesWatched;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        toggleGroup  = root.findViewById(R.id.toggleGroup);
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(checkedId == R.id.this_month ) {
                if(recyclerView != null) {

                    populateVisitedMovies(DateUtil.getMonthStartInMilliSeconds(), System.currentTimeMillis());
                }
            }
            if(checkedId == R.id.this_year) {

                if(recyclerView != null) {


                    populateVisitedMovies(DateUtil.getYearStartInMilliSeconds(), System.currentTimeMillis());
                }

            }

            if(checkedId == R.id.custom_range) {
                numMoviesWatched.setVisibility(View.INVISIBLE);
                Log.i(TAG, "custom  is clicked");
                openCustomDateRangeSelectAlertDialog();
                mAdapter.setMoviesArray(new MovieVisitMini[0]);
                mAdapter.notifyDataSetChanged();


            }

        });

        numMoviesWatched = root.findViewById(R.id.moviesWatchedCount);


        populateVisitedMovies(DateUtil.getMonthStartInMilliSeconds(), System.currentTimeMillis());


        recyclerView = (RecyclerView) root.findViewById(R.id.movie_visits_recycler_view);

        mAdapter = new MovieVisitMiniAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        return root;
    }

    private void openCustomDateRangeSelectAlertDialog() {
        Log.i("### HomeFragment", "on openCustomDateRangeSelectAlertDialog");
        CustomDateRangeSelectorAlertDialog alertDialog = new CustomDateRangeSelectorAlertDialog();
        alertDialog.setTargetFragment(this, 0);
        alertDialog.show(getFragmentManager(), "CustomerDateRangeSelectorAlertDialog");
    }

    private void populateVisitedMovies(long startTime, long endTime) {
        String userId = SharedPreferenceHelper.getKey(getActivity(), LoginConstants.USER_ID, LoginConstants.USER_ID);
        String url = "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit?userName=" + userId + "&startTime=" + startTime + "&endTime=" + endTime;
        System.out.println(url);
        HashMap<String, String> queryParameter = new HashMap<>();
        queryParameter.put("userName", userId);
        queryParameter.put("startTime", Long.toString(startTime));
        queryParameter.put("endTime", Long.toString(endTime));
        ConnectionManager.volleyStringRequest(getContext(), false, null, url, Request.Method.GET,queryParameter, "fetch-home",  new GloxeyCallback.StringResponse() {
                    @Override
                    public void onResponse(String _response, String _tag) {

                        Log.i(TAG, "response \n" + _response);


                        try {
                            MovieVisitMini[] parse = GloxeyJsonParser.getInstance().parse(_response, MovieVisitMini[].class);
                            numMoviesWatched.setText("Number of movies watched: " + parse.length);
                            numMoviesWatched.setVisibility(View.VISIBLE);
                            Log.i(TAG + "parse", parse[0].toString());
                            mAdapter.setMoviesArray(parse);
                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void isConnected(boolean _connected, String _tag) {

                    }

                    @Override
                    public void onErrorResponse(VolleyError _error, boolean _onErrorResponse, String _tag) {

                        /**
                         * handle Volley Error
                         */
                        Log.e(TAG, "exception", _error);
                    }
                }
            );
    }



    @Override
    public void receive(long startDateInMilliSeconds, long endDateInMilliSeconds) {
        Log.i(TAG, "### receive method");
        toggleGroup.check(R.id.custom_range);
        populateVisitedMovies(startDateInMilliSeconds, endDateInMilliSeconds);

    }
}