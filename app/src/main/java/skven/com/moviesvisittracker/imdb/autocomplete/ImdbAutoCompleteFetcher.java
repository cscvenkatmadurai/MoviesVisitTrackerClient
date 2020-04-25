package skven.com.moviesvisittracker.imdb.autocomplete;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;

import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.imdb.autocomplete.dao.IMDBResponse;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitByIdResponse;

public class ImdbAutoCompleteFetcher {

    private static final String TAG = "ImdbAutoCompleteFetcher";

    public static void fetch(final String prefix, final Context context) {
        if(TextUtils.isEmpty(prefix)) {
            return;
        }
        final String sanitizedPrefix = sanitizePrefix(prefix);

        final String url = "https://v2.sg.media-imdb.com/suggestion/titles/" + sanitizedPrefix.charAt(0) +"/" + sanitizedPrefix + ".json";

        ConnectionManager.volleyStringRequest(context, false, null, url, Request.Method.GET,null, "fetch-home",  new GloxeyCallback.StringResponse() {
                    @Override
                    public void onResponse(String _response, String _tag) {

                        Log.i(TAG, "response \n" + _response);
                        Toast.makeText(context, _response, Toast.LENGTH_SHORT).show();
                        try {
                            IMDBResponse parse = GloxeyJsonParser.getInstance().parse(_response, IMDBResponse.class);
                            Toast.makeText(context, parse.getQuery(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private static String sanitizePrefix(String prefix) {
        return prefix.toLowerCase().replace(" +", "_");

    }
}
