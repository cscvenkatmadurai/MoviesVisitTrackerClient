package skven.com.moviesvisittracker.movieVisit;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.constants.LoginConstants;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitMini;

@Singleton
public class MovieVisitByDateFetcher {

    private static MovieVisitMini[] EMPTY_ARRAY = new MovieVisitMini[0];
    private static final String TAG = "MovieVisitByDateFetcher";
    final Context context;
    final SharedPreferences sharedPreferences;


    @Inject
    public MovieVisitByDateFetcher(final Context context, @Named("loginSharedPreference") SharedPreferences loginSharedPreference) {
        this.context = context;
        this.sharedPreferences = loginSharedPreference;
    }


    public void getMovieVisitByDate(
            final long startTime, final long endTime,
            final MovieVisitMiniListener movieVisitMiniListener) {
        System.out.println("MovieVisitByDateFetcher getMovieVisitByDate");
        if (startTime >= endTime) {
            Log.e(TAG, "getMovieVisitByDate: start date is greater than end date");
            return;
        }

        final String userId = sharedPreferences.getString(LoginConstants.USER_ID, LoginConstants.USER_ID);
        if (TextUtils.isEmpty(userId)) {
            Log.e(TAG, "getMovieVisitByDate: userName is empty");
            return;
        }
        Log.i(TAG, "getMovieVisitByDate: context:" + context);

        String url = "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit?userName=" + userId + "&startTime=" + startTime + "&endTime=" + endTime + "&by=date";
        System.out.println(url);
        HashMap<String, String> queryParameter = new HashMap<>();
        queryParameter.put("userName", userId);
        queryParameter.put("startTime", Long.toString(startTime));
        queryParameter.put("endTime", Long.toString(endTime));

        ConnectionManager.volleyStringRequest(context, false, null, url, Request.Method.GET, queryParameter, "fetch-home", new GloxeyCallback.StringResponse() {
                    @Override
                    public void onResponse(String _response, String _tag) {
                        System.out.println("MovieVisitByDateFetcher " + _response);

                        Log.i(TAG, "response \n" + _response);


                        try {
                            MovieVisitMini[] parse = GloxeyJsonParser.getInstance().parse(_response, MovieVisitMini[].class);

                            Log.i(TAG, "movieVisitLength" + parse.length);

                            movieVisitMiniListener.update(parse);
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
                        System.out.println("MovieVisitByDateFetcher " + _error);

                    }
                }
        );


    }


    public interface MovieVisitMiniListener {
        void update(MovieVisitMini[] movieVisitMini);
    }
}
