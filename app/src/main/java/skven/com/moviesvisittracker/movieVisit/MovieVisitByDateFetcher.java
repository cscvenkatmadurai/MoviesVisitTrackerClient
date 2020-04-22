package skven.com.moviesvisittracker.movieVisit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;

import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitMini;


public class MovieVisitByDateFetcher {

    private static MovieVisitMini[] EMPTY_ARRAY = new MovieVisitMini[0];
    private static final String TAG = "MovieVisitByDateFetcher";



    public  static void getMovieVisitByDate(final Context context, final String userId,
                                                 final long startTime, final long endTime,
                                                 final MovieVisitMiniListener movieVisitMiniListener) {
        System.out.println("MovieVisitByDateFetcher getMovieVisitByDate");
        if(startTime >= endTime) {
            Log.e(TAG, "getMovieVisitByDate: start date is greater than end date");
            return;
        }

        if(TextUtils.isEmpty(userId) ) {
            Log.e(TAG, "getMovieVisitByDate: userName is empty"  );
            return;
        }


        String url = "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit?userName=" + userId + "&startTime=" + startTime + "&endTime=" + endTime+"&by=date";
        System.out.println(url);
        HashMap<String, String> queryParameter = new HashMap<>();
        queryParameter.put("userName", userId);
        queryParameter.put("startTime", Long.toString(startTime));
        queryParameter.put("endTime", Long.toString(endTime));

        ConnectionManager.volleyStringRequest(context, true, null, url, Request.Method.GET,queryParameter, "fetch-home",  new GloxeyCallback.StringResponse() {
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
