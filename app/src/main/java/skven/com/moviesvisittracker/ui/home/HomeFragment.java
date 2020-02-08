package skven.com.moviesvisittracker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.constants.LoginConstants;
import skven.com.moviesvisittracker.helper.SharedPreferenceHelper;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMini;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMiniAdapter;

public class HomeFragment extends Fragment {


    private static final String TAG = "###HomeFragment";
    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;
    private MovieVisitMiniAdapter mAdapter;

    private TextView numMoviesWatched;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        numMoviesWatched = root.findViewById(R.id.moviesWatchedCount);


        String userId = SharedPreferenceHelper.getKey(getActivity(), LoginConstants.USER_ID, LoginConstants.USER_ID);
        String url = "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit?userName=" + userId;
        ConnectionManager.volleyStringRequest(getContext(), true, null, url, new VolleyResponse() {
            @Override
            public void onResponse(String _response) {

                /**
                 * Handle Response
                 */
                Log.i(TAG, "response \n" + _response );


                try {
                    MovieVisitMini[] parse = GloxeyJsonParser.getInstance().parse(_response, MovieVisitMini[].class);
                    numMoviesWatched.setText("Number of movies watched: " + parse.length);
                    numMoviesWatched.setVisibility(View.VISIBLE);
                    Log.i(TAG+"parse", parse[0].toString());
                    mAdapter.setMoviesArray(parse);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

                /**
                 * handle Volley Error
                 */
                Log.e(TAG, "exception", error);
            }

            @Override
            public void isNetwork(boolean connected) {
                Log.i(TAG, "isNetworkConnected" + connected);
                /**
                 * True if internet is connected otherwise false
                 */
            }
        });


        recyclerView = (RecyclerView) root.findViewById(R.id.movie_visits_recycler_view);

        mAdapter = new MovieVisitMiniAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        return root;
    }
}