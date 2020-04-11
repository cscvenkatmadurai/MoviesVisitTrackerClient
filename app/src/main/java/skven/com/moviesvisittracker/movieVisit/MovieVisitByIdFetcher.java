package skven.com.moviesvisittracker.movieVisit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitByIdResponse;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitMini;

public class MovieVisitByIdFetcher {

    private static final String TAG = "MovieVisitByIdFetcher";


    public static void getMovieVisitByIdResponse(final Context context, final String userId,
                                                 final long startTime, final long endTime, final String by,
                                                 final MovieVisitByIdResponseListener listener) {

        if(startTime >= endTime) {
            Log.e(TAG, "getMovieVisitByDate: start date is greater than end date");
            return;
        }

        if(TextUtils.isEmpty(userId) ) {
            Log.e(TAG, "getMovieVisitByDate: userName is empty"  );
            return;
        }

        String url = "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit?userName=" + userId + "&startTime=" + startTime + "&endTime=" + endTime+"&by=" + by;


        ConnectionManager.volleyStringRequest(context, false, null, url, Request.Method.GET,null, "fetch-home",  new GloxeyCallback.StringResponse() {
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
