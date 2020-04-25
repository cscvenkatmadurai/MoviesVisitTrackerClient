package skven.com.moviesvisittracker.movieVisit;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;


import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.constants.LoginConstants;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitByIdResponse;

@Singleton
public class MovieVisitByIdFetcher {

    private static final String TAG = "MovieVisitByIdFetcher";


    final Context context;
    final SharedPreferences loginSharedPreference;

    @Inject
    public MovieVisitByIdFetcher(final Context context, @Named("loginSharedPreference") SharedPreferences loginSharedPreference) {
        this.context = context;
        this.loginSharedPreference = loginSharedPreference;
    }

    public void getMovieVisitByIdResponse(final long startTime, final long endTime, final String by,
                                          final MovieVisitByIdResponseListener listener) {

        if (startTime >= endTime) {
            Log.e(TAG, "getMovieVisitByDate: start date is greater than end date");
            return;
        }

        final String userId = loginSharedPreference.getString(LoginConstants.USER_ID, LoginConstants.USER_ID);
        if (TextUtils.isEmpty(userId)) {
            Log.e(TAG, "getMovieVisitByDate: userName is empty");
            return;
        }

        String url = "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit?userName=" + userId + "&startTime=" + startTime + "&endTime=" + endTime + "&by=" + by;


        ConnectionManager.volleyStringRequest(context, false, null, url, Request.Method.GET, null, "fetch-home", new GloxeyCallback.StringResponse() {
                    @Override
                    public void onResponse(String _response, String _tag) {

                        Log.i(TAG, "response \n" + _response);


                        try {
                            MovieVisitByIdResponse response = GloxeyJsonParser.getInstance().parse(_response, MovieVisitByIdResponse.class);
                            listener.update(response);
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


    public interface MovieVisitByIdResponseListener {
        void update(MovieVisitByIdResponse movieVisitByIdResponse);
    }
}
