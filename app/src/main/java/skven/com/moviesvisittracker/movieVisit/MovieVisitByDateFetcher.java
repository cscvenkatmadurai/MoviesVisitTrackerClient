package skven.com.moviesvisittracker.movieVisit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;

import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;


public class MovieVisitByDateFetcher {

    private static MovieVisitMini[] EMPTY_ARRAY = new MovieVisitMini[0];
    private static final String TAG = "MovieVisitByDateFetcher";
    private MovieVisitMini[] result = EMPTY_ARRAY;


    public  MovieVisitMini[] getMovieVisitByDate(final Context context, final String userId,
                                                 final long startTime, final long endTime) {
        if(startTime >= endTime) {
            Log.e(TAG, "getMovieVisitByDate: start date is greater than end date");
            return  EMPTY_ARRAY;
        }

        if(TextUtils.isEmpty(userId) ) {
            Log.e(TAG, "getMovieVisitByDate: userName is empty"  );
            return EMPTY_ARRAY;
        }


        String url = "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit?userName=" + userId + "&startTime=" + startTime + "&endTime=" + endTime;
        System.out.println(url);
        HashMap<String, String> queryParameter = new HashMap<>();
        queryParameter.put("userName", userId);
        queryParameter.put("startTime", Long.toString(startTime));
        queryParameter.put("endTime", Long.toString(endTime));

        ConnectionManager.volleyStringRequest(context, false, null, url, Request.Method.GET,queryParameter, "fetch-home",  new GloxeyCallback.StringResponse() {
                    @Override
                    public void onResponse(String _response, String _tag) {

                        Log.i(TAG, "response \n" + _response);


                        try {
                            MovieVisitMini[] parse = GloxeyJsonParser.getInstance().parse(_response, MovieVisitMini[].class);
                            if(parse.length > 0) {
                                Log.i(TAG + "parse", parse[0].toString());
                            }
                            result =  parse;
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


        return result;

    }
}
