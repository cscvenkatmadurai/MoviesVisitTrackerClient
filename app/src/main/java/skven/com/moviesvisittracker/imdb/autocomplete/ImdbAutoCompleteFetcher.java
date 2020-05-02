package skven.com.moviesvisittracker.imdb.autocomplete;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

import okhttp3.Response;
import skven.com.moviesvisittracker.imdb.autocomplete.dao.IMDBResponse;
import skven.com.moviesvisittracker.imdb.autocomplete.dao.IMDBSuggestions;

@Singleton
public class ImdbAutoCompleteFetcher {


    private static final String TAG = "ImdbAutoCompleteFetcher";
    private static List<IMDBSuggestions> EMPTY_LIST = new ArrayList<IMDBSuggestions>();
    final Context context;
    final OkHttpClient okHttpClient;

    @Inject
    public ImdbAutoCompleteFetcher(final Context context, final OkHttpClient httpClient) {
        this.context = context;
        this.okHttpClient = httpClient;
    }


    public void fetch(final String prefix) {
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

    public List<IMDBSuggestions> getSuggestions(final String prefix) {

        if(TextUtils.isEmpty(prefix)) {
            return EMPTY_LIST;
        }
        final String sanitizedPrefix = sanitizePrefix(prefix);

        final String url = "https://v2.sg.media-imdb.com/suggestion/titles/" + sanitizedPrefix.charAt(0) +"/" + sanitizedPrefix + ".json";


        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        try {
            String responseAsString = okHttpClient.newCall(request).execute().body().string();
            return GloxeyJsonParser.getInstance().parse(responseAsString, IMDBResponse.class).getSuggestions();
        }catch (Exception e){
            Log.e(TAG, "getSuggestions: exception in fetching autocomplete", e);
        }
        return EMPTY_LIST;

    }

    private static String sanitizePrefix(String prefix) {
        return prefix.toLowerCase().replace(" +", "_");

    }
}
